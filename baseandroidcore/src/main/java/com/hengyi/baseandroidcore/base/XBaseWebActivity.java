package com.hengyi.baseandroidcore.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
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
public class XBaseWebActivity extends XBaseActivity {
	public static final String WEB_SHOW_TITLE_BAR = "show_title_bar";
	public static final String WEB_URL_PARAM = "url";

	private EaseTitleBar easeTitleBar;
	private SwipeRefreshLayout swipe_container;
	private WebView webview;
	private LinearLayout linerLayout_webview;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		easeTitleBar = (EaseTitleBar) findViewById(R.id.titleBar);
		linerLayout_webview = (LinearLayout) findViewById(R.id.linerLayout_webview);
		webview = new WebView(this);
		linerLayout_webview.addView(webview);
        init();
	}

	private void init(){
		Intent i = getIntent();
		String url = i.getStringExtra(WEB_URL_PARAM);
		boolean show_title_bar = i.getBooleanExtra(WEB_SHOW_TITLE_BAR,true);
		InitWeb(url);
		if(show_title_bar){
			easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					back();
				}
			});
		}else{
			easeTitleBar.setVisibility(View.GONE);
		}

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
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				return super.shouldOverrideUrlLoading(view, request);
			}
		});
		
		webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            	progressBar.setProgress(newProgress);
				if(newProgress == 1){
					progressBar.setVisibility(View.VISIBLE);
				}
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
				CustomAlertDialog alert = new CustomAlertDialog(XBaseWebActivity.this).builder();
				alert.setTitle("温馨提示");
				alert.setMsg(message);
				alert.show();
				return true;
			}

			@Override
			public void onCloseWindow(WebView window) {
				super.onCloseWindow(window);
				ActivityStack.getInstance().popActivity();
			}

			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}

			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}
		});
	}

	private void back(){
		if(webview.canGoBack())
		{
			webview.goBack();//返回上一页面
		}
		else
		{
			webview.loadUrl("about:blank");
			destory();
			ActivityStack.getInstance().popActivity(this);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			back();
		}
		return true;
	}

	private void destory(){
		if(linerLayout_webview != null){
			linerLayout_webview.removeAllViews();
			linerLayout_webview = null;
		}
		if (webview != null) {
			// 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
			// destory()
			ViewParent parent = webview.getParent();
			if (parent != null) {
				((ViewGroup) parent).removeView(webview);
			}

			webview.clearHistory();
			webview.clearCache(true);
			webview.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
			webview.pauseTimers();
			webview.stopLoading();
			webview.getSettings().setJavaScriptEnabled(false);
			webview.removeAllViews();
			webview = null;
			try {
				webview.destroy();
			} catch (Throwable ex) {

			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		//销毁网页
		destory();
	}
}