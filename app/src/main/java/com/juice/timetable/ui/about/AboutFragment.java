package com.juice.timetable.ui.about;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;
import com.juice.timetable.app.Constant;
import com.juice.timetable.data.parse.ParseVersion;
import com.juice.timetable.utils.LogUtils;
import com.juice.timetable.utils.VersionUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class AboutFragment extends Fragment {
    private TextView developer, cookApk, github, versionView, feedback;
    private Button checkUpdatesButton;
    private Handler mHandler;
    private String info;
    private String id;

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

        setOnClickListener();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkUpdate();
    }

    // 按钮点击跳转功能
    private void setOnClickListener() {
        checkUpdatesButton.setOnClickListener(v -> {
            // 爬虫获取酷安的版本号
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    try {
                        LogUtils.getInstance().d("检查更新爬虫线程启动");
                        String str = ParseVersion.getSource(Constant.URI_COOLAPK);
                        id = ParseVersion.getVersion(str);
                        info = ParseVersion.getVersionInfo(str);
                        LogUtils.getInstance().d("酷安id-->" + id);

                        message.what = Constant.MSG_COOLAPKID_SUCCESS;
                        mHandler.sendMessage(message);
                    } catch (Exception e) {
                        // 网络不好的情况
                        LogUtils.getInstance().d("setOnRefreshListener：" + e.getMessage());
                        if (e.getMessage().contains("Unable to resolve host")) {
                            message.obj = "网络好像不太好，请检查网络";
                            mHandler.sendMessage(message);
                        } else {
                            message.obj = e.getMessage();
                        }
                    }
                }
            }).start();
        });
        github.setOnClickListener(v -> {
            Uri uri = Uri.parse(Constant.URI_GITHUB);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        cookApk.setOnClickListener(v -> {
            Uri uri = Uri.parse(Constant.URI_COOLAPK);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
        feedback.setOnClickListener(v -> joinEmail());
        developer.setOnClickListener(v -> new SweetAlertDialog(requireContext(), SweetAlertDialog.NORMAL_TYPE)
                .setContentText("happy_tree_friends <br><br>PlanB")
                .hideConfirmButton()
                .show());
    }

    private void findID(View root) {
        github = root.findViewById(R.id.tv_github);
        developer = root.findViewById(R.id.tv_developer);
        cookApk = root.findViewById(R.id.tv_cool_apk);
        checkUpdatesButton = root.findViewById(R.id.checkUpdatesButton);
        versionView = root.findViewById(R.id.tv_version);
        feedback = root.findViewById(R.id.tv_feedback);
    }

    @SuppressLint({"IntentReset", "UseCompatLoadingForDrawables"})
    private void joinEmail() {
        Uri uri = Uri.parse(getResources().getString(R.string.email));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_TEXT, "“对于<橙汁>的意见反馈"); // 正文
        intent.putExtra(Intent.EXTRA_SUBJECT, "我的建议");
        try {
            startActivity(Intent.createChooser(intent, "请选择"));
        } catch (Exception e) {
            Toasty.custom(requireActivity(), "您没有任何邮箱软件",
                    getResources().getDrawable(R.drawable.ic_error, null),
                    getResources().getColor(R.color.red, null),
                    getResources().getColor(R.color.white, null),
                    LENGTH_SHORT, true, true).show();
            Toasty.Config.reset();
        }
    }

    private void checkUpdate() {
        Uri uri = Uri.parse(Constant.URI_COOLAPK);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // 获取当前app的版本号
        String currVersion = VersionUtils.getVersionCode(requireActivity());
        // 接受线程消息
        mHandler = new Handler(Looper.getMainLooper()) {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                // 询问是否下载更新
                if (msg.what == Constant.MSG_COOLAPKID_SUCCESS) {
                    if (id.equals(currVersion)) {
                        Toasty.custom(requireActivity(), "已经是最新版本",
                                getResources().getDrawable(R.drawable.about, null),
                                getResources().getColor(R.color.green, null),
                                getResources().getColor(R.color.white, null),
                                LENGTH_SHORT, false, true).show();
                    } else {
                        new AlertDialog.Builder(requireActivity())
                                .setTitle(getString(R.string.new_version_dialog_title))
                                .setMessage(info.replace(" ", "\n"))
                                .setPositiveButton(R.string.ok_quit_dialog_title, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton(R.string.no_quit_dialog_title, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                    }
                } else if (msg.obj == "网络好像不太好，请检查网络") {
                    Toasty.custom(requireActivity(),
                            msg.obj.toString(),
                            getResources().getDrawable(R.drawable.ic_error, null),
                            getResources().getColor(R.color.red, null),
                            getResources().getColor(R.color.white, null),
                            LENGTH_SHORT, true, true).show();
                }
            }
        };
    }

    //获取手机所有包名
    private void initAppList() {
        PackageManager packageManager = requireActivity().getPackageManager();
        @SuppressLint("QueryPermissionsNeeded")
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            System.out.println(packageInfo.packageName);
        }
    }
}
