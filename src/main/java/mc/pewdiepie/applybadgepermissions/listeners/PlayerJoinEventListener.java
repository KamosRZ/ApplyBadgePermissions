package mc.pewdiepie.applybadgepermissions.listeners;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import mc.pewdiepie.applybadgepermissions.managers.PermissionsManager;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinEventListener implements Listener {

    private final Configuration CONFIG;

    public PlayerJoinEventListener(ApplyBadgePermissions plugin) {
        this.CONFIG = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        String permission = CONFIG.getString("Joined.permissionNode");
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            PermissionsManager.addPermission(player, permission);
        }
    }
}
