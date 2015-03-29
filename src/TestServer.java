import edu.fau.simplechat.server.ChatServer;


public class TestServer {

	public static void main(final String[] args)
	{
		ChatServer server = ChatServer.getInstance();
		server.setup(75);
		server.testingThread();
		server.run();
	}
}