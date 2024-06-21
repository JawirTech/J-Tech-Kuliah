import java.util.Scanner;       
public class Lab1 {
    public static void main(String[] args) {
        while (true) {          //menggunakan while loop karena ketika validasi jumlahMatkul bernilai salah, maka akan mengulang dari awal
            Scanner input = new Scanner(System.in);         //menginisiasi Scanner untuk dipakai
            System.out.print("Masukkan jumlah mata kuliah: ");
            double jumlahMatkul = input.nextDouble(), jumlahMutu = 0, sksDiambil = 0, mutuLulus = 0, sksLulus = 0;     //Variable jumlahMatkul untuk menandakan berapakali loop akan dilakukan dengan method next.Double()
            if (jumlahMatkul < 0) {         //Validasi jumlahMatkul, karena jumlah matkul tidak boleh negatif
                System.out.println("Jumlah mata kuliah yang diambil tidak dapat negatif, silahkan isi kembali");     
                continue;   
            }
        
            for (int i = 1; i < jumlahMatkul + 1; i++) {        //Loop sebanyak jumlahMatkul untuk menentukan nilai, nilai huruf, bobot, mutu dari sebuah mata kuliah yang di input saat namaMatkul
                input.nextLine();       //Meng-clear line supaya nextLine() selanjutnya dapat diterima
                System.out.print("Masukkan nama mata kuliah ke-" + i + ": ");
                String namaMatkul = input.nextLine();       //Input nama matakuliah berupa String
                
                double jumlahSKS;
                while (true) {      //Loop yang akan mengulang hanya jika validasi gagal
                    System.out.print("Masukkan jumlah sks: ");
                    jumlahSKS = input.nextDouble();     //Input berupa double untuk jumlah sks tiap mata kuliah tadi
                    if (jumlahSKS <= 0) {           //Validasi jumlah SKS yang tidak boleh 0 atau negatif
                        System.out.println("Jumlah SKS mata kuliah yang diambil tidak dapat negatif atau 0, silahkan isi kembali");
                        continue;
                    }
                    break;
                }
                sksDiambil += jumlahSKS;     //Terus menambah total sks tiap input SKS tiap mata kuliah

                double nilaiMatkul;
                while (true) {      //Loop yang akan mengulang hanya jika validasi gagal
                    System.out.print("Masukkan nilai: ");
                    nilaiMatkul = input.nextDouble();       //Input berupa double untuk nilai akhir dari mata kuliah tadi
                    if (nilaiMatkul < 0 || nilaiMatkul > 100) {     //validasi nilai, nilai hanya boleh diantara 0-100
                        System.out.println("Nilai mata kuliah tidak valid, silahkan isi kembali");
                        continue;
                    }
                    break;
                }
                //Mencari nilai huruf yang sesuai dengan input nilai tadi, serta bobotnya
                String nilaiHuruf;
                double bobot;
                if (nilaiMatkul >= 85) {
                    nilaiHuruf = "A";
                    bobot = 4;
                }
                else if (nilaiMatkul < 85 && nilaiMatkul >= 80) {
                    nilaiHuruf = "A-";
                    bobot = 3.7;
                }
                else if (nilaiMatkul < 80 && nilaiMatkul >= 75) {
                    nilaiHuruf = "B+";
                    bobot = 3.3;
                }
                else if (nilaiMatkul < 75 && nilaiMatkul >= 70) {
                    nilaiHuruf = "B";
                    bobot = 3.0;
                }
                else if (nilaiMatkul < 70 && nilaiMatkul >= 65) {
                    nilaiHuruf = "B-";
                    bobot = 2.7;
                }
                else if (nilaiMatkul < 65 && nilaiMatkul >= 60) {
                    nilaiHuruf = "C+";
                    bobot = 2.3;
                }
                else if (nilaiMatkul < 60 && nilaiMatkul >= 55) {
                    nilaiHuruf = "C";
                    bobot = 2.0;
                }
                else if (nilaiMatkul < 55 && nilaiMatkul >= 40) {
                    nilaiHuruf = "D";
                    bobot = 1.0;
                }
                else {
                    nilaiHuruf = "E";
                    bobot = 0;
                }
                double mutuMatkul = jumlahSKS * bobot;      //Menghitung mutu mata kuliah berdasarkan jumlah SKS matkul dan bobot nilainya
                jumlahMutu += mutuMatkul;       //total mutu akan terus bertambah tiap loop
                if (bobot >= 2) {       //mutuLulus dan sksLulus  hanya akan bertambah jika nilai mata kuliah lulus yaitu dengan bobot 2 atau lebih
                    mutuLulus += mutuMatkul;
                    sksLulus += jumlahSKS;
                }
                System.out.println("Nilai huruf mata kuliah " + namaMatkul + " adalah " + nilaiHuruf + " dengan mutu " + String.format("%.2f", mutuMatkul) + " \n");
            }
            double ipSemester = jumlahMutu / sksDiambil;        //Menhitung IP Semester mahasiswa yang bergantung dengan jumlahMutu dan sks yang diambil
            double ipKumulatif;
            if (mutuLulus == 0.0) {     //Ketika mutuLulus = 0, maka ipKumulatif otomatis menjadi 0
                ipKumulatif = 0;
            }
            else {
                ipKumulatif = mutuLulus / sksLulus;     //IP kumulatif hanya ditentukan oleh mutu yang lulus serta jumlah sks yang lulus
            }
            
            System.out.println("Jumlah mutu: " + String.format("%.2f", jumlahMutu));
            System.out.println("Jumlah sks diambil: " + String.format("%.2f", sksDiambil));
            System.out.println("IP Semester: " + String.format("%.2f", ipSemester));
            System.out.println("Jumlah mutu lulus: " + String.format("%.2f", mutuLulus));
            System.out.println("Jumlah sks lulus: " + String.format("%.2f", sksLulus));
            System.out.println("IP Kumulatif " + String.format("%.2f", ipKumulatif));
            break;
        }
    }
}