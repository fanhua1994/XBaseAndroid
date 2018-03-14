package com.hengyi.baseandroidcore.base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.tools.FileDownloader;
import com.hengyi.baseandroidcore.utils.ActivityStack;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.weight.EaseTitleBar;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

/*
 * 作者：董志平
 * 名称：通用安卓web引擎
 */
public class XBaseBrowserActivity extends XBaseActivity implements DownloadListener {
	public static final String ANDROID_ASSSET_PATH = "file:///android_asset/";
	public static final String WEB_SHOW_TITLE_BAR = "show_title_bar";
	public static final String WEB_STATUS_COLOR = "statusbar_color";
	public static final String WEB_URL_PARAM = "url";

	private EaseTitleBar easeTitleBar;
	private SwipeRefreshLayout swipe_container;
	private WebView webview;
	private LinearLayout linerLayout_webview;
	private ProgressBar progressBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		progressBar = findViewById(R.id.progressBar);
		swipe_container = findViewById(R.id.swipe_container);
		easeTitleBar = findViewById(R.id.titleBar);
		linerLayout_webview = findViewById(R.id.linerLayout_webview);
		webview = new WebView(this);
		linerLayout_webview.addView(webview);
        init();
	}

	private void init(){
		Intent i = getIntent();
		String url = i.getStringExtra(WEB_URL_PARAM);
		boolean show_title_bar = i.getBooleanExtra(WEB_SHOW_TITLE_BAR,true);
		int status_color = i.getIntExtra(WEB_STATUS_COLOR,R.color.main_color);
		StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,status_color)));

		initWeb(url);
		if(show_title_bar){
			easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					back();
				}
			});

			easeTitleBar.setRightText(getString(R.string.close));
			easeTitleBar.setRightTextClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view) {
					destory();
				}
			});
		}else{
			easeTitleBar.setVisibility(View.GONE);
		}

		swipe_container.setColorSchemeColors(getResources().getColor(R.color.main_color));
		swipe_container.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				progressBar.setVisibility(View.VISIBLE);
				swipe_container.setRefreshing(true);
				webview.reload();
			}
		});

		// 设置子视图是否允许滚动到顶部
		swipe_container.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
			@Override
			public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
				return webview.getView().getScrollY() > 0;
			}
		});
	}

	@Override
	public int setBaseContentView() {
		return R.layout.activity_web_engines;
	}

	private void initWeb(String url){
		progressBar.setVisibility(View.VISIBLE);
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		//settings.setPluginState(WebSettings.PluginState.ON);
		settings.setUseWideViewPort(true); // 关键点
		settings.setAllowFileAccess(true); // 允许访问文件
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(false);
		settings.setSupportZoom(false);
		settings.setDisplayZoomControls(false);

		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		webview.setVerticalScrollBarEnabled(false);
		webview.setHorizontalScrollBarEnabled(false);

		webview.setDownloadListener(this);
		webview.loadUrl(url);
		
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
				progressBar.setVisibility(View.VISIBLE);
				super.onPageStarted(webView, s, bitmap);
			}

			@Override
			public void onPageFinished(WebView webView, String s) {
				progressBar.setVisibility(View.GONE);
				swipe_container.setRefreshing(false);
				super.onPageFinished(webView, s);
			}
		});
		
		webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
            	progressBar.setProgress(newProgress);
            }

            @Override
			public void onReceivedTitle(WebView view, String tit) {
            		easeTitleBar.setTitle(tit);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				CustomAlertDialog alert = new CustomAlertDialog(XBaseBrowserActivity.this).builder();
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
		if(webview.canGoBack()) {
			webview.goBack();//返回上一页面
		} else {
			webview.loadUrl("about:blank");
			destory();
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
			ViewParent parent = webview.getParent();
			if (parent != null) {
				((ViewGroup) parent).removeView(webview);
			}

			webview.clearHistory();
			webview.clearCache(true);
			webview.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
			webview.pauseTimers();
			webview.stopLoading();
			webview.removeAllViews();
			webview = null;
			try {
				webview.destroy();
			} catch (Throwable ex) {

			}
		}
		ActivityStack.getInstance().popActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		destory();
	}

	@Override
	public void onDownloadStart(final String s, String s1, String s2, String s3, long l) {
		final CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext()).builder();
		customAlertDialog.setMsg("您真的要下载该文件吗？");
		customAlertDialog.setTitle("温馨提示");
		customAlertDialog.setPositiveButton("确定", new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final FileDownloader fileDownloader = FileDownloader.getInstance();
				fileDownloader.download(getContext(), s, fileDownloader.getDefaultPath(), fileDownloader.getDefaultFilename(s), new FileDownloader.DownloadStatusListener() {
					@Override
					public void onSuccess(File file) {
						toast("文件下载成功，文件存入" + fileDownloader.getDefaultPath());
					}

					@Override
					public void OnError(String message) {
						toast("文件下载失败");
					}
				}, true);
			}
		});
		customAlertDialog.setNegativeButton("取消",null);
		customAlertDialog.show();
	}
}