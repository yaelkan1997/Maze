
package algorithms.search;

import java.util.ArrayList;

public class BestFirstSearch extends BreadthFirstSearch {
    @Override
    public Solution solve(ISearchable domain) {
        this.domain = domain;
        Solution v = new Solution();
        v.setSolution(path());
        //Makes sure Solution v contains a valid path, by removing unnecessary node traversals.
        ArrayList<AState> path = v.getSolutionPath();
        for (int i = 0; i < path.size() - 2; i++) {
            AState curr = path.get(i);
            AState next = path.get(i + 2);
            if(domain.validTraversal(curr, next, false))
                path.remove(i + 1);

        }
        v.setSolution(path);
        return v;
    }

    @Override
    public String getName() {
        return "BestFirstSearch";
    }

}
