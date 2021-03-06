// 题目描述
// 输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
// 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
// (注意: 在返回值的list中，数组长度大的数组靠前)

package exercise_24;

import java.util.ArrayList;

public class Solution {

    public static void main(String[] args){
        Solution solution = new Solution();

        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(3);
//        root.right = new TreeNode(6);
//        root.right.left = new TreeNode(0);
//        root.right.right = new TreeNode(1);

        ArrayList<ArrayList<Integer>> answer = solution.FindPath(root, 9);
        System.out.println(answer);
    }

    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        ArrayList<ArrayList<Integer>> answer = new ArrayList<ArrayList<Integer>>();
        if(root == null || (target != root.val && root.left == null && root.right == null))
            return answer;

        if(target == root.val && root.left == null && root.right == null) {
            //当前节点是叶节点，且路径和为target
            ArrayList<Integer> arrayList = new ArrayList<>();
            arrayList.add(root.val);
            answer.add(arrayList);
            return answer;
        }

        int subTreeTarget = target - root.val;  //子树需要的和
        ArrayList<ArrayList<Integer>> leftAnswer = FindPath(root.left, subTreeTarget);
        ArrayList<ArrayList<Integer>> rightAnswer = FindPath(root.right, subTreeTarget);
        leftAnswer.addAll(rightAnswer);

        for(ArrayList arrayList : leftAnswer)
            arrayList.add(0, root.val);
        return leftAnswer;
    }
}
