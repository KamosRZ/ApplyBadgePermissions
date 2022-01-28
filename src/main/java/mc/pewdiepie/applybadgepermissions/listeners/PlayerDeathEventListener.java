package mc.pewdiepie.applybadgepermissions.listeners;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import mc.pewdiepie.applybadgepermissions.managers.PermissionsManager;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathEventListener implements Listener {

    private final Configuration CONFIG;

    public PlayerDeathEventListener(ApplyBadgePermissions plugin) {
        this.CONFIG = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerDiedPermission = CONFIG.getString("Died.permissionNode");
        if (!player.hasPermission(playerDiedPermission)) {
            PermissionsManager.addPermission(player, playerDiedPermission);
        }
    }

}
