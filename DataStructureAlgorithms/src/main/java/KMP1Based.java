public class KMP1Based {

    // 计算 1-based 索引的 next 数组
    public static int[] getNext1Based(String pattern) {
        int m = pattern.length();
        // 创建长度为 m+1 的数组，索引 0 不使用，使用 1~m
        int[] next = new int[m + 1];

        // 初始化：i 是主指针，j 是前缀指针
        int i = 1, j = 0;
        next[1] = 0;  // 第一个字符的 next 值总是 0

        // 从第二个字符开始计算 next 值
        while (i < m) {
            if (j == 0 || pattern.charAt(i - 1) == pattern.charAt(j - 1)) {
                System.out.println("i = " + i + ", j = " + j);
                next[++i] = ++j;
                System.out.println("next["+i+"]=" + j);
            } else {
                j = next[j];
                System.out.println("i = " + i + ", j = " + j);
                System.out.println("j"+"="+next[j]);
            }
        }
        return next;
    }

    // 1-based 索引的 KMP 搜索
    public static int kmpSearch1Based(String text, String pattern) {
        if (text == null || pattern == null) {
            throw new IllegalArgumentException("文本和模式不能为 null");
        }
        if(pattern.isEmpty()||pattern.length()>text.length()){
            return -1;
        }
        int n = text.length();
        int m = pattern.length();

        int[] next = getNext1Based(pattern);

        // 文本和模式指针 (1-based)
        int i = 1, j = 1;

        while (i <= n && j <= m) {
            // j == 0 表示需要从模式串开头重新匹配
            if (j == 0 || text.charAt(i - 1) == pattern.charAt(j - 1)) {
                i++;
                j++;
            } else {
                j = next[j];  // 根据 next 数组回溯
            }
        }

        // 找到匹配
        return j>m?i-m:-1;
    }

    public static void main(String[] args) {
        String text = "ababaabaabc";
        String pattern = "abaabc";

        System.out.println("文本: " + text);
        System.out.println("模式: " + pattern);

        int pos = kmpSearch1Based(text, pattern);

        if (pos != -1) {
            System.out.println("匹配起始位置: " + pos + " (1-based)");

            // 可视化展示匹配结果
            System.out.println("\n匹配结果:");
            System.out.println(text);
            for (int i = 1; i < pos; i++) System.out.print(" ");
            System.out.println(pattern);
        } else {
            System.out.println("未找到匹配");
        }

        // 计算并输出 next 数组 (1-based)
        int[] next = getNext1Based(pattern);
        System.out.print("\nNext 数组 (1-based): ");
        for (int i = 1; i <= pattern.length(); i++) {
            System.out.print(next[i] + " ");
        }
        System.out.println();

        // 显示 next 数组含义说明
        System.out.println("\nNext 数组含义 (1-based):");
        System.out.println("当模式串第 j 个字符匹配失败时，跳转到 next[j] 继续匹配");
        System.out.println("j    : 1 2 3 4 5 6");
        System.out.print("char : ");
        for (int i = 0; i < pattern.length(); i++) {
            System.out.print(pattern.charAt(i) + " ");
        }
        System.out.print("\nnext : ");
        for (int i = 1; i <= pattern.length(); i++) {
            System.out.print(next[i] + " ");
        }
    }
}
