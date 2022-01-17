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
    private Boolean[] saavutustenTilat;
    private Boolean[] vetopokeriParannustenTilat;
    private int viimeisinVoitto;
    private int viimeisinKadenArvo;
    private int yhteisvoitot;
    private int yhteishaviot;
    private int voitetutJaot;
    private int havitytJaot;

    public Pelaaja(String nimi) {
        this.nimi = nimi;
        panos = 0;
        saldo = 20;
        kilpapelinSaldo = 0;
        kilpapelinTulokset = alustaKilpapelinTulokset();
        kasi = alustaKasi();
        saavutustenTilat = alustaSaavutustenTilat();
        vetopokeriParannustenTilat = alustaVetopokeriParannustenTilat();
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

    public Boolean[] getSaavutustenTilat() {
        return saavutustenTilat;
    }

    public Boolean[] getVetopokeriParannustenTilat() {return vetopokeriParannustenTilat; }

    public void setVetopokeriParannuksenTilat(Boolean[] vetopokeriParannukset) {
        this.vetopokeriParannustenTilat = vetopokeriParannukset;
    }

    public int getViimeisinVoitto() {
        return viimeisinVoitto;
    }

    public int getViimeisinKadenArvo() {
        return viimeisinKadenArvo;
    }

    public int getYhteisvoitot() {
        return yhteisvoitot;
    }

    public void setYhteisvoitot(int yhteisvoitot) {
        this.yhteisvoitot = yhteisvoitot;
    }

    public int getYhteishaviot() {
        return yhteishaviot;
    }

    public int getVoitetutJaot() {
        return voitetutJaot;
    }

    public int getHavitytJaot() {
        return havitytJaot;
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

    public Boolean[] alustaSaavutustenTilat() {
        Boolean[] saavutustenTilat = new Boolean[40];
        for (int i=0; i<40; i++) {
            saavutustenTilat[i] = false;
        }
        return saavutustenTilat;
    }

    public Boolean[] alustaVetopokeriParannustenTilat() {
        Boolean[] vetopokeriParannustenTilat = new Boolean[20];
        for (int i=0; i<20; i++) {
            vetopokeriParannustenTilat[i] = false;
        }
        return vetopokeriParannustenTilat;
    }

    public void tulostaKasi(boolean korttienVaihtoTapahtunut) {
        System.out.println();
        if (korttienVaihtoTapahtunut) {
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
        for (String vaihdettava : vaihdettavat) {
            vaihdettavatInt.add(Integer.valueOf(vaihdettava));
        }
        for (int i : vaihdettavatInt) {
            if (i < 1 || i > 5 || vaihdettavatInt.size() > 5) {
                throw new CustomException("Kelvoton syöte!\n" + "Syötä vaihdettavien korttien indeksit pilkuilla erotettuina. Esim. '2,4,5'");
            }
        }
        for(int vaihdettava : vaihdettavatInt) {
            kasi.set(vaihdettava-1, pakka.nostaUusiKortti());
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
        String[] saavutukset = new Saavutukset().getJasenet();
        int[] voitettavatTulokset = {99999, 69420, 31415, 12345, 4999, 2021, 1357, 987, 444, 2};

        for (int i=0; i<10; i++) {
            if (kilpapelinTulos != 0) {
                if (kilpapelinTulos > voitettavatTulokset[i] && !saavutustenTilat[i]) {
                    saavutustenTilat[i] = true;
                    tulosteet.add(0, "Avasit uuden saavutuksen: " + saavutukset[i] +"!");
                }
            }
        }

        if (tulosteet.size() > 0) {
            for (String tuloste: tulosteet) {
                System.out.println(tuloste);
                }
        }
    }

    public ArrayList<String> tarkistaPokerikadenSaavutukset(int viimeisinKadenArvo) {
        //Tarkistetaan pokerikäden arvoon perustuvat saavutukset

        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");

        for (int i=10; i<20; i++){
            if (viimeisinKadenArvo == i-10 && !saavutustenTilat[i]) {
                saavutustenTilat[i] = true;
                //Tehdään viesteistä lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja jälkeen on kuitenkin tyhjä rivi
                tulosteet.add("Avasit uuden saavutuksen: " + saavutukset[i] + "!");
            }
        }
        return tulosteet;
    }

    public ArrayList<String> tarkistaVoittosummanSaavutukset(int voitto) {
        //Tarkistetaan voiton suuruuteen perustuvat saavutukset
        int[] voittosummat = {100000, 50000, 10000, 5000, 1000, 750, 500, 200, 100, 20};
        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=20; i<30; i++){
            if (voitto >= voittosummat[i-20] && !saavutustenTilat[i]) {
                saavutustenTilat[i] = true;
                //Tehdään viesteistä lista, jotta ne voidaan tulostaa lopuksi pakettina jota ennen ja jälkeen on kuitenkin tyhjä rivi
                tulosteet.add("Avasit uuden saavutuksen: " + saavutukset[i] + "!");
            }
        }
        return tulosteet;
    }

    public void kadenTarkistus(String pelimuoto) {
        ArrayList<Maa> kasiMaat = new ArrayList<>();
        ArrayList<Integer> kasiArvot = new ArrayList<>();
        yhteishaviot = yhteishaviot + panos;

        //kasi =;

        for (int i=0; i<5; i++) {
            kasiMaat.add(kasi.get(i).getMaa());
        }
        for (int j=0; j<5; j++) {
            kasiArvot.add(kasi.get(j).getNumeroarvo());
        }


        if (testaaKuningasVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Kuningasvärisuora!");
            int kerroin = 250;
            voitonmaksu(kerroin, 0, pelimuoto);

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
            havitytJaot++;
        }
    }

    public void voitonmaksu(int kerroin, int kadenArvo, String pelimuoto) {

        if (panos == 0) {
            if (kerroin > 2) {
                System.out.println("Voitit " + (kerroin / 2) + 1 + " kolikkoa.");
                viimeisinVoitto = (kerroin / 2) + 1;
                viimeisinKadenArvo = kadenArvo;
                yhteisvoitot = yhteisvoitot + (kerroin / 2) + 1;
                voitetutJaot++;
            } else {
                setSaldo(getSaldo()+kerroin/2);
            }
        } else {
            System.out.println("Voitit " + panos*kerroin + " kolikkoa.");
            viimeisinVoitto = kerroin*panos;
            viimeisinKadenArvo = kadenArvo;
            yhteisvoitot = yhteisvoitot + kerroin*panos;
            voitetutJaot++;
        }

        if (pelimuoto.equals("vapaapeli")) {
            if (panos == 0 && kerroin > 2) {
                setSaldo(getSaldo()+(kerroin/2)+1);
            } else {
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
            //Jos pakassa on mukana jokereita
            for (int i=4; i>=0; i--) {
                if ((Collections.frequency(kasiMaat, maa) == i) && (Collections.frequency(kasiMaat, Maa.JOKERI) == 5-i)) {
                    return true;
                }
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