package no.hvl.dat110.messaging;

import java.net.InetSocketAddress;
import java.net.Socket;


public class MessagingClient {

	// name/IP address of the messaging server
	private final String server;

	// server port on which the messaging server is listening
	private final int port;
	
	public MessagingClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	// setup of a messaging connection to a messaging server
	public MessageConnection connect () {
		try {
			// client-side socket for underlying TCP connection to messaging server
			Socket clientSocket = new Socket();

			// connect to messaging server using a TCP socket
			clientSocket.connect(new InetSocketAddress(server, port), 1000);

			// create and return a corresponding messaging connection
			return new MessageConnection(clientSocket);

		} catch(Exception ignored) {}
		return null;
	}
}
