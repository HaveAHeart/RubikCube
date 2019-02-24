package main;

import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class Cube {
    private Map<String, Side> sides = new HashMap<>();
    private int dim;
    //generators
    public Cube(Side[] input, int dimension) { //side order - top, front, right, back, left, bottom
        sides.put("top", input[0]);
        sides.put("front", input[1]);
        sides.put("right", input[2]);
        sides.put("back", input[3]);
        sides.put("left", input[4]);
        sides.put("bottom", input[5]);
        dim = dimension;
    }

    //------------------
    //rotation methods
    public void rotateCWRowLayer(String side, int rowNum) {
        String[] vertOrderCW = { "top", "right", "bottom", "left" };
        String[] commonOrderCW = { "back", "left", "front", "right" };

        if (side.equals("top") || side.equals("bottom")) {
            int invertedNum = dim - 1 - rowNum;
            Row replacingRow = sides.get("left").getCol(rowNum).rotateCW();
            Row tempRow;

            tempRow = sides.get("top").getRow(rowNum);
            sides.get("top").changeRow(rowNum, replacingRow);
            replacingRow = tempRow;

            tempRow = sides.get("right").getCol(invertedNum).rotateCW();
            sides.get("right").changeCol(invertedNum, replacingRow.rotateCW());
            replacingRow = tempRow;

            tempRow = sides.get("bottom").getRow(invertedNum);
            sides.get("bottom").changeRow(invertedNum, replacingRow);
            replacingRow = tempRow;

            tempRow = sides.get("left").getCol(rowNum).rotateCW();
            sides.get("left").changeCol(rowNum, replacingRow.rotateCW());
        }
        else {
            Row replacingRow = sides.get(commonOrderCW[3]).getRow(rowNum);
            Row tempRow;
            for (String sideName : commonOrderCW) {
                tempRow = sides.get(sideName).getRow(rowNum);
                sides.get(sideName).changeRow(rowNum, replacingRow);
                replacingRow = tempRow;
            }
        }
    }

    public void rotateRowCW(String side, int startNum, int endNum) {
        side = side.toLowerCase();

        //checking input data
        String[] sideNames = { "top", "front", "right", "back", "left", "bottom" };
        if (!Arrays.asList(sideNames).contains(side)) {
            throw new InputMismatchException("incorrect side name");
        }

        if (startNum > endNum || startNum < 0 || startNum > dim - 1 || endNum > dim - 1) {
            throw new InputMismatchException("incorrect startNum/endNum");
        }
        //----------

        for (int i = startNum; i <= endNum; i++) {
            //rotating affected rows or columns - one of them on side
            rotateCWRowLayer(side, i);
            //----------

            //rotating WHOLE affected sides
            //if we are rotating layer on top or bottom side
            if ((i == 0 && side.equals("top")) || (i == dim - 1 && side.equals("bottom"))) {
                sides.get("back").rotateCCW(); //data format requires to invert most of actions with back side
            }
            else if ((i == 0 && side.equals("bottom")) || (i == dim - 1 && side.equals("top"))) {
                sides.get("front").rotateCW();
            }
            //all the other sides
            else if (i == 0) {
                sides.get("top").rotateCW();
            }
            else if (i == dim - 1) {
                sides.get("bottom").rotateCW();
            }
            //----------
        }
    }

    public void rotateCol(String side, String direction, int startNum, int endNum) {

    }
}