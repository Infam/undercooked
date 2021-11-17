//import java.util.Scanner;
import java.awt.event.KeyEvent;

public class Player {

    private int[] pos;
    private Tile facing;
    private Item food;

    public Player(int[] startingPos, Tile facing){
        this.pos = startingPos;
        this.facing = facing;
    }
    
    public void movePos(KeyEvent e) {
        int c = e.getKeyCode();
        if(c == KeyEvent.VK_UP){                
            pos[1]--;
        } 
        else if(c == KeyEvent.VK_DOWN){                
            pos[1]++;   
        } 
        else if(c == KeyEvent.VK_LEFT){                
            pos[0]--;   
        } 
        else if(c == KeyEvent.VK_RIGHT){                
            pos[0]++;   
        }
    }

    public void grabOrPlace(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_1){
            if(food.getInHand() == true){
                food.dropped();
            }
            else{
                food.pickedUP();
            }
        }
    }

    public void action(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_2){
            facing.action();
        }
    }

    //Getters
    public int[] getPlayerPos(){
        return pos;
    }

    public Tile getDirectionFacing(){
        return facing;
    }
}
