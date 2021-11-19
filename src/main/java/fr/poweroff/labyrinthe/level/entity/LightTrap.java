package fr.poweroff.labyrinthe.level.entity;

import fr.poweroff.labyrinthe.utils.Segment;

import java.awt.*;
import java.util.ArrayList;

public class LightTrap{
    ArrayList<Segment>  rayLight;
    LightTrap(){

    }
    public void evolve() {

    }
    public void draw(Graphics2D graph){
        rayLight.forEach(Segment -> Segment.draw(graph) );
    }

}
