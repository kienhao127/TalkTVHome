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
import java.util.LinkedHashMap;
import java.util.Map;

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
    public static final String MESSAGE_COLUMN_NAME_DATETIME = "timeofmsg";
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
                    MESSAGE_COLUMN_NAME_DATETIME + " TEXT, " +
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
                    TOPIC_COLUMN_NAME_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TOPIC_COLUMN_NAME_NAME + " TEXT, " +
                    TOPIC_COLUMN_NAME_LASTMSG + " TEXT, " +
                    TOPIC_COLUMN_NAME_AVATAR + " TEXT, " +
                    TOPIC_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    TOPIC_COLUMN_NAME_DATETIME + " TEXT, " +
                    TOPIC_COLUMN_NAME_HASNEWMSG + " INTEGER" +
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

    public boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME_ID, user.getId());
        values.put(USER_COLUMN_NAME_NAME, user.getName());
        values.put(USER_COLUMN_NAME_AVATAR, user.getAvatar());

        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public User getUser(int userID){
        Map<Integer, User> linkedHashMapUser = new LinkedHashMap();
        linkedHashMapUser.put(0, new User(0, "https://img14.androidappsapk.co/300/6/7/8/vn.com.vng.talktv.png", "TalkTV"));
        linkedHashMapUser.put(1, new User(1, "http://avatar1.cctalk.vn/csmtalk_user3/305561959?t=1485278568", "Thúy Chi"));
        linkedHashMapUser.put(2, new User(2, "http://avatar1.cctalk.vn/csmtalk_user3/450425623?t=1502078349", "Trang Lady"));
        linkedHashMapUser.put(5, new User(5, "http://is2.mzstatic.com/image/thumb/Purple127/v4/95/75/d9/9575d99b-8854-11cc-25ef-4aa4b4bb6dc3/source/1200x630bb.jpg", "Tui"));
//        String selectQuery = "SELECT  *" +
//                " FROM " + USER_TABLE_NAME +
//                " WHERE " + USER_COLUMN_NAME_ID + " = " + userID;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cUser = db.rawQuery(selectQuery, null);
//        if (cUser.moveToFirst()) {
//            do {
//                user.setId(cUser.getInt(cUser.getColumnIndex(USER_COLUMN_NAME_ID)));
//                user.setAvatar(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_AVATAR)));
//                user.setName(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_NAME)));
//            } while (cUser.moveToNext());
//        }
//        db.close();
        return linkedHashMapUser.get(userID);
    }

    public boolean insertTopic(Topic topic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TOPIC_COLUMN_NAME_ACTIONTYPE, topic.getAction_type());
        values.put(TOPIC_COLUMN_NAME_AVATAR, topic.getAvatar());
        values.put(TOPIC_COLUMN_NAME_DATETIME, topic.getDate());
        values.put(TOPIC_COLUMN_NAME_HASNEWMSG, (topic.isHasNewMessage())?1:0);
        values.put(TOPIC_COLUMN_NAME_LASTMSG, topic.getLastMess());
        values.put(TOPIC_COLUMN_NAME_NAME, topic.getName());
        values.put(TOPIC_COLUMN_NAME_USERID, topic.getUserId());

        long result = db.insert(TOPIC_TABLE_NAME, null, values);
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public boolean updateTopic(Topic topic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TOPIC_COLUMN_NAME_ACTIONTYPE, topic.getAction_type());
        values.put(TOPIC_COLUMN_NAME_AVATAR, topic.getAvatar());
        values.put(TOPIC_COLUMN_NAME_DATETIME, topic.getDate());
        values.put(TOPIC_COLUMN_NAME_HASNEWMSG, (topic.isHasNewMessage())?1:0);
        values.put(TOPIC_COLUMN_NAME_LASTMSG, topic.getLastMess());
        values.put(TOPIC_COLUMN_NAME_NAME, topic.getName());
        values.put(TOPIC_COLUMN_NAME_USERID, topic.getUserId());

        long result = db.update(TOPIC_TABLE_NAME, values, TOPIC_COLUMN_NAME_USERID + " = ?", new String[] {String.valueOf(topic.getUserId())});
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public ArrayList<Topic> getListTopic(){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        String selectQuery = "SELECT *" +
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
                topic.setHasNewMessage((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG)) == 1)?true:false);
                topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ACTIONTYPE)));
                topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
                arrTopic.add(topic);
            } while (cTopic.moveToNext());
        }
        db.close();
        return arrTopic;
    }

    public boolean insertMessage(MessageDetail messageDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN_NAME_SENDERID, messageDetail.getUser().getId());
        values.put(MESSAGE_COLUMN_NAME_TITLE, messageDetail.getTitle());
        values.put(MESSAGE_COLUMN_NAME_DATETIME, messageDetail.getDatetime());
        values.put(MESSAGE_COLUMN_NAME_IMAGEURL, messageDetail.getImageURL());
        values.put(MESSAGE_COLUMN_NAME_DESCRIPTION, messageDetail.getText());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTYPE, messageDetail.getAction_type());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTITLE, messageDetail.getAction_title());
        values.put(MESSAGE_COLUMN_NAME_ACTIONEXTRA, messageDetail.getAction_extra());
        values.put(MESSAGE_COLUMN_NAME_TYPE, messageDetail.getType());

        long result = db.insert(MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }

    public ArrayList<MessageDetail> getListMessage(int senderID, int scrollTimes){
        ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
        String selectQuery = "SELECT  *" +
                            " FROM (SELECT * FROM " + MESSAGE_TABLE_NAME + " ORDER BY " + MESSAGE_COLUMN_NAME_DATETIME + " DESC LIMIT 30 OFFSET " + String.valueOf(scrollTimes*30) + ")" +
                            " WHERE " + MESSAGE_COLUMN_NAME_SENDERID + " = " + senderID +
                            " ORDER BY " + MESSAGE_COLUMN_NAME_DATETIME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                MessageDetail messageDetail = new MessageDetail();
                messageDetail.setType(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_TYPE)));
                messageDetail.setId(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ID)));
                messageDetail.setTitle(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TITLE)));
                messageDetail.setDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_DATETIME)));
                messageDetail.setImageURL(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_IMAGEURL)));
                messageDetail.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_DESCRIPTION)));
                messageDetail.setAction_type(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                messageDetail.setAction_title(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                messageDetail.setAction_extra(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                messageDetail.setWarning(Boolean.parseBoolean(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ISWARNING))));
                messageDetail.setUser(getUser(senderID));

                arrMessDetail.add(messageDetail);
            } while (c.moveToNext());
        }
        db.close();
        return arrMessDetail;
    }
}
