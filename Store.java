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
        return 100;//TODO: Scoring system

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
}
