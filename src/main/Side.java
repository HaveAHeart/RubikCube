package main;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

public final class Side {
    private int dim;
    private Color[][] colors = new Color[dim][dim]; //row - position (e.g. colors[0][2] - 1st row, 3rd position)

    //overriding toString(), equals and hashcode for tests

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("\n");
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                switch (colors[i][j]) {
                    case WHITE: { sb.append("w "); break; }
                    case YELLOW: { sb.append("y "); break; }
                    case ORANGE: { sb.append("o "); break; }
                    case BLUE: { sb.append("b "); break; }
                    case RED: { sb.append("r "); break; }
                    case GREEN: { sb.append("g "); break; }
                }

            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Side side = (Side) o;
        return dim == side.dim &&
                Arrays.equals(colors, side.colors);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dim);
        result = 31 * result + Arrays.hashCode(colors);
        return result;
    }

    //generators
    public Side(Col[] cols, int dimension) {
        dim = dimension;
        if (cols.length != dim) { //column checks its own size in its constructor
            throw new InputMismatchException("wrong amount of columns while trying to create a side");
        }
        Color[][] tempColor = new Color[dim][dim];
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++){
                tempColor[i][j] = cols[j].getValue(i);

            }
        }
        this.colors = tempColor;
    }

    public Side(Row[] rows, int dimension) {
        dim = dimension;
        if (rows.length != dim) { //row checks its own size in its constructor
            throw new InputMismatchException("wrong amount of columns while trying to create a side");
        }
        for (int i = 0; i < dim; i++) {
            for (int b = 0; b < dim; b++) {
                colors[i][b] = rows[i].getValue(b);
            }
        }
    }

    public Side(Color[][] input, int dimension) {
        dim = dimension;
        if (input.length != dimension) { //checking amount of rows
            throw new InputMismatchException("wrong amount of inner arrays while trying to create a side");
        }
        for (int i = 0; i < dim; i++) {
            if (input[i].length != dimension) { //checking amount of columns
                throw new InputMismatchException("wrong amount of inner arrays while trying to create a side");
            }
        }
        colors = input;
    }

    //------------------
    //side parts getters
    public Row getRow(int rowNum) {
        //input data check
        if (rowNum < 0 || rowNum > dim - 1) {
            throw new InputMismatchException("incorrect rowNum while trying to receive a row from side");
        }

        return new Row(colors[rowNum], dim); //just taking Color[] array we need
    }

    public Col getCol(int colNum) {
        //input data check
        if (colNum < 0 || colNum > dim - 1) {
            throw new InputMismatchException("incorrect colNum while trying to receive a col from side");
        }

        Color[] tempColors = new Color[dim];
        for (int i = 0; i < dim; i++) {
            tempColors[i] = colors[i][colNum]; //taking color from needed positions
        }
        return new Col(tempColors, dim);
    }

    //side parts setter
    public void setRow(int rowNum, Row replace) {
        for (int i = 0; i < dim; i++) {
            colors[rowNum][i] = replace.getValue(i);
        }
    }

    public void setCol(int colNum, Col replace) {
        for (int i = 0; i < dim; i++) {
            colors[i][colNum] = replace.getValue(i);
        }
    }

    //rotation methods
    public Side rotateCW() {
        Col[] newCols = new Col[dim];
        for (int i = 0; i < dim; i++) {
            newCols[dim - 1 - i] = new Row(colors[i], dim).rotateCW();
        }
        return new Side(newCols, dim);

    }

    public Side rotateCCW() {
        Col[] newCols = new Col[dim];
        for (int i = 0; i < dim; i++) {
            newCols[i] = new Row(colors[i], dim).rotateCCW();
        }
        return new Side(newCols, dim);
    }
}
