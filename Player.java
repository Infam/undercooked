/*
 * The player, which interacts with tiles and is controlled by the keyboard.
 */
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {

    private int[] pos;
    private Tile facingtile;
    private int facingdir; //facing:{0,1,2,3} = {up, down, left, right}
    private Item item;
    private Store store;

    private BufferedImage image;
    private BufferedImage itemimage;
    private Point point;

    public Player(Store store, int[] startingPos, int facingdir, Tile facing){
        this.pos = startingPos;
        this.facingtile = facing;
        this.facingdir = facingdir;
        this.store = store;
        this.point = new Point(startingPos[1], startingPos[0]);
        loadImage();
    }

    //Render the player on the grid
    private void loadImage(){
        String face;
        switch (facingdir) {
            case (0) -> face = "up";
            case (1) -> face = "down";
            case (2) -> face = "left";
            case (3) -> face = "right";
            default -> face = "down";
        }
        try{
            image = ImageIO.read(new File("resources/cheficon" + face + ".png"));
        } catch (IOException exc){
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }

    //Load image of whatever item the player is holding in front of them
    private void loadItemImage(){
	    String itemName = item.getName();
	    int cooklvl = item.getCook();
	    int cutlvl  = item.getCut();
	    String cut = "";
	    String cook= "";
	    if(cutlvl > 0)
		    cut = "_cut";
	    if(cooklvl == 0)
		    cook = "_raw";
	    else if(cooklvl == 1)
		    cook = "_cooked";
	    else if(cooklvl == 2)
		    cook = "_burnt";
	    try{
		itemimage = ImageIO.read(new File("resources/" + itemName + cut + cook +".png"));
	    } catch (IOException exc){
		System.out.println("Error opening image file for " + itemName + exc.getMessage());
	    }
    }

    //draw the image
    public void draw(Graphics g, ImageObserver observer){
        loadImage();
        g.drawImage(image, point.x * Store.TILE_SIZE, point.y*Store.TILE_SIZE, observer);
        if (item != null){
            loadItemImage();
            g.drawImage(itemimage, facingtile.getPoint().x * Store.TILE_SIZE, facingtile.getPoint().y*Store.TILE_SIZE, observer);
        }
    }

    //Update position based on key press
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT){
            moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT){
            moveRight();
        }
        if (key == KeyEvent.VK_UP){
            moveUp();
        }
        if (key == KeyEvent.VK_DOWN){
            moveDown();
        }
        if (key == KeyEvent.VK_X){
            interact();
        }
        if (key == KeyEvent.VK_C){
            place();
        }
        if (key == KeyEvent.VK_P){
            store.printOrders();
        }
    }

    //Movement Logic
    public void moveLeft(){
        facingdir = 2;
        if(tileCheck()) {
            pos[1]--;
            point.translate(-1, 0);
        updateFacingTile();
        }
    }

    //Movement Logic
    public void moveRight(){
        facingdir = 3;
        if(tileCheck()) {
            pos[1]++;
            point.translate(1, 0);
        updateFacingTile();
        }
    }

    //Movement Logic
    public void moveUp(){
        facingdir = 0;
        if(tileCheck()) {
            pos[0]--;
            point.translate(0, -1);
        updateFacingTile();
        }
    }

    //Movement Logic
    public void moveDown(){
        facingdir = 1;
        if(tileCheck()) {
            pos[0]++;
            point.translate(0, 1);
        updateFacingTile();
        }
    }

    //Update which tile the player is facing, so you know what you're interacting with.
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

    //Make sure grid has nonfloor tiles on perimeter
    public boolean tileCheck(){ 
        updateFacingTile();
        return facingtile.getType() == 0;
    }

    //Interact with the facing tile
    public void interact(){
        facingtile.action();
    }

    //place whatever you're holding on the tile in front of you.
    public void place(){
        if(item != null){ //if holding someting
            facingtile.holdingPlace(this);
        }
        else {
	    //System.out.println("Player is not holding anything. Placing Nothing Down...");
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

    public Point getPoint(){
        return point;
    }

    public Item getItem(){
        return item;
    }

    public int getFacingDirection(){
        return facingdir;
    }
    public Tile getFacingTile(){return facingtile;}
}
