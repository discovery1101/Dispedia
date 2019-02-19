package com.dispedia.work.dispedia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sqlite.SqliteOpenHelper;

public class DetailActivity extends AppCompatActivity {

    private SqliteOpenHelper helper;
    private SQLiteDatabase db;

    private final String[] columns = new String[]{"ITEM_ID", "ITEM_NAME", "ITEM_KANA", "CONTENT", "CATEGORY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 遷移元画面からパラメータを受け取る。
        Intent intent = this.getIntent();
        String tangoId = intent.getStringExtra("id");

        // DBから画面表示用のデータを取得する。
        readData(tangoId);

        Button sendTagButton1 = findViewById(R.id.tagButton1);
        Button sendTagButton2 = findViewById(R.id.tagButton2);
        Button sendTagButton3 = findViewById(R.id.tagButton3);
        Button sendEditButton = findViewById(R.id.editButton);

        sendTagButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });

        sendTagButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });

        sendTagButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });

        sendEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), EditActivity.class);
                intent.putExtra("mode", "E");
                startActivity(intent);
            }
        });
    }

    private void readData(String tangoId){

        TextView tangoName = findViewById(R.id.wordNameText);
        TextView tangoKana = findViewById(R.id.readNameText);
        TextView tangoMean = findViewById(R.id.wordMeanText);

        if(helper == null){
            helper = new SqliteOpenHelper(getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }

        Log.d("debug","**********Cursor");

        // DBに接続し、クエリを発行する。
        Cursor cursor = db.query(
                "DP_DICTIONARY",
                columns,
                "ITEM_ID = ?",
                new String[]{tangoId},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            // 検索結果が存在した場合
            tangoName.setText(cursor.getString(1));
            tangoKana.setText(cursor.getString(2));
            tangoMean.setText(cursor.getString(3));
        } else {
            TextView errMsg = findViewById(R.id.errorMsg);
            errMsg.setText("登録に失敗しました。");
            errMsg.setTextColor(Color.RED);
        }

        cursor.close();

        Log.d("debug","**********"+cursor);

    }
}
