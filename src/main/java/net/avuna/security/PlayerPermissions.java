package net.avuna.security;

import net.avuna.util.MathUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

/*
    TODO: add a way to dynamically add permissions
    TODO: fix legacy permissions
 */
public class PlayerPermissions {

    /*
        WARNING: These are subject to change in the future, please use with a grain of salt, if they are changed this may
        cause a security issue

        To avoid any issues, please use these static fields where applicable instead of the number literally.
    */

    /**
     * CAUTION: NEVER set players rights to ANY, this will give them ALL privileges.
     */
    public static final long ANY = Long.MAX_VALUE;

    public static final long PLAYER = 1;
    public static final long DONATOR = 1 << 1;
    public static final long SUPER_DONATOR = 1 << 2;
    public static final long EXTREME_DONATOR = 1 << 3;
    public static final long HELPER = 1 << 4;
    public static final long MODERATOR = 1 << 5;
    public static final long ADMINISTRATOR = 1 << 6;
    public static final long OWNER = 1 << 7;
    public static final long DEVELOPER = 1 << 8;
    public static final long CONSOLE = 1 << 9;

    public static final long ALL_STAFF = HELPER | MODERATOR | ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;
    public static final long MODERATOR_PLUS = MODERATOR | ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;
    public static final long ADMINISTRATOR_PLUS = ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;
    public static final long OWNER_PLUS = OWNER | DEVELOPER | CONSOLE;
    public static final long ALL_DONATORS = DONATOR | SUPER_DONATOR | EXTREME_DONATOR;
    public static final long SUPER_DONATOR_PLUS = SUPER_DONATOR | EXTREME_DONATOR;
    public static final long ALL_DONATORS_AND_STAFF = ALL_DONATORS | ALL_STAFF;

    private static final Map<String, Long> RANKS = new HashMap<>();

    static {
        for(Field f : PlayerPermissions.class.getDeclaredFields()) {
            if(Modifier.isStatic(f.getModifiers()) && Modifier.isFinal(f.getModifiers()) & f.getType() == long.class) {
                try {
                    RANKS.putIfAbsent(f.getName(), f.getLong(null));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void addPermission(String name, long permissions) {
        if(!MathUtils.IsPowerOfTwo(permissions, true)) {
            throw new IllegalArgumentException("Permissions must be a power of 2");
        }
        if(RANKS.containsKey(name)) {
            throw new IllegalArgumentException("You may not override ranks that already exist");
        } else {
            RANKS.put(name, permissions);
        }
    }

    private long permissions;

    private PlayerPermissions(long permissions) {
        this.permissions = permissions;
    }

    public long getPermissions() {
        return permissions;
    }

    public boolean isPlayer() {
        return hasPermission(PLAYER);
    }

    /**
     * @return true if player is a donator, super donator or extreme donator
     */
    public boolean isDonator() {
        return hasPermission(DONATOR) || isSuperDonator() || isExtremeDonator();
    }

    /**
     * @return true if player is a super donator or an extreme donator
     */
    public boolean isSuperDonator() {
        return hasPermission(SUPER_DONATOR) || isExtremeDonator();
    }

    public boolean isExtremeDonator() {
        return hasPermission(EXTREME_DONATOR);
    }

    public boolean isHelper() {
        return hasPermission(HELPER);
    }

    public boolean isModerator() {
        return hasPermission(MODERATOR);
    }

    public boolean isAdministrator() {
        return hasPermission(ADMINISTRATOR);
    }

    public boolean isOwner() {
        return hasPermission(OWNER);
    }

    public boolean isDeveloper() {
        return hasPermission(DEVELOPER);
    }

    public boolean isConsole() {
        return hasPermission(CONSOLE);
    }

    /**
     * @return true if player is either a helper, moderator, administrator or owner
     */
    public boolean isStaff() {
        return hasPermission(ALL_STAFF);
    }

    public boolean hasPermission(long permissions) {
        checkPermissions(permissions);
        return (getPermissions() & permissions) != 0;
    }

    public boolean hasPermission(String permissionName) {
        return hasPermission(RANKS.get(permissionName));
    }

    public static PlayerPermissions of(long permissions) {
        checkPermissions(permissions);
        return new PlayerPermissions(permissions);
    }

    public void setPermissions(long permissions) {
        checkPermissions(permissions);
        this.permissions = permissions;
    }

    public static PlayerPermissions fromOldRights(long permissions) {
        long modifier = PLAYER;
        if(permissions == 1) {
            modifier = MODERATOR;
        } else if(permissions == 2) {
            modifier = ADMINISTRATOR;
        } else if(permissions == 3) {
            modifier = OWNER;
        }
        return of(modifier);
    }

    private static void checkPermissions(long permissions) {
        if(permissions <= 0) {
            throw new IllegalArgumentException("permissions must be >= 1");
        }
    }

    private static final Set<String> EXEMPT = Set.of("ANY", "ALL_STAFF", "MODERATOR_PLUS", "ADMINISTRATOR_PLUS", "OWNER_PLUS", "ALL_DONATORS",
            "SUPER_DONATOR_PLUS", "ALL_DONATORS_AND_STAFF");

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        RANKS.entrySet().forEach(e -> {
            if(EXEMPT.contains(e.getKey())) {
                return;
            }
            if(hasPermission(e.getValue())) {
                joiner.add(e.getKey());
            }
        });
        return getPermissions() + "=" + joiner.toString();
    }
}