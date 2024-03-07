package com.roo.core.utils;

import com.lzh.easythread.EasyThread;

import java.util.concurrent.ScheduledExecutorService;

public final class ThreadManager {

    private final static EasyThread io;
    private final static EasyThread cache;
    private final static EasyThread calculator;
    private final static EasyThread file;
    private static ScheduledExecutorService scheduled;

    static {
        io = EasyThread.Builder.createFixed(6).setName("IO").setPriority(7).build();
        cache = EasyThread.Builder.createCacheable().setName("Cache").build();
        calculator = EasyThread.Builder.createFixed(4).setName("Calculator").setPriority(Thread.MAX_PRIORITY).build();
        file = EasyThread.Builder.createFixed(4).setName("File").setPriority(3).build();
        scheduled = (ScheduledExecutorService) EasyThread.Builder.createScheduled(1).setName("Scheduled").setPriority(3).build().getExecutor();
    }

    public static ScheduledExecutorService getScheduled() {
        return scheduled;
    }

    public static EasyThread getIO() {
        return io;
    }

    public static EasyThread getCache() {
        return cache;
    }

    public static EasyThread getCalculator() {
        return calculator;
    }

    public static EasyThread getFile() {
        return file;
    }
}