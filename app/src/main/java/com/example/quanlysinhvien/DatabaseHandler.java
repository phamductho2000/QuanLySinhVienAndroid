package com.example.quanlysinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sudent_manager";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_students_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_PHONE);
        db.execSQL(create_students_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String drop_students_table = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
        db.execSQL(drop_students_table);

        onCreate(db);
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, student.getId());
        values.put(KEY_NAME, student.getName());
        values.put(KEY_PHONE, student.getPhone());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<Student> getAllStudents(){
        List<Student>  students = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            students.add(student);
            cursor.moveToNext();
        }
        return students;
    }

    public void deleteStudent(long studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(studentId) });
        db.close();
    }

    public Student getStudent(long studentId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, KEY_ID + " = ?", new String[] { String.valueOf(studentId) },null, null, null);
        if(cursor != null)
            cursor.moveToFirst();
        Student student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        return student;
    }

    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, student.getName());
        values.put(KEY_PHONE, student.getPhone());
        db.update(TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(student.getId()) });
        db.close();
    }
}
