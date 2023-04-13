package dk.pert.rubiks.rubiksmodel;

import dk.pert.rubiks.rubiksmodel.enums.Axis;
import dk.pert.rubiks.rubiksmodel.enums.Direction;
import dk.pert.rubiks.rubiksmodel.enums.Move;
import dk.pert.rubiks.rubiksmodel.enums.Turn;

public interface IPiece {
    ISurface getSurface(Direction direction);

    boolean hasSurface(Direction direction);

    void moveSurface(Direction before, Direction after);

    void moveLeft();

    void moveLeftInverted();

    void moveRight();

    void moveRightInverted();

    void moveUp();

    void moveUpInverted();

    void moveDown();

    void moveDownInverted();

    void moveFront();

    void moveFrontInverted();

    void moveBack();

    void moveBackInverted();

    void turnClockwiseAroundXAxis();

    void turnCounterclockwiseAroundXAxis();

    void turnClockwiseAroundYAxis();

    void turnCounterclockwiseAroundYAxis();

    void turnClockwiseAroundZAxis();

    void turnCounterclockwiseAroundZAxis();

    void move(Move move);

    void turnClockwiseAround(Axis axis);

    void turnCounterClockwiseAround(Axis axis);

    void turn(Turn turn, Axis axis);
}
