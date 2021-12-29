package ru.vsu.cs.Person.UI;

import ru.vsu.cs.Person.Step.BotStep;
import ru.vsu.cs.Person.Step.StepPlayer;
import ru.vsu.cs.Person.Сomponents.Coord;
import ru.vsu.cs.Person.Inventory.Figure;
import ru.vsu.cs.Person.game.Game;
import ru.vsu.cs.Person.Player.Bot;
import ru.vsu.cs.Person.Player.Player;

import java.io.IOException;
import java.util.Scanner;

public class Console implements FormUI
{
    private Game game ;
    Scanner scanner = new Scanner(System.in);


    public Console()
    {
        System.out.println("Введите имя игрока белыми:");
        String name1 = scanner.nextLine();
        System.out.println("Введите имя игрока черными:");
        String name2 = scanner.nextLine();
        game = new Game(new Player(name1, new StepPlayer(),true), new Bot(name2,new BotStep(), false));
        game.printBoard();
        try {
            gameProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void gameProcess() throws IOException {
        do {
            game.makeMove();
            game.printBoard();
        } while (!game.checkWinner());
        System.out.println(game.getUnCurrentPlayer().getName() + ", ты победил!!!");
    }


    private Coord changeCoord( Figure figure)
    {
        System.out.println("Введи, куда хочешь походить");
        System.out.print("x: ");
        int x = scanner.nextInt();
        System.out.print("y: ");
        int y = scanner.nextInt();
        Coord coord = new Coord(x, y);
        if(game.checkStep(coord, figure))
            return coord;
        else return changeCoord(figure);
    }


    private int chanceFigure(Player current)
    {
        Coord coord;

        System.out.print("Введи x шашки ");
        int x=scanner.nextInt();
        System.out.print("Введи y шашки ");
        int y= scanner.nextInt();
        coord = new Coord(x, y);

        int whichFigure = current.whichFigure(coord);
        if(whichFigure == -1) {
            System.out.println("Попробуй еще раз!");
            whichFigure = chanceFigure(current);
        }
        return whichFigure;
    }




}
