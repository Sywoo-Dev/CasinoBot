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

public class Roll extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getIdLong() != Long.valueOf("606554519060480010")) {
			return;
		}

		String command = event.getMessage().getContentDisplay();
		MessageChannel channel = event.getChannel();
		if(command.startsWith("-")) {

			String[] args = command.split(" ");
			
			if(args.length < 2) {
				channel.sendMessage(":x: Commande mal execut� !").complete();
				return;
			}
			
			if(args[0].contains("roll")) {
				if(args[1].equalsIgnoreCase("help")) {
					new MessageUtils().sendPrivateMessage(event.getAuthor(), "Les r�gles du roll : \n -roll <somme mis�> \n 3 emoticons identiques ? C'est gagn� !"
							+ " \n :bomb: Mise x2 \n :lemon: Mise x5 \n :seven: Mise x10");
					return;
				}
				if(new MathsUtils().isInteger(args[1])) {
					
					int mise = Integer.valueOf(args[1]);
					
					int random_1 = (int) (Math.random()*3);
					int random_2 = (int) (Math.random()*3);
					int random_3 = (int) (Math.random()*3);
					
					CasinoUser cu = CasinoUser.getUser(event.getAuthor());
					cu.removeCoins(mise);
					String victory = "";
					
					if(random_1 == random_2 && random_2 == random_3) {
						int multiple = 0;
						if(random_1 == 0) {
							multiple = 2;
						}
						if(random_1 == 1) {
							multiple = 5;
						}
						if(random_1 == 2) {
							multiple = 10;
						}
						victory = ":moneybag: Vous avez remporter" + mise * multiple + " Points";
						cu.addCoins(mise*multiple);
					}else {
						victory = ":money_with_wings: Vous perdez " + mise + " Points";
					}
					
					String[] list = {":bomb: ", ":lemon: ", ":seven: "};
					
					EmbedBuilder embed = new EmbedBuilder();
					embed.setAuthor(event.getAuthor().getName());
					embed.addField(new Field("R�sultat:", victory, true));
					embed.setColor(Color.YELLOW);
					embed.setDescription(list[random_1] + " " + list[random_2] + " " + list[random_3]);

					channel.sendMessage(embed.build()).complete();
					
					
				}else {
					channel.sendMessage(":x: La mise doit �tre un chiffre rond !").complete();
				}
				return;
			}
		}
	}

}
