package fr.sywoo.casino.events;

import fr.sywoo.casino.Main;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BoostManager extends ListenerAdapter{
	
	@Override
	public void onGuildMemberRoleAdd(GuildMemberRoleAddEvent event) {
		//Id of Nitro = 630119034368557069
		System.out.println(event.getRoles().get(0).getIdLong());
		if(event.getRoles().get(0).getIdLong() == Long.getLong("630119034368557069")) {
			MessageChannel channel = Main.jda.getTextChannelById(Long.valueOf("633365709820526638"));
			channel.sendMessage(event.getUser().getAsMention() + " Soutient le serveur !\n Il reçois donc 200 Coins").complete();
		}
	}

}
