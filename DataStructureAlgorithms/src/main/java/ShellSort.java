public class ShellSort {

    public static void shellSort(int[] arr) {
        int n = arr.length;

        // 使用希尔增量序列（每次减半）
        for (int gap = n / 2; gap > 0; gap /= 2) {
            // 难点1：理解gap分组方式
            // 从第gap个元素开始，对每个分组进行插入排序，因为每组第一个元素默认有序
            for (int i = gap; i < n; i++) {
                int temp = arr[i];
                int j;

                // 难点2：理解分组内插入排序的实现
                // 对当前分组的元素进行插入排序
                for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                    // 易错点1：注意这里是j-gap而不是j-1
                    arr[j] = arr[j - gap];
                }

                // 插入当前元素到正确位置
                arr[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {49, 38, 65, 97, 76, 13, 27, 49};

        System.out.println("排序前:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();

        shellSort(arr);

        System.out.println("排序后:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}