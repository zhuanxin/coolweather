package com.coolweaather.app.until;

/**
 * Created by zhuanxin on 2016/5/31.
 */
public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
