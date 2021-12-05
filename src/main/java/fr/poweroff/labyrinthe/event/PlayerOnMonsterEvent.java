package fr.poweroff.labyrinthe.event;

public class PlayerOnMonsterEvent implements Event<Boolean> {

    @Override
    public String getName() {
        return "PlayerOnMonster";
    }

    @Override
    public Boolean getData() {
        return true;
    }
}
