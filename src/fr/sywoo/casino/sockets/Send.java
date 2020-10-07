package fr.sywoo.casino.sockets;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Send {

	public static void sendSocket(int port, String message) {
		
		InetSocketAddress adress = new InetSocketAddress("127.0.0.1", port);
		
		try {
			@SuppressWarnings("resource")
			Socket socket = new Socket(adress.getHostString(), port);
			if(socket.isClosed()) return;
			DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
			dOut.writeUTF(message);
			dOut.flush();
			dOut.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
