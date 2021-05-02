### Player Permissions
First you must note that all permissions are a power of 2, if you want the player to have multiple permissions, all you must simply do is add the two values together
IE: 
```
Player + Donator + Owner = 1 + 2 + 128 = 131
```
Every player should have a base permission of `1 - Player` this number should not be <= 0 

These are the default permissions

| Permission Type | Value  |
|--|--|
| `PlayerPermissions.PLAYER` | 1 |
| `PlayerPermissions.DONATOR`| 2 |
| `PlayerPermissions.SUPER_DONATOR` | 4 |
| `PlayerPermissions.EXTREME_DONATOR` | 8 |
| `PlayerPermissions.HELPER` | 16 |
| `PlayerPermissions.MODERATOR` | 32 |
| `PlayerPermissions.ADMINISTRATOR` | 64 |
| `PlayerPermissions.OWNER`| 128 |
| `PlayerPermissions.DEVELOPER`| 256 |
| `PlayerPermissions.CONSOLE`| 512 |

### Registering custom permissions

You can register a new permission using the PlayerPermissions#addPermission function, this value must be an unused power of 2

### Example
```java
	PlayerPermissions.addPermission("Youtuber", 1024);
```
### Checking players permissions
You may now check if a player has the required permissions either by using the name of the permission or the value

```java
if(player.getPermissions().hasPermission("Youtuber")) {
	//allow user exclusive Youtuber access
}

OR

if(player.getPermissions().hasPermission(1024)) {
	//allow user exclusive Youtuber access
}
```

Now since each permission uses a power of 2, we can take advantage of this and use the binary operator `| (OR)` to OR the bits together and that allows us to set or check a players permissions to multiple different ranks simutaneously, neat eh?

```java
if(player.getPermissions().hasPermission(32 | 64 | 128 | 256) {
	/*
		this will check if the user has permissions for
		Moderator, Administrator, Owner Or Developer
	*/
}
```  
I don't expect you to write this all out for instance if you want to register a command that includes all donators or staff so I already wrote a few static helper fields
in the `PlayerPermissions` class for you
```java
public static final int ALL_STAFF = HELPER | MODERATOR | ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;  
public static final int MODERATOR_PLUS = MODERATOR | ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;  
public static final int ADMINISTRATOR_PLUS = ADMINISTRATOR | OWNER | DEVELOPER | CONSOLE;  
public static final int OWNER_PLUS = OWNER | DEVELOPER | CONSOLE;  
public static final int ALL_DONATORS = DONATOR | SUPER_DONATOR | EXTREME_DONATOR;  
public static final int SUPER_DONATOR_PLUS = SUPER_DONATOR | EXTREME_DONATOR;  
public static final int ALL_DONATORS_AND_STAFF = ALL_DONATORS | ALL_STAFF;
```

```java
	if(player.getPermissions().hasPermission(PlayerPermissions.ALL_DONATORS) {
		//allow all donators to teleport to donator zone
	}
```