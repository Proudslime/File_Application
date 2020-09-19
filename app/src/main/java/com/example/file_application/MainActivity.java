package com.example.file_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    Button btn_send, btn_get,btn_Share_send,btn_Share_get;
    EditText editname,editid;
    TextView editshow;
    private final String filename = "textFile";

    private final static String Key_Name = "姓名";
    private final static String Key_id = "学号";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_send = findViewById(R.id.send);
        btn_get = findViewById(R.id.get);
        btn_Share_get = findViewById(R.id.getShare);
        btn_Share_send =findViewById(R.id.sendShare);

        editname = findViewById(R.id.editname);
        editid = findViewById(R.id.editid);
        editshow = findViewById(R.id.show);

        preferences = getSharedPreferences(filename + "Shared",MODE_PRIVATE);
        editor = preferences.edit();

        btn_Share_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString(Key_Name,editname.getText().toString());
                editor.putString(Key_id,editid.getText().toString());
                Toast.makeText(MainActivity.this,"Shared发送成功",Toast.LENGTH_LONG).show();
                editor.apply();
            }
        });

        btn_Share_get.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String strName = preferences.getString(Key_Name,null);
                String strid = preferences.getString(Key_id,null);
                if(strName != null && strid != null){
                    editshow.setText(strName + " " + strid);
                    Toast.makeText(MainActivity.this,"Shared接受成功",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "error",Toast.LENGTH_LONG).show();
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OutputStream output = null;

                try {

                    FileOutputStream fileOutputStream = openFileOutput(filename,MODE_PRIVATE);
                    output = new BufferedOutputStream(fileOutputStream);
                    String content = editname.getText().toString() + " " + editid.getText().toString() + ".";
                    try {

                        output.write(content.getBytes(StandardCharsets.UTF_8));

                    }finally {

                        if(output != null){
                            Toast.makeText(MainActivity.this,"发送成功",Toast.LENGTH_LONG).show();
                            output.close();
                        }

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputStream input = null;
                try{

                    FileInputStream fileInputStream = openFileInput(filename);
                    input = new BufferedInputStream(fileInputStream);

                    int c;
                    StringBuilder stringBuilder = new StringBuilder("");

                    try{
                        while((c=input.read()) != -1){
                            stringBuilder.append((char)c);
                        }
                        Toast.makeText(MainActivity.this,"接受成功",Toast.LENGTH_LONG).show();
                        editshow.setText(stringBuilder.toString());
                    }finally {
                        if(input != null)
                            input.close();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
}