package com.coordsafe.gateway.client.echo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class LocatorTester {
	
/*	public static void main(String[] args){
		for(int i = 0; i < 200; i++){
			EchoClient ec = new EchoClient("localhost", 2225, i);
			ec.run();
		}
	}
*/
	
	private static ExecutorService pool;
	public static void main (String [] args){
		pool = Executors.newFixedThreadPool(5);
		int i = 1;
		while(i < 5){
			pool.execute(new EchoClient("localhost", 2226, i));//137.132.145.228
			i++;
			//simulator.
		}
		
/*		System.out.println(pool.isShutdown());
		pool.shutdown();*/
		
	}

}


/*


*/
