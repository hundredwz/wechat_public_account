package message.generate;

import database.DaoHelper;
import message.response.Article;
import message.response.NewsMessage;
import util.MessageUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by wzhuo on 2015/11/14.
 */
public class FilmGenerate {

    public static String createText(Map<String, String> requestMap) {

        String respMessage = null;
        StringBuffer sb = new StringBuffer();

        String fromUserName = requestMap.get("FromUserName");
        String toUserName = requestMap.get("ToUserName");
        String filmName = requestMap.get("Content");
        //用户发送的第一个数字代表要查询的类型，在实际使用的时候要将其删除
        filmName = filmName.substring(1, filmName.length());

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);

        List<Map<String, String>> list = getLink(filmName);
        List<Article> articleList = new ArrayList<Article>();

        if (list.size() == 0) {
            Article article = new Article();
            article.setTitle(filmName);
            article.setDescription("对不起，我们没找到这部电影");
            //没找到以后换个哭脸表情
            article.setPicUrl("");
            article.setUrl("");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
            return respMessage;
        }

        for (Map<String, String> map : list) {

            //注意，此处未判断这些信息是否为空，理论上应该判断下，时间等原因暂时先不添加了
            Article article = new Article();
            article.setTitle(map.get("filmname"));
            article.setDescription(map.get("filmlink") + ":" + map.get
                    ("filmpwd") + "\n" + map.get("filmmsg")
                    + "\n空间数据库连接不上，搜索不了，以后再说");
            article.setPicUrl(map.get("filmposter"));
            article.setUrl(map.get("filmlink"));
            articleList.add(article);
        }
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        respMessage = MessageUtil.newsMessageToXml(newsMessage);

        return respMessage;
    }

    public static List<Map<String, String>> getLink(String filmName) {

        List<Map<String, String>> list = new ArrayList<>();

        String sql = "select * from filmdata where filmname=";

        Connection conn = DaoHelper.getConn();
        Statement stmt = null;
        ResultSet rs = null;


        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql + "'" + filmName + "';");
            while (rs.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("filmname", filmName);
                map.put("filmlink", rs.getString("filmlink"));
                map.put("filmpwd", rs.getString("filmpwd"));
                map.put("filmmsg", rs.getString("filmmsg"));
                map.put("filmposter", rs.getString("filmposter"));
                list.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DaoHelper.closeConn(conn);
        }
        return list;
    }


}
