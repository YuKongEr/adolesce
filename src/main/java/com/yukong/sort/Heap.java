package com.yukong.sort;

/**
 * @author yukong
 * @date 2019-03-12 16:18
 */
public class Heap<T extends Comparable<T>> {

    public boolean less(T[] t,int a, int b){
        return t[a].compareTo(t[b]) < 0;
    }

    public void swap(T[] t, Integer a, Integer b){
        T temp = t[a];
        t[a] = t[b];
        t[b] = temp;
    }

    private T[] heap;
    private int N = 0;

    public Heap(int maxN) {
        this.heap = (T[]) new Comparable[maxN + 1];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    public int size() {
        return N;
    }

    /**
     * 元素上浮 当插入一个新的元素的时候
     * 当一个元素比它的父元素还要大的时候就需要上浮
     * @param k
     */
    private void swim(int k){
        // 如果父元素小于子元素  则子元素上浮
        while (k > 1 && less(heap, k/2, k)){
            // 交换 上浮
            swap(heap, k/2, k);
            // 再次判断父元素的父元素
            k = k/2;

        }
    }

    /**
     * 元素下沉
     * @param k
     */
    private void ink(int k) {
        while (2 * k < N){
            int j = 2 * k;
            if(j < N && less(heap,j + 1, j)){
                j++;
            }
            if(less(heap, j, k)){
                break;
            }
            swap(heap, j, k);
            k = j;
        }
    }


}
