import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    public static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static void validateInput(String[] args) {
        if (args.length == 0) {
            throw new Error("Specify your arguments! Type cdencrypt --help to know what arguments you need to pass!");
        }
        if (Objects.equals(args[0], "--help") || Objects.equals(args[0], "-h")) {
            String helpMessage = "Welcome to dencrypt!\n\nThe following arguments are required.\n\n1) The path of the file to encrypt/decrypt. (Format: .txt)\n\n2) The shift depth of the algorithm. (Number)\n\n3) Whether you want to encrypt/decrypt the file. (-d/-e)";
            System.out.println(helpMessage);
            System.exit(0);
        }
        if (args.length == 1) {
            throw new Error("Specify the shift depth and whether you want to encrypt or decrypt the file");
        }
        if (args.length == 2) {
            throw new Error("Specify whether you want to encrypt or decrypt the file");
        }
    }

    public static void main(String[] args) {
        validateInput(args);
        String path = args[0];
        String shiftDepth = args[1];
        int shiftDepthI;
        String encrypt = args[2];

        try {
           shiftDepthI = Integer.parseInt(shiftDepth);
        } catch (NumberFormatException e) {
            throw new Error("Shift Depth is not a number!");
        }

        if (!encrypt.equalsIgnoreCase("-d") && !encrypt.equalsIgnoreCase("-e")) {
            throw new Error("Specify whether you want to encrypt or decrypt the file correctly! Use -d for decrypt, and -e for encrypt.");
        }

        try {
            StringBuilder result = new StringBuilder();
            boolean isEncryption = encrypt.equalsIgnoreCase("-e");
            File file = new File(path);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                 String data = scanner.nextLine();
                 for (int i = 0; i < data.length(); i++) {
                     if (Objects.equals(data.charAt(i), ' ')) {
                         result.append(' ');
                         continue;
                     }
                     int charPosition = ALPHABET.indexOf(data.charAt(i));
                     char newChar = 'x';
                     if (isEncryption) {
                         if (shiftDepthI + charPosition > ALPHABET.length()) {
                             charPosition = (shiftDepthI + charPosition) % ALPHABET.length();
                             newChar = ALPHABET.charAt(charPosition);
                             result.append(newChar);
                         } else {
                             newChar = ALPHABET.charAt(charPosition + shiftDepthI);
                             result.append(newChar);
                         }
                     } else {
                         if (charPosition - shiftDepthI < 0) {
                             charPosition = ALPHABET.length() - (shiftDepthI + charPosition) % ALPHABET.length();
                             newChar = ALPHABET.charAt(charPosition);
                             result.append(newChar);
                         } else {
                             newChar = ALPHABET.charAt(charPosition - shiftDepthI);
                             result.append(newChar);
                         }
                     }
                 }
                 result.append("\n");
            }
            System.out.println(result);
        } catch (FileNotFoundException e) {
            throw new Error("Specify a valid path!");
        }
    }
}
