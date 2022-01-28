package mc.pewdiepie.applybadgepermissions.managers;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPermsPermissionsManager {

    public static void addPermission(Player player,String permission) {
        final RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

        if (provider != null) {

            LuckPerms lpApi = provider.getProvider();
            User user = lpApi.getUserManager().getUser(player.getUniqueId());
            Node permissionNode = Node.builder(permission).build();

            user.data().add(permissionNode);
            lpApi.getUserManager().saveUser(user);

        }
    }
}
