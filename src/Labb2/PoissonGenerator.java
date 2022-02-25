package Labb2;
import java.util.Random;
import java.lang.Math;

public class PoissonGenerator extends TimerGenerator{
    private Random random;

    public PoissonGenerator(int network, int node, int number, int startSeq) {
        super( network, node, number, 0, startSeq );
        this.random = new Random();
    }

    // Create random generating function
    public int getRandomTime() {
        return random.ints( 0, 1001 ).findFirst().getAsInt();
    }
}
