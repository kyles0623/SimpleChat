package edu.fau.simplechat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.fau.simplechat.gui.IRequestListener;
import edu.fau.simplechat.logger.Logger;
import edu.fau.simplechat.model.UserModel;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.ServerResponse;
import edu.fau.simplechat.response.handler.ResponseHandlerFactory;

/**
 * Handles all communication with the server
 * by sending and receiving requests and responses.
 * Expected to be ran on its own thread.
 * @author kyle
 *
 */
public class ServerConnection implements Runnable {

	/**
	 * Listener to communicate with GUI
	 */
	private IRequestListener requestListener;
	
	/**
	 * Main socket connection to server
	 */
	private Socket serverSocket;
	
	/**
	 * List of Request yet to be handled
	 */
	private RequestList pendingRequestList;
	
	/**
	 * OutputStream connected to Server Socket
	 */
	private ObjectOutputStream outputStream;
		
	/**
	 * InputStream connected to server Socket
	 */
	private ObjectInputStream inputStream;
	
	/**
	 * Port number for server
	 */
	private final int port;
	
	/**
	 * Host server is hosted on
	 */
	private final String host;
	
	/**
	 * Boolean indicating whether thread should be running
	 */
	private final AtomicBoolean running;
	
	/**
	 * Current User's information
	 */
	private UserModel user;
	
	/**
	 * Thread Pool for current  connection to server
	 */
	private final ExecutorService singleThreadPool;
	
	/**
	 * Initialize the ServerConnection 
	 * @param host Host server is hosted on
	 * @param port Port to connect to server through
	 */
	public ServerConnection(final String host, final int port)
	{
		this.host = host;
		this.port = port;
		
		running = new AtomicBoolean(true);
		pendingRequestList = new RequestList();
		singleThreadPool = Executors.newSingleThreadExecutor();
		
	}
	
	/**
	 * Close connection to Server
	 */
	public synchronized void stopRunning()
	{
		try {
			if(serverSocket != null)
			{
				serverSocket.close();
			}
			running.set(false);
			singleThreadPool.shutdown();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Connect to the server. 
	 */
	public void connect()
	{
		try 
		{
			serverSocket = new Socket(host,port);
			Logger.getInstance().write("Creating Output and Input Objects.");
			outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
			inputStream = new ObjectInputStream(serverSocket.getInputStream());
			Logger.getInstance().write("Created messages");
			
			if(requestListener != null)
			{
				requestListener.onConnection();
			}
		}
		catch (UnknownHostException e) {
			if(requestListener != null)
			{
				requestListener.onError("Unable to connect to "+host+":"+port+". Please make sure they are right.");
			}
			Logger.getInstance().write("Host does not exist");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * set Users information. To be called after login
	 * @param user UserModel to be set
	 */
	public synchronized void setUser(UserModel user)
	{
		this.user = user;
	}
	
	/**
	 * Retrieve UserModel containing users information
	 * @return UserModel of current User
	 */
	public UserModel getUser()
	{
		return user;
	}
	
	/**
	 * Set the RequestListener that listens for information from Server
	 * to GUI
	 * @param listener RequestListener to be set
	 */
	public synchronized void setRequestListener(IRequestListener listener)
	{
		this.requestListener = listener;
	}
	
	/**
	 * Send a ClientRequest to the server to be handled.
	 * @param request ClientRequest to be sent
	 */
	public synchronized void sendRequest(ClientRequest request)
	{
		Logger.getInstance().write("Sending Request for:"+request.getClass().toString());
		try {
			outputStream.writeObject(request);
			pendingRequestList.add(request);
		} 
		catch (IOException e) {
			error("Error sending request");
			e.printStackTrace();
		}
	}
	
	/**
	 * Log an error and let the listener know about it
	 * @param error
	 */
	private void error(String error)
	{
		Logger.getInstance().write("Error: "+error);
		
		if(requestListener != null)
		{
			requestListener.onError(error);
		}
	}
	
	/**
	 * Handle a response received from the Server
	 * @param response Response to be handled
	 */
	private synchronized void handleResponse(ServerResponse response)
	{
		ClientRequest request = null;
		
		if(response.getRequestId() != null)
		{
			request = pendingRequestList.getRequestById(response.getRequestId());
		}
		
		ResponseHandlerFactory.getInstance()
		.createResponseHandler(this,response, 
				request, 
				requestListener).handle();
		pendingRequestList.remove(response.getRequestId());
	}
	
	/**
	 * 
	 */
	public void start()
	{
		singleThreadPool.execute(this);
	}
	
	/**
	 * Thread runs code to receive requests from server.
	 */
	@Override
	public void run()
	{
		while(running.get())
		{
			try {
				
				ServerResponse response = (ServerResponse)inputStream.readObject();
				Logger.getInstance().write("Received Response: "+response.getClass().getName());
				handleResponse(response);
			} 
			catch (IOException e) 
			{
				running.set(false);
				Logger.getInstance().write("Closing application.");
				if(requestListener != null)
				{
					requestListener.onLostConnection();
				}
			}
			catch(ClassNotFoundException e)
			{
				Logger.getInstance().write("AbstractRequest class not found: "+e);
			}
		}
	}
}
