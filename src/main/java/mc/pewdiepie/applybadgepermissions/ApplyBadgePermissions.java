package mc.pewdiepie.applybadgepermissions;

import mc.pewdiepie.applybadgepermissions.listeners.PlayerAdvancementDoneEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerDeathEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerJoinEventListener;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApplyBadgePermissions extends JavaPlugin {

    private static Permission perms = null;
    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        registerListeners();
        setupPermissions();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneEventListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(this), this);

    }

    public static Permission getPermissions() {
        return perms;
    }
}
