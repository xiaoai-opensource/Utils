package cn.bitlove.test.asyn;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 多线程下载
 * */
public class DownloadThread extends Thread {
	private RandomAccessFile mFile;
	private URL mUrl;
	private HttpURLConnection mConnection;
	private int mBegin;
	private int mLength;
	private String mFilePath;
	DefaultHttpClient client = new DefaultHttpClient();
	/**
	 * @param url 下载地址
	 * @param filePath 本地文件路径
	 * @param begin 数据开始位置
	 * @param length 下载数据的长度
	 * */
	public DownloadThread(URL url,String filePath,int begin,int length){
		mUrl = url;
		mBegin = begin;
		mLength = length;
		mFilePath = filePath;
	}
	@Override
	public void run() {

		try {
			mConnection = (HttpURLConnection) mUrl.openConnection();

			mConnection.setRequestProperty("RANGE", "bytes=" + mBegin+ "-" +(mBegin+mLength));

			InputStream is;

			is = mConnection.getInputStream();

			mFile = new RandomAccessFile(mFilePath, "rw");
			mFile.seek(mBegin);
			byte[] bytes = new byte[10240];
			int len;
			while((len = is.read(bytes))>0){
				mFile.write(bytes,0,len);
			}

			mFile.close();
			is.close();
			System.out.println(" thread "+ this.getId() + " done ...");
		} catch (IOException e) {
			e.printStackTrace();
		}


		super.run();
	}
}
