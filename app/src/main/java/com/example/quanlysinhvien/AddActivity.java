package com.example.quanlysinhvien;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AddActivity extends AppCompatActivity {
    private TextView textViewId;
    private TextView textViewName;
    private TextView textViewPhone;
    private EditText edtId;
    private EditText edtName;
    private EditText edtPhone;
    private Button btnBack;
    private Button btnSave;
    private DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Init();
        Back();
        SaveData();
    }
    public void Init(){
        textViewId = (TextView) findViewById(R.id.txtViewId);
        textViewName = (TextView) findViewById(R.id.txtViewName);
        textViewPhone = (TextView) findViewById(R.id.txtViewPhone);
        edtId = (EditText) findViewById(R.id.edtId);
        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSave = (Button) findViewById(R.id.btnSave);
        db = new DatabaseHandler(this);
    }
    public void Back(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void SaveData(){
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student student = new Student( Integer.valueOf(edtId.getText().toString()),
                        edtName.getText().toString(),
                        edtPhone.getText().toString());
                db.addStudent(student);
            }
        });
    }
}
