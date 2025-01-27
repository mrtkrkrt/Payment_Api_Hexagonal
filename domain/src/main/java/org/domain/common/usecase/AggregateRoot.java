package org.domain.common.usecase;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public abstract class AggregateRoot {

    private final Queue<Message> events = new LinkedList<>();

    public void registerEvent(Message event) {
        events.add(event);
        log.info("Event registered: {}", event);
    }

    public void publish() {
        events.forEach(event -> {
            EventPublisher instance = EventPublisher.InstanceHolder.INSTANCE;
            if (instance != null) {
                instance.publishAsync(event);
                unRegisterEvent(event);
            }
        });
        log.info("Events published: {}", events);
    }

    public void unRegisterEvent(Message event) {
        events.remove(event);
    }
}
