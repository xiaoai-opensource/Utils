package cn.bitlove.test.asyn;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ̽��ָ��URL�ļ���С����ͨ���ص��ӿڷ���
 * */
public class DetectFileSizeThread extends Thread {
	IDoneListener mIDoneListener;
	URL mUrl;
	
	public DetectFileSizeThread(URL url){
		mUrl = url;
	}
	public void setIDnoeListener(IDoneListener listener){
		mIDoneListener = listener;
	}
	@Override
	public void run() {
		int size = -1;
		try {
			HttpURLConnection  mConnection = (HttpURLConnection) mUrl.openConnection();
			mConnection.connect();
			size = mConnection.getContentLength();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			mIDoneListener.done(size);
		}

		super.run();
	}

	public interface IDoneListener{
		public int done(int size);
	}
}
