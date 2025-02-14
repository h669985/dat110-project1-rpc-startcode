package no.hvl.dat110.system.display;

import no.hvl.dat110.rpc.RPCClient; // Is this used?
import no.hvl.dat110.rpc.RPCServer;
import no.hvl.dat110.system.controller.Common;


public class DisplayDevice {
	
	public static void main(String[] args) {
		
		System.out.println("Display server starting ...");

		// implement the operation of the display RPC server
		// see how this is done for the sensor RPC server in SensorDevice

		RPCServer displayserver = new RPCServer(Common.DISPLAYPORT);

		DisplayImpl display = new DisplayImpl((byte)Common.WRITE_RPCID, displayserver);

		displayserver.run();

		displayserver.stop();
		
		System.out.println("Display server stopping ...");
		
	}
}
