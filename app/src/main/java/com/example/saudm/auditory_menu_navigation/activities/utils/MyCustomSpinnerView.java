package com.example.saudm.auditory_menu_navigation.activities.utils;

import android.content.Context;
import android.util.AttributeSet;

public class MyCustomSpinnerView extends android.support.v7.widget.AppCompatSpinner {

    public MyCustomSpinnerView(Context context) {
        super(context);
    }

    public MyCustomSpinnerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomSpinnerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void
    setSelection(int position, boolean animate) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position, animate);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }

    @Override
    public void
    setSelection(int position) {
        boolean sameSelected = position == getSelectedItemPosition();
        super.setSelection(position);
        if (sameSelected) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            getOnItemSelectedListener().onItemSelected(this, getSelectedView(), position, getSelectedItemId());
        }
    }
}
