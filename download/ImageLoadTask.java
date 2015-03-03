package com.example.test2.download;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;

/**
 * Õº∆¨œ¬‘ÿ
 * */
public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {

	private IOnPost mOnPost;
	String url = "";
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		Bitmap bm = null;
		if(urls.length>0){
			url = urls[0];
			ImageDownloader downloader = new ImageDownloader();
			bm = downloader.download(url);			
			
		}

		return bm;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		if(mOnPost!=null){
			mOnPost.onPostExecute(url,result);
		}

	}
	
	public void setOnPost(IOnPost onPost){
		mOnPost = onPost;
	}
	
	public interface IOnPost{
		public void onPostExecute(String url,Bitmap result);
	}

}
