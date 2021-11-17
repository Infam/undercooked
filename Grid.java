public class Grid{
	private int[][] worldgrid;
	private Tile[][] worldtiles;
	//private int[] playerpos;
	private Player player;

	public Grid(){
		worldgrid = new int[][]
			{{1, 1, 1, 1, 1, 2, 2, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 0, 0, 0},
			 {0, 0, 0, 0, 0, 3, 3, 4}
			};
		
		worldtiles = new Tile[worldgrid.length][worldgrid[0].length];
		for(int i = 0; i < worldtiles.length; i++){
			for(int j = 0; j < worldtiles[0].length; j++){
				if(worldgrid[i][j] == 1){
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j, "lettuce");
				}
				else
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j);
			}
		}

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
}
