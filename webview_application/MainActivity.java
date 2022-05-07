package com.example.webview_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    public static void actionStart(Context context, String name, String number){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("number",number);
        context.startActivity(intent);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = findViewById(R.id.web_view);
        Button button_web = findViewById(R.id.button_web);
        Button button_http = findViewById(R.id.button_http);
        Button button_okhttp = findViewById(R.id.button_okhttp);

        button_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setJavaScriptEnabled使得webView支持javaScript脚本
                webView.getSettings().setJavaScriptEnabled(true);
                // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        // 根据传入的参数再去加载新的网页
                        view.loadUrl(url);
                        // 表示当前WebView可以处理打开新网页的请求，不用借助系统浏览器
                        return true;
                    }
                });
                webView.loadUrl("https://www.gdpu.edu.cn/");
            }
        });

        button_http.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpURLActivity.actionStart(MainActivity.this);
            }
        });

        button_okhttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpActivity.actionStart(MainActivity.this);
            }
        });
    }
}