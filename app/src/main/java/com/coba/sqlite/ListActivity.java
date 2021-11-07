package com.coba.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    List<StudentModel> students;
    StudentAdapter studentAdapter;
    ListView studentListView;

    public static ListActivity current;

    CrudHelper crudHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        crudHelper = new CrudHelper(this);
        current = this;

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, AddActivity.class);
            startActivity(intent);
        });

        FloatingActionButton fabLogout = findViewById(R.id.fabLogout);
        fabLogout.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        RefreshList();
    }

    public void RefreshList() {
        List<StudentModel> row = crudHelper.index();
        students = new ArrayList<>(row.size());
        students.addAll(row);

        studentAdapter = new StudentAdapter(ListActivity.this, students);

        studentListView = findViewById(R.id.studentList);
        studentListView.setAdapter(studentAdapter);
        studentListView.setSelected(true);
        studentListView.setOnItemClickListener((adapterView, view, position, id) -> {
            final StudentModel student = new StudentModel(
                    students.get(position).getId(),
                    students.get(position).getName(),
                    students.get(position).getKelas()
            );
            final CharSequence[] dialogitem = {"View Student", "Edit Student", "Delete Student"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
            builder.setTitle("Option");
            builder.setItems(dialogitem, (dialog, item) -> {
                switch (item) {
                    case 0:
                        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        i.putExtra("studentId", student.getId());
                        startActivity(i);
                        break;
                    case 1:
                        Intent in = new Intent(getApplicationContext(), UpdateActivity.class);
                        in.putExtra("studentId", student.getId());
                        startActivity(in);
                        break;
                    case 2:
                        crudHelper.delete(student.getId());
                        Toast.makeText(getApplicationContext(), "Successfully delete student", Toast.LENGTH_LONG).show();
                        RefreshList();
                        break;
                }
            });
            builder.create().show();
        });
        ((StudentAdapter) studentListView.getAdapter()).notifyDataSetInvalidated();
    }
}