//=====================Strategy Pattern=======================
interface Strategy{
	public void action(Tile t);
}

class Dispenser implements Strategy{
	public void action(Tile t){ //Dispense item
		if(t.getItem() == null){
			t.setItem(new Item(t.getDispDesc()));
		}
	}
}

class Empty implements Strategy{
	public void action(Tile t){ //Dispense item
		return;
	}
}

class CuttingBoard implements Strategy{
	public void action(Tile t){ //Dispense item
		if(t.getItem() != null){
			Item i = t.getItem();
			i.cut();
		}
	}
}

class Grill implements Strategy{
	public void action(Tile t){ //Dispense item
		if(t.getItem() != null){
			Item i = t.getItem();
			i.cook();
		}
	}
}

class Tile{
	private int[] position;
	private int type;
	private String dispenserDesc; //If dispenser, item's description.

	private Item item;

	private Strategy strategy;

	public Tile(int type, int x, int y){
		if (type == 0)
			strategy = new Empty();
		if (type == 0)
			strategy = new Empty();
		if (type == 0)
			strategy = new Empty();
		if (type == 0)
			strategy = new Empty();
		if (type == 0)
			strategy = new Empty();

	}

	public Tile(int type, int x, int y,String desc){ //dispenser
		this.dispenserDesc = desc;
		strategy = new Dispenser();
	}

	public int setItem(Item i){ //1 on success, 0 on failure
		if (item == null){
			item = i;
			return 1;
		}
		return 0;
	}

	public void action(){
		strategy.action(this);
	}

	//-----------------------------------
	//Setters
	public void setPosition(int x,int y){
		position[0] = x;
		position[1] = y;
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
