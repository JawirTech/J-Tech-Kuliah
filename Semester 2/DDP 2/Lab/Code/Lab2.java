import java.util.Scanner;
public class Lab2 {
    public static String reverseString(String str) {
        String res = "";
        for (int i = str.length() - 1; i >= 0; i--) {   //return string baru yaitu reverse dari string yg di parameter
            res += str.charAt(i);
        }
        return res;
    }
    public static void main(String[] args) {
        String inputString;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Masukkan confession dalam bentuk kode (ketik 'selesai' untuk keluar):");
        while (true){
            inputString = scanner.nextLine();
            if(inputString.equals("selesai")){
                break;
            }
            String extractedString = reverseString(getConfessWrapper(inputString)); //extract binary string dari belakang
            System.out.println(extractedString);
        }
        scanner.close();
    }

    public static String getConfessWrapper(String confess) {
        confess = "0" + confess + " ";      //Dipastikan bahwa ada update translasi ketika confess habis.
        return reverseString(getConfess(confess, "", 0, 0));   //reverse kembali agar jawaban tidak terbalik
    }
    
    public static String getConfess(String confess, String currentTranslation, int currentDecimal, int currentExponent){
        if (confess.length() == 0) {        //mengembalikan translate ketika string di parameter kosong setelah recursive berapa kali
            return currentTranslation;
        }
        if (Character.isDigit(confess.charAt(0))) {
            if (confess.charAt(0) == '0' || confess.charAt(0) == '1') {
                int digit = Character.getNumericValue(confess.charAt(0));       //ketika char adalah angka biner, maka akan dimasukkan ke perhitungan
                currentDecimal = currentDecimal * 2 + digit;
                currentExponent++;
            }
        }
        else {
            currentTranslation += asciiToString(currentDecimal);        //meng update translate setelah tidak ketemu char biner
            currentDecimal = 0;
            currentExponent = 0;
        }
        return getConfess(confess.substring(1), currentTranslation, currentDecimal, currentExponent);       //Recursive agar semua char biner dapat di translate
    }
    public static String asciiToString(int asciiValue) {    //Mentranslate angka ascii ke bentuk char
        if (asciiValue == 0) {
            return "";
        }
        char translate = (char) asciiValue;
        return "" + translate;
    }
}