package JavaPokeri;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Kayttoliittyma {


    /** branch master**/

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
    // -palautusprosentin pyöristys


    //pelaajalistassa ei ehkä tarvitse tallentaa kättä
    //lisää kommentointi tarvittaviin metodeihin
    //korjaa metodien näkyvyydet
    //lisää mahdollisuus kahden tai useamman pelaajan pelimuotoon, jossa korotetaan panosta ja pelataan muita vastaan
    //-pikapokeri pelimuoto
    // moikka tämä on verio 1.5.2


    //-vapaapelissä täytyy kerätä kolikoita jotta voi mennä kilpapeliin. Kilpapeli maksaa 200 kolikkoa
    //-vapaapelissä kolikkoja saa vähintään käden kertoimen verran jos panos on 0, alussa saldo aina nolla
    //-kilpapelin muoto joka kalliimpi ja jossa paljon jokereita
    //-kerätään dataa esim. jokaisen pelaajan voitoista, voittokäsien arvoista, yhteisvoitoista jne.
    //-tämä on master branch


    /** Tallennetaan pelaajan tiedot JSON-tiedostoon, jotta peliä voidaan jatkaa
        siitä mihin jäätiin. */
    public static void tallennus(Pelaaja pelaaja) {
        // Haetaan tallennustiedostostaa pelaajien tiedot pelaajalistaan, jotta tietoja on helppo käsitellä.
        ArrayList<Pelaaja> pelaajalista = haePelaajalista();
        ArrayList<Pelaaja> pelaajalistaKopio = haePelaajalista();
        // Päivitetään olemassaolevan pelaajan tiedot tai lisätään uusi pelaaja. Käytetään apuna listaa
        // pelaajalistaKopio, jotta vältytään ConcurrentModificationExceptionilta.
        if (pelaajalistaKopio.size() > 0) {
            for (Pelaaja p : pelaajalistaKopio) {
                // Päivitetään pelaajan tiedot jos listasta löytyy sama nimi.
                if (p.getNimi().equals(pelaaja.getNimi())) {
                    pelaajalista.set(pelaajalistaKopio.indexOf(p), pelaaja);
                    break;
                }
                // Jos saavutetaan pelaajalistan loppu, lisätään listaan uusi pelaaja.
                if (pelaajalistaKopio.indexOf(p) == pelaajalistaKopio.size()-1) {
                    pelaajalista.add(pelaaja);
                }
            }
            // Lisätään tyhjään listaan ensimmäinen pelaaja
        } else {
            pelaajalista.add(pelaaja);
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


    /** Metodi hakee ja palauttaa tallennustiedoston sisällön tai tyhjän listan jos pelaajia ei vielä ole. */
    public static ArrayList<Pelaaja> haePelaajalista() {
        ArrayList<Pelaaja> pelaajalista = new ArrayList<>();
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

    public static void alkuvalikko() {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            ArrayList<Pelaaja> pelaajalista = haePelaajalista();
            System.out.println("--- JAVAPOKERI ---");
            System.out.println(
                            "('U')  Uusi käyttäjä" + "\n" +
                            "('L')  Lataa käyttäjä" + "\n" +
                            "('P')  Poista käyttäjä" + "\n" +
                            "('E')  Exit");
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
                                    alkuvalikko2(p);
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
                                alkuvalikko2(uusiPelaaja);
                            }
                        }
                    } else {
                        uudestaan = false;
                        System.out.println();
                        System.out.println("Tervetuloa " + kayttajanimi + "!");
                        Pelaaja uusiPelaaja = new Pelaaja(kayttajanimi);
                        tallennus(uusiPelaaja);
                        alkuvalikko2(new Pelaaja(kayttajanimi));
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
                        alkuvalikko2(valittuPelaaja);
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

    public static void alkuvalikko2(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                            "('P')  Pelimuodot" + "\n" +
                            "('S')  Saavutukset" + "\n" +
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
                    tulostaOhjeet();
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
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                            "PELIMUODOT:" + "\n" +
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
                    tulostaOhjeet();
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    alkuvalikko2(pelaaja);
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
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                    "VETOPOKERI:" + "\n" +
                            "('V')  Vapaapeli" + "\n" +
                            "('K')  Kilpapeli" + "\n" +
                            "('O')  Ohjeet" + "\n" +
                            "('T')  Takaisin");
            System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    System.out.println();
                    uudestaan = false;
                    vetopokeriVapaapeli(pelaaja);
                } else if (input.equals("K")) {
                    System.out.println();
                    uudestaan = false;
                    vetopokeriKilpapeli(pelaaja);
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    valitsePelimuoto(pelaaja);
                } else if (input.equals("O")) {
                    tulostaOhjeet();
                } else {
                    throw new CustomException("Kelvoton syöte!");
                }
            } catch (CustomException e) {
                System.out.println("Virhe! " + e.getMessage());
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
        Korttipakka pakka = new Korttipakka();
        boolean uudestaan = true;
        while (uudestaan) {
            try {
                if (pelaaja.getSaldo() == 0) {
                    pelaaja.setPanos(0);
                    pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                    pelaaja.tulostaKasi(1);
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
                        throw new CustomException("Kelvoton panos!");
                    } else if (panos >= 0 && panos <= pelaaja.getSaldo()) {
                        pelaaja.setPanos(panos);
                        pelaaja.setSaldo(pelaaja.getSaldo() - panos);
                        pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                        pelaaja.tulostaKasi(1);
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
                    pelaaja.tulostaKasi(2);
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
                            "('P')  Pelaa (100 kolikkoa)" + "\n" +
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
                } /*else if (input.equals("N")) {
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa");
                    System.out.println();
                }*/ else if (input.equals("O")) {
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
        int jako = 1;
        while (jako <= jakojenMaara) {
            System.out.println("--- " + jako + ". JAKO ---");
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
                    Korttipakka pakka = new Korttipakka();
                    pelaaja.setKilpapelinSaldo(pelaaja.getKilpapelinSaldo() - panos);
                    pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                    pelaaja.tulostaKasi(1);
                    korttienVaihto(pelaaja, pakka, "kilpapeli");
                    pelaaja.kadenTarkistus("kilpapeli");
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " kilpapelin saldo: " + pelaaja.getKilpapelinSaldo() + " kolikkoa");
                    pelaaja.tarkistaSaavutukset();
                    if (pelaaja.getKilpapelinSaldo() < minimipanos) {
                        System.out.println("Hävisit kilpapelin, sillä saldo tippui alle vaaditun minimipanoksen.");
                        System.out.println("Parempi onni ensi kerralla!");
                        System.out.println("----------------------------------");
                        System.out.println();
                        System.out.println();
                        jako = jakojenMaara + 1;
                        vetopokeriKilpapeli(pelaaja);
                    }
                    System.out.println("----------------------------------");
                    System.out.println();
                    System.out.println();
                    if (jako == jakojenMaara) {
                        System.out.println("Kilpapeli on päättynyt.");
                        System.out.println("Pelaajan " + pelaaja.getNimi() + " lopulliset pisteet: " + pelaaja.getKilpapelinSaldo());
                        System.out.println();
                        pelaaja.tarkistaKilpapelinSaavutukset(pelaaja.getKilpapelinSaldo());
                        int tulos = pelaaja.getKilpapelinSaldo();
                        pelaaja.lisaaKilpapelinTulos(tulos);
                        vetopokeriKilpapeli(pelaaja);
                    }
                    jako++;
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

    public static void tulostaOhjeet() {
        System.out.println();
        System.out.println("---JAVAPOKERI OHJEET---\n\n" +
                "VETOPOKERI:\n" +
                        "   Vetopokerissa pelaaja asettaa ensin alkupanoksen, jonka jälkeen hänelle jaetaan korttipakasta\n" +
                        "   viisi sattumanvaraista korttia. Korttipakassa on kaikki 52 korttia ja yksi JOKERI. Ensimmäisen jaon \n" +
                        "   jälkeen pelaajalla on mahdollisuus vaihtaa mitkä tahansa saamistaan viidestä kortista yhden kerran, \n" +
                        "   jolloin niiden tilalle tulee samasta pakasta uudet sattumanvaraiset kortit. Ennen uuden käden \n" +
                        "   paljastusta pelaaja saa vielä korottaa panostaan. Korotuksen maksimimäärä on puolet alkupanoksesta. \n" +
                        "   Tavoitteena on saada aikaan mahdollisimman hyvä pokerikäsi. Voitot jaetaan käden arvon ja panoksen \n" +
                        "   suuruuden mukaan.\n\n" +
                "POKERIKÄSIEN ARVOJÄRJESTYS:\n" +
                        "   Kuningasvärisuora  250 * panos\n" +
                        "   Viitoset           250 * panos\n" +
                        "   Värisuora           75 * panos\n" +
                        "   Neloset             50 * panos\n" +
                        "   Täyskäsi            20 * panos\n" +
                        "   Väri                15 * panos\n" +
                        "   Suora               10 * panos\n" +
                        "   Kolmoset             5 * panos\n" +
                        "   Kaksi paria          3 * panos\n" +
                        "   10-A pari            2 * panos\n" + "\n" +
                "VETOPOKERI: Vapaapeli\n" +
                        "   Vapaapelissä pelaajan tavoitteena on kasvattaa oman tilin saldoa. Uusien käsien jakoa ei ole rajoitettu,\n" +
                        "   joten pelata voi niin monta kertaa kuin haluaa. Vapaapeliä voi pelata myös ilman panosta (alkupanos 0), \n" +
                        "   mutta silloin voiton suuruus on vain puolet samanarvoisen pokerikäden voittokertoimesta. Esimerkiksi \n" +
                        "   täyskäsi maksaisi siis panoksen kanssa 20*panos kolikkoa, mutta ilman panosta vain 10 kolikkoa. Aloittaessaan  \n" +
                        "   vapaapelin ensimmäistä kertaa, uuden pelaajan saldo on 10 kolikkoa. Keräämällä 100 kolikkoa pelaaja pääsee  \n" +
                        "   kokeilemaan onneaan kilpapeliin.\n\n" +
                "VETOPOKERI: Kilpapeli\n" +
                        "   Kilpapelissä pelaaja aloittaa 100 kolikkolla ja uusia käsiä jaetaan vain 5 kappaletta. Jokainen uusi \n" +
                        "   jako suoritetaan täydestä 53 kortin korttipakasta. Pelimuodossa pyritään kasvattamaan kolikoiden määrä\n" +
                        "   mahdollisimman suureksi näiden 10 jaon aikana. Kilpapelissä jokaisen jaon minimipanos on 10 kolikkoa.\n" +
                        "   Pelaaja häviää, jos saldo tippuu alle tarvittavan minimipanoksen. Uuden kilpapelin aloittaminen maksaa\n" +
                        "   pelaajalle aina 100 kolikkoa. Kolikoita voi kerätä lisää vetopokerin vapaapelistä.\n");
        System.out.println();

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
        ArrayList<String> saavutukset = pelaaja.getSaavutukset();
        System.out.println("Pelaaja: " + pelaaja.getNimi());
        System.out.println("--- SAAVUTUKSET ---");
        for (int i = 0; i < 30; i++) {
            if (!(saavutukset.get(i).equals("?"))) {
                String saavutus = saavutukset.get(i);
                int saavutuksenMerkkimaara = saavutus.length();
                System.out.print(saavutukset.get(i));
                for (int j = saavutuksenMerkkimaara; j < 23; j++) {
                    System.out.print(".");
                }
                saavutuksenKuvaus(String.valueOf(i));
            } else {
                System.out.println("?????");
            }
        }
        System.out.println();
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

    public static void saavutuksenKuvaus(String i) {
        switch (i) {
            case "0": {System.out.println("Päihitä Nahka-Lasse vetopokerin kilpapelissä");break;}
            case "1": {System.out.println("Päihitä NPC-Janne vetopokerin kilpapelissä");break;}
            case "2": {System.out.println("Päihitä Anil vetopokerin kilpapelissä");break;}
            case "3": {System.out.println("Päihitä Erno vetopokerin kilpapelissä");break;}
            case "4": {System.out.println("Päihitä Jytäpojat vetopokerin kilpapelissä");break;}
            case "5": {System.out.println("Päihitä Srinivasa vetopokerin kilpapelissä");break;}
            case "6": {System.out.println("Päihitä Levrai vetopokerin kilpapelissä");break;}
            case "7": {System.out.println("Päihitä Jari-Matti vetopokerin kilpapelissä");break;}
            case "8": {System.out.println("Päihitä David Patterson vetopokerin kilpapelissä");break;}
            case "9": {System.out.println("Päihitä Jean Baptiste Joseph Fourier vetopokerin kilpapelissä");break;}
            case "10": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on kuningasvärisuora");break;}
            case "11": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on viitoset");break;}
            case "12": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on värisuora");break;}
            case "13": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on neloset");break;}
            case "14": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on täyskäsi");break;}
            case "15": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on väri");break;}
            case "16": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on suora");break;}
            case "17": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on kolmoset");break;}
            case "18": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on kaksi paria");break;}
            case "19": {System.out.println("Voita missä tahansa pelimuodosa kädellä jonka arvo on 10-A pari");break;}
            case "20": {System.out.println("Voita yhdellä pokerikädellä 100000 kolikkoa");break;}
            case "21": {System.out.println("Voita yhdellä pokerikädellä 50000 kolikkoa");break;}
            case "22": {System.out.println("Voita yhdellä pokerikädellä 10000 kolikkoa");break;}
            case "23": {System.out.println("Voita yhdellä pokerikädellä 5000 kolikkoa");break;}
            case "24": {System.out.println("Voita yhdellä pokerikädellä 1000 kolikkoa");break;}
            case "25": {System.out.println("Voita yhdellä pokerikädellä 750 kolikkoa");break;}
            case "26": {System.out.println("Voita yhdellä pokerikädellä 500 kolikkoa");break;}
            case "27": {System.out.println("Voita yhdellä pokerikädellä 200 kolikkoa");break;}
            case "28": {System.out.println("Voita yhdellä pokerikädellä 100 kolikkoa");break;}
            case "29": {System.out.println("Voita yhdellä pokerikädellä 20 kolikkoa");break;}
        }
    }

}
