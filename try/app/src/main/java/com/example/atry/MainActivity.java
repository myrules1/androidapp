package com.example.atry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdBlocker.init(this);
/*

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

 */
        WebView webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setSupportMultipleWindows(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(false);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        webView.setWebViewClient(new MyBrowser());





        webView.loadUrl("https://yandex.com/games/app/196254#app-id=196254&catalog-session-uid=catalog-b92a38ab-798c-5fa4-b4ac-bd7125ab61b2-1676104962622-d338&rtx-reqid=3418776672751785468&pos=%7B%22listType%22%3A%22suggested%22%2C%22tabCategory%22%3A%22common%22%7D");
    }


}
class MyBrowser extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    private Map<String, Boolean> loadedUrls = new HashMap<>();
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        boolean ad;
        if (!loadedUrls.containsKey(url)) {
            ad = AdBlocker.isAd(url);
            loadedUrls.put(url, ad);
        } else {
            ad = loadedUrls.get(url);
        }
        return ad ? AdBlocker.createEmptyResource() :
                super.shouldInterceptRequest(view, url);
    }
}