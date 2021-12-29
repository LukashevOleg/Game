package ru.vsu.cs.Person.Inventory;


import ru.vsu.cs.Person.Сomponents.Coord;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Figure
{
    private Color color;
    private Coord coord;
    private boolean isMissis = false; // проверка на дамку
    private List<Coord> possibleNextStep= new ArrayList<>();

    public Coord getCoord() {
        return coord;
    }

    public List<Coord> getPossibleNextStep() {
        return possibleNextStep;
    }

    public void setPossibleNextStep(List<Coord> possibleNextStep) {
        this.possibleNextStep = possibleNextStep;
    }

    public boolean isMissis() {
        return isMissis;
    }

    public Color getColor() {
        return color;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public void setMissis(boolean missis) {
        isMissis = missis;
    }

    public Figure(Color color, Coord coord) {
        this.color = color;
        this.coord = coord;
    }

    public void paint(Graphics2D g, int x, int y, int size)
    {
        g.setColor(color);
        int a= x + (int) (size*0.22);
        int b =y + (int)(size*0.22);


        g.fillOval(a,b, (int)(size*0.65), (int)(size*0.65)  );
        if(isMissis)
        {
            g.setColor(Color.orange);
            g.drawString("Д", x+(int)(size*0.4), y+(int)(size*0.65));
        }

    }
}
