//=====================Strategy Pattern=======================
public interface Strategy{
    public void action(Tile t);
}

class Dispenser implements Strategy{
    public void action(Tile t){ //Dispense item
        if(t.getItem() == null){
            t.setItem(new Item(t.getDispDesc()));
        }
    }
}

class Floor implements Strategy{
    public void action(Tile t){ //Dispense item
        return;
    }
}

class CuttingBoard implements Strategy{
    public void action(Tile t){ //Dispense item
        if(t.getItem() != null){
            Item i = t.getItem();
            i.cut();
        }
    }
}

class Grill implements Strategy{
    public void action(Tile t){ //Dispense item
        if(t.getItem() != null){
            Item i = t.getItem();
            i.cook();
        }
    }
}

class GarbageDisposal implements Strategy{
    public void action(Tile t){ //Dispense item
        if(t.getItem() != null){
            t.setItem(null);
        }
    }
}

class Assembler implements Strategy{
    public void action(Tile t){
        if(t.getItem() != null){
            t.addItem(t.getItem());
        }
    }
}

//https://stackoverflow.com/questions/36894487/java-gui-how-to-move-a-ball-using-wasd-keys