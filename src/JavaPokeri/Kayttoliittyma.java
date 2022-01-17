package JavaPokeri;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Kayttoliittyma {


    /** branch maven**/

    //siisti koodia ja laita sitten gittiin vain yksi repo "JavaPokeri" jossa koodi ja jar tiedosto

    //TODO:
    // -käyttäjän vaihto
    // -alkuun pelin lataamiseen lista tallennetuista käyttäjistä
    // -(käyttäjän poisto)
    // -korjaa ässän sisältävien käsien tarkistus
    // -lisää pakkaan jokeri ja korjaa tarkistukset sen mukaan
    // -testaa viitoset, koska jokeri
    // -kortin enum-luokka voisi olla parempi, ja kuvakorteilla voisi olla symbolit
    // -       commit1 versio 1
    // -mahdollisuus valita eri pelimuotoja
    // -pelimuoto (vapaa peli / kilpapeli)
    // -mahdollistettu panoksen korotus ensimmäisen käden jälkeen (max 1/2 alkup.panos)
    // -       commit2 versio 1.1
    // -tallennus korjattu heti uuden käyttäjän luomiseen
    // -lisää ohjesivut
    // -lisää voittotaulukko
    // -kilpapeli pelimuoto
    // -vapaapeli pelimuoto
    // -ei tarvitse tarjota mahdollisuutta panoksen korotukseen jos kolikoita on nolla
    // -lisää avattavia saavutuksia
    // -Hall of fame
    // -oma tulostaulukko
    // -pelimuodon valinta
    // -+muuta korjailua ja parantelua
    // -        commit3 versio 1.5
    // -ei enää json
    // -pelaajatiedot.ser
    // -saavutusten alustus muutettu
    // -pelaajalle yhteisvoitot attribuutti
    // -        commit4 versio 1.6
    // -lisätty yhteisvoitot, yhteishäviöt
    // -palautusprosentti
    // -voitetut ja hävityt jaot
    // -        commit5 versio 1.7
    // -pelaajalle lisätty parannukset attribuutti
    // -palautusprosentin pyöristys
    // -JavaPokeri banner
    // -bugi korjattu kilpapelistä
    // -bugi korjattu alkuvalikosta
    // -bugi korjattu kauppavalikosta
    // -tilastoille oma otsikko
    // -alkusaldo nostettu 20 kolikkoon
    // -nollapanoksen voitot pyöristetään ylöspäin
    // -max panos 500 kolikkoa
    // -AvattavatAsiat
    // -lisätty jokerin ostomahdollisuus
    // -alussa ei jokereita
    // -korttipakalle attribuutti jokerien määrä
    // -implementoi parannuksen oston jälkeiset tapahtumat
    // -vain ensimmäisen ostettavan parannuksen voi ostaa
    // -koodin siistiminen
    // -koodicommit 1.7.9
    //-tarkistukset kun pakassa on 2 jokeria tai enemmän
    //-         commit versio 1.8

    //-jaa käyttöliittymäluokkaa useampiin järkeviin osiin eri luokiksi
    //-lisää kommentointi tarvittaviin metodeihin
    //-korjaa metodien näkyvyydet
    //-lisää mahdollisuus kahden tai useamman pelaajan pelimuotoon, jossa korotetaan panosta ja pelataan muita vastaan
    //-pikapokeri pelimuoto
    //-kilpapelin muoto joka kalliimpi ja jossa paljon jokereita
    //-saavutuksiin lisää tilastoja
    //-oma tilastot sivu
    //-tillastoihin paras käsi
    //-saatujen eri käsien määrä
    //-kauppa jossa ostettavia parannuksia, kuten lisää jokereita pakkaan
    //-jos paranuksia, alku oltava ehkä vaikeampi
    //-myös kalliimpia pelimuotoja kilpapeliin
    //-lisää blackjack pelimuoto
    //-uusia saavutuksia: voittoputki, häviöputki, salon määrä vapaapelissä
    //-vapaapeliin myös hall of fame




    /** Tallennetaan pelaajan tiedot tallennustiedostoon 'pelaajatioedot.ser', jotta peliä voidaan jatkaa
        siitä mihin jäätiin. */
    public static void tallennus(Pelaaja tallennettavaPelaaja) {
        // Haetaan tallennustiedostostaa pelaajien tiedot pelaajalistaan, jotta tietoja on helppo käsitellä.
        ArrayList<Pelaaja> pelaajalista = haePelaajalista();
        ArrayList<Pelaaja> pelaajalistaKopio = haePelaajalista();

        // Päivitetään olemassaolevan pelaajan tiedot tai lisätään uusi pelaaja. Käytetään apuna listaa
        // pelaajalistaKopio, jotta vältytään ConcurrentModificationExceptionilta.
        if (pelaajalistaKopio.size() > 0) {
            for (Pelaaja tallennettuPelaaja : pelaajalistaKopio) {
                // Päivitetään pelaajan tiedot jos listasta löytyy sama nimi.
                if (tallennettuPelaaja.getNimi().equals(tallennettavaPelaaja.getNimi())) {
                    pelaajalista.set(pelaajalistaKopio.indexOf(tallennettuPelaaja), tallennettavaPelaaja);
                    break;
                }
                // Jos pelaajalistasta ei löydy samaa nimeä, lisätään listaan uusi pelaaja.
                if (pelaajalistaKopio.indexOf(tallennettuPelaaja) == pelaajalistaKopio.size()-1) {
                    pelaajalista.add(tallennettavaPelaaja);
                }
            }

        //Lisätään tyhjään listaan ensimmäinen pelaaja
        } else {
            pelaajalista.add(tallennettavaPelaaja);
        }

        // Kirjoitetaan muutokset tallennustiedostoon
        try {
            FileOutputStream fos = new FileOutputStream("pelaajatiedot.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pelaajalista);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println();
        }

    }


    /** Metodi hakee ja palauttaa tallennustiedoston sisältämät pelaaja-oliot listamuodossa,
        tai tyhjän listan jos pelaajia ei vielä ole tallennettu. */
    public static ArrayList<Pelaaja> haePelaajalista() {
        ArrayList<Pelaaja> pelaajalista;
        ArrayList<Pelaaja> tyhjaPelaajalista = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream("pelaajatiedot.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            pelaajalista = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            return pelaajalista;

        } catch (IOException ioe) {
            return tyhjaPelaajalista;
        }  catch (ClassNotFoundException c) {
            return tyhjaPelaajalista;
        }

    }

    public static void poistaKayttaja(int indeksi) {
        ArrayList<Pelaaja> pelaajalista = haePelaajalista();
        pelaajalista.remove(indeksi);

        try {
            FileOutputStream fos = new FileOutputStream("pelaajatiedot.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pelaajalista);
            oos.close();
            fos.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println();
        }
    }

    public static void tulostaBanner1() {
        System.out.println("""    
                      
                      
                      
                             
                      ██╗ █████╗ ██╗   ██╗ █████╗ ██████╗  ██████╗ ██╗  ██╗███████╗██████╗ ██╗
                      ██║██╔══██╗██║   ██║██╔══██╗██╔══██╗██╔═══██╗██║ ██╔╝██╔════╝██╔══██╗██║
                      ██║███████║██║   ██║███████║██████╔╝██║   ██║█████╔╝ █████╗  ██████╔╝██║
                 ██   ██║██╔══██║╚██╗ ██╔╝██╔══██║██╔═══╝ ██║   ██║██╔═██╗ ██╔══╝  ██╔══██╗██║
                 ╚█████╔╝██║  ██║ ╚████╔╝ ██║  ██║██║     ╚██████╔╝██║  ██╗███████╗██║  ██║██║
                  ╚════╝ ╚═╝  ╚═╝  ╚═══╝  ╚═╝  ╚═╝╚═╝      ╚═════╝ ╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝╚═╝"""
        );
    }

    /*public static void tulostaBanner2() {
        System.out.println("""
                  
                  
                  
                  ____      _ _                           _       _  
                 |  _ \\ ___| (_)_ __ ___  _   _  ___   __| | ___ | |_
                 | |_) / _ \\ | | '_ ` _ \\| | | |/ _ \\ / _` |/ _ \\| __|
                 |  __/  __/ | | | | | | | |_| | (_) | (_| | (_) | |_
                 |_|   \\___|_|_|_| |_| |_|\\__,_|\\___/ \\__,_|\\___/ \\__|""");
    }

    public static void tulostaBanner3() {
        System.out.println("""      
                
                
                    
                __   __   _                 _           _\s
                \\ \\ / /__| |_ ___ _ __  ___| |_____ _ _(_)
                 \\ V / -_)  _/ _ \\ '_ \\/ _ \\ / / -_) '_| |
                  \\_/\\___|\\__\\___/ .__/\\___/_\\_\\___|_| |_|
                                 |_|""");
    }*/

    public static void alkuvalikko() {
        Scanner lukija = new Scanner(System.in);
        tulostaBanner1();
        Flag flag = new Flag(false);
        boolean uudestaan = true;
        while (uudestaan) {
            ArrayList<Pelaaja> pelaajalista = haePelaajalista();
            //System.out.println("--- JAVAPOKERI ---");
            System.out.println(
                            "(1)  Uusi käyttäjä" + "\n" +
                            "(2)  Lataa käyttäjä" + "\n" +
                            "(3)  Poista käyttäjä" + "\n" +
                            "(4)  Exit");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("U")) {
                    System.out.print("Käyttäjänimi: ");
                    String kayttajanimi = lukija.nextLine();
                    if (kayttajanimi.length() > 30) {
                        throw new CustomException("Liian pitkä käyttäjänimi! Maksimipituus on 30 merkkiä.");
                    }
                    if (pelaajalista.size() > 0) {
                        for (Pelaaja p : pelaajalista) {
                            if (p.getNimi().equals(kayttajanimi)) {
                                System.out.println("Virhe! Kyseinen käyttäjä on jo luotu.");
                                System.out.println("Haluatko ladata käyttäjän " + kayttajanimi + " pelin? ('K') Kyllä,  ('E') Ei");
                                System.out.print("Valitse syöttämällä kirjain: ");
                                String input2 = lukija.nextLine();
                                if (input2.equals("K")) {
                                    System.out.println();
                                    System.out.println("Tervetuloa takaisin " + p.getNimi() + "!");
                                    uudestaan = false;
                                    alkuvalikko2(p, flag);
                                    break;
                                } else if (input2.equals("E")) {
                                    throw new CustomException("Valitse jokin muu käyttäjänimi.");
                                } else {
                                    throw new CustomException("Kelvoton syöte!");
                                }
                            }
                            if (pelaajalista.indexOf(p) == pelaajalista.size()-1) {
                                uudestaan = false;
                                System.out.println();
                                System.out.println("Tervetuloa " + kayttajanimi + "!");
                                Pelaaja uusiPelaaja = new Pelaaja(kayttajanimi);
                                tallennus(uusiPelaaja);
                                alkuvalikko2(uusiPelaaja, flag);
                            }
                        }
                    } else {
                        uudestaan = false;
                        System.out.println();
                        System.out.println("Tervetuloa " + kayttajanimi + "!");
                        Pelaaja uusiPelaaja = new Pelaaja(kayttajanimi);
                        tallennus(uusiPelaaja);
                        alkuvalikko2(new Pelaaja(kayttajanimi), flag);
                    }

                } else if (input.equals("L")) {
                    int i = 1;
                    System.out.println();
                    if (pelaajalista.size() > 0) {
                        System.out.println("Tallennetut käyttäjät:");
                        for (Pelaaja p : pelaajalista) {
                            System.out.println("(" + i + ") " + p.getNimi());
                            i++;
                        }
                        System.out.print("Valitse ladattava käyttäjä syöttämällä numero: ");
                        int input2 = Integer.valueOf(lukija.nextLine());
                        if (input2 > pelaajalista.size() || input2 < 1) {
                            throw new CustomException("Kelvoton syöte!");
                        }
                        uudestaan = false;
                        Pelaaja valittuPelaaja = pelaajalista.get(input2 - 1);
                        System.out.println();
                        System.out.println("Tervetuloa takaisin " + valittuPelaaja.getNimi() + "!");
                        alkuvalikko2(valittuPelaaja, flag);
                    } else {
                        throw new CustomException("Ei yhtäkään tallennettua käyttäjää.");
                    }

                } else if (input.equals("E")) {
                    System.out.println();
                    System.out.println("Hei Hei! Kiitos kun pelasit!");
                    uudestaan = false;

                } else if (input.equals("P")) {
                    int i = 1;
                    System.out.println();
                    if (pelaajalista.size() > 0) {
                        System.out.println("Tallennetut käyttäjät:");
                        for (Pelaaja p : pelaajalista) {
                            System.out.println("(" + i + ") " + p.getNimi());
                            i++;
                        }
                        System.out.print("Valitse poistettava käyttäjä syöttämällä numero: ");
                        int input3 = Integer.valueOf(lukija.nextLine());
                        if (input3 > pelaajalista.size() || input3 < 1) {
                            throw new CustomException("Kelvoton syöte!");
                        }
                        poistaKayttaja(input3 - 1);
                        System.out.println("Poistettu.");
                        System.out.println();
                    } else {
                        throw new CustomException("Ei yhtäkään tallennettua käyttäjää.");
                    }

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e){
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e){
                System.out.println("Virhe! Kelvoton syöte!\n" + "Syötä pelkkä käyttäjän numero.");
                System.out.println();
            }
        }
    }

    public static void alkuvalikko2(Pelaaja pelaaja, Flag flag) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            if (flag.getArvo()) {
                System.out.println("Pelaaja: " + pelaaja.getNimi());
            }
            System.out.println(
                            "Saldo: " + pelaaja.getSaldo() + "\n" +
                            "('P')  Pelimuodot" + "\n" +
                            "('S')  Saavutukset ja tilastot" + "\n" +
                            "('O')  Ohjeet" + "\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P")) {
                    uudestaan = false;
                    System.out.println();
                    valitsePelimuoto(pelaaja);
                } else if (input.equals("S")) {
                    tulostaSaavutukset(pelaaja);
                } else if (input.equals("O")) {
                    new Ohjeet();
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    alkuvalikko();
                } else if (input.equals("0452301216")) {
                    System.out.print("Talletettavien kolikoiden määrä: ");
                    int maara = Integer.valueOf(lukija.nextLine());
                    if (maara >= 0) {
                        pelaaja.setSaldo(maara + pelaaja.getSaldo());
                        System.out.println("Uusi saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        System.out.println();
                    } else {
                        throw new CustomException("Negatiivinen talletus!");
                    }
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static void valitsePelimuoto(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        //tulostaBanner2();
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                            "--- PELIMUODOT ---" + "\n" +
                            "('V')  Vetopokeri" + "\n" +
                            "('?')  ?????" + "\n" +
                            "('O')  Ohjeet" + "\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);
                } else if (input.equals("?")) {
                    throw new CustomException("Tämä pelimuoto on vielä työn alla :)");
                } else if (input.equals("O")) {
                    new Ohjeet();
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    Flag flag = new Flag(true);
                    alkuvalikko2(pelaaja, flag);
                } else if (input.equals("0452301216")) {
                    System.out.print("Talletettavien kolikoiden määrä: ");
                    int maara = Integer.valueOf(lukija.nextLine());
                    if (maara >= 0) {
                        pelaaja.setSaldo(maara + pelaaja.getSaldo());
                        System.out.println("Uusi saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        System.out.println();
                    } else {
                        throw new CustomException("Negatiivinen talletus!");
                    }
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static void vetopokeriValinta(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        //tulostaBanner3();
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                    "--- VETOPOKERI ---" + "\n" +
                            "('V')  Vapaapeli" + "\n" +
                            "('K')  Kilpapeli" + "\n" +
                            "('S')  Kauppa" + "\n" +
                            "('O')  Ohjeet" + "\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriVapaapeli(pelaaja);
                } else if (input.equals("K")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriKilpapeli(pelaaja);
                } else if (input.equals("S")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriKauppa(pelaaja);
                } else if (input.equals("T")) {
                    uudestaan = false;
                    System.out.println();
                    valitsePelimuoto(pelaaja);
                } else if (input.equals("O")) {
                    new Ohjeet();
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static void vetopokeriKauppa(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println();
            System.out.println(
                    "--- VETOPOKERI KAUPPA ---\n" +
                            "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n");
            parannukset(pelaaja);
            //System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P")) {
                    uusiVapaaPeli(pelaaja);
                } else if (input.equals("T")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
                System.out.println();
            }
        }
    }

    public static void parannukset(Pelaaja pelaaja) {
        int[] parannustenHinnat = {200, 1000, 2000, 15000, 50000};
        String[] parannustenTilat = new String[5];
        Boolean[] vetopokeriParannustenTilat = pelaaja.getVetopokeriParannustenTilat();
        boolean uudestaan = true;

        outerloop:
        while (uudestaan) {
            for (int i=0; i<5; i++) {
                if (vetopokeriParannustenTilat[i]) {
                    parannustenTilat[i] = "Käytössä";
                } else {
                    parannustenTilat[i] = "Hinta: " + String.valueOf(parannustenHinnat[i]) + " kolikkoa";
                }
            }

            System.out.println("Ostettavat parannukset:\n" +
                    "('1') Lisää pakkaan 1. JOKERI     " + parannustenTilat[0] + "\n" +
                    "('2') Lisää pakkaan 2. JOKERI     " + parannustenTilat[1] + "\n" +
                    "('3') Lisää pakkaan 3. JOKERI     " + parannustenTilat[2] + "\n" +
                    "('4') Lisää pakkaan 4. JOKERI     " + parannustenTilat[3] + "\n" +
                    "('5') Lisää pakkaan 5. JOKERI     " + parannustenTilat[4] + "\n" +
                    "('T') Takaisin");
            System.out.print("Valitse ostettava parannus: ");
            Scanner lukija = new Scanner(System.in);
            String input = lukija.nextLine();

            try {
                for (int i=0; i<5; i++) {
                    if (input.equals(String.valueOf(i+1))) {
                        if (vetopokeriParannustenTilat[i]) {
                            throw new CustomException("Kyseinen parannus on jo ostettu.");

                        } else if (i > 0 && !vetopokeriParannustenTilat[i] && !vetopokeriParannustenTilat[i-1]){
                            throw new CustomException("Parannukset on osetettava järjestyksessä.");

                        } else {
                            //uudestaan = false;
                            parannuksenOsto(pelaaja, parannustenHinnat[i], i);
                            //Hypätään pois kesken jääneestä while loopista
                            break outerloop;
                        }
                    }
                }

                if (input.equals("T")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }

            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            }
        }


    }

    public static void parannuksenOsto(Pelaaja pelaaja, int hinta, int parannuksenIndeksi) {

        System.out.println("Haluatko ostaa parannuksen " + hinta + " kolikolla? ('K') Kyllä, ('E') Ei");
        System.out.print("Valitse syöttämällä kirjain: ");
        Scanner lukija = new Scanner(System.in);
        String input = lukija.nextLine();

        boolean uudestaan = true;
        while (uudestaan) {
            try {
                if (input.equals("K")) {
                    if (pelaaja.getSaldo() < hinta) {
                        uudestaan = false;
                        throw new CustomException("Ei riittävästi kolikoita!" + "\n" +
                                "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa");
                    }
                    Boolean[] parannukset = pelaaja.getVetopokeriParannustenTilat();
                    parannukset[parannuksenIndeksi] = true;
                    pelaaja.setVetopokeriParannuksenTilat(parannukset);
                    pelaaja.setSaldo(pelaaja.getSaldo()-hinta);
                    System.out.println("Parannus ostettu. Saldo: " + pelaaja.getSaldo());
                    System.out.println();
                    uudestaan = false;
                    parannukset(pelaaja);

                } else if (input.equals("E")) {
                    uudestaan = false;
                    System.out.println();
                    parannukset(pelaaja);
                    break;
                }

            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
                parannukset(pelaaja);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println();
            }

        }

    }

    public static void vetopokeriVapaapeli(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            tallennus(pelaaja);
            System.out.println(
                            "VETOPOKERI: Vapaapeli\n" +
                            "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n" +
                            "('P')  Pelaa\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P")) {
                    uusiVapaaPeli(pelaaja);
                } else if (input.equals("T")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);

                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
                System.out.println();
            }
        }
    }

    public static void uusiVapaaPeli(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        int jokerienMaara = Collections.frequency(List.of(pelaaja.getVetopokeriParannustenTilat()), true);
        Korttipakka pakka = new Korttipakka(jokerienMaara);
        boolean uudestaan = true;
        while (uudestaan) {
            try {
                if (pelaaja.getSaldo() == 0) {
                    pelaaja.setPanos(0);
                    pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                    pelaaja.tulostaKasi(false);
                    korttienVaihto(pelaaja, pakka, "vapaapeli");
                    pelaaja.kadenTarkistus("vapaapeli");
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
                    System.out.println("----------------------------------");
                    System.out.println();
                    uudestaan = false;
                } else {
                    System.out.print("Aseta alkupanos: ");
                    int panos = Integer.valueOf(lukija.nextLine());
                    if (panos > pelaaja.getSaldo()) {
                        throw new CustomException("Liian suuri panos!" + "\n" +
                                "Pelitilin saldo: " + pelaaja.getSaldo());
                    } else if (panos < 0) {
                        throw new CustomException("Negatiivinen panos!");
                    } else if (panos > 500) {
                        throw new CustomException("Liian suuri panos! Vapaapelissä suurin hyväksytty alkupanos on 500 kolikkoa.");
                    } else if (panos >= 0 && panos <= pelaaja.getSaldo() && panos <= 500) {
                        pelaaja.setPanos(panos);
                        pelaaja.setSaldo(pelaaja.getSaldo() - panos);
                        pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                        pelaaja.tulostaKasi(false);
                        korttienVaihto(pelaaja, pakka, "vapaapeli");
                        pelaaja.kadenTarkistus("vapaapeli");
                        pelaaja.tarkistaSaavutukset();
                        System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        System.out.println("----------------------------------");
                        System.out.println();
                        uudestaan = false;
                    }
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton panos!");
                System.out.println();
            }
        }
    }

    public static void korttienVaihto(Pelaaja pelaaja, Korttipakka pakka, String pelimuoto) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println("('V')  Vaihda kortteja" + "\n" + "('P')  Pidä kortit");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    System.out.print("Valitse kortit, jotka haluat vaihtaa: ");
                    String[] vaihdettavat = lukija.nextLine().split(",");
                    pelaaja.vaihdaKortteja(vaihdettavat, pakka);
                    if (pelaaja.getPanos() != 0) {
                        if (pelimuoto.equals("vapaapeli") && pelaaja.getSaldo() > 0) {
                            lisapanos(pelaaja, pelimuoto);
                        }
                        if (pelimuoto.equals("kilpapeli") && pelaaja.getKilpapelinSaldo() > 0) {
                            lisapanos(pelaaja, pelimuoto);
                        }
                    }
                    pelaaja.tulostaKasi(true);
                    uudestaan = false;
                } else if (input.equals("P")) {
                    if (pelaaja.getPanos() != 0) {
                        if (pelimuoto.equals("vapaapeli") && pelaaja.getSaldo() > 0) {
                            lisapanos(pelaaja, pelimuoto);
                        }
                        if (pelimuoto.equals("kilpapeli") && pelaaja.getKilpapelinSaldo() > 0) {
                            lisapanos(pelaaja, pelimuoto);
                        }
                    }
                    uudestaan = false;
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e)	{
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!\n" + "Syötä vaihdettavien korttien indeksit pilkuilla erotettuina. Esim. '2,4,5'");
                System.out.println();
            }
        }
    }

    public static void lisapanos(Pelaaja pelaaja, String pelimuoto) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println("Haluatko korottaa panosta? ('K') Kyllä, ('E') Ei");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("K")) {
                    int maksimikorotus = pelaaja.getPanos()/2;
                    System.out.println("Suurin mahdollinen panoksen korotus: " + maksimikorotus + " kolikkoa");
                    System.out.print("Valitse korotuksen suuruus: ");
                    int korotus = Integer.valueOf(lukija.nextLine());
                    if (korotus >= 0 && korotus <= maksimikorotus && pelimuoto.equals("vapaapeli")) {
                        if (korotus > pelaaja.getSaldo()) {
                            throw new CustomException("Jäljellä oleva saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        }
                        pelaaja.setPanos(korotus + pelaaja.getPanos());
                        System.out.println("Uusi kokonaispanos: " + pelaaja.getPanos() + " kolikkoa");
                        pelaaja.setSaldo(pelaaja.getSaldo() - korotus);
                        System.out.println();
                        uudestaan = false;
                    } else if (korotus >= 0 && korotus <= maksimikorotus && pelimuoto.equals("kilpapeli")) {
                        if (korotus > pelaaja.getKilpapelinSaldo()) {
                            throw new CustomException("Jäljellä oleva kilpapelin saldo: " + pelaaja.getKilpapelinSaldo() + " kolikkoa");
                        }
                        pelaaja.setPanos(korotus + pelaaja.getPanos());
                        System.out.println("Uusi kokonaispanos: " + pelaaja.getPanos() + " kolikkoa");
                        pelaaja.setKilpapelinSaldo(pelaaja.getKilpapelinSaldo() - korotus);
                        uudestaan = false;
                    } else {
                        throw new CustomException("Kelvoton syöte!");
                    }
                } else if (input.equals("E")) {
                    uudestaan = false;
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton syöte!");
                System.out.println();
            }
        }
    }

    public static void vetopokeriKilpapeli(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        int kilpapelinHinta = 100;
        boolean uudestaan = true;
        while (uudestaan) {
            tallennus(pelaaja);
            System.out.println(
                    "VETOPOKERI: Kilpapeli" + "\n" +
                            "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n" +
                            "('P')  Pelaa" + "\n" +
                            //"('N')  Näytä saldo" + "\n" +
                            "('O')  Omat tulokset" + "\n" +
                            "('H')  Hall of Fame" + "\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P") && pelaaja.getSaldo() >= kilpapelinHinta) {
                    System.out.println("Uuden kilpapelin aloittaminen maksaa 100 kolikkoa. \n" +
                                    "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa" + "\n" +
                            "Haluatko aloittaa uuden kilpapelin? ('K') Kyllä, ('E') Ei");
                    System.out.print("Valitse syöttämällä kirjain: ");
                    String input2 = lukija.nextLine();
                    if (input2.equals("K")) {
                        pelaaja.setSaldo(pelaaja.getSaldo() - kilpapelinHinta);
                        uudestaan = false;
                        System.out.println();
                        uusiKilpapeli(pelaaja, kilpapelinHinta);
                        break;
                    } else if (input2.equals("E")) {
                        break;
                    } else {
                        throw new CustomException("Kelvoton syöte!");
                    }
                } else if (input.equals("P") && pelaaja.getSaldo() < kilpapelinHinta) {
                    throw new CustomException("Ei riittävästi kolikoita!" + "\n" +
                            "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa");
                } else if (input.equals("O")) {
                    System.out.println();
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " kilpapelin TOP-10 tulokset:");
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
                } else if (input.equals("H")) {
                    tulostaHallOfFame();
                } else if (input.equals("T")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (Exception e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            }
        }
    }

    public static void uusiKilpapeli(Pelaaja pelaaja, int kilpapelinHinta) {
        Scanner lukija = new Scanner(System.in);
        pelaaja.setKilpapelinSaldo(kilpapelinHinta);
        int minimipanos = 10;
        int jakojenMaara = 5;
        tallennus(pelaaja);
        System.out.println("Uusi kilpapeli alkaa.");
        System.out.println("Kilpapelin saldo: " + pelaaja.getKilpapelinSaldo());
        int nykyinenJako = 1;
        while (nykyinenJako <= jakojenMaara) {
            System.out.println("--- " + nykyinenJako + ". JAKO ---");
            System.out.print("Aseta alkupanos: ");
            try {
                int panos = Integer.valueOf(lukija.nextLine());
                if (panos > pelaaja.getKilpapelinSaldo()) {
                    throw new CustomException("Liian suuri panos!" + "\n" +
                            "Kilpapelin saldo: " + pelaaja.getKilpapelinSaldo());
                } else if (panos < minimipanos) {
                    throw new CustomException("Liian pieni panos! Kilpapelissä minimipanos on 10 kolikkoa.");
                } else if (panos >= minimipanos && panos <= pelaaja.getKilpapelinSaldo()){
                    pelaaja.setPanos(panos);
                    int jokerienMaara = Collections.frequency(List.of(pelaaja.getVetopokeriParannustenTilat()), true);
                    Korttipakka pakka = new Korttipakka(jokerienMaara);
                    pelaaja.setKilpapelinSaldo(pelaaja.getKilpapelinSaldo() - panos);
                    pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                    pelaaja.tulostaKasi(false);
                    korttienVaihto(pelaaja, pakka, "kilpapeli");
                    pelaaja.kadenTarkistus("kilpapeli");
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " kilpapelin saldo: " + pelaaja.getKilpapelinSaldo() + " kolikkoa");
                    pelaaja.tarkistaSaavutukset();
                    if (pelaaja.getKilpapelinSaldo() < minimipanos && nykyinenJako < jakojenMaara) {
                        System.out.println("Hävisit kilpapelin, sillä saldo tippui alle vaaditun minimipanoksen.");
                        System.out.println("Parempi onni ensi kerralla!");
                        System.out.println("----------------------------------");
                        System.out.println();
                        System.out.println();
                        nykyinenJako = jakojenMaara + 1;
                        vetopokeriKilpapeli(pelaaja);
                    }
                    System.out.println("----------------------------------");
                    System.out.println();
                    System.out.println();
                    if (nykyinenJako == jakojenMaara) {
                        System.out.println("Kilpapeli on päättynyt.");
                        System.out.println("Pelaajan " + pelaaja.getNimi() + " lopulliset pisteet: " + pelaaja.getKilpapelinSaldo());
                        System.out.println();
                        pelaaja.tarkistaKilpapelinSaavutukset(pelaaja.getKilpapelinSaldo());
                        int tulos = pelaaja.getKilpapelinSaldo();
                        pelaaja.lisaaKilpapelinTulos(tulos);
                        vetopokeriKilpapeli(pelaaja);
                    }
                    nykyinenJako++;
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
                System.out.println();
            } catch (NumberFormatException e) {
                System.out.println("Virhe! Kelvoton panos!");
                System.out.println();
            }
        }
    }

    public static void tulostaHallOfFame() {
        ArrayList<Pelaaja> pelaajalista = haePelaajalista();
        ArrayList<Kilpapelitulos> halloffameTulokset = new ArrayList<>();
        for (Pelaaja p: pelaajalista) {
            for (int kilpapelinTulos: p.getKilpapelinTulokset()) {
                halloffameTulokset.add(new Kilpapelitulos(p.getNimi(), kilpapelinTulos));
            }
        }
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
        Collections.sort(halloffameTulokset, Collections.reverseOrder());
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

    public static void tulostaVali(int nimenPituus, int pisinNimi) {
        for (int i = nimenPituus; i <= pisinNimi + 3; i++)
        System.out.print(".");
    }

    public static void tulostaSaavutukset(Pelaaja pelaaja) {
        System.out.println();
        String[] saavutukset = new Saavutukset().getJasenet();
        Boolean[] saavutustenTilat = pelaaja.getSaavutustenTilat();
        System.out.println("Pelaaja: " + pelaaja.getNimi());
        System.out.println("--- SAAVUTUKSET ---");
        for (int i = 0; i < 30; i++) {
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
        System.out.println();
        System.out.println("--- TILASTOT ---");
        System.out.println("Voitot yhteensä: " + pelaaja.getYhteisvoitot() + " kolikkoa");
        System.out.println("Häviöt yhteensä: " + pelaaja.getYhteishaviot() + " kolikkoa");
        double palautusprosentti = 0.0;
        if (pelaaja.getYhteishaviot() > 0) {
            palautusprosentti = (double) pelaaja.getYhteisvoitot()/pelaaja.getYhteishaviot() * 100;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        System.out.println("Palautusprosentti: " + df.format(palautusprosentti) + "%");
        System.out.println("Voitetut jaot: " + pelaaja.getVoitetutJaot());
        System.out.println("Hävityt jaot: " + pelaaja.getHavitytJaot());
        System.out.println();
        System.out.println();
    }


}
