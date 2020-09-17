package mvc;

public interface Observable {
    void registerObserver(String eventType, EventListener eventListener);

    void notifyObservers(String eventType);
}