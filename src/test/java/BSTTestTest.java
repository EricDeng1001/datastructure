/*
 * Author: Eric Deng (aka Antinux)
 * Copyright (c) 2019.
 */

import tree.BinarySearchTree;

public class BSTTestTest {
  public static void main(String[] args) {
    BinarySearchTree<Integer> test = new BinarySearchTree<Integer>();
    test.add(1);
    test.add(-1);
    test.add(3);
    test.add(5);
    test.add(10);
    for (Integer integer : test) {
      System.out.println(integer);
    }
  }
}
