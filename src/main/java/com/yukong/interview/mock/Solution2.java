package com.yukong.interview.mock;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author yukong
 * @date 2019-06-12 15:05
 */
public class Solution2 {

    //bfs
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] visited = new int[n][m];
        Comparator<Node> cmp = new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return a.val - b.val;
            }
        };
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        PriorityQueue<Node> q = new PriorityQueue<Node>(n * m, cmp);
        Node node = new Node(grid[0][0], 0, 0);
        q.offer(node);
        visited[0][0] = 1;
        while(!q.isEmpty()) {
            Node cur = q.poll();
            if(cur.x == n - 1 && cur.y == m - 1) {
                return cur.val;
            }

            for(int k = 0; k < 4; k++) {
                int nx = cur.x + dx[k];
                int ny = cur.y + dy[k];
                if(isInGrid(nx, ny, n, m) && visited[nx][ny] == 0) {
                    grid[nx][ny] = Math.max(grid[cur.x][cur.y], grid[nx][ny]);
                    q.offer(new Node(grid[nx][ny], nx, ny));
                    visited[nx][ny] = 1;
                }
            }
        }

        return 0;
    }

    public boolean isInGrid(int x, int y, int n, int m) {
        if(x >= 0 && x < n && y >= 0 && y < m) {
            return true;
        }
        return false;
    }


    class Node {
        public int val;
        public int x;
        public int y;
        Node(int val, int x, int y) {
            this.val = val;
            this.x = x;
            this.y = y;
        }
    }
}
