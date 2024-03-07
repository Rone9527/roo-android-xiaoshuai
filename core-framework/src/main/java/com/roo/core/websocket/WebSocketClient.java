package com.roo.core.websocket;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.core.domain.trade.Arg;
import com.core.domain.trade.TickerBean;
import com.jess.arms.utils.ArmsUtils;
import com.roo.core.app.GlobalContext;
import com.roo.core.app.constants.EventBusTag;
import com.roo.core.app.constants.GlobalConstant;
import com.roo.core.utils.LogManage;
import com.roo.core.utils.ThreadManager;
import com.roo.core.utils.utils.TickerManager;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;

public class WebSocketClient {
    private static final String TAG = WebSocketClient.class.getSimpleName();

    private WebSocket webSocket;

    private final ScheduledFuture<?> scheduledReconnect;
    private final ClientProxy webSocketListener;
    private long lastReceiverTime = 0;
    private boolean canceled = false;
    private boolean isConnected = false;
    public static boolean logger = false;
    private final ScheduledFuture<?> scheduledArgs;
    private boolean isReconnected = false;

    public WebSocketClient(ClientProxy clientProxy) {
        this.webSocketListener = clientProxy;
        scheduledReconnect = ThreadManager.getScheduled().scheduleAtFixedRate(() -> {
            if (System.currentTimeMillis() - lastReceiverTime > 10_000 && !isConnected && !canceled) {
                init();
            }
            HashMap<String, String> message = new HashMap<>();
            message.put(Arg.CMD, Arg.SUB);
            message.put(Arg.TOPIC,"ping");
            String ticker = Arg.toJSONString(message);

            log(">>sendMessage>>" + ticker);
            webSocket.send(ticker);
        }, 0, 25, TimeUnit.SECONDS);

        scheduledArgs = ThreadManager.getScheduled().scheduleAtFixedRate(() -> {
            if (isConnected && !webSocketListener.getQueue().isEmpty()) {
                String parameter = webSocketListener.getQueue().remove(0);
                log(">>sendMessage>>" + parameter);
                webSocket.send(parameter);
            }
        }, 300, 50, TimeUnit.MILLISECONDS);

    }

    private void init() {
        canceled = false;
        if (webSocket != null) {
            log(">>initWebSocket>>断开重连");
            webSocket.close(1001, "断开重连");
            webSocket = null;
            isReconnected = true;
        }
        log(">>initWebSocket>>开始连接");
        webSocket = ArmsUtils.obtainAppComponentFromContext(GlobalContext.getAppContext()).okHttpClient()
                .newWebSocket(new Request.Builder().url(GlobalConstant.URL_WS).build(), new SimpleWebSocketListener() {

                    @Override
                    public void onOpen(WebSocket webSocket, Response response) {
                        log(">>onOpen>>连接成功");

                        HashMap<String, String> message = new HashMap<>();
                        message.put(Arg.CMD, Arg.SUB);
                        message.put(Arg.TOPIC,"tickers");
                        String ticker = Arg.toJSONString(message);

                        log(">>onOpen>>" + ticker);
                        webSocket.send(ticker);
                        if (isReconnected) {
                            log("发送断开重连Event");
                            EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.RECONNECT_EVENT);
                        }
                        isReconnected = false;
                        isConnected = true;
                    }

                    @Override
                    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                        isConnected = false;
                    }

                    @Override
                    public void onMessageReceived(String text) {
                        if (TextUtils.isEmpty(text)) return;
                        log(">>onMessageReceived>>" + text);
                        lastReceiverTime = System.currentTimeMillis();
                        try {
                            JSONObject jsonObject = JSONObject.parseObject(text);
                            String topic = jsonObject.getString("topic");
                            if ("tickers".equals(topic)) {
                                List<TickerBean> data = jsonObject.getJSONArray("data").toJavaList(TickerBean.class);
                                TickerManager.getInstance().setTickerList(data);
                                EventBus.getDefault().post(EventBusTag.NULL_EVENT, EventBusTag.DATA_TICKER);
                            }
                        } catch (Exception e) {
                            log(">>onMessageReceived>>数据解析出现异常" + e);
                        }
                    }
                });
    }

    private void log(String logStr) {
        if (logger) LogManage.i(TAG, logStr);
    }

    public void closeConnect() {
        lastReceiverTime = 0;
        canceled = true;
        if (webSocket != null) {
            log(">>initWebSocket>>主动断开连接");
            webSocket.close(1002, "主动断开连接");
            webSocket = null;
        }
        if (scheduledReconnect != null && !scheduledReconnect.isCancelled()) {
            scheduledReconnect.cancel(true);
        }
        if (scheduledArgs != null && !scheduledArgs.isCancelled()) {
            scheduledArgs.cancel(true);
        }
    }

}
