import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//=====================Strategy Pattern=======================
public interface Strategy {
    void action(Tile t);
    default void place(Tile t, Player p){
        Item item = p.getItem();
        t.setItem(item);
        p.setItem(null);
    }
    default void pickup(Tile t, Player p){
        Item item = new Item(t.getItem());
        p.setItem(item);
        t.setItem(null);
    }
    default void swap(Tile t, Player p){
        Item pitem = new Item(p.getItem());
        Item titem = new Item(t.getItem());
        p.setItem(titem);
        t.setItem(pitem);
    }
    default void update(Tile t){}

    default void loadImage(Tile t){
        try{
            t.setImage(ImageIO.read(new File("resources/" + this.getClass().getSimpleName() + ".png")));
        } catch (IOException exc){
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}

class Dispenser implements Strategy {
    public void action(Tile t){ //Dispense item
        if(t.getItem() == null){
            t.setItem(new Item(t.getDispDesc()));
        }
    }
    @Override
    public void place(Tile t, Player p){}

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){
        Item item = new Item(t.getItem());
        p.setItem(item);
    }
}

class Floor implements Strategy {
    public void action(Tile t){ //Dispense item
    }

    public void place(Tile t, Player p){}

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){}
}

class CuttingBoard implements Strategy {
    public void action(Tile t){ //Dispense item
        if(t.getItem() != null){
            Item i = t.getItem();
            i.cut();
        }
    }
}

class Grill implements Strategy {
    public void action(Tile t){
    }

    public void update(Tile t){
	    System.out.println("Updating Grill!");
        if(t.getItem() != null){
            Item i = t.getItem();//TODO: Timer for grill
            i.cook();
        }
    }
}

class GarbageDisposal implements Strategy {
    public void action(Tile t){}

    public void update(Tile t){ //Dispense item
	    System.out.println("Updating Garbage!");
        if(t.getItem() != null){
            t.setItem(null);
        }
    }

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){}
}

class Assembler implements Strategy {
    public void action(Tile t){
        t.orderUp();
    }

    public void place(Tile t, Player p){
        Item item = p.getItem();
        t.assembleItem(item);
        p.setItem(null);
    }

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){}
}

class Counter implements Strategy {
    public void action(Tile t){}

}

//https://stackoverflow.com/questions/36894487/java-gui-how-to-move-a-ball-using-wasd-keys
