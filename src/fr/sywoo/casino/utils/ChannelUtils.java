package fr.sywoo.casino.utils;

import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

public class ChannelUtils {

	public void clear(MessageChannel channel) {
		try {
			int x = 0;
			for(Message msgs : channel.getIterableHistory()) {
					msgs.delete().complete();
					x++;
			}
			Message msg = channel.sendMessage(":x: Supression de " + x + " messages effectués !").complete();
			msg.delete().completeAfter(5, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

	public void clear(MessageChannel channel, String contain_key) {
		try {
			int x = 0;
			for(Message msgs : channel.getIterableHistory()) {
				if(!msgs.getContentDisplay().contains(contain_key)) {
					msgs.delete().complete();
					x++;
				}
			}
			
			Message msg = channel.sendMessage(":x: Supression de " + x + " messages effectués !").complete();
			msg.delete().completeAfter(5, TimeUnit.SECONDS);
			
		} catch (Exception e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}

}
