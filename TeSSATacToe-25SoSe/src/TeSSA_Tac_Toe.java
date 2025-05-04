import java.awt.EventQueue;

import gfx.MainWindow;
import gfx.Ressources;
import logic.Board;
import logic.Player;

public class TeSSA_Tac_Toe {

    public static boolean DEBUG = false;

    public static void main(String[] args) {

        if (args.length == 1) {
            boolean b = (1 == Integer.valueOf(args[0])) ? true : false;
            MainWindow.setDebugg(b);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Player p1 = new Player("Player 1", Ressources.icon_x);
                    Player p2 = new Player("Player 2", Ressources.icon_o);
                    Board board = new Board(4, 5, 3, p1, p2);
                    MainWindow frame = new MainWindow(p1, p2, board);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
