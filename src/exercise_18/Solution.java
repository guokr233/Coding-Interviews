// 题目描述
// 操作给定的二叉树，将其变换为源二叉树的镜像。
// 镜像：根节点不变，左子树变为原右子树的镜像，右子树变为原左子树的镜像
package exercise_18;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();

        TreeNode tree1 = new TreeNode(1);
        tree1.left = new TreeNode(2);
        tree1.right = new TreeNode(3);
        tree1.left.left = new TreeNode(4);
        tree1.left.right = new TreeNode(5);
        tree1.right.left = new TreeNode(6);
        tree1.right.right = new TreeNode(7);

        solution.Mirror2(tree1);
        System.out.println();
    }

    //递归实现
    public void Mirror(TreeNode root) {
        if(root == null)
            return;

        //交换当前节点的左右子树
        TreeNode rootLeft = root.left;  //交换值时，需要记住一个值
        root.left = root.right;
        root.right = rootLeft;
        //对左右子树进行镜像翻转
        Mirror(root.left);
        Mirror(root.right);
    }

    //循环实现（利用队列）
    public void Mirror2(TreeNode root) {
        if(root == null)
            return;

        TreeNode popedNode, popedNodeLeft;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);  //根节点入队

        while (!queue.isEmpty()){
            popedNode = queue.remove();  //出队节点
            //交换当前节点的左右子树
            popedNodeLeft = popedNode.left;  //交换值时，需要记住一个值
            popedNode.left = popedNode.right;
            popedNode.right = popedNodeLeft;

            //左右节点入队
            if(popedNode.left != null)
                queue.add(popedNode.left);
            if(popedNode.right != null)
                queue.add(popedNode.right);
        }
    }
}
