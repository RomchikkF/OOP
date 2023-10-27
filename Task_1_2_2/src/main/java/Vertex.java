public class Vertex<T> {
    T value;

    public Vertex (T newValue){
        value = newValue;
    }

    @Override
    public String toString(){
        return value.toString();
    }
}
