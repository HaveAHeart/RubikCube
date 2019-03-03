package main;

import java.util.*;

public final class Cube {
    private Map<String, Side> sides = new HashMap<>();
    private int dim;
    String[] sideNames = { "top", "front", "right", "back", "left", "bottom" };

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

    //overriding toString() for debugging, cube will be printed in such format:
    // top row of sides: TOP; middle row of sides: BACK LEFT FRONT RIGHT; bottom row of sides: BOTTOM
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

    public Cube(int dimension) {
        dim = dimension;
        //standard color scheme for Rubik's cube
        sides.put("top", new Side(Color.GREEN, dim));
        sides.put("front", new Side(Color.WHITE, dim));
        sides.put("right", new Side(Color.ORANGE, dim));
        sides.put("back", new Side(Color.YELLOW, dim));
        sides.put("left", new Side(Color.RED, dim));
        sides.put("bottom", new Side(Color.BLUE, dim));
    }

    //side setter
    public void setSide(Side replacing, String sideName) {
        sides.put(sideName, replacing);
    }

    //CW rotation methods
    private void rotateCWRowSide (String side, int rowNum) {
        //only by rotating 1st or last row in side we affect other sides - that's why there are
        //checks for rowNum == 0 (1st row) and for rowNum == dim - 1 (last row)

        //if we are rotating layer on top or bottom side
        if ((rowNum == 0 && side.equals("top")) || (rowNum == dim - 1 && side.equals("bottom"))) {
            setSide(sides.get("back").rotateCCW(), "back");
            //data format requires to invert most of actions with back side
        }
        else if ((rowNum == 0 && side.equals("bottom")) || (rowNum == dim - 1 && side.equals("top"))) {
            setSide(sides.get("front").rotateCW(), "front");
        }

        //all the other sides
        else if (rowNum == 0) setSide(sides.get("top").rotateCW(), "top");
        else if (rowNum == dim - 1) setSide(sides.get("bottom").rotateCW(), "bottom");
    }

    private void rotateCWRowLayer(String side, int rowNum) {

        if (side.equals("top") || side.equals("bottom")) {

            //String[] vertOrderCW = { "top", "right", "bottom", "left" };
            //better to use 12 code strings to describe vertical row rotation in more details
            //than doing the loop which will be hardly understandable and will require same
            //amount of code strings

            int invertedNum = dim - 1 - rowNum;
            Row replacingRow = sides.get("left").getCol(rowNum).rotateCW().deepClone();
            Row tempRow;

            tempRow = sides.get("top").getRow(rowNum).deepClone();
            sides.get("top").setRow(rowNum, replacingRow);
            replacingRow = tempRow;

            tempRow = sides.get("right").getCol(invertedNum).rotateCW().deepClone();
            sides.get("right").setCol(invertedNum, replacingRow.rotateCW());
            replacingRow = tempRow;

            tempRow = sides.get("bottom").getRow(invertedNum).deepClone();
            sides.get("bottom").setRow(invertedNum, replacingRow);
            replacingRow = tempRow;

            sides.get("left").setCol(rowNum, replacingRow.rotateCW());

        }
        else {

            //here we can easily use loop because no additional row modifications required

            String[] commonOrderCW = { "back", "left", "front", "right" };
            Row replacingRow = sides.get("right").getRow(rowNum).deepClone();
            Row tempRow;

            for (String sideName : commonOrderCW) {
                tempRow = sides.get(sideName).getRow(rowNum).deepClone();
                sides.get(sideName).setRow(rowNum, replacingRow);
                replacingRow = tempRow;
            }
        }
    }

    public void rotateRowCW(String side, int startNum, int endNum) {
        side = side.toLowerCase();

        //checking input data
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

            //rotating affected side
            rotateCWRowSide(side, i);
        }
    }

    //CCW rotation methods
    private void rotateCCWRowLayer (String side, int rowNum) {

        if (side.equals("top") || side.equals("bottom")) {

            //String[] vertOrderCCW = { "top", "left", "bottom", "right" };
            //better to use 12 code strings to describe vertical row rotation in more details
            //than doing the loop which will be hardly understandable and will require same
            //amount of code strings

            int invertedNum = dim - 1 - rowNum;
            Row replacingRow = sides.get("right").getCol(invertedNum).rotateCCW().deepClone();
            Row tempRow;

            tempRow = sides.get("top").getRow(rowNum).deepClone();
            sides.get("top").setRow(rowNum, replacingRow);
            replacingRow = tempRow;

            tempRow = sides.get("left").getCol(rowNum).rotateCCW().deepClone();
            sides.get("left").setCol(rowNum, replacingRow.rotateCCW());
            replacingRow = tempRow;

            tempRow = sides.get("bottom").getRow(invertedNum).deepClone();
            sides.get("bottom").setRow(invertedNum, replacingRow);
            replacingRow = tempRow;

            sides.get("right").setCol(invertedNum, replacingRow.rotateCCW());

        }
        else {

            //here we can easily use loop because no additional row modifications required

            String[] commonOrderCCW = { "front", "left", "back", "right" };
            Row replacingRow = sides.get("right").getRow(rowNum).deepClone();
            Row tempRow;

            for (String sideName : commonOrderCCW) {
                tempRow = sides.get(sideName).getRow(rowNum).deepClone();
                sides.get(sideName).setRow(rowNum, replacingRow);
                replacingRow = tempRow;
            }
        }
    }

    private void rotateCCWRowSide (String side, int rowNum) {
        //only by rotating 1st or last row in side we affect other sides - that's why there are
        //checks for rowNum == 0 (1st row) and for rowNum == dim - 1 (last row)

        //if we are rotating layer on top or bottom side
        if ((rowNum == 0 && side.equals("top")) || (rowNum == dim - 1 && side.equals("bottom")))
            setSide(sides.get("back").rotateCW(), "back");
            //data format requires to invert most of actions with back side

        else if ((rowNum == 0 && side.equals("bottom")) || (rowNum == dim - 1 && side.equals("top")))
            setSide(sides.get("front").rotateCCW(), "front");

        //all the other sides
        else if (rowNum == 0) setSide(sides.get("top").rotateCCW(), "top");
        else if (rowNum == dim - 1) setSide(sides.get("bottom").rotateCCW(), "bottom");
    }

    public void rotateRowCCW(String side, int startNum, int endNum) {
        side = side.toLowerCase();

        //checking input data
        if (!Arrays.asList(sideNames).contains(side))
            throw new InputMismatchException("incorrect side name");

        if (startNum > endNum || startNum < 0 || startNum > dim - 1 || endNum > dim - 1)
            throw new InputMismatchException("incorrect startNum/endNum");


        for (int i = startNum; i <= endNum; i++) {

            //rotating affected rows or columns - on of them on side
            rotateCCWRowLayer(side, i);

            //rotating affected side
            rotateCCWRowSide(side, i);
        }
    }

    //Up rotation methods
    private void rotateUpColLayer (String side, int colNum) {
        int invertedNum = dim - 1 - colNum;

        switch (side) {
            case "right":
                //layer rotation of right columns use same mechanics (column - row replacing order and algorithm)
                //as CCW rotation of top rows, so there is no need to repeat same pieces of code
                rotateCCWRowLayer("top", invertedNum);
                break;

            case "left":
                //layer rotation of left columns use same mechanics (column - row replacing order and algorithm)
                //as CW rotation of top rows, so there is no need to repeat same pieces of code
                rotateCWRowLayer("top", colNum);
                break;

            case "back":
                //rotating back columns up is the same to rotating front/top/bottom columns down, so there is
                //no need to repeat same pieces of code
                rotateDownColLayer("front", invertedNum);
                break;

            default:
                //for top/front/bottom sides
                String[] commonOrderUp = {"bottom", "front", "top", "back"};
                Col replacingCol = sides.get("back").getCol(invertedNum).deepClone().invert();
                //data format requires me to invert most of actions with back side
                Col tempCol;

                //top/front/bottom column replacing
                for (int i = 0; i < 3; i++) {
                    tempCol = sides.get(commonOrderUp[i]).getCol(colNum).deepClone();
                    sides.get(commonOrderUp[i]).setCol(colNum, replacingCol);
                    replacingCol = tempCol;
                }

                //back column replacing
                sides.get("back").setCol(invertedNum, replacingCol.invert());
                break;

        }
    }

    private void rotateUpColSide (String side, int colNum) {
        //only by rotating 1st or last column in side we affect other sides - that's why there are
        //checks for colNum == 0 (1st col) and for colNum == dim - 1 (last col)
        switch (side) {
            case "right":
                if (colNum == 0) setSide(sides.get("front").rotateCCW(), "front");
                else if (colNum == dim - 1) setSide(sides.get("back").rotateCW(), "back");
                break;

            case "left":
                if (colNum == 0) setSide(sides.get("back").rotateCCW(), "back");
                else if (colNum == dim - 1) setSide(sides.get("front").rotateCW(), "front");
                break;

            case "back":
                if (colNum == 0) setSide(sides.get("right").rotateCCW(), "right");
                else if (colNum == dim - 1) setSide(sides.get("left").rotateCW(), "left");
                break;

            default:
                if (colNum == 0) setSide(sides.get("left").rotateCCW(), "left");
                else if (colNum == dim - 1) setSide(sides.get("right").rotateCW(), "right");
                break;
        }
    }

    public void rotateColUp(String side, int startNum, int endNum) {
        side = side.toLowerCase();

        //checking input data
        if (!Arrays.asList(sideNames).contains(side)) {
            throw new InputMismatchException("incorrect side name");
        }

        if (startNum > endNum || startNum < 0 || startNum > dim - 1 || endNum > dim - 1) {
            throw new InputMismatchException("incorrect startNum/endNum");
        }
        //----------
        for (int i = startNum; i <= endNum; i++) {

            //rotating affected rows or columns - on of them on side
            rotateUpColLayer(side, i);

            //rotating affected side
            rotateUpColSide(side, i);
        }
    }

    //Down rotation methods
    private void rotateDownColLayer (String side, int colNum) {
        int invertedNum = dim - 1 - colNum;

        switch (side) {
            case "right":
                //layer rotation of right columns use same mechanics (column - row replacing order and algorithm)
                //as CCW rotation of top rows, so there is no need to repeat same pieces of code
                rotateCWRowLayer("top", invertedNum);
                break;

            case "left":
                //layer rotation of left columns use same mechanics (column - row replacing order and algorithm)
                //as CW rotation of top rows, so there is no need to repeat same pieces of code
                rotateCCWRowLayer("top", colNum);
                break;

            case "back":
                //rotating back columns up is the same to rotating front/top/bottom columns down, so there is
                //no need to repeat same pieces of code
                rotateUpColLayer("front", invertedNum);
                break;

            default:
                //for top/front/bottom sides
                String[] commonOrderUp = {"top", "front", "bottom", "back"};
                Col replacingCol = sides.get("back").getCol(invertedNum).deepClone().invert();
                //data format requires me to invert most of actions with back side
                Col tempCol;

                //top/front/bottom column replacing
                for (int i = 0; i < 3; i++) {
                    tempCol = sides.get(commonOrderUp[i]).getCol(colNum).deepClone();
                    sides.get(commonOrderUp[i]).setCol(colNum, replacingCol);
                    replacingCol = tempCol;
                }

                //back column replacing
                sides.get("back").setCol(invertedNum, replacingCol.invert());
                break;

        }
    }

    private void rotateDownColSide (String side, int colNum) {
        //only by rotating 1st or last column in side we affect other sides - that's why there are
        //checks for colNum == 0 (1st col) and for colNum == dim - 1 (last col)
        switch (side) {
            case "right":
                if (colNum == 0) setSide(sides.get("front").rotateCW(), "front");
                else if (colNum == dim - 1) setSide(sides.get("back").rotateCCW(), "back");
                break;

            case "left":
                if (colNum == 0) setSide(sides.get("back").rotateCW(), "back");
                else if (colNum == dim - 1) setSide(sides.get("front").rotateCCW(), "front");
                break;

            case "back":
                if (colNum == 0) setSide(sides.get("right").rotateCW(), "right");
                else if (colNum == dim - 1) setSide(sides.get("left").rotateCCW(), "left");
                break;

            default:
                if (colNum == 0) setSide(sides.get("left").rotateCW(), "left");
                else if (colNum == dim - 1) setSide(sides.get("right").rotateCCW(), "right");
                break;
        }
    }

    public void rotateColDown(String side, int startNum, int endNum) {
        side = side.toLowerCase();

        //checking input data
        if (!Arrays.asList(sideNames).contains(side)) {
            throw new InputMismatchException("incorrect side name");
        }

        if (startNum > endNum || startNum < 0 || startNum > dim - 1 || endNum > dim - 1) {
            throw new InputMismatchException("incorrect startNum/endNum");
        }
        //----------
        for (int i = startNum; i <= endNum; i++) {

            //rotating affected rows or columns - on of them on side
            rotateDownColLayer(side, i);

            //rotating affected side
            rotateDownColSide(side, i);
        }
    }

    //cube randomisation
    public void randomizeState (int rotateNum) {
        if(rotateNum < 0)
            throw new InputMismatchException("incorrect rotateNum while trying to randomize");

        Random random = new Random();
        for (int i = 0; i < rotateNum; i++) {
            int action = random.nextInt(3);
            String side = sideNames[random.nextInt(6)];
            int num = random.nextInt(dim);
            switch (action) {
                case 0:
                    this.rotateRowCW(side, num, num);
                    break;
                case 1:
                    this.rotateRowCCW(side, num, num);
                    break;
                case 2:
                    this.rotateColUp(side, num, num);
                    break;
                case 3:
                    this.rotateColDown(side, num, num);
                    break;
            }
        }
    }
}