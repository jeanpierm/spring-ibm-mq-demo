package com.jm.ibmmqdemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MQListener {

    @Value("${ibm.jm.mq.queue-1-name}")
    String queueName;

    // @JmsListener(destination = "${ibm.jm.mq.queue-1-name}")
    public void receiveMessage(final Message message) {
        try {
            String messageData = null;
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                final var correlatrionId = textMessage.getJMSCorrelationID();
                messageData = textMessage.getText();
                log.info("Mensaje recibido: [{}] {}", correlatrionId, messageData);
            }
        } catch (Exception ex) {
            log.error("Un error ocurrió escuchando cola " + queueName, ex);
        }
    }
}
