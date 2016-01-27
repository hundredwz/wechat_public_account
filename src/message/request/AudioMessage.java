package message.request;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class AudioMessage extends BaseMessage {

    private String MediaId;

    private String Format;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
}