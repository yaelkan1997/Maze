
package algorithms.search;

import java.util.ArrayList;
import java.util.Hashtable;

public class DepthFirstSearch extends ASearchingAlgorithm {
    /**
     * Solves the maze using a DFS algorithm
     */
    @Override
    public Solution solve(ISearchable domain) {
        Solution solution = new Solution();
        //The current situation
        AState stateNow = null;
        //The solution I will return
        ArrayList<AState> source = new ArrayList<>();
        //Saves everyone I visited, that is, makes the nood gray
        ArrayList<AState> visited = new ArrayList<>();
        //In order to find the vertex quickly we will use the Hash table key is the source
        Hashtable<AState, AState> prevHashtable = new Hashtable<>();
        source.add(domain.getStart());
        //Here we do the dfs search itself
        while (!source.isEmpty()) {
            //the current situation
            stateNow = source.get(source.size() - 1);
            //Checks if it is in start state
            if (stateNow.equals(domain.getGoal())) {
                break;
            }
            source.remove(source.size() - 1);
            // Goes through all the states that the current state currently has
            for (AState state : domain.getAllPossibleStates(stateNow)) {
                //checks if it has been visited already
                if (!(visited.contains(state)) && domain.isIn(state)) {
                    visited.add(state);
                    source.add(state);
                    prevHashtable.put(state, stateNow);
                }
            }
            nodes_Evaluated++;
        }
        ArrayList<AState> upsidedown= path(domain, prevHashtable, stateNow);
        //Create the solution by going through the list from end to stateNowbeginning
        while (!upsidedown.isEmpty()) {
            solution.addState(upsidedown.remove(upsidedown.size() - 1));
        }
        return solution;
    }

    public ArrayList<AState> path(ISearchable domain, Hashtable<AState, AState> prevHashtable, AState stateNow){
        ArrayList<AState> upsidedown = new ArrayList<>();
        upsidedown.add(domain.getGoal());
        stateNow = domain.getGoal();
        AState prev_state;
        //finding the previous one
        while (!(stateNow.equals(domain.getStart()))) {

            for (AState key : prevHashtable.keySet()) {
                if (key.equals(stateNow)) {
                    stateNow = key;
                    break;
                }
            }
            //get the previous one
            prev_state = prevHashtable.get(stateNow);
            upsidedown.add(stateNow);
            stateNow = prev_state;
        }
        upsidedown.add(domain.getStart());
        upsidedown.remove(0);
        return upsidedown;

    }

    @Override
    public String getName() {
        return "DepthFirstSearch";
    }

}
