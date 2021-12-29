package ru.vsu.cs.Person.UI.Window;

import ru.vsu.cs.Person.Step.BotStep;
import ru.vsu.cs.Person.game.SGame;
import ru.vsu.cs.Person.Сomponents.Coord;
import ru.vsu.cs.Person.game.Game;
import ru.vsu.cs.Person.Player.Bot;
import ru.vsu.cs.Person.Player.Player;

import javax.swing.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainPanel extends JPanel
{
    JButton buttonForBot = new JButton("Ход бота");
    private Game game;
    Player player1 = new Bot("Oleg", new BotStep(), true);
    Player player2 = new Bot("Max" , new BotStep(), false);
    Player currPlayer;

    private final int xCoordDask=300;
    private final int yCoordDask=150;
    private final int size=500;
    Drawer drawer;
    Timer timer;


    public MainPanel() throws IOException {
        this.setLayout(null);
        buttonForBot.setBounds(950,700,120,35);
        this.add(buttonForBot);
        timer = new Timer(300, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

//                System.out.println(timeValue);
                currPlayer = game.getCurrentPlayer();
//                System.out.println(currPlayer!= null );
//                System.out.println(" currPlayer.isBot():" + currPlayer.isBot());
                if(currPlayer!= null && currPlayer.isBot()) {
                    System.out.println("timer working good");
                    game.makeMove();
                }
                repaint();

            }
        });
//        this.game = game;
        newGame();



        buttonForBot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                game.makeMove();
                repaint();

                try {
                    if (game.checkWinner()) {
                        JOptionPane.showMessageDialog(null, game.getUnCurrentPlayer().getName() + " are winner!!!");
                        repaint();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currPlayer = game.getCurrentPlayer();
                int x= (e.getX()-xCoordDask)/(size/8);
                int y = (e.getY()-yCoordDask)/(size/8);
                Coord coord = new Coord(x, y);
//                drawer.drawActive(coord);
//                repaint();
                System.out.println("координата");
                if(!currPlayer.isBot())
                    if(currPlayer.getActiveFigureForStep() == null)
                    {
                        System.out.println("взял фигуру");
                        takeFigure(coord);
                    }
                    else if(currPlayer.getActiveFigureForStep() != null){
    //                    doStep(coord);
                        try {
                            game.makeMove(currPlayer.getActiveFigureForStep(), coord);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                repaint();

                try {
                    if (game.checkWinner()) {
                        JOptionPane.showMessageDialog(e.getComponent(), game.getUnCurrentPlayer().getName() + " are winner!!!");
                        repaint();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }

    public void takeFigure(Coord coord)
    {
        game.createForFigureStep(game.getCurrentPlayer());
        currPlayer = game.getCurrentPlayer();
        int whichFigure= currPlayer.whichFigure(coord);
        if(whichFigure != -1) {
             currPlayer.setActiveFigureForStep(currPlayer.getListFigure().get(whichFigure));
        }
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void newGame() throws IOException {
        game = new SGame(player1, player2);
        game.createForFigureStep(game.getCurrentPlayer());
//        timer = new Timer(5, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent ae) {
//
////                System.out.println(timeValue);
//                System.out.println(currPlayer!= null );
//                System.out.println(" currPlayer.isBot():" + currPlayer.isBot());
//                if(currPlayer!= null && currPlayer.isBot()) {
//                    System.out.println("timer working good");
//                    game.makeMove();
//                }
//                repaint();
//
//            }
//        });
        timer.start();

        game.changeCurrPlayer();
        game.createForFigureStep(game.getCurrentPlayer());
        game.changeCurrPlayer();

    }

    @Override
    public void paint(Graphics graphics)
    {

        super.paint(graphics);
        Graphics2D g = (Graphics2D) graphics;

        drawer = new Drawer(xCoordDask, yCoordDask, size, g);
        drawer.drawBack();
        drawer.drawFigure(game.getCurrentPlayer());
        drawer.drawFigure(game.getUnCurrentPlayer());
    }



}
