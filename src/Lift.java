public class Lift {
    private String name;
    private int numSets;
    private int minReps;
    private int maxReps;
    private double increments;

    private double lastWeight;
    private int lastReps;
    private int setsCompleted;

    public Lift(String name, int numSets, int minReps, int maxReps, double increments){
        this.name = name;
        this.numSets = numSets;
        this.minReps = minReps;
        this.maxReps = maxReps;
        this.increments = increments;
    }

    public void logWorkout(double weight, int lowestRep, int setsCompleted){
        this.lastWeight = weight;
        this.lastReps = lowestRep;
        this.setsCompleted = setsCompleted;
    }

    public String getNextTarget(){
        if(setsCompleted == 0){
            return "Start with " + numSets + "x" + minReps;
        }
        if(setsCompleted >= numSets && lastReps >=maxReps) {
            return "Increase weight to " + (lastWeight + increments) + "lbs for " + numSets + "x" + minReps;
        }
        return "Keep " + lastWeight + " lbs and aim for  " + numSets + "x" + Math.min(lastReps + 1, maxReps);
    }
    public String getName(){
        return name;
    }
}

