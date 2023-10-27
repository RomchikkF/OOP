import java.util.ArrayList;
import java.util.HashMap;

public abstract class Graph<V, W extends Number & Comparable<W>> {

    HashMap<V, Integer> verticesID = new HashMap<V, Integer>();
    public ArrayList<V> vertices = new ArrayList<>();;
    public int verticesCount = 0;

    public void AddVertex(V vertex){
        // check if such vertex already exists in the graph
        if (vertices.contains(vertex)){
            return;
        }
        // add vertex
        vertices.add(vertex);
        verticesID.put(vertex, verticesCount);
        verticesCount++;
    }

    public void RemoveVertex(V vertex){
        if (!vertices.contains(vertex)){
            return;
        }
        for (int i = 0; i < verticesCount; ++i){
            if (i > verticesID.get(vertex)){
                verticesID.put(vertices.get(i), i - 1);
            }
        }
        verticesID.remove(vertex);
        vertices.remove(vertex);
        verticesCount--;
    }

    public void RemoveVertex(int i){
        if (i < 0 || i >= verticesCount){
            return;
        }
        RemoveVertex(vertices.get(i));
    }

    public V GetVertex(int i){
        if (i < 0 || i >= verticesCount){
            return null;
        }
        return vertices.get(i);
    }

    public ArrayList<V> GetVertices(){
        return vertices;
    }

    abstract public void AddEdge(V vertex1, V vertex2, W weight);
    abstract public void RemoveEdge(int i, int j);
    abstract public void RemoveEdge(V vertex1, V vertex2);
    abstract public Edge<W> GetEdge(int i, int j);
    abstract public Edge<W> GetEdge(V vertex1, V vertex2);
    abstract public ArrayList<Edge<W>> GetIncidentEdges(V vertex);
    abstract public HashMap<V, W> GetDistancesFrom(V start);
    abstract public ArrayList<V> SortedByDistanceFrom(V start);

    public String PrintSortedByDistanceFrom(V start){
        HashMap<V, W> distances = GetDistancesFrom(start);
        ArrayList<V> sortedVertices = SortedByDistanceFrom(start);
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < verticesCount; ++i){
            if (i > 0){
                string.append(", ");
            }
            V vertex = sortedVertices.get(i);
            if (vertex == start || distances.get(vertex) != null){
                string.append(vertex.toString());
                string.append('(');
                if (vertex == start){
                    string.append('0');
                } else {
                    string.append(distances.get(vertex).toString());
                }
                string.append('(');
            } else {
                //unreachable vertices
                break;
            }
        }
        return string.toString();
    }
}
