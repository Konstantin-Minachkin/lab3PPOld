package com.example.lab3pp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addBtn, outputBtn, changeBtn;
    EditText fio;
    DBHelper sql;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fio = (EditText) findViewById(R.id.name);
        addBtn = (Button) findViewById(R.id.add_btn);

        sql = new DBHelper(this); // создаем объект для создания и управления версиями БД
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(fio.getText().toString().equals("") || fio.getText().toString().equals(" ") || fio.getText().toString().equals("ФИО") ))
                    sql.add(fio.getText().toString());
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ошибка при вводе", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        outputBtn = (Button) findViewById(R.id.output_btn);
        outputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OutputActivity.class);
                Cursor table = null;
                table = sql.getStudentTable();
                ArrayList<String> output = new ArrayList<String>();
                if (table != null) {
                    table.moveToFirst();
                    while (!table.isLast()){
                        output.add(String.valueOf(table.getInt(0)) + ' ' + table.getString(1) + ' ' + table.getString(2) + '\n');
                        table.moveToNext();
                    }
                    output.add(String.valueOf(table.getInt(0)) + ' ' + table.getString(1) + ' ' + table.getString(2));
                }
                intent.putExtra("mas", output);
                startActivity(intent);
            }
        });

        changeBtn = (Button) findViewById(R.id.change_btn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sql.update();
            }
        });
    }
}
