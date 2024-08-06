package stib.util;

public interface Observable {
    /**
     * Register the current observer
     * @param obs the observer to register
     */
    void register(Observer obs);

    /**
     * Unregister the observer hwo we want to remove
     * @param obs the observer to remove
     */
    void unregister(Observer obs);

}
