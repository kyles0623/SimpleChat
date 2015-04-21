package edu.fau.simplechat.request;

/**
 * Represents a request for a user logging in with a username
 * @author kyle
 *
 */
public class LoginClientRequest extends ClientRequest{

	/**
	 * Serializable ID
	 */
	private static final long serialVersionUID = 1591929371418001710L;

	/**
	 * Name user wants to login with
	 */
	private final String username;

	/**
	 * Create a LoginClientRequest to login user into chat system
	 * @param username Username user wants to be associated with
	 */
	public LoginClientRequest(final String username)
	{
		super(null);
		this.username = username;
	}

	/**
	 * Retrieve username user wants to be associated with
	 * @return Username
	 * @precondition none
	 * @postcondition String is returned
	 */
	public String getUsername()
	{
		return username;
	}
}
