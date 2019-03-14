/*
 * Author: Eric Deng (aka Antinux)
 * Copyright (c) 2019.
 */

import tree.BinarySearchTree;

import java.util.Scanner;

public class BSTTestTest {
  public static void main(String[] args) {
    BinarySearchTree<Integer> test = new BinarySearchTree<>();
    Scanner user_input = new Scanner(System.in);
    while (true) {
      switch (user_input.next(".").charAt(0)) {
        case 'a':
          System.out.println(test.add(user_input.nextInt()));
          break;
        case 'r':
          System.out.println(test.remove(user_input.nextInt()));
          break;
        case 'c':
          System.out.println(test.contains(user_input.nextInt()));
          break;
        case 's':
          for (Integer integer : test) {
            System.out.print(integer);
            System.out.print(',');
          }
          System.out.println();
          break;
        default:
          System.out.println("operation unknown");
      }
    }
  }
}
