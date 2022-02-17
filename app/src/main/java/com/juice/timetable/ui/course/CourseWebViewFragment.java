package com.juice.timetable.ui.course;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.utils.CookieUtils;

public class CourseWebViewFragment extends Fragment {
    WebView originCourseWeb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_course_web_view, container, false);

        //隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);

        getWeb(root, Constant.URI_CUR_WEEK);

        return root;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void getWeb(View root, String url) {
        //从数据库获取cookies
        String prefCookie = CookieUtils.getCookie(Constant.PREF_EDU_COOKIE).replaceAll("path=/;", "");

        setCookie(url, prefCookie);

        originCourseWeb = root.findViewById(R.id.originCourseWeb);
        originCourseWeb.getSettings().setSupportZoom(true);
        originCourseWeb.getSettings().setBuiltInZoomControls(true);
        //启用JavaScript
        originCourseWeb.getSettings().setJavaScriptEnabled(true);
        //访问url
        originCourseWeb.loadUrl(url);
        originCourseWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        TextView textView = root.findViewById(R.id.text_Loading);
        originCourseWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                    textView.setText(progress);
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                    textView.setText(progress);
                }
            }
        });
    }


    //设置webView的cookies
    private void setCookie(String url, String cookie) {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeSessionCookies(null);
        cookieManager.flush();
        cookieManager.setAcceptCookie(true);

        String[] c = cookie.split(";");
        for (String i : c) {
            cookieManager.setCookie(url, i);
        }
    }


    @Override
    public void onDestroy() {
        if (originCourseWeb != null) {
            originCourseWeb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            originCourseWeb.clearHistory();

            ((ViewGroup) originCourseWeb.getParent()).removeView(originCourseWeb);
            originCourseWeb.destroy();
            originCourseWeb = null;
        }
        super.onDestroy();
    }
}