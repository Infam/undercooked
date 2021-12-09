/*
 * Store is the main class that handles the majority of updating the grid, images, and player control.
*/
import javax.swing.*;
import java.util.ArrayList;
//import java.util.TimerTask;
import java.util.List;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Array;
import java.nio.charset.CoderResult;
import java.util.HashSet;

public class Store extends JPanel implements ActionListener, KeyListener, Subject {
    private double register;
    private List<Order> ordered;
    private List<Order> served;
    private Grid grid;
    private Player player;
    private OrderFactory orderFactory;
    private List<Observer> observers;
    private int time;

    private final int DELAY = 50;
    public static final int TILE_SIZE = 50;
    private Timer timer;


    public Store(){
        initStore();
        setPreferredSize(new Dimension(TILE_SIZE*grid.getHeight(), TILE_SIZE*grid.getWidth()));
        setBackground(new Color(232, 232, 232));

        //item update action
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateGrid();
            }
        };

    	//Initialize clock for update function call
        Timer clock = new Timer(5000, taskPerformer);
        clock.start();

	//Timer for player movement and screen refresh
        timer = new Timer(DELAY, this);
        timer.start();

    }

    //Initialize the store
    public void initStore(){
        register = 0.0;
        grid = new Grid(this);
        initPlayer(2,4,1);
        ordered = new ArrayList<>();
        orderFactory = new OrderFactory();
        served = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    //for every tile of the grid, update them if they have the update function.
    public void updateGrid(){
        for (int row = 0; row< grid.getHeight(); row++){
            for (int col = 0; col < grid.getWidth(); col++){
                int type = grid.getTile(row, col).getType();
                if (type == 3 || type == 4 || type == 5)
                    grid.getTile(row, col).update();
            }
        }
        //System.out.println("Grid Updated!");
        /*System.out.println(ordered);
        for(int i = 0; i < ordered.size(); i++)
        {
            System.out.println(ordered.get(i).items);
        }*/
    }

    @Override
    //if there was an action, repaint the screen.
    public void actionPerformed(ActionEvent e){
        if(Utility.percentage(0.002)){
            newOrder();
        }
        repaint();
    }

    @Override
    //More drawing
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        //populateGrid(g);
        //drawScore(g); //TODO: add draw score

        for (int row = 0; row<grid.getHeight(); row++) {
            for (int col = 0; col < grid.getWidth(); col++) {
                grid.getTile(row, col).draw(g, this);
            }
        }

        player.draw(g, this);

        Toolkit.getDefaultToolkit().sync();
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    // react to key down events
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    // react to key up events
    public void keyReleased(KeyEvent e) {
    }

    //Initialize the player
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
    	//set the facing tile, and call the constructor
        this.player = new Player(this, pos, facing, facingtile);
    }

    //Create a new order
    public void newOrder(){
        if(ordered.size() < 5){
            Order neworder = orderFactory.createOrder();
            System.out.print("New Order: ");
            for(int i = 0; i<neworder.items.size(); i++){
                System.out.print(neworder.items.get(i) + " ");
            }
            System.out.println();
            ordered.add(neworder);
        }
    }

    //Check if the order is correct, and give points based on how right it is
    public float serveOrder(List<String> items){
        List<String> order = ordered.get(0).items;
        ordered.remove(ordered.get(0));
        int score = 100;
        int incorrect = 0;
        if(order.size() != items.size()){
            System.out.println("0 points, at least get the number of ingredients right!");
            return score = 0;
        }
        
        else if(order.equals(items)){
            System.out.println("100 points, great job!");
            return score;
        }

        else{
            List<String> orderIngdetails = new ArrayList<String>();
            List<String> ocutdetails = new ArrayList<String>();
            List<String> ocookdetails = new ArrayList<String>();

            List<String> itemingdetails = new ArrayList<String>();
            List<String> icutdetails = new ArrayList<String>();
            List<String> icookdetails = new ArrayList<String>();

            for(String item : order){
                String[] itemDetails = item.split("\\,");
                orderIngdetails.add(itemDetails[0]);
                ocutdetails.add(itemDetails[1]);
                ocookdetails.add(itemDetails[2]);
            }

            for(String item: items){
                String[] itemDetails = item.split("\\,");
                itemingdetails.add(itemDetails[0]);
                icutdetails.add(itemDetails[1]);
                icookdetails.add(itemDetails[2]);
            }

            HashSet<String> orderSet = new HashSet<String>(orderIngdetails);
            System.out.println(orderSet);
            int totalCorrectIng = 0;
            for(String ingredient : orderSet)
            {
                System.out.println(ingredient);
                int correctNumIng = 0;
                int itemNumIng = 0;
                for(int index = 0; index < items.size(); index++){
                    if(ingredient.equals(orderIngdetails.get(index)))
                    {
                        correctNumIng += 1;
                    }
                    if(ingredient.equals(itemingdetails.get(index)))
                    {
                        itemNumIng += 1;
                    }
                }
                if(itemNumIng >= correctNumIng){
                    totalCorrectIng += correctNumIng;
                }
                else{
                    totalCorrectIng += itemNumIng;
                }
            }
            incorrect += order.size() - totalCorrectIng;
            System.out.println(totalCorrectIng + "," + incorrect);
            
            for(int index = 0; index < order.size(); index++){
                if(!ocutdetails.get(index).equals(icutdetails.get(index)))
                {
                    System.out.println("Cut comparison" + ocutdetails.get(index) + "," + icutdetails.get(index));
                    incorrect += 1;
                }
                //else{
                //    totalCorrectIng += 1;
                //}
                if(!ocookdetails.get(index).equals(icookdetails.get(index))){
                    System.out.println("Cook comparison" + ocookdetails.get(index) + "," + icookdetails.get(index));
                    incorrect += 1;
                }
                //else{
                //    totalCorrectIng += 1;
                //}
            }
            float percent = (100*(order.size()*3-incorrect)/(order.size()*3));
            System.out.println(incorrect + "," + percent);
            return percent;
        }
    }

    //Populate the grid with images. Maps grid to GUI.
    private void populateGrid(Graphics g){
        int type;
        for (int row = 0; row<grid.getHeight(); row++){
            for (int col = 0; col < grid.getWidth(); col++){
                type = grid.getTile(row, col).getType();
                //System.out.println(row + " " + col + " " + type);
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
                if (type == 5)
                    g.setColor(new Color(255, 255, 100));
                if (type == 6)
                    g.setColor(new Color(128, 128, 128));
                g.fillRect(row*TILE_SIZE, col*TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }


    //Observer Pattern
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

    public int getTime(){
        return time;
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

    public void printOrders(){
        for(int j = 0; j<ordered.size(); j++){
            System.out.print("Order " + j + ": ");
            for(int i = 0; i<ordered.get(j).items.size(); i++){
                System.out.print(ordered.get(j).items.get(i) + " ");
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
