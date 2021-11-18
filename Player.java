//import java.util.Scanner;
import java.awt.event.KeyEvent;

public class Player {

    private int[] pos;
    private Tile facingtile;
    private int facingdir;
    private Item item;

    public Player(int[] startingPos, int facingdir, Tile facing){
        this.pos = startingPos;
        this.facingtile = facing;
        this.facingdir = facingdir;
    }

    public void moveLeft(){
        pos[0]--;
    }

    public void moveRight(){
        pos[0]++;
    }

    public void moveUp(){
        pos[1]--;
    }

    public void moveDown(){
        pos[1]++;
    }

    public void interact(){
        facingtile.action();
    }

    public void place(){
        if(item != null){
            facingtile.holdingPlace(this);
        }
        else {
            facingtile.emptyPlace(this);
        }

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
            if(item.getInHand() == true){
                item.dropped();
            }
            else{
                item.pickedUP();
            }
        }
    }

    public void action(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_2){
            facingtile.action();
        }
    }
    //Setters
    public void setItem(Item item){
        this.item = item;
    }

    //Getters
    public int[] getPlayerPos(){
        return pos;
    }

    public Item getItem(){
        return item;
    }

    public int getDirectionFacing(){
        return facingdir;
    }
    public Tile getTileFacing(){return facingtile;}
}
