import java.io.BufferedInputStream;
import java.util.*;

public class Solution {

    private class Edge {
        private State state;
        private int cost;

        public Edge (State state, int cost) {
            this.state = state;
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        public State getState() {
            return state;
        }
    }

    private int[] capacities;
    private int goal;

    public Solution(int goal, int[] capacities) {
        this.goal = goal;
        this.capacities = capacities;
    }

    public int[] getCapacities() {
        return this.capacities;
    }

    public int getGoal() {
        return this.goal;
    }

    public Integer solve() {
        int [] initialCapacities = new int [capacities.length];
        initialCapacities[0]=capacities[0];
        for(int i=1; i<initialCapacities.length; i++) {
            initialCapacities[i]=0;
        }
        State initialState = new State(this, initialCapacities);

        return findSolution(initialState);
    }

    private Integer findSolution(State initialState) {
        Edge finishingEdge = null;
        PriorityQueue<Edge> toExplore = new PriorityQueue<Edge>(Comparator.comparingInt(Edge::getCost));
        Set<State> explored = new HashSet<State>();
        toExplore.add(new Edge(initialState, 0));
        while(!toExplore.isEmpty()) {
            Edge currentlyExplored = toExplore.poll();
            if(explored.contains(currentlyExplored.getState())) {
                continue;
            }
            explored.add(currentlyExplored.getState());
            if(currentlyExplored.getState().isFinishingState()) {
                //currentlyExplored.getState().print();
                finishingEdge = currentlyExplored;
                break;
            }
            for(State s : currentlyExplored.getState().getNeighbours()) {
                if(!explored.contains(s)) {
                    //System.out.println("Cost to given state: " + currentlyExplored.getCost() + " Cost to children: " + s.getCost());
                    toExplore.add(new Edge(s, currentlyExplored.getCost()+s.getCost()));
                }
            }
        }

        return (finishingEdge == null)?null:finishingEdge.getCost();
    }


    public static void main(String [] args) {

        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        String input = scanner.nextLine();
        Integer [] inputNumbers = Arrays.stream(input.split(" ")).map(Integer::parseInt).toArray(Integer[]::new);

        int amount = inputNumbers[0];
        int [] inputArray = new int [amount];
        for(int i = 0; i< amount; i++) {
            inputArray[i]=inputNumbers[i+1];
        }
        int goal = inputNumbers[inputNumbers.length-1];

        Solution solution = new Solution(goal, inputArray);
        Integer result = solution.solve();
        if(result!=null) {
            System.out.println(result);
        } else {
            System.out.println("impossible");
        }
    }
}
