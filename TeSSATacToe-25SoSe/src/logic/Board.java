package logic;

public class Board {

    private int n, m, k;
    private int board[][];
    private Player player1, player2;
    private Player active;

    public Board(int m, int n, int k, Player p1, Player p2) {
        this.setN(n);
        this.setM(m);
        this.setK(k);
        player1 = p1;
        player2 = p2;
        board = new int[m][n];
        active = player1;
    }

    public void nextTurn() {
        active = (active == player1) ? player2 : player1;
    }

    public Player getActivePlayer() {
        return active;
    }

    public int get2d(int m, int n) {
        return board[m][n];
    }

    public int getSize() {
        return getM() * getN();
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public String getPlayerNameInField(int m, int n) {
        int val = get2d(m, n);
        String retStr;
        switch (val) {
        case 1:
            retStr = player1.getName();
            break;
        case 2:
            retStr = player2.getName();
            break;
        default:
            retStr = "        ";
        }
        return String.format("%5s", retStr);
    }

    public Player getPlayer2d(int m, int n) {
        int pid = get2d(m, n);
        switch (pid) {
        case 1:
            return player1;
        case 2:
            return player2;
        default:
            return null;
        }
    }

    public void resetGame() {
        active = player1;
        board = new int[m][n];
    }



    public void setToken2d(int m, int n, Player p) {
            board[m][n] = (player1 == p) ? 1 : 2;
    }


    public WinState checkWin() {
        int tilesLeft = 0;

        for (int m = 0; m < getM(); m++) {
            for (int n = 0; n < getN(); n++) {
                int checkPlayer = board[m][n];

                if (checkPlayer == 0) {
                    tilesLeft++;
                    continue;
                }

                // Horizontal →
                if (n + k <= getN()) {
                    boolean win = true;
                    for (int i = 0; i < k; i++) {
                        if (board[m][n + i] != checkPlayer) {
                            win = false;
                            break;
                        }
                    }
                    if (win) return WinState.values()[checkPlayer];
                }

                // Vertical ↓
                if (m + k <= getM()) {
                    boolean win = true;
                    for (int i = 0; i < k; i++) {
                        if (board[m + i][n] != checkPlayer) {
                            win = false;
                            break;
                        }
                    }
                    if (win) return WinState.values()[checkPlayer];
                }

                // Diagonal ↘
                if (m + k <= getM() && n + k <= getN()) {
                    boolean win = true;
                    for (int i = 0; i < k; i++) {
                        if (board[m + i][n + i] != checkPlayer) {
                            win = false;
                            break;
                        }
                    }
                    if (win) return WinState.values()[checkPlayer];
                }

                // Anti-diagonal ↙
                if (m + k <= getM() && n - k + 1 >= 0) {
                    boolean win = true;
                    for (int i = 0; i < k; i++) {
                        if (board[m + i][n - i] != checkPlayer) {
                            win = false;
                            break;
                        }
                    }
                    if (win) return WinState.values()[checkPlayer];
                }
            }
        }

        return (tilesLeft == 0) ? WinState.tie : WinState.none;
    }

}
