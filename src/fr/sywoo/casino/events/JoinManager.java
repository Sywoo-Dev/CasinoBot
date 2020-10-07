package fr.sywoo.casino.events;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.utils.BCrypt;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinManager extends ListenerAdapter{

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
		
		MessageChannel channel = event.getJDA().getTextChannelById("606553120280936477");
		String password = "Vous avez déjà un compte chez nous !";
		try {
			PreparedStatement s = Main.db.getConnection().prepareStatement("SELECT * FROM user WHERE uuid = ?");
			s.setString(1, event.getUser().getId());
			
			ResultSet r = s.executeQuery();
			
			if(!r.next()){
				String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
						+ "0123456789"
						+ "abcdefghijklmnopqrstuvxyz"; 

				// create StringBuffer size of AlphaNumericString 
				StringBuilder sb = new StringBuilder(12); 

				for (int i = 0; i < 12; i++) { 
					int index 
					= (int)(AlphaNumericString.length() 
							* Math.random()); 

					sb.append(AlphaNumericString 
							.charAt(index)); 
				}

				password = sb.toString();
				
				String uuid = event.getUser().getId();
				String crypted = BCrypt.hashpw(password, BCrypt.gensalt());
				
				try {
					PreparedStatement statement = Main.db.getConnection().prepareStatement("INSERT INTO user (uuid, coins, password) VALUES (?, ?, ?)");
					statement.setString(1, uuid);
					statement.setInt(2, 50);
					statement.setString(3, crypted);
					
					statement.execute();
					statement.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		new MessageUtils().sendPrivateMessage(event.getUser(), "Bienvenue sur le discord Casino, si tu veux gagner des tonnes de cadeaux tu es au bon endroit, tu reçois 50 coins gratuit ! \n"
				+ "A cours de crédits ? Pas de panique procure toi en sur le site !");
		
		new MessageUtils().sendPrivateMessage(event.getUser(), "Pour te connecter au site web http://url.com/ tes identifiants sont désormais:"
				+ " \n Utilisateur: " + event.getUser().getId() + "\n Mot de passe: " + password + "\n\nNous te conesillons d'épingler ce message ! Ou de changer le mot de passe dès ta première connection sur le site.");
		
		channel.sendMessage(event.getUser().getAsMention() + " Nous à rejoint ! :sunglasses: \nNous sommes désormais " + (event.getGuild().getMembers().size() - 1)).complete();
		
		Invite used_invite = null;
		
		for (Invite cloned : Main.clone_invites) {
			for (Invite i : Main.jda.getGuildById(Long.valueOf("606552405827649547")).getInvites().complete()) {
				if(cloned.getUses() < i.getUses()) {
					used_invite = i;
					break;
				}
			} 
		}
		
		if(used_invite != null) {
			CasinoUser.getUser(used_invite.getInviter()).addCoins(30);
			new MessageUtils().sendPrivateMessage(used_invite.getInviter(), "Vous avez été créditer de 30 coins pour l'invitation de " + event.getUser().getAsMention());
			Main.clone_invites.clear();
			for(Invite i : Main.jda.getGuildById(Long.valueOf("606552405827649547")).getInvites().complete()){
				Main.clone_invites.add(i);
			}
			
		}
		
	}

	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		MessageChannel channel = event.getJDA().getTextChannelById("606553120280936477");
		channel.sendMessage(event.getUser().getAsMention() + " Nous é quitter ! :scream: \nNous sommes désormais " + (event.getGuild().getMembers().size() - 1)).complete();
	}
}
