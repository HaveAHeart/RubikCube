package main;

public class Rubik {
    int dim;

    public Rubik(int dimensions) {
        dim = dimensions;
    }

    public class Row {
        String values;

        //generator
        public Row(String str) {
            values = str;
        } //TODO: str length check

        //------------------
        //rotation methods
        public Col rotateCW() {
            return new Col(values);
        }

        public Col rotateCCW() {
            values = new StringBuilder(values).reverse().toString();
            return new Col(values);
        }

        //------------------
        //access methods
        public char getChar(int num) {
            return values.charAt(num);
        } //TODO: num check
    }

    public class Col {
        String values;

        //generator
        public Col(String str) {
            values = str;
        } //TODO: str length check

        //------------------
        //rotation methods
        public Row rotateCW() {
            values = new StringBuilder(values).reverse().toString();
            return new Row(values);
        }

        public Row rotateCCW() {
            return new Row(values);
        }

        //------------------
        //access methods
        public char getChar(int num) {
            return values.charAt(num);
        } //TODO: num check
    }

    public class Side {
        String values = "";
        StringBuilder sb = new StringBuilder();

        //generators
        public Side(Col[] cols) { //TODO: check amount and size of columns
            for (int i = 0; i < dim; i++) {
                for (int b = 0; b < dim; b++) {
                    sb.append(cols[b].getChar(i));
                }
            }
            values = sb.toString();
        }

        public Side(Row[] rows) { //TODO: check amount and size of rows
            for (int i = 0; i < dim; i++) {
                for (int b = 0; b < dim; b++) {
                    sb.append(rows[i].getChar(b));
                }
            }
            values = sb.toString();
        }

        public Side(String str) {
            values = str;
        }

        //------------------
        //side parts access methods
        public Row getRow(int rowNum) { //TODO: check rowNum
            StringBuilder sb = new StringBuilder();
            for (int i = rowNum * (dim - 1) - 1; i < rowNum * dim - 1; i++) {
                sb.append(values.charAt(i));
            }
            return new Row(sb.toString());
        }

        public Col getCol(int colNum) { //TODO: check colnum
            StringBuilder sb = new StringBuilder();
            for (int i = colNum - 1; i < dim * dim; i += dim) {
                sb.append(values.charAt(i));
            }
            return new Col(sb.toString());
        }

        public Side changeRow(int rowNum, Row replace) {
            StringBuilder sb = new StringBuilder(values);
            values = sb.replace(dim * rowNum, dim * (rowNum + 1), replace.values).toString();
            return this;
        }

        public Side changeCol(int colNum, Col replace) {
            StringBuilder sb = new StringBuilder(values);
            for (int i = 0; i < dim; i++) {
                sb.replace(colNum + (dim * i), colNum + (dim * i) + 1,
                        Character.toString(replace.values.charAt(i)));
            }
            values = sb.toString();
            return this;
        }

        //------------------
        //rotation methods
        public Side rotateCW() {
            Col[] cols = new Col[dim];
            for (int i = dim - 1; i >= 0; i--) {
                cols[i] = this.getRow(dim - i).rotateCW();
            }
            return new Side(cols);
        }

        public Side rotateCCW() {
            Col[] cols = new Col[dim];
            for (int i = 0; i < dim; i++) {
                cols[i] = this.getRow(i).rotateCCW();
            }
            return new Side(cols);
        }

        //------------------
        //side state access methods
        public void sideState() { //TODO: maybe, some array-returning realisation?
            for (int i = 0; i < dim; i++) {
                StringBuilder sb = new StringBuilder();
                for (int b = 0; b < dim; b++) {
                    sb.append(values.charAt(i * dim + b));
                    sb.append(" ");
                }
                sb.append("\r\n");
            }
            System.out.println(sb.toString());

        }
    }

    public class Cube {
        Side[] values = new Side[6]; //sides arr format: top - front - right - back - left - bot sides

        //generators
        public Cube(Side[] sides) {
            values = sides;
        }

        //------------------
        //rotation methods
        public void rotateRow(String side, String direction, int startNum, int endNum) {
            if (side.toLowerCase().equals("top") || side.toLowerCase().equals("bottom")) {
                if (direction.toLowerCase().equals("cw")) {
                    for (int rowNum = startNum; rowNum <= endNum; rowNum++) {
                        if (rowNum == 0) { //rotating whole affected front side
                            if (side.toLowerCase().equals("top")) { values[3] = values[3].rotateCCW(); }
                            else { values[1] = values[1].rotateCW(); }
                        } else {
                            if (rowNum == dim - 1) { //rotating whole affected back side
                                if (side.toLowerCase().equals("top")) { values[1] = values[1].rotateCW(); }
                                else { values[3] = values[3].rotateCCW(); }
                            }
                        }
                        Row replacingRow = values[4].getCol(dim - rowNum).rotateCW();
                        Row tempRow = values[0].getRow(rowNum); //rotating - taking row at the top side
                        values[0] = values[0].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[2].getCol(dim - rowNum).rotateCW();
                        values[2] = values[2].changeCol(dim - rowNum, replacingRow.rotateCW());
                        replacingRow = tempRow;
                        tempRow = values[5].getRow(dim - rowNum);
                        values[5] = values[5].changeRow(dim - rowNum, replacingRow);
                        replacingRow = tempRow;
                        values[4] = values[4].changeCol(dim - rowNum, replacingRow.rotateCW());
                    }
                } else {
                    for (int rowNum = startNum; rowNum <= endNum; rowNum++) {
                        //rotating whole affected front/back side
                        if (rowNum == 0) {
                            if (side.toLowerCase().equals("top")) { values[3] = values[3].rotateCCW(); }
                            else { values[1] = values[1].rotateCW(); }
                        } else {
                            if (rowNum == dim - 1) {
                                if (side.toLowerCase().equals("top")) { values[1] = values[1].rotateCW(); }
                                else { values[3] = values[3].rotateCCW(); }
                            }
                        }
                        //------------------
                        //replacing row - column - row - column
                        Row replacingRow = values[2].getCol(dim - rowNum).rotateCCW();
                        Row tempRow = values[0].getRow(rowNum); //rotating - taking row at the top side
                        values[0] = values[0].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[4].getCol(dim - rowNum).rotateCCW();
                        values[4] = values[4].changeCol(dim - rowNum, replacingRow.rotateCCW());
                        replacingRow = tempRow;
                        tempRow = values[5].getRow(dim - rowNum);
                        values[5] = values[5].changeRow(dim - rowNum, replacingRow);
                        replacingRow = tempRow;
                        values[4] = values[4].changeCol(dim - rowNum, replacingRow.rotateCCW());
                    }
                }
            } else {
                if (direction.toLowerCase().equals("cw")) {
                    for (int rowNum = startNum; rowNum <= endNum; rowNum++) {
                        if (rowNum == 0) { values[0] = values[0].rotateCW(); }
                        else {
                            if (rowNum == dim - 1) { values[5] = values[5].rotateCCW(); }//top/bottom side
                        }
                        Row replacingRow = values[4].getRow(rowNum);
                        Row tempRow = values[1].getRow(rowNum);
                        values[1] = values[1].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[2].getRow(rowNum);
                        values[2] = values[2].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[3].getRow(rowNum);
                        values[3] = values[3].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        values[4] = values[4].changeRow(rowNum, replacingRow);
                    }
                } else {
                    for (int rowNum = startNum; rowNum <= endNum; rowNum++) {
                        //rotating whole affected front/back side
                        if (rowNum == 0) { values[0] = values[0].rotateCCW(); } //rotating whole affected
                        else {
                            if (rowNum == dim - 1) { values[5] = values[5].rotateCW(); } //top/bottom side
                        }
                        //------------------
                        //replacing row - column - row - column
                        Row replacingRow = values[2].getRow(rowNum);
                        Row tempRow = values[1].getRow(rowNum);
                        values[1] = values[1].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[4].getRow(rowNum);
                        values[4] = values[4].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        tempRow = values[3].getRow(rowNum);
                        values[3] = values[3].changeRow(rowNum, replacingRow);
                        replacingRow = tempRow;
                        values[2] = values[2].changeRow(rowNum, replacingRow);
                    }
                }
            }
        }
        public void rotateCol(String side, String direction, int startNum, int endNum) {
            if (side.toLowerCase().equals("left") || side.toLowerCase().equals("right")) {
                if (direction.toLowerCase().equals("cw")) {
                    for (int colNum = startNum; colNum <= endNum; colNum++) {
                        if (colNum == 0) { //rotating whole affected front/back side
                            if (side.toLowerCase().equals("left")) { values[3] = values[3].rotateCCW(); }
                            else { values[1] = values[1].rotateCW(); }
                        } else {
                            if (colNum == dim - 1) {
                                if (side.toLowerCase().equals("left")) { values[1] = values[1].rotateCW(); }
                                else { values[3] = values[3].rotateCCW(); }
                            }
                        }
                        Col replacingCol = values[5].getRow(dim - colNum).rotateCW();
                        Col tempCol = values[4].getCol(colNum); //rotating - taking row at the top side
                        values[4] = values[4].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[0].getRow(colNum).rotateCW();
                        values[0] = values[0].changeRow(colNum, replacingCol.rotateCW());
                        replacingCol = tempCol;
                        tempCol = values[2].getCol(dim - colNum);
                        values[2] = values[2].changeCol(dim - colNum, replacingCol);
                        replacingCol = tempCol;
                        values[5] = values[5].changeRow(dim - colNum, replacingCol.rotateCW());
                    }
                } else {
                    for (int colNum = startNum; colNum <= endNum; colNum++) {
                        //rotating whole affected front/back side
                        if (colNum == 0) {
                            if (side.toLowerCase().equals("left")) { values[3] = values[3].rotateCW(); }
                            else { values[1] = values[1].rotateCCW(); }
                        } else {
                            if (colNum == dim - 1) {
                                if (side.toLowerCase().equals("left")) { values[1] = values[1].rotateCCW(); }
                                else { values[3] = values[3].rotateCW(); }
                            }
                        }
                        //------------------
                        //replacing column - row - column - row
                        Col replacingCol = values[0].getRow(colNum).rotateCCW();
                        Col tempCol = values[4].getCol(colNum);
                        values[4] = values[4].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[5].getRow(dim - colNum).rotateCCW();
                        values[5] = values[5].changeRow(dim - colNum, replacingCol.rotateCCW());
                        replacingCol = tempCol;
                        tempCol = values[2].getCol(dim - colNum);
                        values[2] = values[2].changeCol(dim - colNum, replacingCol);
                        replacingCol = tempCol;
                        values[0] = values[0].changeRow(dim - colNum, replacingCol.rotateCCW());
                    }
                }
            } else {
                if (direction.toLowerCase().equals("cw")) { //front side should go up
                    for (int colNum = startNum; colNum <= endNum; colNum++) {
                        //rotating affected left/right sides
                        if (colNum == 0) {
                            if (side.toLowerCase().equals("bottom")) {values[2] = values[2].rotateCW(); }
                            else {values[4] = values[4].rotateCCW(); }
                        } else if (colNum == dim - 1) {
                            if (side.toLowerCase().equals("bottom")) {values[4] = values[4].rotateCCW();}
                            else {values[2] = values[2].rotateCW(); }
                        }
                        Col replacingCol = values[5].getCol(colNum);
                        Col tempCol = values[1].getCol(colNum);
                        values[1] = values[1].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[0].getCol(colNum);
                        values[0] = values[0].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[3].getCol(dim - colNum); //back side - need to rotate column
                        values[3] = values[3].changeCol(dim - colNum, replacingCol.rotateCW().rotateCW());
                        replacingCol = tempCol;
                        values[5] = values[5].changeCol(colNum, replacingCol.rotateCW().rotateCW());
                    }
                } else {
                    for (int colNum = startNum; colNum <= endNum; colNum++) {
                        //rotating affected left/right sides
                        if (colNum == 0) {
                            if (side.toLowerCase().equals("bottom")) {values[2] = values[2].rotateCCW(); }
                            else {values[4] = values[4].rotateCW(); }
                        } else if (colNum == dim - 1) {
                            if (side.toLowerCase().equals("bottom")) {values[4] = values[4].rotateCW();}
                            else {values[2] = values[2].rotateCCW(); }
                        }
                        //------------------
                        //replacing column - row - column - row
                        Col replacingCol = values[0].getCol(colNum);
                        Col tempCol = values[1].getCol(colNum);
                        values[1] = values[1].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[5].getCol(colNum);
                        values[5] = values[5].changeCol(colNum, replacingCol);
                        replacingCol = tempCol;
                        tempCol = values[3].getCol(dim - colNum);
                        values[3] = values[3].changeCol(dim - colNum, replacingCol.rotateCW().rotateCW());
                        replacingCol = tempCol; //these double rotations - reverse column because back is mirrored
                        values[0] = values[0].changeCol(colNum, replacingCol.rotateCW().rotateCW());
                    }
                }
            }
        }
    }
}
