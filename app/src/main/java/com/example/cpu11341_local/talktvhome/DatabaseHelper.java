package com.example.cpu11341_local.talktvhome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cpu11341_local.talktvhome.data.EventMessage;
import com.example.cpu11341_local.talktvhome.data.MessageDetail;
import com.example.cpu11341_local.talktvhome.data.RemindMessage;
import com.example.cpu11341_local.talktvhome.data.SimpleMessage;
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

    public static final String MESSAGE_TABLE_NAME = "message";
    public static final String MESSAGE_COLUMN_NAME_TYPE = "type";
    public static final String MESSAGE_COLUMN_NAME_ID = "id";
    public static final String MESSAGE_COLUMN_NAME_SENDERID = "senderid";
    public static final String MESSAGE_COLUMN_NAME_DATETIME = "timeofmsg";
    public static final String MESSAGE_COLUMN_NAME_TEXT = "text";
    public static final String MESSAGE_COLUMN_NAME_TOPICID = "topicid";

    public static final String EVENT_MESSAGE_TABLE_NAME = "eventmessage";
    public static final String EVENT_MESSAGE_COLUMN_NAME_ID = "id";
    public static final String EVENT_MESSAGE_COLUMN_NAME_TITLE = "title";
    public static final String EVENT_MESSAGE_COLUMN_NAME_EVENTDATETIME = "timeofeventmsg";
    public static final String EVENT_MESSAGE_COLUMN_NAME_IMAGEURL = "imageurl";
    public static final String EVENT_MESSAGE_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String EVENT_MESSAGE_COLUMN_NAME_ACTIONTITLE = "actiontitle";
    public static final String EVENT_MESSAGE_COLUMN_NAME_ACTIONEXTRA = "actionextra";

    public static final String REMIND_MESSAGE_TABLE_NAME = "remindmessage";
    public static final String REMIND_MESSAGE_COLUMN_NAME_ID = "id";
    public static final String REMIND_MESSAGE_COLUMN_NAME_TITLE = "remindtitle";
    public static final String REMIND_MESSAGE_COLUMN_NAME_REMINDATETIME = "timeofremindmsg";
    public static final String REMIND_MESSAGE_COLUMN_NAME_ACTIONTYPE = "remindactiontype";
    public static final String REMIND_MESSAGE_COLUMN_NAME_ACTIONTITLE = "remindactiontitle";
    public static final String REMIND_MESSAGE_COLUMN_NAME_ACTIONEXTRA = "remindactionextra";

    public static final String SIMPLE_MESSAGE_TABLE_NAME = "simplemessage";
    public static final String SIMPLE_MESSAGE_COLUMN_NAME_ID = "id";
    public static final String SIMPLE_MESSAGE_COLUMN_NAME_ISWARNING = "iswarning";

    public static final String TOPIC_TABLE_NAME = "topic";
    public static final String TOPIC_COLUMN_NAME_TOPICID = "topicid";
    public static final String TOPIC_COLUMN_NAME_NAME = "name";
    public static final String TOPIC_COLUMN_NAME_AVATAR = "avatar";
    public static final String TOPIC_COLUMN_NAME_LASTMSG = "lastmsg";
    public static final String TOPIC_COLUMN_NAME_DATETIME = "datetime";
    public static final String TOPIC_COLUMN_NAME_ACTIONTYPE = "actiontype";
    public static final String TOPIC_COLUMN_NAME_HASNEWMSG = "hasnewmsg";
    public static final String TOPIC_COLUMN_NAME_ISFOLLOW = "isfollow";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_COLUMN_NAME_USERID = "userid";
    public static final String USER_COLUMN_NAME_NAME = "name";
    public static final String USER_COLUMN_NAME_AVATAR = "avatar";

    private static final String MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                    MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MESSAGE_COLUMN_NAME_TYPE + " INTEGER, " +
                    MESSAGE_COLUMN_NAME_SENDERID + " TEXT, " +
                    MESSAGE_COLUMN_NAME_DATETIME + " TEXT, " +
                    MESSAGE_COLUMN_NAME_TEXT + " TEXT, " +
                    MESSAGE_COLUMN_NAME_TOPICID + " TEXT" +
                    ");";

    private static final String EVENT_MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + EVENT_MESSAGE_TABLE_NAME + " (" +
                    EVENT_MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    EVENT_MESSAGE_COLUMN_NAME_TITLE + " TEXT, " +
                    EVENT_MESSAGE_COLUMN_NAME_EVENTDATETIME + " TEXT, " +
                    EVENT_MESSAGE_COLUMN_NAME_IMAGEURL + " TEXT, " +
                    EVENT_MESSAGE_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    EVENT_MESSAGE_COLUMN_NAME_ACTIONTITLE + " TEXT, " +
                    EVENT_MESSAGE_COLUMN_NAME_ACTIONEXTRA + " TEXT, " +
                    "FOREIGN KEY (id) REFERENCES message(id) " +
                    ");";

    private static final String REMIND_MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + REMIND_MESSAGE_TABLE_NAME + " (" +
                    REMIND_MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    REMIND_MESSAGE_COLUMN_NAME_TITLE + " TEXT, " +
                    REMIND_MESSAGE_COLUMN_NAME_REMINDATETIME + " TEXT, " +
                    REMIND_MESSAGE_COLUMN_NAME_ACTIONTYPE + " INTEGER, " +
                    REMIND_MESSAGE_COLUMN_NAME_ACTIONTITLE + " TEXT, " +
                    REMIND_MESSAGE_COLUMN_NAME_ACTIONEXTRA + " TEXT, " +
                    "FOREIGN KEY (id) REFERENCES message(id) " +
                    ");";

    private static final String SIMPLE_MESSAGE_TABLE_CREATE =
            "CREATE TABLE " + SIMPLE_MESSAGE_TABLE_NAME + " (" +
                    SIMPLE_MESSAGE_COLUMN_NAME_ID + " INTEGER PRIMARY KEY, " +
                    SIMPLE_MESSAGE_COLUMN_NAME_ISWARNING + " INTEGER, " +
                    "FOREIGN KEY (id) REFERENCES message(id) " +
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

    private static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_COLUMN_NAME_USERID + " TEXT PRIMARY KEY, " +
                    USER_COLUMN_NAME_NAME + " TEXT, " +
                    USER_COLUMN_NAME_AVATAR + " TEXT" +
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
        db.execSQL(EVENT_MESSAGE_TABLE_CREATE);
        db.execSQL(REMIND_MESSAGE_TABLE_CREATE);
        db.execSQL(SIMPLE_MESSAGE_TABLE_CREATE);
        db.execSQL(TOPIC_TABLE_CREATE);
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + EVENT_MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + REMIND_MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + SIMPLE_MESSAGE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS" + TOPIC_TABLE_CREATE);
        db.execSQL("DROP TABLE IF EXISTS" + USER_TABLE_CREATE);
        onCreate(db);
    }

    //USER------------------
    public boolean insertUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COLUMN_NAME_USERID, user.getId());
        values.put(USER_COLUMN_NAME_NAME, user.getName());
        values.put(USER_COLUMN_NAME_AVATAR, user.getAvatar());
        long result = db.insert(USER_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public User getUser(String userID){
        User user = new User();
        String selectQuery = "SELECT *" +
                " FROM " + USER_TABLE_NAME +
                " WHERE " + USER_COLUMN_NAME_USERID + " = '" + userID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cUser = db.rawQuery(selectQuery, null);
        if (cUser.moveToFirst()) {
            user.setId(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_USERID)));
            user.setName(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_NAME)));
            user.setAvatar(cUser.getString(cUser.getColumnIndex(USER_COLUMN_NAME_AVATAR)));
        }
        return user;
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
                " FROM ( SELECT * " +
                        " FROM " + TOPIC_TABLE_NAME +
                        " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + ((isFollow)?1:0) + " and (" + TOPIC_COLUMN_NAME_TOPICID + " = '-1' or " + TOPIC_COLUMN_NAME_TOPICID + " = '0')" +
                        " ORDER BY " + TOPIC_COLUMN_NAME_TOPICID + " DESC" +
                        " LIMIT 30 OFFSET " + loadFrom + " )" +

                " UNION ALL" +

                " SELECT *" +
                " FROM ( SELECT *" +
                        " FROM " + TOPIC_TABLE_NAME +
                        " WHERE " + TOPIC_COLUMN_NAME_ISFOLLOW + " = " + ((isFollow)?1:0) + " and " + TOPIC_COLUMN_NAME_TOPICID + " <> '-1' and " + TOPIC_COLUMN_NAME_TOPICID + " <> '0'" +
                        " ORDER BY " + TOPIC_COLUMN_NAME_DATETIME + " DESC" +
                        " LIMIT 30 OFFSET " + loadFrom + " )";
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

    public boolean insertMessage(MessageDetail messageDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_COLUMN_NAME_SENDERID, messageDetail.getUser().getId());
        values.put(MESSAGE_COLUMN_NAME_DATETIME, messageDetail.getDatetime());
        values.put(MESSAGE_COLUMN_NAME_TEXT, messageDetail.getText());
        values.put(MESSAGE_COLUMN_NAME_TYPE, messageDetail.getType());
        values.put(MESSAGE_COLUMN_NAME_TOPICID, messageDetail.getTopicID());

        try {
            insertUser(messageDetail.getUser());
        } catch (SQLiteException e){

        }
        long result = db.insert(MESSAGE_TABLE_NAME, null, values);
        String selectQuery = "SELECT MAX(id) FROM " + MESSAGE_TABLE_NAME;
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            int lastID = c.getInt(0);
            switch (messageDetail.getType()){
                case 1:{
                    insertEventMessage(lastID, (EventMessage) messageDetail);
                    break;
                }
                case 2:{
                    insertRemindMessage(lastID, (RemindMessage) messageDetail);
                    break;
                }
                case 3:
                case 4:{
                    insertSimpleMessage(lastID, (SimpleMessage) messageDetail);
                    break;
                }
            }
        }

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    boolean insertEventMessage(int id, EventMessage eventMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EVENT_MESSAGE_COLUMN_NAME_ID, id);
        values.put(EVENT_MESSAGE_COLUMN_NAME_TITLE, eventMessage.getTitle());
        values.put(EVENT_MESSAGE_COLUMN_NAME_EVENTDATETIME, eventMessage.getEventDatetime());
        values.put(EVENT_MESSAGE_COLUMN_NAME_IMAGEURL, eventMessage.getImageURL());
        values.put(EVENT_MESSAGE_COLUMN_NAME_ACTIONTYPE, eventMessage.getAction_type());
        values.put(EVENT_MESSAGE_COLUMN_NAME_ACTIONTITLE, eventMessage.getAction_title());
        values.put(EVENT_MESSAGE_COLUMN_NAME_ACTIONEXTRA, eventMessage.getAction_extra());
        long result = db.insert(EVENT_MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    boolean insertRemindMessage(int id, RemindMessage remindMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(REMIND_MESSAGE_COLUMN_NAME_ID, id);
        values.put(REMIND_MESSAGE_COLUMN_NAME_TITLE, remindMessage.getTitle());
        values.put(REMIND_MESSAGE_COLUMN_NAME_REMINDATETIME, remindMessage.getEventDatetime());
        values.put(REMIND_MESSAGE_COLUMN_NAME_ACTIONTYPE, remindMessage.getAction_type());
        values.put(REMIND_MESSAGE_COLUMN_NAME_ACTIONTITLE, remindMessage.getAction_title());
        values.put(REMIND_MESSAGE_COLUMN_NAME_ACTIONEXTRA, remindMessage.getAction_extra());
        long result = db.insert(REMIND_MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    boolean insertSimpleMessage(int id, SimpleMessage simpleMessage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SIMPLE_MESSAGE_COLUMN_NAME_ID, id);
        values.put(SIMPLE_MESSAGE_COLUMN_NAME_ISWARNING, (simpleMessage.isWarning()?1:0));
        long result = db.insert(SIMPLE_MESSAGE_TABLE_NAME, null, values);
        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<MessageDetail> getListMessage(String topicID, int loadMoreFrom){
        ArrayList<MessageDetail> arrMessDetail = new ArrayList<>();
        String selectQuery = "SELECT *" +
                            " FROM (SELECT * " +
                                    " FROM message m LEFT JOIN eventmessage e on m.id = e.id LEFT JOIN remindmessage r on m.id = r.id LEFT JOIN simplemessage s on m.id = s.id" +
                                    " WHERE m.topicid = '" + topicID + "' " +
                                    " ORDER BY " + MESSAGE_COLUMN_NAME_DATETIME + " DESC" +
                                    " LIMIT 30 OFFSET " + loadMoreFrom + ")" +
                            " ORDER BY " + MESSAGE_COLUMN_NAME_DATETIME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                switch (c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_TYPE))){
                    case 1:{
                        EventMessage eventMessage = new EventMessage();
                        eventMessage.setType(1);
                        eventMessage.setId(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ID)));
                        eventMessage.setTitle(c.getString(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_TITLE)));
                        eventMessage.setDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_DATETIME)));
                        eventMessage.setEventDatetime(c.getLong(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_EVENTDATETIME)));
                        eventMessage.setImageURL(c.getString(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_IMAGEURL)));
                        eventMessage.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TEXT)));
                        eventMessage.setAction_type(c.getInt(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                        eventMessage.setAction_title(c.getString(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                        eventMessage.setAction_extra(c.getString(c.getColumnIndex(EVENT_MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                        eventMessage.setTopicID(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TOPICID)));
                        User user = getUser(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERID)));
                        eventMessage.setUser(user);
                        arrMessDetail.add(eventMessage);
                        break;
                    }
                    case 2:{
                        RemindMessage remindMessage = new RemindMessage();
                        remindMessage.setType(2);
                        remindMessage.setId(c.getInt(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_ID)));
                        remindMessage.setTitle(c.getString(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_TITLE)));
                        remindMessage.setDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_DATETIME)));
                        remindMessage.setEventDatetime(c.getLong(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_REMINDATETIME)));
                        remindMessage.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TEXT)));
                        remindMessage.setAction_type(c.getInt(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_ACTIONTYPE)));
                        remindMessage.setAction_title(c.getString(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_ACTIONTITLE)));
                        remindMessage.setAction_extra(c.getString(c.getColumnIndex(REMIND_MESSAGE_COLUMN_NAME_ACTIONEXTRA)));
                        remindMessage.setTopicID(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TOPICID)));
                        User user = getUser(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERID)));
                        remindMessage.setUser(user);
                        arrMessDetail.add(remindMessage);
                        break;
                    }
                    case 3:
                    case 4:{
                        SimpleMessage simpleMessage = new SimpleMessage();
                        simpleMessage.setType(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_TYPE)));
                        simpleMessage.setId(c.getInt(c.getColumnIndex(MESSAGE_COLUMN_NAME_ID)));
                        simpleMessage.setDatetime(c.getLong(c.getColumnIndex(MESSAGE_COLUMN_NAME_DATETIME)));
                        simpleMessage.setText(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TEXT)));
                        simpleMessage.setTopicID(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_TOPICID)));
                        User user = getUser(c.getString(c.getColumnIndex(MESSAGE_COLUMN_NAME_SENDERID)));
                        simpleMessage.setUser(user);
                        simpleMessage.setWarning((c.getInt(c.getColumnIndex(SIMPLE_MESSAGE_COLUMN_NAME_ISWARNING)) == 1)?true:false);
                        arrMessDetail.add(simpleMessage);
                        break;
                    }
                }
            } while (c.moveToNext());
        }
        return arrMessDetail;
    }

    public void deleteMessage(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(MESSAGE_TABLE_NAME, MESSAGE_COLUMN_NAME_ID + "=" + id, null);
        db.delete(EVENT_MESSAGE_TABLE_NAME, EVENT_MESSAGE_COLUMN_NAME_ID + "=" + id, null);
        db.delete(REMIND_MESSAGE_TABLE_NAME, REMIND_MESSAGE_COLUMN_NAME_ID + "=" + id, null);
        db.delete(SIMPLE_MESSAGE_TABLE_NAME, SIMPLE_MESSAGE_COLUMN_NAME_ID + "=" + id, null);
    }

    public void deleteAllMessage(String topicID){
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "DELETE FROM " + EVENT_MESSAGE_TABLE_NAME + " WHERE id IN (SELECT m.id FROM message m WHERE m.topicid = '" + topicID + "')";
        db.execSQL(queryString);
        queryString = "DELETE FROM " + REMIND_MESSAGE_TABLE_NAME + " WHERE id IN (SELECT m.id FROM message m WHERE m.topicid = '" + topicID + "')";
        db.execSQL(queryString);
        queryString = "DELETE FROM " + SIMPLE_MESSAGE_TABLE_NAME + " WHERE id IN (SELECT m.id FROM message m WHERE m.topicid = '" + topicID + "')";
        db.execSQL(queryString);
        db.delete(MESSAGE_TABLE_NAME, MESSAGE_COLUMN_NAME_TOPICID + "='" + topicID + "'" , null);
    }
}
