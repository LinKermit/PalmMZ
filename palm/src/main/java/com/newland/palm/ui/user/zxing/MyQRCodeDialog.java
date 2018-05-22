package com.newland.palm.ui.user.zxing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.newland.palm.R;
import com.newland.palm.utils.UltimateBar;

/**
 * @author lin
 * @version 2018/5/22 0022
 */
public class MyQRCodeDialog extends Dialog {

    private ImageView mIvCode;
    private Bitmap bitmap;

    public MyQRCodeDialog(@NonNull Context context) {
        this(context,R.style.PubTheme);
    }

    //themeResId 主题样式
    public MyQRCodeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View contentView = View.inflate(context, R.layout.dialog_my_qr_code,null);
        mIvCode = contentView.findViewById(R.id.iv_qr_code);
        try {
            bitmap = QrCodeUtils.Create2DCode("http://gdown.baidu.com/data/wisegame/21c41b43bf5a27b1/QQ_762.apk");
            mIvCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        contentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                MyQRCodeDialog.this.dismiss();
                return false;
            }
        });

        super.setContentView(contentView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
//        getWindow().setGravity(Gravity.CENTER);
//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();
//        WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        p.height = ViewGroup.LayoutParams.MATCH_PARENT;
//        getWindow().setAttributes(p);
    }
}
