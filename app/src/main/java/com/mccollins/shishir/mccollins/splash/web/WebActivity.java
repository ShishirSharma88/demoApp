package com.mccollins.shishir.mccollins.splash.web;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mccollins.shishir.mccollins.R;

public class WebActivity extends AppCompatActivity {

    private android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        String data = getIntent().getExtras().getString("data");
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(data);

        // In case you have any html to draw you can use this
        //webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        webView.setHorizontalScrollBarEnabled(false);
    }
}
