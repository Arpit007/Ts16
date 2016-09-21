package com.nitkkr.gawds.ts16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class httpRequest
{
	public String SendGetRequest(String requestURL)
	{
		StringBuilder stringBuilder=new StringBuilder();
		try {
			URL url=new URL(requestURL);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line="";
			while((line=reader.readLine())!=null)
			{
				stringBuilder.append(line+"\n");
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}
}
