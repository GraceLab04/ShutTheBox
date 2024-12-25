import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PlayerBoard extends HBox{
    private final Button[] buttons;
    private final HBox playerBox;
    
    public PlayerBoard(double prefWidth, double prefHeight){  
        buttons = new Button[9];
        playerBox = new HBox();
        for(int i = 0; i < 9; i++){
            buttons[i] = new Button("" + (i+ 1));
            buttons[i].setPrefSize(80,100);
            buttons[i].setStyle("-fx-border-color: black;");
            playerBox.getChildren().add(buttons[i]);
            buttons[i].setAlignment(Pos.CENTER);
        }
        this.getChildren().add(playerBox);
    }
    
    public Button[] getButtons() {
        return buttons;
    }
    
    public HBox getPlayerBox(){
        return playerBox;
    }
}