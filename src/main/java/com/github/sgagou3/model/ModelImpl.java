package com.github.sgagou3.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private PuzzleLibrary pl;
  private Puzzle p;
  private int[][] lampBoard;
  private List<ModelObserver> observers;

  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException();
    }
    pl = library;
    p = pl.getPuzzle(0);
    lampBoard = new int[p.getHeight()][p.getWidth()];
    observers = new ArrayList<>();
  }

  @Override
  public void addLamp(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lampBoard[r][c] = 1;
    update();
  }

  @Override
  public void removeLamp(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    lampBoard[r][c] = 0;
    update();
  }

  @Override
  public boolean isLit(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    for (int i = r; 0 <= i; i--) {
      if (lampBoard[i][c] == 1) return true;
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) break;
    }
    for (int i = r; i < p.getHeight(); i++) {
      if (lampBoard[i][c] == 1) return true;
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) break;
    }
    for (int i = c; 0 <= i; i--) {
      if (lampBoard[r][i] == 1) return true;
      if (p.getCellType(r, i) == CellType.WALL || p.getCellType(r, i) == CellType.CLUE) break;
    }
    for (int i = c; i < p.getWidth(); i++) {
      if (lampBoard[r][i] == 1) return true;
      if (p.getCellType(r, i) == CellType.WALL || p.getCellType(r, i) == CellType.CLUE) break;
    }
    return false;
  }

  @Override
  public boolean isLamp(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (p.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lampBoard[r][c] == 1;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return p;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (lampBoard[r][c] != 1) {
      throw new IllegalArgumentException();
    }
    for (int i = r; 0 <= i; i--) {
      if (i == r) continue;
      if (lampBoard[i][c] == 1) return true;
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) break;
    }
    for (int i = r; i < p.getHeight(); i++) {
      if (i == r) continue;
      if (lampBoard[i][c] == 1) return true;
      if (p.getCellType(i, c) == CellType.WALL || p.getCellType(i, c) == CellType.CLUE) break;
    }
    for (int i = c; 0 <= i; i--) {
      if (i == c) continue;
      if (lampBoard[r][i] == 1) return true;
      if (p.getCellType(r, i) == CellType.WALL || p.getCellType(r, i) == CellType.CLUE) break;
    }
    for (int i = c; i < p.getWidth(); i++) {
      if (i == c) continue;
      if (lampBoard[r][i] == 1) return true;
      if (p.getCellType(r, i) == CellType.WALL || p.getCellType(r, i) == CellType.CLUE) break;
    }
    return false;
  }

  @Override
  public int getActivePuzzleIndex() {
    for (int i = 0; i < pl.size(); i++) {
      if (p == pl.getPuzzle(i)) {
        return i;
      }
    }
    return 0;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || pl.size() <= index) {
      throw new IllegalArgumentException();
    }
    p = pl.getPuzzle(index);
    lampBoard = new int[p.getHeight()][p.getWidth()];
    update();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return pl.size();
  }

  @Override
  public void resetPuzzle() {
    for (int i = 0; i < p.getWidth(); i++) {
      for (int j = 0; j < p.getHeight(); j++) {
        lampBoard[i][j] = 0;
      }
    }
    update();
  }

  @Override
  public boolean isSolved() {
    for (int i = 0; i < p.getHeight(); i++) {
      for (int j = 0; j < p.getWidth(); j++) {
        if (p.getCellType(i, j) == CellType.CLUE && !isClueSatisfied(i, j)) return false;
        if (p.getCellType(i, j) == CellType.CORRIDOR && !isLit(i, j)) return false;
        if (p.getCellType(i, j) == CellType.CORRIDOR && isLamp(i, j) && isLampIllegal(i, j))
          return false;
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    if (r < 0 || c < 0 || p.getHeight() <= r || p.getWidth() <= c) {
      throw new IndexOutOfBoundsException();
    } else if (p.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    } else {
      int result = 0;
      if (r + 1 < p.getHeight() && p.getCellType(r + 1, c) == CellType.CORRIDOR) {
        if (isLamp(r + 1, c)) {
          result++;
        }
      }
      if (0 <= r - 1 && p.getCellType(r - 1, c) == CellType.CORRIDOR) {
        if (isLamp(r - 1, c)) {
          result++;
        }
      }
      if (c + 1 < p.getWidth() && p.getCellType(r, c + 1) == CellType.CORRIDOR) {
        if (isLamp(r, c + 1)) {
          result++;
        }
      }
      if (0 <= c - 1 && p.getCellType(r, c - 1) == CellType.CORRIDOR) {
        if (isLamp(r, c - 1)) {
          result++;
        }
      }
      return result == p.getClue(r, c);
    }
  }

  @Override
  public void addObserver(ModelObserver observer) {
    if (observer == null) {
      throw new IllegalArgumentException();
    }
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    if (!observers.contains(observer)) {
      throw new IllegalArgumentException();
    }
    observers.remove(observer);
  }

  public void update() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }
}
