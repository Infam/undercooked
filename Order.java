import java.util.ArrayList;
import java.util.List;

public abstract class Order {
    protected double price;
    protected double tips;
    protected List<String> items;

    public Order(){}

    //Getters
    public double getPrice(){
        return price;
    }

    public double getTips(){
        return tips;
    }

    //Setters
    public void setPrice(double price){
        this.price = price;
    }

    public void setTips(double tips){
        this.tips = tips;
    }

}

class Burger extends Order{
    public Burger(){
        this.items = new ArrayList<>();
        this.price = 1.00;
    }
}

abstract class OrderDecorator extends Order{
    Order order;
}

class Bread extends OrderDecorator{
    public Bread(Order order, int cut, int cook){
        this.order = order;
        this.price = order.getPrice() + 0.25;
        this.items = order.items;
        items.add("Bread,"+cut+","+cook);
    }
}

class Patty extends OrderDecorator{
    public Patty(Order order, int cut, int cook){
        this.order = order;
        this.price = order.getPrice() + 0.50;
        this.items = order.items;
        items.add("Patty,"+cut+","+cook);
    }
}

class Lettuce extends OrderDecorator{
    public Lettuce(Order order, int cut, int cook){
        this.order = order;
        this.price = order.getPrice() + 0.25;
        this.items = order.items;
        items.add("Lettuce,"+cut+","+cook);
    }
}

class Tomato extends OrderDecorator {
    public Tomato(Order order, int cut, int cook) {
        this.order = order;
        this.price = order.getPrice() + 0.25;
        this.items = order.items;
        items.add("Tomato," + cut + "," + cook);
    }
}

class Cheese extends OrderDecorator {
    public Cheese(Order order, int cut, int cook) {
        this.order = order;
        this.price = order.getPrice() + 0.25;
        this.items = order.items;
        items.add("Cheese," + cut + "," + cook);
    }
}
