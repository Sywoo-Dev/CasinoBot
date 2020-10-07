package fr.sywoo.casino.events.moderation;

import java.util.List;

import fr.sywoo.casino.utils.ChannelUtils;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ModerationCommand extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getMessage().isFromType(ChannelType.PRIVATE)) return;
		List<Role> roles = event.getMember().getRoles();
		if(roles.isEmpty()) return;
		//if(roles.contains(Main.jda.getRoleById(Long.getLong("606553834852057106"))) || roles.contains(Main.jda.getRoleById(Long.getLong("606552865137360928")))) {
			String cmd = event.getMessage().getContentDisplay();
			if(cmd.startsWith("$")) {
				if(cmd.contains("clear")) {
					new ChannelUtils().clear(event.getChannel());    
				}
			}
	//	}
	}
	
	
}
