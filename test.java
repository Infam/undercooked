import java.util.List;

public class test {
    public static void main(String[] args) {

        // initializing a map with values: https://stackoverflow.com/questions/6802483/how-to-directly-initialize-a-hashmap-in-a-literal-way
        OrderFactory factory = new OrderFactory();
        List<String> items = factory.createOrder().items;
        for(int i = 0; i<items.size(); i++){
            System.out.println(items.get(i));
        }
    }

}

class test2 {
    public static void main(String[] args){
        Store store = new Store();
        store.newOrder();
        store.newOrder();
        store.newOrder();
        for(int i = 0; i<store.getOrdered().size(); i++){
            //System.out.println(store.getOrdered().get(i).items);
        }
        store.printGrid();
        System.out.println("");
        store.getPlayer().moveDown();
        store.printGrid();
        System.out.println("");
        store.getPlayer().moveLeft();
        store.printGrid();
        System.out.println("");
        store.getPlayer().moveUp();
        store.printGrid();
        System.out.println("");
        store.getPlayer().moveRight();
        store.printGrid();


    }
}
