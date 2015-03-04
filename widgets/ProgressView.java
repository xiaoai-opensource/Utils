package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class ProgressView extends TextView {

	private int start=0;
	public ProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				start+=10;
				invalidate();
			}
		}, 3000);
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


		Paint paint = new Paint();
		paint.setStyle(Style.STROKE); 
		paint.setStrokeWidth(4);
		paint.setARGB(200, 127, 255, 212);
		RectF oval = new RectF(0,0,200,200);

		canvas.drawArc(oval, 180+start, 90, false, paint);
		canvas.drawArc(oval, 0+start, 90, false, paint);


		paint.setARGB(30, 127, 255, 212);
		canvas.drawArc(oval, 90+start, 90, false, paint);
		canvas.drawArc(oval, 270+start, 90, false, paint);
		canvas.save();
	}

}
