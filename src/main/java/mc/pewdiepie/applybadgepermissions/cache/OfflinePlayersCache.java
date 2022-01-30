package mc.pewdiepie.applybadgepermissions.cache;

import org.bukkit.OfflinePlayer;

import java.util.HashSet;

public class OfflinePlayersCache {

    private static HashSet<String> offlinePlayersCache;

    public static void instantiateOfflinePlayersCache() {
        offlinePlayersCache = new HashSet<>();
    }

    public static void addToOfflinePlayersCache(OfflinePlayer offlinePlayer, String permission) {
        offlinePlayersCache.add(constructOfflinePlayerPermissionPair(offlinePlayer, permission));
    }

    public static boolean containsOfflinePlayerPermissionPair(OfflinePlayer offlinePlayer, String permission) {
        return offlinePlayersCache.contains(constructOfflinePlayerPermissionPair(offlinePlayer, permission));
    }

    public static void clearOfflinePlayersCache() {
        offlinePlayersCache.clear();
    }

    private static String constructOfflinePlayerPermissionPair(OfflinePlayer offlinePlayer, String permission) {
        return offlinePlayer.getName() + "|" + permission;
    }
}
