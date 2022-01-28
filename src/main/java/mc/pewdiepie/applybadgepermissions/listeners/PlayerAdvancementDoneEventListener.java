package mc.pewdiepie.applybadgepermissions.listeners;

import mc.pewdiepie.applybadgepermissions.ApplyBadgePermissions;
import mc.pewdiepie.applybadgepermissions.managers.PermissionsManager;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class PlayerAdvancementDoneEventListener implements Listener {

    private final Configuration CONFIG;
    private final boolean playedBadgeEnabled;
    private final boolean dragonSlayerBadgeEnabled;
    private final boolean heldEggBadgeEnabled;

    public PlayerAdvancementDoneEventListener(ApplyBadgePermissions plugin) {
        this.CONFIG = plugin.getConfig();
        playedBadgeEnabled = CONFIG.getBoolean("Played.enabled");
        dragonSlayerBadgeEnabled = CONFIG.getBoolean("DragonSlayer.enabled");
        heldEggBadgeEnabled = CONFIG.getBoolean("HeldEgg.enabled");
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {

        String advancement = event.getAdvancement().getKey().toString();
        boolean isMajorAdvancement = checkIfMajorAdvancement(advancement);

        if (isMajorAdvancement) {
            Player player = event.getPlayer();

            //Played Badge functionality
            if (playedBadgeEnabled) {
                String playedBadgePermission = CONFIG.getString("Played.permissionNode");
                if (!player.hasPermission(playedBadgePermission)) {
                    PermissionsManager.addPermission(player, playedBadgePermission);
                }
            }

            //Other Badges
            checkSpecificAdvancement(advancement, player);
        }

    }

    private void checkSpecificAdvancement(String advancement, Player player) {

        if (dragonSlayerBadgeEnabled) {
            if (advancement.equals("minecraft:end/kill_dragon")) {
                String dragonSlayerBadgePermission = CONFIG.getString("DragonSlayer.permissionNode");
                if (!player.hasPermission(dragonSlayerBadgePermission)) {
                    PermissionsManager.addPermission(player, dragonSlayerBadgePermission);
                    return;
                }
            }
        }

        if (heldEggBadgeEnabled) {
            if (advancement.equals("minecraft:end/dragon_egg")) {
                String heldEggBadgePermission = CONFIG.getString("HeldEgg.permissionNode");
                if (!player.hasPermission(heldEggBadgePermission)) {
                    PermissionsManager.addPermission(player, heldEggBadgePermission);
                    return;
                }
            }
        }

    }

    private boolean checkIfMajorAdvancement(String advancement) {
        String[] majorAdvancementCategories = {"minecraft:story/", "minecraft:nether/", "minecraft:end/",
                "minecraft:adventure/", "minecraft:husbandry/"};

        for (String category : majorAdvancementCategories) {
            if (advancement.contains(category)) {
                return true;
            }
        }

        return false;
    }
}
