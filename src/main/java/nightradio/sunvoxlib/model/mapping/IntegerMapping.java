package nightradio.sunvoxlib.model.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class IntegerMapping<T extends IInteger> {
    private final Map<Integer,T> mapping=new HashMap<>();

    public void put(T obj){
        mapping.put(obj.getValue(),obj);
    }

    public T get(int obj){
        return mapping.get(obj);
    }

    public T computeAbsent(int obj, Function<Integer,T> mappingFunction){
        return mapping.computeIfAbsent(obj,mappingFunction);
    }

    public List<T> getAllMatching(int obj){
        List<T> list=new ArrayList<>();
        mapping.forEach((k,v)->{
            if(IInteger.containsFlag(obj,k)){
                list.add(v);
            }
        });
        return list;
    }

    public boolean contains(T obj){
        return contains(obj.getValue());
    }

    public boolean contains(int code){
        return mapping.containsKey(code);
    }
}
