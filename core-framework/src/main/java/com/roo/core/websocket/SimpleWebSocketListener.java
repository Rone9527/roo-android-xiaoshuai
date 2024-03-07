package com.roo.core.websocket;

import java.io.IOException;

import okhttp3.WebSocket;
import okio.Buffer;
import okio.ByteString;
import okio.GzipSource;
import okio.Okio;

/**
 * <pre>
 *     project name: client-android
 *     author      : 李琼
 *     create time : 2020-03-28 14:56
 *     desc        : 描述--//SimpleWebSocketListener
 * </pre>
 */

public abstract class SimpleWebSocketListener extends okhttp3.WebSocketListener {

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        onMessageReceived(text);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        onMessageReceived(deflate(bytes));
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
    }

    public abstract void onMessageReceived(String text);

    private final Buffer buffer = new Buffer();

    private String deflate(ByteString bytes) {
        try {
            return Okio.buffer(new GzipSource(buffer.write(bytes))).readUtf8();
        } catch (IOException e) {
            return "";
        }
    }
}
