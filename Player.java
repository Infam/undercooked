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
