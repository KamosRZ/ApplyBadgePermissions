package mc.pewdiepie.applybadgepermissions.managers;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

public class VaultPermissionsManager {

    public static void addPermission(Player player, String permission) {
        Permission perm = ApplyBadgePermissions.getPermissions();
        perm.playerAdd(null, player, permission);
   }
}
