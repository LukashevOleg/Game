package ru.vsu.cs.Person.UI.server;



import ru.vsu.cs.Person.UI.FormUI;
import ru.vsu.cs.Person.UI.Window.MainFrame;
import ru.vsu.cs.Person.game.Game;
import ru.vsu.cs.Person.Player.Bot;
import ru.vsu.cs.Person.Player.Player;
import ru.vsu.cs.Person.Step.BotStep;
import ru.vsu.cs.Person.Step.StepServer;
import ru.vsu.cs.Person.game.SGame;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.Socket;

public class GameSession implements Runnable {

    private final SGame game;
    MainFrame ui;

    public GameSession(Socket socket) throws IOException {
        Player p1 = new Player("Player", new StepServer(socket),  true);
        Player p2 = new Bot("Bot",new BotStep(), false);
        game = new SGame(p1, p2);
        game.setSocket(socket);
        try {
//            ui.setGame();
            ui = new MainFrame(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("GameSession(Socket socket)");
    }

    @Override
    public void run()
    {
        while (!game.checkWinner())
        {
//            game.printBoard();
//            game.makeMove();
        }
        System.out.println("dknvjd");

        System.out.println("Game Over!");
    }

}