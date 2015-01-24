package com.example.asrar.firstapp;

/**
 * Created by asrar on 12/16/2014.
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


public class CustomBaseAdapter extends BaseAdapter {
    Context context;
    List<RowItem> rowItems;
    static SparseBooleanArray mCheckStates = new SparseBooleanArray();

    public CustomBaseAdapter(Context context, List<RowItem> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
        CheckBox checkBox;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_image_item, null);

            //convertView.setTag(holder);
        }
        else {
            //holder = (ViewHolder) convertView.getTag();
        }

        holder = new ViewHolder();
        holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
        holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

        // If CheckBox is toggled, update the planet it is tagged with.
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                mCheckStates.put(getPosition, isChecked);
            }
        });

        RowItem rowItem = (RowItem) getItem(position);

        holder.checkBox.setTag(position);
        holder.checkBox.setChecked(mCheckStates.get(position,false));
        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

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

    public boolean isChecked(int position) {
        return mCheckStates.get(position, false);
    }

    public void setChecked(int position, boolean isChecked) {
        mCheckStates.put(position, isChecked);

    }

    public void toggle(int position) {
        setChecked(position, !isChecked(position));

    }
}