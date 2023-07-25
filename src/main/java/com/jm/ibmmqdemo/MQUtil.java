package com.jm.ibmmqdemo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jms.core.MessageCreator;

import jakarta.jms.Session;
import jakarta.jms.TextMessage;

public class MQUtil {

    public static MessageCreator generateMessageCreator(String replyTo, String messageId, String message) {
        return (Session session) -> {
            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setJMSReplyTo(session.createQueue(replyTo));
            textMessage.setJMSCorrelationID(messageId);
            return textMessage;
        };
    }

    public static String generateCorrelationID() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }
}
