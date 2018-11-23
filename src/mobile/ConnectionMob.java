package mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ConnectionMob extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
	
	//Create a new thread
	public ConnectionMob (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {
			System.out.println("Connection: " + e.getMessage());
		}
	}
	//Reads in from the socket then writes other data out.
	public void run(String callOutData){
		try {
			String data = in.readUTF();
			System.out.println("Received: " + data);
			out.writeUTF(callOutData);
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	//Return read from a socket
	public String getData() throws IOException {
		return  in.readUTF();
	}
	
	//Write to out
	public void sendData(String data) throws IOException {
		out.writeUTF(data);
	}
}