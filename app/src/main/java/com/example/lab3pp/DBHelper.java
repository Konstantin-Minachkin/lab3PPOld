package com.example.lab3pp;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DBHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Student";
    private static final String TABLE_STUDENT = "student";
    private static final String KEY_ID = "id";
    private static final String KEY_FIO = "fio";
    private static final String KEY_TIME = "time_of_writing"; //Время добавления записи

    private static final String DB_CREATE = "create table " + TABLE_STUDENT + "(" + KEY_ID  + " integer primary key autoincrement NOT NULL," + KEY_FIO
            + " text," + KEY_TIME + " text" + ")";

    private final String format = "dd.MM.yy HH:mm";
    private ArrayList<String> list = new ArrayList<String>();
    //String lastElemId = "";
    Context myContext;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
        list.add("Абдуалимов Амирхон Рустамович"); list.add("Акжигитов Радмир Русланович"); list.add("Артемов Александр Андреевич"); list.add("Аскандер Стивен Самех Саад"); list.add("Болдырев Григорий Михайлович"); list.add("Гарянин Никита Андреевич"); list.add("Гриценко Александр Сергеевич"); list.add("Звенигородская Арина Александровна"); list.add("Зекирьяев Руслан Тимурович"); list.add("Исхак Керолос Камал");
        list.add("Коватьев Иван Михайлович"); list.add("Костина Оксана Владимировна"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Миночкин Константин Даниилович"); list.add("Кузьмин Кирилл Дмитриевич"); list.add("Нгуен Куок Ань"); list.add("Олевская Анна Леонидовна"); list.add("Рабочих Андрей Юрьевич"); list.add("Сторожук Даниил Сергеевич"); list.add("Терентьев Павел Владимирович");
        list.add("Турсунов Парвиз Бахоралиевич"); list.add("Флоря Дмитрий"); list.add("Чимидов Эльвек Эренцович"); list.add("Шатров Савелий Иванович");
        SQLiteDatabase db = this.getWritableDatabase();
       // db.execSQL("drop table if exists " + TABLE_STUDENT);
       // onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        //добавляем строку
        for (int i =0; i < 5; i++) {
            int rand = 0 + (int) (Math.random() * list.size());
            ContentValues initialValues = createContentValues(list.get(rand), date);
            db.insert(TABLE_STUDENT, null, initialValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db.getVersion() != 1) {
            db.execSQL("drop table if exists " + TABLE_STUDENT);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("drop table if exists " + TABLE_STUDENT);
        onCreate(db);
    }

    //Описываем структуру данных
    private ContentValues createContentValues(String FIO, String TIME) {
        ContentValues values = new ContentValues();
        values.put(KEY_FIO, FIO);
        values.put(KEY_TIME, TIME);
        return values;
    }

    //Изменение строчки
    public void update() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor = db.query(true, TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME}, null,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToLast();
        }
        String id = mCursor.getString(0);
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        db.execSQL("UPDATE " + TABLE_STUDENT + " SET " + KEY_FIO + " = 'Иванов Иван Иванович', " + KEY_TIME + " = '" + date +
                "' WHERE " + KEY_ID + " = (SELECT MAX(" + KEY_ID + ") FROM " + TABLE_STUDENT + ")");
    }

    public void add(String FIO) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        ContentValues values = createContentValues(FIO, date);
        db.insert(TABLE_STUDENT, null, values);
        db.close();
    }

    //Получение всех студентов
    public Cursor getStudentTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME }, null, null, null,
                null, null);
    }

    /*Получаем одного студента
    public Cursor get(int rowId) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(true, TABLE_STUDENT, new String[] { KEY_ID, KEY_FIO, KEY_TIME, }, KEY_ID + "=" + rowId,
                null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void delete(int rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STUDENT, KEY_ID + "=" + rowId, null);
        db.close();
    }

    //Изменение строчки
    public boolean update(String ID, String FIO) {
        SQLiteDatabase db = this.getWritableDatabase();
        String date = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
        ContentValues updateValues = createContentValues(FIO, date);
        return db.update(TABLE_STUDENT, updateValues, KEY_ID + "=" + ID, null) > 0;
    }*/
}
