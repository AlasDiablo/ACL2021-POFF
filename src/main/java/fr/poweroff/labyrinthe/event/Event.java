package fr.poweroff.labyrinthe.event;

/**
 * Event interface use to define the base function need to use it
 *
 * @param <T> The type of the data use in the event
 */
public interface Event<T> {

    /**
     * Function used to get the name of the event
     *
     * @return A string containing the name of the event
     */
    String getName();

    /**
     * Function used to get the element how throw the event
     *
     * @return An object of the type T
     */
    T getData();
}
