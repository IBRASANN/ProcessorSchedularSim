package simulators;

import tasks.Task;

import java.io.File;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulator {
    List<Task> allTasks;

    public void readTasksFromFile() throws FileNotFoundException {

        List<List<String>> tasks = new ArrayList<>();
        int numOfTasks = 0;
        Scanner scanner = new Scanner(new File("src/example1"));
        if(scanner.hasNextLine())numOfTasks = Integer.parseInt(scanner.nextLine());
        while (scanner.hasNextLine()) {
            tasks.add(List.of(scanner.nextLine().split(" ")));
        }
        scanner.close();

        for(int i = 0; i < numOfTasks; i++){
            try{
                allTasks.add(new Task(Integer.parseInt(tasks.get(i).get(0)), Integer.parseInt(tasks.get(i).get(1)), Integer.parseInt(tasks.get(i).get(2))));
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startSimulation(){

    }
}
