package com.coba.sqlite;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StudentAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<StudentModel> students;
    public StudentAdapter(Activity activity, List<StudentModel> students) {
        this.activity = activity;
        this.students = students;
    }
    @Override
    public int getCount() {
        return students.size();
    }
    @Override
    public Object getItem(int location) {
        return students.get(location);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.student_row, null);
        TextView id = convertView.findViewById(R.id.id);
        TextView name = convertView.findViewById(R.id.name);
        TextView kelas = convertView.findViewById(R.id.kelas);
        StudentModel student = students.get(position);
        id.setText(student.getId());
        name.setText(student.getName());
        kelas.setText(student.getKelas());
        return convertView;
    }
}
