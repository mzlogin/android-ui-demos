package org.mazhuang.androiduidemos.view;

import android.content.Context;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * ref https://github.com/ittianyu/BottomNavigationViewEx/blob/master/widget/src/main/java/com/ittianyu/bottomnavigationviewex/BottomNavigationViewEx.java
 *
 * @author mazhuang
 * @date 2018/9/16
 */
public class CustomBottomNavigationView extends BottomNavigationView {

    private BottomNavigationMenuView mMenuView;
    private BottomNavigationItemView[] mButtons;

    public CustomBottomNavigationView(Context context) {
        super(context);
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * get private filed in this specific object
     *
     * @param targetClass
     * @param instance    the filed owner
     * @param fieldName
     * @param <T>
     * @return field if success, null otherwise.
     */
    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * get private mMenuView
     *
     * @return
     */
    private BottomNavigationMenuView getBottomNavigationMenuView() {
        if (null == mMenuView) {
            mMenuView = getField(BottomNavigationView.class, this, "mMenuView");
        }
        return mMenuView;
    }

    /**
     * get private mButtons in mMenuView
     *
     * @return
     */
    public BottomNavigationItemView[] getBottomNavigationItemViews() {
        if (null != mButtons) {
            return mButtons;
        }
        /*
         * 1 private final BottomNavigationMenuView mMenuView;
         * 2 private BottomNavigationItemView[] mButtons;
         */
        BottomNavigationMenuView mMenuView = getBottomNavigationMenuView();
        mButtons = getField(mMenuView.getClass(), mMenuView, "mButtons");
        return mButtons;
    }

    /**
     * get private mButton in mMenuView at position
     *
     * @param position
     * @return
     */
    public BottomNavigationItemView getBottomNavigationItemView(int position) {
        return getBottomNavigationItemViews()[position];
    }
}
