package JavaPokeri;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Korttipakka {
    private ArrayList<Kortti> pakka;

    public Korttipakka() {
        pakka = new ArrayList<>();
        luoPakka();
    }

    public void lisaaKortti(Maa maa, Arvo arvo) {
        pakka.add(new Kortti(maa, arvo));
    }

    public void luoPakka() {
        ArrayList<Maa> maalista = new ArrayList<>(List.of(Maa.PATA, Maa.HERTTA, Maa.RISTI, Maa.RUUTU));
        ArrayList<Arvo> arvolista = new ArrayList<>(List.of(Arvo.ÄSSÄ, Arvo.KAKSI, Arvo.KOLME,
                Arvo.NELJÄ, Arvo.VIISI, Arvo.KUUSI, Arvo.SEITSEMÄN,
                Arvo.KAHDEKSAN, Arvo.YHDEKSÄN, Arvo.KYMMENEN, Arvo.JÄTKÄ,
                Arvo.KUNINGATAR, Arvo.KUNINGAS));
        for(int i=0; i<4; i++) {
            for(int j=0; j<13; j++) {
                lisaaKortti(maalista.get(i), arvolista.get(j));
            }
        }
        lisaaKortti(Maa.JOKERI, Arvo.JOKERI);
    }

    public ArrayList<Kortti> arvo5korttia() {
        ArrayList<Kortti> kasi = new ArrayList<>();
        Random r = new Random();
        for(int i=0; i<5; i++) {
            int arvottu = r.nextInt(pakka.size());
            kasi.add(getKortti(arvottu));
            poistaKortti(arvottu);
        }
        return kasi;
    }

    public Kortti vaihdaKortti(Kortti vaihdettava) {
        Random r = new Random();
        int arvottu = r.nextInt(pakka.size());
        return poistaKortti(arvottu);
    }

    @Override
    public String toString() {
        return "Korttipakka:\n" + pakka;
    }

    public Kortti getKortti(int i) {
        return pakka.get(i);
    }

    public Kortti poistaKortti(int i) {
        return pakka.remove(i);
    }

}