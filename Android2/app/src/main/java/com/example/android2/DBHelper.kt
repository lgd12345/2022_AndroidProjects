package com.example.android2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "testdb", null, 1) {
    //데이터베이스 테이블 생성
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table TODO_TB("+
                "_id integer primary key autoincrement,"+
                "todo not null," +
                "today DATETIME DEFAULT CURRENT_TIMESTAMP)"
        )
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }
}