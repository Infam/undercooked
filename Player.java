//import java.util.Scanner;
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

    private void loadItemImage(){
	    String itemName = item.getName();
	    int cooklvl = item.getCook();
	    int cutlvl  = item.getCut();


	    try{
		if(itemName.equals("Patty")){
			    if(cutlvl > 0 && cooklvl == 0)
				    itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_raw.png"));
			    else if(cutlvl > 0 && cooklvl == 1)
				    itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_cooked.png"));
			    else if(cutlvl > 0 && cooklvl == 2)
				    itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_burnt.png"));
				else{
					itemimage = ImageIO.read(new File("resources/" + itemName + ".png"));
				}
		    }
		    else if(cutlvl > 0){
			    itemimage = ImageIO.read(new File("resources/" + itemName + "_cut.png"));
		    }
		    else{
			    itemimage = ImageIO.read(new File("resources/" + itemName + ".png"));
		    }
	    } catch (IOException exc){
		System.out.println("Error opening image file for " + itemName + exc.getMessage());
	    }

    }

    public void draw(Graphics g, ImageObserver observer){
        loadImage();
        g.drawImage(image, point.x * Store.TILE_SIZE, point.y*Store.TILE_SIZE, observer);
        if (item != null){
            loadItemImage();
            g.drawImage(itemimage, facingtile.getPoint().x * Store.TILE_SIZE, facingtile.getPoint().y*Store.TILE_SIZE, observer);
        }
    }

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
    }

    public void moveLeft(){
        facingdir = 2;
        if(tileCheck()) {
            pos[1]--;
            point.translate(-1, 0);
        updateFacingTile();
        }
    }

    public void moveRight(){
        facingdir = 3;
        if(tileCheck()) {
            pos[1]++;
            point.translate(1, 0);
        updateFacingTile();
        }
    }

    public void moveUp(){
        facingdir = 0;
        if(tileCheck()) {
            pos[0]--;
            point.translate(0, -1);
        updateFacingTile();
        }
    }

    public void moveDown(){
        facingdir = 1;
        if(tileCheck()) {
            pos[0]++;
            point.translate(0, 1);
        updateFacingTile();
        }
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
        updateFacingTile();
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
	    System.out.println("Player is not holding anything. Placing Nothing Down...");
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
