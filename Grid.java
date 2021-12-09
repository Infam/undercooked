/*
 * Grid is the combination class that holds all the tiles together and relates their coordinates.
 * The grid doesn't do much on its own, and essentially just acts like a data structure.
*/
import java.util.List;
import java.util.ArrayList;

public class Grid{
	private int[][] worldgrid;
	private Tile[][] worldtiles;
	private List<String> holding = new ArrayList<String>();
	//private int[] playerpos;
	private Player player;
	private int width;
	private int height;
	private Store store;

	public Grid(Store store){
		this.store = store;

		//The actual grid for the world. This can be editted to change tile types.
		worldgrid = new int[][]
			{{6,10, 11, 12, 13, 14, 2, 2, 6},
			 {6, 0, 0, 0, 0, 0, 0, 0, 6},
			 {6, 0, 0, 0, 0, 0, 0, 0, 5},
			 {6, 0, 0, 0, 0, 0, 0, 0, 6},
			 {6, 0, 0, 0, 0, 0, 0, 0, 6},
			 {6, 6, 6, 6, 6, 3, 3, 4, 6}
			};
		
		worldtiles = new Tile[worldgrid.length][worldgrid[0].length];

		//Create tiles for every location in the matrix.
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
					worldtiles[i][j] = new Tile(worldgrid[i][j], i, j, this.store, holding);
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
