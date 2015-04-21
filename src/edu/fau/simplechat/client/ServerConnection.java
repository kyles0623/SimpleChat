package edu.fau.simplechat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.fau.simplechat.gui.IResponseListener;
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
	private IResponseListener responseListener;

	/**
	 * Main socket connection to server
	 */
	private Socket serverSocket;

	/**
	 * List of Request yet to be handled
	 */
	private final RequestList pendingRequestList;

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
	 * @precondition There is server running at host:port
	 * @postcondition The ServerConnection will be initialized
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
	 * Close connection to the server
	 * 
	 * @precondition none
	 * @postcondition Connection to the server will be closed
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
	 * Connect to the server and send message to RequestListener
	 * @precondition setResponseListener has been called
	 * @postcondition If successful, ResponseListener.onConnection will be called
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

			if(responseListener != null)
			{
				responseListener.onConnection();
			}
		}
		catch (UnknownHostException e) {
			if(responseListener != null)
			{
				responseListener.onError("Unable to connect to "+host+":"+port+". Please make sure they are right.");
			}
			Logger.getInstance().write("Host does not exist");
		}
		catch (IOException e) {
			if(responseListener != null)
			{
				responseListener.onError("Unable to connect to "+host+":"+port+". Please make sure they are right.");
			}
			Logger.getInstance().write("Host does not exist");
			e.printStackTrace();
		}
	}

	/**
	 * set the user associated with this connection
	 * @param user User to be set to
	 * @precondition connect() has been called
	 * @postcondition The user will be associated with this server connection
	 */
	public synchronized void setUser(final UserModel user)
	{
		this.user = user;
	}

	/**
	 * retrieve the user associated with this connection
	 * @return UserModel associated with this connection
	 * @precondition setUser has been called
	 * @postcondition none
	 */
	public UserModel getUser()
	{
		return user;
	}

	/**
	 * Set the ResponseListener that listens for information from Server
	 * to GUI
	 * @param listener ResponseListener to be set
	 * @precondition none
	 * @postcondition Any response from the server will call upon the set response listener
	 */
	public synchronized void setResponseListener(final IResponseListener listener)
	{
		this.responseListener = listener;
	}

	/**
	 * Send a ClientRequest to the server to be handled.
	 * @param request ClientRequest to be sent to server
	 * @precondition connect() has been successfully called
	 * @postcondition The Server will receive the request
	 */
	public synchronized void sendRequest(final ClientRequest request)
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
	 * Log an error and let the ResponseListener know
	 * @param error
	 * @precondition
	 * @postcondition
	 */
	private void error(final String error)
	{
		Logger.getInstance().write("Error: "+error);

		if(responseListener != null)
		{
			responseListener.onError(error);
		}
	}

	/**
	 * Handle a response from the server
	 * @param response ServerResponse to be handled
	 * @precondition none
	 * @postcondition Response will be handled
	 */
	private synchronized void handleResponse(final ServerResponse response)
	{
		ClientRequest request = null;

		if(response.getRequestId() != null)
		{
			request = pendingRequestList.getRequestById(response.getRequestId());
		}

		ResponseHandlerFactory.getInstance()
		.createResponseHandler(this,response,
				request,
				responseListener).handle();
		pendingRequestList.remove(response.getRequestId());
	}

	/**
	 * Start the separate thread to receive and handle responses from server
	 * @precondition connect() has been called
	 * @postcondition This connection will receive responses from the server.
	 */
	public void start()
	{
		singleThreadPool.execute(this);
	}

	@Override
	public void run()
	{
		//while receiving connections
		while(running.get())
		{
			try {
				//attempt to retrieve response from server
				ServerResponse response = (ServerResponse)inputStream.readObject();
				Logger.getInstance().write("Received Response: "+response.getClass().getName());

				handleResponse(response);
			}
			//An exception usually means connection lost.
			catch (IOException e)
			{
				running.set(false);
				Logger.getInstance().write("Closing application.");
				if(responseListener != null)
				{
					responseListener.onLostConnection();
				}
				singleThreadPool.shutdown();
			}
			//Should not be called
			catch(ClassNotFoundException e)
			{
				Logger.getInstance().write("AbstractRequest class not found: "+e);
			}
		}
	}
}
