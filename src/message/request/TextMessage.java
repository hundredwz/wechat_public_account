package message.request;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class TextMessage extends BaseMessage{
    private String Content;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
