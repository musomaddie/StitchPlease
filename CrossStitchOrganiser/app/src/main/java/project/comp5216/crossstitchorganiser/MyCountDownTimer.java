package comp5216.sydney.edu.au.logoutlistener;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyCountDownTimer extends CountDownTimer {
    boolean success =false;
    ContentResolver contentResolver;
    int brightness;


    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */




    public MyCountDownTimer(ContentResolver contentResolver ,long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.contentResolver = contentResolver;
        Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, brightness);
            }

    @Override
    public void onTick(long millisUntilFinished) {


    }

    @Override
    public void onFinish() {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
    }








}
