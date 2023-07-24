package com.jm.ibmmqdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping
@RestController
@Slf4j
public class MQController {

    @Autowired
    JmsOperations jmsOperations;

    @Value("${ibm.jm.mq.queue-1-name}")
    String queueName;

    @GetMapping(value = "/send", produces = MediaType.TEXT_PLAIN_VALUE)
    public String send() {
        final var message = "Helluuuuuuuuuuuu!!";
        try {
            jmsOperations.convertAndSend(queueName, message);
            log.info("MENSAJE ENVIADO: " + message);
            return "MENSAJE ENVIADO: " + message;
        } catch (Exception ex) {
            log.error("Error enviando mensaje IBM MQ", ex);
            return "OOPS, ERROR ENVIANDO MENSAJE: " + ex.getMessage();
        }
    }

    @GetMapping(value = "/receive", produces = MediaType.TEXT_PLAIN_VALUE)
    public String receive() {
        try {
            final var messageRaw = jmsOperations.receiveAndConvert(queueName);
            if (messageRaw == null) {
                log.info("NO HAY MENSAJES");
                return "NO HAY MENSAJES";
            }
            final var message = messageRaw.toString();
            log.info("MENSAJE RECIBIDO: " + message);
            return "MENSAJE RECIBIDO: " + message;
        } catch (Exception ex) {
            log.error("Error enviando mensaje IBM MQ", ex);
            return "OOPS, ERROR RECIBIENDO MENSAJE: " + ex.getMessage();
        }
    }
}
