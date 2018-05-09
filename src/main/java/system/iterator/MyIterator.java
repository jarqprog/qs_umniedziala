package system.iterator;

import java.util.List;

public class MyIterator <T> implements IIterator{
    private int index = 0;
    private List<T> collection;
    
    public MyIterator(List<T> collection){
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
