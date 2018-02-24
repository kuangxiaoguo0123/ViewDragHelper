package com.spriteapp.viewdragexample;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by kuangxiaoguo on 2018/2/24.
 */

public class ViewDragLayout extends LinearLayout {

    private static final String TAG = "ViewDragLayout-";
    private ViewDragHelper mViewDragHelper;
    private Point mAutoBackPoint;

    public ViewDragLayout(Context context) {
        this(context, null);
    }

    public ViewDragLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragCallback());
    }

    class DragCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            if (mAutoBackPoint == null) {
                mAutoBackPoint = new Point(child.getLeft(), child.getTop());
            }
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mAutoBackPoint != null) {
                mViewDragHelper.settleCapturedViewAt(mAutoBackPoint.x, mAutoBackPoint.y);
                invalidate();
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
