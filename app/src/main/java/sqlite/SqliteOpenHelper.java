package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqliteOpenHelper extends SQLiteOpenHelper {

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 1;

    // データーベース名
    private static final String DATABASE_NAME = "dispedia.db";
    private static final String TABLE_NAME = "DP_DICTIONARY";
    private static final String ITEM_ID = "ITEM_ID";
    private static final String ITEM_NAME = "ITEM_NAME";
    private static final String ITEM_KANA = "ITEM_KANA";
    private static final String ITEM_INDEX = "ITEM_INDEX";
    private static final String CONTENT = "CONTENT";
    private static final String CATEGORY = "CATEGORY";
    private static final String TAG1 = "TAG1";
    private static final String TAG2 = "TAG2";
    private static final String TAG3 = "TAG3";
    private static final String TAG4 = "TAG4";
    private static final String TAG5 = "TAG5";
    private static final String DELETE_FLG = "DELETE_FLG";
    private static final String REGIST_DATE = "REGIST_DATE";
    private static final String UPDATE_DATE = "UPDATE_DATE";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + ITEM_NAME + " TEXT,"
                    + ITEM_KANA + " TEXT,"
                    + ITEM_INDEX + " INTEGER,"
                    + CONTENT + " TEXT,"
                    + CATEGORY + " TEXT,"
                    + TAG1 + " TEXT,"
                    + TAG2 + " TEXT,"
                    + TAG3 + " TEXT,"
                    + TAG4 + " TEXT,"
                    + TAG5 + " TEXT,"
                    + DELETE_FLG + " TEXT,"
                    + REGIST_DATE + " TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')),"
                    + UPDATE_DATE + " TIMESTAMP DEFAULT (datetime(CURRENT_TIMESTAMP,'localtime')))";

    private static final String SQL_DELETE_ENTRIES;

    static {
        SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public SqliteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // テーブル作成
        // SQLiteファイルがなければSQLiteファイルが作成される
        db.execSQL(
                SQL_CREATE_ENTRIES
        );

        Log.d("debug", "onCreate(SQLiteDatabase db)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
