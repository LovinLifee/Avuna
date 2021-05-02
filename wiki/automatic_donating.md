
## Automatic donating
  
### Requirements  
This totally depends on having [KingFox's Donation V3 script](https://foxtrot-studios.co/view/16-store-v3-final)  

You don't need to register a command to claim the donation, I've already taken the liberty to do this for you.  
The command aliases are  
```java  
@Command(aliases = {"donated", "claimdonation","claimdonated"})
```    

### How to setup  
First you must configure the `./avuna/configs/donations-config.json` file, it should look something like this.

```json
{  
  "dbHost": "examplehost.com",  //host of the MySQL server
  "dbPort": 3306, //port of the MySQL server (3306 by default)
  "dbUsername": "admin", //username of the MySQL server
  "dbPassword": "password", //password
  "database": "example_database" //the database you used when setting up KingFox's store
}
```

Then all you need to do is register an event to listen for the `PlayerDonatedEvent` See: [Registering an event](https://github.com/LovinLifee/Avuna/blob/master/wiki/registering_an_event.md)    
    
Here's an example    
```java
private void onDonated(PlayerDonatedEvent event) {  
	String username = event.getTransaction().getUsername();  
	YourPlayerClass player = (YourPlayerClass) Avuna.getPlayers().lookup(username).get();  
	Avuna.getPlayers().sendMessageToAll(String.format("Please thank %s for making a generous donation of $%f :)", username, event.getTransaction().getPrice()));  
    //give the item to the player  
    //maybe use a discord webhook to announce it to your discord server as well!
}
```  
  
And finally, register the event.  
```java  
Avuna.getEventManager().registerEventListener(PlayerDonatedEvent.class, this::onDonated); 
```