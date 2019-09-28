package edu.amd.spbstu.jumper;

import java.util.ArrayList;

class HamiltonianCycle
{
    private int V = 0;
    private int[] path;
    private int[][] graph;
    private ArrayList<Block> blocks;
    private static int counter = 0;
    private int start_position = 0;

    public void rebuildGraph() {
        blocks = AppConstants.getGameEngine().getBlocksAlive();

        V = blocks.size();
        graph = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                int dist = Block.dist(AppConstants.getGameEngine().getBlocks(), blocks.get(i), blocks.get(j));
                if (dist != Integer.MAX_VALUE)
                    graph[i][j] = 1;
                else
                    graph[i][j] = 0;

            }
        }

        graph[V - 1][0] = 1; // make cycle
    }

    public HamiltonianCycle() {
        rebuildGraph();
    }

    public int[] findHamiltonianPath() {
        rebuildGraph();
        System.out.println("START POS IS");
        System.out.println(start_position);
        hamCycle(graph, start_position);
        return path;
    }

    private boolean isSafe(int v, int graph[][], int path[], int pos)
    {
        if (graph[path[pos - 1]][v] == 0)
            return false;

        for (int i = 0; i < pos; i++)
            if (path[i] == v)
                return false;

        return true;
    }


    private boolean hamCycleUtil(int graph[][], int path[], int pos)
    {
        //System.out.println(counter++);
        //if (counter > 10000) {
            //System.out.println("BREAK");
            //return false;
        //}
        if (pos == V)
        {
            if (graph[path[pos - 1]][path[0]] == 1) {
                return true;
            }
            else {
                return false;
            }
        }

        for (int v = 1; v < V; v++)
        {
            if (isSafe(v, graph, path, pos))
            {
                path[pos] = v;

                if (hamCycleUtil(graph, path, pos + 1)) {
                    return true;
                }
                path[pos] = -1;
            }
        }

        return false;
    }

    private int hamCycle(int graph[][], int start_position)
    {
        path = new int[V];
        for (int i = 0; i < V; i++)
            path[i] = -1;

        path[0] = start_position;
        if (!hamCycleUtil(graph, path, 1))
        {
            printSolution(path);
            System.out.println("\nSolution does not exist");
            return 0;
        }

        printSolution(path);
        return 1;
    }

    private void printSolution(int path[])
    {
        System.out.println("Solution Exists: Following" +
                " is one Hamiltonian Cycle");
        for (int i = 0; i < V; i++)
            System.out.print(" " + path[i] + " ");

        System.out.println(" " + path[0] + " ");
    }

}
