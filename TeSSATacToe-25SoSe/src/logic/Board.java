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
            for (int n = 0; n <= getN() - 2; n++) {
                int checkPlayer = board[m][n];
                if (checkPlayer != 0) {
                    boolean win = false;
                    // rows
                    if ((n + k <= getN())) {
                        win = true;
                        for (int i = 0; i < getK(); i++) {
                            if (checkPlayer != board[m][n + i]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (!win && m + k <= getM()) {
                        win = true;
                        for (int i = 0; i < getK(); i++) {
                            if (checkPlayer != board[m + i][n]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (!win) {
                        win = true;
                        for (int i = 0; i < getK(); i++) {
                            if (checkPlayer != board[(m + i) % getM()][n]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (!win && (m + k <= getM()) && (n + k <= getN())) {
                        win = true;
                        for (int i = 0; i < getK(); i++) {
                            if (checkPlayer != board[m + i][n + i]) {
                                win = false;
                            }
                            if (getM() < 3 && getN() < 3) {
                                win = true;
                            }
                        }
                    }
                    if (!win && (m + k <= getM()) && (n - (k - 1) >= 0)) {
                        win = true;
                        for (int i = 0; i < getK(); i++) {
                            if (checkPlayer != board[m + i][n - i]) {
                                win = false;
                                break;
                            }
                        }
                    }
                    if (!win && (m + 1 < getM()) && (n + 1 < getN())) {
                        win = true;
                        if (checkPlayer != board[m][n]) {
                            win = false;
                        }
                        if (checkPlayer != board[m + 1][n]) {
                            win = false;
                        }
                        if (checkPlayer != board[m + 1][n + 1]) {
                            win = false;
                        }
                    }
                    if (!win && (m + 1 < getM()) && (n + 2 < getN())) {
                        win = true;
                        if (checkPlayer != board[m][n]) {
                            win = false;
                        }
                        if (checkPlayer != board[m][n + 2]) {
                            win = false;
                        }
                        if (checkPlayer != board[m + 1][n + 1]) {
                            win = false;
                        }
                    }
                    if (win) {
                        return WinState.values()[checkPlayer];
                    }
                } else {
                    tilesLeft++;
                }
            }
        }
        if (tilesLeft == 0)

        {
            return WinState.tie;
        }

        return WinState.none;
    }
}
