/*
 * Item are the ingredients that are interacted with by tiles and players.
 * Items have different levels of cut'ness and cooked'ness, as well as a name.
 * Aside from that, it is a fairly simply class.
*/
public class Item{
	private boolean inhand = false;
	private int cut; //0 = uncut, 1 = cut
	private int cooklvl; //0 = raw, 1 = cooked, 2 = burnt
	private String name;

	//Common constructor
	public Item(String n){
		cut = 0;
		cooklvl = 0;
		name = n;
	}

	//Copy constructor
	public Item(Item i){
		cut = i.getCut();
		cooklvl = i.getCook();
		name = new String(i.getName());
	}

	public void cut(){
		if(cut == 0)
			cut += 1;
	}

	public void cook(){
		if(cooklvl < 2)
			cooklvl += 1;
	}


	//-----------------------------------
	//Setters
	public void pickedUP(){
		inhand = true;
	}

	public void dropped(){
		inhand = false;
	}
	
	//Getters
	public String getName(){
		return name;
	}

	public int getCut(){
		return cut;
	}

	public int getCook(){
		return cooklvl;
	}

	public boolean getInHand(){
		return inhand;
	}
}
