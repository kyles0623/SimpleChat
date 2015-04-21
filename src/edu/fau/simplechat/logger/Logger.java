package edu.fau.simplechat.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Logs information for debug purposes. Logs information to chat.txt file.
 * @author kyle
 *
 */
public class Logger {

	/**
	 * Static instance of Logger class
	 */
	private static Logger instance;

	/**
	 * FileWriter object for writing logs to file
	 */
	private FileWriter file;

	/**
	 * DateFormat object to format the dates appropriately.
	 */
	private final DateFormat dateFormat;

	/**
	 * boolean indicating if the file is open
	 */
	private boolean isOpen = true;

	/**
	 * Intialize Logger Class.
	 */
	private Logger()
	{
		try
		{
			file = new FileWriter("chat.txt",true);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dateFormat = DateFormat.getInstance();
	}

	/**
	 * Returns instance of Logger class
	 * @return Logger Class instance
	 * @precondition none
	 * @postcondition returns Logger Class
	 */
	public static synchronized Logger getInstance()
	{
		if(instance == null)
		{
			instance = new Logger();
		}

		return instance;
	}

	/**
	 * Write a message to the log
	 * @param message Message to write
	 * @precondition none
	 * @postcondition Message will be written to the log
	 */
	public void write(final String message)
	{
		Date date = new Date();
		String mes = dateFormat.format(date)+": "+message+"\n";
		try
		{
			file.append(mes);
			System.out.print(mes);

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Close the file associated with the logger class
	 * 
	 * @precondition none
	 * @postcondition file will be closed for logger class
	 */
	public void close()
	{
		try {
			if(isOpen)
			{
				file.close();
			}
			isOpen = false;
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ensures logger class has closed the file.
	 */
	@Override
	public void finalize()
	{
		close();
	}
}
