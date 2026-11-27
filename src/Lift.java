/*
Class that represnets a single excerise and tracks progressive overaload
*/
public class Lift {
    private String name;
    private int numSets;
    private int minReps;
    private int maxReps;
    private double increments;
    private double lastWeight;
    private int lastReps;
    private int setsCompleted;

    // contructs a lift with set bounds, rep bounds, and weight porgression.
    public Lift(String name, int numSets, int minReps, int maxReps, double increments) {
        this.name = name;
        this.numSets = numSets;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.increments = increments;
        this.lastWeight = 0.0;
        this.lastReps = 0;
        this.setsCompleted = 0;
    }

    public String getName() {
        return name;
    }

    public int getNumSets() {
        return numSets;
    }

    public int getMinReps() {
        return minReps;
    }

    public int getMaxReps() {
        return maxReps;
    }

    public double getIncrements() {
        return increments;
    }

    public double getLastWeight() {
        return lastWeight;
    }

    public int getLastReps() {
        return lastReps;
    }

    public int getSetsCompleted() {
        return setsCompleted;
    }

    // restores saved workouts  
    public void restoreState(double lastWeight, int lastReps, int setsCompleted) {
        if (lastWeight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (lastReps < 0) {
            throw new IllegalArgumentException("Reps cannot be negative");
        }
        if (setsCompleted < 0) {
            throw new IllegalArgumentException("Sets completed cannot be negative");
        }
        this.lastWeight = lastWeight;
        this.lastReps = lastReps;
        this.setsCompleted = setsCompleted;
    }

    // validates input and stores results of completed workout sessions  
    public void logWorkout(double weight, int lowestRep, int setsCompleted) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight cannot be negative");
        }
        if (lowestRep < 0) {
            throw new IllegalArgumentException("Reps cannot be negative");
        }
        if (setsCompleted <= 0) {
            throw new IllegalArgumentException("Sets completed must be greater than 0");
        }
        this.lastWeight = weight;
        this.lastReps = lowestRep;
        this.setsCompleted = setsCompleted;
    }
    // claculates target for next session by using completed sets to set goal and increment the number of reps for the next session
    public String getNextTarget() {
        if (setsCompleted == 0) {
            return "Start at baseline (" + numSets + "x" + minReps + "-" + maxReps + ")";
        }
        if (setsCompleted >= numSets && lastReps >= maxReps) {
            return "Increase weight to " + (lastWeight + increments) + " lbs for " + numSets + "x" + minReps;
        } else {
            int nextReps = Math.min(lastReps + 1, maxReps);
            return "Keep " + lastWeight + " lbs and aim for " + numSets + "x" + nextReps;
        }
    }
}
