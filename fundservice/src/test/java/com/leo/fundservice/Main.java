package com.leo.fundservice;

import org.junit.Test;

/**
 * @author leo-zu
 * @date 2021-07-19 7:23
 */
public class Main {
    @Test
    public void test() {
        int[][] mat = {{1,1,3,2,4,3,2},
                       {1,1,3,2,4,3,2},
                       {1,1,3,2,4,3,2}};
        int threshold = 4;
        System.out.print(maxSideLength(mat, threshold));
    }

    public int maxSideLength(int[][] mat, int threshold) {
        int m = mat.length; // 行数
        int n = mat[0].length; // 列数
        int f = Math.min(m, n); // 找出行列最小值
        int[][] prefix = new int[m+1][n+1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = prefix[i][j-1] + prefix[i-1][j] - prefix[i-1][j-1] + mat[i-1][j-1];
            }
        }

        // 从大到小遍历边
        for (int len=f; len>0; len--){
            for (int i=len; i<m+1; i++){
                for (int j=len; j<n+1; j++){
                    if (threshold >= sum(prefix, i, j, len)){
                        return len;
                    }
                }
            }
        }
        return 0;
    }

    private int sum(int[][] prefix, int row, int col, int len){
        return prefix[row][col] - prefix[row - len][col] - prefix[row][col - len] + prefix[row-len][col-len];
    }

}
