package com.example.test2.download;

import java.util.ArrayList;
import java.util.List;

import com.example.test2.download.ImageLoadTask.IOnPost;

import android.graphics.Bitmap;

public class DownloadPool {
	private DownloadPool(){};
	private static DownloadPool mDownloadPool = new DownloadPool();
	//待执行列表
	private List<ImageLoadTask> toDownLoadTasks=new ArrayList<ImageLoadTask>();
	//最大执行数量
	private final int MAX_EXC_COUNT=3;
	//正在执行的数量
	private int excCount=0;


	public static DownloadPool getInstance(){
		return mDownloadPool;
	}
	public synchronized void  addTask(ImageLoadTask task){
		toDownLoadTasks.add(task);
	}

	public void excTasks(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("toDownLoadTasks.size() 111= " + toDownLoadTasks.size() +" excCount :"+excCount);
				for(int i=excCount;i<MAX_EXC_COUNT;i++){
					if(toDownLoadTasks.size()>0){
						ImageLoadTask task = toDownLoadTasks.get(0);
						toDownLoadTasks.remove(0);
						task.addOnPostListener(new IOnPost() {

							@Override
							public void onPostExecute(String url, Bitmap result) {
								excCount--;
								System.out.println("exc done");
								/*if(excCount<MAX_EXC_COUNT){
										this.notify();
									}*/

								if(toDownLoadTasks.size()>0){
									excTasks();
								}
							}
						});

						task.execute(task.getUrls());
						excCount++;
						//this.notify();
					}
				}
				System.out.println("toDownLoadTasks.size() 22= " + toDownLoadTasks.size());
			}

			//}
		}).run();


	}

}
