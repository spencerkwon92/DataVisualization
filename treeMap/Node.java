package univ.cis.cs490.treeMap;

public class Node {

    Node parent;
    int size;
    String fileName;

    Node(String fn, int size, Node pn){
        this.parent = pn;
        this.size = size;
        this.fileName = fn;

    }
}
