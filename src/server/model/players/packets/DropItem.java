package server.model.players.packets;

import server.Config;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Drop Item
 **/
public class DropItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	
		int itemId = c.getInStream().readUnsignedWordA();
		c.getInStream().readUnsignedByte();
		c.getInStream().readUnsignedByte();
		int slot = c.getInStream().readUnsignedWordA();		
		//c.alchDelay = System.currentTimeMillis();
		if (System.currentTimeMillis() - c.alchDelay < 1800) {
			return;
		}
		if(c.arenas()) {
			c.sendMessage("You can't drop items inside the arena!");
			return;
		}
		if(c.inTrade) {
			c.sendMessage("You can't drop items while trading!");
			return;
			}

		boolean droppable = true;
		for (int i : Config.UNDROPPABLE_ITEMS) {
			if (i == itemId) {
				droppable = false;
				break;
			}
		}
		if(c.playerItemsN[slot] != 0 && itemId != -1 && c.playerItems[slot] == itemId + 1) {
			if(droppable) {
			/*if (c.getShops().getItemShopValue(itemId) > 5000000) {
					c.droppedItem = itemId;
					c.getPA().destroyInterface(itemId);
					return;
				}*/
				if (c.underAttackBy > 0) {
					if (c.getShops().getItemShopValue(itemId) > 1000) {
						c.sendMessage("You may not drop items worth more than 1000 while in combat.");
						return;
					}
				}
				c.sendMessage("Your item dissapears when it touches the ground.");
				c.getItems().deleteItem(itemId, slot, c.playerItemsN[slot]);
				server.model.players.PlayerSave.saveGame(c);
			} else {
				c.sendMessage("This items cannot be dropped.");
			}
		}

	}
}
