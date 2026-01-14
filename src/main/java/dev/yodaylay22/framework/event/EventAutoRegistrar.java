package dev.yodaylay22.framework.event;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;

import java.lang.reflect.Constructor;
import java.util.Set;
import java.util.function.Consumer;

public class EventAutoRegistrar {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void register(JavaPlugin plugin, Set<Class<?>> classes) {

        for (Class<?> clazz : classes) {

            if (!clazz.isAnnotationPresent(Listen.class)) {
                continue;
            }

            if (!EventListener.class.isAssignableFrom(clazz)) {
                throw new RuntimeException(
                        clazz.getName() + " has @Listen but does not implement EventListener"
                );
            }

            Listen listen = clazz.getAnnotation(Listen.class);

            try {
                Constructor<?> constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);

                EventListener listener =
                        (EventListener) constructor.newInstance();

                Class eventClass = listen.event();

                Consumer handler = new Consumer() {
                    @Override
                    public void accept(Object event) {
                        listener.handle(event);
                    }
                };

                plugin.getEventRegistry().registerGlobal(
                        eventClass,
                        handler
                );

            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to register event: " + clazz.getName(), e
                );
            }
        }
    }
}