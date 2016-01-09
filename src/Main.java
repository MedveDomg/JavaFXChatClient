package com.medvedomg;
	
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


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

	private ListView list;
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
					try {
						
						System.out.println(serverName+" "+port);
						client = new Socket(serverName, port);

					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					}

					catch (IOException e1) {
						e1.printStackTrace();
					}

					System.out.println("Just connected to " + client.getRemoteSocketAddress());
//					launch(args);
					try {

						while (true) {

							InputStream inFromServer = client.getInputStream();

							DataInputStream in = new DataInputStream(inFromServer);

							System.out.println("Server says " + in.readUTF());

						}

					} catch (Exception e) {
						e.printStackTrace();
					}

					try {

						client.close();

					} catch (IOException e) {
						e.printStackTrace();
					}
					
					}

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
	       
	        
	        root.getChildren().addAll(addNewItem,lIP,lPort,tfIP,tfPort,connect,rectangle,list,tfTextInput,send);
		
		//	scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			
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
		
		
		}}}

