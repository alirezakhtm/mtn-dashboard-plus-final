
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alireza
 */
public class MapSortTest {
    public static void main(String[] args) {
        Map<Integer, Integer> mapUnsorted = new LinkedHashMap<>();
        mapUnsorted.put(1, 25);
        mapUnsorted.put(2, 65);
        mapUnsorted.put(3, 89);
        mapUnsorted.put(4, 23);
        mapUnsorted.put(5, 456);
        mapUnsorted.put(6, 15);
        mapUnsorted.put(7, 5);
        System.out.println("Unsorted Map:");
        printMap(mapUnsorted);
        Map<Integer, Integer> mapSorted = sortMap(mapUnsorted);
        System.out.println("Sorted Map:");
        printMap(mapSorted);
        
    }
    
    public static <k, v> void printMap(Map<k, v> map){
        Set<k> lstKeys = map.keySet();
        for(k m : lstKeys){
            System.out.println("key: " + m + "\tvalue: " + map.get(m));
        }
    }
    
    public static Map<Integer, Integer> sortMap(Map<Integer, Integer> map){
        List<Map.Entry<Integer, Integer>> lst = new LinkedList<>(map.entrySet());
        Collections.sort(lst, new Comparator<Map.Entry<Integer, Integer>>(){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        Map<Integer, Integer> mapAnswer = new LinkedHashMap<>();
        for(Map.Entry<Integer, Integer> entry : lst){
            mapAnswer.put(entry.getKey(), entry.getValue());
        }
        return mapAnswer;
    }
}
