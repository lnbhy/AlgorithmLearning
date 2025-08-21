import java.util.Arrays;

public class InsertionSort {

    /**
     * 直接插入排序（不带哨兵）
     * 时间复杂度：最好O(n)，最坏O(n²)，平均O(n²)
     * 空间复杂度：O(1)
     * 稳定性：稳定
     */
    public static void insertionSort(int[] arr) {
        // 易错点1：i从1开始，不是从0开始
        for (int i = 1; i < arr.length; i++) {
            // 只有当前元素小于前一个元素时才需要插入(升序)
            //待排序列元素第一个<已排序列最后一个元素
            if (arr[i] < arr[i - 1]) {
                int temp = arr[i]; // 保存当前需要插入的元素
                int j;

                // 难点1：从后往前查找插入位置，同时移动元素
                // 易错点2：注意循环条件是j>=0 && arr[j]>temp ，循环结束后如果j有效则j指向第一个不大于temp的元素
                for (j = i - 1; j >= 0 && arr[j] > temp; j--) {
                    arr[j + 1] = arr[j]; // 元素后移
                }

                // 难点2：插入位置是j+1，不是j，因为后移操作后第一个不大于temp的元素后空出位置，所以插入位置是j+1
                arr[j + 1] = temp; // 插入到正确位置
            }
        }
    }

    /**
     * 直接插入排序（带哨兵）
     * 使用arr[0]作为哨兵，可以减少比较次数
     * 注意：使用此方法时，arr[0]不能存放有效数据
     */
    public static void insertionSortWithSentinel(int[] arr) {
        // 易错点3：使用哨兵时，i从2开始，因为arr[1]是第一个有效数据
        for (int i = 2; i < arr.length; i++) {
            // 只有当前元素小于前一个元素时才需要插入
            if (arr[i] < arr[i - 1]) {
                arr[0] = arr[i]; // 设置哨兵
                int j;

                // 难点3：使用哨兵后，无需判断j>=0，因为arr[0]会保证循环终止
                for (j = i - 1; arr[j] > arr[0]; j--) {
                    arr[j + 1] = arr[j]; // 元素后移
                }

                arr[j + 1] = arr[0]; // 插入到正确位置
            }
        }
    }

    /**
     * 折半插入排序
     * 使用二分查找来减少比较次数，但移动次数不变
     */
    public static void binaryInsertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                int temp = arr[i];

                // 使用二分查找找到插入位置
                int left = 0;
                int right = i - 1;

                // 难点4：二分查找的终止条件和边界处理
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (arr[mid] > temp) {
                        right = mid - 1;
                    } else {
                        // 易错点4：为保证稳定性，相等时继续在右侧查找
                        left = mid + 1;
                    }
                }

                // 移动元素
                for (int j = i - 1; j >= left; j--) {
                    arr[j + 1] = arr[j];
                }

                arr[left] = temp; // 插入到正确位置
            }
        }
    }

    // 测试代码
    public static void main(String[] args) {
        int[] arr1 = {5, 2, 8, 3, 1, 6, 4};
        System.out.println("原始数组: " + Arrays.toString(arr1));
        insertionSort(arr1);
        System.out.println("直接插入排序后: " + Arrays.toString(arr1));

        // 注意：使用哨兵版本时，数组第一个元素不能是有效数据
        int[] arr2 = {0, 5, 2, 8, 3, 1, 6, 4};
        System.out.println("原始数组: " + Arrays.toString(arr2));
        insertionSortWithSentinel(arr2);
        System.out.println("带哨兵插入排序后: " + Arrays.toString(arr2));

        int[] arr3 = {5, 2, 8, 3, 1, 6, 4};
        System.out.println("原始数组: " + Arrays.toString(arr3));
        binaryInsertionSort(arr3);
        System.out.println("折半插入排序后: " + Arrays.toString(arr3));

        // 测试稳定性（有相同元素）
        int[] arr4 = {5, 2, 8, 3, 2, 6, 4};
        System.out.println("原始数组: " + Arrays.toString(arr4));
        insertionSort(arr4);
        System.out.println("稳定性测试后: " + Arrays.toString(arr4));
    }
}
