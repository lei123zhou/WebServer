package com.webserver.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import static com.webserver.http.HttpContext.CR;
import static com.webserver.http.HttpContext.LF;

/**
 * 请求对象
 * 该类的每一个实例用于表示客户端发送给浏览器的一个HTTP请求
 * HTTP协议规定一个请求包含三部分内容:
 * 请求行,消息头,消息正文
 */
public class HttpRequest {
    //请求行相关信息
    private String method;//请求方式
    private String protocol;//协议版本
    private String uri;//抽象路径
    private String requestURI;//抽象路径请求部分
    private String queryString;//抽象路径中间参数部分
    private Map<String, String> parameters = new HashMap<>();//保存每一组参数
    //消息头相关信息
    private Socket socket;
    private Map<String, String> headers = new HashMap<>();
    //消息正文相关信息

    /**
     * 实例化HttpRequest的过程就是解析请求的过程
     */
    public HttpRequest(Socket socket) throws IOException, EmptyRequestException {
        this.socket = socket;
        //1解析请求

        //1解析请求行
        parseRequestLine();
        //2解析消息头
        parseHeaders();
        //3解析消息正文
        parseContent();
    }

    public String readLine() throws IOException {
        /**
         * d调用同一份socket的getInputStream方法时,无论调用多少次
         * 返回的输入流始终都是同一个流，输出流也是一样的
         * */
        InputStream in = socket.getInputStream();
        StringBuilder builder = new StringBuilder();
        //cur表示本次读到的字符，pre表示上次读到的字符
        int d;
        char cur = 'a', pre = 'a';
        while ((d = in.read()) != -1) {
            cur = (char) d;
            if (cur == LF && pre == CR) {
                break;
            }
            builder.append(cur);
            pre = cur;
        }
        return builder.toString().trim();

    }

    /**
     * 解析请求行
     */
    private void parseRequestLine() throws IOException, EmptyRequestException {

        String line = readLine();
        if (line.isEmpty()) {
            //请求行如果没有内容则说明是一个空请求
            throw new EmptyRequestException();
        }
        System.out.println("请求行为：" + line);
        String[] data = line.split("\\s");
        method = data[0];//请求方式
        uri = data[1];//抽象路径
        protocol = data[2];//协议版本
        parseUri();
        System.out.println("请求方式:" + method);
        System.out.println("url:" + uri);
        System.out.println("protocol:" + protocol);
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getQueryString() {
        return queryString;
    }

    /**
     * 进一步解析请求行
     */
    private void parseUri() {

/**
 * uri分为两种情况:含有参数和不含有参数
 * 如果uri不含有cans参数,则直接将uri的值复制给requestRUI即可
 *
 * 如果uri中含有参数,则需要进一步餐粉解析:
 * 首先按照“？”将uri拆分为两部分:请求部分和参数部分
 * 然后将请求部分赋值给requestURI
 * 参数部分赋值给queryString
 * 之后再进一步拆分出每一组参数
 * 将参数部分按照"&"进行拆分,每一组参数在按照"="拆分出参数名
 * 与参数值。并将参数名作为key,参数值作为value存入parameters
 * 这个Map中完成解析*/
//        int index = uri.indexOf("?");
        if (uri.contains("?")) {
            //有参数
            String[] data = uri.split("\\?");
            requestURI = data[0];
            if (requestURI.length() > 1) {
                queryString = data[1];
                //拆分出每一组参数
                data = queryString.split("&");
                //遍历每一个参数
                for (String para : data) {
                    String[] paras = para.split("=");
                    if (paras.length > 1) {
                        //有参数名也有参数值,比如password=1213

                        try {
                            paras[1] = URLDecoder.decode(paras[1], "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        parameters.put(paras[0], paras[1]);
                    } else {
                        //有参数名没有参数值
                        parameters.put(paras[0], null);
                    }
                }
            }

        } else {
//没有参数
            requestURI = uri;
        }
        System.out.println(parameters);
    }

    /**
     * 解析消息头
     */
    private void parseHeaders() throws IOException {
        while (true) {
            String line = readLine();
            //读取请求行是如果返回的是恐惧感字符串,说明单独读取到了CRLF
            if (line.isEmpty()) {
                break;
            }
            System.out.println("消息头:" + line);
            //存放所有消息头的Map,key:消息头的名字 value:消息头的值
            String[] data = line.split(":\\s");
            headers.put(data[0], data[1]);
        }
    }

    /**
     * 解析消息正文
     */
    private void parseContent() {
    }

    public String getMethod() {
        return method;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUri() {
        return uri;
    }

    public String getHeader(String name) {
        return headers.get(name);
    }

    /**
     * 提供一个字符串得到名字
     */
    public String getParameters(String name) {
        return parameters.get(name);
    }
}
