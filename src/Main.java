import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        DataLog dataLog = new DataLog("data/workouts.csv");

        Lift[] defaultLifts = {
                new Lift("Bench", 3, 5, 8, 5.0),
                new Lift("Squat", 3, 5, 8, 5.0),
                new Lift("Deadlift", 3, 3, 5, 10.0)
        };

        List<Lift> lifts = new ArrayList<>(Arrays.asList(dataLog.loadOrDefault(defaultLifts)));

        while (true) {
            System.out.println("\n--- Progressive Overload Tracker ---");
            for (int i = 0; i < lifts.size(); i++) {
                System.out.println((i + 1) + ". " + lifts.get(i).getName() + " [" + lifts.get(i).getNextTarget() + "]");
            }
            int addOption = lifts.size() + 1;
            int removeOption = lifts.size() + 2;
            System.out.println(addOption + ". + Add Custom Exercise");
            System.out.println(removeOption + ". - Remove an Exercise");
            System.out.println("0: EXIT");
            System.out.print("Select option: ");

            int choice = readInt(reader);
            if (choice == 0) {
                break;
            }

            if (choice == addOption) {
                addCustomLift(reader, lifts, dataLog);
                continue;
            }

            if (choice == removeOption) {
                removeLift(reader, lifts, dataLog);
                continue;
            }

            if (choice < 1 || choice > lifts.size()) {
                System.out.println("Invalid choice.");
                continue;
            }

            Lift selected = lifts.get(choice - 1);

            try {
                System.out.print("Weight used (lbs): ");
                double weight = readDouble(reader);

                System.out.print("Number of sets were completed: ");
                int sets = readInt(reader);

                System.out.print("Lowest reps hit in a set: ");
                int reps = readInt(reader);

                selected.logWorkout(weight, reps, sets);
                dataLog.save(lifts.toArray(new Lift[0]));
                System.out.println("Updated! Next goal: " + selected.getNextTarget());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid entry: " + e.getMessage() + ". Please try again.");
            }
        }
        reader.close();
    }

    private static void addCustomLift(BufferedReader reader, List<Lift> lifts, DataLog dataLog) throws IOException {
        System.out.print("Lift name (e.g., Overhead Press): ");
        String name = reader.readLine();
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Lift name cannot be empty.");
            return;
        }
        name = name.trim().replace(",", " ");

        for (Lift existing : lifts) {
            if (existing.getName().equalsIgnoreCase(name)) {
                System.out.println("An exercise with that name already exists.");
                return;
            }
        }

        System.out.print("Target sets (e.g., 3): ");
        int numSets = readInt(reader);
        if (numSets <= 0) {
            System.out.println("Target sets must be greater than 0.");
            return;
        }

        System.out.print("Minimum reps per set (e.g., 5): ");
        int minReps = readInt(reader);
        if (minReps <= 0) {
            System.out.println("Minimum reps must be greater than 0.");
            return;
        }

        System.out.print("Maximum reps per set (e.g., 8): ");
        int maxReps = readInt(reader);
        if (maxReps < minReps) {
            System.out.println("Maximum reps must be greater than or equal to minimum reps.");
            return;
        }

        System.out.print("Weight increment in lbs (e.g., 5.0): ");
        double increment = readDouble(reader);
        if (increment <= 0) {
            System.out.println("Weight increment must be greater than 0.");
            return;
        }

        Lift customLift = new Lift(name, numSets, minReps, maxReps, increment);
        lifts.add(customLift);
        dataLog.save(lifts.toArray(new Lift[0]));
        System.out.println("Added \"" + name + "\"! (" + customLift.getNextTarget() + ")");
    }

    private static void removeLift(BufferedReader reader, List<Lift> lifts, DataLog dataLog) throws IOException {
        if (lifts.isEmpty()) {
            System.out.println("No lifts to remove.");
            return;
        }
        System.out.print("Enter lift number to remove (or 0 to cancel): ");
        int index = readInt(reader);
        if (index == 0) {
            return;
        }
        if (index < 1 || index > lifts.size()) {
            System.out.println("Invalid lift number.");
            return;
        }
        Lift removed = lifts.remove(index - 1);
        dataLog.save(lifts.toArray(new Lift[0]));
        System.out.println("Removed \"" + removed.getName() + "\".");
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
                System.out.print("Please enter a valid whole number: ");
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
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
