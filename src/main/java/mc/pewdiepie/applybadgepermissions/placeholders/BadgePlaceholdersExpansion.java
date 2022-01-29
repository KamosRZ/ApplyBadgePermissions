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

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {

        String hasPermissionPlaceholder = "perm";
        String[] splitParams = params.split("\\|");
        Player player = offlinePlayer.getPlayer();

        if (player == null) {
            return Boolean.FALSE.toString();
        }

        if(splitParams[0].equalsIgnoreCase(hasPermissionPlaceholder)){

            if (splitParams.length == 2) {
                if (player.hasPermission(splitParams[1])) {
                    return Boolean.TRUE.toString();
                } else {
                    return Boolean.FALSE.toString();
                }
            }

            //String viewingPermission = splitParams[1] + "." + splitParams[2];
            OfflinePlayer viewedOfflinePlayer = Bukkit.getOfflinePlayerIfCached(splitParams[2]);
            if (viewedOfflinePlayer == null) {
                return Boolean.FALSE.toString();
            }

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
