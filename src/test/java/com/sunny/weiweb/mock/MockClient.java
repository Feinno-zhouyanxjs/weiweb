/**
 * 
 */
package com.sunny.weiweb.mock;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * 伪造客户端请求
 * 
 * Create on Feb 7, 2014 11:46:59 PM
 * 
 * @author <a href="mailto:zhouyan@pzoomtech.com">ZhouYan</a>.
 * 
 */
public class MockClient {

	/**
	 * @param args
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://localhost:8080/weiweb/Core.do");
		httpPost.addHeader("Content-Type", "text/html;charset=utf-8");
		String reqStr = readFile(new FileReader(new File("F:/request.txt")));
		HttpEntity content = new StringEntity(reqStr, "utf-8");
		httpPost.setEntity(content);
		CloseableHttpResponse response = httpclient.execute(httpPost);
		HttpEntity responseEntity = response.getEntity();
		InputStream in = responseEntity.getContent();

		InputStreamReader inr = new InputStreamReader(in);
		String rs = readFile(inr);
		System.out.println(rs);

		EntityUtils.consume(responseEntity);
		response.close();
	}

	private static String readFile(Reader reader) throws IOException {
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		try {
			br = new BufferedReader(reader);
			String line = null;
			while ((line = br.readLine()) != null)
				sb.append(line);
		} finally {
			br.close();
			reader.close();
		}
		return sb.toString();
	}

}
