package no.hvl.dat110.messaging;

import java.io.IOException;
import java.net.ServerSocket;

public class MessagingServer {

	// server-side socket for accepting incoming TCP connections
	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {

		try {

			this.welcomeSocket = new ServerSocket(port);

		} catch (IOException ex) {

			System.out.println("Messaging server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// accept an incoming connection from a client
	public MessageConnection accept() {
		try {
			// accept TCP connection on welcome socket and create messaging connection to be returned
			return new MessageConnection(welcomeSocket.accept());
		} catch (IOException ex) {
			System.out.println("Messaging server: " + ex.getMessage());
		}
		return null;
	}

	public void stop() {

		if (welcomeSocket != null) {

			try {
				welcomeSocket.close();
			} catch (IOException ex) {

				System.out.println("Messaging server: " + ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

}
