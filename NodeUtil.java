package com.clb.alth;

import java.util.ArrayList;
import java.util.List;

public class NodeUtil {

    public static Node build(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        Node head = new Node(array[0]);
        Node cur = head;
        int idx = 1;
        while (idx < array.length) {
            cur.next = new Node(array[idx++]);
            cur = cur.next;
        }

        return head;
    }

    public static void printNode(Node head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        System.out.println(list.toString());
    }

    public static TreeNode buildTreeXXX(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        int[] levelNodeCount = {1, 2, 4, 8, 16, 32, 64, 128};
        int maxLevel = (int) (Math.log(array.length) / Math.log(2));
        TreeNode root = new TreeNode(array[0], null, null);
        int level = 1;
//        while (true) {
//            root.left = new TreeNode(array[level], null, null);
//            root.right = new TreeNode(array[level+1], null, null);
//        }

        return null;

    }

    public static TreeNode buildTree(int[] array) {
        if (array == null || array.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(array[0], null, null);
        List<TreeNode> levelNodeList = new ArrayList<>();
        levelNodeList.add(root);

        int level = 1;
        for (int i = 1; i < array.length; i=2*i+1) {

            List<TreeNode> children = new ArrayList<>();
            for (int k = i; k < 2*level + i && k < array.length; i++) {
                children.add(new TreeNode(array[i], null, null));
            }

            for (int m = 0, n = 0; m < levelNodeList.size() && n < children.size(); m++, n+= 2) {
                levelNodeList.get(m).left = children.get(n);
                levelNodeList.get(m).right = children.get(n+1);
            }

        }

        return root;
    }

}
