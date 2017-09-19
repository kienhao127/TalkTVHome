package com.example.cpu11341_local.talktvhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.Topic;
import com.example.cpu11341_local.talktvhome.data.User;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by CPU11341-local on 9/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "talktv.db";
    public static final String MESSAGE_TABLE_NAME = "massage";
    public static final String MESSAGE_COLUMN_NAME_TYPE = "type";
    public static final String MESSAGE_COLUMN_NAME_ID = "id";
    public static final String MESSAGE_COLUMN_NAME_SENDERID = "senderid";
    public static final String MESSAGE_COLUMN_NAME_TITLE = "title";
    public static final String MESSAGE_COLUMN_NAME_TIMEOFMESS = "timeofmsg";
    public static final String MESSAGE_COLUMN_NAME_IMAGEURL = "imageurl";
    public static final String MESSAGE_COLUMN_NAME_DESCRIPTION = "text";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTITLE = "actiontitle";
    public static final String MESSAGE_COLUMN_NAME_ACTIONEXTRA = "actionextra";
    public static final String MESSAGE_COLUMN_NAME_ISWARNING = "iswarning";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME_ID = "id";
    public static final String USER_COLUMN_NAME_NAME = "name";
    public static final String USER_COLUMN_NAME_AVATAR = "avatar";

    public static final String TOPIC_TABLE_NAME = "topic";
    public static final String TOPIC_COLUMN_NAME_USERID = "userid";
    public static final String TOPIC_COLUMN_NAME_NAME = "name";
    public static final String TOPIC_COLUMN_NAME_AVATAR = "avatar";
    public static final String TOPIC_COLUMN_NAME_LASTMSG = "lastmsg";
    public static final String TOPIC_COLUMN_NAME_DATETIME = "datetime";
    public static final String TOPIC_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String TOPIC_COLUMN_NAME_HASNEWMSG = "hasnewmsg";


    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_COLUMN_NAME_TYPE + " INTEGER," +
                    MESSAGE_COLUMN_NAME_SENDERID + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_TITLE + " TEXT, " +
                    MESSAGE_COLUMN_NAME_TIMEOFMESS + " TEXT, " +
                    MESSAGE_COLUMN_NAME_IMAGEURL + " TEXT, " +
                    MESSAGE_COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_ACTIONTITLE + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ACTIONEXTRA + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ISWARNING + " INTEGER" +
                    ");";

    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_COLUMN_NAME_NAME + " TEXT," +
                    USER_COLUMN_NAME_AVATAR + " TEXT" +
                    ");";

    private static final String TOPIC_TABLE_CREATE =
            "CREATE TABLE " + TOPIC_TABLE_NAME + " (" +
                    TOPIC_COLUMN_NAME_USERID + " INTEGER PRIMARY KEY, " +
                    TOPIC_COLUMN_NAME_NAME + "TEXT, " +
                    TOPIC_COLUMN_NAME_LASTMSG + "TEXT, " +
                    TOPIC_COLUMN_NAME_AVATAR + "TEXT, " +
                    TOPIC_COLUMN_NAME_ACTIONTYPE + "INTEGER, " +
                    TOPIC_COLUMN_NAME_HASNEWMSG + "INTEGER" +
                    ");";

    private static DatabaseHelper instance = null;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static DatabaseHelper getInstance(Context context) {
        if(instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MESSAGE_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(TOPIC_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + USER_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS" + TOPIC_TABLE_CREATE);
        onCreate(db);
    }

    public boolean addUser(String name, String avatar, int isFollow) {
        SQLiteDatabase db = this.getWritableDatabase();
        //adding user name in users table
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME_NAME, name);
        values.put(USER_COLUMN_NAME_AVATAR, avatar);
        // db.insert(TABLE_USER, null, values);
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertMessage(MessageDetail messageDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN_NAME_SENDERID, messageDetail.getUser().getId());
        values.put(MESSAGE_COLUMN_NAME_TITLE, messageDetail.getTitle());
        values.put(MESSAGE_COLUMN_NAME_TIMEOFMESS, messageDetail.getDatetime());
        values.put(MESSAGE_COLUMN_NAME_IMAGEURL, messageDetail.getImageURL());
        values.put(MESSAGE_COLUMN_NAME_DESCRIPTION, messageDetail.getText());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTYPE, messageDetail.getAction_type());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTITLE, messageDetail.getAction_title());
        values.put(MESSAGE_COLUMN_NAME_ACTIONEXTRA, messageDetail.getAction_extra());
        values.put(MESSAGE_COLUMN_NAME_TYPE, messageDetail.getType());

        long result = db.insert(MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME_ID, user.getId());
        values.put(USER_COLUMN_NAME_NAME, user.getName());
        values.put(USER_COLUMN_NAME_AVATAR, user.getAvatar());

        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public User getUser(int userID){
        User user = new User();
        String selectQuery = "SELECT  *" +
                " FROM " + MESSAGE_TABLE_NAME +
                " WHERE " + MESSAGE_COLUMN_NAME_SENDERID + " = " + userID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cUser = db.rawQuery(selectQuery, null);
        if (cUser.moveToFirst()) {
            do {
                user.setId(cUser.getInt(cUser.getColumnIndex(USER_COLUMN_NAME_ID)));
                user.setAvatar(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_AVATAR)));
                user.setName(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_NAME)));
            } while (cUser.moveToNext());
        }
        return user;
    }

    public ArrayList<Topic> getListTopic(){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        String selectQuery = "SELECT  *" +
                " FROM " + TOPIC_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setUserId(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_USERID)));
                topic.setAvatar(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_AVATAR)));
                topic.setName(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_NAME)));
                topic.setLastMess(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_LASTMSG)));
                topic.setHasNewMessage(Boolean.parseBoolean(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG))));
                topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
                topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
            } while (cTopic.moveToNext());
        }
        return null;
    }

    public ArrayList<MessageDetail> getListMessage(int senderID){
        ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
        String selectQuery = "SELECT  *" +
                            " FROM " + MESSAGE_TABLE_NAME +
                            " WHERE " + MESSAGE_COLUMN_NAME_SENDERID + " = " + senderID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                MessageDetail messageDetail = new MessageDetail();
                messageDetail.setType(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_TYPE)));
                messageDetail.setId(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ID)));
                messageDetail.setTitle(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TITLE)));
                messageDetail.setDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_TIMEOFMESS)));
                messageDetail.setImageURL(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_IMAGEURL)));
                messageDetail.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_DESCRIPTION)));
                messageDetail.setAction_type(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                messageDetail.setAction_title(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                messageDetail.setAction_extra(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                messageDetail.setWarning(Boolean.parseBoolean(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ISWARNING))));

                User user = new User();
                String selectUser = "SELECT  * FROM " + USER_TABLE_NAME +" WHERE "+ USER_COLUMN_NAME_ID +" = "+ senderID;
                Cursor cUser = db.rawQuery(selectUser, null);
                if (cUser.moveToFirst()) {
                    do {
                        user.setId(cUser.getInt(cUser.getColumnIndex(USER_COLUMN_NAME_ID)));
                        user.setAvatar(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_AVATAR)));
                        user.setName(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_NAME)));
                    } while (cUser.moveToNext());
                }
                messageDetail.setUser(user);
                arrMessDetail.add(messageDetail);
            } while (c.moveToNext());
        }
        return arrMessDetail;
    }
}
