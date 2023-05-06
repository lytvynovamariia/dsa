public class Main {
    public static void main(String[] args){
        Tree tree = new Tree();
        BDD bdd = tree.create("L!M!N+XYZ+T", "LMNXYZT");
        tree.print();
        // V+YY+DQC  VYDQC
        // ABC+!AB!D+!BC+!BD   ABCD
        // !AB!F+!C!D+E!F+AGH+I!JK+L!M!N+XYZ+T   ABCDEFGHIJKLMNXYZT
        //L!M!N+XYZ+T  LMNXYZT
        //ABC!DEF+!ABC!D!E!F+AB!C!DEF+!AB!C!DEF+A!BC!D!EF+!AB!C!DEF  ABCDEF

     //   int a = tree.BDD_use("001");
       // System.out.println(a);
        System.out.println("IT is BDD " + bdd.first + "  " + bdd.number + " " + bdd.num_var);
    }
}
