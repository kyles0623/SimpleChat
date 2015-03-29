package edu.fau.simplechat.response;

import edu.fau.simplechat.model.MessageModel;

public class MessageResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7664902638333127554L;

	private final MessageModel message;
	
	public MessageResponse(MessageModel message) {
		super(null);
		
		this.message=  message;
		// TODO Auto-generated constructor stub
	}
	
	public MessageModel getMessage()
	{
		return message;
	}

}
