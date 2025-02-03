package no.hvl.dat110.rpc;

import no.hvl.dat110.messaging.*;

public class RPCClient {

	// underlying messaging client used for RPC communication
	private MessagingClient msgclient;

	// underlying messaging connection used for RPC communication
	private MessageConnection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
		// connect using the RPC client
		connection = msgclient.connect();
	}
	
	public void disconnect() {
		// disconnect by closing the underlying messaging connection
		connection.close();
	}

	/*
	 Make a remote call on the method on the RPC server by sending an RPC request message and receive an RPC reply message

	 rpcid is the identifier on the server side of the method to be called
	 param is the marshalled parameter of the method to be called
	 */

	public byte[] call(byte rpcid, byte[] param) {

		/*

		The rpcid and param must be encapsulated according to the RPC message format

		The return value from the RPC call must be decapsulated according to the RPC message format

		*/

		byte[] request = RPCUtils.encapsulate(rpcid,param);

		connection.send(new Message(request));

		Message responseMsg = connection.receive();
		byte[] response = responseMsg.getData();

		return RPCUtils.decapsulate(response);
		
	}

}
