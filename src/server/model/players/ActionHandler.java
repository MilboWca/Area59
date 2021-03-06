package server.model.players;

import server.Config;
import server.Server;
import server.model.objects.Object;
import server.util.Misc;
import server.util.ScriptManager;
import server.model.npcs.NPC;
import server.model.players.Flax;
import server.content.skill.*;
import server.event.*;
import server.model.minigames.*;
import server.model.players.skills.*;
import server.model.players.packets.*;
import server.model.minigames.Sailing;

public class ActionHandler {
	
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}
	
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}	
		if (c.getAgility().agilityObstacle(c, objectType)) {
			c.getAgility().agilityCourse(c, objectType);
		}
		if(Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}		
		switch(objectType) {
			case 2491:
				Mining.mineEss(c, objectType);
				break;
		case 3044:
			c.getSmithing().sendSmelting();
			break;				
			case 2478: Runecrafting.craftRunesOnAltar(c, 1, 5, 556, 30, 45, 60); break;
			case 2480: Runecrafting.craftRunesOnAltar(c, 5, 6, 555, 30, 45, 60); break;
			case 2481: Runecrafting.craftRunesOnAltar(c, 9, 7, 557, 45, 55, 65); break;
			case 2482: Runecrafting.craftRunesOnAltar(c, 14, 7, 554, 50, 60, 70); break;
			case 2483: Runecrafting.craftRunesOnAltar(c, 20, 8, 559, 55, 65, 75); break;
			case 2487: Runecrafting.craftRunesOnAltar(c, 35, 9, 562, 60, 70, 80); break;
			case 2486: Runecrafting.craftRunesOnAltar(c, 44, 9, 561, 60, 74, 91); break;
			case 2485: Runecrafting.craftRunesOnAltar(c, 54, 10, 563, 65, 79, 93); break;
			case 2488: Runecrafting.craftRunesOnAltar(c, 65, 10, 560, 72, 84, 96); break;
			case 2490: Runecrafting.craftRunesOnAltar(c, 77, 11, 565, 89, 94, 99); break;		
		case 804:
			Tanning.sendTanningInterface(c);
			break;		
		/* AL KHARID */
		case 2883:
		case 2882:
			c.getDH().sendDialogues(1023, 925);
		break;
		case 2412:
			Sailing.startTravel(c, 1);
			break;
		case 2414:
			Sailing.startTravel(c, 2);
			break;
		case 2083:
			Sailing.startTravel(c, 5);
			break;
		case 2081:
			Sailing.startTravel(c, 6);
			break;
		case 14304:
			Sailing.startTravel(c, 14);
			break;
		case 14306:
			Sailing.startTravel(c, 15);
			break;
			
		case 2213:
		case 3045:
		case 14367:
		case 11758:
		case 3193:
		case 10517:
		case 11402:
		case 26972:
			c.getPA().openUpBank();
		break; 
 
 	/**
		 * Entering the Fight Caves.
		 */
		case 9356:
			c.getPA().enterCaves();
			c.sendMessage("Best of luck in the waves!");
			c.sendMessage("If something bugs, just relog!");
		break;
				
		
		/**
		 * Clicking on the Ancient Altar. 
		 */
		case 6552:
		if (c.playerMagicBook == 0) {
                        c.playerMagicBook = 1;
                        c.setSidebarInterface(6, 12855);
                        c.autocasting = false;
                        c.sendMessage("An ancient wisdomin fills your mind.");
                        c.getPA().resetAutocast();
		} else {
			c.setSidebarInterface(6, 1151); 
			c.playerMagicBook = 0;
                        c.autocasting = false;
			c.sendMessage("You feel a drain on your memory.");
			c.autocastId = -1;
			c.getPA().resetAutocast();
		}	
		break;
		
		
        /**
         * Recharing prayer points.
         */
		case 409:
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		
		
		
		
		/**
		 * Aquring god capes.
		 */
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			}	
		break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
		break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
		break;
		
		
		/**
		 * Oblisks in the wilderness.
		 */
		case 14829:
		case 14830:
		case 14827:
		case 14828:
		case 14826:
		case 14831:
			Server.objectManager.startObelisk(objectType);
		break;
		
		
		/**
		 * Clicking certain doors.
		 */
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
			
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
			
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
			
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
			
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
			
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
			
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
			
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
			
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
			
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break; 
			
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			break;
		
		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
		break;
		
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;
		
		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);				
		break;
		case 1558:
			if (c.absX == 3041 && c.absY == 10308) {
                            c.getPA().movePlayer(3040, 10308, 0);	
                        } else if(c.absX == 3040 && c.absY == 10308) {
                                  c.getPA().movePlayer(3041, 10308, 0);
                        } else if(c.absX == 3040 && c.absY == 10307) {
                                  c.getPA().movePlayer(3041, 10307, 0);
                        } else if(c.absX == 3041 && c.absY == 10307) {
                                  c.getPA().movePlayer(3040, 10307, 0);
                        } else if(c.absX == 3044 && c.absY == 10341) {
                                  c.getPA().movePlayer(3045, 10341, 0);
                        } else if(c.absX == 3045 && c.absY == 10341) {
                                  c.getPA().movePlayer(3044, 10341, 0);
                        } else if(c.absX == 3044 && c.absY == 10342) {
                                  c.getPA().movePlayer(3045, 10342, 0);
                        } else if(c.absX == 3045 && c.absY == 10342) {
                                  c.getPA().movePlayer(3044, 10343, 0);
                        }
		break;
		case 1557:
			if (c.absX == 3023 && c.absY == 10312) {
                            c.getPA().movePlayer(3022, 10312, 0);	
                        } else if(c.absX == 3022 && c.absY == 10312) {
                                  c.getPA().movePlayer(3023, 10312, 0);
                        } else if(c.absX == 3023 && c.absY == 10311) {
                                  c.getPA().movePlayer(3022, 10311, 0);
                        } else if(c.absX == 3022 && c.absY == 10311) {
                                  c.getPA().movePlayer(3023, 10311, 0);
                        }
		break;
		case 2558:
			c.sendMessage("This door is locked.");	
		break;
		
		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
		break;
		
		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
		break;
		
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
	
		case 733:
			c.startAnimation(451);
		
			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10, 733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10, 733, 50);
			}
		break;
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;
			
		
		/**
		 * Forfeiting a duel.
		 */
		case 3203: 
			//if(c.playerRights >= 0) {
				c.sendMessage("Forfeiting has been disabled.");
				//break;
			/*}	
			if (c.duelCount > 0) {
				c.sendMessage("You may not forfeit yet.");
				break;
			}
			Client o = (Client) Server.playerHandler.players[c.duelingWith];				
			if(o == null) {
				//c.getTradeAndDuel().resetDuel();
				c.sendMessage("Forfeiting the duel has been disabled!");
				//c.getPA().movePlayer(Settings.DUELING_RESPAWN_X+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), Settings.DUELING_RESPAWN_Y+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), 0);
				break;
				
			}
			if(c.duelRule[0]) {
				c.sendMessage("Forfeiting the duel has been disabled!");
				break;
			}
			if(o != null) {
				/*o.getPA().movePlayer(Settings.DUELING_RESPAWN_X+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), Settings.DUELING_RESPAWN_Y+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), 0);
				c.getPA().movePlayer(Settings.DUELING_RESPAWN_X+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), Settings.DUELING_RESPAWN_Y+(Misc.random(Settings.RANDOM_DUELING_RESPAWN)), 0);
				o.duelStatus = 6;
				o.getTradeAndDuel().duelVictory();
				c.getTradeAndDuel().resetDuel();
				c.getTradeAndDuel().resetDuelItems();
				o.sendMessage("The other player has forfeited the duel!");
				c.sendMessage("You forfeit the duel!");*/
				//break;
			//}
			break;
		
		
		/**
		 * Barrows brothers.
		 */
		case 6772:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6707: // Verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;
			
		case 6823:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // Torag
			c.getPA().movePlayer(3553, 3283, 0);
			break;
						
		case 6705: // Karil
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6704: // Guthan
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6703: // Dharok
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6702: // Ahrim
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
		
		/**
		 * Clicking the barrows chest.
		 */
		case 10284:
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;
		

		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		switch(objectType) {
		case 2781:
		case 11666:
		case 3044:
			c.getSmithing().sendSmelting();
			break;		
			case 2565:
				Thieving.stealFromStall(c, 995, 50000, 54, 50, objectType,obX,obY,2);
				break;
			case 2564:
				Thieving.stealFromStall(c, 995, 65000, 81, 65, objectType,obX,obY,0);
				break;
			case 2563:
				Thieving.stealFromStall(c, 995, 36000, 35, 36, objectType,obX,obY,0);
				break;
			case 2562:
				Thieving.stealFromStall(c, 995, 75000, 16, 75, objectType,obX,obY,3);
				break;
			case 2561:
				Thieving.stealFromStall(c, 995, 5000, 16, 5, objectType,obX,obY, obX == 2667 ? 3 : 1);
				break;
			case 2560:
				Thieving.stealFromStall(c, 995, 20000, 24, 20, objectType,obX,obY, obX == 2662 ? 2 : 1);
				break;

			case 14011:
				Thieving.stealFromStall(c, 995, 1000, 10, 1, objectType,obX,obY, 3);
				break;
			case 7053:
				Thieving.stealFromStall(c, 995, 10000, 18, 10, objectType,obX,obY, obX == 3079 ? 2 : 1);
				break;		
			case 2646:
				Flax.pickFlax(c, obX, obY);
			break;
			
			
		/**
		 * Opening the bank.
		 */
		case 2213:
		case 14367:
		case 11758:
		case 10517:
		case 26972:
			c.getPA().openUpBank();
		break;
			
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if(Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 1, npcType);
			return;
		}		
		switch (npcType) {
	
        /**
         * Shops.
         */
		case 953:	//Banker
		case 2574:	//Banker
		case 166:	//Gnome Banker
		case 1702:	//Ghost Banker
		case 494:	//Banker
		case 495:	//Banker
		case 496:	//Banker
		case 497:	//Banker
		case 498:	//Banker
		case 499:	//Banker
		case 567: 	//Banker
		case 1036:	//Banker
		case 1360:	//Banker
		case 2163:	//Banker
		case 2164:	//Banker
		case 2354:	//Banker
		case 2355:	//Banker
		case 2568:	//Banker
		case 2569:	//Banker
		case 2570:	//Banker
			//FreeDialogues.handledialogue(c, 1013, npcType);
		//break;
c.getDH().sendDialogues(1013, c.npcType);
break;
case 588:
			c.getShops().openShop(2);
			break;

			case 550:
			c.getShops().openShop(3);
			break;

			case 575:
			c.getShops().openShop(4);
			break;

			case 2356:
			c.getShops().openShop(5);
			break;

			case 3796:
			c.getShops().openShop(6);
			break;

			case 1860:
			c.getShops().openShop(7);
			break;

			case 519:
			c.getShops().openShop(8); //should sell barrows or something like that
			break;

			case 559:
			c.getShops().openShop(9);
			break;

			case 562:
			c.getShops().openShop(10);
			break;

			case 581:
			c.getShops().openShop(11);
			break;

			case 548:
			c.getShops().openShop(12);
			break;

			case 554:
			c.getShops().openShop(13);
			break;

			case 601:
			c.getShops().openShop(14);
			break;

			case 1301:
			c.getShops().openShop(15);
			break;

			case 1039:
			c.getShops().openShop(16);
			break;

			case 2353:
			c.getShops().openShop(17);
			break;

			case 3166:
			c.getShops().openShop(18);
			break;

			case 2161:
			c.getShops().openShop(19);
			break;

			case 2162:
			c.getShops().openShop(20);
			break;

			case 600:
			c.getShops().openShop(21);
			break;

			case 603:
			c.getShops().openShop(22);
			break;

			case 593:
			c.getShops().openShop(23);
			break;

			case 545:
			c.getShops().openShop(24);
			break;

			case 585:
			c.getShops().openShop(25);
			break;

			case 2305:
			c.getShops().openShop(26);
			break;

			case 2307:
			c.getShops().openShop(27);
			break;

			case 2304:
			c.getShops().openShop(28);
			break;

			case 2306:
			c.getShops().openShop(29);
			break;

			case 517:
			c.getShops().openShop(30);
			break;

			case 558:
			c.getShops().openShop(31);
			break;

			case 576:
			c.getShops().openShop(32);
			break;

			case 1369:
			c.getShops().openShop(33);
			break;

			case 557:
			c.getShops().openShop(34);
			break;

			case 1038:
			c.getShops().openShop(35);
			break;

			case 1433:
			c.getShops().openShop(36);
			break;

			case 584:
			c.getShops().openShop(37);
			break;

			case 540:
			c.getShops().openShop(38);
			break;

			case 2157:
			c.getShops().openShop(39);
			break;

			case 538:
			c.getShops().openShop(40);
			break;

			case 1303:
			c.getShops().openShop(41);
			break;

			case 578:
			c.getShops().openShop(42);
			break;

			case 587:
			c.getShops().openShop(43);
			break;

			case 1398:
			c.getShops().openShop(44);
			break;

			case 556:
			c.getShops().openShop(45);
			break;

			case 1865:
			c.getShops().openShop(46);
			break;

			case 543:
			c.getShops().openShop(47);
			break;

			case 2198:
			c.getShops().openShop(48);
			break;

			case 580:
			c.getShops().openShop(49);
			break;

			case 1862:
			c.getShops().openShop(50);
			break;

			case 583:
			c.getShops().openShop(51);
			break;

			case 553:
			c.getShops().openShop(52);
			break;
                              
                        case 461:
			c.getShops().openShop(53);
			break;


			case 903:
			c.getShops().openShop(54);
			break;

			case 2258:
			c.getPA().startTeleport(3039, 4834, 0, "modern"); //first click teleports second click open shops
			break;

			case 1435:
			c.getShops().openShop(56);
			break;

			case 3800:
			c.getShops().openShop(57);
			break;

			case 2623:
			c.getShops().openShop(58);
			break;

			case 594:
			c.getShops().openShop(59);
			break;

			case 579:
			c.getShops().openShop(60);
			break;

			case 2160:
			case 2191:
			c.getShops().openShop(61);
			break;

			case 589:
			c.getShops().openShop(62);
			break;

			case 549:
			c.getShops().openShop(63);
			break;

			case 542:
			c.getShops().openShop(64);
			break;

			case 3038:
			c.getShops().openShop(65);
			break;

			case 544:
			c.getShops().openShop(66);
			break;

			case 541:
			c.getShops().openShop(67);
			break;

			case 1434:
			c.getShops().openShop(68);
			break;

			case 577:
			c.getShops().openShop(69);
			break;

			case 539:
			c.getShops().openShop(70);
			break;

			case 1980:
			c.getShops().openShop(71);
			break;

			case 546:
			c.getShops().openShop(72);
			break;

			case 382:
			c.getShops().openShop(73);
			break;

			case 3541:
			c.getShops().openShop(74);
			break;

			case 520:
			c.getShops().openShop(75);
			break;

			case 1436:
			c.getShops().openShop(76);
			break;

			case 590:
			c.getShops().openShop(77);
			break;

			case 971:
			c.getShops().openShop(78);
			break;

			case 1917:
			c.getShops().openShop(79);
			break;

			case 1040:
			c.getShops().openShop(80);
			break;

			case 563:
			c.getShops().openShop(81);
			break;

			case 522:
			c.getShops().openShop(82);
			break;

			case 524:
			c.getShops().openShop(83);
			break;

			case 526:
			c.getShops().openShop(84);
			break;

			case 2154:
			c.getShops().openShop(85);
			break;

			case 1334:
			c.getShops().openShop(86);
			break;

			case 2552:
			c.getShops().openShop(87);
			break;

			case 528:
			c.getShops().openShop(88);
			break;

			case 1254:
			c.getShops().openShop(89);
			break;

			case 2086:
			c.getShops().openShop(90);
			break;

			case 3824:
			c.getShops().openShop(91);
			break;

			case 1866:
			c.getShops().openShop(92);
			break;

			case 1699:
			c.getShops().openShop(93);
			break;

			case 1282:
			c.getShops().openShop(94);
			break;

			case 530:
			c.getShops().openShop(95);
			break;

			case 516:
			c.getShops().openShop(96);
			break;

			case 560:
			c.getShops().openShop(97);
			break;

			case 471:
			c.getShops().openShop(98);
			break;

			case 1208:
			c.getShops().openShop(99);
			break;

			case 532:
			c.getShops().openShop(100);
			break;

			case 606:
			c.getShops().openShop(101);
			break;

			case 534:
			c.getShops().openShop(102);
			break;

			case 836:
			c.getShops().openShop(103);
			break;

			case 551:
			c.getShops().openShop(104);
			break;

			case 586:
			c.getShops().openShop(105);
			break;

			case 564:
			c.getShops().openShop(106);
			break;

			case 573:
			case 1316:
			case 547:
			c.getShops().openShop(108);
			break;

			case 1787:
			c.getShops().openShop(110);
			break;		 
		case 2566:
			c.getShops().openSkillCape();
		break;
		
		
		/**
		 * Make over mage.
		 */
		
		}
	}

	public void secondClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if(Thieving.pickpocketNPC(c, npcType)) {
			Thieving.attemptToPickpocket(c, npcType);
			return;
		}		
		if(Fishing.fishingNPC(c, npcType)) {
			Fishing.fishingNPC(c, 2, npcType);
			return;
		}		
		switch(npcType) {
		case 953:	//Banker
		case 2574:	//Banker
		case 166:	//Gnome Banker
		case 1702:	//Ghost Banker
		case 494:	//Banker
		case 495:	//Banker
		case 496:	//Banker
		case 497:	//Banker
		case 498:	//Banker
		case 499:	//Banker
		case 567: 	//Banker
		case 1036:	//Banker
		case 1360:	//Banker
		case 2163:	//Banker
		case 2164:	//Banker
		case 2354:	//Banker
		case 2355:	//Banker
		case 2568:	//Banker
		case 2569:	//Banker
		case 2570:	//Banker
		c.getPA().openUpBank();
		break;		
case 588:
			c.getShops().openShop(2);
			break;

			case 550:
			c.getShops().openShop(3);
			break;

			case 575:
			c.getShops().openShop(4);
			break;

			case 2356:
			c.getShops().openShop(5);
			break;

			case 3796:
			c.getShops().openShop(6);
			break;

			case 1860:
			c.getShops().openShop(7);
			break;

			case 519:
			c.getShops().openShop(8); //should sell barrows or something like that
			break;

			case 559:
			c.getShops().openShop(9);
			break;

			case 562:
			c.getShops().openShop(10);
			break;

			case 581:
			c.getShops().openShop(11);
			break;

			case 548:
			c.getShops().openShop(12);
			break;

			case 554:
			c.getShops().openShop(13);
			break;

			case 601:
			c.getShops().openShop(14);
			break;

			case 1301:
			c.getShops().openShop(15);
			break;

			case 1039:
			c.getShops().openShop(16);
			break;

			case 2353:
			c.getShops().openShop(17);
			break;

			case 3166:
			c.getShops().openShop(18);
			break;

			case 2161:
			c.getShops().openShop(19);
			break;

			case 2162:
			c.getShops().openShop(20);
			break;

			case 600:
			c.getShops().openShop(21);
			break;

			case 603:
			c.getShops().openShop(22);
			break;

			case 593:
			c.getShops().openShop(23);
			break;

			case 545:
			c.getShops().openShop(24);
			break;

			case 585:
			c.getShops().openShop(25);
			break;

			case 2305:
			c.getShops().openShop(26);
			break;

			case 2307:
			c.getShops().openShop(27);
			break;

			case 2304:
			c.getShops().openShop(28);
			break;

			case 2306:
			c.getShops().openShop(29);
			break;

			case 517:
			c.getShops().openShop(30);
			break;

			case 558:
			c.getShops().openShop(31);
			break;

			case 576:
			c.getShops().openShop(32);
			break;

			case 1369:
			c.getShops().openShop(33);
			break;

			case 557:
			c.getShops().openShop(34);
			break;

			case 1038:
			c.getShops().openShop(35);
			break;

			case 1433:
			c.getShops().openShop(36);
			break;

			case 584:
			c.getShops().openShop(37);
			break;

			case 540:
			c.getShops().openShop(38);
			break;

			case 2157:
			c.getShops().openShop(39);
			break;

			case 538:
			c.getShops().openShop(40);
			break;

			case 1303:
			c.getShops().openShop(41);
			break;

			case 578:
			c.getShops().openShop(42);
			break;

			case 587:
			c.getShops().openShop(43);
			break;

			case 1398:
			c.getShops().openShop(44);
			break;

			case 556:
			c.getShops().openShop(45);
			break;

			case 1865:
			c.getShops().openShop(46);
			break;

			case 543:
			c.getShops().openShop(47);
			break;

			case 2198:
			c.getShops().openShop(48);
			break;

			case 580:
			c.getShops().openShop(49);
			break;

			case 1862:
			c.getShops().openShop(50);
			break;

			case 583:
			c.getShops().openShop(51);
			break;

			case 553:
			c.getShops().openShop(52);
			break;
                              
                        case 461:
			c.getShops().openShop(53);
			break;


			case 903:
			c.getShops().openShop(54);
			break;

			case 2258:
			c.getPA().startTeleport(3039, 4834, 0, "modern"); //first click teleports second click open shops
			break;

			case 1435:
			c.getShops().openShop(56);
			break;

			case 3800:
			c.getShops().openShop(57);
			break;

			case 2623:
			c.getShops().openShop(58);
			break;

			case 594:
			c.getShops().openShop(59);
			break;

			case 579:
			c.getShops().openShop(60);
			break;

			case 2160:
			case 2191:
			c.getShops().openShop(61);
			break;

			case 589:
			c.getShops().openShop(62);
			break;

			case 549:
			c.getShops().openShop(63);
			break;

			case 542:
			c.getShops().openShop(64);
			break;

			case 3038:
			c.getShops().openShop(65);
			break;

			case 544:
			c.getShops().openShop(66);
			break;

			case 541:
			c.getShops().openShop(67);
			break;

			case 1434:
			c.getShops().openShop(68);
			break;

			case 577:
			c.getShops().openShop(69);
			break;

			case 539:
			c.getShops().openShop(70);
			break;

			case 1980:
			c.getShops().openShop(71);
			break;

			case 546:
			c.getShops().openShop(72);
			break;

			case 382:
			c.getShops().openShop(73);
			break;

			case 3541:
			c.getShops().openShop(74);
			break;

			case 520:
			c.getShops().openShop(75);
			break;

			case 1436:
			c.getShops().openShop(76);
			break;

			case 590:
			c.getShops().openShop(77);
			break;

			case 971:
			c.getShops().openShop(78);
			break;

			case 1917:
			c.getShops().openShop(79);
			break;

			case 1040:
			c.getShops().openShop(80);
			break;

			case 563:
			c.getShops().openShop(81);
			break;

			case 522:
			c.getShops().openShop(82);
			break;

			case 524:
			c.getShops().openShop(83);
			break;

			case 526:
			c.getShops().openShop(84);
			break;

			case 2154:
			c.getShops().openShop(85);
			break;

			case 1334:
			c.getShops().openShop(86);
			break;

			case 2552:
			c.getShops().openShop(87);
			break;

			case 528:
			c.getShops().openShop(88);
			break;

			case 1254:
			c.getShops().openShop(89);
			break;

			case 2086:
			c.getShops().openShop(90);
			break;

			case 3824:
			c.getShops().openShop(91);
			break;

			case 1866:
			c.getShops().openShop(92);
			break;

			case 1699:
			c.getShops().openShop(93);
			break;

			case 1282:
			c.getShops().openShop(94);
			break;

			case 530:
			c.getShops().openShop(95);
			break;

			case 516:
			c.getShops().openShop(96);
			break;

			case 560:
			c.getShops().openShop(97);
			break;

			case 471:
			c.getShops().openShop(98);
			break;

			case 1208:
			c.getShops().openShop(99);
			break;

			case 532:
			c.getShops().openShop(100);
			break;

			case 606:
			c.getShops().openShop(101);
			break;

			case 534:
			c.getShops().openShop(102);
			break;

			case 836:
			c.getShops().openShop(103);
			break;

			case 551:
			c.getShops().openShop(104);
			break;

			case 586:
			c.getShops().openShop(105);
			break;

			case 564:
			c.getShops().openShop(106);
			break;

			case 573:
			case 1316:
			case 547:
			c.getShops().openShop(108);
			break;

			case 1787:
			c.getShops().openShop(110);
			break;		
		
			
		}
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {
		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
		c.getShops().openShop(109);
		break;
		case 548:
		if (!c.ardiRizal()) {
			c.sendMessage("You must remove your equipment before changing your appearence.");
			c.canChangeAppearance = false;
		} else {		
			c.getPA().showInterface(3559);
			c.canChangeAppearance = true;	
		}
		break;		

case 836:
		c.getShops().openShop(103);
		break;
		

		}
	}
	

}