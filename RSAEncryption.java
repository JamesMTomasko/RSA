/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaencryption;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author James
 */
public class RSAEncryption {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        while(true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Would you like to Encrypt(E), Decrypt(D), or Quit(Q)\n");
            String in = scan.nextLine();
            if (in.equals("E") || in.equals("e")) {
                System.out.print("Please enter your public key numbers seperated by a ',' e.g. n,e\n");
                in = scan.nextLine();
                String[] values = in.split(",");
                int n = Integer.parseInt(values[0].trim());
                int e = Integer.parseInt(values[1].trim());
                System.out.print("Please enter the path to the file you want to encrypt\n");
                in = scan.nextLine();
                String f = in;
                f = f.replace("\"", "");
                String[][] M = readFileE(f);
                System.out.print("Please enter the path to the file you want to print to\n");
                in = scan.nextLine();
                String o = in;
                o = o.replace("\"", "");
                encrypt(n, e, M, o);
            } else if (in.equals("D") || in.equals("d")) {
                System.out.print("Please enter your private key numbers seperated by a ',' e.g. n,d\n");
                in = scan.nextLine();
                String[] values = in.split(",");
                int n = Integer.parseInt(values[0].trim());
                int d = Integer.parseInt(values[1].trim());
                System.out.print("Please enter the path to the file you want to decrypt\n");
                in = scan.nextLine();
                String f = in;
                f = f.replace("\"", "");
                String[][] C = readFileD(f);
                System.out.print("Please enter the path to the file you want to print to\n");
                in = scan.nextLine();
                String o = in;
                o = o.replace("\"", "");
                decrypt(n, d, C, o);
            } else if (in.equals("Q") || in.equals("q")) {
                break;
            } else {
                System.out.print("Invalid Input\n");
            }
        }
    }

    private static void encrypt(int n, int e, String[][] m, String o) {
        char[] line;
        try{
            PrintWriter writer = new PrintWriter(o);
            for (int i = 0; i < m.length; i++) {
                for(int j = 0; j < m[i].length; j++) {
                    line = m[i][j].toCharArray();
                    char in = line[0];
                    BigInteger g = BigInteger.valueOf(in);
                    int c = (g.modPow(BigInteger.valueOf(e), BigInteger.valueOf(n))).intValue();
                    writer.print(c);
                    writer.print(" ");
                }
                writer.println();
            }
            System.out.println("File Encrypted");
            writer.close();
        } catch (Exception g) {
            System.err.format("Exception occurred trying to write to file.");
            g.printStackTrace();
        }
    }
    
    private static void decrypt(int n, int d, String[][] m, String o) {
        String split;
        try{
            PrintWriter writer = new PrintWriter(o);
            for (int i = 0; i < m.length; i++) {
                for(int j = 0; j < m[i].length; j++) {
                    split = m[i][j];
                    int in = Integer.parseInt(split);
                    BigInteger g = BigInteger.valueOf(in);
                    char c = (char) (g.modPow(BigInteger.valueOf(d), BigInteger.valueOf(n))).intValue();
                    writer.print(c);
                }
                writer.println();
            }
            System.out.println("File Decrypted");
            writer.close();
        } catch (Exception g) {
            System.err.format("Exception occurred trying to write to file.");
            g.printStackTrace();
        }
    }
    
    public static String[][] readFileE(String filename) throws FileNotFoundException, IOException {
        String[][] result;
        String[] split;
        List<String[]> temp = new ArrayList<String[]>();
        FileReader filereader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(filereader);
        String line = null;
        while((line = reader.readLine()) != null) {
            split = line.split("");
            temp.add(split);
        }
        result = new String[temp.size()][];
        for(int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }
    
    public static String[][] readFileD(String filename) throws FileNotFoundException, IOException {
        String[][] result;
        String[] split;
        List<String[]> temp = new ArrayList<String[]>();
        FileReader filereader = new FileReader(filename);
        BufferedReader reader = new BufferedReader(filereader);
        String line = null;
        while((line = reader.readLine()) != null) {
            split = line.split(" ");
            temp.add(split);
        }
        result = new String[temp.size()][];
        for(int i = 0; i < temp.size(); i++) {
            result[i] = temp.get(i);
        }
        return result;
    }
}
