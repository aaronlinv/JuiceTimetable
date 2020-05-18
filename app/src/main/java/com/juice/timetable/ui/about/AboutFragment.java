package com.juice.timetable.ui.about;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.juice.timetable.R;

public class AboutFragment extends Fragment {
    private Toolbar toolbar;
    private ImageButton imageButton;
    private TextView githubLink, blogLink;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        findID(root);
        Menu menu = toolbar.getMenu();
        menu.setGroupVisible(0, false);
        imageButton.getBackground().setAlpha(200);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                joinQQGroup("NP5EqyF94Aiyi5CQoNr4-yZYyv5SynLZ");
            }
        });
        NoUnderlineSpan mNoUnderlineSpan = new NoUnderlineSpan();
        if (githubLink.getText() instanceof Spannable) {
            Spannable github = (Spannable) githubLink.getText();
            Spannable blog = (Spannable) blogLink.getText();
            github.setSpan(mNoUnderlineSpan, 0, github.length(), Spanned.SPAN_MARK_MARK);
            blog.setSpan(mNoUnderlineSpan, 0, blog.length(), Spanned.SPAN_MARK_MARK);
        }
        return root;
    }

    private void findID(View root) {
        toolbar = requireActivity().findViewById(R.id.toolbar);
        imageButton = root.findViewById(R.id.QQ_feedback);
        githubLink = root.findViewById(R.id.tv_github);
        blogLink = root.findViewById(R.id.blogLink);
    }

    /****************
     *
     * 发起添加群流程。群号：冲鸭(957024515) 的 key 为： NP5EqyF94Aiyi5CQoNr4-yZYyv5SynLZ
     * 调用 joinQQGroup(NP5EqyF94Aiyi5CQoNr4-yZYyv5SynLZ) 即可发起手Q客户端申请加群 冲鸭(957024515)
     *
     * @param key 由官网生成的key
     ******************/
    private void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(requireActivity(), "您还没有安装QQ，请先安装软件", Toast.LENGTH_SHORT).show();
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
