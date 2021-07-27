package com.juice.timetable.ui.about;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    private TextView githubLink, cookApkLink, blogLink, versionView;
    private LinearLayout linearLayout;
    private Button checkUpdatesButton;
    private String id = null;

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
        checkUpdatesButton.setOnClickListener(new OnClickListener() {
            @SuppressLint("ResourceAsColor")
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

    private void findID(View root) {
        githubLink = root.findViewById(R.id.tv_github);
        blogLink = root.findViewById(R.id.blogLink);
        linearLayout = root.findViewById(R.id.linearLayout3);
        cookApkLink = root.findViewById(R.id.tv_cool_apk);
        checkUpdatesButton = root.findViewById(R.id.checkUpdatesButton);
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

    private void checkUpdate() {
        Uri uri = Uri.parse(Constant.URI_COOLAPK);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        // 获取当前app的版本号
        String currVersion = VersionUtils.getVersionCode(requireActivity());
        // 爬虫获取酷安的版本号
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = ParseVersion.getSource(Constant.URI_COOLAPK);
                    id = ParseVersion.getVersion(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 询问是否下载更新
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        if (id != null && id.equals(currVersion)) {
            Toasty.custom(requireActivity(), "已经是最新版本", getResources().getDrawable(R.drawable.about), getResources().getColor(R.color.green), getResources().getColor(R.color.white), LENGTH_SHORT, true, true).show();
        } else if (id != null) {
            builder.setTitle(getString(R.string.quit_dialog_title));
            // 好
            builder.setPositiveButton(R.string.ok_quit_dialog_title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startActivity(intent);
                }
            });
            // 取消
            builder.setNegativeButton(R.string.no_quit_dialog_title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
//
//        try {
//
//        } catch (Exception e) {
//            //暂时先放这
//            }


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
