import javax.swing.*;
import java.util.*;
import java.util.LinkedHashSet;

public class Tree {
    Node root;
    Node nula = new Node('0', "");
    Node jeden = new Node('1', "");
    String expression;
    private HashMap<String, Node> hash_map = new HashMap<>();
    int num_red = 0;
    int number = 0;
    public BDD create(String bool, String queue){
        expression = queue;
        HashTable table = new HashTable();
       // root = create_help(root, bool, queue, table);
        root = create_help(root, bool, queue, 0);
        String[] str = bool.split("\\+");
        for(int i = 0; i < str.length; i++){
            root = add_last(root, str[i], queue);
        }
        root = add_nulu(root);
        root.number += 2;
        System.out.println("root number: " + root.number);
        BDD bdd = new BDD(root, number, queue.length());
        return bdd;
    }
    //private Node create_help(Node root, String bool, String queue, HashTable table){
        private Node create_help(Node root, String bool, String queue, int level){
        if(bool != null && !bool.isBlank() && queue != null && !queue.isBlank() && !bool.equals("1")
                && !bool.equals("0") && !bool.equals("+")) { //check if we have a queue and boolean
            char qu = queue.charAt(0); //create an array
            if (bool.contains("++"))
                bool = bool.replace("++", "+");
            if (bool.charAt(0) == '+')
                bool = bool.substring(1, bool.length());
            root = hash_map.get(bool+level);
            if(root != null)
                return root;

      //      if (root == null) {

              //  table.add(root.name, root.bo);
          //  }

            String neg = "!" + qu; //create a string from a !first element in a queue
            String pos = "" + qu;  //create a string from a first element
            LinkedHashSet<String> set = new LinkedHashSet<String>(); //uses for deleting copy elements
            String[] help =  bool.split("\\+"); //make an array from boolean expression
            for(int i = 0; i < help.length; i++){
                set.add(help[i]);
            }
          //  System.out.println("SET " + set);
            help = new String[set.size()];
            help = set.toArray(help); //assign elements without duplicates
            String first = "";   //use this for recursion with negative expressions
            String second = "";  //use this with positive expressions
            for (int i = 0; i < help.length; i++) {
                if (help[i].contains(neg)) { //if it contains !first element in queue
                    help[i] = help[i].replace(neg, "0"); //replace
                    if (help[i].contains(pos)) {
                        help[i] = help[i].replace(pos, "1");
                        first = first.concat(help[i]); //add a new string to our buffer string
                        first += "+"; //add +
                    }
                    first = first.concat(help[i]);
                    first += "+";
                } else if (help[i].contains(pos)) { //if it contains first element in a queue
                    help[i] = help[i].replace(pos, "1");
                    second = second.concat(help[i]);
                    second += "+";

                } else { //if it is not contain first or ! element - add to all buffer strings
                    if (help[i] != null && !help[i].isBlank()) {
                        first = first.concat(help[i]);
                        first += "+";
                        second = second.concat(help[i]);
                        second += "+";
                    }
                }
            }
            if (first != null && !first.isBlank())  //reduce strings
                first = first.substring(0, first.length() - 1); //delete the last +
            if (second != null && !second.isBlank())
                second = second.substring(0, second.length() - 1); //delete the last +
            if (queue != null && !queue.isBlank())
                queue = queue.substring(1, queue.length()); //delete first element in the queue
            if (first.equals(second)) { //if our element`s children are equal
                int k = 1;
                while (k == 1) {
                    if (first != null && !first.isBlank() && second != null && !second.isBlank() && queue != null &&
                            !queue.isBlank()) {
                        qu = queue.charAt(0);
                        root = new Node(qu, bool);
                     //   table.add(root.name, root.bo);
                        neg = "!" + qu;
                        pos = "" + qu;
                        String[] arr_help = first.split("\\+");
                        for(int i = 0; i < arr_help.length; i++){
                            set.add(arr_help[i]);
                        }
                        arr_help = new String[set.size()];
                        arr_help = set.toArray(arr_help);
                        first = "";
                        second = "";
                        for (int i = 0; i < arr_help.length; i++) {
                            if (arr_help[i].contains(neg)) {
                                arr_help[i] = arr_help[i].replace(neg, "0");
                                first = first.concat(arr_help[i]);
                                first += "+";
                            } else if (arr_help[i].contains(pos)) {
                                arr_help[i] = arr_help[i].replace(pos, "1");
                                second = second.concat(arr_help[i]);
                                second += "+";

                            } else {
                                if (arr_help[i] != null && !arr_help[i].isBlank()) {
                                    first = first.concat(arr_help[i]);
                                    first += "+";
                                    second = second.concat(arr_help[i]);
                                    second += "+";
                                }
                            }
                        }
                        if (first != null && !first.isBlank())
                            first = first.substring(0, first.length() - 1);
                        if (second != null && !second.isBlank())
                            second = second.substring(0, second.length() - 1);
                        if (queue != null && !queue.isBlank())
                            queue = queue.substring(1, queue.length());

                        if (first.equals(second) != true || first == null || first.isBlank() || second == null ||
                                second.isBlank())
                            k = 0;

                    } else
                        k = 0;

                }
            }
            //   root = table.search(queue.charAt(0), )
            // System.out.println("\troot is " + root.name);
            //     first = first.replace("0+", "");
            //   first = first.replace("+0", "");
            first = first.replace("0", "");
            //    second = second.replace("1+", "");
            //  second = second.replace("+1", "");
            second = second.replace("1", "");


            root = new Node(qu, bool);
            hash_map.put(bool + level, root);
            number++;
       //     table.add(root.name, root.bo, first, second, queue.charAt(0));
            if(first != null && !first.isBlank()){
                if (first.contains("++"))
                    first = first.replace("++", "+");
                if (first.charAt(0) == '+')
                    first = first.substring(1, first.length());
            }
            if(second != null && !second.isBlank()){
                if (second.contains("++"))
                    second = second.replace("++", "+");
                if (second.charAt(0) == '+')
                    second = second.substring(1, second.length());
            }
            if(first != null && !first.isBlank()){
                help = first.split("\\+");
                first = "";
                set.clear();
                for(int i = 0; i < help.length; i++){ //delete duplicates
                    set.add(help[i]);
                }
                help = new String[set.size()];
                help = set.toArray(help);
                for(int i = 0; i < help.length; i++){ //add elements to the first
                    first += help[i];
                    first += "+";
                }
                if(first != null && !first.isBlank())
                    first = first.substring(0, first.length()-1);
            }
            level++;
            if (queue != "") {
                if (hash_map.get(first+level) != null && hash_map.get(second+level) != null) {
                    root.left = hash_map.get(first+(level+1));
                    root.right = hash_map.get(second+(level+1));
                  //  num_red += (root.left.number + root.right.number);
                }
                else if(second.equals("") && hash_map.get(first+level) != null){
                    root.left = hash_map.get(first+(level+1));
                }
                else if(first.equals("") && hash_map.get(second+level) != null){
                    root.right = hash_map.get(second+(level+1));
                }
                else{
                    root.left = create_help(root.left, first, queue, level);
                    root.right = create_help(root.right, second, queue, level);
                }

            }
            else{
              //  root.left = create_help(root.left, first, queue, table);
              //  root.right = create_help(root.right, second, queue, table);
            root.left = create_help(root.left, first, queue, level+1);
            root.right = create_help(root.right, second, queue, level+1);
                if (root.left != null && root.right != null) {
                    root.number = root.left.number + root.right.number + 1;
                } else if (root.left != null && root.number == 1) {
                    root.number = root.left.number + 1;
                } else if (root.right != null && root.number == 1) {
                    root.number = root.right.number + 1;
                }
            }
        }
        else
            return root;
        return root;
    }
    private Node add_last(Node root, String bool, String queue){
        if(bool != null && !bool.isBlank() && queue != null && !queue.isBlank()) { //if we have a queue and boolean
            char qu = queue.charAt(0); //find a first character in queue
            if (root == null)
                root = jeden;
            else {
                if (qu != root.name) { //if we have S-reduction
                    int k = 1;
                    while (k == 1) { //skip letters in queue
                        if(queue != null && !queue.isBlank()) {
                            queue = queue.substring(1, queue.length());
                            if(queue != null && !queue.isBlank())
                                qu = queue.charAt(0);
                            else
                                k = 0;

                            if (qu == root.name)
                                k = 0;
                        }
                        else
                            return root;
                    }
                }
                String first = "!" + qu;
                String second = "" + qu;
                if (bool.contains(first)) { //if we have !A
                    if (bool != null && !bool.isBlank())
                        bool = bool.replace(first, "");
                    if (bool.contains(second)) { //if we have !AA
                        if (bool != null && !bool.isBlank())
                            bool = bool.replace(second, "");
                        if (queue != null && !queue.isBlank())
                            queue = queue.substring(1, queue.length());
                        if (root != nula && root != jeden) {
                            if (root == null)
                                root = add_last(root, bool, queue);
                            else {
                                root.left = add_last(root.left, bool, queue);
                                root.right = add_last(root.right, bool, queue);
                            }
                        }
                    } else {
                        if (queue != null && !queue.isBlank())
                            queue = queue.substring(1, queue.length());
                        if (root != nula && root != jeden) {
                            if (root == null)
                                root = add_last(root, bool, queue);
                            else
                                root.left = add_last(root.left, bool, queue);
                        }
                    }
                } else if (bool.contains(second)) { //if we have A
                    if (bool != null && !bool.isBlank())
                        bool = bool.replace(second, "");
                    if (queue != null && !queue.isBlank())
                        queue = queue.substring(1, queue.length());
                    if (root != nula && root != jeden) {
                        if (root == null)
                            root = add_last(root, bool, queue);
                        else
                            root.right = add_last(root.right, bool, queue);
                    }
                } else {  //if we don`t have A or !A
                    if (queue != null && !queue.isBlank())
                        queue = queue.substring(1, queue.length());
                    //  if (bool == null || bool.isBlank()){
                    if (root != nula && root != jeden) {
                        if (root == null)
                            root = add_last(root, bool, queue);
                        else {
                            root.left = add_last(root.left, bool, queue);
                            root.right = add_last(root.right, bool, queue);
                        }

                    }
                }
            }
        }
        else{
            if(root == null) {
                root = jeden;
                System.out.println("Huiston");
            }
            else if(queue != null && !queue.isBlank()){
                queue = queue.substring(1, queue.length());
                if(root != jeden) {
                    root.left = add_last(root.left, bool, queue);
                    root.right = add_last(root.right, bool, queue);
                }
            }
            return root;
        }
        return root;
    }
    private Node add_nulu(Node root){
        root = help_nula(root);
        return root;
    }
    private Node help_nula(Node root){
        if(root.left != null && root.right != null){
            root.left  = help_nula(root.left);
            root.right = help_nula(root.right);
        }
        if(root != nula && root != jeden) {
            if (root.left == null) {
                root.left = nula;
            }
            if (root.right == null)
                root.right = nula;
            root.left = help_nula(root.left);
            root.right = help_nula(root.right);
        }
        return root;
    }
    public int BDD_use(String queue){
        int result = use_help(root, expression, queue, 0);
        return result;
    }
    private int use_help(Node root, String queue, String poradie, int number){
        if(queue != null && !queue.isBlank() && poradie != null && !poradie.isBlank()){
            int i = 1;
            while(i == 1) {
                char qu = queue.charAt(0); //find first char at our queue
                if (qu != root.name) { //if our char at queue dosen`t match the node`s name
                    if (queue != null && !queue.isBlank() && poradie != null && !poradie.isBlank()) {
                        queue = queue.substring(1, queue.length());
                        poradie = poradie.substring(1, poradie.length());
                    }
                } else
                    i = 0;
            }

            char po = ' ';
            if(poradie != null && !poradie.isBlank() && queue != null && !queue.isBlank()) {
                po = poradie.charAt(0);
                poradie = poradie.substring(1, poradie.length());
                queue = queue.substring(1, queue.length());
            }
            if(po == '0')
                number = use_help(root.left, queue, poradie, number);
            else
                number = use_help(root.right, queue, poradie, number);
        }
        else {
            number = root.name - '0';
            return number;
        }
        return number;
    }
    public void print(){
        print_help(root);
    }
    private void print_help(Node root){
        if(root != null){
            print_help(root.left);
            System.out.println(root.name + " " + root.number + " bool: " + root.bo);
            print_help(root.right);
        }
    }
}
class BDD{
    Node first;
    int number;
    int num_var;
    public BDD(Node first, int number, int num_var){
        this.first = first;
        this.number = number;
        this.num_var = num_var;
    }
}