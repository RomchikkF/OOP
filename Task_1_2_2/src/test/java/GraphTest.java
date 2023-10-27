import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class GraphTest {

    void testAddVertex(Graph<Character, Integer> graph){
        graph.AddVertex('A');
        graph.AddVertex('A');
        graph.AddVertex('B');
        graph.AddVertex('C');
        assertEquals(new ArrayList<>( Arrays.asList('A', 'B', 'C')), graph.GetVertices());
    }

    void testRemoveVertex(Graph<Character, Integer> graph){
        graph.AddVertex('A');
        graph.AddVertex('B');
        graph.AddVertex('C');
        graph.RemoveVertex('A');
        graph.RemoveVertex('A');
        assertEquals(new ArrayList<>( Arrays.asList('B', 'C')), graph.GetVertices());
    }

    void testAddEdge(Graph<Character, Integer> graph){
        graph.AddVertex('A');
        graph.AddVertex('B');
        graph.AddVertex('C');
        graph.AddEdge('A', 'B', 1);
        graph.AddEdge('B', 'C', 2);
        graph.AddEdge('B', 'C', 3);
        assertEquals(new Edge<>(3).toString(), graph.GetEdge('B', 'C').toString());
    }

    void testRemoveEdge(Graph<Character, Integer> graph){
        graph.AddVertex('A');
        graph.AddVertex('B');
        graph.AddVertex('C');
        graph.RemoveEdge('A', 'B');
        graph.AddEdge('B', 'C', 2);
        graph.AddEdge('B', 'C', 3);
        graph.RemoveEdge('B', 'C');
        assertNull(graph.GetEdge('B', 'C'));
    }

    @Test
    void AdjacencyMatrixAddVTest() {
        testAddVertex(new AdjacencyMatrixGraph<>());
    }

    @Test
    void AdjacencyMatrixRemoveVTest() {
        testRemoveVertex(new AdjacencyMatrixGraph<>());
    }

    @Test
    void AdjacencyMatrixAddETest() {
        testAddEdge(new AdjacencyMatrixGraph<>());
    }

    @Test
    void AdjacencyMatrixRemoveETest() {
        testRemoveEdge(new AdjacencyMatrixGraph<>());
    }
}