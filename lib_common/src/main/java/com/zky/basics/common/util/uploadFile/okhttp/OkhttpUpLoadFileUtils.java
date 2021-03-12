package com.zky.basics.common.util.uploadFile.okhttp;

import android.util.Log;


import com.zky.basics.api.config.API;
import com.zky.basics.common.util.uploadFile.UploadingFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lk
 * Date 2020/4/21
 * Time 15:22
 * Detail:
 */
public class OkhttpUpLoadFileUtils {
    private List<UploadingFile> mFiles;
    //    private HashMap<String, Object> mData = new HashMap<>();
    private FileCallBack mFileCallBack;

    /**
     * 单利 静态内部类
     *
     * @return
     */
    public static OkhttpUpLoadFileUtils getInstance() {
        return InstanceHolder.okhttpUpLoadFileUtils;
    }

    private static class InstanceHolder {
        private static OkhttpUpLoadFileUtils okhttpUpLoadFileUtils = new OkhttpUpLoadFileUtils();
    }

    public OkhttpUpLoadFileUtils setData(List<UploadingFile> files, FileCallBack fileCallBack) {
        this.mFiles = files;
//        this.mData = data;
        this.mFileCallBack = fileCallBack;
        return InstanceHolder.okhttpUpLoadFileUtils;
    }

    public void build() {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();

            if (mFiles == null || mFiles.size() == 0) {
                return;
            }

            MediaType MutilPart_Form_Data = MediaType.parse("multipart/form-data; charset=utf-8");

            RequestBody bodyParams = RequestBody.create(MutilPart_Form_Data, "");

            MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
//                    .addFormDataPart("projectFile", "", bodyParams);
            for (int i = 0; i < mFiles.size(); i++) {
                File file = new File(mFiles.get(i).getFilePath());
                Log.e("file", "file-->" + file.exists());
                HashMap<String, String> params = mFiles.get(i).getParams();
//                String s = new Gson().toJson(params);
//                s=s.substring(1,s.length()-1);
                if (params != null) {

                    for (Map.Entry<String, String> entry: params.entrySet()){

                        requestBodyBuilder.addFormDataPart(String.valueOf(entry.getKey()), entry.getValue(), bodyParams);

                    }
//                    requestBodyBuilder.addFormDataPart("projectFile", s, bodyParams);


                }

                MediaType parse = null;
                if (file.getName().contains("mp4")) {
                    parse= MediaType.parse("video/mp4");
                } else {
                    parse = MediaType.parse("image/png");
                }
                RequestBody fileBody = RequestBody.create(parse, file);

                requestBodyBuilder.addFormDataPart("file", file.getName(), fileBody);
            }
            RequestBody requestBody = requestBodyBuilder.build();
            Request request = new Request.Builder()
                    .url(API.URL_UPLOAD_NEW)
                    .post(requestBody)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    if (mFileCallBack != null) {
                        mFileCallBack.onErrorUploadFile(e);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String messge = response.body().string();
                    if (mFileCallBack != null) {
                        mFileCallBack.onSuccedUpLoadFile(messge);
                    }
                }
            });


        } catch (Exception e) {
            if (mFileCallBack != null) {
                mFileCallBack.onErrorUploadFile(e);
            }
        }
    }


    public interface FileCallBack {

        void onErrorUploadFile(Exception e);

        void onSuccedUpLoadFile(String message);
    }


}
