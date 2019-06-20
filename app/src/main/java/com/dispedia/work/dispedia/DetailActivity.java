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
        String wordNameText = intent.getStringExtra("word");
        String readNameText = intent.getStringExtra("kana");
        String wordMeanText = intent.getStringExtra("content");
        String mode = intent.getStringExtra("mode");
        TextView msg = findViewById(R.id.errorMsg);

        if (tangoId == null) {
            // 単語IDが存在しない場合
            tangoId = "";
        }

        if (mode == null) {
            // 遷移元画面のモードが存在しない場合
            mode = "";
        }

        boolean isError = false;

        // TODO 入力チェック
        if(wordNameText.isEmpty()) {
            isError = true;
        }
        if(readNameText.isEmpty()) {
            isError = true;
        }
        if(wordMeanText.isEmpty()) {
            isError = true;
        }

        if(isError){
            // TODO エラーメッセージ
            setMsg(msg, "システムエラー", Color.RED);
        } else {
            setMsg(msg, "登録が完了しました", Color.BLUE);
        }

        TextView tangoName = findViewById(R.id.wordNameText);
        TextView tangoKana = findViewById(R.id.readNameText);
        TextView tangoMean = findViewById(R.id.wordMeanText);

        // DBから画面表示用のデータを取得する。
        // readData(tangoId, mode);

        tangoName.setText(wordNameText);
        tangoKana.setText(readNameText);
        tangoMean.setText(wordMeanText);


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

    private void readData(String tangoId, String mode){

        TextView tangoName = findViewById(R.id.wordNameText);
        TextView tangoKana = findViewById(R.id.readNameText);
        TextView tangoMean = findViewById(R.id.wordMeanText);
        TextView msg = findViewById(R.id.errorMsg);

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

            editMsg(mode, msg, Color.BLUE);

        } else {
            // 検索結果が存在しない場合
            editMsg(mode, msg, Color.RED);
        }

        cursor.close();

        Log.d("debug","**********"+cursor);

    }

    private void editMsg(String mode, TextView msg, int msgColor) {
        switch (mode) {
            case "R":
                // 新規登録の場合
                if (Color.BLUE == msgColor) {
                    // 正常の場合(文字の色が青)
                    setMsg(msg, "登録が完了しました。", Color.BLUE);
                } else {
                    // 上記以外の場合
                    setMsg(msg, "登録に失敗しました。", Color.RED);
                }
                break;
            case "E":
                // 編集の場合
                if (Color.BLUE == msgColor) {
                    // 正常の場合(文字の色が青)
                    setMsg(msg, "編集が完了しました。", Color.BLUE);
                } else {
                    // 上記以外の場合
                    setMsg(msg, "編集に失敗しました。", Color.RED);
                }
                break;
            case "S":
                // 検索の場合
                if (Color.BLUE == msgColor) {
                    // 正常の場合(文字の色が青)
                    setMsg(msg, "検索が完了しました。", Color.BLUE);
                } else {
                    // 上記以外の場合
                    setMsg(msg, "検索に失敗しました。", Color.RED);
                }
                break;
            default:
                // 存在しない場合
                setMsg(msg, "システムエラー", Color.RED);
        }
    }

    private void setMsg(TextView msg, String msgText, int msgColor) {
        msg.setText(msgText);
        msg.setTextColor(msgColor);
    }
}
