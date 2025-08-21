import java.util.*;

/**
 * 使用Kahn算法实现拓扑排序的类。
 * 该类通过维护图的邻接表和各节点的入度，实现对有向无环图（DAG）的拓扑排序。
 */
public class KahnTopologicalSort {

    // 使用邻接表表示有向图
    private int numVertices;
    private List<List<Integer>> adjacencyList;
    private int[] indegree;

    /**
     * 构造函数，初始化图的顶点数、邻接表和入度数组。
     *
     * @param numVertices 图中顶点的数量
     */
    public KahnTopologicalSort(int numVertices) {
        this.numVertices = numVertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjacencyList.add(new LinkedList<>());
        }
        this.indegree = new int[numVertices];
    }

    /**
     * 向图中添加一条有向边。
     *
     * @param from 起始节点索引
     * @param to   目标节点索引
     */
    public void addEdge(int from, int to) {
        adjacencyList.get(from).add(to);
        indegree[to]++; // 更新目标节点的入度
    }

    /**
     * 使用Kahn算法执行拓扑排序。
     *
     * @return 拓扑排序后的节点列表
     * @throws IllegalStateException 如果图中存在环，无法进行拓扑排序时抛出异常
     */
    public List<Integer> topologicalSort() {
        // 1. 初始化队列（存储入度为0的节点）
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();

        // 2. 将所有入度为0的节点加入队列
        for (int i = 0; i < numVertices; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        // 3. 处理队列中的节点
        int count = 0; // 记录已处理的节点数量
        while (!queue.isEmpty()) {
            int node = queue.poll();
            result.add(node);
            count++;

            // 4. 遍历当前节点的所有邻接节点
            for (int neighbor : adjacencyList.get(node)) {
                // 5. 减少邻接节点的入度
                indegree[neighbor]--;

                // 6. 如果入度减为0，加入队列
                if (indegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }

        // 7. 检查是否存在环（关键难点！）
        if (count != numVertices) {
            throw new IllegalStateException("图中存在环，无法进行拓扑排序");
        }

        return result;
    }

    /**
     * 主方法，用于测试拓扑排序功能。
     * 示例模拟“番茄炒蛋”工程的依赖关系。
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        // 创建示例图（番茄炒蛋工程）
        KahnTopologicalSort graph = new KahnTopologicalSort(7);

        // 定义节点索引
        int 准备厨具 = 0, 买菜 = 1, 洗番茄 = 2, 切番茄 = 3,
                打鸡蛋 = 4, 下锅炒 = 5, 吃 = 6;

        // 添加边（依赖关系）
        graph.addEdge(准备厨具, 打鸡蛋);
        graph.addEdge(准备厨具, 下锅炒);
        graph.addEdge(买菜, 洗番茄);
        graph.addEdge(洗番茄, 切番茄);
        graph.addEdge(切番茄, 下锅炒);
        graph.addEdge(打鸡蛋, 下锅炒);
        graph.addEdge(下锅炒, 吃);

        try {
            List<Integer> sortedOrder = graph.topologicalSort();
            System.out.println("拓扑排序结果: ");
            for (int node : sortedOrder) {
                switch (node) {
                    case 0:
                        System.out.println("准备厨具");
                        break;
                    case 1:
                        System.out.println("买菜");
                        break;
                    case 2:
                        System.out.println("洗番茄");
                        break;
                    case 3:
                        System.out.println("切番茄");
                        break;
                    case 4:
                        System.out.println("打鸡蛋");
                        break;
                    case 5:
                        System.out.println("下锅炒");
                        break;
                    case 6:
                        System.out.println("吃");
                        break;
                }
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }
}
