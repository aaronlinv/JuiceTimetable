package com.juice.timetable.ui.about;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;

import es.dmoral.toasty.Toasty;

import static es.dmoral.toasty.Toasty.LENGTH_SHORT;

public class AboutFragment extends Fragment {
    private TextView githubLink;
    private TextView blogLink;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        findID(root);
        // 隐藏 toolbar 的按钮 和星期下拉菜单按钮
        Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);
        toolbar.findViewById(R.id.spinner).setVisibility(View.INVISIBLE);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);

        blogLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                joinEmail();
            }
        });
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        if (githubLink.getText() instanceof Spannable) {
            Spannable github = (Spannable) githubLink.getText();
            github.setSpan(mNoUnderlineSpan, 0, github.length(), Spanned.SPAN_MARK_MARK);
        }
        return root;
    }

    private void findID(View root) {
        githubLink = root.findViewById(R.id.tv_github);
        blogLink = root.findViewById(R.id.blogLink);

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
            // 未安装手Q或安装的版本不支持
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
