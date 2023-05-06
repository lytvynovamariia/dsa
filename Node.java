public class Node {
    char name;
    String bo;
    int number;
    Node left;
    Node right;
    Node(char name, String bo){
        this.name = name;
        this.bo = bo;
        number = 1;
        left = null;
        right = null;
    }
}
