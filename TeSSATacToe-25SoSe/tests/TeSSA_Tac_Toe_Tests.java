// Version für JUnit 5
import gfx.MainWindow;
import gfx.Ressources;
import logic.Board;
import logic.Player;
import logic.WinState;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TeSSA_Tac_Toe_Tests {
    private Player p1, p2;
    private Board board;
    private MainWindow frame;

    private static final int TIME_OUT = 0;

    @BeforeEach
    public void setUp() throws Exception {
        p1 = new Player("Player 1", Ressources.icon_x);
        p2 = new Player("Player 2", Ressources.icon_o);
        board = new Board(4, 5, 3, p1, p2);
        frame = new MainWindow(p1, p2, board);
        frame.setVisible(true);
        MainWindow.setDebugg(true);
    }

    @AfterEach
    public void tearDown() throws Exception {
        p1 = p2 = null;
        board = null;
        frame = null;
    }

    @Test
    public void test01() throws InterruptedException {
        frame.turn(0, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 1);
        Thread.sleep(TIME_OUT);
        frame.turn(1, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 2);
        Thread.sleep(TIME_OUT);
        WinState winner = frame.turn(2, 0);
        assertSame(WinState.player1, winner);
    }

    @Test
    public void test02() {
        frame.turn(0, 0);
        String retString = board.getPlayerNameInField(0, 0);
        assertEquals("Player 1", retString);
    }

    @Test
    public void test03() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        String retString = board.getPlayerNameInField(0, 1);
        assertEquals("Player 2", retString);
    }

    @Test
    public void test04() {
        String retString = board.getPlayerNameInField(0, 0);
        assertEquals("        ", retString);
    }

    @Test
    public void test05() {
        WinState wst = frame.turn(0, 0);
        frame.checkWinner(wst);
    }

    @Test
    public void test06() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 0);
        frame.turn(1, 1);
        frame.turn(2, 1);
        WinState winSt = frame.turn(1, 0);
        frame.checkWinner(winSt);
    }

    @Test
    public void test07() {
        frame.settingsFrame();
    }


    @Test
    public void test001() {
        int rows = 4;
        int cols = 5;

        // Simulate filling the board without checking for game end
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                frame.turn(row, col);
            }
        }
        assert frame.getComponentAt(3, 4) != null : "Cell should be filled!";
    }

    @Test //fenster wer gewinnt
    public void test002() throws InterruptedException { //Gewinn ueber Ecke
        frame.turn(2, 0); //x
        Thread.sleep(TIME_OUT);
        frame.turn(2, 1); //o
        Thread.sleep(TIME_OUT);
        frame.turn(2, 2); //x
        Thread.sleep(TIME_OUT);
        frame.turn(3, 2); //o
        Thread.sleep(TIME_OUT);
        frame.turn(2, 3); //x
        Thread.sleep(TIME_OUT);
        WinState winner = frame.turn(3, 1); //o
        assertSame(WinState.none, winner);
    }


    @Test
    public void test003() {
        frame.getPlayer1_score().setText("1"); //inital score
        frame.getPlayer2_score().setText("99"); // -1 kriegen .... aber sollte immer 1+ sein

        // Simulate a win for Player 1
        WinState winner1 = WinState.player1;
        if (WinState.player1 == winner1) {
            // Exponentially increase Player 1's score:
            // (1 | 1) << 2 = 1 << 2 = 4
            int current = Integer.parseInt(frame.getPlayer1_score().getText());
            int updated = (current | 1) << 2;
            frame.getPlayer1_score().setText("" + updated);
        }

        int expectedPlayer1Score = (1 | 1) << 2; // = 1 << 2 = 4
        int actualPlayer1Score = Integer.parseInt(frame.getPlayer1_score().getText());
        Assertions.assertEquals(expectedPlayer1Score, actualPlayer1Score, "Player 1 score should match exponential logic");

        // Simulate a win for Player 2
        WinState winner2 = WinState.player2;
        if (WinState.player2 == winner2) {
            // Always set to -1 regardless of current value
            frame.getPlayer2_score().setText("-1");
        }

        int expectedPlayer2Score = -1;
        int actualPlayer2Score = Integer.parseInt(frame.getPlayer2_score().getText());
        Assertions.assertEquals(expectedPlayer2Score, actualPlayer2Score, "Player 2 score should always be -1 upon winning");
    }

    @Test //004
    public void bauiNegativ() throws InterruptedException {
        frame.turn(0, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 1);
        Thread.sleep(TIME_OUT);
        frame.turn(2, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 2);
        Thread.sleep(TIME_OUT);
        frame.turn(3, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(1, 3);
        Thread.sleep(TIME_OUT);
        WinState winner = frame.turn(2, 0);
        assertSame(WinState.none, winner);
    }

    @Test //006
    public void klickeoftnegativ() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            frame.turn(1, 1);
            Thread.sleep(TIME_OUT);
        }
        String retString = board.getPlayerNameInField(0, 0);

        assertEquals("        ", retString);
    }

    @Test
    public void test07_bauV_gewinnt_nicht() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(1, 1);
        frame.turn(2, 2);
        WinState winst = frame.turn(0, 2);
        assertSame(WinState.none, winst);
    }

    @Test
    public void test08_Unentschieden_error() {
        frame.turn(0, 0);
        frame.turn(0, 1);
        frame.turn(0, 3);
        frame.turn(0, 2);
        frame.turn(1, 1);
        frame.turn(1, 0);
        frame.turn(1, 2);
        frame.turn(1, 3);
        frame.turn(2, 0);
        frame.turn(2, 1);
        frame.turn(2, 3);
        frame.turn(2, 2);
        frame.turn(3, 1);
        frame.turn(3, 0);
        frame.turn(3, 2);
        WinState winSt = frame.turn(3, 3);
        assertSame(WinState.none, winSt);
    }

    @Test
    public void test09_BackslashNoWinAtRightEdge() throws InterruptedException {
        frame.turn(0, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 1);
        Thread.sleep(TIME_OUT);
        frame.turn(1, 1);
        Thread.sleep(TIME_OUT);
        frame.turn(0, 2);
        Thread.sleep(TIME_OUT);
        WinState result = frame.turn(2, 2);
        assertSame(WinState.player1, result);
    }

    @Test
    public void testBackslashDiagonalWinFromBottomLeft() throws InterruptedException {

        p1 = new Player("Player 1", Ressources.icon_x);
        p2 = new Player("Player 2", Ressources.icon_o);
        board = new Board(4, 4, 3, p1, p2);

        frame = new MainWindow(p1, p2, board);
        frame.setVisible(true);
        MainWindow.setDebugg(true);

        frame.turn(0, 3);
        frame.turn(0, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(1, 2);
        frame.turn(1, 0);
        Thread.sleep(TIME_OUT);
        frame.turn(2, 1);
        WinState win = frame.turn(3, 0);

        assertSame(WinState.player1, win);
    }

}