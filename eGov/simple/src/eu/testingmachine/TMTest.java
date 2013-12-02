package eu.testingmachine;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TMTest
{
	@Test
	public void checkUrl() throws IOException
	{
	    URL url = new URL(System.getProperty("tm.url")); 
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); 
	    httpURLConnection.setRequestMethod("HEAD");
	    httpURLConnection.connect(); 
		Assert.assertEquals(httpURLConnection.getResponseCode(), HttpURLConnection.HTTP_OK);
	}
}