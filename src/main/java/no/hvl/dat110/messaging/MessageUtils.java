package no.hvl.dat110.messaging;

public class MessageUtils {

	public static final int SEGMENTSIZE = 128;

	public static int MESSAGINGPORT = 8080;
	public static String MESSAGINGHOST = "localhost";

	public static byte[] encapsulate(Message message) {
		
		byte[] segment = new byte[SEGMENTSIZE];
		byte[] data = message.getData();
		
		// encapulate/encode the payload data of the message and form a segment
		// according to the segment format for the messaging layer

		segment[0] = (byte) data.length;

        System.arraycopy(data, 0, segment, 1, data.length);

		return segment;
		
	}

	public static Message decapsulate(byte[] segment) {

		byte[] data = new byte[segment[0]];

		System.arraycopy(segment, 1, data, 0, data.length);
		
		return new Message(data);
		
	}
	
}
