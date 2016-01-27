package service;

import message.generate.MsgFromTextGenerate;
import message.generate.NewsGenerate;
import message.response.TextMessage;
import util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class CoreService {

    public static String processRequest(HttpServletRequest request) {
        String respMessage = null;
        try {
            String respContent = "the request works wrong!try it later!";

            Map<String, String> requestMap = MessageUtil.parseXml(request);

            String fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");

            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setFuncFlag(0);

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                respMessage= MsgFromTextGenerate.createMsg(requestMap);

                return respMessage;
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = emoji(0x1f602) + "sorry!our function has not " +
                        "been" +
                        " designed " +
                        "well!\nthe programmer is trying to build it " +
                        "better!\nwhat you " +
                        "send is picture message!";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                respContent = "sorry!our function has not been designed well!" +
                        "!\n" +
                        "the programmer is trying to build it better!\nwhat" +
                        " you send is location message!";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "sorry!our function has not been designed " +
                        "well!\n" +
                        "the programmer is trying to build it better!\nwhat " +
                        "you send is link message!";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "sorry!our function has not been designed " +
                        "well!\nthe programmer is trying to build it " +
                        "better!\nwhat you send is audio message!";
            }
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "Thanks for subscribing!";
                }
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                }
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                }
            }

            textMessage.setContent(respContent);
            respMessage = MessageUtil.textMessageToXml(textMessage);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }


    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }


}
