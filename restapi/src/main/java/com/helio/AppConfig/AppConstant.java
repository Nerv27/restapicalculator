package com.helio.AppConfig;

public class AppConstant {
    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    public static final String IN_PROGRESS = "IN_PROGRESS";
    public static final String IN_QUEUE = "REQUEST_IN_QUEUE";
    public static final String FAILED = "FAILED";

    /* Error codes Starts */
    public static final String MESSAGE_QUEUE_SEND_ERROR = "MESSAGE_QUEUE_SEND_ERROR";
    public static final String MESSAGE_QUEUE_RECEIVE_ERROR = "MESSAGE_QUEUE_RECEIVE_ERROR";

    /* Constants */
    public static final int MESSAGE_RETRY_DELAY=5000;
}
