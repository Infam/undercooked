public class Item{
	private int cut; //0 = uncut, 1 = cut
	private int cooklvl; //0 = raw, 1 = cooked, 2 = burnt
	private String name;

	public Item(String n){
		cut = 0;
		cooklvl = 0;
		name = n;
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
}
