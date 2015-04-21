package edu.fau.simplechat.server;

/**
 * Class to run the server
 * @author kyle
 *
 */
public class RunServer {

	/**
	 * Default port if one isn't supplied
	 */
	private final static int defaultPort = 75;

	public static void main(final String[] args)
	{
		ChatServer server = ChatServer.getInstance();

		int port = defaultPort;

		if(args.length > 0)
		{
			try{
				port = Integer.parseInt(args[0]);
			}
			catch(NumberFormatException e)
			{
				port = defaultPort;
			}
		}

		server.setup(port);
		server.run();
	}
}