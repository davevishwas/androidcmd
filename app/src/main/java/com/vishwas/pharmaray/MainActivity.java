package com.vishwas.pharmaray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.view.KeyEvent;


public class MainActivity extends AppCompatActivity {

    WebView mWebView;

    String URL="https://pharmaray.in/";

    ProgressBar loadingProgressBar,loadingTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView) findViewById(R.id.myview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(URL);
        mWebView.setWebViewClient(new MyWebViewClient());

        loadingProgressBar=(ProgressBar)findViewById(R.id.progressbar_Horizontal

        );
        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 4.1.1; Galaxy Nexus Build/JRO03C) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
        mWebView.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);


                loadingProgressBar.setProgress(newProgress);
                //loadingTitle.setProgress(newProgress);
                // hide the progress bar if the loading is complete

                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else{
                    loadingProgressBar.setVisibility(View.VISIBLE);

                }

            }

        });

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);

    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url != null && url.startsWith("whatsapp://"))
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;


            }
            else if (url.startsWith("mailto:")) {


                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { "support@unkar.co.in" });
                intent.putExtra(Intent.EXTRA_SUBJECT, "My subject");

                startActivity(Intent.createChooser(intent, "Email via..."));
                return true;

            }
            else if (url.startsWith("tel:")) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8141200123"));
                startActivity(intent);
                return true;
            }

            else {
                view.loadUrl(url);
                return true;
            }
        }
    }



}