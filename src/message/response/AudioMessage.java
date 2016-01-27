package message.response;

/**
 * Created by wzhuo on 2015/11/4.
 */
public class AudioMessage extends BaseMessage {
    private Audio audio;

    public Audio getMusic() {
        return audio;
    }

    public void setMusic(Audio audio) {
        audio = audio;
    }
}