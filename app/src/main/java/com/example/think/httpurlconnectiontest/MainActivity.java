package com.example.think.httpurlconnectiontest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=(TextView)findViewById(R.id.text_view);
        send=(Button)findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection=null;
                        BufferedReader reader;
                        try{
                            URL url=new URL("https://www.baidu.com");
                            connection=(HttpURLConnection)url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in=connection.getInputStream();
                            reader=new BufferedReader(new InputStreamReader(in));
                            StringBuilder response=new StringBuilder();
                            String line;
                            while ((line=reader.readLine())!=null)
                                response.append(line);
                            //调主线程获取response的方法
                            returnDataToUI(response.toString());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }finally {
                            if(connection!=null)
                                connection.disconnect();
                        }
                    }
                }).start();
            }
        });
    }

    public void returnDataToUI(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }
}
