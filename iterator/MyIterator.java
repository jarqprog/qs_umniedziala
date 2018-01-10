package iterator;

import java.util.ArrayList;

public class MyIterator <T> implements IIterator{
    private int index = 0;
    private ArrayList<T> collection;
    
    public MyIterator(ArrayList<T> collection){
        this.collection = collection;
    }
    
    public boolean hasNext(){
        if(index < collection.size()){
            return true;
        }
        return false;
        
    }
    
    public T next(){
        if(hasNext()){
            return collection.get(index++);
        }
        return null;
    }
}
