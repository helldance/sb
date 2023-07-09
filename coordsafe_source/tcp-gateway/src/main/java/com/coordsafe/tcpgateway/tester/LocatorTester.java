package com.coordsafe.tcpgateway.tester;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocatorTester {
	private static ExecutorService pool;
	public static void main (String [] args){
		pool = Executors.newFixedThreadPool(256);
		
		for (int i = 0; i < 100; i ++){
			pool.execute(new LocatorSimulator());

			System.out.println("start simulator " + i);
			//simulator.
		}
	}
}
