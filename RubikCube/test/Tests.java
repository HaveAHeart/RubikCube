import main.Rubik;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Tests {
    @Test
    public void rightRowRotating() {
        Rubik.Cube.Side.Row tester = new Rubik.Cube.Side.Row(char["w", "y", "b"]);
        Rubik.Cube.Side.Row needed = new Rubik.Cube.Side.Row(["w", "y", "b"])
        assertEquals([""]);
    }
}
