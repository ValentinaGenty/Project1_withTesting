package CustomArrayList;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Arrays;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomArrayList<T> implements CustomList<T> {

    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;
    private Object elements[];

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    public void add(T element) {
        if (size == elements.length){
            resize();
        }
        elements[size++] = element;
    }

    private void resize(){
        int newSize = elements.length * 2;
        elements = Arrays.copyOf(elements, newSize);
    }

    public int size(){
        return size;
    }

    public T get(int index){
        return (T) elements[index];
    }
}
