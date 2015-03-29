package edu.fau.simplechat.response;

import java.io.Serializable;
import java.util.UUID;

public abstract class ServerResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5280802070635854053L;
	protected UUID requestId;
		
	
	public ServerResponse(UUID reqId)
	{
		requestId = reqId;
	}
	
	public UUID getRequestId()
	{
		return requestId;
	}
}
