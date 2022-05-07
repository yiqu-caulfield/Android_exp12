package com.example.webview_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

public class HttpURLActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "HttpURLConnection";

    public static void actionStart(Context context){
        Intent intent = new Intent(context, HttpURLActivity.class);
        context.startActivity(intent);
    }

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_urlconnection);
        Button sendRequest = findViewById(R.id.button_http_url);
        responseText = findViewById(R.id.text_http_url);
        //该方法的使用需要当前类实现OnClickListener，即implements View.OnClickListener
        sendRequest.setOnClickListener(this);
    }

   public void onClick(View view){
        if(view.getId() == R.id.button_http_url){
            sendRequestWithHttpURLConnection();
        }
   }

    private void sendRequestWithHttpURLConnection() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    //获取httpURLConnection实例
                    URL url = new URL("http://weather.sina.com.cn/");
                    connection = (HttpURLConnection)url.openConnection();
                    //设置请求方式 post or get
                    connection.setRequestMethod("GET");
                    //自定义设置
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //获取服务器返回的输入流
                    InputStream inputStream = connection.getInputStream();
                    //使用BufferedReader读取输入流inputStream
                    reader = new BufferedReader(new InputStreamReader
                            (inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        //关闭这个HTTP连接
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(String response) {
        // 其中，由于android不允许在子线程中进行UI操作
        // 因此通过runOnUiThread()方法切换到主线程来更新UI元素
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在此处进行UI操作，将结果封装进TextView，显示到界面上
                responseText.setText(response);
            }
        });
    }
}