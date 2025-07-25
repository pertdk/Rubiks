package dk.pert.rubiks.model;

import javax.swing.tree.FixedHeightLayoutCache;

import dk.pert.rubiks.model.enums.Color;
import dk.pert.rubiks.model.enums.Direction;
import dk.pert.rubiks.model.interfaces.IPiece;
import dk.pert.rubiks.model.pieces.CenterPiece;
import dk.pert.rubiks.model.pieces.CornerPiece;
import dk.pert.rubiks.model.pieces.EdgePiece;

/**
 * Place description here.
 *
 * @author PET@nykredit.dk
 */

public class Cube {
    private static final int CENTER = 1;
    private static final int LEFT = 0;
    private static final int RIGHT = 2;
    private static final int TOP = 2;
    private static final int BOTTOM = 0;
    private static final int FRONT = 0;
    private static final int BACK = 2;

    private final IPiece[][][] pieces;

    public Cube() {
        pieces = new IPiece[3][3][3];
        createCenterPieces();
        createEdgePieces();
        createCornerPieces();
    }

    private void createCenterPieces() {
        /* Notice all centerpieces have exactly two CENTER coordinates */
        pieces[CENTER][CENTER][FRONT] = new CenterPiece(new Surface(Color.GREEN, Direction.FRONT));
        pieces[LEFT][CENTER][CENTER] = new CenterPiece(new Surface(Color.ORANGE, Direction.LEFT));
        pieces[CENTER][CENTER][BACK] = new CenterPiece(new Surface(Color.BLUE, Direction.BACK));
        pieces[RIGHT][CENTER][CENTER] = new CenterPiece(new Surface(Color.RED, Direction.RIGHT));
        pieces[CENTER][TOP][CENTER] = new CenterPiece(new Surface(Color.WHITE, Direction.UP));
        pieces[CENTER][BOTTOM][CENTER] = new CenterPiece(new Surface(Color.YELLOW, Direction.DOWN));
    }

    private void createEdgePieces() {
        /* Notice all edge pieces have exactly one CENTER coordinate */
        pieces[LEFT][TOP][CENTER] = new EdgePiece(
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.ORANGE, Direction.LEFT)
        );
        pieces[CENTER][TOP][BACK] = new EdgePiece(
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][TOP][CENTER] = new EdgePiece(
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.RED, Direction.RIGHT)
        );
        pieces[CENTER][TOP][FRONT] = new EdgePiece(
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.GREEN, Direction.FRONT)
        );

        pieces[LEFT][CENTER][FRONT] = new EdgePiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.GREEN, Direction.FRONT)
        );
        pieces[LEFT][CENTER][BACK] = new EdgePiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][CENTER][BACK] = new EdgePiece(
                new Surface(Color.RED, Direction.RIGHT),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][CENTER][FRONT] = new EdgePiece(
                new Surface(Color.RED, Direction.RIGHT),
                new Surface(Color.GREEN, Direction.FRONT)
        );

        pieces[LEFT][BOTTOM][CENTER] = new EdgePiece(
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.ORANGE, Direction.LEFT)
        );
        pieces[CENTER][BOTTOM][BACK] = new EdgePiece(
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][BOTTOM][CENTER] = new EdgePiece(
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.RED, Direction.RIGHT)
        );
        pieces[CENTER][BOTTOM][FRONT] = new EdgePiece(
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.GREEN, Direction.FRONT)
        );

    }

    private void createCornerPieces() {
        /* Notice corner pieces does not have any CENTER coordinate */
        pieces[LEFT][TOP][BACK] = new CornerPiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[LEFT][TOP][FRONT] = new CornerPiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.GREEN, Direction.FRONT)
        );
        pieces[LEFT][BOTTOM][BACK] = new CornerPiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[LEFT][BOTTOM][FRONT] = new CornerPiece(
                new Surface(Color.ORANGE, Direction.LEFT),
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.GREEN, Direction.FRONT)
        );
        pieces[RIGHT][TOP][BACK] = new CornerPiece(
                new Surface(Color.RED, Direction.LEFT),
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][TOP][FRONT] = new CornerPiece(
                new Surface(Color.RED, Direction.LEFT),
                new Surface(Color.WHITE, Direction.UP),
                new Surface(Color.GREEN, Direction.FRONT)
        );
        pieces[RIGHT][BOTTOM][BACK] = new CornerPiece(
                new Surface(Color.RED, Direction.LEFT),
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.BLUE, Direction.BACK)
        );
        pieces[RIGHT][BOTTOM][FRONT] = new CornerPiece(
                new Surface(Color.RED, Direction.LEFT),
                new Surface(Color.YELLOW, Direction.DOWN),
                new Surface(Color.GREEN, Direction.FRONT)
        );
    }

    public IPiece getPiece(int x, int y, int z) {
        return pieces[x][y][z];
    }

    public void moveLeft() {
        IPiece[][] leftPieces = pieces[LEFT];
        for (IPiece[] llPieces : leftPieces) {
            for (IPiece lPiece : llPieces) {
                lPiece.moveLeft();
            }
        }
    }

    private static String encodeColor(Color color) {
        return switch (color) {
            case WHITE -> "000";
            case YELLOW -> "001";
            case ORANGE -> "010";
            case RED -> "011";
            case GREEN -> "100";
            case BLUE -> "101";
        };
    }
    private static Color decodeColor(String color) {
        return switch (color) {
            case "000" -> Color.WHITE;
            case "001" -> Color.YELLOW;
            case "010" -> Color.ORANGE;
            case "011" -> Color.RED;
            case "100" -> Color.GREEN;
            case "101" -> Color.BLUE;
            default -> throw new IllegalArgumentException("Unknown color representation: " + color);
        };
    }

    private String encodeTopLayer() {
        return encodeColor(pieces[LEFT][TOP][BACK].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][TOP][BACK].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[LEFT][TOP][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[CENTER][TOP][BACK].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[CENTER][TOP][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[RIGHT][TOP][BACK].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][TOP][BACK].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[RIGHT][TOP][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[LEFT][TOP][CENTER].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][TOP][CENTER].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[RIGHT][TOP][CENTER].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][TOP][CENTER].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[LEFT][TOP][FRONT].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][TOP][FRONT].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[LEFT][TOP][FRONT].getSurface(Direction.FRONT).getColor()) +
            encodeColor(pieces[CENTER][TOP][FRONT].getSurface(Direction.FRONT).getColor()) +
            encodeColor(pieces[CENTER][TOP][FRONT].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[RIGHT][TOP][FRONT].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][TOP][FRONT].getSurface(Direction.UP).getColor()) +
            encodeColor(pieces[RIGHT][TOP][FRONT].getSurface(Direction.FRONT).getColor());
    }

    private String encodeCenterLayer() {
        return encodeColor(pieces[LEFT][CENTER][BACK].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][CENTER][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[RIGHT][CENTER][BACK].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][CENTER][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[LEFT][CENTER][FRONT].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][CENTER][FRONT].getSurface(Direction.FRONT).getColor()) +
            encodeColor(pieces[RIGHT][CENTER][FRONT].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][CENTER][FRONT].getSurface(Direction.FRONT).getColor());
    }

    private String encodeBottomLayer() {
        return encodeColor(pieces[LEFT][BOTTOM][BACK].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][BACK].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[CENTER][BOTTOM][BACK].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[CENTER][BOTTOM][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][BACK].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][BACK].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][BACK].getSurface(Direction.BACK).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][CENTER].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][CENTER].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][CENTER].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][CENTER].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][FRONT].getSurface(Direction.LEFT).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][FRONT].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[LEFT][BOTTOM][FRONT].getSurface(Direction.FRONT).getColor()) +
            encodeColor(pieces[CENTER][BOTTOM][FRONT].getSurface(Direction.FRONT).getColor()) +
            encodeColor(pieces[CENTER][BOTTOM][FRONT].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][FRONT].getSurface(Direction.RIGHT).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][FRONT].getSurface(Direction.DOWN).getColor()) +
            encodeColor(pieces[RIGHT][BOTTOM][FRONT].getSurface(Direction.FRONT).getColor());
    }

    private String encodeCube() {
        return encodeTopLayer() + encodeCenterLayer() + encodeBottomLayer();
    }

    private byte[] toByteArray(String dataStr) {
        int numOfBytes = dataStr.length() / 8;
        byte[] bytes = new byte[numOfBytes];
        for (int byteNo = 0; byteNo < numOfBytes; byteNo++) {
            String byteStr = dataStr.substring(byteNo * 8, (byteNo  * 8) + 7);
            bytes[byteNo] = (byte) Integer.parseUnsignedInt(byteStr, 2);
        }
        return bytes;
    }
    private String toString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String byteStr = Integer.toUnsignedString(aByte, 2);
            while (byteStr.length() < 8) {
                byteStr = "0" + byteStr; // Pad with leading zeros
            }
            sb.append(byteStr);
        }
        return sb.toString();
    }
}
