public class ListMerge {

    // Node class for linked list
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }

    // Function to merge two sorted linked lists
    static Node mergeLists(Node head1, Node head2) {
        // Create a dummy node to simplify handling edge cases
        Node dummy = new Node(0);
        Node current = dummy;

        // Traverse both lists simultaneously
        while (head1 != null && head2 != null) {
            if (head1.data < head2.data) {
                current.next = head1;
                head1 = head1.next;
            } else if (head1.data > head2.data) {
                current.next = head2;
                head2 = head2.next;
            } else {
                // Add only one instance of duplicate values
                current.next = head1;
                head1 = head1.next;
                head2 = head2.next;
            }
            current = current.next;
        }

        // Append remaining elements from either list
        if (head1 != null) {
            current.next = head1;
        } else {
            current.next = head2;
        }

        return dummy.next; // Return the merged list
    }

    // Function to print the elements of a linked list
    static void printList(Node head) {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    // Main method to test the mergeLists function
    public static void main(String[] args) {
        // Create two sorted linked lists
        Node head1 = new Node(1);
        head1.next = new Node(3);
        head1.next.next = new Node(5);
        head1.next.next.next = new Node(7);
        head1.next.next.next.next = new Node(9);

        Node head2 = new Node(2);
        head2.next = new Node(4);
        head2.next.next = new Node(6);
        head2.next.next.next = new Node(8);
        head2.next.next.next.next = new Node(10);

        // Merge the two lists and print the merged list
        Node mergedHead = mergeLists(head1, head2);
        printList(mergedHead);
    }
}
