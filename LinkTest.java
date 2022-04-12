package com.clb.alth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class LinkTest {

    public static void main(String[] args) {
//        NodeUtil.printNode(merge(NodeUtil.build(new int[]{1, 4, 7, 9}), NodeUtil.build(new int[]{1, 2, 3, 8})));

//        System.out.println(maxNum(new int[]{1, 22, 18, 9, 6, 4, 3}));

//        NodeUtil.printNode(reverseOrderByAsc(NodeUtil.build(new int[]{1, 2, 3, 4, 5, 6, 7, 8}), 3));

//        NodeUtil.printNode(reverseOrderByDesc(NodeUtil.build(new int[]{1, 2, 3, 4, 5, 6, 7, 8}), 3));

//        Node head = NodeUtil.build(new int[]{1, 2, 3, 4, 5, 6, 7});
//        head.next.next.next.next.next.next.next = head.next.next.next;
//        System.out.println(isRecycle(head)); //123[4567->4567]

//        NodeUtil.printNode(lastKNode(NodeUtil.build(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}), 3));

//        System.out.println(find(2, new int[][]{ {1,2,8,9}, {2,4,9,12}, {4,7,10,13}, {6,8,11,15} }));


        TreeNode root = new TreeNode(1,
                new TreeNode(2, new TreeNode(4, null, null), new TreeNode(5, null, null)),
                new TreeNode(3, new TreeNode(6, null, null), new TreeNode(7, null, null))
        );
        List<Integer> list = new ArrayList<>();
        dfs(root, list);
        System.out.println(list.toString());
    }

    /**
     * 合并两个链表，返回合并后链表的头结点
     * 思想：判断表头元素大小，顺序加入新链表中
     * @param head1 单调递增链表
     * @param head2 单调递增链表
     * @return
     */
    public static Node merge(Node head1, Node head2) {
        if (head1 == null || head2 == null) {
            return head2 == null ? head1 : head2;
        }

        Node head, cur;

        if (head1.val <= head2.val) {
            head = head1;
            head1 = head1.next;
        } else {
            head = head2;
            head2 = head2.next;
        }
        cur = head;

        while (true) {
            if (head2 == null) {
                cur.next = head1;
                break;
            }
            if (head1 == null) {
                cur.next = head2;
                break;
            }
            if (head1.val <= head2.val) {
                cur.next = head1;
                head1 = head1.next;
            } else {
                cur.next = head2;
                head2 = head2.next;
            }
            cur = cur.next;
        }

        return head;
    }

    /**
     * 对给定链表反转(从后向前反转)
     * 思想：分治法
     * @param head 链表, 如：1->2->3->4->5->6->7->8
     * @param n 反转长度
     * @return 如n=3时, 上面链表反转后结果为：1->2->5->4->3->8->7->6
     */
    public static Node reverseOrderByDesc(Node head, int n) {
        Node reverse1 = reverse(head);

        Node reverseHead = reverseOrderByAsc(reverse1, n);

        return reverse(reverseHead);
    }

    /**
     * 对给定链表反转
     * 思想：分治法
     * @param head 链表, 如：1->2->3->4->5->6->7->8
     * @param n 反转长度
     * @return 如n=3时, 上面链表反转后结果为：3->2->1->6->5->4->7->8
     */
    public static Node reverseOrderByAsc(Node head, int n) {

        Node reverseResultHead = null;
        Node reverseResultTail = null;

        do {
            Node checkNode = head;
            int c = n;
            while (c-- > 0 && checkNode != null) {
                checkNode = checkNode.next;
            }
            if (c >= 0) {
                break;
            }

            Node rNode = reverseKNode(head, n);

            int k = n;
            Node tmpHead = rNode;
            while (k-- > 0 && tmpHead != null) {
                tmpHead = tmpHead.next;
            }

            head = tmpHead;

            if (reverseResultHead == null) {
                reverseResultHead = rNode;

                Node tmpNode = reverseResultHead;
                int t = n;
                while (t-- > 1) {
                    reverseResultTail = tmpNode.next;
                    tmpNode = tmpNode.next;
                }
            } else {
                reverseResultTail.next = rNode;

                int t = n;
                while (t-- > 0) {
                    reverseResultTail = reverseResultTail.next;
                }
            }

        } while (true);

        return reverseResultHead;
    }

    /**
     * 反转单链表
     * @param head
     * @return
     */
    public static Node reverse(Node head) {
        Node tail = head;
        Node cur = head.next;

        while (cur != null) {
            Node tmp = head;
            head = cur;
            cur = cur.next;
            tail.next = cur;
            head.next = tmp;
        }

        return head;
    }

    /**
     * 反转前k个节点
     * @param head 链表
     * @param k 反转节点个数
     * @return
     */
    public static Node reverseKNode(Node head, int k) {
        Node tail = head;
        Node cur = head.next;

        while (cur != null && --k > 0) {
            Node tmp = head;
            head = cur;
            cur = cur.next;
            tail.next = cur;
            head.next = tmp;
        }

        return head;
    }

    /**
     * 判断链表是否有环
     * 思想：快慢指针
     * @param head
     * @return
     */
    public static boolean isRecycle(Node head) {
        Node slower = head;
        Node faster = head.next;
        while (faster != null && slower != null) {
            //System.out.println("slower:" + slower.val + ", faster:" + faster.val);
            if (faster == slower) {
                return true;
            }
            slower = slower.next;
            if (faster.next == null) {
                break;
            }
            faster = faster.next.next;
        }

        return false;
    }


    /**
     * 链表中倒数最后k个结点
     * 思想：通过快慢指针的步长，计算倒数k的起始Node的位置
     * @param head
     * @return
     */
    public static Node lastKNode(Node head, int k) {
        Node slower = head;
        Node faster = head.next;

        int slowerIndex = 0;
        int len = 1; //快慢指针的间距长度
        while (faster != null && slower != null) {
            slower = slower.next;
            slowerIndex++;
            if (faster.next == null) {
                break;
            }
            faster = faster.next.next;
            len++;
        }

        Node start = slower;
        if (len >= k) {
            while (len-- - k > 0) {
                start = start.next;
            }
        } else {
            int startIndex = slowerIndex - (k - len);
            start = head;
            while (startIndex-- > 0) {
                start = start.next;
            }
        }

        return start;
    }


        /**
         * 求给定特点数组的最大元素
         * 思想：二分法，求升序的最大元素即可
         * @param array 元素不重复，先升序后降序；例如：[1, 2, 8, 7, 6, 4, 3]
         * @return
         */
    public static int maxNum(int[] array) {
        if (array == null || array.length < 3) {
            throw new RuntimeException("array items lost 3");
        }

        int left = 1;
        int right = array.length - 2;

        while (left < right) {
            if (left == right-1) {
                left = array[left] > array[right] ? left : right;
                break;
            }
            int mid = (left + right) / 2;
            //判断中间两个元素的升降序
            if (array[mid] < array[mid+1]) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return array[left];
    }

    /**
     * 描述
     * 在一个二维数组array中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，
     * 每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     * [
     * [1,2,8,9],
     * [2,4,9,12],
     * [4,7,10,13],
     * [6,8,11,15]
     * ]
     *
     * 给定 target = 7，返回 true。
     * 给定 target = 3，返回 false。
     *
     * 数据范围：矩阵的长宽满足 0≤n,m≤5000 \le n,m \le 5000≤n,m≤500 ， 矩阵中的值满足 0≤val≤1090 \le val \le 10^90≤val≤109
     * 进阶：空间复杂度 O(1)O(1)O(1) ，时间复杂度 O(n+m)O(n+m)O(n+m)
     * @param target
     * @param array
     * @return
     */
    public static boolean find(int target, int [][] array) {
        if(array == null || array[0] == null || array[0].length == 0) {
            return false;
        }

        int mid;
        for(int i = array.length-1, j = 0; i >= 0 && j < array[0].length;) {
            if((mid = array[i][j]) == target) {
                return true;
            }

            if(mid > target) {
                i--;
            } else {
                j++;
            }
        }

        return false;
    }

    public static void dfs(TreeNode root, List<Integer> result) {
        if (root == null) {
            return ;
        }

        //先序
        result.add(root.val);
        dfs(root.left, result);
        dfs(root.right, result);

        //中序
//        dfs(root.left, result);
//        result.add(root.val);
//        dfs(root.right, result);

        //后序
//        dfs(root.left, result);
//        dfs(root.right, result);
//        result.add(root.val);
    }


}
