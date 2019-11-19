package com.dispedia.work.dispedia;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;

public class SearchActivity extends AppCompatActivity {
    // 変数ベタ書き
    String id = "";
    String word = "HTML5";
    String kana = "えいちてぃーえむえるふぁいぶ";
    String content = "HyperText Markup Languageの第5版。";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button sendSearchButton = findViewById(R.id.searchButton);
        Button sendDetailButton1 = findViewById(R.id.linkButton1);
        Button sendDetailButton2 = findViewById(R.id.linkButton2);
        Button sendDetailButton3 = findViewById(R.id.linkButton3);


        sendSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SearchActivity.class);
                startActivity(intent);
            }
        });

        sendDetailButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);

                intent.putExtra("id", id);
                intent.putExtra("word", word);
                intent.putExtra("kana", kana);
                intent.putExtra("content", content);

                startActivity(intent);
            }
        });

        sendDetailButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });

        sendDetailButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), DetailActivity.class);
                startActivity(intent);
            }
        });


    }

}
