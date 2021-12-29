package ru.vsu.cs.Person.Сomponents;

public class Coord
{
    private int x;
    private int y;

    public Coord(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Coord{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public int getY() {
        return y;
    }
}
