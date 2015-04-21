package edu.fau.simplechat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.fau.simplechat.logger.Logger;

/**
 * Chat Server is the main connection all users
 * will be connecting to. Chat Server will receive
 * connections to users and will give them their
 * own thread to receive requests from.
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
	private final ChatManager chatManager;

	private final ExecutorService clientThreadPool;

	private final static ChatServer instance = new ChatServer();

	/**
	 * Initialize the chat server with the given port number.
	 */
	private ChatServer()
	{
		this.running = new AtomicBoolean(true);
		chatManager = new ChatManager();
		clientThreadPool = Executors.newCachedThreadPool();
	}

	/**
	 * Retrieve single instance of ChatServer
	 * @return ChatServer instance
	 * @precondition none
	 * @postcondition none
	 */
	public static ChatServer getInstance()
	{
		return instance;
	}

	/**
	 * Initializes port number for ChatServer
	 * @param port Port to initialize to
	 * @precondition none
	 * @postcondition Server will be receiving connections through port
	 */
	public void setup(final int port)
	{
		this.port = port;
	}

	/**
	 * Start to retrieve incoming sockets connecting to the server.
	 * @precondition setup method has been called with a port number.
	 * @postcondition Chat Server will receive connections to users
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
				running.set(false);
				clientThreadPool.shutdown();
			}
		}

		clientThreadPool.shutdown();
		Logger.getInstance().close();
	}

	/**
	 * Stop the server from running.
	 * 
	 * @precondition
	 * @postcondition
	 */
	public synchronized void stop()
	{
		running.set(false);
	}
}
