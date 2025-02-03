package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.*;

public class DisplayStub extends RPCLocalStub {

	public DisplayStub(RPCClient rpcclient) {
		super(rpcclient);
	}
	
	public void write (String message) {

		// implement marshalling, call and unmarshalling for write RPC method
		byte[] request = RPCUtils.marshallString(message);

		// No return value in write() - don't bother with assigning a variable to the return value of call()
		rpcclient.call((byte)Common.WRITE_RPCID, request);

		// No return value; no need to unmarshall?
	}
}
