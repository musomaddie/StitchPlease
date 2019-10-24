package project.comp5216.crossstitchorganiser;

import android.content.ContentResolver;
import android.os.CountDownTimer;
import android.provider.Settings;

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




    public MyCountDownTimer(ContentResolver contentResolver , long millisInFuture, long countDownInterval, int current_brightness) {
        super(millisInFuture, countDownInterval);
        this.contentResolver = contentResolver;
        this.brightness=current_brightness;
            }

    @Override
    public void onTick(long millisUntilFinished) {


    }

    @Override
    public void onFinish() {
        Settings.System.putInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS, 0);
    }










}
