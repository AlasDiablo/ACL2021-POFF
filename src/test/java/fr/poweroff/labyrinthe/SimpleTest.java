package fr.poweroff.labyrinthe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class SimpleTest {

    public List<String> simpleStringList;

    @Before
    public void setup() {
        this.simpleStringList = new ArrayList<>();
        this.simpleStringList.add("test");
    }

    @After
    public void tear_down() {
        // les teste se termine ici généralment on peut fermé les Stream
    }

    @Test
    public void simple_test() {
        assertTrue("'simpleStringList' doit contenir 'test'", this.simpleStringList.contains("test"));

        // Si ce teste doit etre décommenté alors il y aura une erreur comme il y a 'test' dans 'simpleStringList'
        // assertFalse("simpleStringList ne doit contenir 'test'", this.simpleStringList.contains("test"));
    }
}
