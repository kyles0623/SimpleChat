package edu.fau.simplechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.GroupModel;

/**
 * TODO: Description of ChatServer
 * @author kyle
 *
 */
public class ChatServer {

	/**
	 * port number for server to run on
	 */
	private int port;
	
	/**
	 * atomic boolean indicating that server is running.
	 */
	private final AtomicBoolean running;
	
	/**
	 * ServerSocket that connects clients to this server.
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Manages all of the user connections and groups. All UserConnections and ChatGroups should be created through here.
	 */
	private ChatManager chatManager;
	
	private ExecutorService clientThreadPool;
	
	private final static ChatServer instance = new ChatServer();
	
	/**
	 * TODO :Initialize the chat server with the given port number. 
	 * @param port Port number  to connect through
	 */
	private ChatServer()
	{
		this.running = new AtomicBoolean(true);
		chatManager = new ChatManager();
		clientThreadPool = Executors.newCachedThreadPool();
	}
	
	public static ChatServer getInstance()
	{
		return instance;
	}
	
	/**
	 * Initializes port number for ChatServer
	 * @param port
	 * @precondition
	 * @postcondition
	 */
	public void setup(int port)
	{
		this.port = port;
	}
	
	/**
	 * Start to retrieve incoming sockets connecting to the server.
	 * @throws IOException
	 * @precondition setup method has been called.
	 * @postcondition TODO
	 */
	public synchronized void run() 
	{
		try{
			//Initialize ServerSocket
			this.serverSocket = new ServerSocket(port);
		}
		catch(IOException e)
		{
			System.out.println("Error creating ServerSocket: "+e);
			return;
		}

		while(running.get())
		{
			Logger.getInstance().write("Server waiting for clients on port: "+port);
			try{
				Socket socket = serverSocket.accept();
				UserConnection connection = chatManager.createUserConnection(socket);
				clientThreadPool.execute(connection);
			}
			catch(IOException e)
			{
				System.out.println("Error in connection while waiting for socket: "+e);
				clientThreadPool.shutdown();
			}
		}
		
		clientThreadPool.shutdown();
		Logger.getInstance().close();
	}
	
	public synchronized void testingThread()
	{
		Thread thread  = new Thread(new Runnable(){

			@Override
			public void run() {
				
				while(true){
					Scanner scan = new Scanner(System.in);
					
					String line = scan.nextLine();
					
					if(line.contains("USERS")){
						List<GroupModel> groups = chatManager.getGroups();
						
						for(GroupModel group: groups)
						{
							ChatGroup chatGroup = chatManager.getGroupById(group.getId());
							
							System.out.print(chatGroup.toString());
						}
						
					}
				
				}
				
			}
			
			
		});
		
		thread.start();
		
	}
	
	/**
	 * Stop the server from running.
	 */
	public synchronized void stop()
	{
		running.set(false);
	}
}
