package JavaPokeri;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Korttipakka {
    private final ArrayList<Kortti> PAKKA;

    public Korttipakka(int jokerienMaara) {
        PAKKA = luoPakka(jokerienMaara);
    }

    private void lisaaKortti(ArrayList<Kortti> pakka, Maa maa, Arvo arvo) {
        pakka.add(new Kortti(maa, arvo));
    }

    /** Metodi luo ja palauttaa korttipakan, jossa on kaikki tavanomaiset 52 korttia
        ja valittu määrä jokereita. */
    private ArrayList<Kortti> luoPakka(int jokerienMaara) {
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

    /** Metodi arpoo korttipakasta 5 korttia ja palauttaa niistä muodostuvan käden.
        Metodi myös poistaa arvotut kortit pakasta.*/
    ArrayList<Kortti> arvo5korttia() {
        ArrayList<Kortti> kasi = new ArrayList<>();
        Random r = new Random();
        for(int i=0; i<5; i++) {
            int arvottu = r.nextInt(PAKKA.size());
            kasi.add(getKortti(arvottu));
            poistaKortti(arvottu);
        }
        return kasi;
    }

    /** Metodi arpoo ja palauttaa pakasta yhden kortin
        ja samalla poistaa sen pakasta. */
    Kortti nostaUusiKortti() {
        Random r = new Random();
        int arvottu = r.nextInt(PAKKA.size());
        return poistaKortti(arvottu);
    }

    @Override
    public String toString() {
        return "Korttipakka:\n" + PAKKA;
    }

    Kortti getKortti(int i) {
        return PAKKA.get(i);
    }

    Kortti poistaKortti(int i) {
        return PAKKA.remove(i);
    }

}