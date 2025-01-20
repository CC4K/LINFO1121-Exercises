package graphs;

import java.util.*;

/**
 * Author: Alexis Englebert
 * Context: You are operating a power plant in the new city of Louvain-La-Neuve,
 * but lack plans for the city's electrical network.
 * Your goal is to minimize the cost of electrical wires ensuring the city is connected with just one wire.
 *
 * The method 'minimumSpanningTreeCost' is designed to find the minimum cost to connect all cities in a given electrical network.
 * The network is represented as a graph where the nodes are the buildings, the edges are the possible connections
 * and their associated cost.
 *
 * Example:
 * Given a network with three buildings (nodes) and the cost of wires (edges) between them:
 * 0 - 1 (5), 1 - 2 (10), 0 - 2 (20)
 * The minimum cost to connect all the buildings is 15 (5 + 10).
 *
 * Note: The method assumes that the input graph is connected and the input is valid.
 */
public class Electricity {

    /**
     * @param n       The number of buildings (nodes) in the network.
     * @param edges   A 2D array where each row represents an edge in the form [building1, building2, cost].
     *                The edges are undirected so (building2, building1, cost) is equivalent to (building1, building2, cost).
     * @return       The minimum cost to connect all cities.
     */
    public static int minimumSpanningCost(int n, int [][] edges) {
        //TODO
        HashMap<Integer,List<CostNode>> map = new HashMap<>();
        for(int i = 0 ; i < n ; i++){
            map.put(i , new ArrayList<>());
        }
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            int cost = edge[2];
            map.get(from).add(new CostNode(from, to, cost));
            map.get(to).add(new CostNode(to, from, cost));
        }

        HashSet<Integer> visited = new HashSet<>();
        PriorityQueue<CostNode> pq = new PriorityQueue<>((n1, n2) -> n1.cost - n2.cost);
        pq.add(new CostNode(-1,0,0));
        int MST = 0;
        while(!pq.isEmpty()){
            CostNode current = pq.poll();
            if (visited.contains(current.nodeEnd)) continue;
            visited.add(current.nodeEnd);
            MST += current.cost;
            if(visited.size() == n){
                return MST;
            }
            for (CostNode neighbor : map.get(current.nodeEnd)) {
                if (!visited.contains(neighbor.nodeEnd)) {
                    pq.add(neighbor);
                }
            }
        }
        return -1;
    }

    public static class CostNode{
        int nodeBegin;
        int nodeEnd;
        int cost;

        public CostNode(int nodeBegin,int nodeEnd, int cost){
            this.nodeBegin = nodeBegin;
            this.nodeEnd = nodeEnd;
            this.cost = cost;
        }
    }

}
