package fr.poweroff.labyrinthe.event;

public class TimeOutEvent implements Event<Boolean> {
    @Override
    public String getName() {
        return "TimeOut";
    }

    @Override
    public Boolean getData() {
        return true;
    }
}
