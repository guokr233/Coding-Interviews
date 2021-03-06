package BinaryTree;

/**
 * 平衡二叉树，即AVL树
 * Author: Goke J 2020.03.07
 */

public class AVLTree<AnyType extends Comparable> {


    public static void main(String[] args){
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(6);
        tree.insert(4);
        tree.insert(9);
        tree.insert(3);
        tree.insert(1);  //LL旋转
        tree.insert(5);  //LR旋转
        tree.insert(7);
        tree.insert(10);
        tree.insert(13); //RR旋转
        tree.insert(8);  //RL旋转

        tree.remove(4);  //删除有两棵子树的节点
        tree.remove(1);
        tree.remove(5);

        AVLNode<Integer> foundNode1 = tree.search(9);
        AVLNode<Integer> foundNode2 = tree.search(11);

        System.out.println();
    }

    private static int MAX_BALANCE_FACTOR = 1;
    private AVLNode<AnyType> root;

    public AVLTree(){
    }

    //查找最小值节点
    public AVLNode<AnyType> findMin(){
        return findMin(this.root);
    }

    //查找最大值节点
    public AVLNode<AnyType> findMax(){
        return findMax(this.root);
    }

    // 获取一棵树的高度（验证是否为空）
    public static int height(AVLNode node) {
        return node == null ? -1 : node.height;
    }

    //查找节点
    public AVLNode<AnyType> search(AnyType theElement){
        return search(root, theElement);
    }

    // 插入节点
    public void insert(AnyType theElement) {
        root = insert(root, theElement);
    }

    //删除节点
    public void remove(AnyType theElement){
        root = remove(root, theElement);
    }

    //查找节点
    private AVLNode<AnyType> search(AVLNode<AnyType> root, AnyType theElement){
        if(root == null)
            return null;

        int compareResult = theElement.compareTo(root.element);

        if(compareResult == 0)
            return root;
        else if(compareResult < 0)
            return search(root.left, theElement);
        else
            return search(root.right, theElement);
    }

    // 插入节点
    private AVLNode<AnyType> insert(AVLNode<AnyType> node, AnyType theElement) {
        if (node == null) {
            return new AVLNode<AnyType>(theElement);  //递归出口：找到应该插入的位置，创建节点并返回
        }

        int compareResult = theElement.compareTo(node.element);
        if (compareResult < 0)
            node.left = insert(node.left, theElement);  //插入到左子树
        else if (compareResult > 0)
            node.right = insert(node.right, theElement);  //插入到右子树
        else
            ; //节点已存在，啥也不干

        return balance(node);
    }

    //删除节点
    private AVLNode<AnyType> remove(AVLNode<AnyType> node, AnyType theElement){
        if(node == null)
            return null;

        int compareResult = theElement.compareTo(node.element);
        if(compareResult < 0)
            node.left = remove(node.left, theElement);  //前往左子树删除
        else if(compareResult > 0)
            node.right = remove(node.right, theElement);  //前往右子树删除
        else{  //找到了要删除的节点
            if(node.left != null && node.right != null){  //左右子树均不为null
                AnyType minOfRight = findMin(node.right).element;
                node.element = minOfRight;  // 将右子树最小节点的值赋到根节点
                node.right = remove(node.right, minOfRight);  //删除右子树最小的节点
            } else {  //左右子树不同时为null
                return node.left == null ? node.right : node.left;
            }
        }

        return balance(node);  //返回调整后的树
    }

    // 维持AVL树的平衡
    private AVLNode<AnyType> balance(AVLNode<AnyType> node) {

        if (node == null)
            return null;

        //判断树是否保持平衡（因为只需用子树的高度判断，所以node高度之前无需更新）
        if (height(node.left) - height(node.right) > MAX_BALANCE_FACTOR) {  //插入在左子树导致失衡
            //下面的判断语句中用 >=号 而不是 >号
            //在insert中调用balance方法是不会出现等于的情况的，而在remove中可能会出现（且此时归类于LL和LR均可）
            if (height(node.left.left) >= height(node.left.right))
                node = rotateWithLeft(node);  //LL旋转
            else
                node = doubleWithLeft(node);  //LR旋转
        } else if (height(node.right) - height(node.left) > MAX_BALANCE_FACTOR) {  //插入至右子树导致失衡
            if (height(node.right.right) >= height(node.right.left))
                node = rotateWithRight(node);  //RR旋转
            else
                node = doubleWithRight(node);  //RL旋转
        }

        //这一行代码很有讲究，
        // i) 新插入的节点的高度是最新的
        // ii) insert自下而上返回时，会在尾部调用balance方法
        //     如果node失衡了，会在旋转后继续更新子树的高度，没有则保持不变
        //     最后直接用两棵子树的高度更新自己的高度（因为是自下而上，所有子树的高度已经被更新了）
        //     最终树的所有节点的高度都会被正确更新
        node.height = Math.max(height(node.left), height(node.right)) + 1;  //更新当前节点的高度
        return node;
    }

    /**
     * LL旋转
     * @param node 失衡处的节点
     * @return 调整后的树
     */
    private AVLNode<AnyType> rotateWithLeft(AVLNode<AnyType> node) {
        AVLNode<AnyType> leftOfNode = node.left;  //记住左节点

        //旋转
        node.left = leftOfNode.right;
        leftOfNode.right = node;
        //更新两个节点的高度（其余节点的子树没有变化，高度不变）
        //因为要兼顾 insert方法 和 remove方法，所以不能硬编码，而是利用子树的高度更新
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        leftOfNode.height = Math.max(height(leftOfNode.left), node.height) + 1;

        return leftOfNode;  //返回调整后的树
    }

    /**
     * RR旋转
     * @param node 失衡处的节点
     * @return 调整后的树
     */
    private AVLNode<AnyType> rotateWithRight(AVLNode<AnyType> node) {
        AVLNode<AnyType> rightOfNode = node.right;  //记住左节点

        //旋转
        node.right = rightOfNode.left;
        rightOfNode.left = node;
        //更新两个节点的高度（其余节点的子树没有变化，高度不变）
        node.height = Math.max(height(node.left), height(node.right)) + 1;
        rightOfNode.height = Math.max(node.height, height(rightOfNode.right)) + 1;

        return rightOfNode;  //返回调整后的树
    }

    /**
     * LR旋转
     * @param node 失衡处的节点
     * @return 调整后的树
     */
    private AVLNode<AnyType> doubleWithLeft(AVLNode<AnyType> node) {
        //双旋转可以被分解为两次单旋转
        //（每次单旋转不一定真的符合单旋转的前提条件，但两次组合在一起就能达到效果）
        node.left = rotateWithRight(node.left);
        return rotateWithLeft(node);
    }

    /**
     * RL旋转
     * @param node 失衡处的节点
     * @return 调整后的树
     */
    private AVLNode<AnyType> doubleWithRight(AVLNode<AnyType> node){
        //类同LR旋转
        node.right = rotateWithLeft(node.right);
        return rotateWithRight(node);
    }

    //查找最小值节点
    private AVLNode<AnyType> findMin(AVLNode<AnyType> root){
        if(root == null)
            return null;
        AVLNode<AnyType> mov = root;
        while (mov.left != null)
            mov = mov.left;
        return mov;
    }

    //查找最大值节点
    private AVLNode<AnyType> findMax(AVLNode<AnyType> root){
        if(root == null)
            return null;
        AVLNode<AnyType> mov = root;
        while (mov.right != null)
            mov = mov.right;
        return mov;
    }
}