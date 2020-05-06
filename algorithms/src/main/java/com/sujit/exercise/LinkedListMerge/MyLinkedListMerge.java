package com.sujit.exercise.LinkedListMerge;

public class MyLinkedListMerge {
    public ListNode doMerge(ListNode a, ListNode b){
        ListNode merged = null;
        while(true){
            int left = 0, right = 0;
            if(a!=null){
                left = a.val;
            }else{
                merged = addNode(merged, b);
                break;
            }
            if(b!=null){
                right = b.val;
            }else{
                merged = addNode(merged, a);
                break;
            }
            if(left < right){
                merged = addNode(merged, left);
                a = a.next;
            }else if(left > right){
                merged = addNode(merged, right);
                b = b.next;
            }else{
                merged = addNode(merged, left);
                merged = addNode(merged, right);
                a = a.next;
                b = b.next;
            }
        }
        return merged;
    }

    public String getFormattedList(ListNode node){
        StringBuilder sb = new StringBuilder("[");
        while(node!=null){
            sb.append(node.val);
            if(node.next!=null){
                sb.append(",");
            }else{
                sb.append("]");
            }
            node = node.next;
        }
        return sb.toString();
    }

    public ListNode addNode(ListNode node, int data){
        ListNode markerNode = node;
        ListNode newNode = new ListNode(data);
        if(markerNode!=null){
            while(markerNode.next!=null){
                markerNode = markerNode.next;
            }
            markerNode.next = newNode;
        }else{
            node = newNode;
        }
        return node;
    }

    public ListNode addNode(ListNode list, ListNode node){
        if(list == null){
            list = node;
        }else{
            ListNode header = list;
            while(header.next!=null){
                header = header.next;
            }
            header.next = node;
        }
        return list;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {this.val = x;}
}
