package edu.fau.simplechat.request;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.json.simple.JSONObject;

/**
 * Abstract class containing basic information for sending request
 * to server.
 * @author kyle
 *
 */
public abstract class ClientRequest implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4992736202526285106L;

	/**
	 * ID for the request sent by the user
	 */
	private final UUID requestId;
	
	/**
	 * ID for the user that sent the request
	 */
	private final UUID userId;
	
	/**
	 * Time the request was sent at.
	 */
	private final Date sentTime;
	
	public ClientRequest(final UUID userId)
	{
		this.userId = userId;
		requestId = UUID.randomUUID();
		sentTime = new Date();
		
	}

	/**
	 * Get ID of Request
	 * @return id of request
	 */
	public UUID getRequestId() {
		return requestId;
	}

	/**
	 * Get ID of User who sent the request.
	 * @return id of user
	 */
	public UUID getUserId() {
		return userId;
	}

	/**
	 * Get Time the request was sent.
	 * @return Date time was sent
	 */
	public Date getSentTime() {
		return new Date(sentTime.getTime());
	}
}
