package com.cloud.tool.util.timedown;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Author: xb.zou
 * @Date: 2019/2/13
 * @Desc: to-> 计时器
 */
public class TimeDownTool {
    private ScheduledExecutorService mExecutorService;

    public TimeDownTool(){
        mExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void startDown(long milliSeconds, final DownListener listener){
        mExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                listener.onDown();
            }
        }, 0, milliSeconds, TimeUnit.MILLISECONDS);
    }

    public void stopDown(){
        mExecutorService.shutdown();
    }

    public interface DownListener{
        void onDown();
    }
}
