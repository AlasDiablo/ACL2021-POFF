package fr.poweroff.labyrinthe.event;

public interface Event<T> {
    String getName();

    T getData();
}
