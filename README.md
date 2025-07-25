# Rubik's Cube Simulator

This is my version of a Rubiks Cube Simulator. The sole purpose of this project is for me (pert) to fool around with
Java concepts.

## Breaking down the Rubiks Cube

In this project, when I'm talking about a Rubik's cube, I'm referring to a 3x3 cube. A cube have 6 faces of which you
can only view 1-3 at a time. A 3x3 cube is a cube where each of the 6 faces is divided into 3x3 surfaces.

The following pictures are of my standard (3x3) Rubik's.

![Blue, White, Orange](src/main/resources/Rubiks%20-%20Blue-White-Orange.png)
![Red, Blue, Yellow](src/main/resources/Rubiks%20-%20Red-Blue-Yellow.png)
![White, Red, Green](src/main/resources/Rubiks%20-%20White-Red-Green.png)

Imagine a solved Rubik's Cube with the white face in front, the orange face to the left, blue face up, red face right,
green face down, and yellow face in the back. For the purpose of my Rubik's Cube model, I'm going to consider this the
starting point of any Rubik's Cube.

### Center pieces

If you ever disassembled a Rubik's Cube, you'll know that there's a stem with 6 center pieces on it. These 6 center
pieces have a surface with exactly one color, and can't be moved in relation to each other. Sure, you can rotate the
stem around, but in the case of my Rubik's Cube, the white center piece will always be opposite the yellow center piece,
the orange always opposite the red, and the blue opposite the green. No matter how much you twist and turn the Rubik's
Cube, you cannot change the center pieces position in relation to each other.

![Center pieces](src/main/resources/Center%20pieces.png)

Hence, from my model's perspective, you cannot rotate a Rubik's Cube. You can look at it from different angles (like
moving a camera around the cube), but the white center piece is always front, the orange center piece always left, blue
center piece is up, red center piece is right, green center piece is down, and yellow center piece is back.

There are exactly 6 center pieces with of each their own color, corresponding to the faces of a cube.

### Positions (Coordinates)

For convenience, the Rubik's Cube is thought of as consisting of 3x3x3 smaller cubes (but there's actually no smaller
cube in the absolute center of the Rubik's Cube). The absolute center of the Rubik's cube is considered to be in (0,0,0)
in a three-dimensional coordinate system. The white center pieces is then positioned in (0,0,-1). Opposite the white
center pieces is the yellow center piece in (0,0,1). The orange center piece is in (-1,0,0) and opposite is the red
center piece in (1,0,0). The blue center piece is in (0,1,0) and opposite is the green center piece in (0,-1,0). Notice
for all center pieces, two of three coordinates are 0.

1. White: (0,0,-1)
2. Yellow: (0,0,1)
3. Orange: (-1,0,0)
4. Red: (1,0,0)
5. Green: (0,-1,0)
6. Blue: (0,1,0)

### Edge pieces

Next to the center pieces are edge pieces. An edge piece have exactly two surfaces with different colors. Each color
corresponding to a center piece. To the left of the white center piece toward the orange center piece is an edge piece
with the colors white and orange. To the right of the white center piece towards the red center piece is an edge piece
with the colors white and red, and so on, and so forth.

It follows that for a solved Rubik's Cube there must be exactly one edge piece, with the colors orange and white, and
that edge piece must be in a certain position, likewise there's exactly one edge piece, with the colors white and red,
and that edge pieces must be in a certain position too, each and every edge piece has exactly two colors, and one
correct position. If any edge piece is not in its correct position, the cube cannot be solved.

Notice that for all edge pieces one, and only one of two coordinates are 0.

01. Orange-White: (-1,0,-1)
02. Blue-White: (0,1,-1)
03. Red-White: (1,0,-1)
04. Green-White: (0,-1,-1)
05. Orange-Blue: (-1,1,0)
06. Blue-Red: (1,1,0)
07. Orange-Green: (-1,-1,0)
08. Red-Green: (1,-1,0)
09. Blue-Yellow: (0,1,1)
10. Orange-Yellow: (-1,0,1)
11. Red-Yellow: (1,0,1)
12. Green-Yellow: (0,-1,1)

### Corner pieces

The last type of piece is a corner piece. Corner pieces are next to edge pieces, and each corner piece have exactly
three surfaces, with three different colors, each color corresponding to the color of a center piece. The corner piece
above the orange-white edge piece, have the colors orange, white, and blue. The corner pieces below the orange-white
edge piece have the colors orange, white and green. Just as with edge piece, there's exactly one corner piece for each
corner, and each corner pieces must be in a certain position for a Rubik's Cube to be considered solved.

Notice that for all corner pieces no coordinates are 0.

1. Orange-Blue-White: (-1,1,-1)
2. Red-Blue-White: (1,1,-1)
3. Orange-Green-White: (-1,-1,-1)
4. Red-Green-White: (1,-1,-1)
5. Orange-Blue-Yellow: (-1,1,1)
6. Red-Blue-Yellow: (1,1,1)
7. Orange-Green-Yellow: (-1,-1,1)
8. Red-Green-Yellow: (1,-1,1)

### Number of Pieces

So there are exactly

- 6 center pieces, each with one surface,
- 12 edge pieces, each with two surfaces and
- 8 corner pieces, each with three surfaces

Giving a total of 26 pieces and 54 surfaces.

### Serializing the Rubik's Cube
I want to represent as many variations of a Rubik's Cube as possible, in as little space as possible.
There are only 6 colors, which means each color can be represented by 3 bits.
The colors are defined as:

- White 0 (000b) 
- Yellow 1 (001b) 
- Orange 2 (010b) 
- Red 3 (011b) 
- Green 4 (100b)
- Blue 5 (101b) 


There's no piece in the absolute center of the Rubik's Cube, and center pieces are fixed, so these will not be represented in the serialized model.
Since there af 54 surfaces, but 6 of them are center pieces, we are left with 48 surfaces to represent.
48 * 3 bits = 144 bits in total.

Each corner piece is represented by 9 bits, and each edge piece is represented by 6 bits.
The surfaces are considered in the following order: left (-x), right (+x), down (-y), up (+y), front (-z), back (+z).
The serialization is then done in layers from top to bottom.
With this in mind, the serialization of a Rubik's Cube is as follows:

#### Top Layer
The top layer contains the blue center piece at fixed position

| Bit position | Description      | Facing | Coordinate  | Initial color |
|:------------:|:-----------------|:------:|:-----------:|:--------------|
|   000-002    | Left,Top,Back    |  Left  | (-1, 1, 1)  | Orange (010b) |
|   003-005    | Left,Top,Back    |   Up   | (-1, 1, 1)  | Blue (101b)   |
|   006-008    | Left,Top,Back    |  Back  | (-1, 1, 1)  | Yellow (001b) |
|   009-011    | Middle,Top,Back  |   Up   | ( 0, 1, 1)  | Blue (101b)   |
|   012-014    | Middle,Top,Back  |  Back  | ( 0, 1, 1)  | Yellow (001b) |
|   015-017    | Right,Top,Back   | Right  | ( 1, 1, 1)  | Red (011b)    |
|   018-020    | Right,Top,Back   |   Up   | ( 1, 1, 1)  | Blue (101b)   |
|   021-023    | Right,Top,Back   |  Back  | ( 1, 1, 1)  | Yellow (001b) |
|   024-026    | Left,Top,Middle  |  Left  | (-1, 1, 0)  | Orange (010b) |
|   027-029    | Left,Top,Middle  |   Up   | (-1, 1, 0)  | Blue (101b)   | 
|   030-032    | Right,Top,Middle | Right  | ( 1, 1, 0)  | Red (011b)    |
|   033-035    | Right,Top,Middle |   Up   | ( 1, 1, 0)  | Blue (101b)   |
|   036-038    | Left,Top,Front   |  Left  | (-1, 1, -1) | Orange (010b) |
|   039-041    | Left,Top,Front   |   Up   | (-1, 1, -1) | Blue (101b)   |
|   042-044    | Left,Top,Front   | Front  | (-1, 1, -1) | White (000b)  |
|   045-047    | Middle,Top,Front |   Up   | ( 0, 1, -1) | Blue (101b)   |
|   048-050    | Middle,Top,Front | Front  | ( 0, 1, -1) | White (000b)  |
|   051-053    | Right,Top,Front  | Right  | ( 1, 1, -1) | Red (011b)    |
|   054-056    | Right,Top,Front  |   Up   | ( 1, 1, -1) | Blue (101b)   |
|   057-059    | Right,Top,Front  | Front  | ( 1, 1, -1) | White (000b)  |

The initial bitstring for the top layer is:
```010101001101001011101001010101011101010101000101000011101000```

#### Middle Layer
The middle layer contains orange, red, white and yellow center pieces at fixed positions

| Bit position | Description        | Facing | Coordinate  | Initial color |
|:------------:|:-------------------|:------:|:-----------:|:--------------|
|   060-062    | Left,Middle,Back   |  Left  | (-1, 0, 1)  | Orange (010b) |
|   063-065    | Left,Middle,Back   |  Back  | (-1, 0, 1)  | Yellow (001b) |
|   066-068    | Right,Middle,Back  | Right  | ( 1, 0, 1)  | Red (011b)    |
|   069-071    | Right,Middle,Back  |  Back  | ( 1, 0, 1)  | Yellow (001b) |
|   072-074    | Left,Middle,Front  |  Left  | (-1, 0, -1) | Orange (010b) |
|   075-077    | Left,Middle,Front  | Front  | (-1, 0, -1) | White (000b)  |
|   078-080    | Right,Middle,Front | Right  | ( 1, 0, -1) | Red (011b)    |
|   081-083    | Right,Middle,Front | Front  | ( 1, 0, -1) | White (000b)  |

The initial bitstring for the middle layer is:
```010001011001010000011000```

#### The Bottom Layer
The bottom layer contains the green center piece at a fixed position

| Bit position | Description        | Facing |  Coordinate  | Initial color    |
|:------------:|:-------------------|:------:|:------------:|:-----------------|
|   084-086    | Left,Bottom,Back   |  Left  | (-1, -1, 1)  | Orange (010b)    |
|   087-089    | Left,Bottom,Back   |  Down  | (-1, -1, 1)  | Green (100b)     |
|   090-092    | Left,Bottom,Back   |  Back  | (-1, -1, 1)  | Yellow (001b)    |
|   093-095    | Middle,Bottom,Back |  Down  | ( 0, -1, 1)  | Green (100b)     |
|   096-098    | Middle,Bottom,Back |  Back  | ( 0, -1, 1)  | Yellow (001b)    |
|   099-101    | Right,Bottom,Back  | Right  | ( 1, -1, 1)  | Red (011b)       |
|   102-104    | Right,Bottom,Back  |  Down  | ( 1, -1, 1)  | Green (100b)     |
|   105-107    | Right,Bottom,Back  |  Back  | ( 1, -1, 1)  | Yellow (001b)    |
|   108-110    | Left,Bottom,Middle |  Left  | (-1, -1, 0)  | Orange (010b)    |
|   111-113    | Left,Bottom,Middle |  Down  | (-1, -1, 0)  | Green (100b)     |
|   114-116    | Right,Bottom,Middle| Right  | ( 1, -1, 0)  | Red (011b)       |
|   117-119    | Right,Bottom,Middle|  Down  | ( 1, -1, 0)  | Green (100b)     |
|   120-122    | Left,Bottom,Front  |  Left  | (-1, -1, -1) | Orange (010b)    |
|   123-125    | Left,Bottom,Front  |  Down  | (-1, -1, -1) | Green (100b)     |
|   126-128    | Left,Bottom,Front  | Front  | (-1, -1, -1) | White (000b)     |
|   129-131    | Middle,Bottom,Front|  Down  | ( 0, -1, -1) | Green (100b)     |
|   132-134    | Middle,Bottom,Front| Front  | ( 0, -1, -1) | White (000b)     |
|   135-137    | Right,Bottom,Front | Right  | ( 1, -1, -1) | Red (011b)       |
|   138-140    | Right,Bottom,Front |  Down  | ( 1, -1, -1) | Green (100b)     |
|   141-143    | Right,Bottom,Front | Front  | ( 1, -1, -1) | White (000b)     |

The initial bitstring for the bottom layer is:
```010100001100001011100001010100011100010100000100000011100000```


The resulting initial bitstring for the entire Rubik's Cube is:

```010101001101001011101001010101011101010101000101000011101000010001011001010000011000010100001100001011100001010100011100010100000100000011100000```

144 bits can fit in 18 bytes.

|Bitstring       | Hexadecimal | Decimal |
|----------------|-------------|---------|
|```01010100``` | 54 | 84      |
|```11010010``` | D2 | 210     |
|```11101001``` | E9 | 233     |
|```01010101``` | 55 | 85      |
|```11010101``` | D5 | 213     |
|```01000101``` | 45 | 69      |
|```00001110``` | 0E | 14      |
|```10000100``` | 84 | 132     |
|```01011001``` | 59 | 89      |
|```01000001``` | 41 | 65      |
|```10000101``` | 85 | 133     |
|```00001100``` | 0C | 12      |
|```00101110``` | D6 | 214     |
|```00010101``` | 15 | 21      |
|```00011100``` | 1C | 28      |
|```01010000``` | 50 | 80      |
|```01000000``` | 40 | 64      |
|```11100000``` | E0 | 224     |


Hexadecimal representation of the initial Rubik's Cube bitstring is:
```54 D2 E9 55 D5 45 0E 84 59 41 85 0C D6 15 1C 50 40 E0```







   







 

