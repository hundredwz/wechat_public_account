package message.generate;

import message.response.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.MessageUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by wzhuo on 2015/11/7.
 */
public class WeatherGenerate {


    public static String createText(Map<String, String> requestMap) {
        String respMessage = null;

        String respContent = null;
        StringBuffer sb = new StringBuffer();

        String fromUserName = requestMap.get("FromUserName");
        String toUserName = requestMap.get("ToUserName");
        String city = requestMap.get("Content");
        //用户发送的第一个数字代表要查询的类型，在实际使用的时候要将其删除
        city=city.substring(1, city.length());
        Map<String, String> map = null;

        //选择的是以textmessage返回消息，具体代码有需要再贴
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);

        try {
            map = getWeather(city);
        } catch (Exception e) {
            //如果找不到城市的话总是报空指针错误，就这样利用了，不知道符不符合规范
            respContent = "抱歉，没有查询到这个城市！\n请确认后再输入！";
            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);
            e.printStackTrace();
            return respMessage;

        }

        //返回的是map,存储方式是这样
        if (map.get("city") != null) {
            sb.append("城市:").append(map.get("city"));
        }
        if (map.get("status1") != null) {
            sb.append("\n当前天气:").append(map.get("status1"));
        }
        if (map.get("direction1") != null) {
            sb.append("\n当前风向:").append(map.get("direction1"));
        }
        if (map.get("temperature2") != null) {
            sb.append("\n最低温度:").append(map.get("temperature2"));
        }
        if (map.get("temperature1") != null) {
            sb.append("\n最高温度:").append(map.get("temperature1"));
        }
        if (map.get("yd_s") != null) {
            sb.append("\n建议:").append(map.get("yd_s"));
        }
        if (sb.toString() != null && !sb.toString().equals("")) {
            respContent = sb.toString();
        }

        textMessage.setContent(respContent);
        //将消息转换为xml
        respMessage = MessageUtil.textMessageToXml(textMessage);

        return respMessage;

    }

    /**
     * 将网络上的天气信息获取下来
     *
     * @return Map以图的形式返回
     */
    public static Map<String, String> getWeather(String city) {

        InputStream inputStream = null;
        HttpURLConnection conn = null;
        Map<String, String> map = null;
        //新浪提供的查询天气api
        String weatherLink = "http://php.weather.sina.com.cn/xml" +
                ".php?city=";


        try {
            //由于使用get方式，需要在连接上加上具体的信息
            weatherLink += codeTrans(city) + "&password=DJOYnieT8234jlsK&day=0";

            URL url = new URL(weatherLink);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            inputStream = conn.getInputStream();

            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            //根节点是profile，然后接下来的是weather，各节点都在weather下
            Element root = document.getRootElement();
            Element weather = root.element("Weather");
            List<Element> elementList = weather.elements();

            map = new HashMap<>();

            for (Element e : elementList)
                map.put(e.getName(), e.getText());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return map;
    }

    /**
     * 字符转换
     * 该api仅支持gb2312编码方式，将城市转换成相应的编码
     */
    private static String codeTrans(String city) {
        try {
            city = URLEncoder.encode(city, "gb2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return city;
    }

}
