package com.example.saudm.auditory_menu_navigation.activities.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.saudm.auditory_menu_navigation.R;

import java.util.ArrayList;

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private ArrayList<String> dropdownItems;

    public CustomSpinnerAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.dropdownItems = list;
    }

    @Override
    public int getCount() {
        return dropdownItems.size();
    }

    @Override
    public String getItem(int position) {
        return dropdownItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.custom_dropdown_item, null);
        final TextView textView = view.findViewById(R.id.dropDownTextView);
        textView.setText(dropdownItems.get(position));
        return textView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.custom_dropdown_item, null);
        final TextView textView = view.findViewById(R.id.dropDownTextView);
        textView.setText(dropdownItems.get(position));
        textView.setPadding(10,10,10,10);
        return view;
    }
}

