package mc.pewdiepie.applybadgepermissions.managers;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class VaultPermissionsManager {

    private static Permission perm = ApplyBadgePermissions.getPermissions();
    public static void addPermission(Player player, String permission) {
        perm.playerAdd(null, player, permission);
   }

   //Do not use on main thread
   public static boolean hasPermission(OfflinePlayer offlinePlayer, String permission) {
        return perm.playerHas(null, offlinePlayer, permission);
   }
}
