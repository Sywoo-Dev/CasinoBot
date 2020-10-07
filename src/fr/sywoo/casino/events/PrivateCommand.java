package fr.sywoo.casino.events;

import java.awt.Color;
import java.util.Map.Entry;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.enums.Messages;
import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.objs.CoinsFlipObject;
import fr.sywoo.casino.utils.ChannelUtils;
import fr.sywoo.casino.utils.MathsUtils;
import fr.sywoo.casino.utils.MessageDisplayer;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed.Field;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateCommand extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(!event.isFromType(ChannelType.PRIVATE)) {
			return;
		}
		
		CasinoUser cu = CasinoUser.getUser(event.getAuthor());
		
		String command = event.getMessage().getContentDisplay();
		String[] args = command.split(" ");
		
		if(command.startsWith("-")) {
			
			if(args[0].contains("cf")) {
				if(args.length < 3) {
					new MessageUtils().sendPrivateMessage(event.getAuthor(), MessageDisplayer.Display(cu.getLang(), Messages.ERROR_COINSFLIP, null));
					return;
				}
				
				if(!new MathsUtils().isInteger(args[1])) {
					new MessageUtils().sendPrivateMessage(event.getAuthor(), MessageDisplayer.Display(cu.getLang(), Messages.ERROR_COINSFLIP, null));
					return;
				}
				
				int somme = Integer.valueOf(args[1]);
				
				String c = "Pile | Heads";

				if(args[2].contains("face") || args[2].contains("tails")) {
					c = "Face | Tails";
				}
								
				MessageChannel french_channel = Main.jda.getTextChannelById(Long.valueOf("612753361053155348"));
				
				EmbedBuilder embed = new EmbedBuilder();
				embed.setAuthor(event.getAuthor().getName());
				embed.addField(new Field("Choix | Choice", c, true));
				embed.setColor(Color.BLUE);
				
				embed.setDescription("Mise | Amount of Money: " + somme);
				
				Message msg = french_channel.sendMessage(embed.build()).complete();
				msg.addReaction("✅").complete();
								
				new CoinsFlipObject(msg.getIdLong(), event.getAuthor().getIdLong(), somme);
				return;
			}
			
			if(args[0].contains("shutdown")) {
				User user = event.getAuthor();
				if(user.getId().equals("247027029654896640") || user.getId().equals("439435331515842570")) {
					new MessageUtils().sendPrivateMessage(user, "D'accord, je sauvegarde mes données et je m'éteint !");
					for(Entry<User, CasinoUser> users : CasinoUser.users.entrySet()) {
						users.getValue().save();
					}
					new MessageUtils().sendPrivateMessage(user, "Tout c'est bien passé, à bientôt Développeur !");
					MessageChannel channel = Main.jda.getTextChannelById(Long.valueOf("612753361053155348"));
					new ChannelUtils().clear(channel, "remporte");
				}
			}

			
		}else {
			new MessageUtils().sendPrivateMessage(event.getAuthor(), "Tu peut faire -help si tu veux connaître mes commandes privées !");
		}
		
	}


}
