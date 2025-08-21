import java.util.HashMap;

public class Solution {
    public int subarraySum(int[] nums, int k) {
        if(nums.length<1)return 0;

        HashMap<Integer,Integer> m=new HashMap<>();
        m.put(0,1);
        System.out.println("(0,"+m.get(0) +")");
        System.out.println("------------------------");
        int count=0,pre_sum=0;

        for(int i=0;i<nums.length;i++){
            pre_sum+=nums[i];
            System.out.println("pre_sum="+pre_sum);
            int target=pre_sum-k;
            System.out.println("target="+target);
            if(m.containsKey(target)){
                System.out.println("m.get(target)="+m.get(target));
                count+=m.get(target);
                System.out.println("count="+count);
            }
            m.put(pre_sum,m.getOrDefault(pre_sum,0)+1);
            System.out.println("("+pre_sum+","+m.get(pre_sum)+")");

            System.out.println("-------------------------");
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s=new Solution();
        int[] nums=new int[]{1,-1,1,-1};
        int k=0;
        System.out.println(s.subarraySum(nums,k));
    }
}