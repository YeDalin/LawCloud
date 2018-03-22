package net.sanhedao.lawcloudserver.http;

import android.os.AsyncTask;
import android.util.Log;

import net.sanhedao.lawcloudserver.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/4.
 *
 * 网络访问框架
 *
 */
public class HttpClient {

    private String use="";

    public static interface OnResponseListener {
        public void onResponse(String result);
    }

    private String post(String url, RequestParameters requestParameters) {
        try {
            URL mUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (requestParameters != null) {
                OutputStream os = conn.getOutputStream();
                os.write(requestParameters.buildRequestString(use).getBytes());
                os.flush();
                os.close();
            }else {
                Logger.e("xxxx",use+" 没有网络请求参数 ");
            }
            Log.e("xxxx","conn.getResponseCode() = "+conn.getResponseCode());
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                int len = 0;
                StringBuffer stringBuffer = new StringBuffer();
                while ((len = is.read()) != -1) {
                    stringBuffer.append((char) len);
                }

                String str=stringBuffer.toString();

                Logger.e("xxxx",use+" 网络请求返回值str= "+str);

                try {
                    str=stringBuffer.substring(str.indexOf("{"),str.length());
                }catch (RuntimeException e){
                    return "Error";
                }


                return str;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Logger.e("xxxx","网址不规范错误： "+e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Logger.e("xxxx","IO异常： "+e.getMessage());
        }
        return "Error";
    }

    public void post(String use,String url, RequestParameters requestParameters, OnResponseListener listener) {
        this.use=use;
        Logger.e("xxx",use+"  网络请求网址url= "+url);
        new MyAsyncTask(url, requestParameters, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);//在3.0以后是单线程执行的
    }

    public void post(String url, RequestParameters requestParameters, OnResponseListener listener) {
        Logger.e("xxx",use+"  网络请求网址url= "+url);
        new MyAsyncTask(url, requestParameters, listener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);//在3.0以后是单线程执行的
    }

    //    异步任务(自带线程池，在多线程访问或者下载的时候，线程池控制当前有线程的执行顺序等逻辑，线程调控)：Thread+Handler
    public class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private String url;

        private RequestParameters requestParameters;

        private OnResponseListener l;

        public MyAsyncTask(String url, RequestParameters requestParameters, OnResponseListener l) {
            this.url = url;
            this.requestParameters = requestParameters;
            this.l = l;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            在异步任务开始之初
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            异步任务执行结束，在主线程
//            s:是doInBackground方法返回的结果
            if (l != null) {
                l.onResponse(s);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            用于进度条的更新
//            values[0]
        }

        @Override
        protected String doInBackground(String... params) {
//            是在支线程
//            params[0]:"http://www.baidu.com"
//            params[1]:"http://www.sina.com.cn"
//            publishProgress(10);

            return post(url, requestParameters);
        }
    }
}
