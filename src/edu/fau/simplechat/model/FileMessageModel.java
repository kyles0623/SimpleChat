package edu.fau.simplechat.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Represents a file being sent over a connection.
 * @author kyle
 *
 */
public class FileMessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6807871583061759704L;

	private final byte[] contents = new byte[SIZE_BYTE_LIMIT];

	private final String fileName;

	private final int numberOfBytes;

	public static final int SIZE_BYTE_LIMIT = 2048;

	private final GroupModel group;

	public FileMessageModel(final GroupModel group,final File file) throws IOException
	{
		this.fileName = file.getName();

		this.group = group;

		final InputStream stream = new FileInputStream(file);

		numberOfBytes = stream.read(contents);

		stream.close();
	}


	public byte[] getContents() {
		return contents;
	}


	public String getFileName() {
		return fileName;
	}


	public int getNumberOfBytes() {
		return numberOfBytes;
	}
}
