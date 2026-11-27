import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        DataLog dataLog = new DataLog("data/workouts.csv");

        Lift[] defaultLifts = {
                new Lift("Bench Press", 3, 5, 8, 5.0),
                new Lift("Squat", 3, 5, 8, 5.0),
                new Lift("Deadlift", 3, 3, 5, 10.0)
        };

        Lift[] lifts = dataLog.loadOrDefault(defaultLifts);

        while (true) {
            System.out.println("\n--- Simple Overload Tracker ---");
            for (int i = 0; i < lifts.length; i++) {
                System.out.println((i + 1) + ". " + lifts[i].getName() + " [" + lifts[i].getNextTarget() + "]");
            }
            System.out.println("0: EXIT");
            System.out.println("Select lift to log: ");

            int choice = readInt(reader);
            if (choice == 0) {
                break;
            }
            if (choice < 1 || choice > lifts.length) {
                System.out.println("Invalid choice");
                continue;
            }

            Lift selected = lifts[choice - 1];

            try {
                System.out.println("Weight used (lbs)");
                double weight = readDouble(reader);

                System.out.println("Sets completed: ");
                int sets = readInt(reader);

                System.out.println("Lowest reps hit in a set: ");
                int reps = readInt(reader);

                selected.logWorkout(weight, reps, sets);
                dataLog.save(lifts);
                System.out.println("Updated! next goal: " + selected.getNextTarget());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid entry: " + e.getMessage() + ". Please try again.");
            }
        }
        reader.close();
    }

    private static int readInt(BufferedReader reader) throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return 0;
            }
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number: ");
            }
        }
    }

    private static double readDouble(BufferedReader reader) throws IOException {
        while (true) {
            String line = reader.readLine();
            if (line == null) {
                return 0.0;
            }
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number: ");
            }
        }
    }
}
