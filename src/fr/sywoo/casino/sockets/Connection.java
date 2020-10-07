package fr.sywoo.casino.sockets;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import fr.sywoo.casino.Main;

public class Connection implements Runnable {

	private ServerSocket serverSocket;
	private Socket socket;
	private PrintWriter out;

	public Connection(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		while (true) {
			try {
				socket = serverSocket.accept();
				out = new PrintWriter(socket.getOutputStream());
				String sender = socket.getRemoteSocketAddress().toString();
				sender = sender.split(":")[0];
				sender = sender.replace(":", "");

				if (sender.equals("127.0.0.1")) {
					System.out.println("Ip tentative" + socket.getRemoteSocketAddress().toString());
					System.out.println(sender + " Result");
					return;
				}
				out.println("Socket Sending");
				out.flush();
				System.out.println("new connect");
				Main.socketlist.add(socket);
				Thread thread = new Thread(new Reception(new BufferedReader(new InputStreamReader(socket.getInputStream())), socket));
				thread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
