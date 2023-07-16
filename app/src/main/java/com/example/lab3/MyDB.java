package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDB extends SQLiteOpenHelper {

    private  static final String DBName="myDatabase";
    private  static final String DBTablename="myStudents";
    private  static final int DBversion=1;

    private  static final String STU_Number="studentNo";

    private  static final String STU_NAME="studentName";
    private  static final String STU_SURNAME="studentSurname";
    private  static final String STU_PROGRAM="studentProgram";

    private  static final String CREATE_TABLE="CREATE TABLE " + DBTablename + " (" + STU_Number + " INTEGER PRIMARY KEY, " + STU_NAME + " INTEGER, " + STU_SURNAME + " INTEGER, " + STU_PROGRAM + " INTEGER) ;";


    private  static final String DROP_TABLE="DROP TABLE IF EXISTS " + DBTablename;



    private Context context;
    public MyDB(@Nullable Context context) {
        super(context, DBName, null, DBversion);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    void AddStu(int stno, String name, String surname, String program){

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();
        cv.put(STU_Number,stno);
        cv.put(STU_NAME,name);
        cv.put(STU_SURNAME,surname);
        cv.put(STU_PROGRAM,program);

        try {
            db.insert(DBTablename,null,cv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    Cursor GetData(){
        String readquery="SELECT * FROM " + DBTablename;
        SQLiteDatabase db=this.getReadableDatabase();

        Cursor c=null;
        if (db!=null) {
            c = db.rawQuery(readquery, null);

        }

        return c;
    }

    void UpdateStu(int stno, String name, String surname, String program){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(STU_NAME,name);
        cv.put(STU_SURNAME,surname);
        cv.put(STU_PROGRAM,program);
        String[] whereArgs = { Integer.toString(stno) };
        int count = db.update(DBTablename, cv, STU_Number + " = ?", whereArgs);
        if (count < 1) {
            throw new RuntimeException("No student found with student number " + stno);
        }
    }

    void DeleteStu(int stno) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereArgs = { Integer.toString(stno) };
        int count = db.delete(DBTablename, STU_Number + " = ?", whereArgs);
        if (count < 1) {
            throw new RuntimeException("No student found with student number " + stno);
        }
        db.close();
    }


}


