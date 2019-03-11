package tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.Stack;

public final class BinarySearchTree<E extends Comparable<E>> implements Collection<E> {
  private Node root = null;
  private int size = 0;
  private Class<E> EClass;

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    return size == 0;
  }

  public boolean contains(Object data) {
    try {
      return contains(root, EClass.cast(data));
    } catch (ClassCastException e) {
      return false;
    }
  }

  private boolean contains(Node root, E value) {
    if (value.equals(root.data)) return true;
    if (value.compareTo(root.data) > 0) return contains(root.right, value);
    return contains(root.left, value);
  }

  public Iterator<E> iterator() {
    return new LDRIterator(this);
  }

  public Object[] toArray() {
    return new Object[0];
  }

  public <T> T[] toArray(T[] a) {
    return null;
  }

  public boolean add(E data) {
    this.root = insert(this.root, data);
    return true;
  }

  public boolean remove(Object o) {
    return false;
  }

  public boolean containsAll(Collection<?> c) {
    return false;
  }

  public boolean addAll(Collection<? extends E> c) {
    return false;
  }

  public boolean removeAll(Collection<?> c) {
    return false;
  }

  public boolean retainAll(Collection<?> c) {
    return false;
  }

  public void clear() {
    root = null;
  }

  private Node insert(Node root, E data) {
    if (root == null) return new Node(data);
    if (root.data.equals(data)) return root;
    if (root.data.compareTo(data) > 0) root.left = insert(root.left, data);
    else root.right = insert(root.right, data);
    return root;
  }

  protected class Node {
    E data;
    Node left = null;
    Node right = null;

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
        current = current.left;
      }
      /*
      after iteration, L is chosen if current has a left node, and if not, current is chosen
      * */
      current = stack.pop();
      Node node = current;
      current = current.right;

      return node.data;
    }

    public void remove() {

    }
  }
}
