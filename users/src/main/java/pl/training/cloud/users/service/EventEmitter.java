package pl.training.cloud.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EventEmitter {

    private Source source;

    @Autowired
    public EventEmitter(Source source) {
        this.source = source;
    }

    public <T> void emit(T message) {
        Logger.getLogger(getClass().getName()).log(Level.INFO, "### Sending message " + message);
        source.output().send(MessageBuilder
            .withPayload(message)
            .build());
    }

}
