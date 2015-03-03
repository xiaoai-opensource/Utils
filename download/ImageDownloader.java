package com.example.test2.download;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.util.Log;

public class ImageDownloader implements Downloader {

	@Override
	public Bitmap download(String url) {

		AndroidHttpClient client = AndroidHttpClient.newInstance("ImageDownloader");
		HttpGet getRequest = new HttpGet(url);  

		try {  
			HttpResponse response = client.execute(getRequest);  
			final int statusCode = response.getStatusLine().getStatusCode();  
			if (statusCode != HttpStatus.SC_OK) {   
				Log.w("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);   
				return null;  
			}  

			final HttpEntity entity = response.getEntity();  
			if (entity != null) {  
				InputStream inputStream = null;  
				try {  
					inputStream = entity.getContent();   
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);  
					return bitmap;  
				} finally {  
					if (inputStream != null) {  
						inputStream.close();    
					}  
					entity.consumeContent();  
				}  
			}  
		} catch (Exception e) {  
			// Could provide a more explicit error message for IOException or IllegalStateException  
			getRequest.abort();  
			Log.w("ImageDownloader", "Error while retrieving bitmap from " + url + e.toString());  
		} finally {  
			if (client != null) {  
				client.close();  
			}  
		}  

		return null;
	}

}
