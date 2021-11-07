package com.coba.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrudHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "studentdb";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_STUDENTS = "students";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_CLASS = "class";

    public static final String SQL_TABLE_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS +
            " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT, "
            + KEY_CLASS + " TEXT "
            + " ) ";

    public CrudHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_TABLE_STUDENTS);
        this.generateStudent(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    private void generateStudent(SQLiteDatabase db) {
        this.insertWithContext(new StudentModel(null,"Satria Yudha","1MSI12"),db);
        this.insertWithContext(new StudentModel(null,"Laurensia","1MAK46"),db);
        this.insertWithContext(new StudentModel(null,"Edbert","5MTI51"),db);
    }

    public List<StudentModel> index() {
        List<StudentModel> students = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                students.add(new StudentModel(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        database.close();
        return students;
    }

    public StudentModel find(String id) {
        StudentModel student = null;

        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(
                    "SELECT * FROM "+TABLE_STUDENTS+" WHERE "+KEY_ID+" = '"+id+ "'",
                null
        );
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            student = new StudentModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }
        database.close();
        return student;
    }

    public StudentModel findByName(String name) {
        StudentModel student = null;

        SQLiteDatabase database = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(
                    "SELECT * FROM "+TABLE_STUDENTS+" WHERE "+KEY_NAME+" = '"+name+ "'",
                null
        );
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            student = new StudentModel(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
        }
        database.close();
        return student;
    }

    public void insert(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, student.getName());
        values.put(KEY_CLASS, student.getKelas());

        db.insert(TABLE_STUDENTS, null, values);
        db.close();
    }

    public void insertWithContext(StudentModel student, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, student.getName());
        values.put(KEY_CLASS, student.getKelas());

        db.insert(TABLE_STUDENTS, null, values);
    }

    public void update(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, student.getName());
        values.put(KEY_CLASS, student.getKelas());

        db.update(TABLE_STUDENTS, values,"id = ?",new String[]{student.getId()});
        db.close();
    }

    public void updateByName(StudentModel student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, student.getName());
        values.put(KEY_CLASS, student.getKelas());

        db.update(TABLE_STUDENTS, values,"name = ?",new String[]{student.getName()});
        db.close();
    }

    public void delete(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STUDENTS,"id = ?",new String[]{id});
        db.close();
    }

    public void deleteByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_STUDENTS,"name = ?",new String[]{name});
        db.close();
    }
}
