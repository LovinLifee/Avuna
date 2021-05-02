
## Automatic voting  
  
### Requirements  
This totally depends on having [KingFox's Vote V2 script](https://foxtrot-studios.co/view/15-vote-2.0)  

You don't need to register a command to claim the vote, I've already taken the liberty to do this for you.  
The command aliases are  
```java  
@Command(aliases = {"voted", "claimvotes", "claimvote"})  
```    

### How to setup  
First you must configure the `./avuna/configs/vote-config.json` file, it should look something like this.

```json
{  
  "apiUrl": "https://yourwebsite.com/vote/api",  
  "bearerToken": "YOUR_BEARER_TOKEN_GOES_HERE"
}
```

The `apiUrl` must be set to the location of the rest endpoint of KingFox's script (you must exclude a trailing slash)

And on the control panel it should show you the bearer token that you need to set here.
 
Then you need to register an event to listen for the `PlayerVotedEvent`  
See: [Registering an event](https://github.com/LovinLifee/Avuna/blob/master/wiki/registering_an_event.md)  
  
Here's an example  
```java  
private void onVote(PlayerVoteEvent event) {  
   YourPlayerClass player = (YourPlayerClass) Avuna.getPlayers().lookup(event.getUsername()).get();  
 if(event.noVotes()) {  
	player.sendMessage("Unfortunately, you have no votes to claim. :(");  
  } else {  
    final int coins = 995;  
	final int amount = 1_000_000 * event.getData().length; //this will give the player 1 million coins per time that they voted   
	player.giveItem(coins, amount);  
	player.sendMessage("Thank you for voting. :)");  
  }  
} 
```

And finally, register the event.
```java
Avuna.getEventManager().registerEventListener(PlayerVoteEvent.class, this::onVote); 
```