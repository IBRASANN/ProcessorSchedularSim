import simulators.StandardSimulator;

public class Main {
    public static void main(String[] args){
        StandardSimulator standardSimulator = new StandardSimulator(2,10,"src/example1.txt");
        standardSimulator.startSimulation();
    }
}