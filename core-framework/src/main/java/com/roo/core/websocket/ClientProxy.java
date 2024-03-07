package com.roo.core.websocket;

import com.roo.core.utils.LogManage;
import com.core.domain.trade.Arg;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author oldnine
 * @since 2018/11/12
 */
public class ClientProxy {

    private WebSocketClient mWebSocketClient;

    private ClientProxy() {

    }

    public static ClientProxy getInstance() {
        return Inner.CLIENT_PROXY;
    }

    private static class Inner {
        private static final ClientProxy CLIENT_PROXY = new ClientProxy();
    }

    public void init() {
        if (mWebSocketClient == null) {
            mWebSocketClient = new WebSocketClient(this);
        }
    }

    public void close() {
        if (mWebSocketClient != null) {
            mWebSocketClient.closeConnect();
            mWebSocketClient = null;
        }
    }

    /**
     * @param className 用于日志打印查看当前发送ws命令的类
     * @param event     事件
     * @param args      ws参数 [key1, value1, key2, value2, ...]
     */
    public void sendEvent(String className, @Arg.Event String event, String... args) {
        HashMap<String, String> message = new HashMap<>();
        message.put(Arg.CMD, event);
        if (args != null && args.length != 0) {
            int i = 0;
            while (i < args.length) {
                message.put(args[i], args[i + 1]);
                i = i + 2;
            }
        }
        if (WebSocketClient.logger) {
            LogManage.e(MessageFormat.format("className={0},sendMessage{1},args{2}",
                    className, event, Arrays.toString(args)));
        }
        queue.add(Arg.toJSONString(message));
    }

    private final ArrayList<String> queue = new ArrayList<>();

    public ArrayList<String> getQueue() {
        return queue;
    }

    public interface EventName {

    }

}
