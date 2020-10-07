package fr.sywoo.casino.events;

import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BotConnection extends ListenerAdapter{

	@Override
	public void onShutdown(ShutdownEvent event) {
		System.out.println("Disconnected");
	}

}
