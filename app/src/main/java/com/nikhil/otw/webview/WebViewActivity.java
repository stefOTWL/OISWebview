package com.nikhil.otw.webview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nikhil.otw.webview.utils.SharedPrefUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.nikhil.otw.webview.utils.Constants.getAppVersion;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.logout)
    ImageView logout;
    @BindView(R.id.version)
    TextView version;

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.iv_notif_icon)
    ImageView ivNotifIcon;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.toolbar_container)
    LinearLayout toolbarContainer;
    private String websiteName;
    private ProgressDialog progressDialog;
    private String websiteLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

        websiteLink = getIntent().getStringExtra("websiteLink");
        websiteName = getIntent().getStringExtra("websiteName");
        title.setText(websiteName);
        version.setText('v' + getAppVersion(this));


//        loadingProgress = (ProgressBar) findViewById(R.id.page_loading);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

//        prefsHelper = PreferenceHelper.getInstance(getApplicationContext());


        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setJavaScriptEnabled(true);

        // Register a new JavaScript interface called HTMLOUT /
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

        webView.setWebViewClient(new WebViewClientPaymentGateway());
        webView.setWebChromeClient(new WebChromeClient());

        CookieSyncManager.createInstance(WebViewActivity.this);
        CookieSyncManager.getInstance().sync();

        CookieManager.getInstance().removeAllCookie();
        webView.clearCache(true);

        webView.loadUrl(websiteLink);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showAlert();

            }
        });

    }

    private void showAlert() {

        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(WebViewActivity.this);
        builder.setTitle("Choose an action");

// add a list
        String[] animals = {"Go back", "Exit Application", "Logout"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        onBackPressed();
                        break;
                    case 1:
                        exitApplication();
                        break;
                    case 2:
                        SharedPrefUtils.put(WebViewActivity.this,"mobile","");
                        exitApplication();
                        break;

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void exitApplication() {

//        getApplication()
        finishAffinity();
        System.exit(0);


    }

    private class MyJavaScriptInterface {

        @JavascriptInterface
        public void onUrlChange(String url) {
            //Log.d("hydrated", "onUrlChange" + url);
        }

        @JavascriptInterface
        public void showHTML(String html) {

            //Log.d("html", "html----->" + html);
        }


        @SuppressWarnings("unused")
        @JavascriptInterface
        public void processHTML(String html) {
            // process the html as needed by the app


        }
    }

    private class WebViewClientPaymentGateway extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            return false;

        }

        public void onPageFinished(WebView paramWebView, String paramString) {
            progressDialog.dismiss();
        }

        public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap) {

        }

        public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2) {


        }
    }

}
