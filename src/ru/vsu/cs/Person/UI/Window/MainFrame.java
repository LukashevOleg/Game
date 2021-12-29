package ru.vsu.cs.Person.UI.Window;

import ru.vsu.cs.Person.game.Game;
import ru.vsu.cs.Person.game.SGame;
import ru.vsu.cs.Person.Сomponents.Coord;
import ru.vsu.cs.Person.UI.FormUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainFrame extends JFrame implements FormUI
{
    MainPanel mainPanel = new MainPanel();

    public void setGame(Game game)
    {
        mainPanel.setGame(game);
    }


    @Override
    public void gameProcess() {

    }

    public MainFrame(Game game) throws HeadlessException, IOException {

        mainPanel.setBackground(Color.decode("#deffed"));
        mainPanel.setGame(game);
        this.add(mainPanel);
        this.setSize(1300,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
