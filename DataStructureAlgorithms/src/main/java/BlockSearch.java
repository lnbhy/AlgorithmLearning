import java.util.Arrays;

// 索引表项
class IndexItem {
    int maxValue; // 分块的最大关键字
    int start;    // 分块起始下标
    int end;      // 分块结束下标

    public IndexItem(int maxValue, int start, int end) {
        this.maxValue = maxValue;
        this.start = start;
        this.end = end;
    }
}

public class BlockSearch {

    // 分块查找实现
    public static int blockSearch(int[] arr, IndexItem[] indexTable, int target) {
        // 1. 在索引表中确定目标所在分块
        int blockIdx = findBlock(indexTable, target);

        // 未找到合适分块
        if (blockIdx == -1) return -1;

        // 2. 在分块内顺序查找
        IndexItem block = indexTable[blockIdx];
        for (int i = block.start; i <= block.end; i++) {
            if (arr[i] == target) {
                return i; // 找到目标
            }
        }
        return -1; // 分块内未找到
    }

    // 在索引表中查找目标所在分块（折半查找）
    private static int findBlock(IndexItem[] indexTable, int target) {
        int low = 0;
        int high = indexTable.length - 1;

        // 折半查找核心逻辑
        while (low <= high) {
            int mid = (low + high) >>> 1; // 防溢出写法

            if (target <= indexTable[mid].maxValue) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        // 易错点1：检查low是否越界
        if (low >= indexTable.length) {
            // 目标值超过所有分块的最大值
            return -1;
        }

        // 重要：返回low指向的分块
        return low;
    }

    public static void main(String[] args) {
        // 示例数据（块内无序，块间有序）
        int[] arr = {7, 10, 13, 19, 16, 20,
                27, 22, 30, 40, 36, 43,
                50, 48};

        // 创建索引表（每个分块的最大值和区间）
        IndexItem[] indexTable = {
                new IndexItem(10, 0, 1),   // 分块1: max=10, [0,1]
                new IndexItem(20, 2, 5),   // 分块2: max=20, [2,5]
                new IndexItem(30, 6, 8),   // 分块3: max=30, [6,8]
                new IndexItem(40, 9, 10),  // 分块4: max=40, [9,10]
                new IndexItem(50, 11, 13)  // 分块5: max=50, [11,13]
        };

        // 测试用例
        System.out.println("查找 22: " + blockSearch(arr, indexTable, 22)); // 应返回 7
        System.out.println("查找 19: " + blockSearch(arr, indexTable, 19)); // 应返回 3
        System.out.println("查找 54: " + blockSearch(arr, indexTable, 54)); // 应返回 -1
    }
}
