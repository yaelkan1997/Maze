package algorithms.search;

import java.util.ArrayList;


public class BreadthFirstSearch extends ASearchingAlgorithm {
    protected ISearchable domain;

    @Override
    public Solution solve(ISearchable domain) {
        this.domain = domain;
        Solution v = new Solution();
        v.setSolution(path());
        return v;
    }

    /**
     * Protected method to be used by inheritance (see: BestFirstSearch)
     *
     * @return Solution to ISearchable domain
     */
    protected ArrayList<AState> path() {
        Solution solution = new Solution();
        AState currentState = domain.getStart();
        //Saves all the nodes we have already visited
        ArrayList<AState> visited = new ArrayList<>();
        //Adding the vertex to the list of all the vertices we have already visited
        visited.add(currentState);
        ArrayList<AState> queue = new ArrayList<>();
        //Inserting a start vertex
        queue.add(currentState);
        //Solution
        mainLoop:
        while (!queue.isEmpty()) {
            currentState = queue.remove(0);
            solution.addState(currentState);
            //If we reached the goal
            if (currentState.equals(domain.getGoal())) { // Reached goal.
                break;
            }
            //Pass over all possible neighbors
            for (AState state : domain.getAllPossibleStates(currentState)) {
                //We will check if we have already visited and if it exists
                if ((!visited.contains(state)) && domain.isIn(state)) {
                    //If we reached the goal
                    if (state.equals(domain.getGoal())) {
                        nodes_Evaluated++;
                        solution.addState(state);
                        break mainLoop;
                    }
                    nodes_Evaluated++;
                    visited.add(state);
                    queue.add(state);
                }
            }

        }
        //Creating clean path to return
        ArrayList<AState> path = solution.getSolutionPath();
        int counter = path.size();
        while (counter >= 3) {
            AState curr = path.get(--counter);
            AState prev = path.get(counter - 1);

            if (!domain.validTraversal(curr, prev, false)) {
                path.remove(prev);
            }
            else if (!domain.validTraversal(curr, prev, true)) {
                path.remove(prev);
                counter++;
            }
        }
        return path;
    }
    @Override
    public String getName() {
        return "BreadthFirstSearch";
    }
}

