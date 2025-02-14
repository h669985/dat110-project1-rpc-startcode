package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageConnection {

	private DataOutputStream outStream; // for writing bytes to the underlying TCP connection
	private DataInputStream inStream; // for reading bytes from the underlying TCP connection
	private Socket socket; // socket for the underlying TCP connection
	
	public MessageConnection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream (socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data = MessageUtils.encapsulate(message);

		// encapsulate the data contained in the Message and write to the output stream

		try {
			outStream.write(data);
		} catch (IOException ex) {
			System.out.println("Connection: " + ex.getMessage());
		}
	}

	public Message receive() {
		try {
			byte[] data = new byte[MessageUtils.SEGMENTSIZE];

			// read a segment from the input stream and decapsulate data into a Message
			inStream.readFully(data);

			return MessageUtils.decapsulate(data);
		} catch (IOException ex) {
			System.out.println("Connection: " + ex.getMessage());
		}
        return null;
	}

	// close the connection by closing streams and the underlying socket	
	public void close() {

		try {
			
			outStream.close();
			inStream.close();

			socket.close();
			
		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}