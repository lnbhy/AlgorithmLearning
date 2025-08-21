public class MergeSort {

    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 创建辅助数组，避免在递归过程中重复创建
        // 难点1：需要理解为什么要在外部创建辅助数组
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
    }

    private static void mergeSort(int[] arr, int left, int right, int[] temp) {
        // 递归终止条件：当子数组只有一个元素时
        if (left < right) {
            int mid = left + (right - left) / 2; // 防止整数溢出

            // 递归排序左半部分
            mergeSort(arr, left, mid, temp);

            // 递归排序右半部分
            mergeSort(arr, mid + 1, right, temp);

            // 合并两个有序子数组
            merge(arr, left, mid, right, temp);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        int i = left;    // 左子数组起始索引
        int j = mid + 1; // 右子数组起始索引
        int k = 0;       // 临时数组索引

        // 比较两个子数组的元素，将较小的放入临时数组
        while (i <= mid && j <= right) {
            // 易错点1：注意这里的比较是 <= 而不是 <，保证排序的稳定性
            if (arr[i] <= arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // 将左子数组剩余元素复制到临时数组
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // 将右子数组剩余元素复制到临时数组
        while (j <= right) {
            temp[k++] = arr[j++];
        }

        // 将临时数组中的元素复制回原数组
        // 难点2：理解这里需要将临时数组的内容复制回原数组的对应位置
        k = 0;
        while (left <= right) {
            arr[left++] = temp[k++];
        }
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("原始数组:");
        printArray(arr);

        mergeSort(arr);

        System.out.println("排序后数组:");
        printArray(arr);
    }

    private static void printArray(int[] arr) {
        for (int value : arr) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
