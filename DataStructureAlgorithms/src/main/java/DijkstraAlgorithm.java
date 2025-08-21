import java.util.Arrays;

public class DijkstraAlgorithm {
    // 使用邻接矩阵存储图（易错点1：需确保矩阵对称性）
    private static final int INF = Integer.MAX_VALUE; // 表示无穷大

    public static void dijkstra(int[][] graph, int source) {
        int n = graph.length;
        int[] dist = new int[n];       // 最短路径长度数组
        int[] path = new int[n];       // 前驱节点数组（易错点2：需记录路径）
        boolean[] finalSet = new boolean[n]; // 已确定最短路径的顶点集

        // 初始化三个核心数组（关键点1）
        Arrays.fill(dist, INF);
        Arrays.fill(path, -1);
        dist[source] = 0;  // 源点到自身距离为0

        // 主循环：执行n-1轮（关键点2）
        for (int count = 0; count < n - 1; count++) {
            // 步骤1：找当前dist最小的未确定节点
            int u = -1;
            int minDist = INF;
            for (int v = 0; v < n; v++) {
                if (!finalSet[v] && dist[v] <= minDist) { // 易错点3：注意等号
                    minDist = dist[v];
                    u = v;
                }
            }
            if (u == -1) break; // 所有顶点已处理完
            finalSet[u] = true; // 标记为已确定

            // 步骤2：更新邻接点（关键点3）
            for (int v = 0; v < n; v++) {
                // 易错点4：需同时检查三个条件
                if (!finalSet[v] &&
                        graph[u][v] != INF &&
                        dist[u] != INF &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    path[v] = u; // 记录前驱节点
                }
            }
        }

        // 打印结果（示例）
        printSolution(dist, path, source);
    }

    private static void printSolution(int[] dist, int[] path, int source) {
        System.out.println("源点: " + source);
        System.out.println("顶点\t距离\t路径");
        for (int i = 0; i < dist.length; i++) {
            System.out.printf("%d\t%d\t", i, dist[i]);
            printPath(path, i);
            System.out.println();
        }
    }

    // 递归打印路径（关键点4）
    private static void printPath(int[] path, int current) {
        if (current == -1) return;
        printPath(path, path[current]);
        System.out.print(current + " ");
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 10, INF, INF, 5},
                {INF, 0, 1, INF, 2},
                {INF, INF, 0, 4, INF},
                {7, INF, 6, 0, INF},
                {INF, 3, 9, 2, 0}
        };
        dijkstra(graph, 0);
    }
}