class AVLNode {
    int key;
    int height;  // 节点高度（代替平衡因子）
    AVLNode left, right;

    public AVLNode(int key) {
        this.key = key;
        this.height = 1;  // 新节点初始高度为1
    }
}

public class AVLTree {
    private AVLNode root;

    // 获取节点高度（处理null节点）
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    // 更新节点高度
    private void updateHeight(AVLNode node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // 获取平衡因子 = 左子树高 - 右子树高
    private int balanceFactor(AVLNode node) {
        return height(node.left) - height(node.right);
    }

    /***************** 旋转操作（核心难点） *****************/
    // 右旋 (LL型)
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // 执行旋转
        x.right = y;
        y.left = T2;

        // 更新高度（必须先更新y，再更新x）
        updateHeight(y);  // 易错点：顺序不能颠倒
        updateHeight(x);

        return x;  // 返回新根节点
    }

    // 左旋 (RR型)
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // 执行旋转
        y.left = x;
        x.right = T2;

        // 更新高度
        updateHeight(x);
        updateHeight(y);

        return y;  // 返回新根节点
    }

    /***************** 插入操作 *****************/
    public void insert(int key) {
        root = insert(root, key);
    }

    private AVLNode insert(AVLNode node, int key) {
        // 1. 标准BST插入
        if (node == null) return new AVLNode(key);

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node; // 重复键不允许
        }

        // 2. 更新当前节点高度
        updateHeight(node);

        // 3. 检查平衡因子 (难点：4种不平衡情况)
        int balance = balanceFactor(node);

        // LL型（左左）
        if (balance > 1 && key < node.left.key) {
            return rightRotate(node);
        }

        // RR型（右右）
        if (balance < -1 && key > node.right.key) {
            return leftRotate(node);
        }

        // LR型（左右）
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);  // 易错点：先对左子左旋
            return rightRotate(node);
        }

        // RL型（右左）
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right); // 易错点：先对右子右旋
            return leftRotate(node);
        }

        return node; // 无需调整
    }

    /***************** 删除操作（难点） *****************/
    public void delete(int key) {
        root = delete(root, key);
    }

    private AVLNode delete(AVLNode node, int key) {
        // 1. 执行标准BST删除
        if (node == null) return null;

        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // 节点找到，执行删除
            if (node.left == null || node.right == null) {
                // 单子节点或无子节点
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    return null; // 无子节点
                } else {
                    return temp; // 单子节点
                }
            } else {
                // 双子节点：用后继节点值替换（难点1）
                AVLNode successor = minValueNode(node.right);
                node.key = successor.key;
                node.right = delete(node.right, successor.key); // 递归删除后继
            }
        }

        // 2. 更新高度
        updateHeight(node);

        // 3. 检查平衡（难点2：删除后可能引起多种不平衡）
        int balance = balanceFactor(node);

        // LL型
        if (balance > 1 && balanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }

        // LR型
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RR型
        if (balance < -1 && balanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }

        // RL型
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // 辅助函数：找最小节点
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /***************** 测试案例 *****************/
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // 插入测试
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);  // 触发RR旋转
        tree.insert(15);
        tree.insert(5);   // 触发RL旋转（若存在）

        // 删除测试
        tree.delete(15);  // 可能触发旋转调整
    }
}
