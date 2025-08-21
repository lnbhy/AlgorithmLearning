import java.util.*;

public class BFSShortestPath {
    private List<List<Integer>> graph; // 邻接表存储图结构
    private int[] dist;                // 存储源点到各顶点的最短距离
    private int[] parent;              // 存储最短路径的前驱节点
    private boolean[] visited;         // 访问标记数组

    public void bfsMinDistance(int start) {
        // 使用graph的大小（包含索引0）
        int n = graph.size();
        dist = new int[n];
        parent = new int[n];
        visited = new boolean[n];

        // 初始化
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        dist[start] = 0;
        visited[start] = true;
        queue.offer(start);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            // 安全遍历（防止空指针）
            if (graph.get(u) != null) {
                for (int w : graph.get(u)) {
                    if (!visited[w]) {
                        dist[w] = dist[u] + 1;
                        parent[w] = u;
                        visited[w] = true;
                        queue.offer(w);
                    }
                }
            }
        }
    }

    // 重建最短路径
    public List<Integer> getPath(int target) {
        LinkedList<Integer> path = new LinkedList<>();
        for (int v = target; v != -1; v = parent[v]) {
            path.addFirst(v);
        }
        return path;
    }

    // 修复后的图初始化
    public static void main(String[] args) {
        BFSShortestPath bfs = new BFSShortestPath();
        int vertexCount = 8; // 顶点数量
        int arraySize = vertexCount + 1; // 数组大小=顶点数+1

        // 创建包含索引0~8的图（共9个位置）
        bfs.graph = new ArrayList<>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            bfs.graph.add(new ArrayList<>());
        }

        // 添加图的边（索引1~8对应顶点1~8）
        // 注意：现在graph.get(8)是合法的
        bfs.graph.get(1).add(2); bfs.graph.get(2).add(1);
        bfs.graph.get(1).add(5); bfs.graph.get(5).add(1);
        bfs.graph.get(2).add(6); bfs.graph.get(6).add(2);
        bfs.graph.get(6).add(3); bfs.graph.get(3).add(6);
        bfs.graph.get(6).add(7); bfs.graph.get(7).add(6);
        bfs.graph.get(3).add(4); bfs.graph.get(4).add(3);
        bfs.graph.get(7).add(8); bfs.graph.get(8).add(7);

        bfs.bfsMinDistance(2); // 从顶点2（索引2）开始

        System.out.println("从2到8的最短距离: " + bfs.dist[8]);
        System.out.println("路径: " + bfs.getPath(8)); // [2, 6, 7, 8]
    }
}
