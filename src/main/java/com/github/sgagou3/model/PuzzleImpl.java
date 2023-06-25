package com.github.sgagou3.model;

public class PuzzleImpl implements Puzzle {
  private int width;
  private int height;
  private int[][] b;

  public PuzzleImpl(int[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException();
    }
    width = board[0].length;
    height = board.length;
    b = new int[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        b[i][j] = board[i][j];
      }
    }
  }

  @Override
  public int getWidth() {
    return width;
  }

  @Override
  public int getHeight() {
    return height;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r < 0 || c < 0 || getHeight() <= r || getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    }
    if (0 <= b[r][c] && b[r][c] <= 4) {
      return CellType.CLUE;
    } else if (b[r][c] == 5) {
      return CellType.WALL;
    } else if (b[r][c] == 6) {
      return CellType.CORRIDOR;
    }
    return null;
  }

  @Override
  public int getClue(int r, int c) {
    if (r < 0 || c < 0 || getHeight() <= r || getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return b[r][c];
  }
}
