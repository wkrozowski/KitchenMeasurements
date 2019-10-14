import java.util.*;

public class State {
    private int[] values;
    private List<State> neighbours;
    private Solution solution;
    private int cost;

    public State(Solution solution, int [] values, int cost) {
        this.values = values;
        this.neighbours = new ArrayList<State>();
        this.solution = solution;
        this.cost = cost;
    }

    public State(Solution solution, int [] values) {
        this(solution, values, 0);
    }

    public void print() {
        for(int i = 0; i<values.length; i++) {
            System.out.print(values[i] + ", ");
        }
        System.out.println();
    }

    public boolean equals(Object o){
        return Arrays.equals(this.values, ((State) o).values);
    }

    public int hashCode() {
        return Arrays.hashCode(this.values);
    }

    public boolean isFinishingState() {
        return (values[0]==solution.getGoal());
    }


    public int getCost() {
        return cost;
    }
    public List<State> getNeighbours() {
        for(int i = 0 ; i < values.length; i++) {
            for(int j = i+1; j < values.length; j++) {
                createNewState(i, j);
                createNewState(j, i);
            }
        }
        return neighbours;
    }

    private void createNewState(int i, int j) {
        int[] newArray;
        newArray = Arrays.copyOf(values, values.length);
        int freeSpace = solution.getCapacities()[j]-values[j];
        int cost;
        if(freeSpace>0) {
            if(freeSpace<=values[i]) {
                //We have more in i than free space in j
                cost=freeSpace;
                newArray[i]-=freeSpace;
                newArray[j]+=freeSpace;
            } else {
                cost=newArray[i];
                newArray[j]+=newArray[i];
                newArray[i] = 0;
            }
        } else {
            cost = 0;
        }
        neighbours.add(new State(solution, newArray, cost));

    }
}
