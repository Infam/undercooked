import java.util.ArrayList;
import java.util.List;

public class Store implements Subject {
    private double register;
    private List<Order> ordered;
    private List<Order> served;
    private Grid grid;
    private Player player;
    private OrderFactory orderFactory;
    private List<Observer> observers;

    public Store(){
        initStore();
    }

    public void initStore(){
        register = 0.0;
        grid = new Grid(this);
        initPlayer(2,4,1);
        ordered = new ArrayList<>();
        orderFactory = new OrderFactory();
        served = new ArrayList<>();
        this.observers = new ArrayList<>();

    }

    public static void main(String[] args){
	    Store s = new Store();
	    s.newOrder();
	    Player p = s.getPlayer();
	    s.printGrid();
	    System.out.println("Moving Up:");
	    p.moveUp();
	    s.printGrid();
	    System.out.println("Moving Down:");
	    p.moveDown();
	    s.printGrid();
	    System.out.println("Moving Left:");
	    p.moveLeft();
	    s.printGrid();
	    System.out.println("Moving Right:");
	    p.moveRight();
	    s.printGrid();
	    System.out.println("Getting Lettuce from dispenser:");
	    p.moveUp();
	    s.printGrid();
	    p.interact();
	    p.place();
	    System.out.println("Player is holding: " + p.getItem().getName());
	    s.printGrid();

    }


    public void initPlayer(int x, int y, int facing){ //facing:{0,1,2,3} = {up, down, left, right}
        int[] pos;
        Tile facingtile;
        pos = new int[2];
        pos[0] = x;
        pos[1] = y;

        facingtile = switch (facing) {
            case (0) -> grid.getTile(x, y - 1);
            case (1) -> grid.getTile(x, y + 1);
            case (2) -> grid.getTile(x - 1, y);
            case (3) -> grid.getTile(x + 1, y);
            default -> grid.getTile(x, y + 1);
        };
        this.player = new Player(pos, facing, facingtile);
    }

    public void newOrder(){
        ordered.add(orderFactory.createOrder());
    }

    public int serveOrder(List<String> items){
        int score = 100;
        List<String> order = ordered.get(0).items;
        if(order.equals(items)){
            return score;
        }
        return 100; //TODO: Scoring system

    }

    public void registerObserver(Observer o){
        observers.add(o);
    }

    public void removeObserver(Observer o){
        observers.remove(o);
    }

    public void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
    //Setters
    public void setRegister(double register){this.register = register;}


    //Getters
    public List<Order> getOrdered(){
        return ordered;
    }

    public Player getPlayer(){
        return player;
    }

    public void printGrid(){
        int[][] grid2d = grid.getGrid();
        int[] ppos = player.getPos();
        //grid2d[ppos[0]][ppos[1]] = 8;

        for(int i = 0; i<grid.getWidth(); i++){
            for(int j = 0; j<grid.getHeight(); j++){
		    if ((i == ppos[0]) && (j == ppos[1]))
                	System.out.print("X\t");
		    else
                	System.out.print(grid2d[i][j]+"\t");
            }
            System.out.println("");
        }
    }

    /*
        private double register;
    private List<Order> ordered;
    private List<Order> served;
    private Grid grid;
    private Player player;
    private OrderFactory orderFactory;
    private List<Observer> observers;
     */
}
