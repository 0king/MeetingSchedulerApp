package com.example.meetingscheduler.data.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultiMap<K, V> {

    private Map<K, ArrayList<V>> m = new HashMap<>(10);

    public void put(K k, V v) {
        if (m.containsKey(k)) {
            m.get(k).add(v);
        } else {
            ArrayList<V> arr = new ArrayList<>();
            arr.add(v);
            m.put(k, arr);
        }
    }

    public boolean containsKey(K k){
        return m.containsKey(k);
    }

    public ArrayList<V> get(K k) {
        return m.get(k);
    }

    public V get(K k, int index) {
        return m.get(k).size()-1 < index ? null : m.get(k).get(index);
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for (K k: m.keySet()) {
            builder.append(k)
                    .append("=>[");
            ArrayList<V> a = m.get(k);
            for (V v: a)
                builder.append(v).append(",");
            builder.append("]\n");
        }
        return builder.toString();
    }
}
