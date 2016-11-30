package skiplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SkipList<T extends Comparable<? super T>> {

    private static class SkipNode<T extends Comparable<? super T>> {
        private final T data;
        private final SkipNode<T>[] forward;
        private int[] span;

        @SuppressWarnings("unchecked")
        private SkipNode (T data, int level) {
            this.data = data;
            forward = new SkipNode[level];
            span = new int[level];
        }
    }

    private SkipNode<T> head;
    private SkipNode<T>[] update;
    private int[] index;

    private int size = 0, level = 1;
    private static final double P = 0.25;
    private int MAX_LEVEL = 32;
    private final Random random = new Random();

    private SkipList(int level) {
        this.MAX_LEVEL = level;
        build(MAX_LEVEL);
    }

    @SuppressWarnings("unchecked")
    private void build(int level) {
        head = new SkipNode<T>(null, level);
        update = new SkipNode[level];
        index = new int[level];

        for(int i = 0; i < level; i++) {
            head.forward[i] = head; //set head pointers and distance
            head.span[i] = 1;
        }
    }

    private SkipNode<T> searchByElement(T element) {
        SkipNode<T> curr = head;
        for (int i = level - 1; i >= 0; i--)
            while (curr.forward[i] != head && curr.forward[i].data.compareTo(element) < 0)
                curr = curr.forward[i];
        curr = curr.forward[0];

        if (curr != head && curr.data.equals(element))
            return curr;
        return null;
    }

    private int getElementIndex(T element) {
        SkipNode<T> curr = head;
        int ind = 0;
        for (int i = level - 1; i >= 0; i--) {
            while (curr.forward[i] != head && curr.forward[i].data.compareTo(element) < 0) {
                ind += curr.span[i];
                curr = curr.forward[i];
            }
        }
        curr = curr.forward[0];

        if (curr != head && curr.data.equals(element))
            return ind;
        return -1;
    }

    private SkipNode<T> searchByIndex(int index) {
        if(index > size)
            return null;

        SkipNode<T> curr = head;
        int ind = -1;
        for (int i = level - 1; i >= 0; i--)
            while (ind + curr.span[i] <= index) {
                ind += curr.span[i];
                curr = curr.forward[i];
            }
        return curr;
    }

    private boolean insert(T value) {
        if(value == null)
            return false;

        final int level_t = randomLevel();
        SkipNode<T> x = head;
        int i;
        int ind = 0;

        for(i = level - 1; i >= 0; i--) {
            while(x.forward[i] != head && x.forward[i].data.compareTo(value) < 0) {
                ind += x.span[i];
                x = x.forward[i];
            }
            update[i] = x;
            index[i] = ind;
        }

        if (level_t > level) {
            for(i = level; i < level_t; i++) {
                head.span[i] = size + 1;
                update[i] = head;
            }
            level = level_t;
        }

        x = new SkipNode<T>(value, level_t);
        for(i = 0; i < level; i++) {
            if (i > level_t - 1)
                update[i].span[i]++;
            else {
                x.forward[i] = update[i].forward[i];
                update[i].forward[i] = x;
                x.span[i] = index[i] + update[i].span[i] - ind;
                update[i].span[i] = ind + 1 - index[i];
            }
        }
        size++;
        return true;
    }

    private boolean remove(T element) {
        if(element == null)
            return false;

        SkipNode<T> x = head;
        for(int i = level - 1; i >= 0; i--) {
            while(x.forward[i] != head && x.forward[i].data.compareTo(element) < 0)
                x = x.forward[i];
            update[i] = x;
        }
        x = x.forward[0];
        if(x == head || x.data.compareTo(element) != 0)
            return false;
        delete(x, update);
        return true;
    }

    private void delete(SkipNode<T> node, SkipNode<T>[] update) {
        for (int i = 0; i < level; i++) {
            if (update[i].forward[i] == node) {
                update[i].forward[i] = node.forward[i];
                update[i].span[i] += node.span[i] - 1;
            } else {
                update[i].span[i]--;
            }
        }
        while (head.forward[level] == head && level > 1)
            level--;
        size--;
    }

    private int randomLevel() {
        int level = 1;
        while (random.nextInt(0xFFFF) < P * 0xFFFF){
            level += 1;
            if (level > MAX_LEVEL){
                break;
            }
        }
        return (level<MAX_LEVEL) ? level : MAX_LEVEL;
    }

    private void print(){
        for (int i = level - 1; i >= 0;i--){
            SkipNode<T> curr = head;
            System.out.print(curr.data+"---");
            while (curr.forward[i] != head) {
                for (int j = 1; j < curr.span[i]; j++){
                    System.out.print("   ---");
                }
                curr = curr.forward[i];
                System.out.print(curr.data+"---");
            }
            System.out.println();
        }
    }

    public static void main(String[] args){
        int max = 6;
        SkipList<Integer> x = new SkipList<Integer>(max);

        for (int i = 0; i < 25; i++){
            x.insert(new Random().nextInt(899)+100);
        }

        x.print();

        SkipList.SkipNode curr = x.searchByElement(25);
        SkipList.SkipNode indCur = x.searchByIndex(3);

        ArrayList<String> arrayList = new ArrayList<>();

        HashMap<String,String> hashMap = new HashMap<>();


    }

}
