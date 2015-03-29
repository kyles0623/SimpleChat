package edu.fau.simplechat.request;

public class LoginClientRequest extends ClientRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1591929371418001710L;
	private final String username;
	
	public LoginClientRequest(String username)
	{
		super(null);
		this.username = username; 
	}
	
	public String getUsername()
	{
		return username;
	}
}
