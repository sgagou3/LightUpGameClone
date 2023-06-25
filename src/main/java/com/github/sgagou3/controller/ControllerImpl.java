package com.github.sgagou3.controller;

import com.github.sgagou3.model.CellType;
import com.github.sgagou3.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private Model m;
  private int idx;

  public ControllerImpl(Model model) {
    m = model;
    idx = 0;
  }

  @Override
  public void clickNextPuzzle() {
    idx++;
    if (idx == m.getPuzzleLibrarySize()) {
      idx = 0;
    }
    m.setActivePuzzleIndex(idx);
  }

  @Override
  public void clickPrevPuzzle() {
    idx--;
    if (idx == -1) {
      idx = m.getPuzzleLibrarySize() - 1;
    }
    m.setActivePuzzleIndex(idx);
  }

  @Override
  public void clickRandPuzzle() {
    Random r = new Random();
    int value = idx;
    while (value == idx) {
      value = r.nextInt(m.getPuzzleLibrarySize());
    }
    idx = value;
    m.setActivePuzzleIndex(idx);
  }

  @Override
  public void clickResetPuzzle() {
    m.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (m.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (m.isLamp(r, c)) {
        m.removeLamp(r, c);
      } else {
        m.addLamp(r, c);
      }
    }
  }
}
