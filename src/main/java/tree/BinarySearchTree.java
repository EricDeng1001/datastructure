package tree;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public final class BinarySearchTree<E extends Comparable<E>> implements Collection<E> {
  private Node root;
  private int size;
  private boolean last_operation_effected;

  public BinarySearchTree() {
    root = null;
    size = 0;
    last_operation_effected = false;
  }

  public BinarySearchTree(BinarySearchTree<E> to_copy) {
    size = to_copy.size;
    last_operation_effected = to_copy.last_operation_effected;
    root = deepClone(to_copy.root);
  }

  public static BinarySearchTree from(BinarySearchTree tree) {
    return new BinarySearchTree(tree);
  }

  private Node deepClone(Node to_copy) {
    if (to_copy == null) return null;
    Node new_node = new Node(to_copy.data);
    new_node.leftChild = deepClone(to_copy.leftChild);
    new_node.rightChild = deepClone(to_copy.rightChild);
    return new_node;
  }

  public boolean isLastOperationEffected() {
    return last_operation_effected;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public Iterator<E> iterator() {
    return new LDRIterator(this);
  }

  @NotNull
  public Object[] toArray() {
    Object[] result = new Object[size];
    Iterator it = new LDRIterator(this);
    for (int i = 0; i < size; i++) {
      result[i] = it.next();
    }
    return result;
  }

  @NotNull
  public <T> T[] toArray(@NotNull T[] a) {
    Iterator it = new LDRIterator(this);
    for (int i = 0; i < size; i++) {
      a[i] = (T) it.next();
    }
    return a;
  }

  public boolean add(E data) {
    root = insert(root, data);
    size++;
    return last_operation_effected;
  }

  private Node insert(Node root, E data) {
    if (root == null) return new Node(data);
    if (root.data.equals(data)) return root;
    if (root.data.compareTo(data) > 0) root.leftChild = insert(root.leftChild, data);
    else root.rightChild = insert(root.rightChild, data);
    return root;
  }

  public boolean containsAll(@NotNull Collection<?> c) {
    for (Object o : c) {
      if (!contains(o)) {
        return false;
      }
    }
    return true;
  }

  public boolean contains(Object data) {
    try {
      return search(root, (E) (data));
    } catch (ClassCastException e) {
      return false;
    }
  }

  private boolean search(Node root, E value) {
    int compare_result = value.compareTo(root.data);

    while (root != null) {
      if (compare_result == 0) return true;
      if (compare_result < 0) {
        root = root.leftChild;
      } else {
        root = root.rightChild;
      }
    }
    return false;
  }

  public boolean addAll(Collection<? extends E> c) {
    boolean changed = true;
    for (E e : c) {
      changed &= add(e);
    }
    return changed;
  }

  public boolean removeAll(Collection<?> c) {
    boolean changed = true;
    for (Object o : c) {
      changed &= remove(o);
    }
    return changed;
  }

  public boolean remove(Object to_remove) {
    root = delete(root, (E) to_remove);
    size--;
    return last_operation_effected;
  }

  private Node delete(Node root, E e) {
    Node parent = null;
    Node to_remove = root;
    Node after_removed;

    for (
      int compare_result = e.compareTo(to_remove.data);
      compare_result != 0;
      compare_result = e.compareTo(to_remove.data)
    ) {
      parent = to_remove;
      if (compare_result > 0) {
        to_remove = to_remove.rightChild;
      } else {
        to_remove = to_remove.leftChild;
      }

      if (to_remove == null) {
        last_operation_effected = false;
        return root;
      }
    }

    if (to_remove.leftChild == null && to_remove.rightChild == null) { // n,n
      after_removed = null;
    } else if (to_remove.leftChild != null && to_remove.rightChild == null) { // l,n
      after_removed = to_remove.leftChild;
    } else if (to_remove.leftChild == null) { // n,r
      after_removed = to_remove.rightChild;
    } else { // l,r
      after_removed = popMin(to_remove.rightChild);
      after_removed.leftChild = to_remove.leftChild;
      after_removed.rightChild = to_remove.rightChild;
    }

    if (parent == null) {
      root = after_removed;
    } else {
      if (parent.rightChild == to_remove) {
        parent.rightChild = after_removed;
      } else {
        parent.leftChild = after_removed;
      }
    }
    last_operation_effected = true;
    return root;
  }

  /**
   * this method must be called with a root has both leftChild and rightChild
   * delete the min value Node form the origin tree and return it.
   *
   * @param root has both leftChild and rightChild
   * @return the min value Node
   */
  private Node popMin(Node root) {
    Node parent = root;
    Node current = root.leftChild;

    while (current.leftChild != null) {
      parent = current;
      current = current.leftChild;
    }

    parent.leftChild = null;

    return current;
  }

  public boolean retainAll(@NotNull Collection<?> c) {
    return false;
  }

  public void clear() {
    root = null;
  }

  protected class Node {
    E data;
    Node leftChild = null;
    Node rightChild = null;

    Node(E data) {
      this.data = data;
    }
  }

  protected class LDRIterator implements Iterator<E> {
    private Stack<Node> stack = new Stack<Node>();
    private Node current;

    LDRIterator(BinarySearchTree<E> tree) {
      current = tree.root;
    }

    public boolean hasNext() {
      return current != null || !stack.empty();
    }

    /*
     * */
    public E next() {
      while (current != null) {
        stack.push(current);
        current = current.leftChild;
      }
      /*
      after iteration, L is chosen if current has a leftChild node, and if not, current is chosen
      * */
      current = stack.pop();
      Node node = current;
      current = current.rightChild;

      return node.data;
    }

    public void remove() {

    }
  }
}
