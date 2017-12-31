package es.esy.android_inyourhand.mykamus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    public static String EXTRA_WORD = "extra_word";
    public static String EXTRA_DETAIL = "extra_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvWord = findViewById(R.id.tvWord);
        TextView tvDetail = findViewById(R.id.tvDetail);
        String word = getIntent().getStringExtra(EXTRA_WORD);
        String detail = getIntent().getStringExtra(EXTRA_DETAIL);
        String title = "Detail "+word;
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvWord.setText(word);
        tvDetail.setText(detail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
