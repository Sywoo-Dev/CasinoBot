package fr.sywoo.casino.events;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.objs.CasinoUser;
import fr.sywoo.casino.utils.BCrypt;
import fr.sywoo.casino.utils.MessageUtils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AccountCommands extends ListenerAdapter{

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		if(event.getChannel().getIdLong() == Long.valueOf("606555755058954241")) {

			ArrayList<User> admins = new ArrayList<>();

			String message = event.getMessage().getContentDisplay();

			admins.add(Main.jda.getUserById(Long.valueOf("247027029654896640")));
			admins.add(Main.jda.getUserById(Long.valueOf("285088763003011073")));
			admins.add(Main.jda.getUserById(Long.valueOf("439435331515842570")));

			for(int i = 0; i < admins.size(); i++) {
				User user = admins.get(i);
				new MessageUtils().sendPrivateMessage(user, "Un message est en attente dans le salon support :\n\n" + event.getAuthor().getAsMention() + "\n```" + message + "```");
			}

			return;
		}
		
		if(event.getChannel() == Main.jda.getTextChannelById(Long.valueOf("636743342943633418"))){
			if(!event.getAuthor().isBot()) {
				event.getMessage().delete().complete();
			}
		}
		
		if(event.getChannel().getIdLong() != Long.valueOf("611676152582373411")) {
			return;
		}

		String command = event.getMessage().getContentDisplay();
		MessageChannel channel = event.getChannel();
		if(command.startsWith("-")) {

			String[] args = command.split(" ");

			if(args[0].contains("gifts") || args[0].contains("lot")) {
				try {
					PreparedStatement statement = Main.db.getConnection().prepareStatement("SELECT * FROM gifts ORDER BY id DESC");
					ResultSet rslt = statement.executeQuery();
					event.getChannel().sendMessage("=========== **Les lots / The Gifts** ===========").complete();
					while(rslt.next()) {
						if (rslt.getInt("qte") > 0) {
							event.getChannel()
							.sendMessage("**" + rslt.getString("name") + "** ```"
									+ rslt.getString("description") + "```\nPrix/Price: " + rslt.getInt("price")
									+ " Points\nQuantité/Amount: " + rslt.getInt("qte") + "\n----").complete();
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch blockqq
				}
				event.getChannel().sendMessage("=========== **Les lots / The Gifts** ===========").complete();
				return;
			}

			if(args[0].contains("login")) {
				new MessageUtils().sendPrivateMessage(event.getAuthor(), "Votre identifiant est: " + event.getAuthor().getId() + "\n Vous avez oublié votre mot de passe ? Suivez la procédure suivante ! http://url.com");
				return;
			}

			if(args[0].contains("register")){
				try {
					String password = "";
					PreparedStatement s = Main.db.getConnection().prepareStatement("SELECT * FROM user WHERE uuid = ?");
					s.setString(1, event.getAuthor().getId());

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

						String uuid = event.getAuthor().getId();
						String crypted = BCrypt.hashpw(password, BCrypt.gensalt());

						try {
							PreparedStatement statement = Main.db.getConnection().prepareStatement("INSERT INTO user (uuid, coins, password) VALUES (?, ?, ?)");
							statement.setString(1, uuid);
							statement.setInt(2, 50);
							statement.setString(3, crypted);

							statement.execute();
							statement.close();

							new MessageUtils().sendPrivateMessage(event.getAuthor(), "Votre compte à été créer !\nVotre identifiant est " + event.getAuthor().getId() + " votre mot de passe est : " 
							+ password + "\n\nNous te conesillons d'épingler ce message ! Ou de changer le mot de passe dès ta première connection sur le site.");

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else {
						new MessageUtils().sendPrivateMessage(event.getAuthor(), "Vous avez déjà un compte chez nous ! Pour récupérer votre identifiant faite -login dans le channel prévu à cette effet");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return;
			}

			if(args[0].contains("coins")) {
				CasinoUser cu = CasinoUser.getUser(event.getAuthor());
				if(args.length < 2) {
					channel.sendMessage(event.getAuthor().getAsMention() + " Vous possèdez " + cu.getCoins() + " <:1000:612744834603548705>").complete();
				}
				new MessageUtils().sendPrivateMessage(event.getAuthor(), "Vous possèdez " + cu.getCoins() + " <:1000:612744834603548705>");
				return;
			}

			if(args[0].contains("web")) {
				channel.sendMessage("Vous pouvez accèder au site web depuis : \n http://url.com").complete();
				return;
			}

			if(args[0].contains("rules")) {
				new MessageUtils().sendPrivateMessage(event.getAuthor(), "le plus zolie des reglement");
				return;
			}

		}else {
			if (!event.getAuthor().isBot()) {
				event.getMessage().delete().complete();
			}
		}
	}
}
