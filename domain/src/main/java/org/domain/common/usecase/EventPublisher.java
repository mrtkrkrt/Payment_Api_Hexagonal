package org.domain.common.usecase;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class EventPublisher {

    private final ApplicationContext applicationContext;

    public EventPublisher(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        InstanceHolder.INSTANCE = this;
    }

    public void publish(Message message) {
        applicationContext.getBeansWithAnnotation(MessageDriven.class)
                .values()
                .forEach(event -> {
                    MessageDriven messageDriven = event.getClass().getAnnotation(MessageDriven.class);
                    String method = messageDriven.method();
                    Class<?> messageClass = messageDriven.message();
                    if (messageClass.isInstance(message)) {
                        try {
                            event.getClass().getMethod(method, messageClass).invoke(event, message);
                            log.info("Event published success: {}", message);
                        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            log.error("Event published failed: {}", message);
                            throw new RuntimeException(e);
                        }
                    }

                });
    }

    public void publishAsync(Message message) {
        CompletableFuture.runAsync(() -> publish(message)).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

    public static class InstanceHolder {
        public static EventPublisher INSTANCE;

        public static void setINSTANCE(EventPublisher INSTANCE) {
            InstanceHolder.INSTANCE = INSTANCE;
        }

        private InstanceHolder() {
        }
    }
}
