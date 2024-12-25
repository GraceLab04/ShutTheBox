
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.util.Duration;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.util.ArrayList;
import javafx.scene.image.ImageView;

public class ShutTheBox extends Application {
     public void start(Stage primaryStage) {
        //Scene1 Panes
        BorderPane root = new BorderPane();
        HBox buttons = new HBox(10);
        VBox vbox = new VBox(5);
        Label text = new Label("Play Shut the Box: 1 Player");
        text.setStyle("-fx-font-size:20");
        
        //Populate dice images from files
        Image[] diceImages = new Image[6];
        for(int i = 0; i < 6; i++) {
            diceImages[i] = new Image("DiceImages/" + (i+1) + ".jpeg");
        }
        
        vbox.getChildren().addAll(text, buttons);
        root.setCenter(vbox);
        buttons.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);
        
        //Scene2 Panes
        BorderPane root2 = new BorderPane();
        HBox player1 = new HBox();
        HBox dice = new HBox(20);
        VBox diceLabel = new VBox(35);
        HBox playerIdDiceValue = new HBox(5);
        HBox clickButtons = new HBox(5);
        
        //alignment, labels, buttons
        clickButtons.setAlignment(Pos.CENTER);
        
        Label playerId = new Label("Player Roll: ");
        playerId.setStyle("-fx-text-fill: BLACK; -fx-font-size:30");
         
        TileCalculation validate = new TileCalculation();
        TileCalculation.selectionMade = false;
        
        Label diceValue = new Label("");
        //diceValue.setStyle("-fx-text-fill: RED; -fx-font-size:30");
         diceValue.setStyle("-fx-text-fill: red; -fx-font-size: 30;");
        
        playerIdDiceValue.getChildren().addAll(playerId, diceValue);
        playerIdDiceValue.setAlignment(Pos.CENTER);
        
        Label result = new Label("");
         result.setStyle("-fx-text-fill: BLACK; -fx-font-size:30");
        
        Button submitChoice = new Button("Flip Tiles");
        submitChoice.setPrefSize(150, 50);
        Button resetBoard = new Button("Reset Board");
        resetBoard.setPrefSize(150, 50);
        
        clickButtons.getChildren().addAll(submitChoice, resetBoard);
        
        //add Dice and LaBel to panes
        diceLabel.getChildren().addAll(result, clickButtons, dice, playerIdDiceValue);
        root2.setCenter(diceLabel);
        dice.setAlignment(Pos.CENTER);
        diceLabel.setAlignment(Pos.CENTER);
        
        PlayerBoard player1Board = new PlayerBoard(80, 100);
        Button[] player1Buttons = player1Board.getButtons();
        
        Scene scene1 = new Scene(root, 1400, 850);
        Scene scene2 = new Scene(root2, 1400, 850);
        primaryStage.setScene(scene1);
        
        //create and set dice images for scene1 and dice images for scene2
        ImageView[] imageList = new ImageView[6];
        ImageView[] imageList2 = new ImageView[6];
        Button[] buttonList = new Button[6];
        for(int i = 0; i < 6; i++) {
            imageList[i] = new ImageView(diceImages[i]);
            imageList2[i] = new ImageView(diceImages[i]);
            imageList[i].setFitWidth(75);
            imageList[i].setFitHeight(75);
            imageList2[i].setFitWidth(75);
            imageList2[i].setFitHeight(75);
        }
        
        //Player board selection
        Button button = new Button("", imageList[0]);
        buttonList[0] = (button);
        buttons.getChildren().add(button);
        button.setStyle("-fx-font-size:20");

            //set button action and change scene here
        button.setOnAction(_ -> {
            root2.setTop(player1Board.getPlayerBox());
            player1Board.getPlayerBox().setAlignment(Pos.CENTER);
            primaryStage.setScene(scene2);
        });
        
        //dice buttons for Scene2 - 2 dice
        Button dice1 = new Button("", imageList[0]);
        Button dice2 = new Button("", imageList2[0]);
        
        resetBoard.setOnAction(_ -> {
                    resetBoard(validate, player1Buttons, diceValue, result, imageList[0] , imageList2[0]);
                    primaryStage.setScene(scene2);
                });
        
        dice.getChildren().addAll(dice1, dice2);
        
        //dice animation
        KeyFrame keyframe = new KeyFrame(Duration.millis(100), _ -> {
            dice1.setGraphic(imageList[((int)(Math.random() * (6)))]);
            dice2.setGraphic(imageList2[((int)(Math.random() * (6)))]);
        });
        Timeline timeline = new Timeline(keyframe);
        
        //dice roll animation
        EventHandler<ActionEvent> playAnimation = _ -> {
            timeline.play();
            timeline.setCycleCount(10);
        };
        
        timeline.setOnFinished(_ -> {
            int diceNum = (int) (Math.random() * 6);
            int diceNum2 = (int) (Math.random() * 6);
            diceValue.setText("" + (diceNum + diceNum2 + 2));
            dice1.setGraphic(imageList[diceNum]);
            dice2.setGraphic(imageList2[diceNum2]);
            TileCalculation.selectionMade = false;
            Integer i = Integer.parseInt(diceValue.getText());
            calculateTiles(i, player1Buttons, submitChoice, result);
            playerId.setText("Player Roll: ");
        });
        
        //On Dice Click:
        dice1.setOnAction(playAnimation);
        dice2.setOnAction(playAnimation);
        
        root2.setCenter(diceLabel);
        //top right bottom left - inset locals

        primaryStage.setTitle("ShutTheBox - By: Grace LaBelle");
        primaryStage.show();
    }//END START METHOD
    
    public static void calculateTiles(Integer i, Button[] buttons, Button submitChoice, Label result){
        TileCalculation validate = new TileCalculation(buttons);
        EventHandler<ActionEvent> flipTiles = _ -> {
            boolean isValidRoll = validate.isValid(i, buttons);
            if(isValidRoll){//remove the red border
                for (Button button : buttons) {
                    String style = button.getStyle();
                    if (!style.contains("-fx-background-color: black")) {
                        button.setStyle("-fx-border-color: black");
                    }
                }
            }
            else {
                for (Button button : buttons) {
                    String style = button.getStyle();
                    result.setText(validate.statusLabelText);
                    if (!style.contains("-fx-background-color: black")) {
                        button.setStyle("-fx-border-color: black");
                    }
                }
            }
        };
        
        //change clicked tiles to red border
        //e -> {
        EventHandler<ActionEvent> borderColor = e -> {
            Button clickedButton = (Button)e.getSource();
            String style = clickedButton.getStyle();
            if(!style.contains("-fx-background-color: black")){
                //clickedButton.setStyle("-fx-border-color: red");
                clickedButton.setStyle("-fx-background-color: radial-gradient(focus-distance 0%, center 50% 50%, radius 50%, #f7ff00, #ff8800); -fx-border-color: red;");
            }
            submitChoice.setOnAction(flipTiles); //calculate the red border
    };
            
        //determine value of white tiles
        int totalWhiteTiles = 0;
        ArrayList<Button> whiteTilesRemaining = new ArrayList<>();
        for(int j = 0; j < buttons.length; j++){
                    String style = buttons[j].getStyle();
                    if(!style.contains("-fx-background-color: black")){
                        whiteTilesRemaining.add(buttons[j]);
                        totalWhiteTiles += j + 1;
                    }
                }
        if (whiteTilesRemaining.isEmpty()) {
            result.setText("YOU SHUT THE BOX! CONGRATULATIONS!");
        } else if(!validate.isGameOver(i, whiteTilesRemaining)){
            result.setText("Game Over! Score: " + totalWhiteTiles);
        }
        else {
            for (Button button : buttons) {
                result.setText(validate.statusLabelText);
                button.setOnAction(borderColor);
            }
        }
    }
    
    public static void resetBoard(TileCalculation validate, Button[] buttons, Label diceValue, Label result,
                                  ImageView image, ImageView image2){
        for(int i = 0; i < 9; i++){
            diceValue.setText("");
            result.setText("");
        }
        for(int i = 0; i < 9; i++){
            buttons[i].setStyle("-fx-border-color: black; -fx-background-color: rgb(225, 225, 225);");
        }
        Button dice1 = new Button("", image);
        Scene scene = dice1.getScene();
        TileCalculation.selectionMade = false;
    }
}





