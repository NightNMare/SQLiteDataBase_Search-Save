package com.example.test8;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText genreview, titleview, contentview,search;
    Button btn1, savebtn, btn2;
    AlertDialog listDialog;
    TextView result_genre,result_content;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        genreview = findViewById(R.id.genre);
        titleview = findViewById(R.id.title);
        contentview = findViewById(R.id.content);
        btn1 = findViewById(R.id.btn1);
        savebtn = findViewById(R.id.save_btn);
        search=findViewById(R.id.search);
        btn2= findViewById(R.id.btn2);
        result_content = findViewById(R.id.result_content);
        result_genre = findViewById(R.id.result_genre);
        //탭화면 만들기
        TabHost tabHost = findViewById(R.id.host);
        tabHost.setup();
        TabHost.TabSpec spec;
        //입력탭
        spec = tabHost.newTabSpec("tab1");
        spec.setIndicator("입력");
        spec.setContent(R.id.tab1);
        tabHost.addTab(spec);
        //조회탭
        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator("조회");
        spec.setContent(R.id.tab2);
        tabHost.addTab(spec);

        btn1.setOnClickListener(this);
        savebtn.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn1) {
            //알림대화상자 장르 선택
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("장르 선택");
            builder.setSingleChoiceItems(R.array.genre, index, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    index = which;
                }
            });
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String[] datas = getResources().getStringArray(R.array.genre);
                    String s = datas[index];
                    genreview.setText(s);
                }
            });
            builder.setNegativeButton("취소", null);
            listDialog = builder.create();
            listDialog.show();
        } else if (v == savebtn) {
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String genre = genreview.getText().toString();
            String title = titleview.getText().toString();
            String content = contentview.getText().toString();
            String insertSQL = "insert into song(genre,title,content) values('" + genre + "', '" + title + "', '" + content + "')";
            try {
                db.execSQL(insertSQL);
            } catch (Exception e) {
                Log.e("insertSQL", "Error");
                Log.e("content",insertSQL);
            }
            Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
            db.close();
            //저장

        }else if(v==btn2){
            DBHelper dbHelper = new DBHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String inquirySQL = "select * from song";
            //데이터 받아오기
            Cursor cursor = db.rawQuery(inquirySQL,null);
            Log.e("title",search.getText().toString());
            while(cursor.moveToNext()){
                Log.e("cursor",cursor.getString(2));
                Log.e("genre and content",cursor.getString(1)+" "+cursor.getString(3));
                Log.e(cursor.getString(2),search.getText().toString());
                if(cursor.getString(2).equals(search.getText().toString())){
                    String genre = cursor.getString(1);
                    String content = cursor.getString(3);
                    result_genre.setText(genre);
                    result_content.setText(content);
                    Log.e("result_genre", genre);
                    Log.e("result_content", content);

                    break;
                }
            }
            db.close();

        }
    }
}
