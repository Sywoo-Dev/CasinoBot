package fr.sywoo.casino.events;

import java.util.ArrayList;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.enums.Languages;
import fr.sywoo.casino.enums.Messages;
import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.objs.CoinsFlipObject;
import fr.sywoo.casino.utils.MessageDisplayer;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CoinsFlipRaction extends ListenerAdapter{  
	
	@Override
	public void onMessageReactionAdd(MessageReactionAddEvent event) {
		
        MessageReaction reaction = event.getReaction();
        //ReactionEmote emote = reaction.getReactionEmote();
        MessageChannel channel = event.getChannel();
        
		System.out.println("Reaction detected");
		
        if(channel.getIdLong() != Long.valueOf("612753361053155348")) return;
        if(event.getUser().isBot()) return;
                
        if(CoinsFlipObject.getCF(event.getMessageIdLong()) == null) {
        	event.getChannel().deleteMessageById(event.getMessageId()).complete();
        	return;
        }
        
        CasinoUser cu = CasinoUser.getUser(event.getUser());
        
        if(cu == null) {
        	return;
        }
        
        CoinsFlipObject cf = CoinsFlipObject.getCF(event.getMessageIdLong());
        
        
        Message message = channel.getMessageById(reaction.getMessageId()).complete();
        message.delete().complete();
        
        User owner = Main.jda.getUserById(cf.getOwner());
        User challenger = event.getUser();
        
        ArrayList<User> users = new ArrayList<>();
        
        for(int i = 0; i < 20; i++) {
        	users.add(challenger);
        	users.add(owner);
        }
        
        int random = (int) (Math.random() * users.size());
        
        User winner = users.get(random);
        User lost = null;
        
        if(winner.getIdLong() == owner.getIdLong()) {
        	lost = challenger;
        }else {
        	lost = owner;
        }
        if(CasinoUser.getUser(winner).getLang() == Languages.FR) {
            channel.sendMessage(winner.getAsMention() + " remporte " + cf.getMise() + " <:1000:612744834603548705> contre " + lost.getAsMention()).complete();
        }else {
            channel.sendMessage(winner.getAsMention() + " won " + cf.getMise() + " <:1000:612744834603548705> against " + lost.getAsMention()).complete();
        }
        
        CasinoUser.getUser(winner).addCoins(cf.getMise());
        CasinoUser.getUser(lost).removeCoins(cf.getMise());
        
        new MessageUtils().sendPrivateMessage(winner, MessageDisplayer.Display(CasinoUser.getUser(winner).getLang(), Messages.MONEY_MOVEMENT_WON, cf.getMise()));
        new MessageUtils().sendPrivateMessage(lost, MessageDisplayer.Display(CasinoUser.getUser(lost).getLang(), Messages.MONEY_MOVEMENT_LOSE, cf.getMise()));
        
        
	}

}
