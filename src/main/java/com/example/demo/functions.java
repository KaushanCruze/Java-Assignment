package com.example.demo;

import java.util.ArrayList;

public class functions {
    public static class SingleDataFormat{
        public int index;
        public double value;
        public SingleDataFormat(int index, String value){
            this.index = index;
            try{
                this.value = Double.parseDouble(value);
            }catch (Exception e){
                this.value=0.0;
            }
        }
    }
    public ArrayList<Data> data;
    functions(){
        data = new ArrayList<Data>();
    }
public ArrayList<SingleDataFormat> shellSort(int col){
        ArrayList<SingleDataFormat> temDataSet = new ArrayList<SingleDataFormat>();
        for(int i = 0; i < data.size(); i++){
            temDataSet.add(new SingleDataFormat(data.get(i).index, data.get(i).value[col]));
        }
        int gap = temDataSet.size() / 2;
        while(gap > 0){
            for(int i = gap; i < temDataSet.size(); i++){
                SingleDataFormat temp = temDataSet.get(i);
                int j = i;
                while(j >= gap && temDataSet.get(j - gap).value > temp.value){
                    temDataSet.set(j, temDataSet.get(j - gap));
                    j -= gap;
                }
                temDataSet.set(j, temp);
            }
            gap = gap / 2;
        }
        return temDataSet;
    }
    
public ArrayList<SingleDataFormat> mergeSort(int col) {
    ArrayList<SingleDataFormat> temDataSet = new ArrayList<>();
    for (Data d : data) {
        temDataSet.add(new SingleDataFormat(d.index, d.value[col]));
    }
    return mergeSortHelper(temDataSet);
}

private ArrayList<SingleDataFormat> mergeSortHelper(ArrayList<SingleDataFormat> list) {
    if (list.size() <= 1) return list;
    int mid = list.size() / 2;
    ArrayList<SingleDataFormat> left = new ArrayList<>(list.subList(0, mid));
    ArrayList<SingleDataFormat> right = new ArrayList<>(list.subList(mid, list.size()));
    return merge(mergeSortHelper(left), mergeSortHelper(right));
}

private ArrayList<SingleDataFormat> merge(ArrayList<SingleDataFormat> left, ArrayList<SingleDataFormat> right) {
    ArrayList<SingleDataFormat> result = new ArrayList<>();
    int i = 0, j = 0;
    while (i < left.size() && j < right.size()) {
        if (left.get(i).value <= right.get(j).value) {
            result.add(left.get(i++));
        } else {
            result.add(right.get(j++));
        }
    }
    while (i < left.size()) result.add(left.get(i++));
    while (j < right.size()) result.add(right.get(j++));
    return result;
}

public ArrayList<SingleDataFormat> quickSort(int col) {
    ArrayList<SingleDataFormat> temDataSet = new ArrayList<>();
    for (Data d : data) {
        temDataSet.add(new SingleDataFormat(d.index, d.value[col]));
    }
    quickSortHelper(temDataSet, 0, temDataSet.size() - 1);
    return temDataSet;
}

private void quickSortHelper(ArrayList<SingleDataFormat> list, int low, int high) {
    if (low < high) {
        int pi = partition(list, low, high);
        quickSortHelper(list, low, pi - 1);
        quickSortHelper(list, pi + 1, high);
    }
}

private int partition(ArrayList<SingleDataFormat> list, int low, int high) {
    double pivot = list.get(high).value;
    int i = (low - 1);
    for (int j = low; j < high; j++) {
        if (list.get(j).value <= pivot) {
            i++;
            SingleDataFormat temp = list.get(i);
            list.set(i, list.get(j));
            list.set(j, temp);
        }
    }
    SingleDataFormat temp = list.get(i + 1);
    list.set(i + 1, list.get(high));
    list.set(high, temp);
    return i + 1;
}

public ArrayList<SingleDataFormat> heapSort(int col) {
    ArrayList<SingleDataFormat> temDataSet = new ArrayList<>();
    for (Data d : data) {
        temDataSet.add(new SingleDataFormat(d.index, d.value[col]));
    }
    int n = temDataSet.size();
    for (int i = n / 2 - 1; i >= 0; i--) {
        heapify(temDataSet, n, i);
    }
    for (int i = n - 1; i > 0; i--) {
        SingleDataFormat temp = temDataSet.get(0);
        temDataSet.set(0, temDataSet.get(i));
        temDataSet.set(i, temp);
        heapify(temDataSet, i, 0);
    }
    return temDataSet;
}

private void heapify(ArrayList<SingleDataFormat> list, int n, int i) {
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    if (left < n && list.get(left).value > list.get(largest).value) {
        largest = left;
    }
    if (right < n && list.get(right).value > list.get(largest).value) {
        largest = right;
    }
    if (largest != i) {
        SingleDataFormat swap = list.get(i);
        list.set(i, list.get(largest));
        list.set(largest, swap);
        heapify(list, n, largest);
    }
}

public ArrayList<SingleDataFormat> insertionSort(int col) {
    ArrayList<SingleDataFormat> temDataSet = new ArrayList<>();
    for (Data d : data) {
        temDataSet.add(new SingleDataFormat(d.index, d.value[col]));
    }
    for (int i = 1; i < temDataSet.size(); i++) {
        SingleDataFormat key = temDataSet.get(i);
        int j = i - 1;
        while (j >= 0 && temDataSet.get(j).value > key.value) {
            temDataSet.set(j + 1, temDataSet.get(j));
            j--;
        }
        temDataSet.set(j + 1, key);
    }
    return temDataSet;
}
}
