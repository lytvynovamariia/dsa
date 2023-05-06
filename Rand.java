import java.io.File;
import java.util.Random;
public class Rand {
    public static void main(String[] args){
        File file = new File("C:\\studium\\LS 2022_2023\\DSA\\2_zadanie\\Project\\src\\test.txt");
        if(file.exists())
            System.out.println("rrr");
        else
            System.out.println("yyyyy");
        Random rand = new Random();
        String give = "";
        int counter = 0;
        int number = 5;
        char buffer;
        boolean bool;
        char next = '+';
        char[] test = new char[number];
        for(int i = 0; i < number; i++){
            test[i] = ' ';
        }
        while(counter != number){
            buffer = (char)(rand.nextInt(26) + 'A');
            give += buffer;
            bool = rand.nextBoolean();
            if(bool)
                give += next;
            for(int i = 0; i < number; i++){
                if(test[i] == ' '){
                    test[i] = buffer;
                    counter++;
                    break;
                }
                else if(test[i] == buffer)
                    break;
            }
        }
        if(give.charAt(give.length() - 1) == '+')
            give = give.substring(0, give.length() - 1);
        System.out.println(give);
        String queue = "";
        for(int i = 0; i < number; i++){
            queue += test[i];
        }
        Tree tree = new Tree();
        tree.create(give, queue);
        tree.print();
    }
}
