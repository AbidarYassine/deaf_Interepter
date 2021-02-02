package com.fstg.deafinterepter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fstg.deafinterepter.R;
import com.fstg.deafinterepter.entities.Tutor;
import com.fstg.deafinterepter.ui.InfoTutorActivity;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Tutor> tutors;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<Tutor> tutors, Context context) {
        this.tutors = tutors;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tutors.size();
    }

    @Override
    public Object getItem(int position) {
        return tutors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.customelist_view, parent, false);
            holder = new ViewHolder();
            holder.tv_full_name = (TextView) convertView.findViewById(R.id.tv_full_name);
            holder.btn_selet = (Button) convertView.findViewById(R.id.btn_selet);
            holder.btn_selet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), InfoTutorActivity.class);
                    intent.putExtra("tutor", tutors.get(position));
//                    intent.putExtra("user", student);
                    parent.getContext().startActivity(intent);
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Car car = this.cars.get(position);
        Tutor tutor = this.tutors.get(position);
        holder.tv_full_name.setText(tutor.getFirstName() + " " + tutor.getLastName());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_full_name;
        Button btn_selet;
    }
}
