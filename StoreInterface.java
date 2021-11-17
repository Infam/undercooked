public interface StoreInterface{
    void resetStore();
    void initPlayer(int x, int y, int facing);
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}