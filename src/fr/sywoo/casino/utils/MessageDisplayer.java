package fr.sywoo.casino.utils;

import javax.annotation.Nullable;

import fr.sywoo.casino.enums.Languages;
import fr.sywoo.casino.enums.Messages;

public class MessageDisplayer {
	
	public static String Display(Languages lang, Messages msg, @Nullable Object arg) {
		String m = "";
		switch (msg) {
		case HELP:
			if(lang == Languages.FR) {
				m = "======== Commandes =========\n-cf <somme> <pile/face>\n";
			}
			if(lang == Languages.EN) {
				m = "======== Commands =========\n-cf <amount of money> <heads/tails>\n";
			}
			break;
		case ERROR_COINSFLIP:
			if(lang == Languages.FR) {
				m = "-cf <somme> <pile/face>";
			}
			if(lang == Languages.EN) {
				m = "-cf <amount of money> <heads/tails>";
			}
			break;
		case MONEY_MOVEMENT_WON:
			if(lang == Languages.FR) {
				m = "Vous avez remporté " + arg + " Coins";
			}
			if(lang == Languages.EN) {
				m = "You have won " + arg + " Coins";
			}
			break;
		case MONEY_MOVEMENT_LOSE:
			if(lang == Languages.FR) {
				m = "Vous avez perdu " + arg + " Coins";
			}
			if(lang == Languages.EN) {
				m = "You have lost " + arg + " Coins";
			}
			break;
		default:
			break;
		}
		return m;
	}

}
