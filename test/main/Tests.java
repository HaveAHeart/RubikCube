package main;

import org.junit.Test;

import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Tests {
    @Test
    public void rowRotate() {
        Color[] colorsException = { Color.WHITE, Color.WHITE, Color.WHITE };
        assertThrows(InputMismatchException.class,
                () -> new Row(colorsException, 2));
        assertThrows(InputMismatchException.class,
                () -> new Row(colorsException, -2));
        assertThrows(InputMismatchException.class,
                () -> new Row(colorsException, -2).getValue(-1));

        Color[] colorsCW = {Color.WHITE, Color.BLUE, Color.ORANGE};
        Row testRow = new Row(colorsCW, 3);
        Col testCol = new Col(colorsCW, 3);
        assertEquals(testCol, testRow.rotateCW());

        Color[] colorsCCWCol = {Color.ORANGE, Color.BLUE, Color.WHITE};
        Row testRowCCW = new Row(colorsCW, 3);
        Col testColCCW = new Col(colorsCCWCol, 3);
        assertEquals(testColCCW, testRowCCW.rotateCCW());

        Color[] colorsCW2 = {Color.WHITE, Color.BLUE, Color.ORANGE, Color.GREEN};
        Row testRow2 = new Row(colorsCW2, 4);
        Col testCol2 = new Col(colorsCW2, 4);
        assertEquals(testCol2, testRow2.rotateCW());

        Color[] colorsCCWCol2 = {Color.GREEN, Color.ORANGE, Color.BLUE, Color.WHITE};
        Row testRowCCW2 = new Row(colorsCW2, 4);
        Col testColCCW2 = new Col(colorsCCWCol2, 4);
        assertEquals(testColCCW2, testRowCCW2.rotateCCW());
    }

    @Test
    public void colRotate() {
        Color[] colorsException = { Color.WHITE, Color.WHITE, Color.WHITE };
        assertThrows(InputMismatchException.class,
                () -> new Col(colorsException, 2));
        assertThrows(InputMismatchException.class,
                () -> new Col(colorsException, -2));
        assertThrows(InputMismatchException.class,
                () -> new Col(colorsException, -2).getValue(-1));

        Color[] colorsCW = {Color.WHITE, Color.BLUE, Color.YELLOW, Color.GREEN};
        Col testColCW = new Col(colorsCW, 4);
        Color[] colorsCWRow = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Row testRowCW = new Row(colorsCWRow, 4);
        assertEquals(testRowCW, testColCW.rotateCW());

        Color[] colorsCCW = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Col testColCCW = new Col(colorsCCW, 4);
        Color[] colorsCCWRow = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Row testRowCCW = new Row(colorsCCWRow, 4);
        assertEquals(testRowCCW, testColCCW.rotateCCW());

        Color[] colorsCol = {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE};
        Col testCol = new Col(colorsCol, 4);

        Color[] colorsInvertedCol = { Color.WHITE, Color.BLUE, Color.YELLOW, Color.GREEN };
        Col invertedCol = new Col(colorsInvertedCol, 4);
        assertEquals(testCol.invert(), invertedCol);
    }

    @Test
    public void sideRotate() {
        Color[][] colorsOrig = {
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
                {Color.GREEN, Color.YELLOW, Color.BLUE, Color.WHITE},
        };
        Side origSide = new Side(colorsOrig, 4);

        Color[][] colorsRotatedCW = {
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
                {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW},
                {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
        };
        Side rotatedSideCW = new Side(colorsRotatedCW, 4);
        assertEquals(rotatedSideCW.toString(), origSide.rotateCW().toString());

        Color[][] colorsRotatedCCW = {
                {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE},
                {Color.BLUE, Color.BLUE, Color.BLUE, Color.BLUE},
                {Color.YELLOW, Color.YELLOW, Color.YELLOW, Color.YELLOW},
                {Color.GREEN, Color.GREEN, Color.GREEN, Color.GREEN},
        };
        Side rotatedSideCCW = new Side(colorsRotatedCCW, 4);
        assertEquals(rotatedSideCCW.toString(), origSide.rotateCCW().toString());

        Color[] testRowColor = { Color.WHITE, Color.BLUE };
        Row[] sideGenTest = {
                new Row( testRowColor, 2),
                new Row( testRowColor, 2),
        };
        Color[][] sideGenColors = {
                { Color.WHITE, Color.BLUE },
                { Color.WHITE, Color.BLUE },
        };
        assertEquals(new Side(sideGenColors, 2).toString(), new Side(sideGenTest, 2).toString());
    }

    @Test
    public void cubeRotate() {
        Side topSide = new Side(Color.GREEN, 4);
        Side frontSide = new Side(Color.WHITE, 4);
        Side rightSide = new Side(Color.ORANGE, 4);
        Side backSide = new Side(Color.YELLOW, 4);
        Side leftSide = new Side(Color.RED, 4);
        Side bottomSide = new Side(Color.BLUE, 4);
        Side[] testSides = { topSide, frontSide, rightSide, backSide, leftSide, bottomSide };
        Cube testCube = new Cube(4);
        assertEquals(new Cube(testSides, 4).toString(), testCube.toString());
        System.out.println(testCube.toString());

        testCube.rotateRowCW("top", 0, 0);
        testCube.rotateRowCCW("bottom", 0, 0);
        testCube.rotateRowCW("left", 0, 0);
        testCube.rotateRowCCW("right", 0, 0);

        testCube.rotateColUp("back", 0, 0);
        testCube.rotateColUp("front", 3, 3);
        testCube.rotateColUp("left", 2, 2);
        testCube.rotateColUp("right", 1, 1);
        testCube.rotateColUp("top", 0, 2);
        testCube.rotateColDown("bottom", 0, 2);
        System.out.println(testCube.toString());
        testCube.randomizeState(1000);
        System.out.println(testCube.toString());
    }
}
