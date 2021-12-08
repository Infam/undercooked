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
import java.util.List;

public class Tile{
	private int[] position;
	private int type;
	private int standable;
	private String dispenserDesc; //If dispenser, item's description.
	private List<String> items;
	private Item item;
	private Strategy strategy;
	private Store store;
	private BufferedImage image;
	private BufferedImage itemimage;
	private Point point;

	public Tile(int type, int x, int y){
		this.type = type;
		if (type == 0)
			strategy = new Floor();
		if (type == 2)
			strategy = new CuttingBoard();
		if (type == 3)
			strategy = new Grill();
		if (type == 4)
			strategy = new GarbageDisposal();
		if (type == 6)
			strategy = new Counter();
		position = new int[2];
		position[0] = x;
		position[1] = y;
		point = new Point(y,x);
		loadImage();

	}

	public Tile(int type, int x, int y,String desc){ //dispenser will by type 1
		this.type = type;
		this.dispenserDesc = desc;
		this.item = new Item(desc);
		strategy = new Dispenser();
		point = new Point(y, x);
		loadImage();
	}

	public Tile(int type, int x, int y, Store store){
		this.type = type;
		this.store = store;
		strategy = new Assembler();
		point = new Point(y,x);
		loadImage();
	}


	public void action(){
		strategy.action(this);
	}

	public void update(){
		strategy.update(this);
	}

	public void holdingPlace(Player player) {
		if(item != null){
			strategy.swap(this, player);
		}
		else{
			strategy.place(this, player);
		}
	}

	public void emptyPlace(Player player){
		if(item != null){
			strategy.pickup(this, player);
		}
	}

	public void orderUp(){
		store.serveOrder(items );
	}

	private void loadImage(){
		strategy.loadImage(this);
	}


	//-----------------------------------
	//Setters
	public int setItem(Item i){ //1 on success, 0 on failure
		if (i == null){
			item = null;
			return 1;
		}
		if (item == null){
			item = i;
			return 1;
		}
		return 0;
	}

	public void setImage(BufferedImage image){
		this.image = image;
	}

	public void draw(Graphics g, ImageObserver observer){
		loadImage();
		g.drawImage(image, point.x * Store.TILE_SIZE, point.y*Store.TILE_SIZE, observer);
		if (item != null){
			loadItemImage();
			g.drawImage(itemimage, point.x * Store.TILE_SIZE, point.y*Store.TILE_SIZE, observer);
		}
	}

	private void loadItemImage(){
		String itemName = item.getName();
		int cooklvl = item.getCook();
		int cutlvl  = item.getCut();


		try{
			if(itemName == "Patty"){
				if(cutlvl > 0 && cooklvl == 0)
					itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_raw.png"));
				if(cutlvl > 0 && cooklvl == 1)
					itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_cooked.png"));
				if(cutlvl > 0 && cooklvl == 2)
					itemimage = ImageIO.read(new File("resources/" + itemName + "_cut_burnt.png"));
			}
			else if(cutlvl > 0){
				itemimage = ImageIO.read(new File("resources/" + itemName + "_cut.png"));
			}
			else{
				itemimage = ImageIO.read(new File("resources/" + itemName + ".png"));
			}
		} catch (IOException exc){
			System.out.println("Error opening image file: " + exc.getMessage());
		}

	}


	public void setPosition(int x,int y){
		position[0] = x;
		position[1] = y;
	}

	public void assembleItem(Item item){
		items.add(item.getName()+","+item.getCut()+","+item.getCook());
	}
	
	//Getters
	public Item getItem(){
		return item;
	}
	public int[] getPosition(){
		return position;
	}
	public Point getPoint(){
		return point;
	}

	public int getType(){
		return type;
	}
	public String getDispDesc(){
		return dispenserDesc;
	}

}
