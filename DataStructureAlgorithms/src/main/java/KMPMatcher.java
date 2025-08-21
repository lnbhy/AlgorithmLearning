public class KMPMatcher {

    // 构建next数组（部分匹配表）
    public static int[] getNextArray(char[] ms) {
        if (ms.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[ms.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;  // next数组填充位置
        int cn = 0; // 当前比较位置

        while (i < ms.length) {
            if (ms[i - 1] == ms[cn]) {
                //next[i]=cn+1;
                //cn++;
                //i++;
                next[i++] = ++cn;
            } else if (cn > 0) {
                cn = next[cn];
            } else {
                next[i++] = 0;
            }
        }
        return next;
    }

    // KMP字符串匹配算法
    public static int getIndexOf(String s, String m) {
        // 参数合法性校验
        if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
            return -1;
        }

        char[] str1 = s.toCharArray();
        char[] str2 = m.toCharArray();
        int i1 = 0; // 主串指针
        int i2 = 0; // 模式串指针
        int[] next = getNextArray(str2); // 获取部分匹配表
        while (i1 < str1.length && i2 < str2.length) {
            if (str1[i1] == str2[i2]) {
                i1++;
                i2++;  // 字符匹配成功，双指针后移
            } else if (next[i2] == -1) {
                i1++;  // 模式串首位不匹配，主串后移
            } else {
                i2 = next[i2];  // 利用next数组跳过已匹配前缀
            }
        }
        // 返回匹配起始位置或-1
        return i2 >= str2.length ? i1 - i2 : -1;
    }

    // 使用示例
    public static void main(String[] args) {
        String text = "sadbutsad";
        String pattern = "sad";

        int index = getIndexOf(text, pattern);
        System.out.println("匹配位置: " + index);  // 输出: 4
    }
}
