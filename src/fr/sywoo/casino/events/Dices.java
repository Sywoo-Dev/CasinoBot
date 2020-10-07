package fr.sywoo.casino.events;

import java.awt.Color;

import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.utils.MathsUtils;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Dices extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getIdLong() != Long.valueOf("606554469660098563")) {
			return;
		}

		String command = event.getMessage().getContentDisplay();
		MessageChannel channel = event.getChannel();
				
		if(command.startsWith("-")) {

			String[] args = command.split(" ");
			
			if(args[0].contains("dice")) {
				if(args[1].equalsIgnoreCase("help")) {
					new MessageUtils().sendPrivateMessage(event.getAuthor(), "Les règles du dice : \n -dice <un chiffre entre 2 et 12> <somme misé> \n C'est gagné ? Vous remporter votre mise x3 !");
					return;
				}
				
				if(args.length < 3) {
					channel.sendMessage(":x: Commande mal executé !").complete();
					return;
				}

				if(!new MathsUtils().isInteger(args[1])) {
					channel.sendMessage(":x: Vous devez choisir un chiffre entre 2 et 12.").complete();
					return;
				}

				if(!new MathsUtils().isInrange(Double.valueOf(args[1]), 1, 13)) {
					channel.sendMessage(":x: Vous devez choisir un chiffre entre 2 et 12 !").complete();
					return;
				}

				if(new MathsUtils().isInteger(args[2])) {

					int choice = Integer.valueOf(args[1]);
					int parie = Integer.valueOf(args[2]);
					
					CasinoUser cu = CasinoUser.getUser(event.getAuthor());
					cu.removeCoins(parie);

					int random_1 = (int) (Math.random() * 6);
					int random_2 = (int) (Math.random() * 6);

					if(random_1 == 0) {
						random_1 = 1;
					}

					if(random_2 == 0) {
						random_2 = 1;
					}

					int total = random_1 + random_2;
					String victory = "";
					if(choice == total) {
						victory = ":moneybag: Vous remportez " + parie * 3 + " Points";
						cu.addCoins(parie*3);
					}else {
						victory = ":money_with_wings: Vous perdez " + parie + " Points";
					}

					EmbedBuilder embed = new EmbedBuilder();
					embed.setAuthor(event.getAuthor().getName());
					embed.addField(new Field("Résultat:", victory, true));
					embed.setColor(Color.YELLOW);
					embed.setDescription(":game_die: Dé n°1 : " + random_1 + "\n" +
							":game_die: Dé n°2 : " + random_2 + "\n" + 
							":checkered_flag: Sois: " + total);

					channel.sendMessage(embed.build()).complete();



				}else {
					channel.sendMessage(":x: La mise doit �tre un chiffre rond !").complete();
				}
				return;
			}
		}
	}

}
