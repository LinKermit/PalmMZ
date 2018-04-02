package com.lin.palmmz.weight;

import android.app.ProgressDialog;

/**
 * Created by lin on 2017/11/17.
 */

public interface DialogControl {

    public abstract void hideWaitDialog();

    public abstract ProgressDialog showWaitDialog();

    public abstract ProgressDialog showWaitDialog(int resid);

    public abstract ProgressDialog showWaitDialog(String text);
}
