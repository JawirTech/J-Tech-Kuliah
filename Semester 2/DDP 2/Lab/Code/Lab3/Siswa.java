public class Siswa {
    private String npm;
    private NilaiController[] listNilai = new NilaiController[100];

    public Siswa(String npm){
        this.npm = npm;
    }

    public String ambilMatkul(MataKuliah mataKuliah) {
        String output = "";
        if (mataKuliah.getJumlahSiswa() < mataKuliah.getKapasitas()) {
            mataKuliah.getListSiswa()[mataKuliah.getJumlahSiswa()] = this;
            mataKuliah.setJumlahSiswa(mataKuliah.getJumlahSiswa() + 1);
            output += "Siswa dengan NPM " + npm + " berhasil mengambil matkul dengan kode " + mataKuliah.getKodeMatkul();
            for (int i = 0; i < listNilai.length; i++) {
                if (listNilai[i] == null) {
                    listNilai[i] = new NilaiController(mataKuliah.getKodeMatkul());
                    break;
                }
            }
        }
        else  {
            output += "Siswa dengan NPM " + npm + " gagal mengambil matkul dengan kode " + mataKuliah.getKodeMatkul();
        }
        return output;
    }

    public String tampilkanNilai() {
        for (int i = 0; i < getListNilai().length; i++) {
            if (getListNilai()[i] != null) {
                return "Kode matkul " + npm + " memiliki nilai " + getListNilai()[i].getNilai();
            } 
        }
        return "Siswa belum mengambil mata kuliah :v";
    }

    public NilaiController[] getListNilai() {
        return listNilai;
    }

    public String getNpm() {
        return npm;
    }
}
