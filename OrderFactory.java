public class OrderFactory {
    public Order createOrder(){
        Order burger = new Burger();
        for(int i = 0; i<Utility.poisson(4); i++){
            if(Utility.percentage(0.25)){
                burger = addPatty(burger);
            }
            else if(Utility.percentage(0.33)){
                burger = addLettuce(burger);
            }
            else if(Utility.percentage(0.5)){
                burger = addTomato(burger);
            }
            else{
                burger = addCheese(burger);
            }
        }
        return burger;
    }

    private Order addBun(Order order){
        int grilled;
        int cut;
        if(Utility.percentage(0.1)){
            return order;
        }
        if(Utility.percentage(0.44)){
            grilled = 0;
        }
        else{grilled = 1;}
        if(Utility.percentage(0.9)){
            cut = 0;
        }
        else{
            cut = 1;
        }
        return new Bread(order, cut, grilled);
    }

    private Order addLettuce(Order order){
        int grilled;
        int cut;
        if(Utility.percentage(0.9)){
            grilled = 0;
        }
        else{grilled = 1;}
        if(Utility.percentage(0.5)){
            cut = 0;
        }
        else{
            cut = 1;
        }
        return new Lettuce(order, cut, grilled);
    }

    private Order addTomato(Order order){
        int grilled;
        int cut;
        if(Utility.percentage(0.8)){
            grilled = 0;
        }
        else{grilled = 1;}
        if(Utility.percentage(0.1)){
            cut = 0;
        }
        else{
            cut = 1;
        }
        return new Tomato(order, cut, grilled);
    }

    private Order addPatty(Order order){
        int grilled;
        int cut;
        if(Utility.percentage(0.1)){
            grilled = 0;
        }
        else{grilled = 1;}
        if(Utility.percentage(0.4)){
            cut = 0;
        }
        else{
            cut = 1;
        }
        return new Patty(order, cut, grilled);
    }

    private Order addCheese(Order order){
        int grilled;
        int cut;
        if(Utility.percentage(0.3)){
            grilled = 0;
        }
        else{grilled = 1;}
        if(Utility.percentage(0.3)){
            cut = 0;
        }
        else{
            cut = 1;
        }
        return new Patty(order, cut, grilled);
    }


}
