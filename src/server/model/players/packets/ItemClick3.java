package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;

/**
 * Item Click 3 Or Alternative Item Option 1
 * 
 * @author Ryan / Lmctruck30
 * 
 * Proper Streams
 */

public class ItemClick3 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int itemId11 = c.getInStream().readSignedWordBigEndianA();
		int itemId1 = c.getInStream().readSignedWordA();
		int itemId = c.getInStream().readSignedWordA();
		if(!c.getItems().playerHasItem(itemId, 1)) {
			return;
		}	

		switch (itemId) {
case 2552:
		case 2554:
		case 2556:
		case 2558:
		case 2560:
		case 2562:
		case 2564:
		case 2566:
			c.getPA().ROD();
		break;
		
case 1712:
c.getPA().handleGlory(itemId);
c.isOperate = true;
c.itemUsing = itemId;
break;

case 1710:
c.getPA().handleGlory(itemId);
c.itemUsing = itemId;
c.isOperate = true;
break;

case 1708:
c.getPA().handleGlory(itemId);
c.itemUsing = itemId;
c.isOperate = true;
break;

case 1706:
c.getPA().handleGlory(itemId);
c.itemUsing = itemId;
c.isOperate = true;
break;
			
		default:
			if (c.playerRights == 3)
				Misc.println(c.playerName+ " - Item3rdOption: "+itemId+" : "+itemId11+" : "+itemId1);
			break;
		}

	}

}
