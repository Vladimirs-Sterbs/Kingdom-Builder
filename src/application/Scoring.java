package application;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Scoring {
    private Board b;
    private String[][] ownerboard;

    public Scoring(Board board) {
        b = board;
    }

    public int[] scoreCities(Board b, int nP) {
        int[] scores = new int[nP];
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                if (b.getTileAt(r, c).getType().equals("5"))
                    for (int i = 0; i < nP; i++) {
                        if (contains(b.adjacentTiles(r, c * 2 + r % 2), i)) {
                            scores[i] += 3;
                        }
                    }
            }
        }
        // System.out.println(scores[0]);
        return scores;
    }

    public boolean contains(Tile[] list, int p) {
        for (Tile t : list) {
            if (t != null && t.getOwner() == p)
                return true;
        }
        // System.out.println(p);
        return false;
    }

    public int scoreCitizens(int p) {
        int count = 0, max = 0;
        int[][] temp = createTempBoard();
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (temp[r][c * 2 + r % 2] == p) {
                    count = countCluster(temp, r, c * 2 + r % 2, p);
                    if (max < count)
                        max = count;
                }

            }
        }
        return (int) (max / 2);
    }

    public int countCluster(int[][] temp, int r, int c, int p) {
        if ((r < 0 || c < 0 || c > 39 || r > 19) || temp[r][c] != p)
            return 0;
        temp[r][c] = -1;
        return 1 + countCluster(temp, r - 1, c - 1, p) + countCluster(temp, r - 1, c + 1, p)
                + countCluster(temp, r, c + 2, p) + countCluster(temp, r + 1, c + 1, p)
                + countCluster(temp, r + 1, c - 1, p) + countCluster(temp, r, c - 2, p);
    }

    public int[][] createTempBoard() {
        Tile[][] board;
        int[][] temp;
        board = b.getBoard();
        temp = new int[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                temp[r][c] = -1;
            }
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (board[r][c * 2 + r % 2] != null)
                    temp[r][c * 2 + r % 2] = board[r][c * 2 + r % 2].getOwner();
            }
        }
        return temp;
    }

    public int scoreDiscoverers(int p) {
        Tile[][] board = b.getBoard();
        int score = 0;
        for (Tile[] row : board) {
            int count = 0;
            for (Tile t : row) {
                if (t != null && t.getOwner() == p)
                    count++;
            }
            score += Math.min(count, 1);
        }
        return score;
    }

    public int scoreFarmers(int p) {
        int[] list = getNumInEachQuadrant(p);
        int min = 100000;
        for (int k : list) {
            min = Math.min(k, min);
        }
        return min * 3;
    }

    public int[] getNumInEachQuadrant(int p) {
        int[] list = new int[4];
        int idx = 0;
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                Tile t = b.getTileAt(r, c);
                if (t.getOwner() == p) {
                    idx = 0;
                    if (r > 9)
                        idx += 2;
                    if (c > 9)
                        idx++;
                    list[idx]++;
                }
            }
        }
        return list;
    }

    public int scoreFisherman(int p) {
        int score = 0;
        Tile[][] board = b.getBoard();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (board[r][c * 2 + r % 2] != null && board[r][c * 2 + r % 2].getOwner() == p
                        && !board[r][c * 2 + r % 2].getType().equals("6")) {
                    if (containsType(b.adjacentTiles(r, c * 2 + r % 2), "" + 6))
                        score++;
                }
            }
        }
        return score;
    }

    public boolean containsType(Tile[] list, String type) {
        for (Tile t : list)
            if (t != null && t.getType().equals(type))
                return true;
        return false;
    }

    public int scoreHermits(int p) {
        int count = 0;
        int[][] temp = createTempBoard();
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (temp[r][c * 2 + r % 2] == p) {
                    countCluster(temp, r, c * 2 + r % 2, p);
                    count++;
                }
            }
        }
        return count;
    }

    public int scoreKnights(int p) {
        Tile[][] board = b.getBoard();
        int max = 0;
        for (Tile[] row : board) {
            int count = 0;
            for (Tile t : row) {
                if (t != null && t.getOwner() == p)
                    count++;
            }
            max = Math.max(max, count);
        }
        return max * 2;
    }

    public int[] scoreLords(int nP) {
        int[] playerScores = new int[nP];
        int[][] playerCounts = new int[nP][];
        for (int p = 0; p < nP; p++) {
            playerCounts[p] = getNumInEachQuadrant(p);
        }
        // printMat(playerCounts);
        for (int c = 0; c < playerCounts[0].length; c++) {
            int max = -1, firstscore = -1;
            ArrayList<Integer> maxP = new ArrayList<>();
            for (int r = 0; r < playerCounts.length; r++) {
                if (playerCounts[r][c] >= max) {
                    max = playerCounts[r][c];
                }
            }
            for (int r = 0; r < playerCounts.length; r++) {
                if (playerCounts[r][c] == max) {
                    maxP.add(r);
                }
            }
            firstscore = max;
            max = -1;
            for (int p : maxP) {
                playerScores[p] += 12;
            }
            // for(int s: playerScores){
            // System.out.print(s+" ");
            // }
            // System.out.println("fin");
            maxP = new ArrayList<>();
            for (int r = 0; r < playerCounts.length; r++) {
                if (playerCounts[r][c] >= max && playerCounts[r][c] < firstscore) {
                    max = playerCounts[r][c];
                }
            }
            for (int r = 0; r < playerCounts.length; r++) {
                if (playerCounts[r][c] == max) {
                    maxP.add(r);
                }
            }
            for (int p : maxP) {
                playerScores[p] += 6;
            }
        }
        return playerScores;
    }

    public int scoreMerchants(int p) {
        int score = 0;
        ownerboard = createMerchantBoard();
        // printMat(ownerboard);
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                if (Pattern.matches("[5ABCDEFGH]", ownerboard[r][c * 2 + r % 2])) {
                    ownerboard[r][c * 2 + r % 2] = "" + p;
                    int tScore = countPath(r, c * 2 + r % 2, p);

                    // System.out.println("score: " + tScore);
                    if (tScore != 0) {
                        tScore += 4;
                    }

                    score += tScore;
                }
            }
        }
        // printMat(ownerboard);
        return score;
    }

    public int countPath(int r, int c, int p) {
        if ((r < 0 || c < 0 || c > 39 || r > 19) || ownerboard[r][c].equals(".") || !(ownerboard[r][c].equals("" + p)
                || ownerboard[r][c].equals("start") || Pattern.matches("[5ABCDEFGH]", ownerboard[r][c])))
            return 0;

        int score = 0;
        if (Pattern.matches("[5ABCDEFGH]", ownerboard[r][c]))
            score = 4;

        // System.out.println(ownerboard[r][c] + " r" + r + " c" + c);
        ownerboard[r][c] = ".";
        return score + countPath(r - 1, c - 1, p) + countPath(r - 1, c + 1, p)
                + countPath(r, c + 2, p) + countPath(r + 1, c + 1, p)
                + countPath(r + 1, c - 1, p) + countPath(r, c - 2, p);
    }

    public String[][] createMerchantBoard() {
        Tile[][] board;
        String[][] temp;
        board = b.getBoard();
        temp = new String[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                temp[r][c] = ".";
            }
        }
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (board[r][c * 2 + r % 2] != null && board[r][c * 2 + r % 2].hasOwner())
                    temp[r][c * 2 + r % 2] = "" + board[r][c * 2 + r % 2].getOwner();
                if (Pattern.matches("[5ABCDEFGH]", board[r][c * 2 + r % 2].getType()))
                    temp[r][c * 2 + r % 2] = board[r][c * 2 + r % 2].getType();
            }
        }
        return temp;
    }

    public int scoreMiners(int p) {
        int score = 0;
        Tile[][] board = b.getBoard();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (board[r][c * 2 + r % 2] != null && board[r][c * 2 + r % 2].getOwner() == p) {
                    if (containsType(b.adjacentTiles(r, c * 2 + r % 2), "" + 7))
                        score++;
                }
            }
        }
        return score;
    }

    public int scoreWorkers(int p) {

        int score = 0;
        Tile[][] board = b.getBoard();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < 20; c++) {
                if (board[r][c * 2 + r % 2] != null && board[r][c * 2 + r % 2].getOwner() == p) {
                    score += numSpecials(b.adjacentTiles(r, c * 2 + r % 2));
                }
            }
        }
        return score;
    }

    public boolean containsSpecial(Tile[] list) {
        for (Tile t : list)
            if (t != null && Pattern.matches("[5ABCDEFHG]", t.getType()))
                return true;
        return false;
    }

    public int numSpecials(Tile[] list) {
        int s = 0;
        for (Tile t : list)
            if (t != null && Pattern.matches("[5ABCDEFHG]", t.getType()))
                s++;
        return s;
    }

    public void printMat(String[][] ownerboard) {
        for (String[] row : ownerboard) {
            for (String k : row)
                System.out.print(k + " ");
            System.out.println();
        }
    }

    public void printMat(int[][] mat) {
        for (int[] row : mat) {
            for (int k : row)
                System.out.print(k + " ");
            System.out.println();
        }
    }
}
