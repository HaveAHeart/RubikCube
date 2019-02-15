package main;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

/*
Cube -> 6 sides. Every side consists of 3 rows OR 3 columns.
Layers spin realisation can be created using rows ans columns.

clockwise rotation - short to CW
counterclockwise - same way to CCW

rotating CW-> top row becomes right column, middle - to middle, so on

111                               1                                   2
222 -> rotating row 111 to column 1 , placing it to the right, 222 -> 2 , so on
333                               1                                   2

connected sides rotation can also be realised with replacing rows

different sizes can be realised this way - just doing some amount of iterations

requires some Row/Column rotating methods(clockwise/counterclockwise)

TODO:
class Row
-rotateRight/rotateLeft methods(transforming to Column)
-way to save data(triplets(-, no size variations), arrays(maybe))
-constructor - solve size problems(4x4x4 cube, how to apply only 4 elements arrays)

class Column
-rotateRight/rotateLeft methods(transforming to Row)
-way to save data(triplets(-, no size variations), arrays(maybe))
-constructor - solve size problems(4x4x4 cube, how to apply only 4 elements arrays)

class Side
-getting 1st/2nd/3rd Row/Column (or just an array of rows/columns)
-constructor - creating Side from 3 Rows or 3 Columns

class Cube
-side state method (just printing it, returning an array)
-rotation methods(CW/CCW, dumb way - CCW spin = 3 CW spins)
--detecting if outer side is rotated - if it is,change that side
    as was written before
--rotate connected rows/columns in connected sides - just replace them
    (depends on rotating direction)
---need a way to somehow realise which sides are connected, also - names of sides
    (I think, just name them after their colors is enough - colors are constantly connected in Rubik Cubes)
-change current side - whole cube rotation
-constructor - create Cube from 6 Sides

random SOLVING cube generation
-8 parts with 3 colors
-6 centers - need to place them in pair by their color (only in odd sizes)
-(N-2)*8 parts with 2 colors - (N-2)*4 on opposite sides
-(N*N*6)-8*3-(N-2)*8*2 parts with 1 color (all the parts without 3-colored parts and without 2-colored parts,
    because I want to generate and to set them firstly) (DO NOT FORGET ABOUT CENTERS!!!!)
-3-colored - constant things, 2-colored - constant(constant border sides), 1-colored - amount/6 for each color
-generate 3-colored
--1st and last element of first and last Row on each side should be colored during this period
-generate 2-colored
--1st and last Row and 1st and last Columns on every side should be generated
-generate 1-colored
--center disposition for odd sizes
--filling all the uncolored spaces with the rest of colors
-returning Cube with random generation
 -OR-
-just rotate sides in already solved cube random amount of times in random directions, lmao

solving 3x3 Cube
-creating white cross
-creating white corners
-creating middle layer
-creating yellow cross
-creating yellow corners
-orientating yellow corner
-arrangement of yellow edges color

 */
public class Rubik {
    int dim;
    public Rubik (int dimensions) {
        dim = dimensions;
    }
    public class Row {
        String values;
        //generator
        public Row(String str) { values = str; } //TODO: str length check
        //------------------
        //rotation methods
        public Col rotateCW() { return new Col(values); }
        public Col rotateCCW() {
            values = new StringBuilder(values).reverse().toString();
            return new Col(values);
        }
        //------------------
        //access methods
        public char getChar(int num) { return values.charAt(num); } //TODO: num check
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
            return new Row(values);
        }
        public Row rotateCCW() {
            values = new StringBuilder(values).reverse().toString();
            return new Row(values);
        }
        //------------------
        //access methods
        public char getChar(int num) { return values.charAt(num); } //TODO: num check
    }

    public class Side {
        String values = "";
        StringBuilder sb = new StringBuilder();
        //generators
        public Side (Col[] cols) { //TODO: check amount and size of columns
            for (int i = 0; i < dim; i++) {
                for (int b = 0; b < dim; b++) { sb.append(cols[b].getChar(i)); }
            }
            values = sb.toString();
        }
        public Side (Row[] rows) { //TODO: check amount and size of rows
            for (int i = 0; i < dim; i++) {
                for (int b = 0; b < dim; b++) { sb.append(rows[i].getChar(b)); }
            }
            values = sb.toString();
        }
        public Side (String str) {
            values = str;
        }
        //------------------
        //side parts access methods
        public Row getRow (int rowNum) { //TODO: check rowNum
            StringBuilder sb = new StringBuilder();
            for (int i = rowNum * (dim - 1) - 1; i < rowNum * dim - 1; i++) {
                sb.append(values.charAt(i));
            }
            return new Row(sb.toString());
        }
        public Col getCol (int colNum) { //TODO: check colnum
            StringBuilder sb = new StringBuilder();
            for (int i = colNum - 1; i < dim * dim; i += dim) {
                sb.append(values.charAt(i));
            }
            return new Col(sb.toString());
        }
        //------------------
        //rotation methods
        public Side rotateCW () {
            Col[] cols = new Col[dim];
            for (int i = dim - 1; i >= 0; i--) {/*TODO: fix this crap, access modifying obj*/
                cols[i] = new Side(values).getRow(dim - i).rotateCW(); //terrible
            }
            return new Side(cols);
        }
        public Side rotateCCW () {
            Col[] cols = new Col[dim];
            for (int i = 0; i < dim; i++) {/*TODO: fix this crap, access modifying obj*/
                cols[i] = new Side(values).getRow(i).rotateCCW(); //terrible
            }
            return new Side(cols);
        }
        //------------------
        //side state access methods
        public void sideState () { //TODO: maybe, some array-returning realisation?
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

    }
}
