package JavaPokeri;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pelaaja {
    private String nimi;
    private int saldo;
    private int panos;
    private ArrayList<Kortti> kasi;

    public Pelaaja(String nimi) {
        this.nimi = nimi;
        panos = 0;
        saldo = 0;
        kasi = new ArrayList<Kortti>(List.of(new Kortti(Maa.PATA, Arvo.ÄSSÄ), new Kortti(Maa.PATA, Arvo.KAKSI), new Kortti(Maa.PATA, Arvo.KOLME), new Kortti(Maa.PATA, Arvo.NELJÄ), new Kortti(Maa.HERTTA, Arvo.KUUSI)));
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

    public void tulostaKasi() {
        System.out.println("1   " + kasi.get(0));
        System.out.println("2   " + kasi.get(1));
        System.out.println("3   " + kasi.get(2));
        System.out.println("4   " + kasi.get(3));
        System.out.println("5   " + kasi.get(4));
    }

    public void jaaUusiKasi(ArrayList<Kortti> kasi) {
        this.kasi = kasi;
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

    public void tarkistus() {
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
            System.out.println("Voitit " + panos*250 + " kolikkoa.");
            setSaldo(getSaldo()+panos*250);

        } else if (testaaViitoset(kasiArvot)) {
            System.out.println("Tulos: Viitoset!");
            System.out.println("Voitit " + panos*250 + " kolikkoa.");
            setSaldo(getSaldo()+panos*250);

        } else if (testaaVarisuora(kasiArvot, kasiMaat)) {
            System.out.println("Tulos: Värisuora!");
            System.out.println("Voitit " + panos*75 + " kolikkoa.");
            setSaldo(getSaldo()+panos*75);

        } else if (testaaNeloset(kasiArvot)) {
            System.out.println("Tulos: Neloset!");
            System.out.println("Voitit " + panos*50 + " kolikkoa.");
            setSaldo(getSaldo()+panos*50);

        } else if (testaaTayskasi(kasiArvot)) {
            System.out.println("Tulos: Täyskäsi!");
            System.out.println("Voitit " + panos*20 + " kolikkoa.");
            setSaldo(getSaldo()+panos*20);

        } else if (testaaVari(kasiMaat)) {
            System.out.println("Tulos: Väri!");
            System.out.println("Voitit " + panos*15 + " kolikkoa.");
            setSaldo(getSaldo()+panos*15);

        } else if (testaaSuora(kasiArvot)) {
            System.out.println("Tulos: Suora!");
            System.out.println("Voitit " + panos*10 + " kolikkoa.");
            setSaldo(getSaldo()+panos*10);

        } else if (testaaKolmoset(kasiArvot)) {
            System.out.println("Tulos: Kolmoset!");
            System.out.println("Voitit " + panos*5 + " kolikkoa.");
            setSaldo(getSaldo()+panos*5);

        } else if (testaaKaksiParia(kasiArvot)) {
            System.out.println("Tulos: Kaksi paria!");
            System.out.println("Voitit " + panos*3 + " kolikkoa.");
            setSaldo(getSaldo()+panos*3);

        } else if (testaaPari(kasiArvot)) {
            System.out.println("Tulos: 10-pari tai parempi!");
            System.out.println("Voitit " + panos*2 + " kolikkoa.");
            setSaldo(getSaldo()+panos*2);

        } else {
            System.out.println("Ei voittoa.");
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