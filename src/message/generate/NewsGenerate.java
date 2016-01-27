package message.generate;

import message.response.Article;
import message.response.NewsMessage;
import util.MessageUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wzhuo on 2015/11/5.
 */
public class NewsGenerate {
    public static String createNews(Map<String, String> requestMap) {
        String respMessage = null;

        String fromUserName = requestMap.get("FromUserName");
        String toUserName = requestMap.get("ToUserName");

        String content = requestMap.get("Content");

        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);

        List<Article> articleList = new ArrayList<Article>();
        if ("1".equals(content)) {
            Article article = new Article();
            article.setTitle("The Single Article");
            article.setDescription
                    ("this is a single article!The platform is " +
                            "developing!Let\'s watch!");
            article.setPicUrl("");
            article.setUrl("http://txiner.top");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        else if ("2".equals(content)) {
            Article article = new Article();
            article.setTitle("The Single without pictures");
            article.setDescription("jlushare" + emoji(0x1f602) + "this is" +
                    " a " +
                    "good wechat demo!you can share your opinion with me!");
            article.setPicUrl("");
            article.setUrl("http://www.jlu.edu.cn");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }
        else if ("3".equals(content)) {
            Article article1 = new Article();
            article1.setTitle("The Multi Articles\nnote");
            article1.setDescription("");
            article1.setPicUrl("");
            article1.setUrl("http://txiner.top");

            Article article2 = new Article();
            article2.setTitle("the second article\nsomething");
            article2.setDescription("");
            article2.setPicUrl("");
            article2.setUrl("http://txiner.top");

            articleList.add(article1);
            articleList.add(article2);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }else {
            Article article = new Article();
            article.setTitle("DO NOT CLICK ON ME");
            article.setDescription
                    ("I wonder why you send me this message!I can not handle " +
                            "this message!");
            article.setPicUrl("");
            article.setUrl("http://txiner.top");
            articleList.add(article);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            respMessage = MessageUtil.newsMessageToXml(newsMessage);
        }

        return respMessage;
    }

    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

}
