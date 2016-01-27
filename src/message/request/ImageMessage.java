package message.request;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class ImageMessage extends BaseMessage {

    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
}