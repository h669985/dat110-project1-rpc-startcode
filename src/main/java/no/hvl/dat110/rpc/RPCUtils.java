package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		if (payload == null) {
			payload = new byte[0];
		}

		// Encapsulate the rpcid and payload in a byte array according to the RPC message syntax / format
		byte[] rpcmsg = new byte[payload.length + 1];
		rpcmsg[0] = rpcid;
		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		if (rpcmsg == null || rpcmsg.length < 1) {
			throw new IllegalArgumentException("Invalid RPC message");
		}

		// Decapsulate the rpcid and payload in a byte array according to the RPC message syntax
		byte[] payload = new byte[rpcmsg.length - 1];
		System.arraycopy(rpcmsg, 1, payload, 0, rpcmsg.length - 1);

		return payload;
	}

	// convert String to byte array
	public static byte[] marshallString(String str) {
		return str.getBytes();
	}

	// convert byte array to a String
	public static String unmarshallString(byte[] data) {
		return new String(data);
	}
	
	public static byte[] marshallVoid() {
		return ByteBuffer.allocate(4).putInt(0).array();
	}
	
	public static void unmarshallVoid(byte[] data) {
		data = new byte[4];
	}

	// convert boolean to a byte array representation
	public static byte[] marshallBoolean(boolean b) {

		byte[] encoded = new byte[1];

		if (b) {
			encoded[0] = 1;
		} else {
			encoded[0] = 0;
		}
		
		return encoded;
	}

	// convert byte array to a boolean representation
	public static boolean unmarshallBoolean(byte[] data) {
		return (data[0] > 0);
	}

	// integer to byte array representation
	public static byte[] marshallInteger(int x) {
		return ByteBuffer.allocate(4).putInt(x).array();
	}
	
	// byte array representation to integer
	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer.wrap(data).getInt();
	}
}
