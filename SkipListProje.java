import java.util.Random;

class SkipListNode<T extends Comparable<? super T>> {
    T value;
    SkipListNode<T>[] forward;

    @SuppressWarnings("unchecked")
    public SkipListNode(T value, int level) {
        this.value = value;
        this.forward = new SkipListNode[level + 1];
    }
}

class SkipList<T extends Comparable<? super T>> {
    private static final int MAX_LEVEL = 16;
    private SkipListNode<T> head;
    private int level;

    public SkipList() {
        this.level = 0;
        this.head = new SkipListNode<>(null, MAX_LEVEL);
    }

    public void addNode(T value) {
        // Array to store nodes to update while traversing the list
        @SuppressWarnings("unchecked")
        SkipListNode<T>[] update = new SkipListNode[MAX_LEVEL + 1];
        SkipListNode<T> current = head;

        // Traverse the list to find the appropriate position to insert the new node
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
            update[i] = current;
        }
        current = current.forward[0];

        // Insert the new node if it doesn't exist in the list
        if (current == null || !current.value.equals(value)) {
            int newLevel = randomLevel();
            if (newLevel > level) {
                // Update the update array with head for levels higher than current level
                for (int i = level + 1; i <= newLevel; i++) {
                    update[i] = head;
                }
                level = newLevel; // Update the maximum level of the list
            }
            // Create a new node and link it in the list at appropriate levels
            SkipListNode<T> newNode = new SkipListNode<>(value, newLevel);
            for (int i = 0; i <= newLevel; i++) {
                newNode.forward[i] = update[i].forward[i];
                update[i].forward[i] = newNode;
            }
        }
    }

    // Generate a random level for a new node
    private int randomLevel() {
        int lvl = 0;
        Random random = new Random();
        while (random.nextDouble() < 0.5 && lvl < MAX_LEVEL) {
            lvl++;
        }
        return lvl;
    }

    // Check if a value exists in the list
    public boolean contains(T value) {
        SkipListNode<T> current = head;
        for (int i = level; i >= 0; i--) {
            while (current.forward[i] != null && current.forward[i].value.compareTo(value) < 0) {
                current = current.forward[i];
            }
        }
        current = current.forward[0];
        return current != null && current.value.equals(value);
    }
}

public class SkipListProje {
    public static void main(String[] args) {
        SkipList<Integer> list = new SkipList<>();

        // Adding nodes to the skip list
        list.addNode(3);
        list.addNode(6);
        list.addNode(7);
        list.addNode(9);
        list.addNode(12);
        list.addNode(19);
        list.addNode(17);

        // Checking if the list contains certain keys
        System.out.println("Contains 6: " + list.contains(6)); // true
        System.out.println("Contains 15: " + list.contains(15)); // false
    }
}
