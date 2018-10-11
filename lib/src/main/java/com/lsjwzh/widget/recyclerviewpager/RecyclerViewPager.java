package com.lsjwzh.widget.recyclerviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerViewPager
 *
 * @author Green
 */
public class RecyclerViewPager extends RecyclerView {
	public static final boolean DEBUG = BuildConfig.DEBUG;
	
	private static final String TAG = RecyclerViewPager.class.getSimpleName();
	
	private RecyclerViewPagerAdapter<?> mViewPagerAdapter;
	private List<OnPageChangedListener> mOnPageChangedListeners;
	private int mSmoothScrollTargetPosition = -1;
	private int mPositionBeforeScroll = -1;
	
	public RecyclerViewPager(Context context) {
		this(context, null);
	}
	
	public RecyclerViewPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	
	public RecyclerViewPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttrs(context, attrs, defStyle);
		setNestedScrollingEnabled(false);
		
		PagerSnapHelper pagerSnapHelper = new PagerSnapHelper(){};
		pagerSnapHelper.attachToRecyclerView(this);
	}
	
	private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
		final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewPager, defStyle,
				0);
		a.recycle();
	}
	
	
	@Override
	public void setAdapter(Adapter adapter) {
		mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
		super.setAdapter(mViewPagerAdapter);
	}
	
	@Override
	public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
		mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
		super.swapAdapter(mViewPagerAdapter, removeAndRecycleExistingViews);
	}
	
	@Override
	public Adapter getAdapter() {
		if (mViewPagerAdapter != null) {
			return mViewPagerAdapter.mAdapter;
		}
		return null;
	}
	
	public RecyclerViewPagerAdapter getWrapperAdapter() {
		return mViewPagerAdapter;
	}
	
	@Override
	public void scrollToPosition(int position) {
		if (DEBUG) {
			Log.d(TAG + ":scrollToPosition", "scrollToPosition:" + position);
		}
		mPositionBeforeScroll = getCurrentPosition();
		mSmoothScrollTargetPosition = position;
		super.scrollToPosition(position);
		
		getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				if (Build.VERSION.SDK_INT < 16) {
					getViewTreeObserver().removeGlobalOnLayoutListener(this);
				} else {
					getViewTreeObserver().removeOnGlobalLayoutListener(this);
				}
				
				if (mSmoothScrollTargetPosition >= 0 && mSmoothScrollTargetPosition < getItemCount()) {
					if (mOnPageChangedListeners != null) {
						for (OnPageChangedListener onPageChangedListener : mOnPageChangedListeners) {
							if (onPageChangedListener != null) {
								onPageChangedListener.OnPageChanged(mPositionBeforeScroll, getCurrentPosition());
							}
						}
					}
				}
			}
		});
	}
	
	private int getItemCount() {
		return mViewPagerAdapter == null ? 0 : mViewPagerAdapter.getItemCount();
	}
	
	/**
	 * get item position in center of viewpager
	 */
	public int getCurrentPosition() {
		int curPosition;
		if (getLayoutManager().canScrollHorizontally()) {
			curPosition = ViewUtils.getCenterXChildPosition(this);
		} else {
			curPosition = ViewUtils.getCenterYChildPosition(this);
		}
		if (curPosition < 0) {
			curPosition = mSmoothScrollTargetPosition;
		}
		return curPosition;
	}
	
	public void addOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		if (mOnPageChangedListeners == null) {
			mOnPageChangedListeners = new ArrayList<>();
		}
		mOnPageChangedListeners.add(listener);
	}
	
	@SuppressWarnings("unused")
	public void removeOnPageChangedListener(@NonNull OnPageChangedListener listener) {
		if (mOnPageChangedListeners != null) {
			mOnPageChangedListeners.remove(listener);
		}
	}
	
	@SuppressWarnings("unused")
	public void clearOnPageChangedListeners() {
		if (mOnPageChangedListeners != null) {
			mOnPageChangedListeners.clear();
		}
	}
	
	@SuppressWarnings("unchecked")
	@NonNull
	protected RecyclerViewPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
		return (adapter instanceof RecyclerViewPagerAdapter)
				? (RecyclerViewPagerAdapter) adapter
				: new RecyclerViewPagerAdapter(this, adapter);
		
	}
	
	public interface OnPageChangedListener {
		/**
		 * Fires when viewpager changes it's page
		 *
		 * @param oldPosition old position
		 * @param newPosition new position
		 */
		void OnPageChanged(int oldPosition, int newPosition);
	}
	
	
}
