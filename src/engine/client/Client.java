package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class Client extends Thread
{

    {
        int i;
        BufferedReader inFromUser =
                new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket;
		try {
			clientSocket = new DatagramSocket();
			 InetAddress IPAddress = InetAddress.getByName("localhost");
		        byte[] sendData = new byte[1024];
		        byte[] receiveData = new byte[1024];
		        String sentence = inFromUser.readLine();
		        sendData = sentence.getBytes();
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6666);
		        clientSocket.send(sendPacket);
		        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		        clientSocket.receive(receivePacket);
		        String modifiedSentence = new String(receivePacket.getData());
		        System.out.println("FROM SERVER:" + modifiedSentence);
		        clientSocket.close();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
    }
}

>>>>>>> engine
