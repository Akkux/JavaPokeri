package JavaPokeri;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Kayttoliittyma {


    //Tallennusmahdollisuus muuttaa hieman pelin ideaa:
    //Anna valmis aloituspanos jolloin lisää tallettaminen ei mahdollista
    //Pistelista, jossa näkyy suurimmat saldot
    //Joko loputtomasti jakoja tai esim. vain 10 jakoa (jolloin tavoitteena saada niiden aikana mahdollisimman suuri saldo)
    //Ehkä maksimipanos ja muut valmiit panosmäärät

    //TODO:
    // -käyttäjän vaihto
    // -alkuun pelin lataamiseen lista tallennetuista käyttäjistä
    // -(käyttäjän poisto)
    // -korjaa ässän sisältävien käsien tarkistus
    // -lisää pakkaan jokeri ja korjaa tarkistukset sen mukaan
    // -testaa viitoset, koska jokeri
    // -kortin enum-luokka voisi olla parempi, ja kuvakorteilla voisi olla symbolit
    //lisää voittotaulukko
    //mahdollista panoksen korotus ensimmäisen käden jälkeen (max 1/2 alkup.panos)
    //pelaajalistassa ei ehkä tarvitse tallentaa kättä
    //scoreboard, jakojen määrä, häviäminen kun jaot loppuu, uuden pelin aloitus samalla käyttäjällä
    //jopa mahdollista valita eri pelimuotoja, jolloin kaikille oma scoreboard
    //Alkuun valikko, mistä valita itse peli (vetopokeri tai pikapokeri)
    //sitten pelin valinnan jälkeen vielä pelimuoto (vapaa peli / kilpapeli)
    //lisää kommentointi tarvittaviin metodeihin


    /** Tallennetaan pelaajan tiedot JSON-tiedostoon, jotta peliä voidaan jatkaa
        siitä mihin jäätiin. */
    public static void tallennus(Pelaaja pelaaja) {
        // Haetaan JSON-tiedostosta pelaajien tiedot pelaajalistaan, jotta tietoja on helppo käsitellä.
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

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("pelaajalista.json"), pelaajalista );
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Kakka1");
        }

        // Kirjoitetaan muutokset JSON-tiedostoon
        /*Gson gson = new Gson();
        try {
            FileWriter fw = new FileWriter("./pelaajalista.json");

            String json = gson.toJson(pelaajalista);
            fw.write(json);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println();
        }*/
    }

    /*public static void tallennus2() {
        Pelaaja p1 = new Pelaaja("Jaakko");
        Pelaaja p2 = new Pelaaja("Oliver");
        ArrayList<Pelaaja> pelaajalista = new ArrayList<Pelaaja>(List.of(p1, p2));

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            // Writing to a file
            //String arrayToJson = mapper.writeValueAsString(pelaajalista);
            mapper.writeValue(new File("pelaajalista.json"), pelaajalista );

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            // Reading from a file
            TypeReference<ArrayList<Pelaaja>> pelaajaLista = new TypeReference<ArrayList<Pelaaja>>() {};
            ArrayList<Pelaaja> jsonToPelaajaList = mapper.readValue(new File("pelaajalista.json"), pelaajaLista);
            jsonToPelaajaList.forEach(System.out::println);
            System.out.println(jsonToPelaajaList.get(0).getNimi());

        } catch (Exception e) {
            System.out.println(e);
        }

    }*/

    /** Metodi hakee ja palauttaa JSON-tiedoston sisällön tai tyhjän listan jos pelaajia ei vielä ole. */
    public static ArrayList<Pelaaja> haePelaajalista() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            ArrayList<Pelaaja> haettuPelaajalista = mapper.readValue(new File("pelaajalista.json"), new TypeReference<ArrayList<Pelaaja>>(){});
            return haettuPelaajalista;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            ArrayList<Pelaaja> tyhjaPelaajalista = new ArrayList<>();
            System.out.println("Kakka2");
            return tyhjaPelaajalista;
        }


        /*Gson gson = new Gson();
        String pelaajalistaString = "";
        try {
            String path = "./pelaajalista.json";
            pelaajalistaString = Files.readString(Paths.get(path));
            System.out.println(pelaajalistaString);
            Type pelaajalistaTyyppi = new TypeToken<ArrayList<Pelaaja>>(){}.getType();
            ArrayList<Pelaaja> pelaajalista = gson.fromJson(pelaajalistaString, pelaajalistaTyyppi);
            return pelaajalista;
        } catch (Exception e) {
            ArrayList<Pelaaja> tyhjaPelaajalista = new ArrayList<>();
            return tyhjaPelaajalista;
        }*/
    }

    public static void poistaKayttaja(int indeksi) {
        ArrayList<Pelaaja> pelaajalista = haePelaajalista();
        pelaajalista.remove(indeksi);

        Gson gson = new Gson();
        try {
            FileWriter fw = new FileWriter("./pelaajalista.json");
            String json = gson.toJson(pelaajalista);
            fw.write(json);
            fw.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println();
        }
    }

    public static void valinta1() {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            ArrayList<Pelaaja> pelaajalista = haePelaajalista();
            System.out.println("--- VETOPOKERI ---");
            System.out.println(
                    "('U')  Uusi peli" + "\n" +
                            "('L')  Lataa peli" + "\n" +
                            "('P')  Poista käyttäjä" + "\n" +
                            "('E')  Exit");
            System.out.print("Valitse: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("U")) {
                    System.out.print("Käyttäjänimi: ");
                    String kayttajanimi = lukija.nextLine();
                    if (pelaajalista.size() > 0) {
                        for (var p : pelaajalista) {
                            if (p.getNimi().equals(kayttajanimi)) {
                                System.out.println("Virhe! Kyseinen käyttäjä on jo luotu.");
                                System.out.println("Haluatko ladata käyttäjän " + kayttajanimi + " pelin? Kyllä ('K'),  Ei ('E')");
                                System.out.print("Valitse: ");
                                String input2 = lukija.nextLine();
                                if (input2.equals("K")) {
                                    System.out.println();
                                    System.out.println("Tervetuloa takaisin " + p.getNimi() + "!");
                                    System.out.println("Saldo: " + p.getSaldo());
                                    uudestaan = false;
                                    valinta2(p);
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
                                System.out.println("Saldo: 0");
                                valinta2(new Pelaaja(kayttajanimi));
                            }
                        }
                    } else {
                        uudestaan = false;
                        System.out.println();
                        System.out.println("Tervetuloa " + kayttajanimi + "!");
                        System.out.println("Saldo: 0");
                        valinta2(new Pelaaja(kayttajanimi));
                    }

                } else if (input.equals("L")) {
                    int i = 1;
                    System.out.println();
                    if (pelaajalista.size() > 0) {
                        System.out.println("Tallennetut käyttäjät:");
                        for (var p : pelaajalista) {
                            System.out.println("(" + i + ") " + p.getNimi());
                            i++;
                        }
                        System.out.print("Valitse ladattava käyttäjä: ");
                        int input2 = Integer.valueOf(lukija.nextLine());
                        if (input2 > pelaajalista.size() || input2 < 1) {
                            throw new CustomException("Kelvoton syöte!");
                        }
                        uudestaan = false;
                        Pelaaja valittuPelaaja = pelaajalista.get(input2 - 1);
                        System.out.println();
                        System.out.println("Tervetuloa takaisin " + valittuPelaaja.getNimi() + "!");
                        System.out.println("Saldo: " + valittuPelaaja.getSaldo());
                        valinta2(valittuPelaaja);
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
                        for (var p : pelaajalista) {
                            System.out.println("(" + i + ") " + p.getNimi());
                            i++;
                        }
                        System.out.print("Valitse poistettava käyttäjä: ");
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

    public static void valinta2(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            tallennus(pelaaja);
            System.out.println(
                    "('P')  Pelaa" + "\n" +
                            "('S')  Näytä saldo" + "\n" +
                            "('T')  Talleta kolikoita" + "\n" +
                            "('E')  Exit");
            System.out.print("Valitse: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P")) {
                    if (pelaaja.getSaldo() > 0) {
                        uusiPeli(pelaaja);
                    } else {
                        throw new CustomException("Pelitilin saldo on 0. Talleta kolikoita pelataksesi.");
                    }
                } else if (input.equals("S")) {
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
                    System.out.println();
                } else if (input.equals("T")) {
                    System.out.print("Talletettavien kolikoiden määrä: ");
                    int maara = Integer.valueOf(lukija.nextLine());
                    if (maara >= 0) {
                        pelaaja.setSaldo(maara + pelaaja.getSaldo());
                        System.out.println("Uusi saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        System.out.println();
                    } else {
                        throw new CustomException("Negatiivinen talletus!");
                    }
                } else if (input.equals("E")) {
                    uudestaan = false;
                    System.out.println();
                    valinta1();

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

    public static void uusiPeli(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        Korttipakka pakka = new Korttipakka();
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.print("Aseta alkupanos:");
            try {
                int panos = Integer.valueOf(lukija.nextLine());
                if (panos > pelaaja.getSaldo()) {
                    throw new CustomException("Liian suuri panos!" + "\n" +
                            "Pelitilin saldo: " + pelaaja.getSaldo());
                } else if (panos <= 0) {
                    throw new CustomException("Kelvoton panos!");
                } else if (panos > 0 && panos <= pelaaja.getSaldo()){
                    pelaaja.setPanos(panos);
                    pelaaja.setSaldo(pelaaja.getSaldo() - panos);
                    pelaaja.jaaUusiKasi(pakka.arvo5korttia());
                    System.out.println();
                    System.out.println("Käsi:");
                    pelaaja.tulostaKasi();
                    System.out.println();
                    valinta3(pelaaja, pakka);
                    pelaaja.tarkistus();
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo() + " kolikkoa");
                    System.out.println("----------------------------------");
                    System.out.println();
                    uudestaan = false;
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

    public static void valinta3(Pelaaja pelaaja, Korttipakka pakka) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println("('V')  Vaihda kortteja" + "\n" + "('P')  Pidä kortit");
            System.out.print("Valitse: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    System.out.print("Valitse kortit, jotka haluat vaihtaa: ");
                    String[] vaihdettavat = lukija.nextLine().split(",");
                    pelaaja.vaihdaKortteja(vaihdettavat, pakka);
                    System.out.println();
                    System.out.println("Uusi käsi:");
                    pelaaja.tulostaKasi();
                    uudestaan = false;
                } else if (input.equals("P")) {
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

}