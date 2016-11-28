package it.carlom.stikkyheader.core;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThomasWang on 2016/11/28.
 */

public class StikkyHeaderRootLayout extends FrameLayout {
    private List<View> mClickCandidates = new ArrayList<>();

    private ListView mScrollableView;

    private GestureDetectorCompat mGestureDetectorCompat;

    private ListViewAutoScrollHelper mScrollHelper;

    public StikkyHeaderRootLayout(Context context) {
        this(context, null, 0);
    }

    public StikkyHeaderRootLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StikkyHeaderRootLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetectorCompat = new GestureDetectorCompat(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return resolveClick(e);
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mScrollableView.scrollListBy((int) distanceY);
                } else {
                    mScrollHelper.scrollTargetBy(0, (int) distanceY);
                }
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    mScrollableView.fling((int) -velocityY);
                } else {
                }
                return false;
            }
        });
    }

    private boolean resolveClick(MotionEvent e) {
        for (View candidate : mClickCandidates) {
            Rect outRect = new Rect();
            candidate.getHitRect(outRect);
            if (outRect.contains((int) e.getX(), (int) e.getY())) {
                return candidate.performClick();
            }
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mGestureDetectorCompat.onTouchEvent(ev);
    }

    public void setScrollableView(ListView scrollView) {
        this.mScrollableView = scrollView;
        mScrollHelper = new ListViewAutoScrollHelper(mScrollableView);
    }

    public void addClickCandidates(View candidate) {
        if (candidate instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) candidate;
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                addClickCandidates(parent.getChildAt(i));
            }
        } else {
            candidate.setEnabled(false);
            mClickCandidates.add(candidate);
        }
    }
}
