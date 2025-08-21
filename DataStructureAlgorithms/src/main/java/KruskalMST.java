import java.util.*;

public class KruskalMST {
    // 边类
    static class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            // 易错点1：排序遗漏 - 必须按权值排序
            return this.weight - other.weight;
        }
    }

    // 并查集类
    static class UnionFind {
        int[] parent, rank;

        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            // 易错点2：并查集未初始化 - 每个顶点独立集合
            for (int i = 0; i < n; i++) {
                parent[i] = i;  // 初始每个顶点独立成集合
            }
        }

        public int find(int x) {
            // 路径压缩优化
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);

            if (rootX == rootY) return;

            // 按秩合并
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }

    public static List<Edge> kruskal(List<Edge> edges, int n) {
        // 易错点1：排序遗漏 - 必须先对边排序
        Collections.sort(edges);

        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();
        int edgesAdded = 0;

        for (Edge edge : edges) {
            // 易错点3：环判断错误 - 必须检查连通性
            if (uf.find(edge.src) != uf.find(edge.dest)) {
                uf.union(edge.src, edge.dest);
                mst.add(edge);
                edgesAdded++;

                // 易错点4：终止条件缺失 - 边数达标提前终止
                if (edgesAdded == n - 1) break;
            }
        }

        // 检查是否形成完整生成树
        if (edgesAdded != n - 1) {
            System.out.println("图不连通，无法形成最小生成树");
            return null;
        }

        return mst;
    }

    public static void main(String[] args) {
        int n = 4;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1, 1));
        edges.add(new Edge(0, 2, 5));
        edges.add(new Edge(0, 3, 6));
        edges.add(new Edge(1, 2, 5));
        edges.add(new Edge(2, 3, 4));

        // 处理重边（可选）
        // 易错点5：重边处理 - 保留最小权值边
        // 本例无重边，实际应用中需要预处理

        List<Edge> mst = kruskal(edges, n);

        if (mst != null) {
            System.out.println("最小生成树边集：");
            int totalWeight = 0;
            for (Edge edge : mst) {
                System.out.println(edge.src + " - " + edge.dest + " : " + edge.weight);
                totalWeight += edge.weight;
            }
            System.out.println("总权重: " + totalWeight); // 正确结果：4+2+4+4+4=18
        }
    }
}
