package com.hengyi.baseandroidcore.http.builder;

import com.hengyi.baseandroidcore.http.OkHttpUtils;
import com.hengyi.baseandroidcore.http.request.OtherRequest;
import com.hengyi.baseandroidcore.http.request.RequestCall;

/**
 * Created by zhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers,id).build();
    }
}
