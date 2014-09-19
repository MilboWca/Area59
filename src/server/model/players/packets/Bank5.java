package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.skills.*;

/**
 * Bank 5 Items
 **/
public class Bank5 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
	int interfaceId = c.getInStream().readSignedWordBigEndianA();
	int removeId = c.getInStream().readSignedWordBigEndianA();
	int removeSlot = c.getInStream().readSignedWordBigEndian();
	
		switch(interfaceId){
		case 4233:
			JewelryMaking.jewelryMaking(c, "RING", removeId, 5);
			break;
		case 4239:
			JewelryMaking.jewelryMaking(c, "NECKLACE", removeId, 5);
			break;
		case 4245:
			JewelryMaking.jewelryMaking(c, "AMULET", removeId, 5);
			break;
			
			case 3900:
			c.getShops().buyItem(removeId, removeSlot, 1);
			break;
			
			case 3823:
			c.getShops().sellItem(removeId, removeSlot, 1);
			break;
			
			case 5064:
			c.getItems().bankItem(removeId, removeSlot, 5);
			break;
			
			case 5382:
			c.getItems().fromBank(removeId, removeSlot, 5);
			break;
			
			case 3322:
			if(c.duelStatus <= 0) { 
                c.getTradeAndDuel().tradeItem(removeId, removeSlot, 5);
           	} else {
				c.getTradeAndDuel().stakeItem(removeId, removeSlot, 5);
			}	
			break;
			
			case 3415:
			if(c.duelStatus <= 0) { 
				c.getTradeAndDuel().fromTrade(removeId, removeSlot, 5);
			}
			break;
			
			case 6669:
			c.getTradeAndDuel().fromDuel(removeId, removeSlot, 5);
			break;

			
		}
	}

}
