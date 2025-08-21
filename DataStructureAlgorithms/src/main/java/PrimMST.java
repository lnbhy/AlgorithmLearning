import java.util.*;

public class PrimMST {
    // 朴素版Prim算法实现（O(V^2)）
    public static int prim(int[][] graph) {
        int n = graph.length;
        // 易错点1：初始化 - 未正确设置起点会导致逻辑错误
        int[] lowCost = new int[n];      // 各顶点加入生成树的最小代价
        boolean[] isJoin = new boolean[n]; // 标记顶点是否已加入生成树

        Arrays.fill(lowCost, Integer.MAX_VALUE);
        lowCost[0] = 0; // 起点最小代价设为0（易错点1.1：起点设为0）

        int totalCost = 0;
        int selectedCount = 0; // 已选顶点计数（用于检测非连通图）

        // 核心流程：共需选择n个顶点（包括起点）
        for (int i = 0; i < n; i++) {
            // 1. 寻找当前lowCost中的最小值顶点u
            int u = -1;
            for (int v = 0; v < n; v++) {
                // 只考虑未加入的顶点（易错点2：已加入顶点不能重复选择）
                if (!isJoin[v] && (u == -1 || lowCost[v] < lowCost[u])) {
                    u = v;
                }
            }

//            // 易错点3：非连通图处理 - 无法选出有效顶点
//            if (u == -1 || lowCost[u] == Integer.MAX_VALUE) {
//                System.out.println("图不连通！");
//                return -1; // 返回错误码
//            }

            isJoin[u] = true; // 将u加入生成树
            totalCost += lowCost[u];
            selectedCount++;

            // 2. 更新u的邻接点的lowCost值
            for (int v = 0; v < n; v++) {
                // 易错点4：更新条件判断（必须同时满足三个条件）
                if (!isJoin[v] && graph[u][v] != 0 && graph[u][v] < lowCost[v]) {
                    // 注意：graph[u][v]!=0 表示存在边（0表示无直接边）
                    // 易错点5：权值比较（需严格小于当前值才更新）
                    lowCost[v] = graph[u][v];
                }
            }
        }

        // 易错点3补充：检查是否加入全部顶点
        if (selectedCount != n) {
            System.out.println("图不连通！");
            return -1;
        }
        return totalCost;
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 1, 5, 6},
                {1, 0, 5, 0},  // 易错点7：无向图需要对称
                {5, 5, 0, 4},
                {6, 0, 4, 0}
        };

        int cost = prim(graph);
        if (cost != -1) {
            System.out.println("最小生成树权值和: " + cost); // 正确结果：7
        }
    }
}