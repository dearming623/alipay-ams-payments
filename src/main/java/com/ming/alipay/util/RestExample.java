package com.ming.alipay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ming.alipay.api.AMSHeader;
import com.ming.alipay.api.Signature;
import com.ming.alipay.ams.request.AMSRequestParam;
import com.ming.alipay.ams.response.AMSObjectCallback;
import com.ming.alipay.ams.response.AMSResponse;
import com.ming.alipay.config.AMSConfiguration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * @author Ming
 * @wechat 147877305
 * @date 7/28/2020 6:06 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestExample<T> {

    private AMSConfiguration sdkConfiguration;
    private AMSHeader amsHeader;
    private String restfulPath;
    private AMSRequestParam amsRequestParam;
    private String requestBody;
    private String requestTime;
    private String agentToken;


    public void post(Class<T> cls, AMSObjectCallback<T> callback){
        //test
        String sign = SignatureUtil.sign(restfulPath,
                sdkConfiguration.getClientId(),
                requestTime,
                sdkConfiguration.getPrivateKey(),
                requestBody);

        Signature signature = Signature.builder()
                .signature(sign)
                .build();

        //生成header
        if (amsHeader == null) {
            amsHeader = AMSHeader.builder()
                    .contentType("application/json; charset=UTF-8")
                    .clientId(sdkConfiguration.getClientId())
                    .signature(signature.toString())
                    .requestTime(requestTime)
                    .agentToken(agentToken)
                    .build();
        }

        String url = sdkConfiguration.getGatewayUrl() + restfulPath;
        HashMap<String, String> httpHeader = AmsUtil.convertHttpHeader(amsHeader);

        if (amsRequestParam != null)
            this.requestBody = JSON.toJSONString(amsRequestParam);

          HttpUtil.doHttpRequest("POST",url, httpHeader,requestBody, cls,callback);
    }

    public T post(Class<T> clazz) {

        String sign = SignatureUtil.sign(restfulPath,
                sdkConfiguration.getClientId(),
                requestTime,
                sdkConfiguration.getPrivateKey(),
                requestBody);

        Signature signature = Signature.builder()
                .signature(sign)
                .build();

        //生成header
        if (amsHeader == null) {
            amsHeader = AMSHeader.builder()
                    .contentType("application/json; charset=UTF-8")
                    .clientId(sdkConfiguration.getClientId())
                    .signature(signature.toString())
                    .requestTime(requestTime)
                    .agentToken(agentToken)
                    .build();
        }

        String url = sdkConfiguration.getGatewayUrl() + restfulPath;
        HashMap<String, String> httpHeader = AmsUtil.convertHttpHeader(amsHeader);

        if (amsRequestParam != null)
            this.requestBody = JSON.toJSONString(amsRequestParam);

//        if (StringUtils.equals(this.requestBody, requestBody)) {
//            System.out.println("request body value is equal ");
//        } else {
//            System.out.println("request body value not equal ");
//        }

        System.out.println();
        System.out.println("Request URL: " + url);
        System.out.println();



//        String response = postRequest(url, httpHeader, requestBody);
//        return parseObject(response, clazz);
//        return response;

        //Ok
        String response =  HttpUtil.doPost(url,httpHeader,requestBody);
        return parseObject(response, clazz);








//        if (StringUtils.isEmpty(response)) {
//            return new AMSResponse().noResponse();
//        } else {
//            return JSON.parseObject(response, AMSResponse.class);
//        }
    }


    public T get(String json, Class clazz) {
        String response = "{}";
//        return JSON.parseObject(json, new TypeReference<T>() { }.getType());
//        return JSON.parseObject(json, Payment.class);

        return parseObject(json, clazz);
    }

    //以下有问题的方法
    public T get(String json) {
        String response = "{}";
//        return JSON.parseObject(json, new TypeReference<T>() { }.getType());
//        return JSON.parseObject(json, Payment.class);


        Type types = new TypeReference<T>() {
        }.getClass().getGenericSuperclass();

        Type[] genericType = ((ParameterizedType) types).getActualTypeArguments();
        for (Type t : genericType) {
            System.out.println(t.getTypeName());

            System.out.println(t.getClass().getName());
        }
        System.out.println("===============================================");
//        return parseObject(json, new TypeReference<T>() { }.getClass());
        return null;
    }

    //
//
    private String postRequest(String requestUrl, HashMap<String, String> header, String requestBody) {
//        AMSResponse response = new AMSResponse();
//        response = response.noResponse();

        StringBuffer buffer = new StringBuffer();//用于存储返回数据
        try {
            URL url = new URL(requestUrl);//创建URL对象

            //配置连接属性
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();//创建连接对象
            httpUrlConn.setDoInput(true);// 从HttpUrlConntion读入，默认true
            httpUrlConn.setDoOutput(true);// post求情向httpUrlConntion读出，默认flase
            httpUrlConn.setUseCaches(false);// post请求不使用缓存
            httpUrlConn.setRequestMethod("POST");// 请求方式，默认GET
            httpUrlConn.setConnectTimeout(10 * 1000);

            if (header != null) {
                header.forEach(httpUrlConn::setRequestProperty);
            }

            //打印header内容
            System.out.println("============ Request header ============ ");
            httpUrlConn.getRequestProperties().forEach((k, v) -> System.out.println(" " + k + " : " + v));
            System.out.println();
            System.out.println("============ Request body ============ ");
            System.out.println(JSONObject.parseObject(requestBody).toString(SerializerFeature.PrettyFormat));
            System.out.println("======================================== ");
            System.out.println(" ");
            System.out.println(" ");

            httpUrlConn.setRequestProperty("Connection", "Keep-Alive");//建议Http为长链接，提高响应速度
//            httpUrlConn.setRequestProperty("Charset", "UTF-8");//字符编码
//            httpUrlConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");// 传输json格式
//            httpUrlConn.setRequestProperty("accept", "application/json");// 接收类型json
            //httpUrlConn.setRequestProperty("accept","*/*")//暴力方法设置接受所有类型，防止出现415

            byte[] writebytes = requestBody.getBytes("UTF-8");//字符流转字节流

            httpUrlConn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));//设置文件长度
            OutputStream outwritestream = httpUrlConn.getOutputStream();//创建输出流对象
            outwritestream.write(writebytes);//输出流的内容，可以传3个参数，默认从0到len
            outwritestream.flush();//清空数据（读完未必写完，缓冲区有遗留的可能）
            outwritestream.close();//关闭输出流

            httpUrlConn.connect();//开始连接，配置信息必须在连接前设置完毕


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

                return JSON.toJSONString(new AMSResponse().noResponse());
            }

            System.out.println("============ Response header ============ ");
            //打印header内容
            httpUrlConn.getHeaderFields().forEach((k, v) -> System.out.println(" " + k + " : " + v));
            System.out.println();
            System.out.println("============ Response body ============ ");
            System.out.println(JSONObject.parseObject(buffer.toString()).toString(SerializerFeature.PrettyFormat));
            System.out.println("======================================== ");

            //将buffer转换成ArrayList格式
            if (buffer.length() != 0) {
//                JSONObject jsonObject = JSONObject.parseObject(buffer.toString());
//                return jsonObject.toString(SerializerFeature.PrettyFormat);

                AMSResponse response = JSON.parseObject(buffer.toString(), AMSResponse.class);
                response.setHeader(httpUrlConn.getHeaderFields());

                return JSON.toJSONString(response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return JSON.toJSONString(new AMSResponse().noResponse());

    }

    //
//
    private <T> T parseObject(String jsonStr, Class clazz) {
        return (T) JSON.parseObject(jsonStr, clazz);
    }


}
