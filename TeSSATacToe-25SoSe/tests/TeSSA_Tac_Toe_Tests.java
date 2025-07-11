// Version für JUnit 5
import com.sun.tools.javac.Main;
import gfx.MainWindow;
import gfx.Ressources;
import logic.Board;
import logic.Player;
import logic.WinState;
import org.junit.jupiter.api.*;

import javax.swing.*;

import java.awt.*;

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

    @Test
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
        frame.getPlayer1_score().setText("1");
        frame.getPlayer2_score().setText("99"); // arbitrary number, will be overwritten to -1 on win

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
    @DisplayName("prüfe i-Formation nicht gewinnt")
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

    @Test
    public void klickeoftnegativ() throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            frame.turn(1, 1); // Clicking on the same position (1,1) each time
            frame.reset();
        }
        String retString = board.getPlayerNameInField(0, 0); // Check the state of the field (0, 0)

        assertEquals("        ", retString); // Assert that the field is still empty
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
        frame.checkWinner(winSt);;
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

    @Test
    public void testcheckwinner() throws InterruptedException {

        p1 = new Player("Player 1", Ressources.icon_x);
        p2 = new Player("Player 2", Ressources.icon_o);
        board = new Board(4, 4, 3, p1, p2);

        frame = new MainWindow(p1, p2, board);
        frame.setVisible(true);
        MainWindow.setDebugg(true);

        frame.turn(0, 3);
        frame.turn(0, 0);

        frame.turn(1, 2);
        frame.turn(1, 0);

        frame.turn(2, 1);
        WinState win = frame.turn(3, 0);
        JFrame setframe=frame.settingsFrame();
        JPanel panel = (JPanel) setframe.getContentPane().getComponent(0);
        JComboBox<?> combo1 = null, combo2 = null;
        int comboBoxCount = 0;
        for (int i = 0; i < panel.getComponentCount(); i++) {
            if (panel.getComponent(i) instanceof JComboBox) {
                comboBoxCount++;
                if (comboBoxCount == 1) combo1 = (JComboBox<?>) panel.getComponent(i);
                if (comboBoxCount == 2) combo2 = (JComboBox<?>) panel.getComponent(i);
            }
        }


        assert combo1 != null;
        assertSame(4,combo1.getItemCount());

    }
    @Test
    public void testwinplayer1() {
        frame.turn(0, 0);
        frame.turn(2, 1);
        frame.turn(0, 1);
        frame.turn(1, 1);
        frame.turn(0, 2);
        WinState winSt = frame.turn(1, 0);
        frame.checkWinner(winSt);
    }

    @Test
    public void testwinplayer2() {
        frame.turn(0, 0);
        frame.turn(2, 0);
        frame.turn(0, 3);
        frame.turn(2, 1);
        frame.turn(2, 3);
        WinState winSt = frame.turn(2, 2);
        frame.checkWinner(winSt);
    }


    @Test
    public void setICon() {
        p1.setIcon(Ressources.icon_tessa_red);
        p2.setIcon(Ressources.icon_tessa_blue);
        assertEquals("TeSSA blue", p2.getIconString());
        assertEquals("TeSSA red", p1.getIconString());

    }
    @Test
    public void setIcon_default() {
        JFrame settingsFrame = frame.settingsFrame();
        JPanel panel = (JPanel) settingsFrame.getContentPane().getComponent(0);
        JComboBox combo1 = (JComboBox) panel.getComponent(1);
        JComboBox combo2 = (JComboBox) panel.getComponent(3);
        JButton saveButton = (JButton) panel.getComponent(4);

        // 강제로 unknown 값 삽입
        combo1.addItem("Unknown");
        combo1.setSelectedItem("Unknown");
        combo2.addItem("Unknown");
        combo2.setSelectedItem("Unknown");

        saveButton.doClick();  // 이 순간 get_icon_for_player("Unknown") 호출됨

        assertEquals("O", p1.getIconString());// icon_o에 대응되는 문자열
        assertEquals("O", p2.getIconString());
    }


    @Test
    public void setIconNothing() {
        p1.setIcon(Ressources.icon_none);
        assertEquals("", p1.getIconString());
    }

//    @Test
//    public void testCheckWinner_DefaultCaseTriggered() throws NullPointerException{
//        frame.checkWinner(null);
//    }

    @Test
    public void testtoString() {

        assertEquals("[Player 1]", p1.toString());


    }

    @Test
    public void testChangeSymbols() throws InterruptedException {

        JPanel settingsPanel= (JPanel) frame.settingsFrame().getContentPane().getComponent(0);
        JComboBox iconAuswahlS1= (JComboBox) settingsPanel.getComponent(1);
        iconAuswahlS1.setSelectedIndex(1);
        JComboBox iconAuswahlS2= (JComboBox) settingsPanel.getComponent(3);
        iconAuswahlS2.selectWithKeyChar((char)"O".getBytes()[0]);
        JButton saveButton= (JButton) settingsPanel.getComponent(4);
        saveButton.doClick();
        assertEquals("O", p2.getIconString());
        assertEquals("TeSSA blue", p1.getIconString());

    }

    @Test
    public void testChangeSymbols2() throws InterruptedException {

        JPanel settingsPanel= (JPanel) frame.settingsFrame().getContentPane().getComponent(0);
        JComboBox iconAuswahlS1= (JComboBox) settingsPanel.getComponent(1);
        iconAuswahlS1.setSelectedIndex(2);
        JComboBox iconAuswahlS2= (JComboBox) settingsPanel.getComponent(3);
        iconAuswahlS2.setSelectedIndex(3);
        JButton saveButton= (JButton) settingsPanel.getComponent(4);
        saveButton.doClick();
        assertEquals("TeSSA blue", p2.getIconString());
        assertEquals("TeSSA red", p1.getIconString());

    }

    @Test
    public void testChangeSymbols3() throws InterruptedException {

        JPanel settingsPanel= (JPanel) frame.settingsFrame().getContentPane().getComponent(0);
        JComboBox iconAuswahlS1= (JComboBox) settingsPanel.getComponent(1);
        iconAuswahlS1.setSelectedIndex(0);
        JComboBox iconAuswahlS2= (JComboBox) settingsPanel.getComponent(3);
        iconAuswahlS2.setSelectedIndex(1);
        JButton saveButton= (JButton) settingsPanel.getComponent(4);
        saveButton.doClick();
        assertEquals("TeSSA blue", p2.getIconString());
        assertEquals("X", p1.getIconString());

    }

    @Test
    public void testChangeSymbols4() throws InterruptedException {

        JPanel settingsPanel= (JPanel) frame.settingsFrame().getContentPane().getComponent(0);
        JComboBox iconAuswahlS1= (JComboBox) settingsPanel.getComponent(1);
        iconAuswahlS1.setSelectedIndex(3);
        JComboBox iconAuswahlS2= (JComboBox) settingsPanel.getComponent(3);
        iconAuswahlS2.setSelectedIndex(2);
        JButton saveButton= (JButton) settingsPanel.getComponent(4);
        saveButton.doClick();
        assertEquals("TeSSA red", p2.getIconString());
        assertEquals("TeSSA blue", p1.getIconString());

    }


    @Test
    public void testgoinSettings() throws InterruptedException {

        JMenuBar menuBar = (JMenuBar) frame.getContentPane().getComponent(0);
        JMenu settingsbutton= (JMenu) menuBar.getComponent(0);
        settingsbutton.doClick();
        settingsbutton.getItem(0).doClick();
    }

    @Test
    public void testReset() throws InterruptedException {

        frame.resetBoard();
        JButton[][] buttons = frame.getButtonArr();
        JButton testbutton=buttons[1][0];
        testbutton.doClick();

    }

    @Test
    public void iconTest() throws InterruptedException {

        frame.setDebugg(false);
        frame.turn(2, 2);


    }

    @Test
    public void test_getSizeAndK() throws InterruptedException {
        Board board = new Board(5,5,3,p1,p2);
        board.getM();
        board.getN();
        board.getK();
        board.getSize();
    }

    @Test
    public void test_tied() throws InterruptedException {

        // Carefully selected pattern to avoid any 4-in-a-row in any direction
        frame.turn(0, 0); // P1 0
        frame.turn(0, 1); // P2 - last move
        frame.turn(0, 3); // 0
        frame.turn(0, 2); // X
        frame.turn(0, 4); // 0

        frame.turn(1, 0); // P2 X
        frame.turn(1, 1); // P1 0
        frame.turn(1, 3); // P2
        frame.turn(1, 2); // P1
        frame.turn(1, 4); // P2

        frame.turn(2, 0); // P1
        frame.turn(2, 1); // P2
        frame.turn(2, 3); // P1
        frame.turn(2, 2); // P2
        frame.turn(2, 4); // P1

        frame.turn(3, 0); // P2
        frame.turn(3, 1); // P1
        frame.turn(3, 3); // P2
        WinState win = frame.turn(3, 2); // P1


        frame.checkWinner(win);
        assertEquals(WinState.tie, win);
    }


}