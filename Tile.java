import java.util.List;

public class Tile{
	private int[] position;
	private int type;
	private String dispenserDesc; //If dispenser, item's description.
	private List<String> items;
	private Item item;

	private Strategy strategy;

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
		if (type == 5)
			strategy = new Assembler();

	}

	public Tile(int type, int x, int y,String desc){ //dispenser will by type 1
		type = 1;
		this.dispenserDesc = desc;
		strategy = new Dispenser();
	}


	public void action(){
		strategy.action(this);
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

	public void setPosition(int x,int y){
		position[0] = x;
		position[1] = y;
	}

	public void addItem(Item item){
		items.add(item.getName()+","+item.getCut()+","+item.getCook());
	}
	
	//Getters
	public Item getItem(){
		return item;
	}
	public int[] getPosition(){
		return position;
	}
	public int getType(){
		return type;
	}
	public String getDispDesc(){
		return dispenserDesc;
	}
} 
