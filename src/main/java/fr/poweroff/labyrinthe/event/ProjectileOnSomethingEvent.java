package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.entity.RailGunProjectile;

public class ProjectileOnSomethingEvent implements Event<Boolean>{
    private RailGunProjectile projectile;

    public ProjectileOnSomethingEvent(RailGunProjectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public String getName() {
        return "ProjectileOnSomething";
    }

    @Override
    public Boolean getData() {
        return true;
    }

    public RailGunProjectile getProjectile() {
        return projectile;
    }
}
