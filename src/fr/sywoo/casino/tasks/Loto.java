package fr.sywoo.casino.tasks;

import java.util.TimerTask;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.objs.CasinoUser;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class Loto extends TimerTask {

	@Override
	public void run() {

		MessageChannel channel = Main.jda.getTextChannelById(Long.valueOf("636743342943633418"));

		if(!Main.launched) {
			Message msg = channel.sendMessage(Main.jda.getRoleById(Long.valueOf("636762825368535058")).getAsMention() + "\nLoto: Tenter votre chance !\nPrenez votre ticket en réagissant avec l'icon :tickets: \n"
					+ "Un gagnant assuré par tirage toutes les heures \nPrix 10 points Cagnotte 100 points").complete();
			msg.addReaction("🎟").complete();
			Main.launched = true;

			if(Main.clone_invites.isEmpty()) {
				for(Invite i : Main.jda.getGuildById(Long.valueOf("606552405827649547")).getInvites().complete()){
					Main.clone_invites.add(i);
				}
			}


			return;
		}

		if(Main.loto_participation.isEmpty()) return;

		for(User users : Main.loto_participation) {
			if(users.isFake() && users.isBot()) {
				Main.loto_participation.remove(users);
			}
		}

		int random = (int) (Math.random() * Main.loto_participation.size());
		User user = Main.loto_participation.get(random);

		
		CasinoUser.getUser(user).addCoins(100);
		Message to_delete = channel.getMessageById(channel.getLatestMessageIdLong()).complete();
		to_delete.delete().complete();

		channel.sendMessage("Félicitation à " + user.getAsMention() + " Remporte la lotterie ! Nous vous invitons à retenter votre chance sur le prochain tirage !").complete();

		Message msg = channel.sendMessage(Main.jda.getRoleById(Long.valueOf("636762825368535058")).getAsMention() + "\nLoto: Tenter votre chance !\nPrenez votre ticket en réagissant avec l'icon :tickets: \n"
				+ "Un gagnant assuré par tirage toutes les heures \nPrix 10 points Cagnotte 100 points").complete();
		msg.addReaction("🎟").complete();
		Main.loto_participation.clear();

	}

}
