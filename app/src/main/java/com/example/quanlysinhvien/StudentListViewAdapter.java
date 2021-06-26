package com.example.quanlysinhvien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentListViewAdapter extends BaseAdapter {
    private List<Student> students;
    private LayoutInflater layoutInflater;
    private Context context;
    public StudentListViewAdapter() {
    }

    public StudentListViewAdapter(Context context, List<Student> students) {
        this.students = students;
        this.context = context;
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.student_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.txtViewSName);
            viewHolder.tvId = (TextView) convertView.findViewById(R.id.txtViewSId);
            viewHolder.tvPhone = (TextView) convertView.findViewById(R.id.txtViewSPhone);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = students.get(position);
        viewHolder.tvId.setText(String.valueOf(position+1));
        viewHolder.tvName.setText(student.getName());
        viewHolder.tvPhone.setText(student.getPhone());
        return convertView;
    }
    public class ViewHolder {
        TextView tvId, tvPhone, tvName;

    }
}
