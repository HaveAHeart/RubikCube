package main;

public class Side {
    private int dim;
    private Color[][] colors = new Color[dim][dim]; //row - position (colors[0][2] - 1st row, 3rd position)

    //generators
    public Side(Col[] cols, int dimension) { //TODO: check amount and size of columns
        dim = dimension;
        for (int i = 0; i < dim; i++) {
            for (int b = 0; b < dim; b++) {
                colors[i][b] = cols[b].getValue(i);
            }
        }
    }

    public Side(Row[] rows, int dimension) { //TODO: check amount and size of rows
        dim = dimension;
        for (int i = 0; i < dim; i++) {
            for (int b = 0; b < dim; b++) {
                colors[i][b] = rows[i].getValue(b);
            }
        }
    }

    //------------------
    //side parts access methods
    public Row getRow(int rowNum) { //TODO: check rowNum
        return new Row(colors[rowNum], dim); //just taking Color[] array we need
    }

    public Col getCol(int colNum) { //TODO: check colnum
        Color[] tempColors = new Color[dim];
        for (int i = 0; i < dim; i++) {
            tempColors[i] = colors[i][colNum]; //taking color from needed positions
        }
        return new Col(tempColors, dim);
    }

    public void changeRow(int rowNum, Row replace) {
        for (int i = 0; i < dim; i++) {
            colors[rowNum][i] = replace.getValue(i);
        }
    }

    public void changeCol(int colNum, Col replace) {
        for (int i = 0; i < dim; i++) {
            colors[i][colNum] = replace.getValue(i);
        }
    }

    //------------------
    //rotation methods
    public void rotateCW() {
        Col[] tempCols = new Col[dim];      //rotating rows to columns
        for (int i = 0; i < dim; i++) { tempCols[dim - 1 - i] = this.getRow(i).rotateCW(); }
        for (int i = 0; i < dim; i++) {     //changing values just not to create new
            for (int b = 0; b < dim; b++) { //object and leave this method void-typed
                colors[i][b] = tempCols[b].getValue(i);
            }
        }
    }

    public void rotateCCW() {
        Col[] tempCols = new Col[dim];      //rotating rows to columns
        for (int i = 0; i < dim; i++) { tempCols[i] = this.getRow(i).rotateCCW(); }
        for (int i = 0; i < dim; i++) {     //changing values just not to create new
            for (int b = 0; b < dim; b++) { //object and leave this method void-typed
                colors[i][b] = tempCols[b].getValue(i);
            }
        }
    }

    //------------------
    //side state access methods
    public Color[][] sideState() {
        return colors;
    }
}
