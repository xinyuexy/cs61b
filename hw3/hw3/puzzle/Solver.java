package hw3.puzzle;
/**
 * refer: https://github.com/azaar/CS61B/blob/master/hw4/hw4/puzzle/Solver.java
 */

import edu.princeton.cs.algs4.MinPQ;

import java.util.Stack;

public class Solver {

    private SearchNode resultNode;
    private Stack<WorldState> solution;

    private class SearchNode implements Comparable<SearchNode> {
        private WorldState word;    //最好都是用其接口类型
        private int moves;
        private SearchNode parent;
        /** 当前点总距离(距目标距离+起始点到该点距离) */
        private int priorityManhattan;

        public SearchNode(WorldState word) {
            this(word, null);
        }

        public SearchNode(WorldState word, SearchNode parent) {
            this.word = word;
            this.parent = parent;

            if (parent == null) {
                this.moves = 0;
            } else {
                this.moves = parent.moves + 1;
            }

            priorityManhattan = word.estimatedDistanceToGoal() + moves;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priorityManhattan - that.priorityManhattan;
        }
    }

    /**
    * Constructor which solves the puzzle, computing
    * everything necessary for moves() and solution() to
    * not have to solve the problem again. Solves the
    * puzzle using the A* algorithm. Assumes a solution exists
    */
    public Solver(WorldState initial) {
        solution = new Stack<>();
        /**因为SearchNode实现了compareTo, 优先队列会自动使用该方法决定优先级 */
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode currentNode = new SearchNode(initial);
        WorldState prevWord;
        pq.insert(currentNode);

        while (!pq.isEmpty() && !currentNode.word.isGoal()) {
            currentNode = pq.delMin();

            if (currentNode.parent == null) {
                prevWord = null;
            } else {
                prevWord = currentNode.parent.word;
            }

            for (WorldState word: currentNode.word.neighbors()) {
                if (!word.equals(prevWord)) {
                    pq.insert(new SearchNode(word, currentNode));
                }
            }
        }

        resultNode = currentNode;
        solution.push(currentNode.word);
        while (currentNode.parent != null) {
            currentNode = currentNode.parent;
            solution.push(currentNode.word);
        }
    }

    public int moves() {
        return resultNode.moves;
    }
    public Iterable<WorldState> solution() {
        Stack<WorldState> path = new Stack<>();
        while (!solution.isEmpty()) {
            path.push(solution.pop());
        }
        return path;
    }
}
