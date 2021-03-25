package io.github.grooters.libraryseat;

import java.util.HashMap;
import java.util.Map;

public class Test {

    private static int[] preOrder = {3, 9, 20, 15, 7};
    private static int[] inOrder = {9, 3, 15, 20, 7};

    private static Map<Integer, Integer> inHashMap = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < inOrder.length; i++) {
            inHashMap.put(inOrder[i], i);
        }
        TreeNode node = buildTree(0, inOrder.length, 0);
        System.out.println("node.left.value:" + node.left.value);
    }

    private static TreeNode buildTree(int left, int right, int preRoot) {
        if (left > right) {
            return null;
        }
        TreeNode node = new TreeNode(preOrder[preRoot]);
        Integer inRoot = inHashMap.get(preOrder[preRoot]);
        if (inRoot == null) {
            System.err.println("Error");
            return null;
        }
        node.left = buildTree(left, inRoot - 1, preRoot + 1);
        node.right = buildTree(preRoot + (inRoot - left) + 1, inRoot + 1, right);
        return node;
    }

    private static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;
        public TreeNode(int value) {
            this.value = value;
        }
    }
}
