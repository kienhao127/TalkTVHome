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
    public static final String MESSAGE_COLUMN_NAME_EVENTDATETIME = "timeofeventmsg";
    public static final String MESSAGE_COLUMN_NAME_IMAGEURL = "imageurl";
    public static final String MESSAGE_COLUMN_NAME_DESCRIPTION = "text";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String MESSAGE_COLUMN_NAME_ACTIONTITLE = "actiontitle";
    public static final String MESSAGE_COLUMN_NAME_ACTIONEXTRA = "actionextra";
    public static final String MESSAGE_COLUMN_NAME_ISWARNING = "iswarning";
    public static final String MESSAGE_COLUMN_NAME_TOPICID = "topicid";
    public static final String MESSAGE_COLUMN_NAME_SENDERNAME = "sendername";
    public static final String MESSAGE_COLUMN_NAME_SENDERAVATAR = "senderavatar";

    public static final String TOPIC_TABLE_NAME = "topic";
    public static final String TOPIC_COLUMN_NAME_TOPICID = "topicid";
    public static final String TOPIC_COLUMN_NAME_NAME = "name";
    public static final String TOPIC_COLUMN_NAME_AVATAR = "avatar";
    public static final String TOPIC_COLUMN_NAME_LASTMSG = "lastmsg";
    public static final String TOPIC_COLUMN_NAME_DATETIME = "datetime";
    public static final String TOPIC_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String TOPIC_COLUMN_NAME_HASNEWMSG = "hasnewmsg";
    public static final String TOPIC_COLUMN_NAME_ISFOLLOW = "isfollow";


    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_COLUMN_NAME_TYPE + " INTEGER," +
                    MESSAGE_COLUMN_NAME_SENDERID + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_SENDERNAME + " TEXT, " +
                    MESSAGE_COLUMN_NAME_SENDERAVATAR + " TEXT, " +
                    MESSAGE_COLUMN_NAME_TITLE + " TEXT, " +
                    MESSAGE_COLUMN_NAME_DATETIME + " TEXT, " +
                    MESSAGE_COLUMN_NAME_EVENTDATETIME + " TEXT, " +
                    MESSAGE_COLUMN_NAME_IMAGEURL + " TEXT, " +
                    MESSAGE_COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_ACTIONTITLE + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ACTIONEXTRA + " TEXT, " +
                    MESSAGE_COLUMN_NAME_ISWARNING + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_TOPICID + " TEXT" +
                    ");";

    private static final String TOPIC_TABLE_CREATE =
            "CREATE TABLE " + TOPIC_TABLE_NAME + " (" +
                    TOPIC_COLUMN_NAME_TOPICID + " TEXT PRIMARY KEY, " +
                    TOPIC_COLUMN_NAME_NAME + " TEXT, " +
                    TOPIC_COLUMN_NAME_LASTMSG + " TEXT, " +
                    TOPIC_COLUMN_NAME_AVATAR + " TEXT, " +
                    TOPIC_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    TOPIC_COLUMN_NAME_DATETIME + " TEXT, " +
                    TOPIC_COLUMN_NAME_HASNEWMSG + " INTEGER, " +
                    TOPIC_COLUMN_NAME_ISFOLLOW + " INTEGER" +
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
        db.execSQL(TOPIC_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TOPIC_TABLE_CREATE);
        onCreate(db);
    }


    //TOPIC-----------------

    public boolean insertTopic(Topic topic){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TOPIC_COLUMN_NAME_ACTIONTYPE, topic.getAction_type());
        values.put(TOPIC_COLUMN_NAME_AVATAR, topic.getAvatar());
        values.put(TOPIC_COLUMN_NAME_DATETIME, topic.getDate());
        values.put(TOPIC_COLUMN_NAME_HASNEWMSG, (topic.isHasNewMessage())?1:0);
        values.put(TOPIC_COLUMN_NAME_LASTMSG, topic.getLastMess());
        values.put(TOPIC_COLUMN_NAME_NAME, topic.getName());
        values.put(TOPIC_COLUMN_NAME_TOPICID, topic.getTopicID());
        values.put(TOPIC_COLUMN_NAME_ISFOLLOW, (topic.isFollow())?1:0);
        long result = db.insert(TOPIC_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
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
        values.put(TOPIC_COLUMN_NAME_TOPICID, topic.getTopicID());
        values.put(TOPIC_COLUMN_NAME_ISFOLLOW, (topic.isFollow())?1:0);

        long result = db.update(TOPIC_TABLE_NAME, values, TOPIC_COLUMN_NAME_TOPICID + " = ?", new String[] {topic.getTopicID()});
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Topic> getListTopic(int loadFrom, boolean isFollow){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        String selectQuery = "SELECT *" +
                " FROM " + TOPIC_TABLE_NAME +
                " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + ((isFollow)?1:0) +
                " ORDER BY " + TOPIC_COLUMN_NAME_DATETIME + " DESC" +
                " LIMIT 30 OFFSET " + loadFrom;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopicID(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_TOPICID)));
                topic.setAvatar(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_AVATAR)));
                topic.setName(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_NAME)));
                topic.setLastMess(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_LASTMSG)));
                topic.setHasNewMessage((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG)) == 1)?true:false);
                topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ACTIONTYPE)));
                topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
                topic.setFollow((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ISFOLLOW)) == 1)?true:false);
                arrTopic.add(topic);
            } while (cTopic.moveToNext());
        }
        return arrTopic;
    }

    public ArrayList<Topic> getListTopic(boolean isFollow){
        ArrayList<Topic> arrTopic = new ArrayList<>();
        String selectQuery = "SELECT *" +
                " FROM " + TOPIC_TABLE_NAME +
                " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + ((isFollow)?1:0);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            do {
                Topic topic = new Topic();
                topic.setTopicID(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_TOPICID)));
                topic.setAvatar(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_AVATAR)));
                topic.setName(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_NAME)));
                topic.setLastMess(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_LASTMSG)));
                topic.setHasNewMessage((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG)) == 1)?true:false);
                topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ACTIONTYPE)));
                topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
                topic.setFollow((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ISFOLLOW)) == 1)?true:false);
                topic.setTopicID(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_TOPICID)));
                arrTopic.add(topic);
            } while (cTopic.moveToNext());
        }
        return arrTopic;
    }

    public Topic getTopic(String topicID){
        Topic topic = new Topic();
        String selectQuery = "SELECT *" +
                " FROM " + TOPIC_TABLE_NAME +
                " WHERE " + TOPIC_COLUMN_NAME_TOPICID + " = '" + topicID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            topic.setTopicID(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_TOPICID)));
            topic.setAvatar(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_AVATAR)));
            topic.setName(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_NAME)));
            topic.setLastMess(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_LASTMSG)));
            topic.setHasNewMessage((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG)) == 1)?true:false);
            topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ACTIONTYPE)));
            topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
            topic.setFollow((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ISFOLLOW)) == 1)?true:false);
        }
        if (topic != null) {
            return topic;
        }
        return null;
    }

    public Topic getNewestUnfollowTopic(){
        Topic topic = new Topic();
        String selectQuery = "SELECT *" +
                " FROM " + TOPIC_TABLE_NAME +
                " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + 0 +
                " ORDER BY " + TOPIC_COLUMN_NAME_DATETIME + " DESC" +
                " LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            topic.setTopicID(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_TOPICID)));
            topic.setAvatar(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_AVATAR)));
            topic.setName(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_NAME)));
            topic.setLastMess(cTopic.getString(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_LASTMSG)));
            topic.setHasNewMessage((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_HASNEWMSG)) == 1)?true:false);
            topic.setAction_type(cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ACTIONTYPE)));
            topic.setDate(cTopic.getLong(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_DATETIME)));
            topic.setFollow((cTopic.getInt(cTopic.getColumnIndex(TOPIC_COLUMN_NAME_ISFOLLOW)) == 1)?true:false);
        }
        if (topic != null) {
            return topic;
        }
        return null;
    }
    public boolean deleteTopic(String topicID){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(TOPIC_TABLE_NAME, TOPIC_COLUMN_NAME_TOPICID + "='" + topicID + "'", null) > 0;
    }

    public boolean isEsixtUnfollowTopic(){
        String selectQuery = "SELECT *" +
                " FROM " + TOPIC_TABLE_NAME +
                " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + 0 +
                " LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cTopic = db.rawQuery(selectQuery, null);
        if (cTopic.moveToFirst()) {
            return true;
        }
        return false;
    }


    //MESSAGE-----------------
    public boolean insertMessage(MessageDetail messageDetail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN_NAME_SENDERID, messageDetail.getUser().getId());
        values.put(MESSAGE_COLUMN_NAME_SENDERNAME, messageDetail.getUser().getName());
        values.put(MESSAGE_COLUMN_NAME_SENDERAVATAR, messageDetail.getUser().getAvatar());
        values.put(MESSAGE_COLUMN_NAME_TITLE, messageDetail.getTitle());
        values.put(MESSAGE_COLUMN_NAME_DATETIME, messageDetail.getDatetime());
        values.put(MESSAGE_COLUMN_NAME_EVENTDATETIME, messageDetail.getEventDatetime());
        values.put(MESSAGE_COLUMN_NAME_IMAGEURL, messageDetail.getImageURL());
        values.put(MESSAGE_COLUMN_NAME_DESCRIPTION, messageDetail.getText());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTYPE, messageDetail.getAction_type());
        values.put(MESSAGE_COLUMN_NAME_ACTIONTITLE, messageDetail.getAction_title());
        values.put(MESSAGE_COLUMN_NAME_ACTIONEXTRA, messageDetail.getAction_extra());
        values.put(MESSAGE_COLUMN_NAME_TYPE, messageDetail.getType());
        values.put(MESSAGE_COLUMN_NAME_TOPICID, messageDetail.getTopicID());

        long result = db.insert(MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<MessageDetail> getListMessage(String topicID, int loadMoreFrom){
        ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
        String selectQuery = "SELECT  *" +
                            " FROM (SELECT * " +
                                    " FROM " + MESSAGE_TABLE_NAME +
                                    " WHERE " + MESSAGE_COLUMN_NAME_TOPICID + " = '" + topicID + "'" +
                                    " ORDER BY " + MESSAGE_COLUMN_NAME_DATETIME + " DESC" +
                                    " LIMIT 30 OFFSET " + loadMoreFrom + ")" +
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
                messageDetail.setEventDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_EVENTDATETIME)));
                messageDetail.setImageURL(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_IMAGEURL)));
                messageDetail.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_DESCRIPTION)));
                messageDetail.setAction_type(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                messageDetail.setAction_title(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                messageDetail.setAction_extra(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                messageDetail.setWarning(Boolean.parseBoolean(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_ISWARNING))));
                messageDetail.setTopicID(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TOPICID)));
                User user = new User();
                user.setId(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERID)));
                user.setName(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERNAME)));
                user.setAvatar(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERAVATAR)));
                messageDetail.setUser(user);

                arrMessDetail.add(messageDetail);
            } while (c.moveToNext());
        }
        return arrMessDetail;
    }

    public boolean deleteMessage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(MESSAGE_TABLE_NAME, MESSAGE_COLUMN_NAME_ID + "=" + id, null) > 0;
    }

    public boolean deleteAllMessage(String senderID, String topicID){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(MESSAGE_TABLE_NAME, MESSAGE_COLUMN_NAME_SENDERID + "='" + senderID + "'" + " and " + MESSAGE_COLUMN_NAME_TOPICID + "='" + topicID + "'" , null) > 0;
    }
}
