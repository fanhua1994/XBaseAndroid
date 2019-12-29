package com.hengyi.baseandroidcore.browser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.hengyi.baseandroidcore.R;
import com.hengyi.baseandroidcore.base.XBaseActivity;
import com.hengyi.baseandroidcore.dialog.CustomAlertDialog;
import com.hengyi.baseandroidcore.dialog.CustomConfirmDialog;
import com.hengyi.baseandroidcore.statusbar.StatusBarCompat;
import com.hengyi.baseandroidcore.activity.ActivityStack;
import com.hengyi.baseandroidcore.utils.ColorUtils;
import com.hengyi.baseandroidcore.weight.XBaseTitleBar;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/*
 * 作者：董志平
 * 名称：内部浏览器 支持隐藏标题栏 支持不刷新 支持长按选中
 */
public class XBaseBrowserActivity extends XBaseActivity {
	public static final String ANDROID_ASSSET_PATH = "file:///android_asset/";
	public static final String START_CACHE = "start_cache";//是否开启缓存
	public static final String SHOW_TITLE_BAR = "show_title_bar";//显示标题栏
	public static final String SHOW_REFRESH = "show_refresh";//显示刷新
	public static final String STATUS_COLOR = "statusbar_color";//状态栏颜色
	public static final String SHOW_CLOSE_APP_DIALOG = "show_close_app_dialog";//显示关闭webview的弹窗提示
	public static final String SHOW_CLOSE_APP_MSG = "show_close_app_msg";//提示的内容
	public static final String WEB_URL = "url";

	private XBaseTitleBar easeTitleBar;
	private SwipeRefreshLayout swipe_container;
	private AgentWeb agentWeb;
	private LinearLayout linerLayoutWebview;

	private String webUrl = null;
	private boolean isShowCloseAppDialog = false;
	private String isShowCloseAppMsg = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	@Override
	public void init(){
		swipe_container = findViewById(R.id.swipe_container);
		easeTitleBar = findViewById(R.id.titleBar);
		linerLayoutWebview = findViewById(R.id.linerLayout_webview);

		Intent i = getIntent();
		webUrl = i.getStringExtra(WEB_URL);
		isShowCloseAppMsg = i.getStringExtra(SHOW_CLOSE_APP_MSG);
		isShowCloseAppDialog = i.getBooleanExtra(SHOW_CLOSE_APP_DIALOG,false);
		boolean showTitleBar = i.getBooleanExtra(SHOW_TITLE_BAR,true);
		boolean showRefresh = i.getBooleanExtra(SHOW_REFRESH,false);
		boolean startCache = i.getBooleanExtra(START_CACHE,false);
		int statusColor = i.getIntExtra(STATUS_COLOR,R.color.main_color);
		easeTitleBar.setBackgroundColor(getResources().getColor(statusColor));
		StatusBarCompat.setStatusBarColor(this, Color.parseColor(ColorUtils.changeColor(this,statusColor)));

		if(showTitleBar){
			easeTitleBar.setLeftLayoutClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					toBack();
				}
			});

			easeTitleBar.setRightText(getString(R.string.xbase_close));
			easeTitleBar.setRightTextClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View view) {
					ActivityStack.getInstance().popActivity();
				}
			});
		}else{
			easeTitleBar.setVisibility(View.GONE);
		}

		if(showRefresh) {
			swipe_container.setColorSchemeColors(getResources().getColor(R.color.main_color));
			swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					swipe_container.setRefreshing(true);
					agentWeb.getWebCreator().getWebView().reload();
				}
			});

			// 设置子视图是否允许滚动到顶部
			swipe_container.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
				@Override
				public boolean canChildScrollUp(SwipeRefreshLayout parent, @Nullable View child) {
					return agentWeb.getWebCreator().getWebView().getScrollY() > 0;
				}
			});
		}else {
			swipe_container.setEnabled(false);
		}

		agentWeb = AgentWeb.with(this)
				.setAgentWebParent(linerLayoutWebview, new LinearLayout.LayoutParams(-1, -1))
				.useDefaultIndicator()
				.setWebChromeClient(webChromeClient)
				.setWebViewClient(webViewClient)
				.createAgentWeb()
				.ready()
				.go(webUrl);

		if(!startCache){
			agentWeb.clearWebCache();
			AgentWebConfig.clearDiskCache(this);
		}
		agentWeb.getJsInterfaceHolder().addJavaObject("xbase",new XBaseJsMapping(this,agentWeb,getPackageName()));


	}

	@Override
	public int setBaseContentView() {
		return R.layout.activity_web_engines;
	}


		private WebViewClient webViewClient = new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest resourceRequest) {
				return super.shouldOverrideUrlLoading(view,resourceRequest);
			}

			@Override
			public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
				super.onPageStarted(webView, s, bitmap);
			}

			@Override
			public void onPageFinished(WebView webView, String s) {
				swipe_container.setRefreshing(false);
				super.onPageFinished(webView, s);
			}
		};
		
		private WebChromeClient webChromeClient = new WebChromeClient() {

			@Override
			public Bitmap getDefaultVideoPoster() {
				try{
					return BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.image_loading_default);
				}catch(Exception e){
					return super.getDefaultVideoPoster();
				}
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view,newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String tit) {
				easeTitleBar.setTitle(tit);
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
				CustomAlertDialog alert = new CustomAlertDialog(XBaseBrowserActivity.this).builder();
				alert.setTitle(getString(R.string.xbase_reminder));
				alert.setMsg(message);
				alert.show();
				result.confirm();
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
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
				CustomAlertDialog dialog = new CustomAlertDialog(XBaseBrowserActivity.this).builder();
				dialog.setTitle(getString(R.string.xbase_reminder));
				dialog.setMsg(message);

				dialog.setPositiveButton(getString(R.string.xbase_confirm), new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						result.confirm();
					}
				});

				dialog.setNegativeButton(getString(R.string.xbase_cancel), new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						result.cancel();
					}
				});

				dialog.show();

				return true;
			}

			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
				CustomConfirmDialog dialog = new CustomConfirmDialog(XBaseBrowserActivity.this).builder();
				dialog.setTitle(getString(R.string.xbase_reminder));
				dialog.setHintText(message);

				dialog.setPositiveButton(getString(R.string.xbase_confirm), new CustomConfirmDialog.OnPostListener() {
					@Override
					public void OnPost(String value) {
						result.confirm(value);
					}
				});

				dialog.setNegativeButton(getString(R.string.xbase_cancel), new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						result.cancel();
					}
				});
				dialog.show();
				return true;
			}
		};


	/**
	 * 关闭APP是否弹窗提示
	 */
	private void closeAppDialog(){
		CustomAlertDialog dialog = new CustomAlertDialog(this).builder();
		dialog.setTitle(getString(R.string.xbase_reminder));
		dialog.setMsg(isShowCloseAppMsg);

		dialog.setPositiveButton(getString(R.string.xbase_confirm),new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				ActivityStack.getInstance().popActivity();
			}
		});
		dialog.setNegativeButton(getString(R.string.xbase_cancel),null );
		dialog.show();
	}

	private void toBack(){
		if (!agentWeb.back()){
			if(isShowCloseAppDialog)
				closeAppDialog();
			else
				ActivityStack.getInstance().popActivity();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			toBack();
		}
		return true;
	}

	@Override
	protected void onPause() {
		agentWeb.getWebLifeCycle().onPause();
		super.onPause();

	}

	@Override
	protected void onResume() {
		agentWeb.getWebLifeCycle().onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		agentWeb.getWebLifeCycle().onDestroy();
		super.onDestroy();
	}

}