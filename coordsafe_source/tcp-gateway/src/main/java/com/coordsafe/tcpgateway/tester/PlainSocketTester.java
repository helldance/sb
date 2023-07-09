package com.coordsafe.tcpgateway.tester;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class PlainSocketTester {
	static String ip = "localhost";
	static int port = 2222;
	
	public static void main (String [] args){
		try {
			File file = new File("../bytes.txt");
			Socket socket = new Socket(ip, port);
			Scanner sc = new Scanner(file);
			
			//System.out.println("Please input byte messages:");
			
			byte [] bytes = new byte [2048];
			
			int i = 0;
			while (sc.hasNext() && i < 81){
				bytes[i] = sc.nextByte();
				i ++;
			}
			
			/*for (byte b : bytes){
				System.out.print(b);
			}*/
			
			OutputStream out = socket.getOutputStream(); 
		    DataOutputStream dos = new DataOutputStream(out);
		    dos.write(bytes);
		    
		    socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
