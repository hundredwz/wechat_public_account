package message.generate;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wzhuo on 2015/11/6.
 */
public class MsgFromTextGenerate {

    public static String createMsg(Map<String, String> requestMap) {
        String respMessage = null;


        String content = requestMap.get("Content");
        //用户发送的第一个信息代表要使用的数据类型
        //目前定义：1.搜索电影
        //2.查看天气

        switch (content.charAt(0)) {
            case '1':
                respMessage = FilmGenerate.createText(requestMap);
                break;
            case '2':
                respMessage = WeatherGenerate.createText(requestMap);
                break;
            default:
                respMessage = FilmGenerate.createText(requestMap);
                break;
        }
        return respMessage;
    }


}
