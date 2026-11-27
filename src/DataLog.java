import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


//stores data from lift to a CSV file 
public class DataLog {
    private final String filePath;

    public DataLog(String filePath) {
        this.filePath = filePath;
    }

// converts lifts to CSV format in the following layout:  name, numSets, minReps, maxReps, increments, lastWeight, lastReps, setsCompleted
    public void save(Lift[] lifts) {
        if (lifts == null) {
            return;
        }

        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Lift lift : lifts) {
                writer.write(
                    escapeCsv(lift.getName()) + "," +
                    lift.getNumSets() + "," +
                    lift.getMinReps() + "," +
                    lift.getMaxReps() + "," +
                    lift.getIncrements() + "," +
                    lift.getLastWeight() + "," +
                    lift.getLastReps() + "," +
                    lift.getSetsCompleted()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not save workout data (" + e.getMessage() + ").");
        }
    }

    
    //loads saved lifts
    // falls back to default lifts if file does nto exist
    public Lift[] loadOrDefault(Lift[] fallbackLifts) {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            return fallbackLifts;
        }

        List<Lift> loaded = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Lift parsed = parseLiftRow(line);
                if (parsed != null) {
                    loaded.add(parsed);
                }
            }
        } catch (IOException e) {
            System.out.println("Warning: Could not read workout data (" + e.getMessage() + "). Using defaults.");
            return fallbackLifts;
        }

        return loaded.isEmpty() ? fallbackLifts : loaded.toArray(new Lift[0]);
    }

    // parses single column of CSV file into Lift object
    // returns null if column fails to be read
    private Lift parseLiftRow(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length != 8) {
            return null;
        }
        try {
            String name = parts[0].trim();
            int numSets = Integer.parseInt(parts[1].trim());
            int minReps = Integer.parseInt(parts[2].trim());
            int maxReps = Integer.parseInt(parts[3].trim());
            double increments = Double.parseDouble(parts[4].trim());
            double lastWeight = Double.parseDouble(parts[5].trim());
            int lastReps = Integer.parseInt(parts[6].trim());
            int setsCompleted = Integer.parseInt(parts[7].trim());

            Lift lift = new Lift(name, numSets, minReps, maxReps, increments);
            lift.restoreState(lastWeight, lastReps, setsCompleted);
            return lift;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    // replaces commas in text fiels with spaces to prevent column shifiting
    private String escapeCsv(String value) {
        return value == null ? "" : value.replace(",", " ");
    }
}
