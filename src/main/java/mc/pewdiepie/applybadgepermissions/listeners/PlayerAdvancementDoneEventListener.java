package mc.pewdiepie.applybadgepermissions.listeners;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneEventListener implements Listener {

    private final Configuration CONFIG;

    public PlayerAdvancementDoneEventListener(ApplyBadgePermissions plugin) {
        this.CONFIG = plugin.getConfig();
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {

        boolean isMajorAdvancement = false;
        String advancement = event.getAdvancement().getKey().toString();
        if (advancement.contains("minecraft:story/") || advancement.contains("minecraft:nether/")
                || advancement.contains("minecraft:end/")) {
            isMajorAdvancement = true;
        }

        if (isMajorAdvancement) {
            Player player = event.getPlayer();

            //Played Badge functionality
            String playedBadgePermission = CONFIG.getString("Played");
            if (!player.hasPermission(playedBadgePermission)) {
                ApplyBadgePermissions.getPermissions().playerAdd(null, player, playedBadgePermission);
            }

            //Other Badges
            checkSpecificAdvancement(advancement, player);
        }

    }

    private void checkSpecificAdvancement(String advancement, Player player) {

        String dragonSlayerBadgePermission = CONFIG.getString("DragonSlayer");
        String heldEggBadgePermission = CONFIG.getString("HeldEgg");

        if (advancement.equals("minecraft:end/kill_dragon")) {
            if (!player.hasPermission(dragonSlayerBadgePermission)) {
                ApplyBadgePermissions.getPermissions().playerAdd(null, player, dragonSlayerBadgePermission);
            }
        } else if (advancement.equals("minecraft:end/dragon_egg")) {
            if (!player.hasPermission(heldEggBadgePermission)) {
                ApplyBadgePermissions.getPermissions().playerAdd(null, player, heldEggBadgePermission);
            }
        }
    }
}
