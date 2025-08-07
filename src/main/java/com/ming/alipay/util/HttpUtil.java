package com.ming.alipay.util;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ming.alipay.ams.response.AMSObjectCallback;
import com.ming.alipay.ams.response.ByteArrayCallback;
import com.ming.alipay.ams.response.Callback;
import com.ming.alipay.ams.response.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ming
 * @wechat 147877305
 * @date 8/1/2020 10:50 AM
 */

public class HttpUtil {

    //线程池
    private static ExecutorService executor;

    static {
        executor = Executors.newFixedThreadPool(5);
    }

    private static Integer connectTimeout = 30 * 1000;
    private static Integer readTimeout = 30 * 1000;


    /**
     * @param requestUrl url
     * @param paramMap   请求参数
     * @param headerMap  head参数
     * @return String
     * @Title doPost 对参数进行URLEncoder
     * @Description post请求
     */
    public static String doPost(String requestUrl, Map<String, String> paramMap, Map<String, String> headerMap) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(requestUrl);
            // 获取一个HttpURLConnection链接对象
            httpurlconnection = (HttpURLConnection) url.openConnection();
            // 设置超时时间
            httpurlconnection.setConnectTimeout(600000);
            httpurlconnection.setReadTimeout(600000);

            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("POST");
            // 添加头部参数
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Entry<String, String> header : headerMap.entrySet()) {
                    httpurlconnection.addRequestProperty(header.getKey(), header.getValue());
                }
            }
            // 添加请求参数
            if (paramMap != null && !paramMap.isEmpty()) {
                StringBuilder sb = new StringBuilder("");
                for (Entry<String, String> param : paramMap.entrySet()) {
                    sb.append("&").append(param.getKey()).append("=")
                            .append(URLEncoder.encode(param.getValue(), "UTF-8"));
                }
                OutputStream outPutStream = httpurlconnection.getOutputStream();
                outPutStream.write(sb.toString().getBytes("UTF-8"));
                outPutStream.flush();
                outPutStream.close();
            }

            // 接口调用后获得输入流
            InputStream ins = httpurlconnection.getInputStream();

            byte[] responseData = IOUtil.readFromStream(ins);
            return new String(responseData, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return "";
    }

    public static String doGet(String requestUrl) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        try {
            url = new URL(requestUrl);
            // 获取一个HttpURLConnection链接对象
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置超时时间
            httpurlconnection.setConnectTimeout(30000);
            httpurlconnection.setReadTimeout(30000);

            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            // 接口调用后获得输入流
            InputStream ins = httpurlconnection.getInputStream();
            byte[] responseData = IOUtil.readFromStream(ins);
            return new String(responseData, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return "";
    }


    /**
     * @param requestUrl url
     * @param json       请求参数josn串
     * @return String
     * @Title doPost
     * @Description post请求
     */
    public static String doPost(String requestUrl, String json) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        System.out.println("requestUrl===" + requestUrl);
        try {
            url = new URL(requestUrl);
            // 获取一个HttpURLConnection链接对象
            httpurlconnection = (HttpURLConnection) url.openConnection();
            // 设置超时时间
            httpurlconnection.setConnectTimeout(600000);
            httpurlconnection.setReadTimeout(600000);

            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.addRequestProperty("Content-Type", "application/json");
            // 添加头部参数
            /*
             * if (headerMap != null && !headerMap.isEmpty()) { for
             * (Entry<String, String> header : headerMap.entrySet()) {
             * httpurlconnection.addRequestProperty(header.getKey(),
             * header.getValue()); } }
             */
            // 添加请求参数
            if (!StringUtils.isEmpty(json)) {
                OutputStream outPutStream = httpurlconnection.getOutputStream();
                outPutStream.write(json.getBytes("UTF-8"));
                outPutStream.flush();
                outPutStream.close();
            }

            // 接口调用后获得输入流
            InputStream ins = httpurlconnection.getInputStream();

            byte[] responseData = IOUtil.readFromStream(ins);
            return new String(responseData, "UTF-8");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return "";
    }

    public static String doPost(String requestUrl, Map<String, String> headerMap, String json) {
        URL url = null;
        HttpURLConnection httpurlconnection = null;
        System.out.println("requestUrl===" + requestUrl);
        try {
            url = new URL(requestUrl);
            // 获取一个HttpURLConnection链接对象
            httpurlconnection = (HttpURLConnection) url.openConnection();
            // 设置超时时间
            httpurlconnection.setConnectTimeout(600000);
            httpurlconnection.setReadTimeout(600000);

            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.addRequestProperty("Content-Type", "application/json");

            // 添加头部参数
            if (headerMap != null && !headerMap.isEmpty()) {
                for
                (Entry<String, String> header : headerMap.entrySet()) {
                    httpurlconnection.addRequestProperty(header.getKey(),
                            header.getValue());
                }
            }

            // 添加请求参数
            if (!StringUtils.isEmpty(json)) {
                OutputStream outPutStream = httpurlconnection.getOutputStream();
                outPutStream.write(json.getBytes(StandardCharsets.UTF_8));
                outPutStream.flush();
                outPutStream.close();
            }

            // 接口调用后获得输入流
            InputStream ins = httpurlconnection.getInputStream();

            byte[] responseData = IOUtil.readFromStream(ins);
            return new String(responseData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (httpurlconnection != null) {
                httpurlconnection.disconnect();
            }
        }
        return "";
    }


    /**
     * 执行网络请求操作,返回数据是对象
     *
     * @param method 请求方式(需要传入String类型的参数:"GET","POST")
     * @param url    请求的url
     * @param params 请求的参数
     */
    public static <T> void doHttpRequest(final String method, final String url,
                                         final Map<String, String> params, final Class<T> cls, final AMSObjectCallback<T> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                OutputStream outputStream = null;
                try {
                    URL u = new URL(url);
                    connection = (HttpURLConnection) u.openConnection();
                    // 设置输入可用
                    connection.setDoInput(true);
                    // 设置输出可用
                    connection.setDoOutput(true);
                    // 设置请求方式
                    connection.setRequestMethod(method);
                    // 设置连接超时
                    connection.setConnectTimeout(5000);
                    // 设置读取超时
                    connection.setReadTimeout(5000);
                    // 设置缓存不可用
                    connection.setUseCaches(false);
                    // 开始连接
                    connection.connect();

                    // 只有当POST请求时才会执行此代码段
                    if (params != null) {
                        // 获取输出流,connection.getOutputStream已经包含了connect方法的调用
                        outputStream = connection.getOutputStream();
                        StringBuilder sb = new StringBuilder();
                        Set<Entry<String, String>> sets = params.entrySet();
                        // 将Hashmap转换为string
                        for (Entry<String, String> entry : sets) {
                            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                        }
                        String param = sb.substring(0, sb.length() - 1);
                        // 使用输出流将string类型的参数写到服务器
                        outputStream.write(param.getBytes());
                        outputStream.flush();
                    }

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStream2String(inputStream);
                        if (result != null && callback != null) {
                            postSuccessObject(callback, JSONObject.parseObject(result, cls));
                        }
                    } else {
                        if (callback != null) {
                            postFailed(callback, responseCode, new Exception("请求数据失败：" + responseCode));
                        }
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        postFailed(callback, 0, e);
                    }

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }


    public static <T> void doHttpRequest(final String method,
                                         final String url,
                                         final Map<String, String> headerMap,
                                         final String json,
                                         final Class<T> cls,
                                         final AMSObjectCallback<T> callback) {


        executor.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                OutputStream outputStream = null;
                try {
                    URL u = new URL(url);
                    connection = (HttpURLConnection) u.openConnection();
                    // 设置输入可用
                    connection.setDoInput(true);
                    // 设置输出可用
                    connection.setDoOutput(true);
                    // 设置请求方式
                    connection.setRequestMethod(method);
                    // 设置连接超时
                    connection.setConnectTimeout(connectTimeout);
                    // 设置读取超时
                    connection.setReadTimeout(readTimeout);
                    // 设置缓存不可用
                    connection.setUseCaches(false);
//                    // 开始连接
//                    connection.connect();

                    if (headerMap != null && !headerMap.isEmpty()) {
                        for
                        (Entry<String, String> header : headerMap.entrySet()) {
                            connection.addRequestProperty(header.getKey(),
                                    header.getValue());
                        }
                    }

                    System.out.println();
                    System.out.println("Request URL: " + url);
                    System.out.println();

                    //打印header内容
                    System.out.println("============ Request header ============ ");
//                    connection.getRequestProperties().forEach((k, v) -> System.out.println(" " + k + " : " + v));
                    Map<String, List<String>> requestProperties = connection.getRequestProperties();
                    for (Map.Entry<String, List<String>> entry :
                            requestProperties.entrySet()) {
                        System.out.println(" " + entry.getKey() + " : " + entry.getValue());
                    }

                    System.out.println();
                    System.out.println("============ Request body ============ ");
                    System.out.println(JSONObject.parseObject(json).toString(SerializerFeature.PrettyFormat));
                    System.out.println("======================================== ");
                    System.out.println(" ");
                    System.out.println(" ");

                    // 添加请求参数
                    if (!StringUtils.isEmpty(json)) {
                        OutputStream outPutStream = connection.getOutputStream();
                        outPutStream.write(json.getBytes(StandardCharsets.UTF_8));
                        outPutStream.flush();
                        outPutStream.close();
                    }
                    postHeader(callback, connection.getHeaderFields());
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        String result = inputStream2String(inputStream);
                        if (result != null && callback != null) {

                            System.out.println();
                            System.out.println("============ Alipay Response body ============ ");
                            System.out.println(JSONObject.parseObject(result).toString(SerializerFeature.PrettyFormat));
                            System.out.println("======================================== ");

                            postSuccessObject(callback, JSONObject.parseObject(result, cls));
                        }
                    } else {
                        if (callback != null) {
                            postFailed(callback, responseCode, new Exception("请求数据失败：" + responseCode));
                        }
                    }

                } catch (final Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        postFailed(callback, 0, e);
                    }

                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        //一定要调用这个方法，不然executorService.isTerminated()永远不为true
//        executor.shutdown();
    }

    public static <T> void sendRequestAsync(final String method,
                                            final String url,
                                            final Map<String, String> headerMap,
                                            final String json,
                                            final Class<T> cls,
                                            final AMSObjectCallback<T> callback) {

        Thread newThread = new Thread(() -> {
            HttpURLConnection connection = null;
            OutputStream outputStream = null;
            try {
                URL u = new URL(url);
                connection = (HttpURLConnection) u.openConnection();
                // 设置输入可用
                connection.setDoInput(true);
                // 设置输出可用
                connection.setDoOutput(true);
                // 设置请求方式
                connection.setRequestMethod(method);
                // 设置连接超时
                connection.setConnectTimeout(connectTimeout);
                // 设置读取超时
                connection.setReadTimeout(readTimeout);
                // 设置缓存不可用
                connection.setUseCaches(false);
//                    // 开始连接
//                    connection.connect();

                if (headerMap != null && !headerMap.isEmpty()) {
                    for
                    (Entry<String, String> header : headerMap.entrySet()) {
                        connection.addRequestProperty(header.getKey(),
                                header.getValue());
                    }
                }

                System.out.println();
                System.out.println("Request URL: " + url);
                System.out.println();

                //打印header内容
                System.out.println("============ Request header ============ ");
//                    connection.getRequestProperties().forEach((k, v) -> System.out.println(" " + k + " : " + v));
                Map<String, List<String>> requestProperties = connection.getRequestProperties();
                for (Map.Entry<String, List<String>> entry :
                        requestProperties.entrySet()) {
                    System.out.println(" " + entry.getKey() + " : " + entry.getValue());
                }

                System.out.println();
                System.out.println("============ Request body ============ ");
                System.out.println(JSONObject.parseObject(json).toString(SerializerFeature.PrettyFormat));
                System.out.println("======================================== ");
                System.out.println(" ");
                System.out.println(" ");

                // 添加请求参数
                if (!StringUtils.isEmpty(json)) {
                    OutputStream outPutStream = connection.getOutputStream();
                    outPutStream.write(json.getBytes(StandardCharsets.UTF_8));
                    outPutStream.flush();
                    outPutStream.close();
                }
                postHeader(callback, connection.getHeaderFields());
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = connection.getInputStream();
                    String result = inputStream2String(inputStream);
                    if (result != null && callback != null) {

                        System.out.println();
                        System.out.println("============ Alipay Response body ============ ");
                        System.out.println(JSONObject.parseObject(result).toString(SerializerFeature.PrettyFormat));
                        System.out.println("======================================== ");

                        postSuccessObject(callback, JSONObject.parseObject(result, cls));
                    }
                } else {
                    if (callback != null) {
                        postFailed(callback, responseCode, new Exception("请求数据失败：" + responseCode));
                    }
                }

            } catch (final Exception e) {
                e.printStackTrace();
                if (callback != null) {
                    postFailed(callback, 0, e);
                }

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        newThread.start();


    }


    /**
     * 字节流转换成字符串
     *
     * @param inputStream
     * @return
     */
    private static String inputStream2String(InputStream inputStream) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = 0;
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 字节流转换成字节数组
     *
     * @param inputStream 输入流
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream inputStream) {
        byte[] result = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 缓冲区
        byte[] bytes = new byte[1024];
        int len = -1;
        try {
            // 使用字节数据输出流来保存数据
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            result = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static void postSuccessString(final StringCallback callback, final String result) {

        callback.onSuccess(result);

    }


    private static void postSuccessByte(final Callback callback, final byte[] bytes) {


        ByteArrayCallback byteArrayCallback = (ByteArrayCallback) callback;
        byteArrayCallback.onSuccess(bytes);

    }

    private static <T> void postSuccessObject(final AMSObjectCallback<T> callback, final T t) {

        callback.onSuccess(t);

    }

    private static void postFailed(final Callback callback, final int code, final Exception e) {

        callback.onFailure(code, e);

    }

    private static void postHeader(final Callback callback, final Map<String, List<String>> headerFields) {
        HashMap<String, String> map = new HashMap<>();
//        headerFields.forEach((k, v) -> map.put(k, v.toString()));  // not support java 1.6
        //support java 1.6
        for (Map.Entry<String, List<String>> entry :
                headerFields.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        callback.onHeader(map);
    }


}
