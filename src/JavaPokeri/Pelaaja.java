package JavaPokeri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pelaaja implements Serializable {
    private String nimi;
    private int saldo;
    private int panos;
    private int kilpapelinSaldo;
    private ArrayList<Integer> kilpapelinTulokset;
    private ArrayList<Kortti> kasi;
    private ArrayList<String> saavutukset;
    private int viimeisinVoitto;
    private int viimeisinKadenArvo;
    private int yhteisvoitot;

    public Pelaaja(String nimi) {
        this.nimi = nimi;
        panos = 0;
        saldo = 10;
        kilpapelinSaldo = 0;
        kilpapelinTulokset = alustaKilpapelinTulokset();
        kasi = alustaKasi();
        saavutukset = alustaSaavutukset();
        viimeisinVoitto = 0;
        viimeisinKadenArvo = -1;
        yhteisvoitot = 0;
    }

    public Pelaaja() {
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setKasi(ArrayList<Kortti> kasi) {
        this.kasi = kasi;
    }

    public String getNimi() {return nimi;}

    public int getPanos() {
        return panos;
    }

    public void setPanos(int panos) {
        this.panos = panos;
    }

    public int getKilpapelinSaldo() {
        return kilpapelinSaldo;
    }

    public void setKilpapelinSaldo(int kilpapelinSaldo) {
        this.kilpapelinSaldo = kilpapelinSaldo;
    }

    public ArrayList<Integer> getKilpapelinTulokset() {
        return kilpapelinTulokset;
    }

    public void setKilpapelinTulokset(ArrayList<Integer> kilpapelinTulokset) {
        this.kilpapelinTulokset = kilpapelinTulokset;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public ArrayList<Kortti> getKasi() {
        return kasi;
    }

    public Kortti getKortti(int i) {
        return kasi.get(i);
    }

    public ArrayList<String> getSaavutukset() {
        return saavutukset;
    }

    public int getViimeisinVoitto() {
        return viimeisinVoitto;
    }

    public int getViimeisinKadenArvo() {
        return viimeisinKadenArvo;
    }

    public ArrayList<Kortti> alustaKasi() {
        ArrayList<Kortti> kasi = new ArrayList<Kortti>(List.of(
                new Kortti(Maa.JOKERI, Arvo.JOKERI)));
        return kasi;
    }

    public ArrayList<Integer> alustaKilpapelinTulokset() {
        ArrayList<Integer> tulokset = new ArrayList<>(List.of(0));
        tulokset.add(0);
        return tulokset;
    }

    public ArrayList<String> alustaSaavutukset() {
        ArrayList<String> saavutukset = new ArrayList<String>(List.of(
                "?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
                "?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
                "?", "?", "?", "?", "?", "?", "?", "?", "?", "?",
                "?", "?", "?", "?", "?", "?", "?", "?", "?", "?"));
        return saavutukset;
    }

    public void tulostaKasi(int jako) {
        System.out.println();
        if (jako == 1) {
            System.out.println("Käsi:");
        } else {
            System.out.println("Uusi Käsi:");
        }
        System.out.println("1   " + kasi.get(0));
        System.out.println("2   " + kasi.get(1));
        System.out.println("3   " + kasi.get(2));
        System.out.println("4   " + kasi.get(3));
        System.out.println("5   " + kasi.get(4));
        System.out.println();
    }

    public void jaaUusiKasi(ArrayList<Kortti> kasi) {
        this.kasi = kasi;
    }

    public void lisaaKilpapelinTulos(int tulos) {
        ArrayList<Integer> tuloslista = getKilpapelinTulokset();
        tuloslista.add(tulos);
        Collections.sort(tuloslista, Collections.reverseOrder());
        this.kilpapelinTulokset = tuloslista;
    }

    public void vaihdaKortteja(String[] vaihdettavat, Korttipakka pakka) throws CustomException{
        ArrayList<Integer> vaihdettavatInt = new ArrayList<>();
        for(String vaihdettava : vaihdettavat) {
            vaihdettavatInt.add(Integer.valueOf(vaihdettava));
        }
        for (int i : vaihdettavatInt) {
            if (i < 1 || i > 5 || vaihdettavatInt.size() > 5) {
                throw new CustomException("Kelvoton syöte!\n" + "Syötä vaihdettavien korttien indeksit pilkuilla erotettuina. Esim. '2,4,5'");
            }
        }
        for(int vaihdettava : vaihdettavatInt) {
            kasi.set(vaihdettava-1, pakka.vaihdaKortti(kasi.get(vaihdettava-1)));
        }
    }

    public void tarkistaSaavutukset() {
        ArrayList<String> tulosteet1 = tarkistaPokerikadenSaavutukset(viimeisinKadenArvo);
        ArrayList<String> tulosteet2 = tarkistaVoittosummanSaavutukset(viimeisinVoitto);
        ArrayList<String> tulosteet3 = new ArrayList<>();
        //Tehdään avattujen saavutusten tulosteista lista, jotta ne voidaan tulostaa siististi konsoliin
        for (int i=1; i<tulosteet1.size(); i++) {
            tulosteet3.add(tulosteet1.get(i));
        }
        for (int i=1; i<tulosteet2.size(); i++) {
            tulosteet3.add(tulosteet2.get(i));
        }
        if (tulosteet3.size() >= 1) {
            System.out.println();
            for (String tuloste: tulosteet3) {
                System.out.println(tuloste);
            }
            System.out.println();
        }

    }

    public void tarkistaKilpapelinSaavutukset(int kilpapelinTulos) {
        //Tarkistetaan kilpapelin tulokseen perustuvat saavutukset

        //Tehdään viesteistä lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja jälkeen on kuitenkin tyhjä rivi
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        if (kilpapelinTulos != 0) {
            if (kilpapelinTulos > 99999 && saavutukset.get(0).equals("?")) {
                saavutukset.set(0, "Nahka-Lassen kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Nahka-Lassen kaataja!");
            }
            if (kilpapelinTulos > 69420 && saavutukset.get(1).equals("?")) {
                saavutukset.set(1, "NPC-Jannen kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: NPC-Jannen kaataja!");
            }
            if (kilpapelinTulos > 31415 && saavutukset.get(2).equals("?")) {
                saavutukset.set(2, "Anilin kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Anilin kaataja!");
            }
            if (kilpapelinTulos > 12345 && saavutukset.get(3).equals("?")) {
                saavutukset.set(3, "Ernon kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Ernon kaataja!");
            }
            if (kilpapelinTulos > 4999 && saavutukset.get(4).equals("?")) {
                saavutukset.set(4, "Jytäpoikien kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Jytäpoikien kaataja!");
            }
            if (kilpapelinTulos > 2021 && saavutukset.get(5).equals("?")) {
                saavutukset.set(5, "Srinivasan kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Srinivasan kaataja!");
            }
            if (kilpapelinTulos > 1357 && saavutukset.get(6).equals("?")) {
                saavutukset.set(6, "Levrain kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Levarin kaataja!");
            }
            if (kilpapelinTulos > 987 && saavutukset.get(7).equals("?")) {
                saavutukset.set(7, "Jari-Matin kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Jari-Matin kaataja!");
            }
            if (kilpapelinTulos > 444 && saavutukset.get(8).equals("?")) {
                saavutukset.set(8, "Pattersonin kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Pattersonin kaataja!");
            }
            if (kilpapelinTulos > 2 && saavutukset.get(9).equals("?")) {
                saavutukset.set(9, "Fourierin kaataja");
                tulosteet.add(0, "Avasit uuden saavutuksen: Fourierin kaataja!");
            }
        }
        if (tulosteet.size() > 0) {
            for (String tuloste: tulosteet) {
                System.out.println(tuloste);
                }
        }
    }

    public ArrayList<String> tarkistaPokerikadenSaavutukset(int kadenArvo) {
        //Tarkistetaan pokerikäden arvoon perustuvat saavutukset
        ArrayList<String> kasiarvot = new ArrayList<>(List.of("Kuningasvärisuora","Viitoset","Värisuora","Neloset",
                "Täyskäsi","Väri","Suora","Kolmoset","Kaksi paria","10-A pari"));
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=10; i<20; i++){
            if (kadenArvo == i-10 && saavutukset.get(i).equals("?")) {
                saavutukset.set(i, kasiarvot.get(i-10));
                //Tehdään viesteistä lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja jälkeen on kuitenkin tyhjä rivi
                tulosteet.add("Avasit uuden saavutuksen: " + kasiarvot.get(i-10) + "!");
            }
        }
        return tulosteet;
    }

    public ArrayList<String> tarkistaVoittosummanSaavutukset(int voitto) {
        //Tarkistetaan voiton suuruuteen perustuvat saavutukset
        ArrayList<Integer> voittosummat = new ArrayList<>(List.of(100000, 50000, 10000, 5000, 1000, 750, 500, 200, 100, 20));
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=20; i<30; i++){
            if (voitto >= voittosummat.get(i-20) && saavutukset.get(i).equals("?")) {
                saavutukset.set(i, (voittosummat.get(i-20)) + " kolikkoa!");
                //Tehdään viesteistä lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja jälkeen on kuitenkin tyhjä rivi
                tulosteet.add("Avasit uuden saavutuksen: " + voittosummat.get(i-20) + " kolikkoa!");
            }
        }
        return tulosteet;
    }

    public void kadenTarkistus(String pelimuoto) {
        ArrayList<Maa> kasiMaat = new ArrayList<>();
        ArrayList<Integer> kasiArvot = new ArrayList<>();
        /*Kortti k1 = new Kortti(Maa.PATA, Arvo.KUUSI);
        Kortti k2 = new Kortti(Maa.PATA, Arvo.JÄTKÄ);
        Kortti k3 = new Kortti(Maa.HERTTA, Arvo.SEITSEMÄN);
        Kortti k4 = new Kortti(Maa.PATA, Arvo.KOLME);
        Kortti k5 = new Kortti(Maa.JOKERI, Arvo.JOKERI);
        ArrayList<Kortti> kasii = new ArrayList<>();
        kasii.add(k1);
        kasii.add(k2);
        kasii.add(k3);
        kasii.add(k4);
        kasii.add(k5);*/

        for (int i=0; i<5; i++) {
            kasiMaat.add(kasi.get(i).getMaa());
        }
        for (int j=0; j<5; j++) {
            kasiArvot.add(kasi.get(j).getNumeroarvo());
        }


        if (testaaKuningasVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Kuningasvärisuora!");
            int kerroin = 250;
            voitonmaksu(250, 0, pelimuoto);
            /*if (panos == 0) {
                System.out.println("Voitit " + kerroin/2 + " kolikkoa.");
                viimeisinVoitto = kerroin/2;
                viimeisinKadenArvo = 0;
            } else {
                System.out.println("Voitit " + panos*kerroin + " kolikkoa.");
                viimeisinVoitto = kerroin*panos;
                viimeisinKadenArvo = 0;
            }
            if (pelimuoto.equals("vapaapeli")) {
                if (panos == 0) {
                    setSaldo(getSaldo()+250/2);
                }
                setSaldo(getSaldo()+panos*250);
            }
            if (pelimuoto.equals("kilpapeli")) {
                setKilpapelinSaldo(getKilpapelinSaldo()+panos*250);

            }*/

        } else if (testaaViitoset(kasiArvot)) {
            System.out.println("Tulos: Viitoset!");
            int kerroin = 250;
            voitonmaksu(kerroin, 1, pelimuoto);

        } else if (testaaVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Värisuora!");
            int kerroin = 75;
            voitonmaksu(kerroin, 2, pelimuoto);

        } else if (testaaNeloset(kasiArvot)) {
            System.out.println("Tulos: Neloset!");
            int kerroin = 50;
            voitonmaksu(kerroin, 3, pelimuoto);

        } else if (testaaTayskasi(kasiArvot)) {
            System.out.println("Tulos: Täyskäsi!");
            int kerroin = 20;
            voitonmaksu(kerroin, 4, pelimuoto);

        } else if (testaaVari(kasiMaat)) {
            System.out.println("Tulos: Väri!");
            int kerroin = 15;
            voitonmaksu(kerroin, 5, pelimuoto);

        } else if (testaaSuora(kasiArvot)) {
            System.out.println("Tulos: Suora!");
            int kerroin = 10;
            voitonmaksu(kerroin, 6, pelimuoto);

        } else if (testaaKolmoset(kasiArvot)) {
            System.out.println("Tulos: Kolmoset!");
            int kerroin = 5;
            voitonmaksu(kerroin, 7, pelimuoto);

        } else if (testaaKaksiParia(kasiArvot)) {
            System.out.println("Tulos: Kaksi paria!");
            int kerroin = 3;
            voitonmaksu(kerroin, 8, pelimuoto);

        } else if (testaaPari(kasiArvot)) {
            System.out.println("Tulos: 10-pari tai parempi!");
            int kerroin = 2;
            voitonmaksu(kerroin, 9, pelimuoto);

        } else {
            System.out.println("Ei voittoa.");
            viimeisinVoitto = 0;
            viimeisinKadenArvo = -1;
        }
    }

    public void voitonmaksu(int kerroin, int kadenArvo, String pelimuoto) {
        if (panos == 0) {
            System.out.println("Voitit " + kerroin/2 + " kolikkoa.");
            viimeisinVoitto = kerroin/2;
            viimeisinKadenArvo = kadenArvo;
        } else {
            System.out.println("Voitit " + panos*kerroin + " kolikkoa.");
            viimeisinVoitto = kerroin*panos;
            viimeisinKadenArvo = kadenArvo;
        }
        if (pelimuoto.equals("vapaapeli")) {
            if (panos == 0) {
                setSaldo(getSaldo()+kerroin/2);
            }
            setSaldo(getSaldo()+panos*kerroin);
        }
        if (pelimuoto.equals("kilpapeli")) {
            setKilpapelinSaldo(getKilpapelinSaldo()+panos*kerroin);

        }
    }


    public boolean testaaKuningasVarisuora(ArrayList<Integer> kasiArvot, ArrayList<Maa> kasiMaat) {
        if (testaaVari(kasiMaat) && testaaKuningasSuora(kasiArvot)) {
            return true;
        }
        return false;
    }

    public boolean testaaViitoset(ArrayList<Integer> kasiArvot) {
        for (int i=1; i<14; i++) {
            if ((Collections.frequency(kasiArvot, i) == 4) && kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }

    public boolean testaaVarisuora(ArrayList<Integer> kasiArvot, ArrayList<Maa> kasiMaat) {
        if (testaaVari(kasiMaat) && testaaSuora(kasiArvot)) {
            return true;
        }
        return false;
    }

    public boolean testaaNeloset(ArrayList<Integer> kasiArvot) {
        for (int i=1; i<14; i++) {
            if (Collections.frequency(kasiArvot, i) == 4) {
                return true;
            }
            //Kolme samaa arvoa + jokeri
            if ((Collections.frequency(kasiArvot, i) == 3) && kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }

    public boolean testaaTayskasi(ArrayList<Integer> kasiArvot) {
        if (!(kasiArvot.contains(0))) {
            if (testaaKolmoset(kasiArvot) && testaaPari2(kasiArvot)) {
                return true;
            }
        } else {
            //Tutkitaan tilanne, jossa jokeri on mukana kolmosissa.
            Collections.sort(kasiArvot);
            ArrayList<Integer> kasiArvotKopio = new ArrayList<>(kasiArvot);
            int i = 0;
            for (int arvo : kasiArvot) {
                if ((Collections.frequency(kasiArvot, arvo) == 2) && kasiArvot.contains(0)) {
                    //Poistetaan kolmosen muodostavat kortit, jotta jokeri ei enää ole mukana parin tarkistuksessa.
                    kasiArvotKopio.remove(i);
                    kasiArvotKopio.remove(i);
                    kasiArvotKopio.remove(0);
                    if (testaaPari2(kasiArvotKopio)) {
                        return true;
                    }
                    //Jos kyseessä on pelkät kolmoset, eikä täyskäsi, lopetetaan for-loop.
                    break;
                }
                i++;
            }
        }
        return false;
    }

    public boolean testaaVari(ArrayList<Maa> kasiMaat) {
        List<Maa> maalista = List.of(Maa.PATA, Maa.HERTTA, Maa.RISTI, Maa.RUUTU);
        for (Maa maa : maalista) {
            if (Collections.frequency(kasiMaat, maa) == 5) {
                return true;
            }
            //Neljä samaa maata + jokeri
            if ((Collections.frequency(kasiMaat, maa) == 4) && kasiMaat.contains(Maa.JOKERI)) {
                return true;
            }
        }
        return false;
    }

    public boolean testaaKuningasSuora(ArrayList<Integer> kasiArvot) {
        Collections.sort(kasiArvot);
        int summa = kasiArvot.stream().mapToInt(luku -> luku).sum();
        int perakkaiset = 0;
        for (int i=0; i<4; i++) {
            if (kasiArvot.get(i)+1 == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
        }
        //Ässän numeroarvoksi on määritetly 14, joten suora on kuningassuora kun ässä on mukana.
        if (perakkaiset == 4 && kasiArvot.contains(14)) {
            return true;
        }
        //Tutkitaan kaikki tilanteet missä jokeri on mukana. Jokerin numeroarvo on 0.
        //Kun jokeri korvaa kympin.
        if (perakkaiset == 3 && summa == 50 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa jätkän.
        if (perakkaiset == 2 && summa == 49 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa kuningattaren.
        if (perakkaiset == 2 && summa == 48 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa kuninkaan.
        if (perakkaiset == 2 && summa == 47 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa ässän.
        if (perakkaiset == 3 && summa == 46 && kasiArvot.contains(0)) {
            return true;
        }
        return false;
    }

    public boolean testaaSuora(ArrayList<Integer> kasiArvot) {
        Collections.sort(kasiArvot);
        int summa = kasiArvot.stream().mapToInt(luku -> luku).sum();
        int perakkaiset = 0;
        for (int i=0; i<4; i++) {
            if (kasiArvot.get(i)+1 == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
        }
        if (perakkaiset == 4) {
            return true;
        }
        //Ässä voi olla myös suoran pienin kortti.
        if (perakkaiset == 3 && summa == 28 && kasiArvot.contains(14)) {
            return true;
        }
        //Tutkitaan kaikki tilanteet missä jokeri on mukana. Jokerin numeroarvo on 0.
        //Kun jokeri korvaa suoran pienimmän tai suurimman arvon.
        if (perakkaiset == 3 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa suoran toiseksi pienimmän arvon. Indeksin 1 arvo == indeksin 2 arvo - 2
        if (perakkaiset == 2 && kasiArvot.get(1) == kasiArvot.get(2)-2 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa suoran keskimmäisen arvon. Indeksin 2 arvo == indeksin 3 arvo - 2
        if (perakkaiset == 2 && kasiArvot.get(2) == kasiArvot.get(3)-2 && kasiArvot.contains(0)) {
            return true;
        }
        //Kun jokeri korvaa suoran toiseksi suurimman arvon.
        if (perakkaiset == 2 && kasiArvot.get(3) == kasiArvot.get(4)-2 && kasiArvot.contains(0)) {
            return true;
        }

        return false;
    }

    public boolean testaaKolmoset(ArrayList<Integer> kasiArvot) {
        for (int i=2; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 3) {
                return true;
            }
            //Kaksi samaa arvoa + jokeri
            if ((Collections.frequency(kasiArvot, i) == 2) && kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }

    public boolean testaaKaksiParia(ArrayList<Integer> kasiArvot) {
        int parit = 0;
        for (int i=2; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                parit++;
            }
        }
        if (parit == 2) {
            return true;
        }
        //Jokeria ei tarvitse erikseen testata, sillä kolmoset ovat parempi kuin kaksi paria.
        return false;
    }

    public boolean testaaPari(ArrayList<Integer> kasiArvot) {
        for (int i=10; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                return true;
            }
            //Toteutuu aina kun kädessä on jokeri ja jokin kortti väliltä 10-14.
            if (kasiArvot.contains(i) && kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }

    public boolean testaaPari2(ArrayList<Integer> kasiArvot) {
        for (int i=2; i<15; i++) {
            if (Collections.frequency(kasiArvot, i) == 2) {
                return true;
            }
            //Jokeri muodostaa aina parin.
            if (kasiArvot.contains(0)) {
                return true;
            }
        }
        return false;
    }
}