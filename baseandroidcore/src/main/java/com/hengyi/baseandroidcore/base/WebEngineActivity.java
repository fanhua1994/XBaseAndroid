package com.hengyi.baseandroidcore.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;

/*
 * 作者：董志平
 * 名称：通用安卓web引擎
 * 配置：支持多重地址访问  支持javascript  支持文件上传   支持Session设置
 */
public class WebEngineActivity extends BaseActivity {
	private EaseTitleBar easeTitleBar;
	private SwipeRefreshLayout swipe_container;
	private WebView webview;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		webview = (WebView) findViewById(R.id.webview);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		easeTitleBar = (EaseTitleBar) findViewById(R.id.titleBar);
        init();
	}

	private void init(){
		Intent i = getIntent();
		String url = i.getStringExtra("url");
		InitWeb(url);

		easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view) {
				ActivityStack.getInstance().popActivity(WebEngineActivity.this);
			}
		});

		swipe_container.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				progressBar.setVisibility(View.VISIBLE);
				swipe_container.setRefreshing(true);
				webview.reload();
			}
		});
	}

	@Override
	public int setBaseContentView() {
		return R.layout.activity_web_engines;
	}

	private void InitWeb(String url){
		progressBar.setVisibility(View.VISIBLE);
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setPluginState(WebSettings.PluginState.ON);
		settings.setUseWideViewPort(true); // 关键点
		settings.setAllowFileAccess(true); // 允许访问文件
		settings.setSupportZoom(true); // 支持缩放
		settings.setLoadWithOverviewMode(true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
		
		webview.loadUrl(url);
		
		webview.setWebViewClient(new WebViewClient(){
	           @Override
	        public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest request) {
	        	   progressBar.setVisibility(View.VISIBLE);
	        	   view.loadUrl(request.getUrl().toString());
	        	   return true;
	        }
       });
		
		
		webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            	progressBar.setProgress(newProgress);
            	if(newProgress == 100){
            		 swipe_container.setRefreshing(false);
            		 progressBar.setVisibility(View.GONE);
            	}
            }

            @Override
			public void onReceivedTitle(WebView view, String tit) {
            		easeTitleBar.setTitle(tit);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				CustomAlertDialog alert = new CustomAlertDialog(WebEngineActivity.this).builder();
				alert.setTitle("温馨提示");
				alert.setMsg(message);
				alert.show();
				return true;
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(webview.canGoBack())
            {
                webview.goBack();//返回上一页面
            }
            else
            {
            	webview.loadUrl("about:blank");
				ActivityStack.getInstance().popActivity(this);
            }
		}
		return true;
	}
}