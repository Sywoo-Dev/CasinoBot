package fr.sywoo.casino.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import fr.sywoo.casino.Main;
import fr.sywoo.casino.enums.Languages;
import fr.sywoo.casino.objs.CasinoUser;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

public class Reception implements Runnable {

	private BufferedReader in;
	private Socket socket;
	private String message;

	public Reception(BufferedReader in, Socket socket) {
		this.in = in;
		this.socket = socket;
	}

	@Override
	public void run() {
		boolean running = true;
		if(!socket.getRemoteSocketAddress().toString().split(":")[0].contains("127.0.0.1")){
			System.out.println("UN FDP A ESSAYER DE SE CO A VOS SOCKETS " + socket.getRemoteSocketAddress());
			return;
		}
		while (running) {
			try {
				message = in.readLine();
				if (message == null) {
					System.out.println("Le message Socket envoyer est null");
					return;
				}
				if (message.equalsIgnoreCase("shutdown")) {
					running = false;
					Main.socketlist.remove(socket);
					socket.close();
				}

				if(message.contains("coins")) {
					String[] args = message.split(":");
					User user = Main.jda.getUserById(Long.valueOf(args[1]));
					int amount = Integer.valueOf(args[2]);

					CasinoUser.getUser(user).addCoins(amount);
					TextChannel channel = Main.jda.getTextChannelById(Long.valueOf("637117273789693962"));
					if (CasinoUser.getUser(user).getLang() == Languages.FR) {
						channel.sendMessage(user.getAsMention() + " à acheté " + amount + " points !\nMerci et bonne chance :four_leaf_clover:").complete();
					}else {
						channel.sendMessage(user.getAsMention() + " bought " + amount + " points !\nThanks and good luck :four_leaf_clover:").complete();
					}
					System.out.println(user.getName() + " à été créditer de " + amount + " Coins [ID = " + user.getId() + " ]");
				}

				System.out.println("Received = " + message);



			} catch (IOException e) {
				running = false;
				new IOException("THE SOCKET ARE CLOSED BEACAUSE " + e.getCause());
			}
		}
	}
}
