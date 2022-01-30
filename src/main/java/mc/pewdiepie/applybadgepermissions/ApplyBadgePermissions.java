package mc.pewdiepie.applybadgepermissions;

import mc.pewdiepie.applybadgepermissions.cache.OfflinePlayersCache;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerAdvancementDoneEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerDeathEventListener;
import mc.pewdiepie.applybadgepermissions.listeners.PlayerJoinEventListener;
import mc.pewdiepie.applybadgepermissions.placeholders.BadgePlaceholdersExpansion;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
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

        loadOfflinePlayersCache();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Loads the Vault permissions module. Throws an error if Vault is not installed.
     */
    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
    }

    /**
     * Register the Bukkit API listeners. Checks for enabled config options for some of the badges.
     */
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

    /**
     * Registers a PlaceHolderAPI expansion if it's installed.
     */
    private void registerPlaceholders() {
        if (hasPlaceholderAPI) {
            new BadgePlaceholdersExpansion(this).register();
        }
    }

    /**
     * Instantiates a player cache used to store player permissions. It's used because Offline players permission check
     * is not safe to perform on the main thread, so it's done Async and saved in the cache for main thread use. This
     * method also registers a repeating task that clears the cache.
     */
    private void loadOfflinePlayersCache() {
        OfflinePlayersCache.instantiateOfflinePlayersCache();

        Bukkit.getScheduler().runTaskTimerAsynchronously(this,
                OfflinePlayersCache::clearOfflinePlayersCache, 0L, 432000L);
    }

    /**
     * Checks if plugins used for some features are installed on the server.
     */
    private void checkSoftDependencies() {
        hasLuckPerms = (getServer().getPluginManager().getPlugin("LuckPerms") != null);
        hasPlaceholderAPI = (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null);
    }

    /**
     * Provides access to Vault's permissions module.
     * @return = Vault's permission module.
     */
    public static Permission getPermissions() {
        return perms;
    }

    /**
     * Returns true if LuckPerms is installed on the server.
     * @return = true if LP is installed, false if not.
     */
    public static boolean usingLuckPerms() {
        return hasLuckPerms;
    }
}
