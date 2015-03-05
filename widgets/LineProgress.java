package com.example.test2.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 线进度条
 * */
public class LineProgress extends View {

	int mHeight;			//控件高度
	int totalWidth;			//总共长度
	int maxCompleteWidth;	//完成长度最大值
	int completeWidth;		//完成长度
	int textWidth=30;		//字体显示宽度
	float completePercent;	//完成百分比

	Paint unCompletePaint;	//未完成画笔
	Paint completePaint;	//已完成画笔
	Paint textPaint;		//字体画笔

	Rect completeRect = new Rect();		//完成区域
	Rect unCompleteRect = new Rect();	//未完成区域

	String percentText="0%";		//百分比内容

	final int TEXT_PADDING = 4;	//显示字体左右padding大小
	final int MIN_HEIGHT = 11;		//最低高度
	final int LINE_HEIGHT = 3;		//线的高度


	public LineProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	@Override
	protected int getSuggestedMinimumHeight() {
		int minHeight = Math.max(MIN_HEIGHT, LINE_HEIGHT);
		int textHeight = measureTextHeight();
		minHeight = Math.max(minHeight, textHeight);
		return minHeight;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		totalWidth = measureSpec(widthMeasureSpec, true);
		mHeight = measureSpec(heightMeasureSpec,false);
		setMeasuredDimension(totalWidth, mHeight);

		init();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//super.onDraw(canvas);

		boolean isAllCompleteLine = completeWidth > maxCompleteWidth;
		int cWidth = isAllCompleteLine?maxCompleteWidth:completeWidth;

		//完成部分
		completeRect.set(getPaddingLeft(), 
				getDrawTopY(LINE_HEIGHT), 
				cWidth, 
				getDrawBottomY(LINE_HEIGHT));
		canvas.drawRect(completeRect,completePaint);

		//字体部分
		FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();  
		int baseline = (mHeight - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top; 
		canvas.drawText(percentText,  cWidth + TEXT_PADDING , baseline, textPaint);

		//未完成部分

		if(!isAllCompleteLine){
			unCompleteRect.set(completeWidth + textWidth + TEXT_PADDING*2, 
					getDrawTopY(LINE_HEIGHT), 
					totalWidth, 
					getDrawBottomY(LINE_HEIGHT));
			canvas.drawRect(unCompleteRect,	unCompletePaint);
		}


	}

	/**
	 * 初始化画笔
	 * */
	private void initPaint(){
		completePaint = new Paint();
		completePaint.setARGB(255, 0, 255, 0);
		completePaint.setStrokeWidth(4);

		unCompletePaint = new Paint();
		unCompletePaint.setARGB(255, 255, 0, 0);
		unCompletePaint.setStrokeWidth(4);

		textPaint = new Paint();
		textPaint.setARGB(255, 0, 0, 255);
		textPaint.setTextSize(24);

	}
	/**
	 * 初始化
	 * */
	private void init(){
		
		textWidth = measureTextSize("000%");

		maxCompleteWidth = totalWidth - textWidth - TEXT_PADDING*2;

	}
	/**
	 * 计算使得垂直居中的上Y坐标
	 * */
	private int getDrawTopY(int objHeight){
		return (mHeight-objHeight)/2;
	}
	/**
	 * 计算使得垂直居中的下Y坐标
	 * */
	private int getDrawBottomY(int objHeight){
		return mHeight/2;
	}

	/**
	 * 计算字体所占宽度
	 * */
	private int measureTextSize(String text){
		float width = textPaint.measureText(text);

		return Math.round(width);
	}
	/**
	 * 计算字体高度
	 * */
	private int measureTextHeight(){
		FontMetrics fm = textPaint.getFontMetrics();  
		return (int) Math.ceil(fm.descent - fm.ascent);
	}
	/**
	 * 测量
	 * */
	private int measureSpec(int measureSpec,boolean isWidth){
		int result = 0;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int padding = isWidth?getPaddingLeft()+getPaddingRight():getPaddingTop()+getPaddingBottom();

		if(mode == MeasureSpec.EXACTLY){
			result = size;
		}else {
			result = isWidth?getSuggestedMinimumWidth():getSuggestedMinimumHeight();
			result += padding;
			if(mode == MeasureSpec.AT_MOST){
				result = isWidth?Math.max(result, size):Math.min(result, size);
			}
		}

		return result;
	}

	public float getCompletePercent() {
		return completePercent;
	}
	/**
	 * 设置当前百分比
	 * */
	public void setCompletePercent(float completePercent) {

		if(completePercent>100 || completePercent<0){
			throw new IllegalArgumentException("百分比在0到100区间");
		}
		this.completePercent = completePercent;

		completeWidth = Math.round(totalWidth * (completePercent /100));

		percentText = Math.round(completePercent) + "%";

		invalidate();
	}

}
