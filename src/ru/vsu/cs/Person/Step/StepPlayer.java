package ru.vsu.cs.Person.Step;

import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Сomponents.Cell;
import ru.vsu.cs.Person.Сomponents.Coord;

import java.util.List;
import java.util.Scanner;

public class StepPlayer implements Step
{
    Scanner scanner = new Scanner(System.in);


    @Override
    public Figure changeFigure(Cell[][] cells, List<Figure> listFigure) {
        return listFigure.get(chanceFigure(listFigure));
    }

    @Override
    public Coord changeCoord(Cell[][] cells, Figure figure) {
        System.out.println("Введи, куда хочешь походить");
        System.out.print("x: ");
        int x = scanner.nextInt();
        System.out.print("y: ");
        int y = scanner.nextInt();
        return new Coord(x, y);
    }

//    private Coord changeCoord(Figure figure)
//    {
//        System.out.println("Введи, куда хочешь походить");
//        System.out.print("x: ");
//        int x = scanner.nextInt();
//        System.out.print("y: ");
//        int y = scanner.nextInt();
//        return new Coord(x, y);
////        Coord coord = new Coord(x, y);
////        if(game.checkStep(coord, figure))
////            return coord;
////        else return changeCoord(figure);
//    }

    private int chanceFigure(List<Figure> listFigure)
    {
        Coord coord;

        System.out.print("Введи x шашки ");
        int x=scanner.nextInt();
        System.out.print("Введи y шашки ");
        int y= scanner.nextInt();
        coord = new Coord(x, y);

        int whichFigure = whichFigure(coord, listFigure);
        if(whichFigure == -1) {
            System.out.println("Попробуй еще раз!");
            whichFigure = chanceFigure(listFigure);
        }
        return whichFigure;
    }

    private int whichFigure(Coord coord, List<Figure> listFigure)
    {
        int i=0;
        for (Figure figure : listFigure) {
            if (figure.getCoord().getX() == coord.getX() &&
                    figure.getCoord().getY() == coord.getY()) {
                return i;
            } else i++;
        }
        return -1;
    }
}
