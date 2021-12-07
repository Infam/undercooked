import javax.swing.*;

public class View {
    public static void initFrame(){
        JFrame frame = new JFrame("Undercooked");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Store store = new Store();
        frame.add(store);
        frame.addKeyListener(store);


        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                initFrame();
            }
        });
    }
}
