package edu.fau.simplechat.response;

import java.util.UUID;

import edu.fau.simplechat.model.FileMessageModel;

public class FileMessageResponse extends ServerResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1926184797227242890L;

	private final FileMessageModel fileMessage;

	public FileMessageResponse(final UUID reqId, final FileMessageModel fileMessage) {
		super(reqId);
		this.fileMessage = fileMessage;
		// TODO Auto-generated constructor stub
	}

	public FileMessageModel getFileMessage()
	{
		return fileMessage;
	}
}
