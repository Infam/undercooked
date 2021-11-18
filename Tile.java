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

	}

	public Tile(int type, int x, int y,String desc){ //dispenser will by type 1
		this.type = type;
		this.dispenserDesc = desc;
		strategy = new Dispenser();
	}

	public Tile(int type, int x, int y, Store store){
		this.type = type;
		this.store = store;
		strategy = new Assembler();
	}


	public void action(){
		strategy.action(this);
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
	public int getType(){
		return type;
	}
	public String getDispDesc(){
		return dispenserDesc;
	}


}
