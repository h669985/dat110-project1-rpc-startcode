package no.hvl.dat110.system.sensor;

import no.hvl.dat110.rpc.RPCRemoteImpl;
import no.hvl.dat110.rpc.RPCUtils;
import no.hvl.dat110.rpc.RPCServer;

public class SensorImpl extends RPCRemoteImpl {

	static final int RANGE = 20;

	public SensorImpl(byte rpcid, RPCServer rpcserver) {
		super(rpcid,rpcserver);
	}

	// implementation of the RPC method
	public int read() {

		long seconds = System.currentTimeMillis();

		double temp = RANGE * Math.sin((double) seconds / 1000);

		// System.out.println("READ:" + temp);
		
		return (int) Math.ceil(temp);
	}

	// called by RPC server on rpc identifier corresponding to read
	public byte[] invoke(byte[] param) {
				
		RPCUtils.unmarshallVoid(param);
		
		int temp = read();

        return RPCUtils.marshallInteger(temp);
	}
}
