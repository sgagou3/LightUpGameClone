package com.github.sgagou3.view;

import com.github.sgagou3.SamplePuzzles;
import com.github.sgagou3.controller.ClassicMvcController;
import com.github.sgagou3.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import com.github.sgagou3.model.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    Model model = new ModelImpl(library);
    ClassicMvcController controller = new ControllerImpl(model);

    Stage winnerStage = new Stage();

    stage.setTitle("Akari");

    BorderPane pane = new BorderPane();
    GridPane centerPane = new GridPane();
    centerPane.setStyle("-fx-background-color: #808080");
    centerPane.setMaxSize(450, 450);
    centerPane.setMinSize(450, 450);
    centerPane.setAlignment(Pos.CENTER);

    pane.setTop(
        new Text(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize()));

    Button next = new Button("Next puzzle");
    Button reset = new Button("Reset");
    Button prev = new Button("Previous puzzle");
    Button rand = new Button("Random puzzle");

    next.setOnAction(
        event -> {
          controller.clickNextPuzzle();
          Text newText =
              new Text(
                  "Puzzle "
                      + (model.getActivePuzzleIndex() + 1)
                      + "/"
                      + model.getPuzzleLibrarySize());
          newText.setTextAlignment(TextAlignment.CENTER);
          pane.setTop(newText);
          setScene(model, stage, controller);
        });
    reset.setOnAction(
        event -> {
          controller.clickResetPuzzle();
          if (winnerStage.isShowing()) {
            winnerStage.close();
          }
          for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
            for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
              if (model.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
              }
              if (model.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #808080;");
                b.setGraphic(null);
              }
              if (model.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #000000;");
              }
            }
          }
        });
    prev.setOnAction(
        event -> {
          controller.clickPrevPuzzle();
          Text newText =
              new Text(
                  "Puzzle "
                      + (model.getActivePuzzleIndex() + 1)
                      + "/"
                      + model.getPuzzleLibrarySize());
          newText.setTextAlignment(TextAlignment.CENTER);
          pane.setTop(newText);
          setScene(model, stage, controller);
        });
    rand.setOnAction(
        event -> {
          controller.clickRandPuzzle();
          Text newText =
              new Text(
                  "Puzzle "
                      + (model.getActivePuzzleIndex() + 1)
                      + "/"
                      + model.getPuzzleLibrarySize());
          newText.setTextAlignment(TextAlignment.CENTER);
          pane.setTop(newText);
          setScene(model, stage, controller);
        });
    setScene(model, stage, controller);
  }

  private void setScene(Model model, Stage stage, ClassicMvcController controller) {
    Stage winnerStage = new Stage();

    stage.setTitle("Akari");

    BorderPane pane = new BorderPane();
    GridPane centerPane = new GridPane();
    centerPane.setStyle("-fx-background-color: #808080");
    centerPane.setMaxSize(450, 450);
    centerPane.setMinSize(450, 450);
    centerPane.setAlignment(Pos.CENTER);

    pane.setTop(
        new Text(
            "Puzzle " + (model.getActivePuzzleIndex() + 1) + "/" + model.getPuzzleLibrarySize()));

    Button next = new Button("Next puzzle");
    Button reset = new Button("Reset");
    Button prev = new Button("Previous puzzle");
    Button rand = new Button("Random puzzle");

    next.setOnAction(
        event -> {
          controller.clickNextPuzzle();
          setScene(model, stage, controller);
        });
    reset.setOnAction(
        event -> {
          controller.clickResetPuzzle();
          if (winnerStage.isShowing()) {
            winnerStage.close();
          }
          for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
            for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
              if (model.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
              }
              if (model.getActivePuzzle().getCellType(i, j) == CellType.CORRIDOR) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #808080;");
                b.setGraphic(null);
              }
              if (model.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
                Button b =
                    (Button)
                        centerPane.getChildren().get(j + i * model.getActivePuzzle().getHeight());
                b.setStyle("-fx-background-color: #000000;");
              }
            }
          }
        });
    prev.setOnAction(
        event -> {
          controller.clickPrevPuzzle();
          setScene(model, stage, controller);
        });
    rand.setOnAction(
        event -> {
          controller.clickRandPuzzle();
          setScene(model, stage, controller);
        });
    for (int i = 0; i < model.getActivePuzzle().getHeight(); i++) {
      for (int j = 0; j < model.getActivePuzzle().getWidth(); j++) {
        if (model.getActivePuzzle().getCellType(i, j) == CellType.WALL) {
          Button b = new Button();
          b.setDisable(true);
          b.setStyle("-fx-background-color: #000000");
          b.setPrefSize(
              450 / model.getActivePuzzle().getWidth(), 450 / model.getActivePuzzle().getHeight());
          centerPane.add(b, j, i);
        } else if (model.getActivePuzzle().getCellType(i, j) == CellType.CLUE) {
          Button b = new Button("" + model.getActivePuzzle().getClue(i, j));
          b.setDisable(true);
          b.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
          b.setPrefSize(
              450 / model.getActivePuzzle().getWidth(), 450 / model.getActivePuzzle().getHeight());
          centerPane.add(b, j, i);
        } else {
          Button b = new Button();
          b.setStyle("-fx-background-color: #808080");
          b.setPrefSize(
              450 / model.getActivePuzzle().getWidth(), 450 / model.getActivePuzzle().getHeight());
          centerPane.add(b, j, i);

          Image image = new Image("light-bulb.png");
          ImageView view = new ImageView(image);
          view.setFitWidth(250 / model.getActivePuzzle().getWidth());
          view.setFitHeight(250 / model.getActivePuzzle().getHeight());

          int finalI = i;
          int finalJ = j;
          final boolean[] parity = {false};
          b.setOnAction(
              event -> {
                if (parity[0]) {
                  if (winnerStage.isShowing()) {
                    winnerStage.close();
                  }
                  parity[0] = false;
                  b.setGraphic(null);
                  boolean illegal = model.isLampIllegal(finalI, finalJ);
                  model.removeLamp(finalI, finalJ);

                  for (int ii = finalI; ii < model.getActivePuzzle().getHeight(); ii++) {
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CORRIDOR) {
                      if (!model.isLit(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #808080;");
                      }
                    } else if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CLUE) {
                      if (!model.isClueSatisfied(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    } else if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.WALL)
                      break;
                  }

                  for (int ii = finalI; 0 <= ii; ii--) {
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CORRIDOR) {
                      if (!model.isLit(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #808080;");
                      }
                    } else if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CLUE) {
                      if (!model.isClueSatisfied(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    } else if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.WALL)
                      break;
                  }

                  for (int jj = finalJ; jj < model.getActivePuzzle().getWidth(); jj++) {
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CORRIDOR) {
                      if (!model.isLit(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #808080;");
                      }
                    } else if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CLUE) {
                      if (!model.isClueSatisfied(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    } else if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.WALL)
                      break;
                  }

                  for (int jj = finalJ; 0 <= jj; jj--) {
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CORRIDOR) {
                      if (!model.isLit(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #808080;");
                      }
                    } else if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CLUE) {
                      if (!model.isClueSatisfied(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    } else if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.WALL)
                      break;
                  }

                  if (illegal) {
                    Node n =
                        centerPane
                            .getChildren()
                            .get(finalJ + finalI * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FFFF00;");
                  }
                } else {
                  parity[0] = true;
                  b.setGraphic(view);
                  model.addLamp(finalI, finalJ);
                  boolean bad = model.isLampIllegal(finalI, finalJ);
                  for (int ii = finalI; ii < model.getActivePuzzle().getHeight(); ii++) {
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CLUE) {
                      if (model.isClueSatisfied(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #00FF00;");
                      } else {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    }
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.WALL) break;
                    Node n =
                        centerPane
                            .getChildren()
                            .get(finalJ + ii * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FFFF00;");
                  }

                  for (int ii = finalI; 0 <= ii; ii--) {
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.CLUE) {
                      if (model.isClueSatisfied(ii, finalJ)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #00FF00;");
                      } else {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(finalJ + ii * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    }
                    if (model.getActivePuzzle().getCellType(ii, finalJ) == CellType.WALL) break;
                    Node n =
                        centerPane
                            .getChildren()
                            .get(finalJ + ii * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FFFF00;");
                  }

                  for (int jj = finalJ; jj < model.getActivePuzzle().getWidth(); jj++) {
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CLUE) {
                      if (model.isClueSatisfied(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #00FF00;");
                      } else {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    }
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.WALL) break;
                    Node n =
                        centerPane
                            .getChildren()
                            .get(jj + finalI * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FFFF00;");
                  }

                  for (int jj = finalJ; 0 <= jj; jj--) {
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.CLUE) {
                      if (model.isClueSatisfied(finalI, jj)) {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #00FF00;");
                      } else {
                        Node n =
                            centerPane
                                .getChildren()
                                .get(jj + finalI * model.getActivePuzzle().getWidth());
                        n.setStyle("-fx-background-color: #000000; -fx-text-fill: white;");
                      }
                      break;
                    }
                    if (model.getActivePuzzle().getCellType(finalI, jj) == CellType.WALL) break;
                    Node n =
                        centerPane
                            .getChildren()
                            .get(jj + finalI * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FFFF00;");
                  }

                  if (bad) {
                    Node n =
                        centerPane
                            .getChildren()
                            .get(finalJ + finalI * model.getActivePuzzle().getWidth());
                    n.setStyle("-fx-background-color: #FF0000;");
                  }
                }
                if (model.isSolved()) {
                  winnerStage.setTitle("Winner winner, chicken dinner!");
                  FlowPane winnerPane = new FlowPane();
                  Text text = new Text("Congratulations! You have solved the puzzle.");
                  winnerPane.getChildren().add(text);
                  winnerPane.setAlignment(Pos.CENTER);
                  Scene winner = new Scene(winnerPane, 400, 400);
                  winnerStage.setResizable(false);
                  winnerStage.setScene(winner);
                  winnerStage.show();
                }
              });
        }
      }
    }
    FlowPane bottomBar = new FlowPane();
    pane.setBottom(bottomBar);

    bottomBar.getChildren().add(prev);
    bottomBar.getChildren().add(next);
    bottomBar.getChildren().add(reset);
    bottomBar.getChildren().add(rand);

    centerPane.setGridLinesVisible(true);

    pane.setCenter(centerPane);

    Scene scene = new Scene(pane, 550, 550);

    stage.setScene(scene);
    stage.setResizable(false);

    stage.show();
  }
}
