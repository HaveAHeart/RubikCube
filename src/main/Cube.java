package main;

import java.util.*;

public class Cube {
    Map<String, Side> sides = new HashMap<>();
    private int dim;

    //overriding equals and hashcode for tests


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return dim == cube.dim &&
                Objects.equals(sides, cube.sides);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sides, dim);
    }

    //overriding toString() for debugging in such format:
    // top row: TOP; middle row: BACK LEFT FRONT RIGHT; bottom row: BOTTOM
    private char enumToChar(Color color) {
        char output = ' ';
        switch (color) {
            case WHITE: { output = 'W'; break; }
            case YELLOW: { output = 'Y'; break; }
            case ORANGE: { output = 'O'; break; }
            case BLUE: { output = 'B'; break; }
            case RED: { output = 'R'; break; }
            case GREEN: { output = 'G'; break; }
        }
        return output;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");

        //printing top side
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < (dim * 2 + 1) * 2; j++) { sb.append(' '); }
            for (int k = 0; k < dim; k++){
                sb.append(enumToChar(sides.get("top").getRow(i).getValue(k))).append(' ');
            }
            sb.append('\n');
        }

        //printing middle row: back - left - front - right
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                sb.append(enumToChar(sides.get("back").getRow(i).getValue(j))).append(" ");
            }
            sb.append('|');
            for (int j = 0; j < dim; j++) {
                sb.append(enumToChar(sides.get("left").getRow(i).getValue(j))).append(" ");
            }
            sb.append('|');
            for (int j = 0; j < dim; j++) {
                sb.append(enumToChar(sides.get("front").getRow(i).getValue(j))).append(" ");
            }
            sb.append('|');
            for (int j = 0; j < dim; j++) {
                sb.append(enumToChar(sides.get("right").getRow(i).getValue(j))).append(" ");
            }
            sb.append('|');
            sb.append('\n');
        }

        //printing bottom side
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < (dim * 2 + 1) * 2; j++) { sb.append(' '); }
            for (int j = 0; j < dim; j++){
                sb.append(enumToChar(sides.get("bottom").getRow(i).getValue(j))).append(' ');
            }
            sb.append('\n');
        }

        return sb.toString();
    }

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

    //replacing methods
    public void changeSide(Side replacing, String sideName) {
        this.sides.put(sideName, replacing);
    }

    //------------------
    //rotation methods
    public void rotateCWRowLayer(String side, int rowNum) {



        if (side.equals("top") || side.equals("bottom")) {

            //String[] vertOrderCW = { "top", "right", "bottom", "left" };
            //better to use 12 code strings to describe vertical row rotation in more details
            //than doing the loop which will be hardly understandable and will require same
            //amount of code strings

            int invertedNum = dim - 1 - rowNum;
            Row replacingRow = sides.get("left").getCol(rowNum).rotateCW().deepClone();
            Row tempRow;

            tempRow = sides.get("top").getRow(rowNum).deepClone();
            sides.get("top").changeRow(rowNum, replacingRow);
            replacingRow = tempRow;

            tempRow = sides.get("right").getCol(invertedNum).rotateCW().deepClone();
            sides.get("right").changeCol(invertedNum, replacingRow.rotateCW());
            replacingRow = tempRow;

            tempRow = sides.get("bottom").getRow(invertedNum).deepClone();
            sides.get("bottom").changeRow(invertedNum, replacingRow);
            replacingRow = tempRow;

            sides.get("left").changeCol(rowNum, replacingRow.rotateCW());

        }
        else {

            //here we can easily use loop because no additional row modifications required

            String[] commonOrderCW = { "back", "left", "front", "right" };
            Row replacingRow = sides.get("right").getRow(rowNum).deepClone();
            Row tempRow;

            for (String sideName : commonOrderCW) {
                tempRow = sides.get(sideName).getRow(rowNum).deepClone();
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
                this.changeSide(sides.get("back").rotateCCW(), "back");
                //data format requires to invert most of actions with back side
            }
            else if ((i == 0 && side.equals("bottom")) || (i == dim - 1 && side.equals("top"))) {
                this.changeSide(sides.get("front").rotateCW(), "front");
            }
            //all the other sides
            else if (i == 0) {
                this.changeSide(sides.get("top").rotateCW(), "top");
            }
            else if (i == dim - 1) {
                this.changeSide(sides.get("bottom").rotateCW(), "bottom");
            }
            //----------
        }
    }

    public void rotateCol(String side, String direction, int startNum, int endNum) {

    }
}