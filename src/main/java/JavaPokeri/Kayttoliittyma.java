package JavaPokeri;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Kayttoliittyma {

    private static Pelaaja pelaaja;

    public static void setPelaaja(Pelaaja pelaaja) {
        Kayttoliittyma.pelaaja = pelaaja;
    }

    /** Metodi tulostaa komentoriville pelin nimen suurella tyylitellyllä fontilla kun alkuvalikko avataan. */
    private static void tulostaBanner() {
        System.out.println("""    
                      
                      
                      
                             
                      ██╗ █████╗ ██╗   ██╗ █████╗ ██████╗  ██████╗ ██╗  ██╗███████╗██████╗ ██╗
                      ██║██╔══██╗██║   ██║██╔══██╗██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝██╔══██╗██║
                      ██║███████║██║   ██║███████║██████╔╝██║   ██║█████╔╝ █████╗  ██████╔╝██║
                 ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██╔══██╗██║
                 ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║██║     ╚██████╔╝██║  ██╗███████╗██║  ██║██║
                  ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝"""
        );
    }


    /**  Metodi käsittelee käyttäjän syötteen pelin alkuvalikossa ja suorittaa valitun
     toimenpiteen */
    public static void alkuvalikko() {
        tulostaBanner();

        boolean uudestaan = true;
        while (uudestaan) {
            tulostaAlkuvalikko();
            String input = tekstiSyote();

            try {
                switch (input) {
                    case "U" -> {
                        luoUusiKayttaja(haePelaajatiedot());
                        uudestaan = false;
                    }
                    case "L" -> {
                        lataaVanhaKayttaja(haePelaajatiedot());
                        uudestaan = false;
                    }
                    case "P" -> poistaKayttaja(haePelaajatiedot());
                    case "E" -> {
                        System.out.println();
                        System.out.println("Hei Hei! Kiitos kun pelasit!");
                        uudestaan = false;
                    }
                    default -> throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e){
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e){
                System.out.println("Virhe! Kelvoton syöte!\n" + "Syötä pelkkä käyttäjän numero.");
            }
        }
    }


    /** Metodi tulostaa pelin alkuvalikon */
    private static void tulostaAlkuvalikko() {
        System.out.println();
        System.out.println(
                """
                        ('U')  Uusi käyttäjä
                        ('L')  Lataa käyttäjä
                        ('P')  Poista käyttäjä
                        ('E')  Exit""");
        System.out.print("Valitse syöttämällä kirjain: ");
    }


    /** Metodi tarkistaa uuden käyttäjänimen kelvollisuuden ja luo uuden pelaajan. */
    private static void luoUusiKayttaja(ArrayList<Pelaaja> pelaajalista) throws CustomException{
        System.out.print("Käyttäjänimi: ");
        String kayttajanimi = tekstiSyote();
        if (kayttajanimi.length() > 30) {
            throw new CustomException("Liian pitkä käyttäjänimi! Maksimipituus on 30 merkkiä.");
        }
        if (pelaajalista.size() > 0) {
            if (joTallennettuPelaaja(kayttajanimi) != null) {
                lataaNimi(kayttajanimi, joTallennettuPelaaja(kayttajanimi));
            }
            else {
                uusiNimi(kayttajanimi);
            }

        } else {
            System.out.println();
            System.out.println("Tervetuloa " + kayttajanimi + "!");
            setPelaaja(new Pelaaja(kayttajanimi));
            tallennus();
            kotivalikko(true);
        }
    }


    /** Metodi tarkistaa onko syötetty uusi käyttäjänimi jo käytössä. */
    private static Pelaaja joTallennettuPelaaja(String kayttajanimi) {
        ArrayList<Pelaaja> pelaajalista = haePelaajatiedot();

        for (Pelaaja tallennettuPelaaja : pelaajalista) {
            if (tallennettuPelaaja.getNimi().equals(kayttajanimi)) {
                return tallennettuPelaaja;
            }
        }
        return null;
    }


    /** Metodi tarjoaa käyttäjälle mahdollisuuden latadata peli, jos selviää, että kyseinen käyttäjänimi
        onkin jo tallennettu järjestelmään. */
    private static void lataaNimi(String kayttajanimi, Pelaaja joTallennettuPelaaja) throws CustomException{
        System.out.println("Kyseinen käyttäjänimi on jo käytössä.");
        System.out.println("Haluatko ladata käyttäjän " + kayttajanimi + " pelin? ('K') Kyllä,  ('E') Ei");
        System.out.print("Valitse syöttämällä kirjain: ");
        String input = tekstiSyote();

        if (input.equals("K")) {
            setPelaaja(joTallennettuPelaaja);
            System.out.println();
            System.out.println("Tervetuloa takaisin " + pelaaja.getNimi() + "!");
            kotivalikko(true);

        } else if (input.equals("E")) {
            throw new CustomException("Valitse jokin muu käyttäjänimi.");
        } else {
            throw new CustomException("Kelvoton syöte!");
        }
    }


    /** Medodi luo uuden pelaajan valitulla käyttäjänimellä ja tervehtii uutta pelaajaa.  */
    private static void uusiNimi(String kayttajanimi) {
        setPelaaja(new Pelaaja(kayttajanimi));
        System.out.println();
        System.out.println("Tervetuloa " + kayttajanimi + "!");
        tallennus();
        kotivalikko(true);
    }


    /** Tallennetaan pelaajan tiedot tallennustiedostoon 'pelaajatioedot.ser', jotta pelaaja voi jatkaa siitä
     mihin jäi. */
    private static void tallennus() {
        ArrayList<Pelaaja> pelaajatiedot = haePelaajatiedot();
        ArrayList<Pelaaja> pelaajatiedotKopio = haePelaajatiedot();

        // Päivitetään olemassaolevan pelaajan tiedot tai lisätään uusi pelaaja. Käytetään listan
        // kopiota jotta vältytään ConcurrentModificationExceptionilta.
        if (pelaajatiedotKopio.size() > 0) {
            for (Pelaaja tallennettuPelaaja : pelaajatiedotKopio) {
                // Päivitetään pelaajan tiedot jos listasta löytyy sama nimi.
                if (tallennettuPelaaja.getNimi().equals(pelaaja.getNimi())) {
                    pelaajatiedot.set(pelaajatiedotKopio.indexOf(tallennettuPelaaja), pelaaja);
                    break;
                }
                // Jos pelaajalistasta ei löydy samaa nimeä, lisätään listaan uusi pelaaja.
                if (pelaajatiedotKopio.indexOf(tallennettuPelaaja) == pelaajatiedotKopio.size() - 1) {
                    pelaajatiedot.add(pelaaja);
                }
            }
            // Jos lista on tyhjä, lisätään siihen ensimmäinen pelaaja.
        } else {
            pelaajatiedot.add(pelaaja);
        }

        // Kirjoitetaan muutokset tallennustiedostoon
        try (FileOutputStream fos = new FileOutputStream("pelaajatiedot.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(pelaajatiedot);

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /** Metodi hakee ja palauttaa tallennustiedoston sisältämät pelaaja-oliot listamuodossa,
     tai tyhjän listan jos yhtään pelaajaa ei vielä ole tallennettu. */
    private static ArrayList<Pelaaja> haePelaajatiedot() {

        ArrayList<Pelaaja> pelaajalista;
        ArrayList<Pelaaja> tyhjaPelaajalista = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("pelaajatiedot.ser");
             ObjectInputStream ois = new ObjectInputStream(fis)){

            pelaajalista = (ArrayList) ois.readObject();
            // Jos tallennustiedosto pelaajatiedot.ser löytyy samasta hakemistosta,
            // palautetaan sen sisältämät oliot listamuodossa.
            return pelaajalista;

        } catch (IOException | ClassNotFoundException e) {
            // Jos tallennustiedostoa ei löydy samasta hakemistosta, palautetaan tyhjä lista.
            return tyhjaPelaajalista;
        }
    }


    /** Metodi tulostaa kaikki tallennetut pelaajat näkyviin ja lataa käyttäjän
        valitseman pelaajan pelin */
    private static void lataaVanhaKayttaja(ArrayList<Pelaaja> pelaajalista) throws CustomException {

        if (pelaajalista.size() > 0) {
            tulostaTallennetutKayttajat(pelaajalista);
            System.out.print("Valitse ladattava käyttäjä syöttämällä numero: ");
            int input = Integer.parseInt(tekstiSyote());

            if (input > pelaajalista.size() || input < 1) {
                throw new CustomException("Kelvoton syöte!");
            }

            setPelaaja(pelaajalista.get(input - 1));
            System.out.println();
            System.out.println("Tervetuloa takaisin " + pelaaja.getNimi() + "!");
            kotivalikko(true);

        } else {
            throw new CustomException("Ei yhtäkään tallennettua käyttäjää.");
        }
    }


    /** Metodi kysyy pelaajalta yhden poistettavan käyttäjän ja suorittaa metodin joka suorittaa poiston. */
    private static void poistaKayttaja(ArrayList<Pelaaja> pelaajalista) throws CustomException {

        if (pelaajalista.size() > 0) {
            tulostaTallennetutKayttajat(pelaajalista);
            System.out.print("Valitse poistettava käyttäjä syöttämällä numero: ");
            int input3 = Integer.parseInt(tekstiSyote());

            if (input3 > pelaajalista.size() || input3 < 1) {
                throw new CustomException("Kelvoton syöte!");
            }

            poistaValittuKayttaja(input3 - 1);
            System.out.println("Poistettu.");
            System.out.println();
        } else {
            throw new CustomException("Ei yhtäkään tallennettua käyttäjää.");
        }
    }


    /** Metodi tulostaa kaikki järjestelmään tallennetut käyttäjät pelaajan nähtäväksi. */
    private static void tulostaTallennetutKayttajat(ArrayList<Pelaaja> pelaajalista) {
        int i = 1;
        System.out.println();
        System.out.println("Tallennetut käyttäjät:");
        for (Pelaaja p : pelaajalista) {
            System.out.println("(" + i + ") " + p.getNimi());
            i++;
        }
    }


    /** Metodi poistaa yhden valitun käyttäjän tallennettujen käyttäjien joukosta. */
    private static void poistaValittuKayttaja(int indeksi) {

        ArrayList<Pelaaja> pelaajalista = haePelaajatiedot();
        pelaajalista.remove(indeksi);

        try (FileOutputStream fos = new FileOutputStream("pelaajatiedot.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos)){

            oos.writeObject(pelaajalista);

        } catch (IOException e) {
            System.out.println(e);
        }
    }


    /** Metodi käsittelee pelaajan syötteen pelin kotivalikossa ja suorittaa valitun
     toimenpiteen */
    private static void kotivalikko(boolean pelaajaaTervehditaan) {

        boolean uudestaan = true;
        while (uudestaan) {
            tulostaKotivalikko(pelaajaaTervehditaan);
            String input = tekstiSyote();

            try {
                switch (input) {
                    case "P" -> {
                        pelimuodotValikko();
                        uudestaan = false;
                    }
                    case "S" -> {
                        tulostaSaavutukset();
                        tulostaTilastot();
                    }
                    case "O" -> new Ohjeet().tulostaOhjeet();
                    case "T" -> {
                        alkuvalikko();
                        uudestaan = false;
                    }
                    case "192837465" -> talletusHuijaus();

                    default -> throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            }
        }
    }


    /** Metodi tulostaa pelaajan kotivalikon. */
    private static void tulostaKotivalikko(boolean pelaajaaTervehditaan) {
        System.out.println();
        // jos pelaaja tulee kotivalikkoon alkuvalikosta, häntä tervehditään.
        if (!pelaajaaTervehditaan) {
            System.out.println("Pelaaja: " + pelaaja.getNimi());
        }
        System.out.println("Saldo: " + pelaaja.getSaldo() + "\n" +
                """
                ('P')  Pelimuodot
                ('S')  Saavutukset ja tilastot
                ('O')  Ohjeet
                ('T')  Takaisin""");
        System.out.print("Valitse syöttämällä kirjain: ");
    }


    /** Metodi käsittelee pelaajan syötteen pelimuotovalikossa ja suorittaa valitun
     toimenpiteen. */
    private static void pelimuodotValikko() {

        boolean uudestaan = true;
        while (uudestaan) {
            tulostaPelimuodotValikko();
            String input = tekstiSyote();

            try {
                switch (input) {
                    case "V" -> {
                        vetopokeriValikko();
                        uudestaan = false;
                    }
                    case "?" -> throw new CustomException("Tämä pelimuoto on vielä työn alla :)");
                    case "O" -> new Ohjeet().tulostaOhjeet();
                    case "T" -> {
                        kotivalikko(false);
                        uudestaan = false;
                    }
                    case "192837465" -> talletusHuijaus();
                    default -> throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            }
        }
    }


    /** Metodi tulostaa pelimuotovalikon. */
    private static void tulostaPelimuodotValikko()  {
        System.out.println();
        System.out.println(
                """
                        --- PELIMUODOT ---
                        ('V')  Vetopokeri
                        ('?')  ?????
                        ('O')  Ohjeet
                        ('T')  Takaisin""");
        System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
    }


    /** Metodi tulostaa näkyviin pelaajan avaamat saavutukset. */
    private static void tulostaSaavutukset() {
        System.out.println();
        String[] saavutukset = new Saavutukset().getJasenet();
        Boolean[] saavutustenTilat = pelaaja.getSaavutustenTilat();
        System.out.println("Pelaaja: " + pelaaja.getNimi());
        System.out.println("--- SAAVUTUKSET ---");
        for (int i = 0; i < 35; i++) {
            if ((saavutustenTilat[i])) {
                String saavutus = saavutukset[i];
                int saavutuksenMerkkimaara = saavutus.length();
                System.out.print(saavutus);
                for (int j = saavutuksenMerkkimaara; j < 23; j++) {
                    System.out.print(".");
                }
                String[] saavutustenKuvaukset = new Saavutukset().getKuvaukset();
                System.out.println(saavutustenKuvaukset[i]);
            } else {
                System.out.println("?????");
            }
        }
    }


    /** Metodi tulostaa pelaajan pelihistoriaa koskevat tilastot.*/
    private static void tulostaTilastot() {
        System.out.println();
        System.out.println("--- TILASTOT ---");
        System.out.println("Suurin yksittäinen voitto: " + pelaaja.getSuurinVoitto() + " kolikkoa");
        System.out.println("Saldon suurin arvo: " + pelaaja.getSuurinSaldo() + " kolikkoa");
        System.out.println("Voitetut kolikot yhteensä: " + pelaaja.getYhteisvoitot());
        System.out.println("Hävityt kolikot yhteensä: " + pelaaja.getYhteishaviot());

        //double palautusprosentti = 0.0;
        //if (pelaaja.getYhteishaviot() > 0) {
        //palautusprosentti = (double) pelaaja.getYhteisvoitot()/pelaaja.getYhteishaviot() * 100;
        //}
        //DecimalFormat df = new DecimalFormat("#.##");
        //System.out.println("Palautusprosentti: " + df.format(palautusprosentti) + "%");

        System.out.println("Voitettujen jakojen määrä: " + pelaaja.getVoitetutJaot());
        System.out.println("Hävittyjen jakojen määrä: " + pelaaja.getHavitytJaot());

        // Mitä pienempi numeroarvo, sitä parempi käsi. Kuningasväriuoran arvo on 0 ja parin 9.
        if ((Integer) pelaaja.getParasKasi()[0] < 10) {
            pelaaja.tulostaParasKasi();
        } else {
            System.out.println("Paras käsi: -");
        }
        System.out.println();
    }


    /** Metodi käsittelee pelaajan syötteen vetopokerin valikossa ja suorittaa valitun
     toimenpiteen */
    private static void vetopokeriValikko() {

        boolean uudestaan = true;
        while (uudestaan) {
            tulostaVetopokeriValikko();
            String input = tekstiSyote();

            try {
                switch (input) {
                    case "V" -> {
                        vetopokeriVapaapeli();
                        uudestaan = false;
                    }
                    case "K" -> {
                        vetopokeriKilpapeli();
                        uudestaan = false;
                    }
                    case "P" -> {
                        vetopokeriParannukset();
                        uudestaan = false;
                    }
                    case "O" -> new Ohjeet().tulostaOhjeet();
                    case "T" -> {
                        pelimuodotValikko();
                        uudestaan = false;
                    }
                    default -> throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            }
        }
    }


    /** Metodi tulostaa vetopokerin valikon. */
    private static void tulostaVetopokeriValikko() {
        System.out.println();
        System.out.println(
                """
                        --- VETOPOKERI ---
                        ('V')  Vapaapeli
                        ('K')  Kilpapeli
                        ('P')  Parannukset
                        ('O')  Ohjeet
                        ('T')  Takaisin""");
        System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
    }


    /** Metodi käsittelee pelaajan syötteen vetopokerin vapaapelin valikossa ja suorittaa valitun
     toimenpiteen. */
    private static void vetopokeriVapaapeli() {

        boolean uudestaan = true;
        while (uudestaan) {
            tallennus();
            tulostaVetopokeriVapaapeliValikko();
            String input = tekstiSyote();

            try {
                if (input.equals("P")) {
                    uusiVetopokeriVapaapeli();

                } else if (input.equals("T")) {
                    vetopokeriValikko();
                    uudestaan = false;

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }

            } catch (ArithmeticException e) {
                pelaaja.setSaldo(2147483647);
                pelaaja.setKilpapelinSaldo(2147483647);
                System.out.println();
                System.out.println("Onnittelut! Kolikkojen määrä ylitti sallitun rajan (2147483647). Hanki elämä.");
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
            }
        }
    }


    /** Metodi tulostaa vetopokerin vapaapelin valikon. */
    private static void tulostaVetopokeriVapaapeliValikko() {
        System.out.println();
        System.out.println(
                "--- VETOPOKERI: Vapaapeli ---\n" +
                        "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n" +
                        "('P')  Pelaa\n" +
                        "('T')  Takaisin");
        System.out.print("Valitse syöttämällä kirjain: ");
    }


    /** Metodi aloittaa uuden vetopokerin vapaapelikierroksen. */
    private static void uusiVetopokeriVapaapeli() throws ArithmeticException {

        String pelimuoto = "vapaapeli";
        int minimipanos = 0;
        int maksimipanos =  500;
        int jokerienMaara = Collections.frequency(List.of(pelaaja.getVetopokeriParannustenTilat()), true);
        Korttipakka pakka = new Korttipakka(jokerienMaara);

        boolean uudestaan = true;
        while (uudestaan) {
            try {

                if (pelaaja.getSaldo() == 0) {
                    vapaapeliJaaJaTarkistaNollapanos(pakka, pelimuoto);
                    uudestaan = false;
                    break;
                }

                int panos = vetopokeriAsetaPanos(pelimuoto, pelaaja.getSaldo(), minimipanos, maksimipanos);

                if (panos == 0) {
                    vapaapeliJaaJaTarkistaNollapanos(pakka, pelimuoto);
                } else {
                    vapaapeliJaaJaTarkista(pakka);
                }
                uudestaan = false;

            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton panos!");
            }
        }
    }

    /** Metodi suorittaa yhteen vetopokerin vapaapelin kierrokseen kuuluvat toimenpiteet kun
        kierrokselle asetettu alkupanos on 0 kolikkoa. */
    private static void vapaapeliJaaJaTarkistaNollapanos(Korttipakka pakka, String pelimuoto)
            throws CustomException, ArithmeticException{

        pelaaja.setPanos(0);
        pelaaja.jaaUusiKasi(pakka.arvo5korttia());
        pelaaja.tulostaKasi(false);
        korttienVaihto(pakka, pelimuoto);
        pelaaja.kadenTarkistus(pelimuoto);
        System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
        System.out.println("----------------------------------");
        System.out.println();
    }


    /** Metodi tarkistaa vetopokerissa pelaajan asettaman alkupanoksen kelvollisuuden. */
    private static int vetopokeriAsetaPanos(String pelimuoto, int saldo, int min, int max) throws CustomException {

        System.out.print("Aseta alkupanos: ");
        String input = tekstiSyote();
        int panos = Integer.parseInt(input);

        if (panos > saldo) {
            if (pelimuoto.equals("vapaapeli")) {
                throw new CustomException("Liian suuri panos!" + "\n" +
                        "Pelitilin saldo: " + pelaaja.getSaldo());
            }
            if (pelimuoto.equals("kilpapeli")) {
                throw new CustomException("Liian suuri panos!" + "\n" +
                        "Kilpapelin saldo: " + pelaaja.getKilpapelinSaldo());
            }

        } if (panos < min) {
            if (pelimuoto.equals("vapaapeli")) {
                throw new CustomException("Negatiivinen panos!");
            }
            if (pelimuoto.equals("kilpapeli")) {
                throw new CustomException("Liian pieni panos! Kilpapelissä minimipanos on 10 kolikkoa.");
            }

        } if (panos > max) {
            if (pelimuoto.equals("vapaapeli")) {
                throw new CustomException("Liian suuri panos! Vapaapelissä suurin hyväksytty alkupanos on 500 kolikkoa.");
            }
            if (pelimuoto.equals("kilpapeli")) {
                throw new CustomException("Kaikella on rajansa. Jopa kilpapelin maksimipanoksella. (5000000 kolikkoa)");
            }
        }
        pelaaja.setPanos(panos);

        if (pelimuoto.equals("vapaapeli")) {
            pelaaja.setSaldo(pelaaja.getSaldo() - panos);
        }
        if (pelimuoto.equals("kilpapeli")) {
            pelaaja.setKilpapelinSaldo(pelaaja.getKilpapelinSaldo() - panos);
        }

        return panos;
    }


    /** Metodi suorittaa yhteen vetopokerin vapaapelin kierrokseen kuuluvat toimenpiteet kun
     kierrokselle asetettu alkupanos on suurempi kuin 0 kolikkoa. */
    private static void vapaapeliJaaJaTarkista(Korttipakka pakka) {
        String pelimuoto = "vapaapeli";
        pelaaja.jaaUusiKasi(pakka.arvo5korttia());
        pelaaja.tulostaKasi(false);
        korttienVaihto(pakka, pelimuoto);
        pelaaja.kadenTarkistus(pelimuoto);
        pelaaja.tarkistaSaavutukset();
        System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
        System.out.println("----------------------------------");
        System.out.println();
    }


    /** Metodi suorittaa pelaajalle jaetun käden korttien vaihtamisen. */
    private static void korttienVaihto(Korttipakka pakka, String pelimuoto) {

        boolean uudestaan = true;
        while (uudestaan) {

            System.out.println("('V')  Vaihda kortteja" + "\n" + "('P')  Pidä kortit");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = tekstiSyote();
            try {
                if (input.equals("V")) {
                    System.out.print("Valitse kortit, jotka haluat vaihtaa: ");
                    String[] vaihdettavat = tekstiSyote().split(",");
                    pelaaja.vaihdaKortteja(vaihdettavat, pakka);

                    if (pelaaja.getSaldo() > 0 && pelimuoto.equals("vapaapeli")) {
                        asetaVetopokeriLisapanos(pelimuoto);
                    }
                    if (pelaaja.getKilpapelinSaldo() > 0 && pelimuoto.equals("kilpapeli")) {
                        asetaVetopokeriLisapanos(pelimuoto);
                    }
                    pelaaja.tulostaKasi(true);
                    uudestaan = false;

                } else if (input.equals("P")) {
                    if (pelaaja.getSaldo() > 0 && pelimuoto.equals("vapaapeli")) {
                        asetaVetopokeriLisapanos(pelimuoto);
                    }
                    if (pelaaja.getKilpapelinSaldo() > 0 && pelimuoto.equals("kilpapeli")) {
                        asetaVetopokeriLisapanos(pelimuoto);
                    }
                    uudestaan = false;

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e)	{
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!\n" +
                        "Syötä vaihdettavien korttien indeksit pilkuilla erotettuina. Esim. '2,4,5'");
            }
        }
    }


    /** Metodi suorittaa toiminnon, jossa pelaaja voi halutessaan korottaa alkuperäistä panostaan. */
    private static void asetaVetopokeriLisapanos(String pelimuoto) {

        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println();
            System.out.println("Haluatko korottaa panosta? ('K') Kyllä, ('E') Ei");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = tekstiSyote();

            try {
                if (input.equals("K")) {
                    int maksimikorotus = pelaaja.getPanos()/2;

                    if (maksimikorotus > pelaaja.getSaldo() && pelimuoto.equals("vapaapeli")) {
                        maksimikorotus = pelaaja.getSaldo();
                    }
                    if (maksimikorotus > pelaaja.getKilpapelinSaldo() && pelimuoto.equals("kilpapeli")) {
                        maksimikorotus = pelaaja.getKilpapelinSaldo();
                    }

                    System.out.println("\nSuurin mahdollinen panoksen korotus: " + maksimikorotus + " kolikkoa");
                    System.out.println("""
                                        Valitse haluamasi lisäpanoksen suuruus syöttämällä luku,
                                        tai valitse suoraan maksimikorotus ('M').""");
                    System.out.print("Lisäpanoksen suuruus: ");
                    input = tekstiSyote();

                    tarkistaVetopokeriLisapanos(input, maksimikorotus, pelimuoto);
                    uudestaan = false;

                } else if (input.equals("E")) {
                    uudestaan = false;

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
            }
        }
    }


    /** Metodi tarkistaa pelaajan asettaman lisäpanoksen suuruuden kelvollisuuden. */
    private static void tarkistaVetopokeriLisapanos(String input, int maksimikorotus, String pelimuoto)
            throws CustomException {

        int lisapanos = -1;

        if (input.equals("M")) {
            lisapanos = maksimikorotus;
        }
        try {
            lisapanos = Integer.parseInt(input);
        } catch (NumberFormatException ignored) {}

        if (lisapanos >= 0 && lisapanos <= maksimikorotus) {
            if (pelimuoto.equals("vapaapeli")) {
                if (lisapanos > pelaaja.getSaldo()) {
                    throw new CustomException("Jäljellä oleva saldo: " + pelaaja.getSaldo() + " kolikkoa");
                }
                pelaaja.setPanos(lisapanos + pelaaja.getPanos());
                System.out.println("Uusi kokonaispanos: " + pelaaja.getPanos() + " kolikkoa");
                pelaaja.setSaldo(pelaaja.getSaldo() - lisapanos);
                System.out.println();
            }
            if (pelimuoto.equals("kilpapeli")) {
                if (lisapanos > pelaaja.getKilpapelinSaldo()) {
                    throw new CustomException("Jäljellä oleva kilpapelin saldo: " + pelaaja.getKilpapelinSaldo() + " kolikkoa");
                }
                pelaaja.setPanos(lisapanos + pelaaja.getPanos());
                System.out.println("Uusi kokonaispanos: " + pelaaja.getPanos() + " kolikkoa");
                pelaaja.setKilpapelinSaldo(pelaaja.getKilpapelinSaldo() - lisapanos);
            }

        } else {
            throw new CustomException("Kelvoton lisäpanos!");
        }
    }


    /** Metodi käsittelee pelaajan syötteen vetopokerin kilpapelin valikossa ja suorittaa valitun
     toimenpiteen */
    private static void vetopokeriKilpapeli() {
        int kilpapelinHinta = 100;

        boolean uudestaan = true;
        while (uudestaan) {
            tallennus();
            tulostaVetopokeriKilpapeliValikko();
            String input = tekstiSyote();

            try {
                switch (input) {
                    case "P" -> {
                        maksaUusiVetopokeriKilpapeli(kilpapelinHinta);
                        uudestaan = false;
                    }
                    case "O" -> tulostaVetopokeriKilpapelinTulostaulu();

                    case "H" -> {
                        ArrayList<Kilpapelitulos> halloffame = muodostaHallOfFame();
                        tulostaHallOfFame(halloffame);
                    }
                    case "T" -> {
                        vetopokeriValikko();
                        uudestaan = false;
                    }

                    default -> throw new CustomException("Kelvoton syöte!");
                }

            } catch (Exception e) {
                System.out.println("Virhe! " + e.getMessage());
            }
        }
    }

    /** Metodi tulostaa vetopokerin kilpapelin valikon. */
    private static void tulostaVetopokeriKilpapeliValikko() {
        System.out.println();
        System.out.println(
                "--- VETOPOKERI: Kilpapeli ---" + "\n" +
                        "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n" +
                        """
                        ('P')  Pelaa
                        ('O')  Omat tulokset
                        ('H')  Hall of Fame
                        ('T')  Takaisin""");
        System.out.print("Valitse syöttämällä kirjain: ");
    }


    /** Metodi suorittaa uuden kilpapelin aloittamista edeltävän maksuoperaation. */
    private static void maksaUusiVetopokeriKilpapeli(int kilpapelinHinta) throws CustomException {
        if (pelaaja.getSaldo() >= kilpapelinHinta) {
            System.out.println("\nUuden kilpapelin aloittaminen maksaa 100 kolikkoa. \n" +
                    "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa" + "\n" +
                    "Haluatko aloittaa uuden kilpapelin? ('K') Kyllä, ('E') Ei");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = tekstiSyote();
            if (input.equals("K")) {
                pelaaja.setSaldo(pelaaja.getSaldo() - kilpapelinHinta);
                System.out.println();
                uusiVetopokeriKilpapeli(kilpapelinHinta);

            } else if (input.equals("E")) {
                return;
            } else {
                throw new CustomException("Kelvoton syöte!");
            }

        } if (pelaaja.getSaldo() < kilpapelinHinta) {
            throw new CustomException("Ei riittävästi kolikoita!" + " Uuden kilpapelin aloittamiseen tarvitaan "+ kilpapelinHinta + " kolikkoa." +"\n" +
                    "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa");
        }
    }


    /** Metodi aloittaa uuden vetopokerin kilpapelin. */
    private static void uusiVetopokeriKilpapeli(int kilpapelinHinta) {

        pelaaja.setKilpapelinSaldo(kilpapelinHinta);
        String pelimuoto = "kilpapeli";
        int minimipanos = 10;
        int maksimipanos = Integer.MAX_VALUE;
        int jakojenMaara = 5;
        int jokerienMaara = Collections.frequency(List.of(pelaaja.getVetopokeriParannustenTilat()), true);
        Korttipakka pakka = new Korttipakka(jokerienMaara);
        int nykyinenJako = 1;
        tallennus();

        System.out.println("Uusi kilpapeli alkaa.");
        System.out.println("Kilpapelin saldo: " + pelaaja.getKilpapelinSaldo());

        while (nykyinenJako <= jakojenMaara) {
            System.out.println();
            System.out.println("--- " + nykyinenJako + ". JAKO ---");

            try {
                vetopokeriAsetaPanos(pelimuoto, pelaaja.getKilpapelinSaldo(), minimipanos, maksimipanos);
                kilpapeliJaaJaTarkista(pakka);

                if (pelaaja.getKilpapelinSaldo() < minimipanos && nykyinenJako < jakojenMaara) {
                    tulostaVetopokeriKilpapeliHavio();
                    vetopokeriKilpapeli();
                    break;
                }
                System.out.println("----------------------------------");
                System.out.println();

                if (nykyinenJako == jakojenMaara) {
                    paataVetopokeriKilpapeli();
                    vetopokeriKilpapeli();
                }
                nykyinenJako++;

            } catch (ArithmeticException e) {
                pelaaja.setSaldo(2147483647);
                pelaaja.setKilpapelinSaldo(2147483647);
                System.out.println();
                System.out.println("Onnittelut! Kolikkojen määrä ylitti sallitun rajan (2147483647). Hanki elämä.");
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton panos!");
            }
        }
    }


    /** Metodi suorittaa vetopokerin kilpapelin yhteen jakoon kuuluvat toimenpiteet. */
    private static void kilpapeliJaaJaTarkista(Korttipakka pakka) {
        String pelimuoto = "kilpapeli";
        pelaaja.jaaUusiKasi(pakka.arvo5korttia());
        pelaaja.tulostaKasi(false);
        korttienVaihto(pakka, pelimuoto);
        pelaaja.kadenTarkistus(pelimuoto);
        System.out.println("Pelaajan " + pelaaja.getNimi() + " kilpapelin saldo: " + pelaaja.getKilpapelinSaldo() + " kolikkoa");
        pelaaja.tarkistaSaavutukset();
    }


    /** Metodi tulostaa kilpapelin häviämisestä seuraavan viestin. */
    private static void tulostaVetopokeriKilpapeliHavio() {
        System.out.println("""
                
                Hävisit kilpapelin, sillä saldo tippui alle vaaditun minimipanoksen.
                Parempi onni ensi kerralla!
                ----------------------------------
                
                """);
    }


    /** Metodi päättää kilpapelin kun lopullinen jakojen määrä on saavutettu ja tulostaa kilpapelin
        tulokset ja mahdolliset avatut saavutukset. */
    private static void paataVetopokeriKilpapeli() {
        System.out.println("\nKilpapeli on päättynyt.");
        System.out.println("Pelaajan " + pelaaja.getNimi() + " lopulliset pisteet: " + pelaaja.getKilpapelinSaldo() + "\n");
        pelaaja.tarkistaKilpapelinSaavutukset(pelaaja.getKilpapelinSaldo());
        int tulos = pelaaja.getKilpapelinSaldo();
        pelaaja.lisaaKilpapelinTulos(tulos);
        pelaaja.setSaldo(pelaaja.getSaldo() + pelaaja.getKilpapelinSaldo());
        ArrayList<String> saldoSaavutukset = (pelaaja.tarkistaSaldonSaavutukset(pelaaja.getSaldo()));

        for (String saavutus : saldoSaavutukset) {
            System.out.println(saavutus);
        }
        if (pelaaja.getSaldo() > pelaaja.getSuurinSaldo()) {
            pelaaja.setSuurinSaldo(pelaaja.getSaldo());
        }
    }


    /** Metodi tulostaa pelaajan nähtäväksi hänen vetopokerin kilpapelin 10 parasta tulosta.  */
    private static void tulostaVetopokeriKilpapelinTulostaulu() {
        System.out.println();
        System.out.println("Pelaajan " + pelaaja.getNimi() + " vetopokerin kilpapelin TOP-10 tulokset:");
        int tulostenMaara = pelaaja.getKilpapelinTulokset().size();
        if (tulostenMaara < 10) {
            int j = 9;
            for (int i = 0; i < tulostenMaara; i++) {
                System.out.println(i + 1 + ".    " + pelaaja.getKilpapelinTulokset().get(i));
                j--;
            }
            for (int i = j; i >= 0; i--) {
                if (i == 0) {
                    System.out.println(10 - j + ".   0");
                    break;
                }
                System.out.println(10 - j + ".    0");
                j--;
            }
        } else {
            for (int i = 0; i < 10; i++) {
                if (i == 9) {
                    System.out.println(i + 1 + ".  " + pelaaja.getKilpapelinTulokset().get(i));
                    break;
                }
                System.out.println(i + 1 + ".   " + pelaaja.getKilpapelinTulokset().get(i));
            }
        }
        System.out.println();
    }


    /** Metodi luo kaikkien tallennettujen käyttäjien vetopokerin kilpapelin tuloksista ja tekaistuista tuloksista
     Hall of Fame tulostaulun, joka sisältää 10 parasta tulosta. */
    private static ArrayList<Kilpapelitulos> muodostaHallOfFame() {
        ArrayList<Pelaaja> pelaajalista = haePelaajatiedot();
        ArrayList<Kilpapelitulos> halloffameTulokset = new ArrayList<>();
        for (Pelaaja p: pelaajalista) {
            for (int kilpapelinTulos: p.getKilpapelinTulokset()) {
                halloffameTulokset.add(new Kilpapelitulos(p.getNimi(), kilpapelinTulos));
            }
        }
        // Lisätään Hall of Fame tuloksiin tekaistuja tuloksia
        halloffameTulokset.add(new Kilpapelitulos("Nahka-Lasse", 99999));
        halloffameTulokset.add(new Kilpapelitulos("NPC-Janne", 69420));
        halloffameTulokset.add(new Kilpapelitulos("Anil", 31415));
        halloffameTulokset.add(new Kilpapelitulos("Erno", 12345));
        halloffameTulokset.add(new Kilpapelitulos("Jytäpojat", 4999));
        halloffameTulokset.add(new Kilpapelitulos("Srinivasa", 2021));
        halloffameTulokset.add(new Kilpapelitulos("Levrai", 1357));
        halloffameTulokset.add(new Kilpapelitulos("Jari-Matti", 987));
        halloffameTulokset.add(new Kilpapelitulos("David Patterson", 444));
        halloffameTulokset.add(new Kilpapelitulos("Jean Baptiste Joseph Fourier", 2));
        halloffameTulokset.sort(Collections.reverseOrder());

        return halloffameTulokset;
    }


    /** Metodi tulostaa tyylitellysti aiemmin muodostetun Hall of Fame tulostaulun. */
    private static void tulostaHallOfFame(ArrayList<Kilpapelitulos> halloffameTulokset) {
        int pisinNimi = 0;
        for (int i = 0; i < 10; i++) {
            int nimenPituus = halloffameTulokset.get(i).getNimi().length();
            if (nimenPituus > pisinNimi) {
                pisinNimi = nimenPituus;
            }
        }
        System.out.println();
        System.out.println("--- HALL OF FAME ---");
        for (int i = 1; i <= 10; i++) {
            String nimi = halloffameTulokset.get(i-1).getNimi();
            int pisteet = halloffameTulokset.get(i-1).getPisteet();

            // Kymmenennellä rivillä yksi välilyönti vähemmän, jotta tulokset alkavat samalta pystyriviltä
            if (i == 10) {
                System.out.print(i + ". " +  nimi);
                tulostaVali(nimi.length(), pisinNimi);
                System.out.println(pisteet);
                break;
            }
            System.out.print(i + ".  " +  nimi);
            tulostaVali(nimi.length(), pisinNimi);
            System.out.println(pisteet);
        }
        System.out.println();
    }


    /** Metodi suorittaa pelaajan valitsemat toimenpiteet vetopokerin parannuskaupassa käyttämällä
        apunaan VetopokeriParannusKauppa-luokkaa. */
    private static void vetopokeriParannukset() {

        boolean uudestaan = true;
        while(uudestaan) {
            try {
                VetopokeriParannusKauppa kauppa = new VetopokeriParannusKauppa(pelaaja);
                kauppa.tulostaVetopokeriParannukset();
                String input = tekstiSyote();

                if (kauppa.ostoMahdollista(input)) {
                    kauppa.parannuksenOsto();

                } else if (input.equals("T")) {
                    vetopokeriValikko();
                    uudestaan = false;

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }

                tallennus();

            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
            }
        }
    }





    /** Metodi palauttaa käyttäjän kirjoittaman syötteen */
    static String tekstiSyote() {
        Scanner lukija = new Scanner(System.in);
        return lukija.nextLine();
    }


    /** Metodi tulostaa kahden merkkijonon väliin tarvittavan määrän pisteitä, että kaikki merkkijonot
     alkavat samoilta pystyriveiltä */
    private static void tulostaVali(int nimenPituus, int pisinNimi) {
        for (int i = nimenPituus; i <= pisinNimi + 3; i++)
            System.out.print(".");
    }


    /** Metodi mahdollistaa kolikoiden rajattoman tallettamisen pelaajan tilille. Kutsuakseen
        metodia pelaajan täytyy tietää salainen numeroyhdistelmä ja paikka johon se tulee syöttää. */
    private static void talletusHuijaus() throws CustomException {
        System.out.print("Talletettavien kolikoiden määrä: ");
        int maara = Integer.parseInt(tekstiSyote());
        if (maara >= 0) {
            pelaaja.setSaldo(maara + pelaaja.getSaldo());
            System.out.println("Uusi saldo: " + pelaaja.getSaldo() + " kolikkoa");
            System.out.println();
        } else {
            throw new CustomException("Negatiivinen talletus!");
        }
    }


}
