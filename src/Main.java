package com.medvedomg;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class Main extends Application {
	private Label lIP;
	private Label lPort;
	public static TextField tfIP;
	public static TextField tfPort;
	public static Button connect;
	private Rectangle rectangle;
	public static Button send;
	public TextField tfTextInput;
	private static int width;
	private static int height;
	private static Pane root;

	public ListView list;
	private Stage primaryStage;
	private static Socket client;
	private static int flag;
	private static String serverName;
	private static int port;
	private static int portFlag;
	private static int serverNameFlag;
	@Override
	public void start(Stage primaryStage) {
		try {
			Pane root = new Pane();

			Scene scene = new Scene(root,450,650);




			width = 400;
			height = 600;

			Button addNewItem = new Button("Add new item");
			addNewItem.relocate(5, 500);

			lIP = new Label("IP : ");
			lIP.relocate(width-300, height-550);

			lPort = new Label("Port : ");
			lPort.relocate(width-300, height-510);

			tfIP = new TextField();

			tfIP.relocate( width/2-50, height-550);
			tfIP.textProperty().addListener((observable, oldValue, newValue) -> {
				serverName = tfIP.getText();
				System.out.println(serverName+" TF");
				serverNameFlag = 1;
			});

			tfPort = new TextField();
			tfPort.relocate(width/2-50, height-510);
			tfPort.textProperty().addListener((observable, oldValue, newValue) -> {
				String text = tfPort.getText();
				port = Integer.parseInt(text);
				portFlag = 1;
			});

			connect = new Button("Connect");
			connect.relocate(width/2, height-470);

			rectangle = new Rectangle(25,25);
			rectangle.relocate(width/2-30, height-470);
			rectangle.setFill(Color.GREEN);
			rectangle.setStrokeWidth(10);

			list = new ListView();
			list.relocate(100, height-430);

			send = new Button("Send");
			send.relocate(width/2-100,600);



			connect.setOnAction(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {
										System.out.println(serverName+" "+port);
										Thread thread = new Thread() {
											public void run() {

												try {
													System.out.println(serverName+" "+port);

													connectServer(port,serverName);
													System.out.println("Just connected to " + client.getRemoteSocketAddress());
												} catch (IOException e1) {
													e1.printStackTrace();
												}
											}
										};

										thread.start();
									}

//					launch(args);





									public void sendMessageToServer(String message) {

										try {

											OutputStream outToServer = client.getOutputStream();

											DataOutputStream out = new DataOutputStream(outToServer);

											out.writeUTF("Hello from " + client.getLocalSocketAddress() + "! My message is : " + message);

										} catch (Exception e) {

										}

									}

								}
			);

			tfTextInput = new TextField();
			tfTextInput.relocate(width/2-50, 600);


			send.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					sendMessageToServer1(tfTextInput.getText());
					list.getItems().add("Me: " + tfTextInput.getText());

				}
			});

			root.getChildren().addAll(addNewItem,lIP,lPort,tfIP,tfPort,connect,rectangle,list,tfTextInput,send);

//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());


			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {


//			if( !serverName.equals("") && port != 0 ){
//		flag = 1;
//		}
		launch(args);



		flag = 1;
		if( flag != 1){
			System.out.println("Connecting to " + serverName + " on port " + port);


		}}

	public static void sendMessageToServer1(String message) {

		try {

			OutputStream outToServer = client.getOutputStream();

			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF(message);


		} catch (Exception e) {


		}

	}

	public  void connectServer(int port, String serverName) throws IOException {

		System.out.println("Test tfPort.getText()");

		System.out.println("Connecting to " + serverName + " on port " + port);


		client = new Socket(serverName, port);

		System.out.println("Just connected to " + client.getRemoteSocketAddress());

		sendMessageToServer1("Рш!");


		InputStream inFromServer = client.getInputStream();
		DataInputStream in = new DataInputStream(inFromServer);

		boolean isConnected = true;
		while (isConnected) {


			String messageFromServer = null;
			try {
				messageFromServer = in.readUTF();
			} catch (Exception e) {
				isConnected = false;
				messageFromServer = "";
			}

			System.out.println("Server says : " + messageFromServer);
			//			l.setText("Server says : " + messageFromServer);

			list.getItems().add("Server: " + messageFromServer);


		}

		client.close();
	}



}