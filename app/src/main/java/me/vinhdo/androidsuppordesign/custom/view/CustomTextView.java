package me.vinhdo.androidsuppordesign.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import me.vinhdo.androidsuppordesign.R;
import me.vinhdo.androidsuppordesign.listeners.OnDrawableClickListener;


/**
 * CustomTextView
 *
 * @author khanhnv
 * @link http://developer.android.com/training/custom-views/create-view.html
 */
public class CustomTextView extends TextView {

    private OnDrawableClickListener mListener;

    public CustomTextView(Context context) {
        super(context);
        initViews();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setCustomFont(context, attrs);
        initViews();
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setCustomFont(context, attrs);
        initViews();
    }

    private void initViews() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                try {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getRawX() >= (getRight() - getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width() - getPaddingRight())) {
                            if (mListener != null) {
                                mListener.onRightClick(CustomTextView.this);
                                return true;
                            }
                        }
                    }
                } catch (Exception ex) {

                }
                return false;
            }
        });
    }

    public void setOnDrawableClickListener(OnDrawableClickListener listener) {
        this.mListener = listener;
    }

    /**
     * @param ctx
     * @param attrs
     */
    private void setCustomFont(Context ctx, AttributeSet attrs) {

        if (!isInEditMode()) {
            TypedArray a = ctx.obtainStyledAttributes(attrs,
                    R.styleable.CustomView);

            // get font name
            String nameOfFont = a.getString(R.styleable.CustomView_font);

            // set default name of font
            if (nameOfFont == null) {
                nameOfFont = getResources().getString(R.string.font_regular);
            }

            setCustomFont(ctx, nameOfFont);

            a.recycle();
        }
    }

    /**
     * set custom font for text view
     *
     * @param ctx
     * @param nameOfFont
     * @return
     */
    public boolean setCustomFont(Context ctx, String nameOfFont) {

        Typeface typeface = loadFont(ctx, "fonts/" + nameOfFont);

        if (typeface == null) {
            return false;
        }

        setTypeface(typeface);

        return true;
    }

    /**
     * Load font
     *
     * @param context
     * @param pathOfFont
     * @return
     */
    private Typeface loadFont(Context context, String pathOfFont) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), pathOfFont);
        } catch (Exception e) {
            Log.e(this.getClass().getName(),"Could not get typeface: " + e.getMessage());
        }
        return tf;
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

}