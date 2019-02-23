package main;

import java.util.HashMap;
import java.util.Map;

public class Cube {
    Map<String, Side> sides = new HashMap<>();
    //generators
    public Cube(Side[] input) { //side order - top, front, right, back, left, bottom
        sides.put("top", input[0]);
        sides.put("front", input[1]);
        sides.put("right", input[2]);
        sides.put("back", input[3]);
        sides.put("left", input[4]);
        sides.put("bottom", input[5]);
    }

    //------------------
    //rotation methods
    public void rotateRow(String side, String direction, int startNum, int endNum) {

    }

    public void rotateCol(String side, String direction, int startNum, int endNum) {

    }
}