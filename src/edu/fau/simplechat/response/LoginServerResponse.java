package edu.fau.simplechat.response;

import java.util.UUID;

import edu.fau.simplechat.model.UserModel;

public class LoginServerResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4765638957383060035L;

	public static final String RESPONSE_SUCCESS = "RESPONSE_SUCCESS";
	
	public static final String RESPONSE_FAILED = "RESPONSE_FAILED";

	public static final String RESPONSE_USERNAME_EXISTS = "RESPONSE_USERNAME_EXISTS";
	
	private final String response;
	
	private final UserModel user;
	
	public LoginServerResponse(UUID reqId,String response,UserModel user) {
		super(reqId);
		this.response = response;
		this.user = user;
	}
	
	public String getResponse()
	{
		return this.response;
	}
	
	public UserModel getUserModel()
	{
		return user;
	}

}
