import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Lift[] lifts = {
                new Lift("Bench Press", 3, 5, 8, 5.0),
                new Lift("Squat", 3, 5, 8, 5.0),
                new Lift("Deadlift", 3, 3, 5, 10.0)
        };
        while(true){
            IO.println("\n--- Simple Overload Tracker ---");
            for(int i = 0; i < lifts.length; i++){
                IO.println((i+1) + ". " + lifts[i].getName() + " [" + lifts[i].getNextTarget() + "]");
            }
            IO.println("0: EXIT");
            IO.println("Select lift to log: ");

            int choice = readInt(reader);
            if (choice == 0){
                break;
            }
            if(choice < 1 || choice > lifts.length){
                IO.println("Invalid choice");
                continue;
            }

            Lift selected = lifts[choice -1];

            IO.println("Weight used (lbs)");
            double weight = Double.parseDouble(reader.readLine());

            IO.println("Sets completed: ");
            int sets = Integer.parseInt(reader.readLine());

            IO.println("Lowest reps hit in a set: ");
            int reps = Integer.parseInt(reader.readLine());

            selected.logWorkout(weight, reps, sets);
            IO.println("Updated! next goal: " + selected.getNextTarget());

        }
        reader.close();
    }
}

