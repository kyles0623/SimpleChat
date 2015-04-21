package edu.fau.simplechat.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

import edu.fau.simplechat.logger.Logger;

/**
 * Represents an Immutable Message being sent to a group that contains a file.
 * @author kyle
 */
public class FileMessageModel implements Serializable {

	/**
	 * Serialization Number
	 */
	private static final long serialVersionUID = 6807871583061759704L;

	/**
	 * Maximum size of file in bytes
	 */
	public static final int SIZE_BYTE_LIMIT = 1024*1024; //1MB

	/**
	 * Byte content of the file
	 */
	private final byte[] contents = new byte[SIZE_BYTE_LIMIT];

	/**
	 * Name of the file
	 */
	private final String fileName;

	/**
	 * Number of bytes in file
	 */
	private int numberOfBytes;

	/**
	 * User sending the file
	 */
	private final UserModel sendingUser;

	/**
	 * time the file was sent
	 */
	private final Date timeSent;

	/**
	 * Group the file is being sent to
	 */
	private final GroupModel group;

	/**
	 * Initialize the FileMessageModel
	 * @param group Group file is being sent to
	 * @param sendingUser User sending the file
	 * @param file File to be sent
	 */
	public FileMessageModel(final GroupModel group,final UserModel sendingUser, final File file)
	{
		this.fileName = file.getName();

		this.group = group;

		this.sendingUser = sendingUser;


		//If an error occurs, the size of the file will still be -1
		numberOfBytes = -1;

		timeSent = new Date();

		try{
			final InputStream stream = new FileInputStream(file);

			//Read the contents of the file
			//into the byte array
			numberOfBytes = stream.read(contents);
			stream.close();
		}
		catch(IOException e){

			Logger.getInstance().write(e.toString());
		}

	}

	/**
	 * Retrieve contents of the file
	 * @return Byte array representing file contents
	 * @precondition numberOfBytes != -1
	 * @postcondition none
	 */
	public byte[] getContents() {
		return contents;
	}

	/**
	 * Retrieve the name of the file
	 * @return The name of the file
	 * @precondition none
	 * @postcondition none
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Retrieve the time the message was originally sent
	 * @return time message was sent at
	 * @precondition none
	 * @postcondition none
	 */
	public Date getTimeSent()
	{
		return new Date(timeSent.getTime());
	}

	/**
	 * Retrieve the number of bytes.
	 * If -1 is returned, there was an error reading the
	 * contents of the file.
	 * @return Number of bytes the file takes up
	 * @precondition none
	 * @postcondition none
	 */
	public int getNumberOfBytes() {
		return numberOfBytes;
	}

	/**
	 * Retrieve the group the file is being sent to
	 * @return The group the file is being sent to
	 * @precondition none
	 * @postcondition none
	 */
	public GroupModel getGroup()
	{
		return group;
	}

	/**
	 * Retrieve the user the file is being sent by
	 * @return The user the file is being sent by
	 * @precondition none
	 * @postcondition none
	 */
	public UserModel getSendingUser()
	{
		return sendingUser;
	}
}
