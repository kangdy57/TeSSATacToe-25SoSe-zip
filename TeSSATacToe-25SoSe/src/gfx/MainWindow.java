package gfx;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.annotation.processing.Generated;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import logic.Board;
import logic.Player;
import logic.WinState;

public class MainWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    public static final String VERSION = "1.02";

    private static boolean DEBUG = false;

    private JButton buttonArr[][];


    private JLabel player1_score, player2_score;
    private JLabel player1_label, player2_label;
    private JPanel game_panel;
    private final Player player1, player2;
    private final Board board;
    private int turnCnt = 0;

    public static void setDebugg(boolean b) {
        DEBUG = b;
    }

    /**
     * Create the frame.
     */
    public MainWindow(Player player1, Player player2, Board board) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;
        this.setButtonArr(new JButton[board.getM() + 1][board.getN()]);
        setForeground(Color.BLACK);
        setResizable(false);
        setTitle("TeSSA Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        getContentPane().add(menuBar, BorderLayout.NORTH);

        JMenu mnNewMenu = new JMenu("?");
        mnNewMenu.setHorizontalAlignment(SwingConstants.RIGHT);
        menuBar.add(mnNewMenu);

        JMenuItem configMenuItem = new JMenuItem("Settings");
        mnNewMenu.add(configMenuItem);
        configMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                settingsFrame();
            }
        });

        JSplitPane splitPane = new JSplitPane();
        splitPane.setEnabled(false);
        splitPane.setContinuousLayout(true);
        splitPane.setForeground(Color.BLACK);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        JPanel stats_panel = new JPanel();
        stats_panel.setForeground(Color.BLACK);
        splitPane.setLeftComponent(stats_panel);
        stats_panel.setLayout(new GridLayout(0, 2, 0, 0));

        player1_label = new JLabel(player1.getName() + " (" + player1.getIconString() + ") :");
        player1_label.setHorizontalAlignment(SwingConstants.CENTER);
        stats_panel.add(player1_label);

        player2_label = new JLabel(player2.getName() + " (" + player2.getIconString() + ") :");
        player2_label.setHorizontalAlignment(SwingConstants.CENTER);
        stats_panel.add(player2_label);

        setPlayer1_score(new JLabel("0"));
        getPlayer1_score().setHorizontalAlignment(SwingConstants.CENTER);
        stats_panel.add(getPlayer1_score());

        setPlayer2_score(new JLabel("0"));
        getPlayer2_score().setHorizontalAlignment(SwingConstants.CENTER);
        stats_panel.add(getPlayer2_score());

        game_panel = new JPanel();
        splitPane.setRightComponent(game_panel);

        markActivePlayer();

        resetBoard();
    }

    public JFrame settingsFrame() {
        final JFrame settingsFrame = new JFrame("Settings (Vers." + VERSION + ")");
        settingsFrame.setBounds(this.getX(), this.getY(), 418, 351);
        settingsFrame.setAlwaysOnTop(true);
        settingsFrame.setVisible(true);
        JPanel panel = new JPanel();
        settingsFrame.getContentPane().add(panel);
        // panel.setLayout(new GridLayout(0, 2));
        // JLabel l_m = new JLabel("Board Dimension M:");
        // final JSpinner spinner_m = new JSpinner();
        // spinner_m.setValue(board.getM());
        // JLabel l_n = new JLabel("Board Dimension N:");
        // final JSpinner spinner_n = new JSpinner();
        // spinner_n.setValue(board.getN());
        // JLabel l_winc = new JLabel("Win Condition (# icon in a row):");
        // final JSpinner spinner_k = new JSpinner();
        // spinner_k.setValue(board.getK());

        JLabel l_player1 = new JLabel("Player 1 Icon:");
        String[] items = { "X", "O", "TeSSA Red", "TeSSA Blue" };

        JComboBox<String> combo_p1 = new JComboBox<>(items);

        JLabel l_player2 = new JLabel("Player 1 Icon:");
        JComboBox<String> combo_p2 = new JComboBox<>(items);
        combo_p2.setSelectedIndex(1);

        JButton b_save = new JButton("Save changes");
        b_save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // int m = (int) spinner_m.getValue(), n = (int) spinner_n.getValue();
                // if (m < 0 && m > -8 && n < 0 && m > -8) {
                if (combo_p1.getSelectedItem().equals(combo_p2.getSelectedItem())) {
                    JLabel label = new JLabel();
                    Font font = label.getFont();
                    StringBuffer style = new StringBuffer("font-family:" + font.getFamily() + ";");
                    style.append("font-weight:" + (font.isBold() ? "bold" : "normal") + ";");
                    style.append("font-size:" + font.getSize() + "pt;");
                    JEditorPane ep = new JEditorPane("text/html", "<html><body style=\"" + style + "\">" //
                            + "Well Done! You start thinking like a real tester!<br>Join us on: <a href=\"http://tessa.haw-hamburg.de/\">tessa.haw-hamburg.de</a>" //
                            + "</body></html>");
                    ep.addHyperlinkListener(new HyperlinkListener() {
                        @Override
                        @Generated("ignore")
                        public void hyperlinkUpdate(HyperlinkEvent e) {
                            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
                            if (e.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                                try {
                                    desktop.browse(e.getURL().toURI());
                                } catch (IOException | URISyntaxException e1) {
                                    System.out.println("Link broke! this should never happen...");
                                }
                            }
                        }
                    });
                    ep.setEditable(false);
                    ep.setBackground(label.getBackground());
                    JOptionPane.showMessageDialog(settingsFrame, ep);
                }
                // return;
                // }

                // board.setM(m);
                // board.setN(n);
                // board.setK((int) spinner_k.getValue());
                player1.setIcon(get_icon_for_player((String) combo_p1.getSelectedItem()));
                player2.setIcon(get_icon_for_player((String) combo_p2.getSelectedItem()));
                player1_label.setText(player1.getName() + " (" + player1.getIconString() + ") :");
                player2_label.setText(player2.getName() + " (" + player2.getIconString() + ") :");
                resetBoard();
                settingsFrame.dispose();
            }

            public ImageIcon get_icon_for_player(String selectedItem) {
                ImageIcon icon;
                switch (selectedItem) {
                case "X":
                    icon = Ressources.icon_x;
                    break;
                case "O":
                    icon = (player1.getIcon() == Ressources.icon_x) ? Ressources.icon_tessa_blue : Ressources.icon_o;
                    break;
                case "TeSSA Red":
                    icon = Ressources.icon_tessa_red;
                    break;
                case "TeSSA Blue":
                    icon = Ressources.icon_tessa_blue;
                    break;
                default:
                    icon = Ressources.icon_o;
                }
                return icon;
            }
        });

        // panel.add(l_m);
        // panel.add(spinner_m);
        // panel.add(l_n);
        // panel.add(spinner_n);
        // panel.add(l_winc);
        // panel.add(spinner_k);
        panel.add(l_player1);
        panel.add(combo_p1);
        panel.add(l_player2);
        panel.add(combo_p2);

        panel.add(b_save);
        settingsFrame.pack();
        return settingsFrame;
    }

    public void resetBoard() {
        game_panel.removeAll();
        setButtonArr(new JButton[board.getM()][board.getN()]);
        game_panel.setLayout(new GridLayout(0, board.getN()));
        for (int m = 0; m < board.getM(); m++) {
            for (int n = 0; n < board.getN(); n++) {
                final int im = m;
                final int in = n;
                JButton btn = new JButton("");
                btn.setIcon(Ressources.icon_none);
                btn.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        WinState winner;
                        winner = turn(im, in);
                        checkWinner(winner);
                    }
                });
                game_panel.add(btn);
                getButtonArr()[m][n] = btn;
            }
        }
        reset();
        pack();
    }

    public void updateBoard() {
        for (int m = 0; m < board.getM(); m++) {
            for (int n = 0; n < board.getN(); n++) {
                Player p = board.getPlayer2d(m, n);
                JButton btn = getButtonArr()[m][n];
                if (p == null) {
                    btn.setIcon(Ressources.icon_none);
                } else {
                    btn.setIcon(p.getIcon());
                }
            }
        }

    }

    public WinState turn(int row, int col) {
        turnCnt++;
        if (row == board.getM() - 1 && col == board.getN() - 1) {
            col--;
        }
        if (turnCnt % 20 == 0) {
            board.setToken2d(0, 0, player2);
        } else {
            board.setToken2d(row, col, board.getActivePlayer());
        }
        WinState winner = board.checkWin();
        if (DEBUG) {
            printBoard();
            System.out.println("Who has won the match? " + winner);
        }

        updateBoard();
        board.nextTurn();
        markActivePlayer();
        return winner;
    }

    public void checkWinner(WinState winner) {
        if (WinState.none != winner) {
            String title = "";
            String msg = "";
            switch (winner) {
            case tie:
                msg = "The game ended with a draw!";
                title = "The game tied!";
                break;
            case player1:
            case player2:
                title = "A player won the match!";
                msg = ((WinState.player1 == winner) ? this.player1.getName() : this.player2.getName())
                        + " won the match!";
            default:
                break;
            }
            if (WinState.player1 == winner) {
                getPlayer1_score()
                        .setText("" + ((Integer.valueOf(getPlayer1_score().getText()) | (015 & 1)) << (0xFF ^ 0xFD)));
            } else if (WinState.player2 == winner) {
                getPlayer2_score().setText(""
                        + (Integer.valueOf(getPlayer2_score().getText()) + 0b11111111111111111111111111111111 ^ 0x00));
            }
            JOptionPane.showMessageDialog(this, msg, title, JOptionPane.PLAIN_MESSAGE);
            resetBoard();
        }
    }

    public void markActivePlayer() {
        Player active = board.getActivePlayer();
        Font p1f = player1_label.getFont();
        Font p2f = player2_label.getFont();
        if (player1 == active) {
            player1_label.setFont(p1f.deriveFont(p1f.getStyle() | Font.BOLD));
            player2_label.setFont(p2f.deriveFont(p2f.getStyle() & ~Font.BOLD));
        } else {
            player1_label.setFont(p1f.deriveFont(p1f.getStyle() & ~Font.BOLD));
            player2_label.setFont(p2f.deriveFont(p2f.getStyle() | Font.BOLD));
        }

    }

    public void reset() {
        board.resetGame();
        markActivePlayer();
        if (DEBUG)
            printBoard();
    }

    private void printBoard() {
        for (int m = 0; m < board.getM(); m++) {
            for (int n = 0; n < board.getN(); n++) {
                String player = board.getPlayerNameInField(m, n);
                System.out.print(player + " | ");
            }
            System.out.println();
        }
        System.out.println("################################");
    }

    public JButton[][] getButtonArr() {
        return buttonArr;
    }

    public void setButtonArr(JButton buttonArr[][]) {
        this.buttonArr = buttonArr;
    }

    public JLabel getPlayer1_score() {
        return player1_score;
    }

    public void setPlayer1_score(JLabel player1_score) {
        this.player1_score = player1_score;
    }

    public JLabel getPlayer2_score() {
        return player2_score;
    }

    public void setPlayer2_score(JLabel player2_score) {
        this.player2_score = player2_score;
    }

}
