import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Tree tree = new Tree();
        BDD bdd = tree.create("ABC!DEF+!ABC!D!E!F+AB!C!DEF+!AB!C!DEF+A!BC!D!EF+!AB!C!DEF", "ABCDEF");
        tree.print();
        // V+YY+DQC  VYDQC
        // ABC+!AB!D+!BC+!BD   ABCD
        // !AB!F+!C!D+E!F+AGH+I!JK+L!M!N+XYZ+T   ABCDEFGHIJKLMNXYZT
        //L!M!N+XYZ+T  LMNXYZT
        //ABC!DEF+!ABC!D!E!F+AB!C!DEF+!AB!C!DEF+A!BC!D!EF+!AB!C!DEF  ABCDEF

   //     int a = tree.BDD_use("1111");
     //   System.out.println(a);
        System.out.println("IT is BDD " + bdd.first + "  " + bdd.number + " " + bdd.num_var);
        BDD_use("ABC!DEF+!ABC!D!E!F+AB!C!DEF+!AB!C!DEF+A!BC!D!EF+!AB!C!DEF", "ABCDEF", tree);
    }
    private static void BDD_use(String bool, String queue, Tree tree){
        int b = queue.length();
        List<Integer> intList = new ArrayList<>();
        for (int i = 0; i < Math.pow(2, b); i++) {
            intList.add(i);
        }

// convert each integer to a binary string and add leading zeros to make it a string of length "a"
        List<String> binList = new ArrayList<>();
        for (int i : intList) {
            String binStr = Integer.toBinaryString(i);
            while (binStr.length() < b) {
                binStr = "0" + binStr;
            }
            binList.add(binStr);
        }

// print the binary strings
        //     for (String binStr : binList) {
        //       System.out.println(binStr);
        // }
      //  String bool = "!ABCD+ABCD";
        //String queue = "ABCD";
        //  int h = 0;
        String next = "111111";
        for(String binStr : binList){
            String[] help = bool.split("\\+");
            for(int i = 0; i < queue.length(); i++) { //replace all elements in bool
                char copy = queue.charAt(i); //element in queue
                char new_char = next.charAt(i);
                String first = "!" + copy;
                //    String second = "" + copy;
                for(int k = 0; k < help.length; k++){ //replace elements
                    if(help[k].contains("!"+copy)){ //if we have !A expression
                        char against = new_char;
                        if(against == '1')
                            against = '0';
                        else
                            against = '1';
                        help[k] = help[k].replace("!" + copy, ""+against);
                    }
                    else if(help[k].contains(""+ copy)){
                        help[k] = help[k].replace(copy, new_char);
                    }
                }
            }
            int result = 0;
            for(int i = 0; i < help.length; i++){
                if(!help[i].contains("0"))
                    result += 1;
            }
            if(result != 0)
                result = 1;
            int a = tree.BDD_use(next);
            if(a != result)
                System.out.println("BDD BDDD BDD");
        }
        System.out.println("WE finish!");
    }

}
