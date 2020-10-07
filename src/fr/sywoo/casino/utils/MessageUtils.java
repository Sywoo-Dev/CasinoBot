package fr.sywoo.casino.utils;

import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.User;

public class MessageUtils {

	public void sendPrivateMessage(User user, String message) {
		if(user.isBot()) return;
		user.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(message).queue();
        });
	}

	public void sendPrivateMessage(User author, MessageEmbed build) {
		if(author.isBot()) return;
		author.openPrivateChannel().queue((channel) ->
        {
            channel.sendMessage(build).queue();
        });
	}
	
}
