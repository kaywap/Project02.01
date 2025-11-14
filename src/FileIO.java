import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileIO {
    public static void main(String[] args) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File("words_all_os.txt"));
            PrintWriter pw = new PrintWriter(new FileWriter("my_poem.txt", true));
            while (input.hasNextLine()) {
                words.add(input.nextLine());
            }
            input.close();
            for(int i = 0; i < 6; i++){
                String temp = words.get((int)(Math.random() * words.size())) + " ";
                System.out.print(temp);
                pw.print(temp);
            }
            pw.println();
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
