package edu.fau.simplechat.response.handler;

import edu.fau.simplechat.client.ServerConnection;
import edu.fau.simplechat.gui.IResponseListener;
import edu.fau.simplechat.request.ClientRequest;
import edu.fau.simplechat.response.LoginServerResponse;
import edu.fau.simplechat.response.ServerResponse;

public class LoginResponseHandler extends ResponseHandler {

	public LoginResponseHandler(ServerConnection u,ServerResponse r, ClientRequest c,
			IResponseListener listener) {
		super(u,r, c, listener);
	}

	@Override
	public void handle() {
		LoginServerResponse response = (LoginServerResponse)this.response;
		
		if(response.getResponse().equals(LoginServerResponse.RESPONSE_SUCCESS))
		{
			user.setUser(response.getUserModel());
			listener.onLogin(response.getUserModel());
		}
		else if(response.getResponse().equals(LoginServerResponse.RESPONSE_USERNAME_EXISTS))
		{
			listener.onError("That username is taken. Please choose a different username.");
		}
		else
		{
			listener.onError("A Problem occured when trying to login.");
		}
		
	}

}
