package com.github.technus.sunvoxlib.model.mapping;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class IntegerMapping<T extends IInteger> implements Iterable<T> {
    private final Map<Integer,T> mapping=new HashMap<>();

    @Override
    public Iterator<T> iterator(){
        return mapping.values().iterator();
    }

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
