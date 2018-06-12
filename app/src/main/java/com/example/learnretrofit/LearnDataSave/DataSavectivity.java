package com.example.learnretrofit.LearnDataSave;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.learnretrofit.LearnVolley.learnVolley;
import com.example.learnretrofit.R;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.crud.LitePalSupport;

import java.util.List;

public class DataSavectivity extends AppCompatActivity {

    private EditText editText;
    private Button savedataBt,readDataBt,sQLCreateBt, sQLAddDataBt, sQLQueryDataBt,
            sQLUpdataDataBt,sQLDeleteDataBt;

    private Button litePalCreat_Bt,litePalAddData_Bt,litePalUpData_Bt,litePalDeleteData_Bt,litePalQueryData_Bt;

    private MyDatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_savectivity);
//        initLitePalView();
        learnVolley.useVolley(getApplicationContext());
    }

    private void initLitePalView() {
        litePalCreat_Bt = findViewById(R.id.datasave_LitePalCreat_Bt);
        litePalCreat_Bt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });
        litePalAddData_Bt = findViewById(R.id.datasave_LitePalAddData_Bt);
        litePalAddData_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setName("圣墟");
                book.setPages(255);
                book.setPrice(52.22);
                book.setPress("不知道");
                book.setAuthor("辰东");
                if(book.save()){
                    Log.d("==============", "onClick: 圣墟保存成功");
                }
                Book book1 = new Book();
                book1.setName("斗破苍穹");
                book1.setPages(4566);
                book1.setPrice(562);
                book1.setPress("不知道");
                book1.setAuthor("天蚕土豆");
                if(book1.save()){
                    Log.d("==============", "onClick: 斗破苍穹保存成功");
                }
                Book book2 = new Book();
                book2.setName("盘龙");
                book2.setPages(22222);
                book2.setPrice(10);
                book2.setPress("不知道");
                book2.setAuthor("我吃西红柿");
                book2.save();
                if(book2.save()){
                    Log.d("==============", "onClick: 盘龙保存成功");
                }

            }
        });

        litePalUpData_Bt = findViewById(R.id.datasave_LitePalUpdata_Bt);
        litePalUpData_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
//                book.setName("完美世界");
//                book.setPages(2562);
//                book.setPrice(80);
//                book.setPress("不知道");
//                book.save();
//                book.setPrice(236);
//                book.save();

                book.setPress("修改之后的");
                book.setPages(320);
                book.setToDefault("price");
                book.updateAll("name = ?","圣墟");
            }
        });

        litePalDeleteData_Bt = findViewById(R.id.datasave_LitePalDelete_Bt);
        litePalDeleteData_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class,"price < ?","15");
            }
        });

        litePalQueryData_Bt = findViewById(R.id.datasave_LitePalQueryData_Bt);
        litePalQueryData_Bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport
                        .select("name","author")
                        .where("pages > ?","50")
                        .order("price desc")
                        .limit(10)
                        .offset(10)
                        .find(Book.class);
                DataSupport.findAll(Book.class);
                for (Book b: books) {
                    Log.d("00000000000000", "name = "+b.getName()+",author = "+b.getAuthor()+
                    ",price = "+b.getPrice()+",pages = "+b.getPages()+",press = "+b.getPress());
                }
                Log.d("", "========================");
            }
        });
    }

    private void initSQLView(){
        editText = findViewById(R.id.dataSave_Et);
        savedataBt = findViewById(R.id.dataSave_SaveBt);
        savedataBt.setText("保存数据");
        savedataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        readDataBt = findViewById(R.id.dataSave_ReadBt);
        readDataBt.setText("读取数据");
        readDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });




    }
    //文件保存
    private void useFileSave(){
        SaveDataByFile.saveData(DataSavectivity.this,"data",editText.getText().toString());
    }

    //文件读取
    private void useFileRead(){
        String s = SaveDataByFile.readData(DataSavectivity.this,"data");
        editText.setText(s);
    }

    //SharedPreferences保存
    private void useSharedPreferencesSave(){
        SharedPreferences preferences = DataSavectivity.this.getSharedPreferences("data",MODE_PRIVATE);
        //名字为当前活动的类名
        SharedPreferences preferences1 = DataSavectivity.this.getPreferences(MODE_PRIVATE);

        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(DataSavectivity.this);

        preferences.edit()
                .putString("name",editText.getText().toString())
                .apply();
    }

    //SharedPreferences读取
    private void useSharedPreferencesRead(){
        SharedPreferences preferences = DataSavectivity.this.getSharedPreferences("data",MODE_PRIVATE);
        editText.setText(preferences.getString("name","错误"));
    }

    //使用安卓自带的数据库
    private void useSQLiteDatabase(){
        dbHelper = new MyDatabaseHelper(this,"BookStore.db",null,8);

        sQLCreateBt = findViewById(R.id.dataSave_SQL_CREATE_Bt);
        sQLCreateBt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dbHelper.getWritableDatabase();
            }
        });

        //添加
        sQLAddDataBt = findViewById(R.id.datasave_SQL_AddData_Bt);
        sQLAddDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","大笑江湖");
                values.put("pages",456);
                values.put("price",20.12);
                values.put("author","作者");
                writableDatabase.insert("Book",null,values);
                values.clear();
                values.put("name","龙王传说");
                values.put("pages",5555555);
                values.put("price",85);
                values.put("author","我吃西红柿");
                writableDatabase.insert("Book",null,values);
                values.clear();
                values.put("name","圣墟");
                values.put("pages",123);
                values.put("price",552.1);
                values.put("author","辰东");
                writableDatabase.insert("Book",null,values);
                values.clear();
                values.put("name","完美世界");
                values.put("pages",4554);
                values.put("price",85.23);
                values.put("author","辰东");
                writableDatabase.insert("Book",null,values);
                values.clear();
                values.put("name","斗破苍穹");
                values.put("pages",11111);
                values.put("price",352);
                values.put("author","唐家三少");
                writableDatabase.insert("Book",null,values);
            }
        });

        //查询
        sQLQueryDataBt = findViewById(R.id.datasave_SQL_QueryData_Bt);
        sQLQueryDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("Book",null,null,null,
                        null,null,null);
                if(cursor.moveToFirst()){
                    do{
                        Log.d("---Query---name---",""+cursor.getString(cursor.getColumnIndex("name")));
                        Log.d("---Query---author---",""+cursor.getString(cursor.getColumnIndex("author")));
                        Log.d("---Query---price---",cursor.getDouble(cursor.getColumnIndex("price"))+"");
                        Log.d("---Query---pages---",""+cursor.getInt(cursor.getColumnIndex("pages")));
                        Log.d("-----------", "onClick: -----------------------------");
                    }while (cursor.moveToNext());
                }else {
                    Log.d("----------", "onClick: 数据库为空");
                }
                System.out.println("=====================================================");
            }
        });


        //更新数据
        sQLUpdataDataBt = findViewById(R.id.datasave_SQL_Updata_Bt);
        sQLUpdataDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",122);
                writableDatabase.update("Book",values,"price = ?",new String[]{"85"});
            }
        });

        //删除
        sQLDeleteDataBt = findViewById(R.id.datasave_SQL_Delete_Bt);
        sQLDeleteDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
                writableDatabase.delete("Book","pages < ?",new String[]{"10000"});
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
