package fr.sywoo.casino.events;

import java.util.ArrayList;

import fr.sywoo.casino.objs.CasinoUser;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Twice extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getIdLong() != Long.valueOf("613345728852525121")) {
			return;
		}

		String command = event.getMessage().getContentDisplay();
		MessageChannel channel = event.getChannel();
		if(command.startsWith("-")) {
			if(command.contains("-qod")) {
				CasinoUser cu = CasinoUser.getUser(event.getAuthor());
				int somme = cu.getCoins();
				
				ArrayList<Integer> variables = new ArrayList<>();
				
				for(int i = 0; i < 20; i++) {
					variables.add(0);
					variables.add(1);
				}
								
				int rdm = (int) (Math.random() * variables.size());
				event.getMessage().delete().complete();
				if(variables.get(rdm) == 1) {
					channel.sendMessage(event.getAuthor().getAsMention() + " Remporte " + (somme*2) + " <:1000:612744834603548705>").complete();
					cu.setCoins(somme*2);
				}else {
					channel.sendMessage(event.getAuthor().getAsMention() + " perds " + somme + " <:1000:612744834603548705>").complete();
					cu.setCoins(0);
				}
			}

		}

	}

}
