package com.rku_18fotca11002.sqlitedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DataBaseHelper db;
    EditText edtName, edtSurname, edtMarks;
    TextView txtDisplay;
    Button btnSubmit,btnDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);

        edtName = findViewById(R.id.edtName);
        edtSurname = findViewById(R.id.edtSurname);
        edtMarks = findViewById(R.id.edtMarks);
        btnSubmit = findViewById(R.id.btnSave);
        btnDisplay = findViewById(R.id.btnDisplay);
        txtDisplay = findViewById(R.id.txtDisplay);
        AddData();
        viewAll();
    }

    public void AddData(){

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String surname = edtSurname.getText().toString();
                String marks = edtMarks.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(MainActivity.this, "Name must not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (surname.equals("")) {
                    Toast.makeText(MainActivity.this, "Surname must not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (marks.equals("")) {
                    Toast.makeText(MainActivity.this, "Marks must not be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.insertData(name, surname, marks)) {
                    Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewAll(){
        btnDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = db.getAllData();
                if(res.getCount() == 0){
//                  Show message
                    Toast.makeText(MainActivity.this, "Database is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Surname :"+res.getString(2)+"\n");
                    buffer.append("Marks :"+res.getString(3)+"\n");
                }
//              Show all data
                txtDisplay.setText(buffer.toString());
            }
        });
    }
}