package mc.pewdiepie.applybadgepermissions.placeholders;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import mc.pewdiepie.applybadgepermissions.managers.PermissionsManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class BadgePlaceholdersExpansion extends PlaceholderExpansion  {

    private final ApplyBadgePermissions plugin;

    public BadgePlaceholdersExpansion(ApplyBadgePermissions plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "KamosRZ";
    }

    @Override
    public String getIdentifier() {
        return "pewbadges";
    }

    @Override
    public String getVersion() {
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
            return null;
        }

        if(splitParams[0].equalsIgnoreCase(hasPermissionPlaceholder)){

            if (splitParams[2].isEmpty()) {
                if (player.hasPermission(splitParams[1])) {
                    return Boolean.TRUE.toString();
                } else {
                    return Boolean.FALSE.toString();
                }
            }

            String viewingPermission = splitParams[1] + "." + splitParams[2];
            if (player.hasPermission(viewingPermission)) {
                return Boolean.TRUE.toString();
            } else {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                    OfflinePlayer viewedOfflinePlayer = Bukkit.getOfflinePlayerIfCached(splitParams[2]);
                    if (PermissionsManager.checkOfflinePlayerPermission(viewedOfflinePlayer, splitParams[1])) {
                        PermissionsManager.addTempPermission(player, viewingPermission, 3);
                    }
                });
            }

            return Boolean.FALSE.toString();

        }
        
        return null; // Placeholder is unknown by the Expansion
    }
}
