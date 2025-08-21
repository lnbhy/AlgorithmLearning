import java.util.Arrays;

public class QuickSort {

    public static void quickSort(int[] arr, int low, int high) {
        // 易错点1：递归终止条件必须是 low < high，否则会导致栈溢出或无效递归
        if (low < high) {
            // 划分操作，返回枢轴元素的最终位置
            int pivotPos = partition(arr, low, high);
            // 递归处理左半部分
            quickSort(arr, low, pivotPos - 1);
            // 递归处理右半部分
            quickSort(arr, pivotPos + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // 选择第一个元素作为枢轴（pivot）
        int pivot = arr[low];

        // 难点1：循环条件是 low < high，确保左右指针不会交叉
        while (low < high) {
            // 难点2：先从右往左扫描，找到第一个小于枢轴的元素
            while (low < high && arr[high] >= pivot) {
                high--;
            }
            // 将比枢轴小的元素移到左端
            arr[low] = arr[high];

            // 再从左往右扫描，找到第一个大于等于枢轴的元素
            while (low < high && arr[low] <= pivot) {
                low++;
            }
            // 将比枢轴大的元素移到右端
            arr[high] = arr[low];
        }

        // 将枢轴放到最终位置
        arr[low] = pivot;
        // 返回枢轴位置
        return low;
    }

    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49};
        quickSort(arr, 0, arr.length - 1);
        System.out.println("排序结果: " + Arrays.toString(arr));
    }
}