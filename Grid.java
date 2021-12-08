public class Grid{
	private int[][] worldgrid;
	private Tile[][] worldtiles;
	//private int[] playerpos;
	private Player player;
	private int width;
	private int height;
	private Store store;

	public Grid(Store store){
		this.store = store;
		worldgrid = new int[][]
			{{10, 11, 12, 13, 14, 2, 2, 6},
			 {6, 0, 0, 0, 0, 0, 0, 6},
			 {6, 0, 0, 0, 0, 0, 0, 5},
			 {6, 0, 0, 0, 0, 0, 0, 6},
			 {6, 0, 0, 0, 0, 0, 0, 6},
			 {6, 6, 6, 6, 3, 3, 4, 6}
			};
		
		worldtiles = new Tile[worldgrid.length][worldgrid[0].length];
		for(int i = 0; i < worldtiles.length; i++){
			for(int j = 0; j < worldtiles[0].length; j++){
				if(worldgrid[i][j] >= 10){
					int disp = worldgrid[i][j] - 10;
					String desc;
					switch (disp) {
						case (0) -> desc = "Lettuce";
						case (1) -> desc = "Tomato";
						case (2) -> desc = "Bun";
						case (3) -> desc = "Patty";
						case (4) -> desc = "Cheese";
						default -> desc = "Patty";
					}
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j, desc);
				}
				else if(worldgrid[i][j] == 5){
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j, this.store);
				}
				else
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j);
			}
		}
		this.width = worldgrid.length;
		this.height = worldgrid[0].length;
	}
	
	//-----------------------------------
	//Setters
	
	//Getters
	/*public int[] getPlayerPos(){
		return player.getPlayerPos();
	}*/
	public Tile getTile(int x, int y){
		return worldtiles[y][x];
	}
	public int[][] getGrid(){
		return worldgrid;
	}

	public int getWidth(){return width;}
	public int getHeight(){return height;}
}
