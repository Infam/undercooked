public class Store {
    private double register;
    private Order[] ordered;
    private Order[] served;
    private Grid grid;
    private Player player;
    private OrderFactory orderFactory;
    //private Observer[] observers;

    public Store(){
        this.register = 0.0;
        this.grid = new Grid();
        //this.player = new Player();
        this.orderFactory = new OrderFactory();
    }


}
