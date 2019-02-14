package com.cloud.common.net.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;

import com.cloud.common.base.Cloud9;
import com.cloud.common.base.ConfigurationKey;
import com.cloud.common.loading.CloudLoader;
import com.cloud.common.net.callback.IRequest;
import com.cloud.common.net.callback.ISuccess;
import com.cloud.common.util.file.FileTool;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * @Author: xb.zou
 * @Date: 2019/2/14
 * @Desc: to-> 保存已经下载的文件
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private static final Handler HANDLER = new Handler();

    SaveFileTask(IRequest REQUEST, ISuccess SUCCESS) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals("")) {
            downloadDir = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileTool.write2Disk(is, downloadDir, extension.toUpperCase(), extension);
        } else {
            return FileTool.write2Disk(is, downloadDir, name, extension);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null) {
            SUCCESS.onSuccess(file.getPath());
        }
        if (REQUEST != null) {
            REQUEST.onRequestEnd();
        }
        dimissDialog();
//        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileTool.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            ((Context) Cloud9.getConfig(ConfigurationKey.APPLICATION_CONTEXT)).startActivity(install);
        }
    }


    /**
     * 隐藏Dialog
     */
    private void dimissDialog() {
        HANDLER.postDelayed(() -> CloudLoader.stopLoading(), 0);
    }
}
