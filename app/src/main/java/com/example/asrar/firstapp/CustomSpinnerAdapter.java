package com.example.asrar.firstapp;

/**
 * Created by asrar on 12/18/2014.
 */
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;


public class CustomSpinnerAdapter extends BaseAdapter {
    Context context;
    List<String> rowItems;
    static SparseBooleanArray mCheckStates = new SparseBooleanArray();

    public CustomSpinnerAdapter(Context context, List<String> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtTitle;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_main_spinner_item, null);

            //convertView.setTag(holder);
        }
        else {
            //holder = (ViewHolder) convertView.getTag();
        }

        holder = new ViewHolder();
        holder.txtTitle = (TextView) convertView.findViewById(R.id.title);

        String rowItem = (String) getItem(position);

        holder.txtTitle.setText(rowItem);

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

}