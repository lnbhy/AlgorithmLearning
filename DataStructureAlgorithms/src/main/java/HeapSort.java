public class HeapSort {

    public static void heapSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        int n = arr.length;

        // 构建最大堆（从最后一个非叶子节点开始）
        // 易错点1：注意循环起始位置应该是最后一个非叶子节点，即n/2-1
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // 一个个从堆顶取出元素
        for (int i = n - 1; i > 0; i--) {
            // 将当前堆顶元素（最大值）与末尾元素交换
            // 易错点2：交换后，堆的大小应该减1（i），但数组长度不变
            swap(arr, 0, i);

            // 对剩余元素重新构建最大堆
            heapify(arr, i, 0);
        }
    }

    // 调整堆（最大堆）
    // 难点1：理解下滤过程，需要递归调整被破坏的子堆
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;        // 初始化最大值为根节点
        int left = 2 * i + 1;   // 左子节点
        int right = 2 * i + 2;  // 右子节点

        // 如果左子节点存在且大于根节点
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }

        // 如果右子节点存在且大于当前最大值
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }

        // 如果最大值不是根节点
        if (largest != i) {
            swap(arr, i, largest);

            // 递归调整受影响的子堆
            // 难点2：理解这里需要递归调整，因为交换可能破坏了子堆的结构
            heapify(arr, n, largest);
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};

        System.out.println("原始数组:");
        printArray(arr);

        heapSort(arr);

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