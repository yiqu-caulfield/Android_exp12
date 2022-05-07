package com.example.webview_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpActivity extends BaseActivity {

    private static final String TAG = "OkHttpActivity";
    
    public static void actionStart(Context context){
        Intent intent = new Intent(context, OkHttpActivity.class);
        context.startActivity(intent);
    }
    
    TextView responseText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        Button sendRequest = findViewById(R.id.button_http_ok);
        responseText = findViewById(R.id.text_http_ok);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    sendRequestWithOkHttp();
            }
        });
    }

    private void sendRequestWithOkHttp() {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient实例
                    OkHttpClient client = new OkHttpClient();
                    //创建Request对象
                    Request request = new Request.Builder()
                            .url("http://weather.sina.com.cn/")
                            .build();
                    //创建一个Call对象并调用它的execute()方法发送请求并获取服务器返回的数据
                    Response response = client.newCall(request).execute();
                    if(response.body()!= null) {
                        String responseData = response.body().string();
                        showResponse(responseData);
                    }
                    else {
                        showResponse("response.body().equals(null)");
                    }
                }catch (Exception e){
                    e.printStackTrace();
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