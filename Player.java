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
        pos[1]--;
    }

    public void moveRight(){
        pos[1]++;
    }

    public void moveUp(){
        pos[0]--;
    }

    public void moveDown(){
        pos[0]++;
    }

    public void interact(){
        facingtile.action();
    }

    public void place(){
        if(item != null){ //if holding someting
            facingtile.holdingPlace(this);
        }
        else {
	    System.out.println("Player is not holding anything. Trying to pick something up...");
            facingtile.emptyPlace(this);
        }

    }

    //Setters
    public void setItem(Item item){
        this.item = item;
    }

    //Getters
    public int[] getPos(){
        return pos;
    }

    public Item getItem(){
        return item;
    }

    public int getFacingDirection(){
        return facingdir;
    }
    public Tile getFacingTile(){return facingtile;}
}
