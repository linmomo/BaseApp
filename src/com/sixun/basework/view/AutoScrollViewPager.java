package com.sixun.basework.view;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Interpolator;

/**
 * 自动滚动 View Pager
 * <ul>
 * <strong>基本设置和用法</strong>
 * <li>{@link #startAutoScroll()} 开始自动滚动, 或者 {@link #startAutoScroll(int)} 开始定时滚动</li>
 * <li>{@link #stopAutoScroll()} 停止自动滚动</li>
 * <li>{@link #setInterval(long)} 设置自动滚动时间, 默认时间 {@link #DEFAULT_INTERVAL}</li>
 * </ul>
 * <ul>
 * <strong>高级设置和用法</strong>
 * <li>{@link #setDirection(int)} 设置自动滚动方向</li>
 * 
 * <li>{@link #setCycle(boolean)} 设置自动循环时是否自动滚动到最后一个或第一个，默认true</li>
 * <li>{@link #setSlideBorderMode(int)} 设置滑动到最后一项或第一项时如何处理</li>
 * <li>{@link #setStopScrollWhenTouch(boolean)} 设置是否触摸时停止滚动, 默认true</li>
 * </ul>
 * 
 */
public class AutoScrollViewPager extends ViewPager {

	/**
	 * 默认间隔
	 */
    public static final int        DEFAULT_INTERVAL            = 1500;

    public static final int        LEFT                        = 0;
    public static final int        RIGHT                       = 1;

    /** 当滑动到最后一项或第一项时什么也不做 **/
    public static final int        SLIDE_BORDER_MODE_NONE      = 0;
    /** 当滑动到最后一项或第一项时循环 **/
    public static final int        SLIDE_BORDER_MODE_CYCLE     = 1;
    /** 当滑动到最后一项或第一项时处理父控件的事件 **/
    public static final int        SLIDE_BORDER_MODE_TO_PARENT = 2;

    /** 自动滚动时间, 默认 {@link #DEFAULT_INTERVAL} **/
    private long                   interval                    = DEFAULT_INTERVAL;
    /** 自动滚动方向, 默认 {@link #RIGHT} **/
    private int                    direction                   = RIGHT;
    /** 自动滚动到最后一项或第一项时，是否自动循环, 默认 true **/
    private boolean                isCycle                     = true;
    /** 触摸时是否停止自动滚动, 默认true **/
    private boolean                stopScrollWhenTouch         = true;
    /** 当滑动到最后一项或第一项时如何处理, 默认 {@link #SLIDE_BORDER_MODE_NONE} **/
    private int                    slideBorderMode             = SLIDE_BORDER_MODE_NONE;
    /** 当自动滚动到第一项或最后一项是是否动画  默认true**/
    private boolean                isBorderAnimation           = true;
    /** 自动滚动时动画因素, 默认 1.0 **/
    private double                 autoScrollFactor            = 1.0;
    /** 滑动时滚动动画因素, 默认 1.0 **/
    private double                 swipeScrollFactor           = 1.0;

    private Handler                handler;
    private boolean                isAutoScroll                = false;
    private boolean                isStopByTouch               = false;
    private float                  touchX                      = 0f, downX = 0f;
    private CustomDurationScroller scroller                    = null;

    public static final int        SCROLL_WHAT                 = 0;

    public AutoScrollViewPager(Context paramContext) {
        super(paramContext);
        init();
    }

    public AutoScrollViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        setViewPagerScroller();
    }

    /**
     * 开始自动滚动, 第一次滚动时间是 {@link #getInterval()}
     */
    public void startAutoScroll() {
        isAutoScroll = true;
        sendScrollMessage((long)(interval + scroller.getDuration() / autoScrollFactor * swipeScrollFactor));
    }

    /**
     * 开始自动滚动
     * 
     * @param delayTimeInMills 第一次滚动延迟时间
     */
    public void startAutoScroll(int delayTimeInMills) {
        isAutoScroll = true;
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * 停止自动滚动
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        handler.removeMessages(SCROLL_WHAT);
    }

    /**
     * 设置滑动时滑动动画的持续时间的因素
     */
    public void setSwipeScrollDurationFactor(double scrollFactor) {
        swipeScrollFactor = scrollFactor;
    }

    /**
     * 设置自动滚动时滑动动画的持续时间的因素
     */
    public void setAutoScrollDurationFactor(double scrollFactor) {
        autoScrollFactor = scrollFactor;
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** 删除之前消息,保留一份正在运行的消息 **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    /**
     * 设置ViewPager滑动时滚动动画时长
     */
    private void setViewPagerScroller() {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            interpolatorField.setAccessible(true);

            scroller = new CustomDurationScroller(getContext(), (Interpolator)interpolatorField.get(null));
            scrollerField.set(this, scroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 滚动一次
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = (direction == LEFT) ? --currentItem : ++currentItem;
        if (nextItem < 0) {
            if (isCycle) {
                setCurrentItem(totalCount - 1, isBorderAnimation);
            }
        } else if (nextItem == totalCount) {
            if (isCycle) {
                setCurrentItem(0, isBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem, true);
        }
    }

    /**
     * <ul>
     * 如果 stopScrollWhenTouch 为true,触摸时停止滚动
     * <li>如果触摸事件是down, 停止自动滚动</li>
     * <li>如果触摸事件是up, 继续自动滚动</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);

        if (stopScrollWhenTouch) {
            if ((action == MotionEvent.ACTION_DOWN) && isAutoScroll) {
                isStopByTouch = true;
                stopAutoScroll();
            } else if (ev.getAction() == MotionEvent.ACTION_UP && isStopByTouch) {
                startAutoScroll();
            }
        }

        if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT || slideBorderMode == SLIDE_BORDER_MODE_CYCLE) {
            touchX = ev.getX();
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                downX = touchX;
            }
            int currentItem = getCurrentItem();
            PagerAdapter adapter = getAdapter();
            int pageCount = adapter == null ? 0 : adapter.getCount();
            /**
             * current index is first one and slide to right or current index is last one and slide to left.<br/>
             * if slide border mode is to parent, then requestDisallowInterceptTouchEvent false.<br/>
             * else scroll to last one when current item is first one, scroll to first one when current item is last one.
             */
            if ((currentItem == 0 && downX <= touchX) || (currentItem == pageCount - 1 && downX >= touchX)) {
                if (slideBorderMode == SLIDE_BORDER_MODE_TO_PARENT) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    if (pageCount > 1) {
                        setCurrentItem(pageCount - currentItem - 1, isBorderAnimation);
                    }
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                return super.dispatchTouchEvent(ev);
            }
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    private static class MyHandler extends Handler {

        private final WeakReference<AutoScrollViewPager> autoScrollViewPager;

        public MyHandler(AutoScrollViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<AutoScrollViewPager>(autoScrollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    AutoScrollViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scroller.setScrollDurationFactor(pager.autoScrollFactor);
                        pager.scrollOnce();
                        pager.scroller.setScrollDurationFactor(pager.swipeScrollFactor);
                        pager.sendScrollMessage(pager.interval + pager.scroller.getDuration());
                    }
                default:
                    break;
            }
        }
    }

    /**
     * 获得自动滚动时间，默认{@link #DEFAULT_INTERVAL}
     * 
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * 设置自动滚动时间, 默认 {@link #DEFAULT_INTERVAL}
     * 
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * 获得自动滚动方向
     * 
     * @return {@link #LEFT} or {@link #RIGHT}, 默认 {@link #RIGHT}
     */
    public int getDirection() {
        return (direction == LEFT) ? LEFT : RIGHT;
    }

    /**
     * 设置自动滚动方向
     * 
     * @param 方向 {@link #LEFT} or {@link #RIGHT}, 默认 {@link #RIGHT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    /**
     * whether automatic cycle when auto scroll reaching the last or first item, default is true
     * 获得当自动滚动到最后一项或第一项时是否自动循环
     * 
     * @return the isCycle
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * set whether automatic cycle when auto scroll reaching the last or first item, 默认 true
     * 设置自动滚动最后一项或第一项时是否自动循环, 默认 true
     * 
     * @param isCycle the isCycle to set
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 获得触摸时是否停止自动滚动, 默认 true
     * 
     * @return the stopScrollWhenTouch
     */
    public boolean isStopScrollWhenTouch() {
        return stopScrollWhenTouch;
    }

    /**
     * 设置触摸时是否停止自动滚动, 默认 true
     * 
     * @param stopScrollWhenTouch
     */
    public void setStopScrollWhenTouch(boolean stopScrollWhenTouch) {
        this.stopScrollWhenTouch = stopScrollWhenTouch;
    }

    /**
     * 获得当滑动到第一项或最后一项时如何处理
     * 
     * @return the slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *         {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    public int getSlideBorderMode() {
        return slideBorderMode;
    }

    /**
     * 设置当滑动到第一项或最后一项时如何处理
     * 
     * @param slideBorderMode {@link #SLIDE_BORDER_MODE_NONE}, {@link #SLIDE_BORDER_MODE_TO_PARENT},
     *        {@link #SLIDE_BORDER_MODE_CYCLE}, default is {@link #SLIDE_BORDER_MODE_NONE}
     */
    public void setSlideBorderMode(int slideBorderMode) {
        this.slideBorderMode = slideBorderMode;
    }

    /**
     * whether animating when auto scroll at the last or first item, default is true
     * 获得当自动滚动到最后一项或第一项时是否有动画，默认ture
     * 
     * @return
     */
    public boolean isBorderAnimation() {
        return isBorderAnimation;
    }

    /**
     * set whether animating when auto scroll at the last or first item, default is true
     * 设置当自动滚动到最后一项或第一项时是否有动画，默认ture
     * 
     * @param isBorderAnimation
     */
    public void setBorderAnimation(boolean isBorderAnimation) {
        this.isBorderAnimation = isBorderAnimation;
    }
}
