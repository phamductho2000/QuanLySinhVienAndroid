package com.example.quanlysinhvien;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private  DatabaseHandler db;
    private StudentListViewAdapter studentListViewAdapter;
    private FloatingActionButton btnAdd;
    private ListView lstViewStudents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstViewStudents = (ListView) findViewById(R.id.lstViewStudents);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        db = new DatabaseHandler(this);
        addStudent();
        showData();
        registerForContextMenu(lstViewStudents);
        lstViewStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long studentId = lstViewStudents.getItemIdAtPosition(info.position);
        switch (item.getItemId()) {
            case R.id.itemEdit:
                EditStudent(studentId);
                break;
            case R.id.itemDelete:
                DeleteStudent(studentId);
                break;
            default:
        }
        return super.onContextItemSelected(item);
    }
    public void showData(){
        studentListViewAdapter = new StudentListViewAdapter(this, db.getAllStudents());
        lstViewStudents = findViewById(R.id.lstViewStudents);
        lstViewStudents.setAdapter(studentListViewAdapter);
    }
    public void addStudent(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
    public void DeleteStudent(long id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to delete?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteStudent(id);
                showData();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void EditStudent(long studentId){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra("STUDENT_ID", studentId);
        startActivity(intent);
    }
}