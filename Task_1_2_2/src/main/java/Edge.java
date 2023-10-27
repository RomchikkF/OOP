public class Edge<W extends Number & Comparable<W>> {
    public W weight;

    Edge(W newWeight){
        weight = newWeight;
    }

    @Override
    public String toString(){
        return weight.toString();
    }

    public W AddTo(W sum_weight){
        // It is insanely hard to correctly parametrise weights of the edges in graph without casting it to double
        // If I want to use edges weights that can't be cast to double probably I must write specified subclass for it.
        Double doubleSum = (sum_weight.doubleValue() + weight.doubleValue());
        W newSum = (W) doubleSum;
        return newSum;
    }
}
