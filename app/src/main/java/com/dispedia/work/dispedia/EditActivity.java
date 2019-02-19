package com.dispedia.work.dispedia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Spinner;
import java.util.ArrayList;
import sqlite.SqliteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;

public class EditActivity extends AppCompatActivity {

    protected String initWordName;
    protected String initReadName;
    protected String initContent;
    protected String changedWordName;
    protected String changedReadName;
    protected String changedContent;
    protected String id;
    protected String word;
    protected String kana;
    protected String content;

    private SqliteOpenHelper helper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        TextView errorMsg = findViewById(R.id.errorMsg);
        errorMsg.setVisibility(View.GONE);
        TextView flatLine = findViewById(R.id.flatLine);
        flatLine.setVisibility(View.GONE);

        Intent intent = this.getIntent();
        String mode = intent.getStringExtra("mode");

        RadioGroup radioEditMode = findViewById(R.id.radioEditMode);
        RadioButton register = findViewById(R.id.registerMode);

        // 新規登録の場合
        if ("R".equals(mode)) {
            radioEditMode.check(R.id.registerMode);
            radioEditMode.setVisibility(View.GONE);

            // 編集の場合
        } else if ("E".equals(mode)) {
            register.setVisibility(View.GONE);
            radioEditMode.check(R.id.editMode);

            EditText inputWordName = findViewById(R.id.inputWordName);
            EditText inputReadName = findViewById(R.id.inputReadName);
            EditText inputContent = findViewById(R.id.inputContent);
            //TODO 本当は詳細画面から値を持ってきて設定する
            /*
            id = intent.getStringExtra("id");
            initWordName = intent.getStringExtra("word");
            initReadName = intent.getStringExtra("kana");
            initContent = intent.getStringExtra("content");
            */
            id = "0005";
            initWordName = "HTML5";
            initReadName = "えいちてぃーえむえるふぁいぶ";
            initContent = "HyperText Markup Languageの第5版。今まで複雑だった処理が簡単に出来て、HTMLをより構造的にスッキリ書ける。\n\nテキストエリアは、入力が指定したサイズを越えた場合スクロールします。\n\n\n\n\n\n\n\n\n\n\n\nここまで";
            inputWordName.setText(initWordName);
            inputReadName.setText(initReadName);
            inputContent.setText(initContent);

            // TODO エラーメッセージがある場合、メッセージエリアにメッセージを表示
        }

        radioEditMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton edit = findViewById(R.id.editMode);
                RadioButton delete = findViewById(R.id.deleteMode);
                EditText inputWordName = findViewById(R.id.inputWordName);
                EditText inputReadName = findViewById(R.id.inputReadName);
                EditText inputContent = findViewById(R.id.inputContent);
                Spinner spinnerRelationTag = findViewById(R.id.spinnerRelationTag);

                // 編集を選択した場合
                if (edit.isChecked()) {
                    inputWordName.setFocusable(true);
                    inputReadName.setFocusable(true);
                    inputContent.setFocusable(true);
                    spinnerRelationTag.setFocusable(true);
                    inputWordName.setEnabled(true);
                    inputReadName.setEnabled(true);
                    inputContent.setEnabled(true);
                    spinnerRelationTag.setEnabled(true);

                    inputWordName.setText(changedWordName);
                    inputReadName.setText(changedReadName);
                    inputContent.setText(changedContent);

                    // 削除を選択した場合
                } else if (delete.isChecked()) {
                    inputWordName.setEnabled(false);
                    inputReadName.setEnabled(false);
                    inputContent.setEnabled(false);
                    spinnerRelationTag.setEnabled(false);
                    inputWordName.setFocusable(false);
                    inputReadName.setFocusable(false);
                    inputContent.setFocusable(false);
                    spinnerRelationTag.setFocusable(false);

                    changedWordName = inputWordName.getText().toString();
                    changedReadName = inputReadName.getText().toString();
                    changedContent = inputContent.getText().toString();

                    inputWordName.setText(initWordName);
                    inputReadName.setText(initReadName);
                    inputContent.setText(initContent);
                }
            }
        });

        Button executeButton = findViewById(R.id.executeButton);
        executeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQLite接続のための初期処理
                if(helper == null){
                    helper = new SqliteOpenHelper(getApplicationContext());
                }
                if(db == null){
                    db = helper.getWritableDatabase();
                }

                // モードにより処理を分岐する
                RadioButton register = findViewById(R.id.registerMode);
                RadioButton edit = findViewById(R.id.editMode);
                RadioButton delete = findViewById(R.id.deleteMode);

                // 登録の場合
                if (register.isChecked()) {
                    EditText inputWordName = findViewById(R.id.inputWordName);
                    EditText inputReadName = findViewById(R.id.inputReadName);
                    EditText inputContent = findViewById(R.id.inputContent);

                    word = inputWordName.getText().toString();
                    kana = inputReadName.getText().toString();
                    content = inputContent.getText().toString();
                    boolean isError = false;
                    ArrayList<String> messages = new ArrayList<String>();

                    if(word.isEmpty()) {
                        messages.add("単語名が未入力です。");
                        isError = true;
                    }

                    if(kana.isEmpty()) {
                        messages.add("かなが未入力です。");
                        isError = true;
                    }

                    if(content.isEmpty()) {
                        messages.add("意味が未入力です。");
                        isError = true;
                    }

                    if(isError) {
                        Intent intent = new Intent(getApplication(), EditActivity.class);
                        intent.putExtra("messages", messages);
                        startActivity(intent);
                        // TODO 上記の記述だけで、続く処理を行わず詳細画面に遷移するか確認する。
                    }
                    insertData(db, id, word, kana, content);

                    // DBの通信処理が不正の場合、エラーメッセージを表示する。
                    // 正常の場合、詳細画面に遷移し登録完了のメッセージを表示する。
                    Intent intent = new Intent(getApplication(), DetailActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("mode", "E");
                    startActivity(intent);

                    // 編集の場合
                } else if(edit.isChecked()) {
                    // TODO

                    // 削除の場合
                } else if(delete.isChecked()) {
                    // TODO

                }

            }
        });
    }

    private void insertData(SQLiteDatabase db, String id, String word, String kana,String content) {

        ContentValues values = new ContentValues();
        values.put("ITEM_ID", id);
        values.put("ITEM_NAME", word);
        values.put("ITEM_KANA", kana);
        values.put("CONTENT", content);
        values.put("CATEGORY", "");
        values.put("TAG1", "");
        values.put("TAG2", "");
        values.put("TAG3", "");
        values.put("TAG4", "");
        values.put("TAG5", "");
        values.put("DELETE_FLG", "");
        values.put("REGIST_DATE", "");
        values.put("UPDATE_DATE", "");

        db.insert("DP_DICTIONARY", null, values);

    }
}
