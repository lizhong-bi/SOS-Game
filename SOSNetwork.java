import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

/**
 * 
 * 
 *
 */
class SOSNetwork {

	int whoseTurn;
	boolean online = false;
	int x;
	int y;
	char c;
	int turn = 0;
	private boolean connected = false;

	ServerSocket serverSocket;
	Socket socket;
	PrintWriter output;
	BufferedReader input;

	JFrame frame, serverFrame, clientFrame, serverCommunicationFrame, clientCommunicationFrame;
	JButton serverButton, clientButton, serverSendButton, clientSendButton;
	
	JTextArea coordinate;
	JPanel sidePanel, sideButtonPanel, labelPanel, serverPanel, clientPanel, gamePanel, serverCommunicatePanel,
			clientCommunicatePanel;
	JLabel selectSide, connectingServerLabel, connectingClientLabel, connectedClientLabel, connectedServerLabel;
	JTextField serverTypeField, clientTypeField;

	char coordinateX, coordinateY, letter;
	String serverReceive, clientSend;


	public void go() {

		frame = new JFrame("SOS");
		sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
		sideButtonPanel = new JPanel();
		sideButtonPanel.setLayout(new FlowLayout());
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout());

		serverButton = new JButton("SERVER");
		serverButton.addActionListener(new ButtonListenerServerButton());
		clientButton = new JButton("CLIENT");
		clientButton.addActionListener(new ButtonListenerClientButton());

		selectSide = new JLabel("Select a side");

		sideButtonPanel.add(clientButton);
		sideButtonPanel.add(serverButton);
		labelPanel.add(selectSide);
		sidePanel.add(labelPanel);
		sidePanel.add(sideButtonPanel);

		frame.add(sidePanel);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setSize(250, 100);

		// wait for a connection
		while (!connected) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void runServer() {

		try {
			serverSocket = new ServerSocket(5000);
			System.out.println("Waiting For connection");
			Socket client = serverSocket.accept(); // wait for connection
			output = new PrintWriter(client.getOutputStream());
			InputStreamReader stream = new InputStreamReader(client.getInputStream());
			input = new BufferedReader(stream);
			whoseTurn = 1;

			connected = true;
			if (connected)
				System.out.println("Connected to Client");
			else
				System.out.println("not connected");
		} catch (Exception i) {
			System.out.println("Error accepting connection");
			i.printStackTrace();
		}

	}

	public void send(int x, int y, char c, int turn) {
		System.out.println("SENDING");
		output.println(x + "" + y + "" + c);
		output.flush();

	}

	public void sendTurn(int turn) {
		System.out.println("Sending Turn");
		output.println(turn);
		output.flush();

	}

	public int recieveTurn() {
		String temp;

		try {
			temp = input.readLine();
			System.out.println("Recieved: " + temp);
			turn = Integer.parseInt(temp.substring(0, 1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return turn;

	}

	public void recieve() {

		System.out.println("Recieving");

		try {
			String temp = input.readLine();
			System.out.println(temp.charAt(0));
			x = Integer.parseInt(temp.substring(0, 1));
			y = Integer.parseInt(temp.substring(1, 2));
			c = temp.charAt(2);

			System.out.println("X is " + x);
			System.out.println("Y is " + y);
			System.out.println("Char is " + c);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void closeAll() {
		try {
			input.close();
			socket.close();
			output.close();
			serverSocket.close();
		} catch (Exception i) {
			System.out.println("Failed to close socket");
		}
	}

	public void runClient() {

		try {
			socket = new Socket("10.242.176.28", 5785); // attempt socket
														// connection // attempt
														// socket
			// connection
			InputStreamReader stream = new InputStreamReader(socket.getInputStream()); // make
																						// Stream
			input = new BufferedReader(stream);
			output = new PrintWriter(socket.getOutputStream());
			whoseTurn = 0;
			connected = true;
			if (connected)
				System.out.println("Connected to server");
			else
				System.out.println("not connected to server");

		} catch (IOException i) {
			i.printStackTrace();
		}

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	

	class ButtonListenerServerButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("SERVER");
			runServer();
		}
	}

	class ButtonListenerClientButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			runClient();
		}
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}

	public void setWhoseTurn(int whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

}
