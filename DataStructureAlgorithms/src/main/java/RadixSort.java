import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class RadixSort {

    /**
     * 基数排序实现（从最低位开始排序）
     * @param arr 待排序数组
     */
    public static void radixSort(int[] arr) {
        // 易错点1：处理空数组或单元素数组
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 难点1：确定最大数的位数
        int max = Arrays.stream(arr).max().getAsInt();
        int maxDigit = getMaxDigit(max);

        // 创建10个桶（0-9）
        Queue<Integer>[] buckets = new Queue[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new LinkedList<>();
        }

        // 从个位开始，依次对每一位进行排序
        for (int digit = 0; digit < maxDigit; digit++) {
            // 分配过程：将数组中的元素放入对应的桶中
            for (int num : arr) {
                int bucketIndex = getDigit(num, digit);
                buckets[bucketIndex].offer(num);
            }

            // 收集过程：将桶中的元素按顺序放回原数组
            int index = 0;
            for (Queue<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[index++] = bucket.poll();
                }
            }
        }
    }

    /**
     * 获取数字指定位上的数字
     * @param num 数字
     * @param digit 位数（0表示个位，1表示十位，以此类推）
     * @return 指定位上的数字
     */
    private static int getDigit(int num, int digit) {
        // 难点2：计算指定位上的数字
        // 先除以10的digit次方，然后对10取余
        return (int) (num / Math.pow(10, digit)) % 10;
    }

    /**
     * 获取数字的位数
     * @param num 数字
     * @return 数字的位数
     */
    private static int getMaxDigit(int num) {
        // 易错点2：处理0的情况
        if (num == 0) {
            return 1;
        }

        int digitCount = 0;
        while (num != 0) {
            digitCount++;
            num /= 10;
        }
        return digitCount;
    }

    public static void main(String[] args) {
        // 测试用例
        int[] arr = {520, 211, 438, 888, 7, 111, 985, 666, 996, 233, 168};
        System.out.println("排序前: " + Arrays.toString(arr));

        radixSort(arr);

        System.out.println("排序后: " + Arrays.toString(arr));

        // 测试包含0的情况
        int[] arr2 = {0, 123, 45, 7, 89};
        System.out.println("排序前: " + Arrays.toString(arr2));

        radixSort(arr2);

        System.out.println("排序后: " + Arrays.toString(arr2));
    }
}