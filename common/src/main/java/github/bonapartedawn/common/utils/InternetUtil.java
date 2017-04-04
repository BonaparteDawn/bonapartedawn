package github.bonapartedawn.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.InetAddress;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class InternetUtil {

    /**
     * 用于HTTPS
     */
    private static TrustManager trustManager = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
    /**
     * 网络超时
     */
    private static int DEFAULT_TIME_OUT = 3000;

    /**
     * ping网络IP地址
     * @param ip
     * @return
     * @throws IOException
     */
    public static boolean ping(String ip) throws IOException {
        Assert.hasLength(ip,"IP_LEN0");
        return InetAddress.getByName(ip).isReachable(DEFAULT_TIME_OUT);
    }

    /**
     * ping网络IP地址
     * @param ip
     * @param time_out
     * @return
     * @throws IOException
     */
    public static boolean ping(String ip,int time_out) throws IOException {
        Assert.hasLength(ip,"IP_LEN0");
        if (time_out <= 0){
            time_out = DEFAULT_TIME_OUT;
        }
        return InetAddress.getByName(ip).isReachable(time_out);
    }

    /**
     * 获取httpClient
     *
     * @return
     */
    private static HttpClient createClient(boolean isHttps) throws Exception {
        HttpClient client = null;
        HttpClientBuilder builder = HttpClientBuilder.create();
        if (isHttps) {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new TrustManager[] { trustManager }, null);
            client = builder.setSSLContext(context).build();
        } else {
            client =  builder.build();
        }
        return client;
    }

    /**
     * post请求
     * @param url
     * @param para
     * @return
     * @throws Exception
     */
    public static <Model> String doPost(String url, Model para) throws Exception {
        HttpResponse response = request2Resonpse(url, para, true);
        return EntityUtils.toString(response.getEntity());
    }
    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static <Model> String doGet(String url,Model para) throws Exception {
        HttpResponse response = request2Resonpse(url, para,false);
        return EntityUtils.toString(response.getEntity());
    }
    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static <Model> String doGet(String url) throws Exception {
        HttpResponse response = request2Resonpse(url, null,false);
        return EntityUtils.toString(response.getEntity());
    }
    /**
     * get请求
     * @param url
     * @return
     * @throws Exception
     */
    public static <Model> HttpResponse request2Resonpse(String url,Model para,boolean isPost) throws Exception {
        HttpRequestBase httpGet = newHttpRequestBase(url, para, isPost);
        return excute(httpGet);
    }
    public static HttpResponse excute(HttpRequestBase httpRequestBase) throws Exception{
        String url = httpRequestBase.getURI().toString();
        boolean isHttps = false;
        if (url.startsWith("https")) {
            isHttps = true;
        }
        HttpClient client = createClient(isHttps);
        return client.execute(httpRequestBase);
    }
    public static <Model> HttpRequestBase newHttpRequestBase(String url,Model para,boolean isPost) throws Exception{
        List<Entry<String, Object>> ps = ObjectUtils.queryModelProperties(para);
        HttpRequestBase res = null;
        if (isPost) {
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Entry<String, Object> entry : ps) {
                list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "utf-8");
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);
            res = httpPost;
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            if (ps.size() > 0) {
                stringBuilder.append("?");
            }
            for (int i = 0; i < ps.size(); i++) {
                Entry<String, ?> entry = ps.get(i);
                if (entry .getValue() != null) {
                    stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
                    if (i != ps.size()-1) {
                        stringBuilder.append("&");
                    }
                }
            }
            res = new HttpGet(url+stringBuilder.toString());
        }
        return res;
    }
}
