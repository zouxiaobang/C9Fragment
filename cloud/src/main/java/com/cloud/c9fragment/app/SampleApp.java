package com.cloud.c9fragment.app;

import android.app.Application;

import com.cloud.c9logger.logger2.log.DefaultConverters;
import com.cloud.c9logger.logger2.log.Logger;
import com.cloud.c9logger.logger2.print.LoggerPrint;
import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;

/**
 * @Author: xb.zou
 * @Date: 2019/1/30
 * @Desc: to-> 默认Application类
 */
public class SampleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Cloud9.init(this)
                .withHost("http://localhost:8080")
                .withLogger("Cloud9")
                .withHideBar(true)
                .withSpName("CLOUD9")
                .configure();


        Logger.initLogger(new DefaultConverters(Cloud9.getConfig(ConfigurationKey.LOGGER)),
                new LoggerPrint()
                /*new FilePrint("/test/", "log_" + System.currentTimeMillis() + ".txt")*/);
    }
}
