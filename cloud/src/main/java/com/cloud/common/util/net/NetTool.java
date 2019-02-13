package com.cloud.common.util.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

/**
 * @Author: xb.zou
 * @Date: 2019/2/12
 * @Desc: to-> 网络工具类
 */
public class NetTool {
    /**
     * 判断网络是否连接
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager
                = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Network[] networks = manager.getAllNetworks();
        NetworkInfo networkInfo;
        for (Network network: networks) {
            networkInfo = manager.getNetworkInfo(network);
            if (networkInfo.isConnected()) {
                return true;
            }
        }

        return false;
    }
}
