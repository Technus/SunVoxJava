package nightradio.sunvoxlib.model.mapping;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StringMapping<T extends IString> {
    private final Map<String,T> mapping=new HashMap<>();

    public void put(T obj){
        mapping.put(obj.getValue(),obj);
    }

    public T get(String obj){
        return mapping.get(obj);
    }

    public T computeAbsent(String obj, Function<String,T> mappingFunction){
        return mapping.computeIfAbsent(obj,mappingFunction);
    }
}
