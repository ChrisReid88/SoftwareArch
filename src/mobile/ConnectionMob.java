package mobile;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ConnectionMob extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
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
	
	public void run(String callOutData){
		try { // an echo server
			String data = in.readUTF();
			System.out.println("Received: " + data);
			out.writeUTF(callOutData);
		} catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	
	public String getData() throws IOException {
		return  in.readUTF();
	}
	
	public void sendData(String data) throws IOException {
		out.writeUTF(data);
	}
}