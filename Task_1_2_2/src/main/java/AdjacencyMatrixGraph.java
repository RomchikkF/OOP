import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class AdjacencyMatrixGraph<V, W extends Number & Comparable<W>> extends Graph<V, W>{
    
    ArrayList<ArrayList<Edge<W>>> adjacencyMatrix = new ArrayList<>();

    @Override
    public void AddVertex(V vertex){
        if (vertices.contains(vertex)){
            return;
        }
        super.AddVertex(vertex);
        if (adjacencyMatrix == null) {
            adjacencyMatrix = new ArrayList<>();
        }
        for (int i = 0; i < verticesCount - 1; ++i){
            adjacencyMatrix.get(i).add(null);
        }
        adjacencyMatrix.add(new ArrayList<Edge<W>>(verticesCount));
    }

    @Override
    public void RemoveVertex(V vertex){
        if (!vertices.contains(vertex)){
            return;
        }
        ArrayList<ArrayList<Edge<W>>> newAdjacencyMatrix = new ArrayList<>();
        for (int i = 0; i < verticesCount; ++i) {
            newAdjacencyMatrix.add(new ArrayList<Edge<W>>(verticesCount));
        }
        HashMap<V, Integer> oldVertexID = new HashMap<V, Integer>();
        for (int i = 0; i < verticesCount; ++i){
            if (vertices.get(i) != vertex){
                oldVertexID.put(vertices.get(i), i);
            }
        }
        super.RemoveVertex(vertex);
        for (int i = 0; i < verticesCount; ++i) {
            for (int j = 0; j < verticesCount; ++j) {
                Edge<W> edge = adjacencyMatrix.get(oldVertexID.get(vertices.get(i))).get(oldVertexID.get(vertices.get(j)));
                if (edge != null){
                    newAdjacencyMatrix.get(i).set(j, edge);
                }
            }
        }
    }

    @Override
    public void AddEdge(V vertex1, V vertex2, W weight){
        // rewrites old value if it existed
        adjacencyMatrix.get(verticesID.get(vertex1)).set(verticesID.get(vertex2), new Edge<W>(weight));
    }

    @Override
    public void RemoveEdge(int i, int j){
        if (i < 0 || i >= verticesCount || j < 0 || j >= verticesCount){
            return;
        }
        adjacencyMatrix.get(i).set(j, null);
    }

    @Override
    public void RemoveEdge(V vertex1, V vertex2){
        if (!verticesID.containsKey(vertex1) || !verticesID.containsKey(vertex2)){
            return;
        }
        RemoveEdge(verticesID.get(vertex1), verticesID.get(vertex2));
    }


    @Override
    public Edge<W> GetEdge(int i, int j) {
        return adjacencyMatrix.get(i).get(j);
    }

    @Override
    public Edge<W> GetEdge(V vertex1, V vertex2) {
        return GetEdge(verticesID.get(vertex1), verticesID.get(vertex2));
    }


    @Override
    public ArrayList<Edge<W>> GetIncidentEdges(V vertex){
        ArrayList<Edge<W>> edges = new ArrayList<>();
        ArrayList<Edge<W>> adjacencyList = adjacencyMatrix.get(verticesID.get(vertex));
        for (int i = 0; i < verticesCount; ++i){
            if (adjacencyList.get(i) != null){
                edges.add(adjacencyList.get(i));
            }
        }
        return edges;
    }

    @Override
    public HashMap<V, W> GetDistancesFrom(V start){
        HashMap<V, W> distances = new HashMap<>();
        HashSet<V> visited = new HashSet<>();
        visited.add(start);
        for (int i = 0; i < verticesCount; ++i){
            V vertex = vertices.get(i);
            Edge<W> edge = GetEdge(start, vertex);
            if (edge != null){
                visited.add(vertex);
                W dist = distances.get(vertex);
                if (dist == null || dist.compareTo(edge.weight) > 0){
                    distances.put(vertex, edge.weight);
                }
            }
        }
        for (int i = 0; i < verticesCount; ++i){
            V closestUnvisited = null;
            W minimalDist = null;
            for (int j = 0; j < verticesCount; ++j){
                V vertex = vertices.get(j);
                if (visited.contains(vertex) || !distances.containsKey(vertex)){
                    continue;
                }
                W dist = distances.get(vertex);
                if (minimalDist == null || minimalDist.compareTo(dist) > 0){
                    closestUnvisited = vertex;
                    minimalDist = dist;
                }
            }
            visited.add(closestUnvisited);
            for (int j = 0; j < verticesCount; ++j){
                Edge<W> edge = GetEdge(closestUnvisited, vertices.get(i));
                if (edge == null) {
                    continue;
                }
                V vertex = vertices.get(j);
                W new_dist = edge.AddTo(minimalDist);
                W dist = distances.get(vertex);
                if (dist == null || dist.compareTo(new_dist) > 0){
                    distances.put(vertex, new_dist);
                }
            }
        }
        return distances;
    }

    @Override
    public ArrayList<V> SortedByDistanceFrom(V start){
        ArrayList<V> sortedVertices = new ArrayList<>();
        HashMap<V, W> distances = GetDistancesFrom(start);
        HashSet<V> added = new HashSet<>();
        added.add(start);
        sortedVertices.add(start);
        for (int i = 0; i < verticesCount; ++i){
            V closestUnvisited = null;
            W minimalDist = null;
            for (int j = 0; j < verticesCount; ++j){
                V vertex = vertices.get(j);
                if (added.contains(vertex) || !distances.containsKey(vertex)){
                    continue;
                }
                W dist = distances.get(vertex);
                if (minimalDist == null || minimalDist.compareTo(dist) > 0){
                    closestUnvisited = vertex;
                    minimalDist = dist;
                }
            }
            added.add(closestUnvisited);
            sortedVertices.add(closestUnvisited);
        }
        return  sortedVertices;
    }

    public ArrayList<ArrayList<Edge<W>>> getAdjacencyMatrix(){
        return adjacencyMatrix;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < verticesCount; ++i) {
            string.append(vertices.get(i).toString());
            string.append(": ");
            for (int j = 0; j < verticesCount; ++j) {
                string.append(adjacencyMatrix.get(i).get(j).toString());
                string.append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }
}
