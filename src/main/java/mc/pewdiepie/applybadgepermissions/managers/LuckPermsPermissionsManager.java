package mc.pewdiepie.applybadgepermissions.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.time.Duration;

public class LuckPermsPermissionsManager {

    public static void addPermission(Player player, String permission, long minutes) {
        final RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {

            LuckPerms lpApi = provider.getProvider();
            User user = lpApi.getUserManager().getUser(player.getUniqueId());
            Node permissionNode = buildPermissionNode(permission, minutes);

            user.data().add(permissionNode);
            lpApi.getUserManager().saveUser(user);

        }
    }

    public static void addPermission(Player player,String permission) {
        addPermission(player, permission, -1);
    }

    private static Node buildPermissionNode(String permission, long minutes) {
        if (minutes < 0) {
            return Node.builder(permission).build();
        } else {
            return Node.builder(permission).expiry(Duration.ofMinutes(minutes)).build();
        }
    }
}
