package mc.pewdiepie.applybadgepermissions.listeners;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import net.milkbowl.vault.permission.Permission;
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

        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            Permission perm = ApplyBadgePermissions.getPermissions();
            perm.playerAdd(null, player, CONFIG.getString("Joined"));
        }
    }
}
