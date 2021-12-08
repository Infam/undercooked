import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

public class Store extends JPanel implements ActionListener, KeyListener, Subject {
    private double register;
    private List<Order> ordered;
    private List<Order> served;
    private Grid grid;
    private Player player;
    private OrderFactory orderFactory;
    private List<Observer> observers;

    private final int DELAY = 50;
    public static final int TILE_SIZE = 50;
    private Timer timer;

    public Store(){
        initStore();
        setPreferredSize(new Dimension(TILE_SIZE*grid.getHeight(), TILE_SIZE*grid.getWidth()));
        setBackground(new Color(232, 232, 232));
        timer = new Timer(DELAY, this);
        timer.start();
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


    @Override
    public void actionPerformed(ActionEvent e){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        populateGrid(g);
        //drawScore(g); //TODO: add draw score
        player.draw(g, this);
        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    public void keyPressed(KeyEvent e) {
        // react to key down events
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // react to key up events
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
	    p.interact();
	    p.place();
	    System.out.println("Player is holding: " + p.getItem().getName());
	    s.printGrid();

	    System.out.println("Cutting lettuce at cutting board:");
	    p.moveDown();
	    p.moveRight();
	    p.moveUp();
	    s.printGrid();
	    p.place();
	    p.interact();
	    p.place();
	    System.out.println("Player is holding: " + p.getItem().getName());
	    System.out.println("Item cut level: " + p.getItem().getCut());

	    System.out.println("Grilling lettuce at grill:");
	    p.moveDown();
	    p.moveDown();
	    p.moveDown();
	    s.printGrid();
	    p.place();
	    p.interact();
	    p.place();
	    System.out.println("Player is holding: " + p.getItem().getName());
	    System.out.println("Item grill level: " + p.getItem().getCook());

	    System.out.println("Throwing lettuce away:");
	    p.moveUp();
	    p.moveRight();
	    p.moveDown();
	    s.printGrid();
	    p.place();
	    p.interact();
	    p.place();
	    System.out.println("Player is holding: " + p.getItem());

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
        this.player = new Player(this, pos, facing, facingtile);
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

    private void populateGrid(Graphics g){
        int type = 0;
        for (int row = 0; row<grid.getHeight(); row++){
            for (int col = 0; col < grid.getWidth(); col++){
                type = grid.getTile(row, col).getType();
                System.out.println(row + " " + col + " " + type);
                if (type == 0){
                    if((row+col)%2==1){
                        g.setColor(new Color(214, 214, 214));
                    }
                    else{
                        g.setColor(new Color(232, 232, 232));
                    }
                }
                if (type == 1){
                    g.setColor(new Color(0, 255, 0));
                }
                if (type == 2)
                    g.setColor(new Color(255, 153, 51));
                if (type == 3)
                    g.setColor(new Color(255, 0, 0));
                if (type == 4)
                    g.setColor(new Color(102, 51, 0));
                if (type == 6)
                    g.setColor(new Color(128, 128, 128));
                g.fillRect(row*TILE_SIZE, col*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
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

    public Grid getGrid(){
        return grid;
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
            System.out.println();
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
