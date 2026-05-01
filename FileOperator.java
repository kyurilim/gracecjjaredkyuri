import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperator {
   private static File myFile;
   private static Scanner fileReader;

   public FileOperator() {
   }

   public static void createFile(String var0) {
      myFile = new File(var0);

      try {
         fileReader = new Scanner(myFile);
      } catch (FileNotFoundException var2) {
         var2.printStackTrace();
         System.out.println("File not found. Please enter a valid file.");
      }

   }

   public static ArrayList<String> getStringList(String var0, int numLines) {
      createFile(var0);
      ArrayList<String> var1 = new ArrayList<>();
      int count = 0;

      while (fileReader.hasNextLine() && (numLines <= 0 || count < numLines)) {
         var1.add(fileReader.nextLine());
         count++;
      }

      return var1;
   }

   public static ArrayList<Double> getDoubleList(String var0) {
      createFile(var0);
      ArrayList var1 = new ArrayList();

      while(fileReader.hasNextDouble()) {
         var1.add(fileReader.nextDouble());
      }

      return var1;
   }

   public static ArrayList<Integer> getIntList(String var0) {
      createFile(var0);
      ArrayList var1 = new ArrayList();

      while(fileReader.hasNextInt()) {
         var1.add(fileReader.nextInt());
      }

      return var1;
   }

   public static ArrayList<String> getWords(String var0) {
      ArrayList var1 = new ArrayList();
      String[] var2 = var0.split(" ");
      String[] var3 = var2;
      int var4 = var2.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String var6 = var3[var5];
         var1.add(var6);
      }

      return var1;
   }
}
