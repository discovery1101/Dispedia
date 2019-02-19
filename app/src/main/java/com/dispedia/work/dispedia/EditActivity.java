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

public class EditActivity extends AppCompatActivity {

    protected String initWordName;
    protected String initReadName;
    protected String initContent;
    protected String changedWordName;
    protected String changedReadName;
    protected String changedContent;

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
            initWordName = intent.getStringExtra("word");
            initReadName = intent.getStringExtra("kana");
            initContent = intent.getStringExtra("content");
            */
            initWordName = "HTML5";
            initReadName = "えいちてぃーえむえるふぁいぶ";
            initContent = "HyperText Markup Languageの第5版。今まで複雑だった処理が簡単に出来て、HTMLをより構造的にスッキリ書ける。\n\nテキストエリアは、入力が指定したサイズを越えた場合スクロールします。\n\n\n\n\n\n\n\n\n\n\n\nここまで";
            inputWordName.setText(initWordName);
            inputReadName.setText(initReadName);
            inputContent.setText(initContent);
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
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
