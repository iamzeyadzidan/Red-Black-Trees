package com.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     *
     * @param args The arguments of the program.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        RBT<String> redBlackTree = new RBT<>();
        Long before;
        Long after;

        // SCAN DICTIONARY
        Scanner scanner = new Scanner(new File("dictionary.txt"));
        ArrayList<String> stringList = new ArrayList<String>(); // SAVE ITEMS IN AN ARRAYLIST OF STRINGS
        while (scanner.hasNext()) {
            String next = scanner.next();
            if (!stringList.contains(next)) {
                stringList.add(next);
            }
        }
        scanner.close(); // END OF SCANNING

        // COPYING THE ELEMENTS TO THE RED-BLACK TREE
        before = System.nanoTime();
        for (Integer index = 0; index < stringList.size(); index++) {
            redBlackTree.insert(stringList.get(index));
        }
        after = System.nanoTime();

        System.out.println("... FILE LOADED ...");
        System.out.println("Time Taken: " +((after-before)*Math.pow(10,-6))+ "ms\n");
        redBlackTree.preOrderTraversal();
        System.out.println();
        Scanner sc = new Scanner(System.in);
        boolean on=true;
        while (on) {
            System.out.println("insertion Choose [A]: ");
            System.out.println("deletion Choose [B]: ");
            System.out.println("close loop Choose [E]: ");
            String inputWay = sc.nextLine();
            if (inputWay.equalsIgnoreCase("a")) {
                System.out.println("please Enter Element");
                String add=sc.nextLine();
                before=System.nanoTime();
                redBlackTree.insert(add);
                after=System.nanoTime();
                System.out.println("Time Taken: " +((after-before)*Math.pow(10,-6))+ "ms\n");
                redBlackTree.preOrderTraversal();
                System.out.println();
            }
            else if (inputWay.equalsIgnoreCase("b")) {
                String delete=sc.nextLine();
                System.out.println("please Enter Element");
                before=System.nanoTime();
                redBlackTree.insert(delete);
                after=System.nanoTime();
                System.out.println("Time Taken: " +((after-before)*Math.pow(10,-6))+ "ms\n");
                System.out.println();
                redBlackTree.preOrderTraversal();
                System.out.println();
            }
            else if (inputWay.equalsIgnoreCase("E")){
                on=false;
                System.out.println("Do You wanna to patch delete?[y]");
            }
        }
        String in=sc.nextLine();
        if(in.equalsIgnoreCase("y")) {
            scanner = new Scanner(new File("deletions.txt"));
            stringList.clear();
            while (scanner.hasNext()) {
                String next = scanner.next();
                if (!stringList.contains(next)) {
                    stringList.add(next);
                }
            }
            scanner.close();
            System.out.println();
            for (Integer index = 0; index < stringList.size(); index++) {
                 before = System.nanoTime();
                redBlackTree.delete(stringList.get(index));
                after = System.nanoTime();
                System.out.println("Time Taken: " +((after-before)*Math.pow(10,-6))+ "ms\n");
            }
            redBlackTree.preOrderTraversal();
        }
    }

    // METHOD TO WRITE TO DICTIONARY IN A PROGRAM [AND USER]-FRIENDLY WAY
    public static void stringHandle(ArrayList<String> stringList) throws IOException {
        String stringListSTR = stringList.toString();
        stringListSTR = stringListSTR.replaceAll(", ", "\n");
        stringListSTR = stringListSTR.replace("[", "");
        stringListSTR = stringListSTR.replace("]", "");
        BufferedWriter writer = new BufferedWriter(new FileWriter("dictionary.txt"));
        writer.write(stringListSTR);
        writer.close();
    }
}
