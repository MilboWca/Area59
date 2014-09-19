package server.model.players.packets;

import server.model.players.Client;
import server.model.players.MithrilSeeds;
import server.event.*;
import server.clip.*;
import server.clip.region.*;
import server.model.players.PacketType;
import server.model.items.Item;
import server.model.objects.Objects;
import server.model.players.skills.*;
import server.Server;

/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		HerbCleaning.handleHerbCleaning(c, itemId, itemSlot);
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
                if (itemId == 5073) {
c.getItems().addItem(5075, 1);
c.getItems().deleteItem(5073, 1);
c.getItems().handleNests (itemId);
}		
if (itemId == 8007) { //Varrock Tele-tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8007, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(3213, 3424, 0);
				c.buryDelay = System.currentTimeMillis();
			}	
		}	
if (itemId == 8008) { //Lumbridge tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8008, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(3222, 3218, 0);
				c.buryDelay = System.currentTimeMillis();
			}
		}
if (itemId == 8009) { //Falador Tele-tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8009, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(2965, 3379, 0);
				c.buryDelay = System.currentTimeMillis();
			}
		}
if (itemId == 8010) { //Camelot Tele-tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8010, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(2757, 3477, 0);
				c.buryDelay = System.currentTimeMillis();
			}
		}
if (itemId == 8011) { // Ardougne tele-tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8011, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(2661, 3305, 0);
				c.buryDelay = System.currentTimeMillis();
			}
		}
if (itemId == 8012) { //Watchtower Tele-tab
			if(System.currentTimeMillis() - c.buryDelay > 1500) {
				c.getItems().deleteItem(8012, 1);
				c.sendMessage("You break the teleport tab.");
				c.startAnimation(4731);
				c.gfx0(678);
				c.getPA().movePlayer(2549, 3112, 0);
				c.buryDelay = System.currentTimeMillis();
			}
		}		
if(itemId == 299) {
		final int[] coords = new int[2];
		coords[0] = c.absX;
		coords[1] = c.absY;

		if (System.currentTimeMillis() - c.lastFlower > c.DELAY) {
			if (c.getItems().playerHasItem(299,1)) {
				c.getItems().deleteItem2(299, 1);
				c.getPA().addSkillXP(2500, 19);
				Server.objectHandler.createAnObject(c, c.randomFlower(), coords[0], coords[1]);
				if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
					c.getPA().walkTo(-1, 0);
		     		} else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
					c.getPA().walkTo(1, 0);
		     		} else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
					c.getPA().walkTo(0, -1);
		       		} else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
					c.getPA().walkTo(0, 1);
		       		}
				c.turnPlayerTo(coords[0] + 1, coords[1]);
				c.lastFlower = System.currentTimeMillis();
				c.getDH().sendDialogues(22, 0);
				c.sendMessage("You plant a flower!");
				}

				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						for (int j = 0; j < Server.playerHandler.players.length; j++) {
						if (Server.playerHandler.players[j] != null) {
						Client c = (Client)Server.playerHandler.players[j];
							Server.objectHandler.createAnObject(c, -1, coords[0], coords[1]);
							c.getItems().addItem(MithrilSeeds.flower(), 1);
							container.stop();
						}
						}
					}
					@Override
					public void stop() {

					}
				}, 100);
			}
                }	
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (c.getHerblore().isUnidHerb(itemId))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId,itemSlot);
		if (c.getPrayer().IsABone(itemId))
			c.getPrayer().buryBone(itemId);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			}
		}
	}

}
