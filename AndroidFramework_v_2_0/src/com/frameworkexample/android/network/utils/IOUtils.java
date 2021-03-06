package com.frameworkexample.android.network.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class IOUtils
{
	 public static void closeQuietly(Reader input)
	  {
	    try
	    {
	      if (input != null)
	        input.close();
	    }
	    catch (IOException ioe)
	    {
	    }
	  }

	  public static void closeQuietly(Writer output)
	  {
	    try
	    {
	      if (output != null)
	        output.close();
	    }
	    catch (IOException ioe)
	    {
	    }
	  }

	  public static void closeQuietly(InputStream input)
	  {
	    try
	    {
	      if (input != null)
	        input.close();
	    }
	    catch (IOException ioe)
	    {
	    }
	  }

	  public static void closeQuietly(OutputStream output)
	  {
	    try
	    {
	      if (output != null)
	        output.close();
	    }
	    catch (IOException ioe)
	    {
	    }
	  }
  public static String toString(InputStream input, String encoding)
    throws IOException
  {
    StringWriter sw = new StringWriter();
    copy(input, sw, encoding);
    return sw.toString();
  }
  public static void copy(InputStream input, Writer output, String encoding)
  throws IOException
 {
  if (encoding == null) {
    copy(input, output);
  } else {
    InputStreamReader in = new InputStreamReader(input, encoding);
    copy(in, output);
  }
 }
  public static void copy(InputStream input, Writer output)
  throws IOException
	{
	  InputStreamReader in = new InputStreamReader(input);
	  copy(in, output);
	}
  public static int copy(Reader input, Writer output)
  	throws IOException
	{
	  char[] buffer = new char[4096];
	  int count = 0;
	  int n = 0;
	  while (-1 != (n = input.read(buffer))) {
	    output.write(buffer, 0, n);
	    count += n;
	  }
	  return count;
	}

  public static int copy(InputStream input, OutputStream output)
  throws IOException
	{
	  byte[] buffer = new byte[4096];
	  int count = 0;
	  int n = 0;
	  while (-1 != (n = input.read(buffer))) {
	    output.write(buffer, 0, n);
	    count += n;
	  }
	  return count;
	}
}
