package fr.sywoo.casino;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import fr.sywoo.casino.events.AccountCommands;
import fr.sywoo.casino.events.BoostManager;
import fr.sywoo.casino.events.BotConnection;
import fr.sywoo.casino.events.CoinsFlipRaction;
import fr.sywoo.casino.events.CountrySelector;
import fr.sywoo.casino.events.Dices;
import fr.sywoo.casino.events.JoinManager;
import fr.sywoo.casino.events.PrivateCommand;
import fr.sywoo.casino.events.Roll;
import fr.sywoo.casino.events.Twice;
import fr.sywoo.casino.events.moderation.ModerationCommand;
import fr.sywoo.casino.objs.CoinsFlipObject;
import fr.sywoo.casino.sockets.Connection;
import fr.sywoo.casino.tasks.Loto;
import fr.sywoo.casino.utils.DataBase;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.User;

public class Main {

	
	public static JDA jda;
	public static DataBase db;
	public static boolean launched;
	
	private static ServerSocket serverSocket;
	
	public static ArrayList<Socket> socketlist = new ArrayList<Socket>();
	
	public static ArrayList<User> loto_participation = new ArrayList<User>();
	
	public static HashMap<Long, CoinsFlipObject> cfs = new HashMap<>();
	
	public static ArrayList<Invite> clone_invites = new ArrayList<Invite>();
	
	public static void main(String[] args) throws LoginException, ClassNotFoundException, IOException {
		
		launched = false;
		JDABuilder builder = new JDABuilder(AccountType.BOT);
		String token = "NjA2NTYxODk5Mjk4OTQ3MDky.XUM21A.QQPfQrMhxwl3X-o1ALkTX8cSjQE";
		builder.setToken(token);
		builder.setGame(Game.watching("https://casinourl.com"));
		
		builder.addEventListener(new JoinManager());
		builder.addEventListener(new BotConnection());
		builder.addEventListener(new BoostManager());
		builder.addEventListener(new Dices());
		builder.addEventListener(new Roll());
		builder.addEventListener(new Twice());
		builder.addEventListener(new AccountCommands());
		builder.addEventListener(new ModerationCommand());
		builder.addEventListener(new CountrySelector());
		builder.addEventListener(new PrivateCommand());
		builder.addEventListener(new CoinsFlipRaction());
	
		db = new DataBase(); 
		db.connect("localhost", "casino", 3306, "casino", "NjIyZjhmNGFjM2Ri");
		int s = 120;
		serverSocket = new ServerSocket(s);
        System.out.println("Port de Socket : " + s);
        Thread thread = new Thread(new Connection(serverSocket));
        thread.start();
		jda = builder.build();
		
		launch();
	}
	
	private static void launch() {
		Timer timer;
		TimerTask task = new Loto();
		timer = new Timer(true);
		timer.schedule(task, 5000, 3600000);
	}
	
	
}
