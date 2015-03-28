package cn.bitlove.test.asyn;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * ���߳�����
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
	 * @param url ���ص�ַ
	 * @param filePath �����ļ�·��
	 * @param begin ���ݿ�ʼλ��
	 * @param length �������ݵĳ���
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
