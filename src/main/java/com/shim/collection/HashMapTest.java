package com.shim.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xn064961 on 2017/1/17.
 */
public class HashMapTest {

    public static void main (String[] s) {
        Map<Integer, String> map = newHashMap();
        iterateMap(map);

        for (Integer key : map.keySet()) {
            String value = map.get(key);
        }
    }

    public static Map<Integer, String> newHashMap () {
        Map<Integer, String> map = new HashMap<Integer, String>();
        for (int i = 1; i < 20; i++) {
            map.put(i, String.valueOf(i));
            iterateMap(map);
        }
        return map;
    }

    public static void iterateMap(Map<Integer, String> map){
        for (Integer key : map.keySet()) {
            System.out.print("[" + key + ":" + hash(key) +"],");
        }
        System.out.println();
    }

    static final int hash(Object k) {
        int h = 0;
        if (0 != h && k instanceof String) {
            return sun.misc.Hashing.stringHash32((String) k);
        }

        h ^= k.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
}
