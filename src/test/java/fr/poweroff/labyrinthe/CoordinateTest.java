package fr.poweroff.labyrinthe;

import fr.poweroff.labyrinthe.model.Coordinate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {

    @Test
    public void createGetterEquals() {
        var coordinate = new Coordinate(1, 1);
        assertEquals("X value should be 1!", 1, coordinate.getCoorX());
        assertEquals("Y value should be 1!", 1, coordinate.getCoorY());
        assertEquals("Coordinate(1, 1) should be the same as an other instance of Coordinate(1, 1)", new Coordinate(1, 1), coordinate);
    }

    @Test
    public void setter() {
        var coordinate = new Coordinate(1, 1);
        coordinate.setCoorX(2);
        assertEquals("X value should be 2!", 2, coordinate.getCoorX());
        assertEquals("Y value should be 1!", 1, coordinate.getCoorY());
        assertEquals("Coordinate(2, 1) should be the same as an other instance of Coordinate(2, 1)", new Coordinate(2, 1), coordinate);
    }

    @Test
    public void toStringGetter() {
        var coordinate    = new Coordinate(1, 1);
        var toStringValue = "Coordinate{coorX=1, coorY=1}";
        assertEquals("toString value should be: " + toStringValue, toStringValue, coordinate.toString());
    }
}
