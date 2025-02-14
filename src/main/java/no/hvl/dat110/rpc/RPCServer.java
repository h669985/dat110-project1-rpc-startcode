package no.hvl.dat110.rpc;

import java.util.HashMap;

import no.hvl.dat110.messaging.MessageConnection;
import no.hvl.dat110.messaging.Message;
import no.hvl.dat110.messaging.MessagingServer;

public class RPCServer {

	private MessagingServer msgserver;
	private MessageConnection connection;

	// HashMap to register RPC methods which extend RPCRemoteImpl
	// The key in the HashMap is the RPC identifier of the method
	private HashMap<Byte, RPCRemoteImpl> services;

	public RPCServer(int port) {
		this.msgserver = new MessagingServer(port);
		this.services = new HashMap<>();
	}

	public void run() {

		// The stop RPC method is built into the server
		RPCRemoteImpl rpcstop = new RPCServerStopImpl(RPCCommon.RPIDSTOP, this);
		register(RPCCommon.RPIDSTOP, rpcstop); // Register stop method

		System.out.println("RPC SERVER RUN - Services: " + services.size());

		connection = msgserver.accept();
		System.out.println("RPC SERVER ACCEPTED");

		boolean stop = false;

		try {
			while (!stop) {

				// Receive a Message containing an RPC request
				Message requestmsg = connection.receive();
				byte[] requestData = requestmsg.getData();

				// Extract the identifier for the RPC method to be invoked
				byte rpcid = requestData[0];

				// Extract the method's parameters
				byte[] params = RPCUtils.decapsulate(requestData);

				// Lookup the method to be invoked
				RPCRemoteImpl impl = services.get(rpcid);

				if (impl == null) {
					System.out.println("Unknown RPC ID: " + rpcid);
					byte[] errorResponse = RPCUtils.encapsulate(rpcid, new byte[0]); // Empty response
					connection.send(new Message(errorResponse));
					continue;
				}

				// Invoke the method and pass the parameters
				byte[] reply = impl.invoke(params);

				// Encapsulate return value
				byte[] encodedReply = RPCUtils.encapsulate(rpcid, reply);

				// Send back the message containing the RPC reply
				Message replymsg = new Message(encodedReply);
				connection.send(replymsg);

				// Stop the server if the stop method was called
				if (rpcid == RPCCommon.RPIDSTOP) {
					stop = true;
				}
			}
		} catch (Exception e) {
			System.out.println("RPCServer error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Used by server-side method implementations to register themselves in the RPC server
	public void register(byte rpcid, RPCRemoteImpl impl) {
		services.put(rpcid, impl);
	}

	public void stop() {
		if (connection != null) {
			connection.close();
		} else {
			System.out.println("RPCServer.stop - connection was null");
		}

		if (msgserver != null) {
			msgserver.stop();
		} else {
			System.out.println("RPCServer.stop - msgserver was null");
		}
	}
}