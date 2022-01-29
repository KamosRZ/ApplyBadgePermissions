package mc.pewdiepie.applybadgepermissions;

import mc.pewdiepie.applybadgepermissions.listeners.PlayerAdvancementDoneEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerDeathEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerJoinEventListener;
import mc.pewdiepie.applybadgepermissions.placeholders.BadgePlaceholdersExpansion;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class ApplyBadgePermissions extends JavaPlugin {

    private static Permission perms = null;
    private static boolean hasLuckPerms = false;
    private static boolean hasPlaceholderAPI = false;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        checkSoftDependencies();

        registerListeners();
        setupPermissions();
        registerPlaceholders();


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

        //Enabled checks are per advancement inside the Class
        getServer().getPluginManager().registerEvents(new PlayerAdvancementDoneEventListener(this), this);

        if (getConfig().getBoolean("Joined.enabled")) {
            getServer().getPluginManager().registerEvents(new PlayerJoinEventListener(this), this);
        }

        if (getConfig().getBoolean("Died.enabled")) {
            getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(this), this);
        }

    }

    private void registerPlaceholders() {
        if (hasPlaceholderAPI) {
            new BadgePlaceholdersExpansion(this).register();
        }
    }

    private void checkSoftDependencies() {
        hasLuckPerms = (getServer().getPluginManager().getPlugin("LuckPerms") != null);
        hasPlaceholderAPI = (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null);
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static boolean usingLuckPerms() {
        return hasLuckPerms;
    }
}
