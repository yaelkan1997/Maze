package algorithms.search;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    private final BestFirstSearch best = new BestFirstSearch();

    @org.junit.jupiter.api.Test
    void solve() {
        Maze Test_Maze = new Maze(5, 5);
        int i, j;
        for (i = 0; i < 5; i++)
            for (j = 0; j < 4; j++)
                Test_Maze.setVal(i, j, 1);
        j = 0;
        for (i = 0; i < 5; i++) {
            Test_Maze.setVal(i, j, 0);
            if (i != 4)
                Test_Maze.setVal(i, ++j, 0);
        }
        Test_Maze.setStartPosition(new Position(0, 0));
        Test_Maze.setGoalPosition(new Position(4, 4));

        SearchableMaze searchableMaze = new SearchableMaze(Test_Maze);
        ArrayList<AState> solution = SolveAlgo(searchableMaze, new BestFirstSearch());
        ArrayList<AState> expected = new ArrayList<>();
        for (i = 0; i < 5; i++)
            expected.add(new MazeState(new Position(i, i)));
        assertEquals(expected, solution);
    }
    private static ArrayList<AState> SolveAlgo(ISearchable domain, ISearchingAlgorithm algorithm) {
        Solution solution = algorithm.solve(domain);
        return solution.getSolutionPath();
    }
    @org.junit.jupiter.api.Test
    void InvalidInput() {
        IMazeGenerator IMazeGenerator = new MyMazeGenerator();
        Maze maze = IMazeGenerator.generate(1, -10);
        assertEquals(2, maze.getColumns());
        assertEquals(2, maze.getRows());
    }

    /**
     * Used to check that BestFirstSearch algorithm always return the best solution in compressing to other algorithms
     */
    @org.junit.jupiter.api.Test
    void IsBest(){
        IMazeGenerator mg = new MyMazeGenerator();
        for (int i = 0; i < 10; i++) {
            Maze maze = mg.generate((int)(Math.random() * 18 + 2), (int)(Math.random() * 18 + 2));
            SearchableMaze searchableMaze = new SearchableMaze(maze);
            int bfs = SolveAlgo(searchableMaze, new BreadthFirstSearch()).size();
            int dfs = SolveAlgo(searchableMaze, new DepthFirstSearch()).size();
            int best = SolveAlgo(searchableMaze, new BestFirstSearch()).size();
            assertFalse(best > dfs || best > bfs);
        }
    }
    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals("BestFirstSearch", best.getName());
    }
}