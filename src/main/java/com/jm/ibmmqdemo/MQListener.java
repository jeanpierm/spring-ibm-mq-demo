package com.jm.ibmmqdemo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MQListener {

    @JmsListener(destination = "${ibm.jm.mq.queue-1-name}")
    public void receiveMessage(final Message message) throws JMSException {
        String messageData = null;
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            messageData = textMessage.getText();
            log.info("Mensaje recibido -> " + messageData);
        }
        return;
    }
}
