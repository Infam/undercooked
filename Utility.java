import java.util.Random;

public interface Utility {
    static int poisson(int mean){
        Random r = new Random();
        double L = Math.exp(-mean);
        int k = 0;
        double p = 1.0;
        do {
            p = p * r.nextDouble();
            k++;
        }while (p>L);
        return k-1;

    }

    static boolean percentage(double percent){
        Random r = new Random();
        double rng = r.nextDouble();
        if(rng <= percent){
            return true;
        }
        return false;
    }
}
