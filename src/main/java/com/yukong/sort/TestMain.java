package com.yukong.sort;

/**
 * @author yukong
 * @date 2019-03-06 10:31
 */
public class TestMain {

    public static int a;

    public static int b = 0;

    public TestMain() {
        a++;
        b++;
    }

    public static void main(String[] args) {
        System.out.println(a);
        System.out.println(b);
        new TestMain();
        System.out.println(a);
        System.out.println(b);
    }

    static class Client {

        public static int a;

        public static int b = 0;

        private Client() {
            a++;
            b++;
        }


        public static void main(String[] args) {
            Client instance =new Client();
            System.out.println("a= " + Client.a);
            System.out.println("b= " + Client.b);
        }

    }


    static class Sort{


        /**
         * 快速排序
         * @param a
         * @param left
         * @param right
         */
        public static void quickSort(int[] a, int left, int right) {
            if (left < right) {
                int mid = partition(a, left, right);
                quickSort(a, left, mid - 1);
                quickSort(a, mid + 1, right);
            }
        }

        public static int partition(int[] a, int left, int right) {
            // 哨兵
            int sentinel = a[left];
            // 左右双指针
            int i = left;
            int j = right;
            // 开始
            while(i < j) {
                // 从右往左找
                while(j >= i && a[j] >= sentinel){
                    j--;
                }
                // 如果发现sentinel小的  则交换
                if(j >= i && a[j] < sentinel) {
                    a[i] = a[j];
                    i++;
                }
                // 从左往右找
                while(i < j && a[i] < sentinel) {
                    i++;
                }
                // 如果发现比sentinel大的  则交换
                if(i < j && a[i] > sentinel) {
                    a[j] = a[i];
                    j--;
                }
            }
            a[i] = sentinel;
            return i;
        }

        /**
         * 冒泡排序
         * @param a
         */
        public static void bubbleSort(int[] a){
            for (int i = 0; i < a.length - 1; i++) {
                for (int j = 0; j < a.length - 1 - i; j++) {
                    if(a[j] > a[j + 1]) {
                        a[j] = a[j] + a[j + 1];
                        a[j + 1] = a[j] - a[j + 1];
                        a[j] = a[j] - a[j + 1];
                    }
                }
            }
        }


        /**
         * 选择排序
         * @param a
         */
        public static void selectSort(int[] a){
            int minIndex;
            int temp;
            for (int i = 0; i < a.length; i++) {
                minIndex = i;
                for (int j = i + 1; j < a.length; j++) {
                    if(a[j] < a[minIndex]){
                        minIndex = j;
                    }
                }
                temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp;
            }
        }

        /**
         * 插入排序
         * @param a
         */
        public static void insertSort(int[] a) {
            int preIndex, currentValue;
            for (int i = 1; i < a.length; i++) {
                preIndex = i - 1;
                currentValue = a[i];
                while (preIndex >= 0 && a[preIndex] > currentValue){
                    a[preIndex + 1] = a[preIndex];
                    preIndex--;
                }
                a[preIndex+1] =  currentValue;
            }
        }

        public static void main(String[] args) {
            int[] arr = { 1,3,3,2,4,6,5,7,5};
            quickSort(arr, 0 ,8);
            System.out.print("quick sort: ");
            for (int a : arr) {
                System.out.print(a + " ");
            }
            System.out.println();
            int[] arr1 = {1,3,5,2,4,6};
            bubbleSort(arr1);
            System.out.print("bubble sort: ");
            for (int a : arr1) {
                System.out.print(a + " ");
            }
            System.out.println();
            int[] arr2 = {1,3,5,2,4,6};
            selectSort(arr2);
            System.out.print("select sort: ");
            for (int a : arr2) {
                System.out.print(a + " ");
            }

            System.out.println();
            int[] arr3 = {1,3,5,2,4,6};
            selectSort(arr3);
            System.out.print("insert sort: ");
            for (int a : arr3) {
                System.out.print(a + " ");
            }

            Integer t1 = new Integer(3);
            Integer t2 = new Integer(3);
            int t3 = 3;
            Integer t5 = 3;
            int t4 = 3;
            System.out.println(t3 == t2);
            System.out.println(t1 == t3);
            System.out.println(t4 == t2);
            System.out.println(t4 == t2);
            System.out.println(t3 == t5);
        }

    }

}
