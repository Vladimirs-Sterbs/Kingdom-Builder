package application;

import java.util.ArrayList;

public class Player {
    private ArrayList<Integer> settlementLocs;
    private ArrayList<String> extra;
    private ArrayList<Boolean> extraUsed;

    private int num, terrain, score;

    public Player(int n) {
        num = n;
        settlementLocs = new ArrayList<>();
        extra = new ArrayList<>();
        extraUsed = new ArrayList<>();
        terrain = 0;
        score = 0;
    }

    public void setTerrainCard(int n) {
        terrain = n;
    }

    public void addSettlment(int x, int y) {
        settlementLocs.add(x * 100 + y);
    }

    public int getNum() {
        return num;
    }

    public int getTerrain() {
        // change once we have this working
        return terrain;
    }

    public ArrayList<Integer> getSettlements() {
        return settlementLocs;
    }

    public int getsettlementnum() {
        return 40 - settlementLocs.size();
    }

    public void removeSettlement(int x, int y, Board b) {
        settlementLocs.remove((Integer) (x * 100 + y));
        b.getBoard()[x][y * 2 + x % 2].changeOwner(-1);
    }

    // change type to 0 - farm; run again for oracle; change type to 4 for oasis
    public ArrayList<Integer> getAvailableTiles(int type, Board b) {
        ArrayList<Integer> temp = new ArrayList<>();

        int count = 0;
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                Tile t = b.getTileAt(r, c);
                // needs to check if next to existing settlement
                if (!t.hasOwner() && b.nextToPlayer(num, r, c * 2 + r % 2)) {
                    // System.out.println("next to player");
                    if (t.getType().compareTo("" + type) == 0) {
                        temp.add(r * 100 + c);
                        // System.out.println("success: "+(r*100+c));
                        count++;
                    }
                }
            }
        }
        if (count == 0) {
            // System.out.println("no valid tiles found: player "+num);
            // b.printBoard(b.getBoard());
            for (int r = 0; r < 20; r++) {
                for (int c = 0; c < 20; c++) {
                    Tile t = b.getTileAt(r, c);
                    if (!t.hasOwner() && t.getType().compareTo("" + type) == 0) {
                        temp.add(r * 100 + c);
                    }
                }
            }
        }
        return temp;
    }

    public ArrayList<Integer> getTower(Board b) {
        ArrayList<Integer> temp = new ArrayList<>();

        int count = 0;
        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                Tile t = b.getTileAt(r, c);
                // needs to check if next to existing settlement
                if (!t.hasOwner() && b.nextToPlayer(num, r, c * 2 + r % 2)) {
                    if (r == 0 || r == 19 || c == 0 || c == 19) {
                        temp.add(r * 100 + c);
                        count++;
                    }
                }
            }
        }
        if (count == 0) {
            for (int r = 0; r < 20; r++) {
                for (int c = 0; c < 20; c++) {
                    Tile t = b.getTileAt(r, c);
                    if (!t.hasOwner() && (r == 0 || r == 19 || c == 0 || c == 19)) {
                        temp.add(r * 100 + c);
                    }
                }
            }
        }
        return filterInvalid(temp, b);
    }

    public ArrayList<Integer> getTavern(Board b) {
        ArrayList<Integer> temp = new ArrayList<>();

        for (int r = 0; r < 20; r++) {
            for (int c = 0; c < 20; c++) {
                Tile t = b.getTileAt(r, c);
                // needs to check if next to existing settlement
                if (t.getOwner() == num) {
                    temp.addAll(b.inLine(r, c * 2 + r % 2, num));
                }
            }
        }
        return filterInvalid(temp, b);
    }

    public ArrayList<Integer> getPaddock(int x, int y, Board b) {
        ArrayList<Integer> temp = new ArrayList<>();
        if (b.isValid(x - 2, y - 2)&&!b.getTileTrueIndex(x - 2, y - 2).hasOwner())
            temp.add((x - 2) * 100 + (y - 2) / 2);
        if (b.isValid(x - 2, y + 2)&&!b.getTileTrueIndex(x - 2, y + 2).hasOwner())
            temp.add((x - 2) * 100 + (y + 2) / 2);
        if (b.isValid(x, y - 4)&&!b.getTileTrueIndex(x, y - 4).hasOwner())
            temp.add((x) * 100 + (y - 4) / 2);
        if (b.isValid(x, y + 4)&&!b.getTileTrueIndex(x, y + 4).hasOwner())
            temp.add((x) * 100 + (y + 4) / 2);
        if (b.isValid(x + 2, y - 2)&&!b.getTileTrueIndex(x + 2, y - 2).hasOwner())
            temp.add((x + 2) * 100 + (y - 2) / 2);
        if (b.isValid(x + 2, y + 2)&&!b.getTileTrueIndex(x + 2, y + 2).hasOwner())
            temp.add((x + 2) * 100 + (y + 2) / 2);
        return filterInvalid(temp, b);
    }
    public ArrayList<Integer> filterInvalid(ArrayList<Integer> temp, Board b){
        for (int i = temp.size() - 1; i >= 0; i--) {
            if (!(b.getTileAt(temp.get(i) / 100, temp.get(i) % 100).hasOwner())
                    && (b.getTileAt(temp.get(i) / 100, temp.get(i) % 100).getType().compareTo("5") >= 0)) {
                temp.remove(i);
            }
        }
        return temp;
    }
    public void incrementScore(int x) {
        score += x;
    }

    public int getScore() {
        return score;
    }

    public boolean extraActions() {
        if (extra.size() != 0) {
            return true;
        }
        return false;
    }

    public boolean hasUsableActions() {
        for (boolean b : getExtraBooleans()) {
            if (!b)
                return true;
        }
        return false;
    }

    public void addExtra(String x) {
        extra.add(x);
        extraUsed.add(true);
    }

    public void minusExtra(String x) {
        for (int i = 0; i < extra.size(); i++) {
            if (extra.get(i).equals(x)) {
                extra.remove(i);
                extraUsed.remove(i);
                return;
            }
        }
    }

    public ArrayList<String> getExtra() {
        return extra;
    }

    public void resetExtraBooleans() {
        int i = extraUsed.size();
        extraUsed = new ArrayList<Boolean>();
        for (int j = 0; j < i; j++)
            extraUsed.add(false);
    }
    
    public void useAll() {
        int i = extraUsed.size();
        extraUsed = new ArrayList<Boolean>();
        for (int j = 0; j < i; j++)
            extraUsed.add(true);
    }

    public ArrayList<Boolean> getExtraBooleans() {
        return extraUsed;
    }

    public void setToUsed(int i) {
        extraUsed.set(i, true);
    }
}
