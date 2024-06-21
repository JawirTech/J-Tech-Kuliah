public class Dosen {
    private String idDosen;
    private MataKuliah mataKuliah;

    public Dosen(String idDosen, MataKuliah mataKuliah){
        this.idDosen = idDosen;
        this.mataKuliah = mataKuliah;
    }

    public String beriNilai(String npm, int nilai) {
        String output = "";
        boolean diberiNilai = false;
        for (int i = 0; i < mataKuliah.getJumlahSiswa(); i++) {
            if (mataKuliah.getListSiswa()[i] != null && mataKuliah.getListSiswa()[i].getNpm().equals(npm)) {      //ketika npm di parameter sama dengan npm siswa yang dimaksud sama, maka akan masuk
                for (int j = 0; j < mataKuliah.getListSiswa()[i].getListNilai().length; j++) {
                    if (mataKuliah.getListSiswa()[i].getListNilai()[i].getKodeMatkul().equals(mataKuliah.getKodeMatkul())) {    //ketika kodeMatkul ada, maka nilai akan diberikan kepada siswa
                        mataKuliah.getListSiswa()[i].getListNilai()[i].setNilai(nilai);
                        output += getIdDosen() + " berhasil memberikan nilai kepada siswa dengan NPM " + npm;
                        diberiNilai = true;
                        break;
                    }
                }
            }
            if (diberiNilai == true) {
                break;
            }
        }
        return output;
    }

    public String getIdDosen() {
        return idDosen;
    }

    public MataKuliah getMataKuliah() {
        return mataKuliah;
    }
}