//import java.util.Scanner;
import java.awt.event.KeyEvent;

public class Player {

    private int[] pos;
    private Tile facingtile;
    private int facingdir; //facing:{0,1,2,3} = {up, down, left, right}
    private Item item;
    private Store store;

    public Player(Store store, int[] startingPos, int facingdir, Tile facing){
        this.pos = startingPos;
        this.facingtile = facing;
        this.facingdir = facingdir;
        this.store = store;
    }

    public void moveLeft(){
        facingdir = 2;
        updateFacingTile();
        if(tileCheck())
            pos[1]--;
    }

    public void moveRight(){
        facingdir = 3;
        updateFacingTile();
        if(tileCheck())
            pos[1]++;
    }

    public void moveUp(){
        facingdir = 0;
        updateFacingTile();
        if(tileCheck())
            pos[0]--;
    }

    public void moveDown(){
        facingdir = 1;
        updateFacingTile();
        if(tileCheck())
            pos[0]++;
    }

    public void updateFacingTile(){
        int x = pos[1];
        int y = pos[0];
        switch (facingdir) {
            case (0) -> y--;
            case (1) -> y++;
            case (2) -> x--;
            case (3) -> x++;
            default -> {
            }
        }
        facingtile = store.getGrid().getTile(x,y);
    }

    public boolean tileCheck(){ //Make sure grid has nonfloor tiles on perimeter
        return facingtile.getType() == 0;
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
