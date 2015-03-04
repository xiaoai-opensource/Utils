package com.example.test2.download;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * 异步下载任务
 * */
public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {

	private List<IOnPost> onPostList = new ArrayList<ImageLoadTask.IOnPost>();
	private String urls[];
	
	
	public String[] getUrls() {
		return urls;
	}

	public void setUrls(String... urls) {
		this.urls = urls;
	}

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
			for(int i=0;i<onPostList.size();i++){
				onPostList.get(i).onPostExecute(url,result);
				
			}

	}
	
	public void addOnPostListener(IOnPost onPost){
		onPostList.add(onPost);
	}
	
	/**
	 * 下载完成后的调用接口
	 * */
	public interface IOnPost{
		public void onPostExecute(String url,Bitmap result);
	}

}
