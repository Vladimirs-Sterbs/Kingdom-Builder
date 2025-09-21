package application;

import java.io.*;
import java.util.*;

public class Board {
	private Tile[][] board;
	private TreeMap<Integer, Tile[][]> smallBoards;
	private static ArrayList<Integer> list;
	private int[] boards;
	private boolean[] orientations;

	public Board() throws IOException {
		smallBoards = new TreeMap<Integer, Tile[][]>();
		Scanner in = new Scanner(getClass().getResourceAsStream("/application/assets/boards.txt"));
		for (int i = 0; i < 8; i++) {
			int num = in.nextInt();
			board = new Tile[10][20];
			for (int r = 0; r < 10; r++) {
				for (int c = 0; c < 10; c++) {
					String t = in.next();
					// add code for determining what child class of tile the tile should be
					board[r][c * 2 + r % 2] = new Tile(t);
					// System.out.println(board);
				}
			}
			smallBoards.put(num, board);
		}
		// System.out.println(smallBoards);
	}

	public ArrayList<Integer> getBoardNumbersList() {
		return list;
	}

	public void createBoard() {
		board = new Tile[20][40];
		TreeSet<Integer> set = new TreeSet<>();
		while (set.size() < 4)
			set.add((int) (Math.random() * 8 + 1));
		list = new ArrayList<>();
		list.addAll(set);
		Collections.shuffle(list);
		// System.out.println(list);
		Tile[][] b1, b2, b3, b4;
		Random rand = new Random();
		orientations = new boolean[4];
		boards = new int[4];

		boards[0] = list.get(0);
		b1 = smallBoards.get(list.get(0));
		orientations[0] = rand.nextBoolean();
		if (orientations[0])
			b1 = reverse(b1);
		boards[1] = list.get(1);
		b2 = smallBoards.get(list.get(1));
		orientations[1] = rand.nextBoolean();
		if (orientations[1])
			b2 = reverse(b2);
		boards[2] = list.get(2);
		b3 = smallBoards.get(list.get(2));
		orientations[2] = rand.nextBoolean();
		if (orientations[2])
			b3 = reverse(b3);
		boards[3] = list.get(3);
		b4 = smallBoards.get(list.get(3));
		orientations[3] = rand.nextBoolean();
		if (orientations[3])
			b4 = reverse(b4);
		for (int i = 0; i < 10; i++) {
			Tile[] bList = new Tile[40];
			for (int j = 0; j < 20; j++) {
				bList[j] = b1[i][j];
			}
			for (int j = 0; j < 20; j++) {
				bList[j + 20] = b2[i][j];
			}
			board[i] = bList;
		}
		for (int i = 0; i < 10; i++) {
			Tile[] bList = new Tile[40];
			for (int j = 0; j < 20; j++) {
				bList[j] = b3[i][j];
			}
			for (int j = 0; j < 20; j++) {
				bList[j + 20] = b4[i][j];
			}
			board[i + 10] = bList;
		}
		// printBoard(board);
	}

	public void printBoard(Tile[][] b) {
		for (Tile[] r : b) {
			for (Tile t : r) {
				if (t != null)
					System.out.print(t + " ");
				else
					System.out.print("  ");
			}
			System.out.println();
		}
	}

	public Tile getTileAt(int x, int y) {
		if (x < 20 && y < 20)
			return board[x][y * 2 + x % 2];
		return null;
	}

	public Tile getTileTrueIndex(int r, int c) {
		if (isValid(r, c))
			return board[r][c];
		return new Tile("sss");
	}

	public Tile[] adjacentTiles(int x, int y) {
		Tile[] list = new Tile[6];
		// starts at the hex to the up left, ends with left, moving clockwise
		list[0] = getTileTrueIndex(x - 1, y - 1);
		list[1] = getTileTrueIndex(x - 1, y + 1);
		list[2] = getTileTrueIndex(x, y + 2);
		list[3] = getTileTrueIndex(x + 1, y + 1);
		list[4] = getTileTrueIndex(x + 1, y - 1);
		list[5] = getTileTrueIndex(x, y - 2);
		return list;
	}

	public ArrayList<Integer> inLine(int x, int y, int p) {
		ArrayList<Integer> list = new ArrayList<>();
		if (isValid(x - 3, y - 3) && getTileTrueIndex(x - 1, y - 1).getOwner() == p
				&& getTileTrueIndex(x - 2, y - 2).getOwner() == p)
			if (!getTileTrueIndex(x - 3, y - 3).hasOwner())
				list.add((x - 3) * 100 + (y - 3) / 2);
		if (isValid(x - 3, y + 3) && getTileTrueIndex(x - 1, y + 1).getOwner() == p
				&& getTileTrueIndex(x - 2, y + 2).getOwner() == p)
			if (!getTileTrueIndex(x - 3, y + 3).hasOwner())
				list.add((x - 3) * 100 + (y + 3) / 2);
		if (isValid(x + 3, y + 3) && getTileTrueIndex(x + 1, y + 1).getOwner() == p
				&& getTileTrueIndex(x + 2, y + 2).getOwner() == p)
			if (!getTileTrueIndex(x + 3, y + 3).hasOwner())
				list.add((x + 3) * 100 + (y + 3) / 2);
		if (isValid(x + 3, y - 3) && getTileTrueIndex(x + 1, y - 1).getOwner() == p
				&& getTileTrueIndex(x + 2, y - 2).getOwner() == p)
			if (!getTileTrueIndex(x + 3, y - 3).hasOwner())
				list.add((x + 3) * 100 + (y - 3) / 2);
		if (isValid(x, y - 6) && getTileTrueIndex(x, y - 2).getOwner() == p
				&& getTileTrueIndex(x, y - 4).getOwner() == p)
			if (!getTileTrueIndex(x, y - 6).hasOwner())
				list.add((x) * 100 + (y - 6) / 2);
		if (isValid(x, y + 6) && getTileTrueIndex(x, y + 2).getOwner() == p
				&& getTileTrueIndex(x, y + 4).getOwner() == p)
			if (!getTileTrueIndex(x, y + 6).hasOwner())
				list.add((x) * 100 + (y + 6) / 2);
		return list;
	}

	public boolean nextToPlayer(int p, int x, int y) {
		Tile[] list = adjacentTiles(x, y);
		for (Tile t : list) {
			if (t != null && t.getOwner() == p) {
				return true;
			}
		}
		return false;
	}

	public Tile[][] reverse(Tile[][] b) {
		Tile[][] temp = new Tile[b.length][b[0].length];
		for (int i = 0; i < b.length; i++) {
			temp[i] = flip(b[b.length - 1 - i]);
		}
		return temp;
	}

	public Tile[] flip(Tile[] list) {
		Tile[] temp = new Tile[list.length];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = list[list.length - i - 1];
		}
		return temp;
	}

	public Tile[] shift(Tile[] list, int amount) {
		Tile[] temp = new Tile[list.length];
		for (int i = 0; i < list.length; i++) {
			temp[i] = list[(i + amount) % list.length];
		}
		return temp;
	}

	// checks if the given index is a valid tile; x is row
	public boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x < board.length && y < 40 && board[x][y] != null;
	}

	public boolean[] getOrientations() {
		return orientations;
	}

	public int[] getBoardNumbers() {
		return boards;
	}

	public Tile[][] getBoard() {
		return board;
	}
	// just for testing that the program works
	/*
	 * public static void main(String args[]) {
	 * Board b = new Board();
	 * b.createBoard();
	 * }
	 */
}
