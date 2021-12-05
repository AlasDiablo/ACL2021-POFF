package fr.poweroff.labyrinthe.event;

import fr.poweroff.labyrinthe.level.entity.RailGunProjectile;

public class ProjectileOnMonsterEvent implements Event<Boolean> {
    private final RailGunProjectile projectile;

    public ProjectileOnMonsterEvent(RailGunProjectile projectile) {
        this.projectile = projectile;
    }

    @Override
    public String getName() {
        return "ProjectileOnMonsterEvent";
    }

    @Override
    public Boolean getData() {
        return true;
    }

    public RailGunProjectile getProjectile() {
        return projectile;
    }
}
