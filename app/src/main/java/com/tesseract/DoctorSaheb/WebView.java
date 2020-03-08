package com.tesseract.DoctorSaheb;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toolbar;

public class WebView extends AppCompatActivity {
    android.webkit.WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        webView = findViewById(R.id.webview);
         progressBar = findViewById(R.id.progress);
        String url = getIntent().getStringExtra("site");
        String title = getIntent().getStringExtra("title");
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.setWebChromeClient( new MyWebChromeClient());
        webView.setWebViewClient( new webClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);


    }
    public class MyWebChromeClient extends WebChromeClient {
        public void onProgressChanged(android.webkit.WebView view, int newProgress) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(newProgress);
        }
    }

    public class webClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(android.webkit.WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            progressBar.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }
}

