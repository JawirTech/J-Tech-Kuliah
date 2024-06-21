public class NilaiController {
    private String kodeMatkul;
    private int nilai;

    public NilaiController(String kodeMatkul){
        this.kodeMatkul = kodeMatkul;
    }

    public String getKodeMatkul() {
        return kodeMatkul;
    }
    public int getNilai() {
        return nilai;
    }
    public void setNilai(int nilai) {
        this.nilai = nilai;
    }
}
