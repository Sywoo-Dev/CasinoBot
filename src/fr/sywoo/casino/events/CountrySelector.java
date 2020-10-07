package fr.sywoo.casino.events;

import fr.sywoo.casino.enums.Languages;
import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.utils.DBUtils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CountrySelector extends ListenerAdapter{
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {

		MessageReaction reaction = event.getReaction();
		MessageChannel channel = event.getChannel();

		if(channel.getIdLong() != Long.valueOf("635299398795919380")) return;
		if(event.getUser().isBot()) return;

		if(reaction.getReactionEmote().getName().equalsIgnoreCase("🇫🇷")) {
			DBUtils.updateString("user", "lang", "uuid", event.getUser().getId(), "FR");
			CasinoUser.getUser(event.getUser()).setLang(Languages.FR);
		}else if(reaction.getReactionEmote().getName().equalsIgnoreCase("🇬🇧")) {
			DBUtils.updateString("user", "lang", "uuid", event.getUser().getId(), "EN");
			CasinoUser.getUser(event.getUser()).setLang(Languages.EN);
		}else {
			reaction.removeReaction(event.getUser()).complete();
		}
	}

}
