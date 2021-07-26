package com.juice.timetable.ui.about;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.parse.ParseVersion;
import com.juice.timetable.utils.VersionUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

public class AboutFragment extends Fragment {
    private TextView githubLink,cookApkLink,blogLink,checkUpdateView,versionView;
    private LinearLayout linearLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        findID(root);
        versionView.setText(VersionUtils.getVersionCode(requireActivity()));

        // 隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEmail();
            }
        });
        blogLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEmail();
            }
        });
        checkUpdateView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        if (githubLink.getText() instanceof Spannable) {
            Spannable github = (Spannable) githubLink.getText();
            github.setSpan(mNoUnderlineSpan, 0, github.length(), Spanned.SPAN_MARK_MARK);
        }
        if (cookApkLink.getText() instanceof Spannable) {
            Spannable cookApk = (Spannable) cookApkLink.getText();
            cookApk.setSpan(mNoUnderlineSpan, 0, cookApk.length(), Spanned.SPAN_MARK_MARK);
        }
        return root;
    }

    private void findID(View root){
        githubLink = root.findViewById(R.id.tv_github);
        blogLink = root.findViewById(R.id.blogLink);
        linearLayout = root.findViewById(R.id.linearLayout3);
        cookApkLink = root.findViewById(R.id.tv_cool_apk);
        checkUpdateView = root.findViewById(R.id.checkUpdatesText);
        versionView = root.findViewById(R.id.tv_version);
    }

    @SuppressLint("IntentReset")
    private void joinEmail() {
        Uri uri = Uri.parse("mailto:aaronlinv@outlook.com");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_TEXT, "“对于<橙汁>的意见反馈"); // 正文
        intent.putExtra(Intent.EXTRA_SUBJECT, "我的建议");
        try {
            startActivity(Intent.createChooser(intent, "请选择"));
        } catch (Exception e) {
            Toasty.custom(requireActivity(), "您没有任何邮箱软件", getResources().getDrawable(R.drawable.ic_error), getResources().getColor(R.color.red), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
            Toasty.Config.reset();
        }
    }

    private void checkUpdate() throws Exception {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = ParseVersion.getSource(Constant.URI_COOLAPK);
                    String id = ParseVersion.getVersion(str);
                    String currVersion = VersionUtils.getVersionCode(requireActivity());

                    if(id.equals(currVersion)){
                        System.out.println("yes");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();




//        Uri uri = Uri.parse("https://www.coolapk.com/apk/com.juice.timetable");
//        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
//        try {
//            startActivity(intent);
//        } catch (Exception e) {
//            //暂时先放这
//            Toasty.custom(requireActivity(), "已经是最新版本", getResources().getDrawable(R.drawable.about), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
//        }
    }

    //获取手机所有包名
    private void initAppList() {
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            System.out.println(packageInfo.packageName);
        }
    }

    /*******
     * 去除下划线
     */
    public static class NoUnderlineSpan extends UnderlineSpan {
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);
        }
    }
}
