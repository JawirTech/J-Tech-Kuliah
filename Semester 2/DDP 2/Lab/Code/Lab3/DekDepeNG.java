import java.io.*;
import java.util.StringTokenizer;

/**
 * DekDepeNG
 */
public class DekDepeNG {

    private static InputReader in = new InputReader(System.in);
    private static OutputStream outputStream = System.out;
    private static PrintWriter out = new PrintWriter(outputStream); 
    private static Dosen[] listDosen;
    private static Siswa[] listSiswa;
    private static MataKuliah[] listMataKuliah;

    public static void main(String[] args) {
        int jumlahDosen = in.nextInt();
        listDosen = new Dosen[jumlahDosen];
        listMataKuliah = new MataKuliah[jumlahDosen];
        for (int i = 0; i < jumlahDosen; i++){
            String idDosen = in.next();
            String kodeMatkul = in.next();
            int kapasitas = in.nextInt();
            listMataKuliah[i] = new MataKuliah(kodeMatkul, kapasitas);
            listDosen[i] = new Dosen(idDosen, listMataKuliah[i]);
        }
        
        int jumlahSiswa = in.nextInt();
        listSiswa = new Siswa[jumlahSiswa];
        for (int i = 0; i < jumlahSiswa; i++){
            String npm = in.next();
            listSiswa[i] = new Siswa(npm);
        }

        int jumlahPerintah = in.nextInt();

        for(int i = 0; i < jumlahPerintah; i++){
            String perintah = in.next();
            switch (perintah) {
                case "BERINILAI": {
                    String idDosen = in.next();
                    String npm = in.next();
                    int nilai = in.nextInt();
                    beriNilai(idDosen, npm, nilai);     //panggil method beriNilai
                    break;
                }
                case "CEKNILAI": {
                    String npm = in.next();
                    cekNilai(npm);                      //panggil method cekNilai
                    break;
                }
                case "AMBILMATKUL": {
                    String npm = in.next();
                    String kodeMatkul = in.next();
                    ambilMatkul(npm, kodeMatkul);       //panggil method ambilMatkul
                    break;
                }
            }
        }
        out.close();
    }

    public static void beriNilai(String idDosen, String npm, int nilai) {
        for (Dosen dosen : listDosen) {
            if (dosen.getIdDosen().equals(idDosen)) {       //jika dosen yang dimaksud ketemu di dalam listDosen, maka akan print return dari beriNilai dari file Dosen
                out.println(dosen.beriNilai(npm, nilai));
            }
        }
    }

    public static void cekNilai(String npm) {
        for (Siswa siswa : listSiswa) {
            if (siswa.getNpm().equals(npm)) {               //jika npm dari siswa yang dimaksud sama dengan npm di parameter, maka akan print return dari tampilkanNilai dari file Siswa
                out.println(siswa.tampilkanNilai());
            }
        }
    }

    public static void ambilMatkul(String npm, String kodeMatkul) {
        for (Siswa siswa : listSiswa) {
            if (siswa.getNpm().equals(npm)) {               //jika npm yang dimaksud benar lanjut kedalam
                for (MataKuliah matkul : listMataKuliah) {
                    if (matkul.getKodeMatkul().equals(kodeMatkul)) {       //jika kodeMatkul ada di listMataKuliah maka akan diambil matkulnya
                        out.println(siswa.ambilMatkul(matkul));
                    }
                }
            }
        }
    }

    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}