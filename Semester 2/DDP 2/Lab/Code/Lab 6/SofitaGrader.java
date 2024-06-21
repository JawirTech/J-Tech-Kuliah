import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class SofitaGrader {
    static Scanner sc = new Scanner(System.in);
    static File direktoriUtama = new File(".");

    public static void main(String[] args) {
        try {
            System.out.println("Welcome to SOFITA GRADER!");
            while (true) {
                printWelcomingMsg();
                System.out.print("Input: ");
                int actionCode = sc.nextInt();
                sc.nextLine();
                if ((actionCode > 3 && actionCode < 10) || actionCode < 1 || actionCode > 10) {
                    throw new InvalidSwitchInput(actionCode);
                }
                switch (actionCode) {
                    case 1:
                        buatQuiz();
                        break;
                    case 2:
                        jawabQuiz();
                        break;
                    case 3:
                        nilaiQuiz();
                        break;
                    case 10:
                        sc.close();
                        System.out.println("Terima kasih sudah memakai SOFITA GRADER!");
                        return;
                }
            }
        }
        catch (InvalidSwitchInput ex) {
            System.out.println("Input invalid, coba lagi.");
            sc.nextLine();
        }
    }
    
    public static void printWelcomingMsg() {
        System.out.println("=".repeat(64));
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("[1] Buat Quiz baru");
        System.out.println("[2] Input Jawaban Quiz");
        System.out.println("[3] Nilai Jawaban Quiz");
        System.out.println("[10] Exit");
        System.out.println("=".repeat(64));
    }

    public static void buatQuiz() {
        System.out.println("\n---BUAT QUIZ---");
        File folder = makeFile();
        makeKJ(folder);
    }

    public static void jawabQuiz() {
        System.out.print("\n---JAWAB QUIZ---\n");
        System.out.println("Berikut adalah daftar folder yang ada:\n-----------------------------");
        printCurrentDirectory();
        System.out.println("-----------------------------   ");
        File folder = aksesFolder();
        try {
            if (folder.exists()) {
                System.out.println();
                makeJawaban(folder);
                return;
            }
            throw new FileNotFoundException("File Tidak ditemukan");
        }
        catch (FileNotFoundException e) {
            System.out.println("File Tidak ditemukan");
        }
    }

    public static void nilaiQuiz() {
        System.out.print("\n---NILAI QUIZ---");
        System.out.println("\nBerikut adalah daftar folder yang ada:\n-----------------------------");
        printCurrentDirectory();
        System.out.println("-----------------------------   ");
        if (direktoriUtama.listFiles().length == 1) {
            return;
        }
        File pilihFolder = aksesFolder();
        while (pilihFolder == null) {
            pilihFolder = aksesFolder();
        }

        File rekapSebelumnya = findFile(pilihFolder, String.format("Nilai Rekap %s.txt", pilihFolder.getName()));
        System.out.println("Isi Rekap Nilai " + pilihFolder.getName() + ":");
        if (rekapSebelumnya != null){
            rekapSebelumnya.delete();
            System.out.println("-------------------------------------");
            System.out.println("| ! Nilai Rekap akan di-overwrite ! |");
            System.out.println("-------------------------------------");
        }   
    
        File[] files = pilihFolder.listFiles();
        if (files.length == 1) {
            System.out.println("Belum ada yang input jawaban");
            return;
        }
        
        File kjQuiz = findFile(pilihFolder, String.format("KJ %s.txt", pilihFolder.getName()));
        BufferedWriter writer = null;
        try {
            File newFile = new File(pilihFolder, "Nilai Rekap " + pilihFolder.getName() + ".txt");
            writer = new BufferedWriter(new FileWriter(newFile));
            for (File file : files) {
                if (!file.getName().equals("Nilai Rekap " + pilihFolder.getName() + ".txt") && !file.getName().equals("KJ " + pilihFolder.getName() + ".txt")) {
                    double nilai = (double) countMatchingLines(file, kjQuiz) / hitungSoal(file) * 100;
                    System.out.println(String.format("%s: %.2f", file.getName().substring(0, file.getName().length() - 4), nilai));
                    writer.write(String.format("%s: %.2f", file.getName().substring(0, file.getName().length() - 4), nilai));
                }
            }
            writer.close();
        }
        catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
    
///////////////////////////////////////////////////////////////////////////////

    public static File makeFile() {
        System.out.print("Masukkan nama folder baru: ");
        String inputNama = sc.nextLine();
        File contents[] = direktoriUtama.listFiles();

        for (File file : contents) {
            if (file.getName().equals(inputNama)){
                System.out.println("Nama sudah terambil!");
                return file;
            }
        }
        File folderBaru = new File(inputNama);
        folderBaru.mkdir();
        System.out.printf("Berhasil buat folder dengan nama %s\n\n", inputNama);
        return folderBaru;
    }

    public static String strJawaban(int jumlahSoal) {
        String ditulis = "";
        int nomor = 1;
        while (jumlahSoal > 0) {  
            System.out.print(nomor + ". ");
            String jawaban = sc.next();
            if (!jawaban.equals("A") && !jawaban.equals("B") && !jawaban.equals("C") && !jawaban.equals("D")) {
                System.out.println("Input tidak valid. Masukkan A, B, C, atau D.");
                continue;
            }
            ditulis += nomor + ". ";
            if (jumlahSoal > 1) {
                ditulis += jawaban + "\n";
            }
            else {
                ditulis += jawaban;
            }
            jumlahSoal--;
            nomor++;
        }
        return ditulis;
    }

    public static void makeKJ(File folderQuiz) {
        // TODO: Implement the logic for creating quiz answers
        // HINT: You might want to utilize File.createNewFile() and BufferedWriter to write the answers to a file
        System.out.print("Silahkan input KJ untuk " + folderQuiz + "\nJumlah soal: ");
        int jumlahSoal = sc.nextInt();
        String jawaban = strJawaban(jumlahSoal);
        try {
            String fileName = "KJ " + folderQuiz + ".txt";
            File file = new File(folderQuiz, fileName);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter output = new BufferedWriter(fileWriter);
            output.write(jawaban);
            output.close();
            System.out.println("Berhasil buat file " + fileName + "\n");
        }
        catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membuat file.");
        }
    }

    public static void makeJawaban(File folderQuiz) {
        System.out.print("Masukkan nama murid: ");
        String namaMurid = sc.nextLine();
        String strFile = namaMurid + ".txt";
        File fileMurid = new File(folderQuiz, strFile);
        if (fileMurid.exists()) {
            System.out.println("-------------------------------------");
            System.out.println("|  !  Jawaban akan di-overwrite  !  |");
            System.out.println("-------------------------------------");
        }
        System.out.println("Masukkan jawaban:");
        try {
            int jumlahSoal = hitungSoal(findFile(folderQuiz, "KJ " + folderQuiz.getName() + ".txt"));
            String jawaban = strJawaban(jumlahSoal);
            FileWriter fileWriter = new FileWriter(fileMurid);
            BufferedWriter output = new BufferedWriter(fileWriter);
            output.write(jawaban);
            output.close();
            System.out.println("Berhasil buat file " + strFile + "\n");
        }
        catch (IOException ex) {
            System.out.println("Terjadi kesalahan saat membuat file.");
        }
    }

    public static File aksesFolder() {
        System.out.print("Pilih nama folder untuk diakses: ");
        String namaFolder = sc.nextLine();
        System.out.println();
        try{
            File selectedFolder = findFile(direktoriUtama, namaFolder); 
            if(selectedFolder == null){ 
                throw new InvalidQuizException("Input tidak valid. Masukkan nama folder yang valid.");
            }
            return findFile(direktoriUtama, namaFolder);
        }catch(InvalidQuizException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public static File findFile(File selectedFolder, String fileName) {
        // TODO: Implement the logic for finding a file in the given folder
        File contents[] = selectedFolder.listFiles();
        for (File file : contents) {
            if (fileName.equals(file.getName())) {
                return file;
            }
        }
        return null;
    }
    
    public static int countMatchingLines(File file, File kjFile) {
        // TODO: Implement the logic for counting matching lines between two files
        int count = 0;
        try {
            BufferedReader readJawaban = new BufferedReader(new FileReader(file));
            BufferedReader readKJ = new BufferedReader(new FileReader(kjFile));
            String lineJawaban = readJawaban.readLine();
            String lineKJ = readKJ.readLine();
            while (lineJawaban != null && lineKJ != null) {
                if (lineJawaban.equals(lineKJ)) {
                    count++;
                }
                lineJawaban = readJawaban.readLine();
                lineKJ = readKJ.readLine();
                
            }
            readJawaban.close();
            readKJ.close();
        } catch (IOException e) {
            System.out.println("Terjadi kesalahan saat membaca file");
        }
        return count;
    }

    /**
     * Prints the names of all files in the given folder that do not have a ".java" extension.
     *
     * @param folderName the folder to search for files
     */
    
    public static void printCurrentDirectory() {
        printFiles(direktoriUtama);
        if (direktoriUtama.listFiles().length == 1) {
            System.out.println("Belum ada folder yang dibuat!");
            return;
        }
    }

    /**
     * Prints the names of all files in the given folder that have a ".java" extension.
     *
     * @param folderName the folder to search for files
     */
    public static void printFiles(File folderName) {
        File contents[] = folderName.listFiles();
        for (File file : contents) {
            if (!file.getName().endsWith(".java")){
                System.out.printf("> %s\n",file.getName());
            }
        }
    }

    /**
     * Calculates the number of questions in a given file.
     * 
     * @param file the file containing the questions
     * @return the number of questions in the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static int hitungSoal(File file) throws IOException {
        Scanner reader = new Scanner(file);
        int soalCount = 0;
        while (reader.hasNextLine()) {
            reader.nextLine();
            soalCount++;
        }
        reader.close();
        return soalCount;
    }
}

class InvalidSwitchInput extends Exception {
    private int inputan;
    InvalidSwitchInput (int inputan) {
        super("Invalid input " + inputan);
        this.inputan = inputan;
    }
}

class InvalidQuizException extends Exception {
    InvalidQuizException(String message) {
        super(message);
    }
}