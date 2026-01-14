package dev.yodaylay22.framework.event;

public interface EventListener<T> {
    void handle(T event);
}