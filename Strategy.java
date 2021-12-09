/*
 * The strategies for all different tile types are described here.
*/
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

//=====================Strategy Pattern=======================
public interface Strategy {
    void action(Tile t);
    //Default functions.

    //place the item the player has on the tile
    default void place(Tile t, Player p){
        Item item = p.getItem();
        t.setItem(item);
        p.setItem(null);
    }

    //Pick up the item on the tile the player is facing
    default void pickup(Tile t, Player p){
        Item item = t.getItem();
        p.setItem(item);
        t.setItem(null);
    }

    //Swap the items the player and the tile have
    default void swap(Tile t, Player p){
        Item pitem = new Item(p.getItem());
        Item titem = new Item(t.getItem());
        p.setItem(titem);
        t.setItem(pitem);
    }

    //Used for updating based on a time interval. See Store.java's timer.
    default void update(Tile t){}

    //Load the tile image when asked.
    default void loadImage(Tile t){
        try{
            t.setImage(ImageIO.read(new File("resources/" + this.getClass().getSimpleName() + ".png")));
        } catch (IOException exc){
            System.out.println("Error opening image file: " + exc.getMessage());
        }
    }
}

//Dispenser will let player pick up an item from it.
//It acts as a source for items.
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

//Floor is a walkable tile that doesn't do anything else
class Floor implements Strategy {
    public void action(Tile t){ //Dispense item
    }

    public void place(Tile t, Player p){}

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){}
}

//Cutting board changes the cut state of the item on it.
class CuttingBoard implements Strategy {
    public void action(Tile t){ //Dispense item
	System.out.println("Cutting...");
        if(t.getItem() != null){
            Item i = t.getItem();
            i.cut();
        }
    }
}

//Grill changes the cook state of the item on it.
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

//GarbageDisposal deletes the item on it after a period of time
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

//Assembler is the tile for placing orders and sending them out
class Assembler implements Strategy {
    public void action(Tile t){
        t.orderUp();
    }

    public void place(Tile t, Player p){
        Item item = p.getItem();
        t.assembleItem(item);
        p.setItem(null);
    }

    public void update(Tile t){
	    System.out.println(t.getItems() + "Updating Assembler");
    }

    public void swap(Tile t, Player p){}

    public void pickup(Tile t, Player p){}
}

//Counter lets you place things on it, but not walk on it.
class Counter implements Strategy {
    public void action(Tile t){}

}
