package JavaPokeri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pelaaja implements Serializable {
    private String nimi;
    private int saldo;
    private int suurinSaldo;
    private int panos;
    private int lisapanos;
    private int kilpapelinSaldo;
    private ArrayList<Integer> kilpapelinTulokset;
    private ArrayList<Kortti> kasi;
    private Object[] parasKasi;
    private Boolean[] saavutustenTilat;
    private Boolean[] vetopokeriParannustenTilat;
    private int viimeisinVoitto;
    private int suurinVoitto;
    private int viimeisinKadenArvo;
    private int yhteisvoitot;
    private int yhteishaviot;
    private int voitetutJaot;
    private int havitytJaot;


    public Pelaaja(String nimi) {
        this.nimi = nimi;
        saldo = 20;
        suurinSaldo = 20;
        panos = 0;
        lisapanos = 0;
        kilpapelinSaldo = 0;
        kilpapelinTulokset = alustaKilpapelinTulokset();
        kasi = alustaKasi();
        parasKasi = alustaParasKasi();
        saavutustenTilat = alustaSaavutustenTilat();
        vetopokeriParannustenTilat = alustaVetopokeriParannustenTilat();
        viimeisinVoitto = 0;
        suurinVoitto = 0;
        viimeisinKadenArvo = -1;
        yhteisvoitot = 0;
        yhteishaviot = 0;
        voitetutJaot = 0;
        havitytJaot = 0;
    }

    public Pelaaja(ArrayList<Kortti> kortit) {
        kasi = kortit;
    }

    public String getNimi() {return nimi;}

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSuurinSaldo() { return suurinSaldo; }

    public int getPanos() {
        return panos;
    }

    public void setPanos(int panos) {
        this.panos = panos;
    }

    public int getLisapanos() {return lisapanos; }

    public void setLisapanos(int lisapanos) { this.lisapanos = lisapanos; }

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

    public ArrayList<Kortti> getKasi() {
        return kasi;
    }

    public void setKasi(ArrayList<Kortti> kasi) {
        this.kasi = kasi;
    }

    public Object[] getParasKasi() { return parasKasi; }

    public void setParasKasi(Object[] parasKasi) { this.parasKasi = parasKasi; }

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

    public int getSuurinVoitto() { return suurinVoitto; }

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

    public Object[] alustaParasKasi() {
        ArrayList<Kortti> huonokasi = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.YHDEKSÄN),
                new Kortti(Maa.HERTTA, Arvo.KYMMENEN) ));
        Object[] paraskasi = {10, huonokasi};
        return paraskasi;
    }

    public void tulostaKasi(boolean korttienVaihtoTapahtunut) {
        System.out.println();
        if (korttienVaihtoTapahtunut) {
            System.out.println("Käsi:");
        } else {
            System.out.println("Uusi Käsi:");
        }
        System.out.println("(1)   " + kasi.get(0));
        System.out.println("(2)   " + kasi.get(1));
        System.out.println("(3)   " + kasi.get(2));
        System.out.println("(4)   " + kasi.get(3));
        System.out.println("(5)   " + kasi.get(4));
        System.out.println();
    }

    public void tulostaParasKasi() {
        System.out.println();
        ArrayList<Kortti> paraskasi = (ArrayList<Kortti>) parasKasi[1];
        System.out.println("Paras käsi: ");
        System.out.println(paraskasi.get(0));
        System.out.println(paraskasi.get(1));
        System.out.println(paraskasi.get(2));
        System.out.println(paraskasi.get(3));
        System.out.println(paraskasi.get(4));
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
        ArrayList<String> tulosteet3 = tarkistaSaldonSaavutukset(saldo);
        ArrayList<String> tulosteet4 = new ArrayList<>();
        //Tehdään jaon tuloksena avattujen saavutusten tulosteista lista, jotta ne voidaan tulostaa siististi konsoliin
        for (int i=1; i<tulosteet1.size(); i++) {
            tulosteet4.add(tulosteet1.get(i));
        }
        for (int i=1; i<tulosteet2.size(); i++) {
            tulosteet4.add(tulosteet2.get(i));
        }
        for (int i=1; i<tulosteet3.size(); i++) {
            tulosteet4.add(tulosteet3.get(i));
        }
        if (tulosteet4.size() >= 1) {
            System.out.println();
            for (String tuloste: tulosteet4) {
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

    public ArrayList<String> tarkistaSaldonSaavutukset(int saldo) {
        //Tarkistetaan saldon suuruuteen perustuvat saavutukset
        int[] saldorajat = {1000000, 100000, 10000, 1000, 100};
        String[] saavutukset = new Saavutukset().getJasenet();
        ArrayList<String> tulosteet = new ArrayList<>();
        tulosteet.add(" ");
        for (int i=30; i<35; i++){
            if (saldo >= saldorajat[i-30] && !saavutustenTilat[i]) {
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

        for (int i=0; i<5; i++) {
            kasiMaat.add(kasi.get(i).getMaa());
        }
        for (int j=0; j<5; j++) {
            kasiArvot.add(kasi.get(j).getNumeroarvo());
        }

        int kadenArvojenSumma = kasiArvot.stream().mapToInt(i -> i).sum();

        //Tätä paremmaksi laittamalla mahdolliset tulostukset tauluun ja tiivistämällä kaikki yhteen blokkiin
        //silloin myös

        ArrayList<Kortti> paraskasi = (ArrayList<Kortti>) parasKasi[1];
        ArrayList<Integer> paraskasiarvot = new ArrayList<>();
        for (int j=0; j<5; j++) {
            paraskasiarvot.add(paraskasi.get(j).getNumeroarvo());
        }

        int kasiJokerienMaara = Collections.frequency(kasiArvot, 0);
        int parasKasiJokerienMaara = Collections.frequency(paraskasiarvot, 0);


        //Tarkistetaan toteuttaako käsi jonkin voittavan käden kriteerit
        if (testaaKuningasVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Kuningasvärisuora!");
            int kerroin = 250;
            voitonmaksu(kerroin, 0, pelimuoto);

            //Vaihdetaan kyseinen käsi uudeksi parhaaksi kädeksi jos se on arvoltaan parempi
            if ((Integer) parasKasi[0] > 0 || (kasiJokerienMaara > parasKasiJokerienMaara && (Integer) parasKasi[0] == 0)) {
                parasKasi[0] = 0;
                parasKasi[1] = kasi;
            }

        } else if (testaaViitoset(kasiArvot)) {
            System.out.println("Tulos: Viitoset!");
            int kerroin = 250;
            voitonmaksu(kerroin, 1, pelimuoto);

            if ((Integer) parasKasi[0] > 1 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 1)) {
                parasKasi[0] = 1;
                parasKasi[1] = kasi;
            }

        } else if (testaaVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Värisuora!");
            int kerroin = 75;
            voitonmaksu(kerroin, 2, pelimuoto);

            if ((Integer) parasKasi[0] > 2 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 2)) {
                parasKasi[0] = 2;
                parasKasi[1] = kasi;
            }

        } else if (testaaNeloset(kasiArvot)) {
            System.out.println("Tulos: Neloset!");
            int kerroin = 50;
            voitonmaksu(kerroin, 3, pelimuoto);

            if ((Integer) parasKasi[0] > 3 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 3)) {
                parasKasi[0] = 3;
                parasKasi[1] = kasi;
            }

        } else if (testaaTayskasi(kasiArvot)) {
            System.out.println("Tulos: Täyskäsi!");
            int kerroin = 20;
            voitonmaksu(kerroin, 4, pelimuoto);

            if ((Integer) parasKasi[0] > 4 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 4)) {
                parasKasi[0] = 4;
                parasKasi[1] = kasi;
            }

        } else if (testaaVari(kasiMaat)) {
            System.out.println("Tulos: Väri!");
            int kerroin = 15;
            voitonmaksu(kerroin, 5, pelimuoto);

            if ((Integer) parasKasi[0] > 5 || (kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 5)) {
                parasKasi[0] = 5;
                parasKasi[1] = kasi;
            }

        } else if (testaaSuora(kasiArvot)) {
            System.out.println("Tulos: Suora!");
            int kerroin = 10;
            voitonmaksu(kerroin, 6, pelimuoto);

            if ((Integer) parasKasi[0] > 6 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara && (Integer) parasKasi[0] == 6)) {
                parasKasi[0] = 6;
                parasKasi[1] = kasi;
            }

        } else if (testaaKolmoset(kasiArvot)) {
            System.out.println("Tulos: Kolmoset!");
            int kerroin = 5;
            voitonmaksu(kerroin, 7, pelimuoto);

            if ((Integer) parasKasi[0] > 7 || (kasi.get(4).getNumeroarvo() > paraskasiarvot.get(4) &&
                    kasiJokerienMaara >= parasKasiJokerienMaara &&(Integer) parasKasi[0] == 7)) {
                parasKasi[0] = 7;
                parasKasi[1] = kasi;
            }

        } else if (testaaKaksiParia(kasiArvot)) {
            System.out.println("Tulos: Kaksi paria!");
            int kerroin = 3;
            voitonmaksu(kerroin, 8, pelimuoto);

            if ((Integer) parasKasi[0] > 8) {
                parasKasi[0] = 8;
                parasKasi[1] = kasi;
            }

        } else if (testaaPari(kasiArvot)) {
            System.out.println("Tulos: 10-A pari!");
            int kerroin = 2;
            voitonmaksu(kerroin, 9, pelimuoto);

            if ((Integer) parasKasi[0] > 9) {
                parasKasi[0] = 9;
                parasKasi[1] = kasi;
            }

        } else {
            System.out.println("Ei voittoa.");
            viimeisinVoitto = 0;
            viimeisinKadenArvo = -1;
            havitytJaot++;
        }
    }


    public void voitonmaksu(int kerroin, int kadenArvo, String pelimuoto) {

        if (panos == 0) {
            //Pyöristetään nollapanoksen voitot ylöspäin lähimpään kokonaislukuun
            viimeisinVoitto = (kerroin+1)/2 ;
            yhteisvoitot = yhteisvoitot + (kerroin+1)/2;
        } else {
            viimeisinVoitto = kerroin*panos;
            yhteisvoitot = yhteisvoitot + kerroin*panos;
        }

        viimeisinKadenArvo = kadenArvo;
        voitetutJaot++;

        System.out.println("Voitit " + viimeisinVoitto + " kolikkoa.");
        /*if (panos == 0) {
            if (kerroin > 2) {
                System.out.println("Voitit " + (kerroin / 2) + 1 + " kolikkoa.");
                viimeisinVoitto = (kerroin / 2) + 1;
                viimeisinKadenArvo = kadenArvo;
                yhteisvoitot = yhteisvoitot + (kerroin / 2) + 1;
                voitetutJaot++;
            } else {
                //setSaldo(getSaldo()+kerroin/2);
            }
        } else {
            System.out.println("Voitit " + panos*kerroin + " kolikkoa.");
            viimeisinVoitto = kerroin*panos;
            viimeisinKadenArvo = kadenArvo;
            yhteisvoitot = yhteisvoitot + kerroin*panos;
            voitetutJaot++;
        }*/

        if (pelimuoto.equals("vapaapeli")) {
                setSaldo(getSaldo() + viimeisinVoitto);
        }

        if (pelimuoto.equals("kilpapeli")) {
            setKilpapelinSaldo(getKilpapelinSaldo() + viimeisinVoitto);
        }

        if (viimeisinVoitto > suurinVoitto) {
            suurinVoitto = viimeisinVoitto;
        }

        if (saldo > suurinSaldo) {
            suurinSaldo = saldo;
        }

    }

    public int kasiArvotIlmanJokereitaSumma() {
        ArrayList<Integer> kasiArvotIlmanJokereita = new ArrayList<>();
        for (int j=0; j<5; j++) {
            kasiArvotIlmanJokereita.add(kasi.get(j).getNumeroarvo());
        }
        int jokerienMaara = Collections.frequency(kasiArvotIlmanJokereita, 0);
        for (int i=0; i<jokerienMaara; i++) {
            kasiArvotIlmanJokereita.remove(Integer.valueOf(0));
        }
        int kasiArvotIlmanJokereitaSumma = kasiArvotIlmanJokereita.stream().mapToInt(i -> i).sum();

        return kasiArvotIlmanJokereitaSumma;
    }


    public boolean testaaKuningasVarisuora(ArrayList<Integer> kasiArvot, ArrayList<Maa> kasiMaat) {
        if (testaaVari(kasiMaat) && testaaKuningasSuora(kasiArvot)) {
            return true;
        }
        return false;
    }

    public boolean testaaViitoset(ArrayList<Integer> kasiArvot) {
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        //Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteismäärä on viisi, käsi on arvoltaan viitoset.
        for (int i=1; i<14; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 5) {
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
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        //Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteismäärä on neljä, käsi on arvoltaan neloset.
        for (int i=1; i<14; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 4) {
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
        int jokerienMaara = Collections.frequency(kasiMaat, Maa.JOKERI);

        //Jos jokerien ja jonkin tietyn maan esiintymien yhteismäärä on viisi, käsi on arvoltaan väri.
        for (Maa maa : maalista) {
            if (Collections.frequency(kasiMaat, maa) + jokerienMaara == 5) {
                return true;
            }
            /*
            //Jos pakassa on mukana jokereita
            for (int i=4; i>=0; i--) {
                if ((Collections.frequency(kasiMaat, maa) == i) && (Collections.frequency(kasiMaat, Maa.JOKERI) == 5-i)) {
                    return true;
                }
            }*/

        }
        return false;
    }

    public boolean testaaKuningasSuora(ArrayList<Integer> kasiArvot) {
        //Tehdään kasiArvot listasta kopio jotta vältytään sivuvaikutuksilta.
        ArrayList<Integer> kasiArvotKopio = new ArrayList<>(kasiArvot);
        //Jos käsi on suora ja sen pienin arvo on 10, käsi on myös kuningassuora.
        if (testaaSuora(kasiArvotKopio)) {
            int jokerienMaara = Collections.frequency(kasiArvotKopio, 0);
            for (int i=0; i<jokerienMaara; i++) {
                kasiArvotKopio.remove(Integer.valueOf(0));
            }
            if (Collections.min(kasiArvotKopio) >= 10) {
                return true;
            }
        }
        return false;
    }


    public boolean testaaSuora(ArrayList<Integer> kasiArvot) {
        //Järjestetään käden korttien numeroarvot. Jokerin arvo on 0, joten ne menevät listan alkuun.
        Collections.sort(kasiArvot);
        int perakkaiset = 0;
        for (int i=0; i<4; i++) {
            if (kasiArvot.get(i)+1 == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
            //Otetaan myös jokerit huomioon. Jokeri muodostaa aina peräkkäisyyden.
              else if (kasiArvot.get(i) + kasiArvot.get(i+1) == kasiArvot.get(i)) {
                perakkaiset++;
            } else if (kasiArvot.get(i) + kasiArvot.get(i+1) == kasiArvot.get(i+1)) {
                perakkaiset++;
            }
        }
        //Tilanteet joissa kaikki kortit ovat arvoiltaan peräkkäisessä järjestyksessä.
        //Kattaa myös tilanteet joissa on 4 tai 5 jokeria.
        if (perakkaiset == 4) {
            return true;
        }

        //Tarkistetaan vielä kaikki tilanteet jossa jokereita on 1, 2 tai 3 kappaletta.
        int jokerienMaara = Collections.frequency(kasiArvot, 0);

        //Jos jokereita on yksi, normaaleista neljästä kortista suurimman ja pienimmän arvon
        // etäisyys toisistaan saa olla enintään 4, mutta vähintään 1.
        int suurinNormaalinKortinArvo = kasiArvot.get(4);
        int pieninNormaalinKortinArvo = kasiArvot.get(1);
        int arvojenEtaisyys = suurinNormaalinKortinArvo-pieninNormaalinKortinArvo;
        if (jokerienMaara == 1 && arvojenEtaisyys < 5 && arvojenEtaisyys > 2) {
            return true;
        }

        //Jos jokereita on kaksi, normaaleista kolmesta kortista suurimman ja pienimmän arvon
        // etäisyys toisistaan saa olla enintään 4, mutta vähintään 1.
        suurinNormaalinKortinArvo = kasiArvot.get(4);
        pieninNormaalinKortinArvo = kasiArvot.get(2);
        arvojenEtaisyys = suurinNormaalinKortinArvo-pieninNormaalinKortinArvo;
        if (jokerienMaara == 2 && arvojenEtaisyys < 5 && arvojenEtaisyys > 1) {
            return true;
        }

        //Jos jokereita on kolme, kahden normaalin kortin arvojen etäisyys toisistaan saa olla enintään 4,
        //mutta vähintään 1.
        int suuremmanNormaalinKortinArvo = kasiArvot.get(4);
        int pienemmanNormaalinKortinArvo = kasiArvot.get(3);
        arvojenEtaisyys = suuremmanNormaalinKortinArvo-pienemmanNormaalinKortinArvo;
        if (jokerienMaara == 3 && arvojenEtaisyys < 5 && arvojenEtaisyys > 0) {
            return true;
        }

        //Tilanteet, joissa ässä on suoran pienin kortti, täytyy tutkia erikseen.
        if (perakkaiset >= 2 && kasiArvot.contains(14) && kasiArvot.get(3) < 6) {
            return true;
        }
        return false;
    }


    public boolean testaaKolmoset(ArrayList<Integer> kasiArvot) {
        int jokerienMaara = Collections.frequency(kasiArvot, 0);
        //Jos jokerien ja jonkin tietyn arvoisen kortin esiintymien yhteismäärä on kolme, käsi on arvoltaan kolmoset.
        for (int i=1; i<14; i++) {
            if (jokerienMaara + Collections.frequency(kasiArvot, i) == 3) {
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
        //Jokeria ei tarvitse erikseen testata, sillä kolmoset ovat aina parempi kuin kaksi paria.
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