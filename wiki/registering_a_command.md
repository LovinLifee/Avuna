
### Registering a command  
You can register a command listener which is a class that has methods that are annotated with the `@Command` annotation and must have the signature `void (YourPlayerClass player, String commandName, ArgumentParser parser)`  
  
Here is an example of a simple yell command  
```java  
public class Commands { 
	@Command(aliases = {"yell", "shout"}, permissions = PlayerPermissions.ALL_DONATORS_AND_STAFF) 
	public void yellCommand(IPlayer player, String commandName, ArgumentParser parser) { 
		if(!parser.ensureTypes(String.class) { 			
			player.sendMessage(String.format("Please use as ::%s <message>", commandName)); } 
			return; 
		} 
        String message = parser.getString(0); 
        Avuna.getPlayers().sendMessageToAll(message);
	}
}
 ```
And we can actually register this listener with

```java
Avuna.getCommandHandler().registerCommandListener(new Commands());
```

Let's break down the `@Command` annotation

The `alias` value allows you to declare multiple names for the same command, this is to prevent code re-use so for instance you could have a command that teleports the player with the aliases `{"teleport", "tele", "tp", "move"} etc..`

The permissions value represents the permissions in which is required of you in order to be able to execute this command. For more information please see:
[Player Permissions](https://github.com/LovinLifee/Avuna/blob/master/wiki/player_permissions.md)