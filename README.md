Cube -> 6 sides. Every side consists of 3 rows OR 3 columns.
Layers spin realisation can be created using rows ans columns.

clockwise rotation - short to CW
counterclockwise - same way to CCW

rotating CW-> top row becomes right column, middle - to middle, so on

111                               1                                   2
222 -> rotating row 111 to column 1 , placing it to the right, 222 -> 2 , so on
333                               1                                   2

also if every side in, for example, 3x3 cube will have numbers from 0 to 8    0 1 2
                                                                            ( 3 4 5 ),
                                                                              6 7 8
we can transform 3d cube into his 2d version:

                 ______
                | 0 1 2 |
                | 3 4 5 |     
                | 3 4 5 |
  ______ _______ _______ ________
| 0 1 2 | 0 1 2 | 0 1 2 | 0 1 2 |
| 3 4 5 | 3 4 5 | 3 4 5 | 3 4 5 |  - also that shows how the side values are indexed
| 6 7 8 | 6 7 8 | 6 7 8 | 6 7 8 |
  ------ ------- ------- --------
                | 0 1 2 |
                | 3 4 5 |
                | 6 7 8 |
                 -------

connected sides rotation can also be realised with replacing rows

different sizes can be realised this way - just doing some amount of iterations

requires some Row/Column rotating methods(clockwise/counterclockwise)

colors are predicted and places on different sides: red-orange; white-yellow; blue-green
TODO:
class Row
-rotateRight/rotateLeft methods(transforming to Column) - done
-way to save data(triplets(-, no size variations), arrays(maybe)) - string - decided
-constructor - solve size problems(4x4x4 cube, how to apply only 4 elements arrays) - decided by using string

class Col (Column)
-rotateRight/rotateLeft methods(transforming to Row) - done
-way to save data(triplets(-, no size variations), arrays(maybe)) - string - decided
-constructor - solve size problems(4x4x4 cube, how to apply only 4 elements arrays) - decided by using string

class Side
-getting 1st/2nd/3rd Row/Column (or just an array of rows/columns) - done
-constructor - creating Side from 3 Rows or 3 Columns - done

class Cube
-side state method (just printing it, returning an array) - done
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

 
