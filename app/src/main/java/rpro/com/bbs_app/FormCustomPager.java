package rpro.com.bbs_app;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Richie on 03/01/2017.
 */
public class FormCustomPager extends ViewPager {

    public FormCustomPager(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }
}
