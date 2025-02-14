package no.hvl.dat110.system.controller;

import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {
		
		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);

		
		// create local display and sensor stub objects
		display = new DisplayStub(displayclient);
		sensor = new SensorStub(sensorclient);

		// connect to sensor and display RPC servers - using the RPCClients
		try {
			displayclient.connect();
			sensorclient.connect();
		} catch (Exception e) {
			System.out.println("Failed to connect: " + e.getMessage());
			return;
		}

		// read value from sensor using RPC and write to display using RPC
		for (int i = 0; i < N; i++) {
			int temp = sensor.read();
			display.write(Integer.toString(temp));

			try {
				Thread.sleep(1000); // Wait 1 second before next reading
			} catch (InterruptedException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
