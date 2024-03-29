package com.ubaid.app.model.logger;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;


public class Queue
{
	final LinkedBlockingQueue<Text> queue = new LinkedBlockingQueue<>(5000);
	private final Random random = new Random();
	
	public void setText(Exception ex, int index)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String exceptionText = sw.toString();
		
		Text text = new Text(exceptionText, Level.ERROR, index);
		try
		{
			queue.put(text);
		}
		catch (InterruptedException e)
		{
			setText(e, getErrorIndex());
		}
	}
	
	public void setIndex(String message)
	{
		Text text = new Text(message, Level.INFO, getEntityIndex());
		try
		{
			queue.put(text);
		}
		catch(InterruptedException e)
		{
			setText(e, getErrorIndex());
		}
	}
	
	public void setText(String message, int index)
	{
		Text text = new Text(message, Level.INFO, index);
		
		try
		{
			queue.put(text);
		}
		catch(InterruptedException exp)
		{
			setText(exp, getErrorIndex());
		}
	}
	
	public void setDuplicationWarning(Exception exp, int index)
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exp.printStackTrace(pw);
		String exceptionText = sw.toString();

		
		Text text = new Text(exceptionText, Level.WARN, index);
		
		try
		{
			queue.put(text);
		}
		catch(InterruptedException e)
		{
			setText(e, getErrorIndex());
		}

	}
	
	
	public Text getNext()
	{
		try
		{
			return queue.take();
		}
		catch(InterruptedException exp)
		{

			setText(exp, getErrorIndex());
			return null;
		}
		
	}
	
	public int getEntityIndex()
	{
		return random.nextInt(10000);
	}
	
	public int getErrorIndex()
	{
		return random.nextInt(10000);

	}
	
	public int getDuplicationIndex()
	{
		return random.nextInt(10000);
	}
}
