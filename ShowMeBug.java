package com.clb.alth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowMeBug {

    public static void main(String[] args) {
        System.out.println(getUniqueNums(new int[]{1,2,1,5}).toString());
        System.out.println(sortRepeatedNums(new int[]{1,2,1,5, 2, 0, 0, 0}).toString());
    }

    // 请按这里筛选出数组中，未重复出现的元素
    public static List<Integer> getUniqueNums(int[] array) {
        List<Integer> noReaptNum = new ArrayList<>();

        Map<Integer, Integer> countMap = calcCount(array);

        for (Integer key : countMap.keySet()) {
            if (countMap.get(key) == 1) {
                noReaptNum.add(key);
            }
        }
        return noReaptNum;
    }

    //两个map
    // 请在这里按照出现次数，对重复出现过的元素，根据出现次数由多到少进行排序
    public static List<Integer> sortRepeatedNums(int[] array) {

        List<Integer> reaptNumSort = new ArrayList<>();

        Map<Integer, Integer> countMap = calcCount(array);

        List<MyNumber> myNumberList = new ArrayList<>();
        for (Integer key : countMap.keySet()) {
            if (countMap.get(key) > 1) {
                myNumberList.add(new MyNumber(key, countMap.get(key)));
            }
        }
        myNumberList.sort((o1, o2) -> (o2.c - o1.c));

        for (MyNumber item : myNumberList) {
            reaptNumSort.add(item.n);
        }
        return reaptNumSort;
    }

    public static Map<Integer, Integer> calcCount(int[] array) {
        Map<Integer, Integer> numbers = new HashMap<>();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i++) {
                Integer val = numbers.get(array[i]);
                if (val == null) {
                    numbers.put(array[i], 0);
                }
                numbers.put(array[i], numbers.get(array[i])+1);
            }
        }
        return numbers;
    }

    static class MyNumber {
        private int n;
        private int c;

        public MyNumber(int num, int count){
            this.n = num;
            this.c = count;
        }
    }
}