package fr.poweroff.labyrinthe.event;

/**
 * Event use when the timer run out
 */
public class TimeOutEvent implements Event<Boolean> {

    /**
     * @return Return the name of the event
     */
    @Override
    public String getName() {
        return "TimeOut";
    }

    /**
     * @return Return true
     */
    @Override
    public Boolean getData() {
        return true;
    }
}
