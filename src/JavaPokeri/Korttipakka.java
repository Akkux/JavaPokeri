package JavaPokeri;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Korttipakka {
    private ArrayList<Kortti> pakka;

    public Korttipakka(int jokerienMaara) {
        pakka = luoPakka(jokerienMaara);

    }

    public void lisaaKortti(ArrayList<Kortti> pakka, Maa maa, Arvo arvo) {
        pakka.add(new Kortti(maa, arvo));
    }

    public ArrayList<Kortti> luoPakka(int jokerienMaara) {
        ArrayList<Kortti> pakka = new ArrayList<>();
        ArrayList<Maa> maalista = new ArrayList<>(List.of(Maa.PATA, Maa.HERTTA, Maa.RISTI, Maa.RUUTU));
        ArrayList<Arvo> arvolista = new ArrayList<>(List.of(Arvo.ÄSSÄ, Arvo.KAKSI, Arvo.KOLME,
                Arvo.NELJÄ, Arvo.VIISI, Arvo.KUUSI, Arvo.SEITSEMÄN,
                Arvo.KAHDEKSAN, Arvo.YHDEKSÄN, Arvo.KYMMENEN, Arvo.JÄTKÄ,
                Arvo.KUNINGATAR, Arvo.KUNINGAS));
        for (int i=0; i<4; i++) {
            for(int j=0; j<13; j++) {
                lisaaKortti(pakka, maalista.get(i), arvolista.get(j));
            }
        }
        for (int i=0; i<jokerienMaara; i++) {
            lisaaKortti(pakka, Maa.JOKERI, Arvo.JOKERI);
        }
        return pakka;
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

    public Kortti nostaUusiKortti() {
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