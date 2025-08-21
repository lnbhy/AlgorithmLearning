import java.util.Arrays;

public class SelectionSort {

    /**
     * 简单选择排序算法实现
     * @param arr 待排序数组
     */
    public static void selectionSort(int[] arr) {
        // 易错点1：注意循环边界条件，只需要n-1趟排序
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i; // 记录最小元素的索引

            // 难点1：内层循环从i+1开始，寻找[i, n-1]区间的最小值
            for (int j = i + 1; j < arr.length; j++) {
                // 易错点2：注意比较的是arr[j]和arr[minIndex]，不是arr[i]
                if (arr[j] < arr[minIndex]) {
                    minIndex = j; // 更新最小元素索引
                }
            }

            // 易错点3：只有当找到的最小元素不是当前位置时才交换，自己和自己交换无意义
            if (minIndex != i) {
                swap(arr, i, minIndex);
            }

            // 可选：打印每趟排序结果，便于理解算法过程
            // System.out.println("第" + (i+1) + "趟排序结果: " + Arrays.toString(arr));
        }
    }

    /**
     * 交换数组中两个元素的位置
     * @param arr 数组
     * @param i 第一个元素的索引
     * @param j 第二个元素的索引
     */
    private static void swap(int[] arr, int i, int j) {
        // 难点2：注意交换算法实现，使用临时变量避免值覆盖
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49};
        System.out.println("排序前: " + Arrays.toString(arr));

        selectionSort(arr);

        System.out.println("排序后: " + Arrays.toString(arr));
    }
}