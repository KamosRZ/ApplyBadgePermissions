package mc.pewdiepie.applybadgepermissions.placeholders;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import mc.pewdiepie.applybadgepermissions.cache.OfflinePlayersCache;
import mc.pewdiepie.applybadgepermissions.managers.PermissionsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BadgePlaceholdersExpansion extends PlaceholderExpansion  {

    private final ApplyBadgePermissions plugin;

    public BadgePlaceholdersExpansion(ApplyBadgePermissions plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "KamosRZ";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "pewbadges";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    /**
     * This method controls the logic behind parsing all custom placeholders that start with %identifier_% (identifier
     * should be "pewbadges". It looks for the format: %pewbadges_perm|permission|username%. If username is
     * missing, it uses the player making the request to check if that player has the provided permission.
     *
     * If there is a provided username, it is first checked if that player and the provided permission are in the cache.
     * Returns true if they are, if not it performs an Async permissions check and adds the result in the cache. Since
     * this plugin is used for CommandPanels and they refresh every half second, the cache check will occur again really
     * fast and it will now contain the information if the provided username has the required permission.
     *
     * @param offlinePlayer = the player issuing the placeholder parse request.
     * @param params = contains everything after the %identifier_% in the used placeholder.
     * @return = true if the user has the permission, false if not. Can return null if the placeholder isn't known.
     */
    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {

        String hasPermissionPlaceholder = "perm";
        String[] splitParams = params.split("\\|");
        Player player = offlinePlayer.getPlayer();

        if (player == null) {
            return Boolean.FALSE.toString();
        }

        //Split params contain: [0] the sub-placeholder prefix (perm), [1] permission, [2] username (if provided)
        if(splitParams[0].equalsIgnoreCase(hasPermissionPlaceholder)){

            // This part handles if no username is provided.
            if (splitParams.length == 2) {
                if (player.hasPermission(splitParams[1])) {
                    return Boolean.TRUE.toString();
                } else {
                    return Boolean.FALSE.toString();
                }
            }

            //This loads an OfflinePlayer entity if the provided username has joined the server in the past
            OfflinePlayer viewedOfflinePlayer = Bukkit.getOfflinePlayerIfCached(splitParams[2]);
            if (viewedOfflinePlayer == null) {
                return Boolean.FALSE.toString();
            }

            //This performs the cache permission check
            if (OfflinePlayersCache.containsOfflinePlayerPermissionPair(viewedOfflinePlayer, splitParams[1])) {
                return Boolean.TRUE.toString();
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {

                    if (PermissionsManager.checkOfflinePlayerPermission(viewedOfflinePlayer, splitParams[1])) {
                        OfflinePlayersCache.addToOfflinePlayersCache(viewedOfflinePlayer, splitParams[1]);
                    }
                });
            }

            return Boolean.FALSE.toString();

        }

        return null; // Placeholder is unknown by the Expansion
    }
}
