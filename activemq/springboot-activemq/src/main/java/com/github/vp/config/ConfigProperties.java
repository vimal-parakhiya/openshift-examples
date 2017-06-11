package com.github.vp.config;

/**
 * Created by vimalpar on 10/06/17.
 */
public class ConfigProperties {
    private String slice;
    private boolean enableSender;
    private boolean enableReceiver;
    private final int delayBetweenMessages;
    private final int retryDelay;
    private int maxRetries;

    public ConfigProperties(String slice, boolean enableSender, boolean enableReceiver, int delayBetweenMessages, int retryDelay, int maxRetries) {
        this.slice = slice;
        this.enableSender = enableSender;
        this.enableReceiver = enableReceiver;
        this.delayBetweenMessages = delayBetweenMessages;
        this.retryDelay = retryDelay;
        this.maxRetries = maxRetries;
    }

    public String getSlice() {
        return slice;
    }

    public boolean isEnableSender() {
        return enableSender;
    }

    public boolean isEnableReceiver() {
        return enableReceiver;
    }

    public int getDelayBetweenMessages() {
        return delayBetweenMessages;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "slice='" + slice + '\'' +
                ", enableSender=" + enableSender +
                ", enableReceiver=" + enableReceiver +
                ", delayBetweenMessages=" + delayBetweenMessages +
                ", retryDelay=" + retryDelay +
                ", maxRetries=" + maxRetries +
                '}';
    }
}
