package edu.fau.simplechat.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

public class Logger {

	private static Logger instance;
	
	private FileWriter file;
	
	private DateFormat dateFormat;
	
	private boolean isOpen = true;
	
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
	
	public static synchronized Logger getInstance()
	{
		if(instance == null)
		{
			instance = new Logger();
		}
		
		return instance;
	}
	
	public void write(String message)
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
	
	@Override
	public void finalize()
	{
		close();
	}
}
