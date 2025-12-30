package com.lqj.client;

import cn.hutool.json.JSONUtil;
import com.lqj.bean.Invocation;
import com.lqj.bean.RpcRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;


/**
 * @Author 李岐鉴
 * @Date 2025/12/29
 * @Description Http客户端
 */
public class HttpClient {

    /**
     * 发送http请求的远程调用
     * @param hostName
     * @param port
     * @param invocation
     * @return
     */
    public static String send(String hostName, Integer port, Invocation invocation) throws Exception{
        // 建立连接
        URL url = new URL("http", hostName, port, "/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // 配置请求体
        OutputStream os = connection.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(invocation);
        oos.flush();
        oos.close();

        // 获取响应内容
        InputStream is = connection.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        String result = (String) ois.readObject();
        return result;
    }

    /**
     * 发送http请求的远程调用SpringBoot的接口
     * @param hostName
     * @param port
     * @param request
     * @return
     */
    public static String send(String hostName, Integer port, RpcRequest request) throws Exception {
        //配置请求参数
        String urlPath = request.getPath();
        if (request.getQueryParameters() != null && !request.getQueryParameters().isEmpty()) {
            StringBuilder queryString = new StringBuilder();
            for (Map.Entry<String, Object> param : request.getQueryParameters().entrySet()) {
                if (queryString.length() > 0) {
                    queryString.append("&");
                }
                //将参数值转换为符合URL规范的格式
                String encodedValue = URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8.name());
                queryString.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8.name()))
                        .append("=")
                        .append(encodedValue);
            }
            urlPath += "?" + queryString.toString();
        }
        URL url = new URL("http", hostName, port, urlPath);

        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
        urlConnection.setRequestMethod(request.getMethod());
        urlConnection.setDoOutput(true);


        //配置请求体参数
        OutputStream os = urlConnection.getOutputStream();
        String jsonBody = JSONUtil.toJsonStr(request.getBodyParameter());
        os.write(jsonBody.getBytes(StandardCharsets.UTF_8));
        os.flush();
        os.close();

        //获取相应内容
        InputStream is = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while( (line = reader.readLine()) != null){
            sb.append(line);
        }
        return sb.toString();

    }



}
