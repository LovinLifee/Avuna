
### Npc Drops  
The NPC drops are stored in the json file: `./avuna/configs/npc-drops.json`
If this file doesn't exist, don't worry. When you bootstrap Avuna it will automatically create this directory for you as well as a default config.

The default config will look something like this

```json
{  
  "npcs": {  
    "1": {  
      "amountOfRolls": 1,  
	  "drops": [{  
          "itemId": 995,  
		  "minAmount": 500,  
		  "maxAmount": 500,  
		  "rarity": 0.1  
		}]  
	}
}
```

## Format
```java
json_object
	npcs //aray of npc objects
		npc_id //id of the npc you wish to drop the items
		
			/*
				The amount of times the drop table should be rolled
				for each kill on the NPC, this should normally be 1
				although you may want to choose more in special cases
				like bosses such as Zulrah where you roll 2 items per kill
			*/
			amountOfRolls
			drops //array of drops this npc should drop
				itemId //this is the item id you want the npc to drop
				minAmount //the minimum amount of this drop
				maxAmount //the maximum amount of this drop
				rarity //the chance of the item dropping
```

## The rarity `(chance of getting the item)`
The rarity is expressed as the quotient of a fraction. This should be between  0 and 1 inclusively where 0 means that the item should **never** be dropped, 1 meaning the item will **always** be dropped and every value in between will have a chance at dropping the item.

Let's say we wanted the rarity of the item to be 1/512 we would divide 1 by 512 and the rarity would be `0.001953125` for simplicity you could just round it to `0.00195` but this is optional