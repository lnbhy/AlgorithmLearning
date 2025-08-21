public class BubbleSort {

    // 基础版本冒泡排序
    public static void bubbleSortBasic(int[] arr) {
        int n = arr.length;

        // 易错点1：需要n-1轮循环，而不是n轮
        for (int i = 0; i < n - 1; i++) {
            // 难点1：理解内层循环的边界条件
            // 每轮结束后，最大的i个元素已经就位，所以内层循环只需到n-1-i
            for (int j = 0; j < n - 1 - i; j++) {
                // 如果前面的元素大于后面的元素，则交换
                if (arr[j] > arr[j + 1]) {
                    // 交换元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 优化版本冒泡排序（添加提前结束标志）
    public static void bubbleSortOptimized(int[] arr) {
        int n = arr.length;
        boolean swapped; // 标记是否发生了交换

        // 易错点2：需要正确处理提前结束的条件
        for (int i = 0; i < n - 1; i++) {
            swapped = false; // 每轮开始前重置标志

            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    // 交换元素
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // 标记发生了交换
                }
            }

            // 难点2：理解提前结束的条件
            // 如果这一轮没有发生交换，说明数组已经有序，可以提前结束
            if (!swapped) {
                break;
            }
        }
    }

    // 从后往前冒泡的版本
    public static void bubbleSortBackward(int[] arr) {
        int n = arr.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            // 难点3：理解从后往前冒泡的实现方式,把小的换前边
            // 从数组末尾开始，向前比较相邻元素
            for (int j = n - 1; j > i; j--) {
                if (arr[j - 1] > arr[j]) {
                    // 交换元素
                    int temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49};

        System.out.println("排序前:");
        printArray(arr);

        // 测试基础版本
        int[] arr1 = arr.clone();
        bubbleSortBasic(arr1);
        System.out.println("基础版本排序后:");
        printArray(arr1);

        // 测试优化版本
        int[] arr2 = arr.clone();
        bubbleSortOptimized(arr2);
        System.out.println("优化版本排序后:");
        printArray(arr2);

        // 测试从后往前版本
        int[] arr3 = arr.clone();
        bubbleSortBackward(arr3);
        System.out.println("从后往前版本排序后:");
        printArray(arr3);
    }

    // 辅助方法：打印数组
    public static void printArray(int[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
}
