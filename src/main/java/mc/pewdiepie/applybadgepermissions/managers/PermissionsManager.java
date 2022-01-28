package mc.pewdiepie.applybadgepermissions.managers;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import org.bukkit.entity.Player;

public class PermissionsManager {

    public static void addPermission(Player player, String permission) {
        if (ApplyBadgePermissions.usingLuckPerms()) {
            LuckPermsPermissionsManager.addPermission(player, permission);
        } else {
            VaultPermissionsManager.addPermission(player, permission);
        }
    }

}
