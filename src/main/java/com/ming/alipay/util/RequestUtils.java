package com.ming.alipay.util;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestUtils {

    //GET请求时可以只传递一个API地址参数
    public static String runRequest(String requestUrl) {
        return runRequest(requestUrl, null, "GET", new HashMap<>());
    }

    //访问服务端API，三个参数，分别是API地址，请求方式，数据/参数
    //Params请求传递Map，Data请求传递JSON字符串
    public static String runRequest(String requestUrl, HashMap<String, String> header, String method, Object info) {
        StringBuffer buffer = new StringBuffer();//用于存储返回数据
        try {
            URL url = new URL(requestUrl);//创建URL对象
            //对请求方式为GET和空值进行处理
            if (method == "GET") {
                url = new URL(requestUrl + "?" + urlencode(info));//拼接URL
            }
            if (method != "GET" && method != "POST") {
                return "{}";//只处理GET和POST请求，其他请求不做处理
            }
            //配置连接属性
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();//创建连接对象
            httpUrlConn.setDoInput(true);// 从HttpUrlConntion读入，默认true
            httpUrlConn.setDoOutput(true);// post求情向httpUrlConntion读出，默认flase
            httpUrlConn.setUseCaches(false);// post请求不使用缓存
            httpUrlConn.setRequestMethod(method);// 请求方式，默认GET

            if (header != null) {
                header.forEach(httpUrlConn::setRequestProperty);
            }

            //打印header内容
            System.out.println("============ Request header ============ ");
            httpUrlConn.getRequestProperties().forEach((k, v) -> System.out.println(" " + k + " : " + v));
            System.out.println();
            System.out.println("============ Request body ============ ");
            System.out.println(JSONObject.parseObject(info.toString()).toString(SerializerFeature.PrettyFormat));
            System.out.println("======================================== ");
            System.out.println(" ");
            System.out.println(" ");

//            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");//建议Http为长链接，提高响应速度
//            httpUrlConn.setRequestProperty("Charset", "UTF-8");//字符编码
//            httpUrlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");// 传输json格式
//            httpUrlConn.setRequestProperty("accept", "application/json");// 接收类型json
            //httpUrlConn.setRequestProperty("accept","*/*")//暴力方法设置接受所有类型，防止出现415
            if (method == "POST") {
                byte[] writebytes = "".getBytes();
                if (info != null) {
                    writebytes = info.toString().getBytes();//字符流转字节流
                }
                httpUrlConn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));//设置文件长度
                OutputStream outwritestream = httpUrlConn.getOutputStream();//创建输出流对象
                outwritestream.write(info.toString().getBytes());//输出流的内容，可以传3个参数，默认从0到len
                outwritestream.flush();//清空数据（读完未必写完，缓冲区有遗留的可能）
                outwritestream.close();//关闭输出流
            }
            httpUrlConn.connect();//开始连接，配置信息必须在连接前设置完毕


            System.out.println("============ Response header ============ ");
            //打印header内容
            httpUrlConn.getHeaderFields().forEach((k, v) -> System.out.println(" " + k + " : " + v));

            int statusCode = httpUrlConn.getResponseCode();//获取状态码
            if (statusCode == 200) {
                InputStream inputStream = httpUrlConn.getInputStream();//获取输入流
                //字节流转字符流
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//读取字符流
                String str = null;
                //将读取到的字符流赋值给buffer，readLine为读取一行，当前行为null时，表示已经读完
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                //关闭bufferReader和输入流
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;//清空输入流
                httpUrlConn.disconnect();//断开连接
            } else {
                //
            }


            System.out.println();
            System.out.println("============ Response body ============ ");
            System.out.println(JSONObject.parseObject(buffer.toString()).toString(SerializerFeature.PrettyFormat));
            System.out.println("======================================== ");


        } catch (Exception e) {
            e.printStackTrace();
        }
        //将buffer转换成ArrayList格式
        if (buffer.length() != 0) {
            JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
            return jsonObject.toString(SerializerFeature.PrettyFormat);
        } else {
            return "{}";
        }
    }

    //GET请求URL处理
    public static String urlencode(Object params) {
        if (params == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Map<String, Object> map = (Map) params;
        for (Map.Entry i : map.entrySet()) {
            try {
                sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        if (str.length() != 0) {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }


}