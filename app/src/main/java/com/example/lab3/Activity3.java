package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity3 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        EditText et_no = (EditText) findViewById(R.id.stdNo);
        EditText et_name = (EditText) findViewById(R.id.stdName);
        EditText et_surname = (EditText) findViewById(R.id.stdSurname);
        RadioButton cmpeButton2 = (RadioButton) findViewById(R.id.cmpeButton2);
        RadioButton cmseButton2 = (RadioButton) findViewById(R.id.cmseButton2);
        RadioButton blgmButton2 = (RadioButton) findViewById(R.id.blgmButton2);


        ArrayList<Student> studentList = new ArrayList<>();
        MyDB db=new MyDB(this);

        Cursor c=db.GetData();

        if (c.moveToNext()){

            do{
                Student student = new Student(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
                studentList.add(student);


            }while(c.moveToNext());
        }

        Button searchButton = (Button) findViewById(R.id.search_button);
        Button update_button = (Button) findViewById(R.id.update_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = Integer.parseInt(et_no.getText().toString());

                for (Student student : studentList){
                    if (student.getId() == id){

                            et_name.setText(student.getName());
                            et_surname.setText(student.getSurname());

                        if (student.getProgram().equals("CMPE")) {
                            cmpeButton2.setChecked(true);
                        } else if (student.getProgram().equals("CMSE")) {
                            cmseButton2.setChecked(true);
                        } else if (student.getProgram().equals("BLGM")) {
                            blgmButton2.setChecked(true);
                        }

                        update_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String newName = et_name.getText().toString();
                                student.setName(newName);
                                String newSurname = et_surname.getText().toString();
                                student.setSurname(newSurname);
                                if (cmpeButton2.isChecked()) {
                                    student.setProgram("CMPE");
                                } else if (cmseButton2.isChecked()) {
                                    student.setProgram("CMSE");
                                } else if (blgmButton2.isChecked()) {
                                    student.setProgram("BLGM");
                                }

                                db.UpdateStu(student.getId(), student.getName(), student.getSurname(), student.getProgram());

                                Toast.makeText(Activity3.this, "Student info updated", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                }

            }
        });

        Button list_button = (Button) findViewById(R.id.list_button);
        list_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity3.this, Activity1.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Students", studentList);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



    }
}