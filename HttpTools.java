package cn.edu.swu.ws.book;

import jdk.internal.util.xml.impl.Input;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpTools {

    public static String getHtml(String strUrl, String charset) throws IOException {
        URL url = new URL(strUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();

        return builder.toString();
    }


    public static byte[] getImage(String imgUrl) throws IOException {
        // TODO： 完成下载图片的功能

        URL url = new URL(imgUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int n =0;
        while((n = inputStream.read(data))!=-1){
            out.write(data,0,n);
        }
        return out.toByteArray();
    }

}
