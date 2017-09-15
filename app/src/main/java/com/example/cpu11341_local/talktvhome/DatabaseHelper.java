package com.example.cpu11341_local.talktvhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;

import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "talktv.db";
    public static final String MESSAGE_TABLE_NAME = "massage";
    public static final String MESSAGE_COLUMN_NAME_ID = "id";
    public static final String MESSAGE_COLUMN_NAME_SENDERID = "senderID";
    public static final String MESSAGE_COLUMN_NAME_TITLE = "title";
    public static final String MESSAGE_COLUMN_NAME_TIMEOFMESS = "timeofmess";
    public static final String MESSAGE_COLUMN_NAME_IMAGEURL = "imageurl";
    public static final String MESSAGE_COLUMN_NAME_DESCRIPTION = "description";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTITLE = "actiontitle";
    public static final String MESSAGE_COLUMN_NAME_ACTIONEXTRA = "actionextra";
    public static final String MESSAGE_COLUMN_NAME_TYPE = "type";
    public static final String MESSAGE_COLUMN_NAME_MESSAGE = "message";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME_ID = "id";
    public static final String USER_COLUMN_NAME_NAME = "name";
    public static final String USER_COLUMN_NAME_AVATAR = "avatar";
    public static final String USER_COLUMN_NAME_ISFOLLOW = "isfollow";

    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_COLUMN_NAME_SENDERID + " INTEGER PRIMARY KEY" +
                    MESSAGE_COLUMN_NAME_TITLE + " TEXT" +
                    MESSAGE_COLUMN_NAME_TIMEOFMESS + " TEXT" +
                    MESSAGE_COLUMN_NAME_IMAGEURL + " TEXT" +
                    MESSAGE_COLUMN_NAME_DESCRIPTION + " TEXT" +
                    MESSAGE_COLUMN_NAME_ACTIONTYPE + " INTEGER" +
                    MESSAGE_COLUMN_NAME_ACTIONTITLE + " TEXT" +
                    MESSAGE_COLUMN_NAME_ACTIONEXTRA + " TEXT" +
                    MESSAGE_COLUMN_NAME_TYPE + " INTEGER" +
                    MESSAGE_COLUMN_NAME_MESSAGE + " TEXT" +
                    ");";

    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_COLUMN_NAME_NAME + " TEXT" +
                    USER_COLUMN_NAME_AVATAR + " TEXT" +
                    USER_COLUMN_NAME_ISFOLLOW + " INTEGER" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + USER_TABLE_CREATE);
        onCreate(db);
    }

    public boolean addUser(String name, String avatar, int isFollow) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME_NAME, name);
        values.put(USER_COLUMN_NAME_AVATAR, avatar);
        values.put(USER_COLUMN_NAME_ISFOLLOW, isFollow);
        // db.insert(TABLE_USER, null, values);
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean addMessage(String senderID, String title, String timeOfMess, String imageURL,
                              String description, int action_type, String action_title, String action_extra, int type, String message) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN_NAME_SENDERID, senderID);
        values.put(MESSAGE_COLUMN_NAME_TITLE, title);
        values.put(MESSAGE_COLUMN_NAME_TIMEOFMESS, timeOfMess);
        values.put(MESSAGE_COLUMN_NAME_IMAGEURL, imageURL);
        values.put(MESSAGE_COLUMN_NAME_DESCRIPTION, description);
        values.put(MESSAGE_COLUMN_NAME_ACTIONTYPE, action_type);
        values.put(MESSAGE_COLUMN_NAME_ACTIONTITLE, action_title);
        values.put(MESSAGE_COLUMN_NAME_ACTIONEXTRA, action_extra);
        values.put(MESSAGE_COLUMN_NAME_TYPE, type);
        values.put(MESSAGE_COLUMN_NAME_MESSAGE, message);
        // db.insert(TABLE_USER, null, values);
        long result = db.insert(MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Topic> getListTopic(){

        return null;
    }

    public ArrayList<MessageDetail> getMessageDetail(int senderID){
        ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
        String selectQuery = "SELECT  *" +
                            " FROM " + MESSAGE_TABLE_NAME + " , " + USER_TABLE_NAME +
                            " WHERE " + MESSAGE_COLUMN_NAME_SENDERID + " = " + senderID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                MessageDetail messageDetail = new MessageDetail();
                messageDetail.setId(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ID)));

                messageDetail.setTitle(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TITLE)));
//                messageDetail.setDatetime(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TIMEOFMESS)));
                messageDetail.setImageURL(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_IMAGEURL)));
                messageDetail.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_DESCRIPTION)));
                messageDetail.setAction_type(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                messageDetail.setAction_title(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                messageDetail.setAction_extra(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                messageDetail.setType(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_TYPE)));

                //getting user hobby where id = id from user_hobby table
//                String selectHobbyQuery = "SELECT  * FROM " + TABLE_USER_HOBBY +" WHERE "+KEY_ID+" = "+ userModel.getId();
//                //SQLiteDatabase dbhobby = this.getReadableDatabase();
//                Cursor cHobby = db.rawQuery(selectHobbyQuery, null);
//
//                if (cHobby.moveToFirst()) {
//                    do {
//                        userModel.setHobby(cHobby.getString(cHobby.getColumnIndex(KEY_HOBBY)));
//                    } while (cHobby.moveToNext());
//                }
                // adding to Students list
                arrMessDetail.add(messageDetail);
            } while (c.moveToNext());
        }
        return arrMessDetail;
    }
}
