import simulators.StandardSimulator;

public class Main {
    public static void main(String[] args){
        StandardSimulator standardSimulator = new StandardSimulator(4,12,"src/example2.txt");
        standardSimulator.startSimulation();
    }
}