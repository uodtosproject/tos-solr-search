package br.com.betohayasida.SolrSearch.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
 
/**
 * Client for REST calls
 * @author rkhayasidajunior
 *
 */
public class ApacheHttpClientPost {
 
	public String post(String body) {
		String output = "";
 
	  try {
 
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost("http://localhost:8080/tos/crawl/");
 
		StringEntity input = new StringEntity(body);
		input.setContentType("text/plain");
		postRequest.setEntity(input);
 
		HttpResponse response = httpClient.execute(postRequest);
 
		BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
 
		String buf;
		while ((buf = br.readLine()) != null) {
			output = output.concat(buf);
		}
 
		httpClient.getConnectionManager().shutdown();
 
	  } catch (MalformedURLException e) {
 
		e.printStackTrace();
 
	  } catch (IOException e) {
 
		e.printStackTrace();
 
	  }
	  
	  return output;
 
	}
 
}