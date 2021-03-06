// 题目描述
// 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字
// 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
// 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.

package exercise_19;

import java.util.ArrayList;
import java.util.Arrays;

public class Solution {

    public static void main(String[] args){
        int[][] matrix1 = {{1, 2, 3, 4 }, {5, 6, 7, 8 }, {9, 10, 11, 12 }, {13, 14, 15, 16}};
        int[][] matrix2 = {{1}, {2}, {3}, {4}, {5}};
        int[][] matrix = {{1, 2, 3, 4, 5}};

        ArrayList<Integer> arrayList = new Solution().printMatrix(matrix);
        System.out.println(Arrays.toString(arrayList.toArray()));
    }

    public ArrayList<Integer> printMatrix(int [][] matrix) {
        if(matrix == null)
            return null;
        if(matrix.length == 0)
            return new ArrayList<>();

        ArrayList<Integer> answer = new ArrayList<>();
        int width = matrix[0].length, height = matrix.length, i, j;  //矩阵的宽度、高度，两个循环变量
        int circles = (Math.min(width, height) + 1) / 2;

        //circle代表循环的圈数
        for(int circle = 0; circle < circles; circle++){
            for(j = circle, i = circle; j < width-circle; j++)
                answer.add(matrix[i][j]);
            for(i = circle+1, j = width-circle-1; i < height-circle; i++)
                answer.add(matrix[i][j]);
            for(j = width-circle-2, i = height-circle-1; j >= circle && height-circle-1 != circle; j--)
                answer.add(matrix[i][j]);
            for(i = height-circle-2, j = circle; i > circle && width-circle-1 != circle; i--)
                answer.add(matrix[i][j]);
        }

        return answer;
    }
}