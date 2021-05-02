## Getting started
To keep Avuna server-agnostic this requires you to inject your own dependencies in to the framework so we can interface with them.

### Requirements
As of right now, Avuna is being compiled with and depends on JDK 11, this is always subject to change so you should always stay updated as I have plans to use features from JDK 15+

### Setting up
There's a few things we need to do manually before getting started, but once this is done you won't ever have to touch it again.


### Players
First you must locate the class that is responsible for your `Player`.
Depending on the server base this is usually called `Player` or `Client`

This class must now inherit from `net.avuna.game.entity.player.IPlayer`
and then you must implement the methods required and write them yourself.

Alternatively you can use the default implementation I have already written for you
`net.avuna.game.entity.player.AbstractPlayer`

Then you must bind the collection or array of players to Avuna
It will look something like this:

```java
    Avuna.getPlayers().bind(PlayerHandler.players);
```

### NPCs

Next we must locate our NPC class and make it inherit from `net.avuna.game.entity.IEntity.INpc`
I am sorry, there is no default implementation for this, you must complete the functions yourself

Then much like the players, we need to bind the collection or array of the NPCs to Avuna.
It will look something like this:

```java
Avuna.getNpcs().bind(NpcHandler.npcs);
```

### Finally
And finally, in the main entry point of the application, we need to bootstrap Avuna.
What this does is tell Avuna to start everything up, create the configs if they don't exist, load configs etc..
All you need to do is call this from the main function
```java
Avuna.bootstrap();
```