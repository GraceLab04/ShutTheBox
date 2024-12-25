import java.util.ArrayList;
import javafx.scene.control.Button;

public class TileCalculation{
    public static boolean selectionMade = false;
    public String statusLabelText = "" ;
    private static Button[] buttons = new Button[9];

    public TileCalculation(Button[] buttons){
        TileCalculation.buttons = buttons;
    }
    
    public TileCalculation(String statusLabelText){
        this.statusLabelText = statusLabelText;
    }
    
    public TileCalculation(){
    }

    public static Button[] getButtons() {
        return buttons;
    }

    public static void setButtons(Button[] buttons) {
        TileCalculation.buttons = buttons;
    }

    public boolean isValid(int valueOfDice, Button[] buttons){
        int sumOfClickedTiles = 0;
        int clickedCount = 0;
        ArrayList<Button> clickedButtons = new ArrayList<>();
        int buttonValueRemaining = 0;
        
        //total of selected tiles
        for(int i = 0; i < buttons.length; i++){
            String style = buttons[i].getStyle();
            if(style.contains("-fx-background-color: radial-gradient(focus-distance 0%, center 50% 50%, radius 50%, #f7ff00, #ff8800); -fx-border-color: red;")){
                sumOfClickedTiles += i + 1;
                clickedButtons.add(buttons[i]); //list of selected buttons
            }
        }
        
        //checking if selection is equal to dice valueOfDice
        if(sumOfClickedTiles == valueOfDice && !selectionMade){
            //if equal, change to black
            for (Button clickedButton : clickedButtons) {
                clickedButton.setStyle("-fx-background-color: black");
            }
            selectionMade = true; //prevent further selection with same dice roll
        
            //if no selected tiles are turned
            for (Button button : buttons) {
                String style = button.getStyle();
                if (!style.contains("-fx-background-color: black")) {
                    statusLabelText = "Roll Again";
                    return false;
                }
            }
            statusLabelText = "Roll Again";
            return true;
        }
        statusLabelText = "Incorrect Selection";
        return false;
    }
    
    public boolean isGameOver(int diceRoll, ArrayList<Button> whiteTilesRemaining) {
        return combinations(diceRoll, whiteTilesRemaining, 0);
    }
    
    private boolean combinations(int diceRoll, ArrayList<Button> whiteTilesRemaining,
        int index){
        //recursion
        //base case - valid selection is found
        if(diceRoll == 0){
            return true;
        }
        
        //base case - no valid selection is found
        if(diceRoll < 0 || index >= whiteTilesRemaining.size()){
            return false;
        }
        Button tile = whiteTilesRemaining.get(index);
        
        //return true if valid tile options remain
        if(combinations(diceRoll - Integer.parseInt(tile.getText()), 
            whiteTilesRemaining, index + 1)){
                return true;
        }
        //move to the next time for evaluation
        return combinations(diceRoll, whiteTilesRemaining, index+1);
    }

    public int getButtonValueRemaining() {
        return 0;
    }
}
