package message.request;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class BaseMessage {
    //微信接收名称
    private String ToUserName;
    //微信发送名称
    private String FromUserName;
    // 信息创建时间
    private long CreateTime;
    // 消息类型
    private String MsgType;
    // 消息id
    private long MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}
