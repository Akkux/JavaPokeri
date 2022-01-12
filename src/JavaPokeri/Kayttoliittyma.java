package JavaPokeri;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.File;
import java.util.ArrayList;
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
    // -       commit1
    // -mahdollisuus valita eri pelimuotoja
    // -pelimuoto (vapaa peli / kilpapeli)
    // -mahdollistettu panoksen korotus ensimmäisen käden jälkeen (max 1/2 alkup.panos)
    //
    //lisää voittotaulukko
    //lisää avattavia saavutuksia
    //pelaajalistassa ei ehkä tarvitse tallentaa kättä
    //scoreboard, jakojen määrä, häviäminen kun jaot loppuu, uuden pelin aloitus samalla käyttäjällä
    //kaikille oma scoreboard
    //Alkuun valikko, mistä valita itse peli (vetopokeri tai pikapokeri)
    //lisää kommentointi tarvittaviin metodeihin
    //lisää mahdollisuus kahden tai useamman pelaajan pelimuotoon, jossa korotetaan panosta ja pelataan muita vastaan


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

        // Kirjoitetaan muutokset JSON-tiedostoon
        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("pelaajalista.json"), pelaajalista );
        } catch (Exception e) {
            System.out.println(e);
            System.out.println();
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
            ArrayList<Pelaaja> tyhjaPelaajalista = new ArrayList<>();
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

        final ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("pelaajalista.json"), pelaajalista );
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
                                    valitsePelimuoto(p);
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
                                valitsePelimuoto(new Pelaaja(kayttajanimi));
                            }
                        }
                    } else {
                        uudestaan = false;
                        System.out.println();
                        System.out.println("Tervetuloa " + kayttajanimi + "!");
                        valitsePelimuoto(new Pelaaja(kayttajanimi));
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
                        valitsePelimuoto(valittuPelaaja);
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

    public static void valitsePelimuoto(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            System.out.println(
                            "PELIMUODOT:" + "\n" +
                            "('V')  Vetopokeri" + "\n" +
                            "('?')  ?????" + "\n" +
                            "('T')  Takaisin alkuvalikkoon");
            System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    uudestaan = false;
                    System.out.println();
                    vetopokeriValinta(pelaaja);
                } else if (input.equals("?")) {
                    throw new CustomException("Tämä pelimuoto on tulossa pian :)");
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    alkuvalikko();
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
                            "('V')  Vapaapeli  (Loputtomat kolikot ja jaot)" + "\n" +
                            "('K')  Kilpapeli  (ennaltamäärätty kolikkojen ja jakojen määrä)" + "\n" +
                            "('T')  Takaisin pelimuodon valintaan");
            System.out.print("Valitse pelimuoto syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("V")) {
                    System.out.println();
                    System.out.println("Pelaajan " + pelaaja.getNimi() + " saldo: " + pelaaja.getSaldo());
                    uudestaan = false;
                    vetopokeriVapaapeli(pelaaja);
                } else if (input.equals("K")) {
                    //uudestaan = false;
                    //vetopokeriKilpapeli(pelaaja);
                } else if (input.equals("T")) {
                    System.out.println();
                    uudestaan = false;
                    valitsePelimuoto(pelaaja);
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
                            "VETOPOKERI: Vapaapeli" + "\n" +
                            "('P')  Pelaa" + "\n" +
                            "('S')  Näytä saldo" + "\n" +
                            "('T')  Talleta kolikoita" + "\n" +
                            "('E')  Exit");
            System.out.print("Valitse syöttämällä kirjain: ");
            String input = lukija.nextLine();
            try {
                if (input.equals("P")) {
                    if (pelaaja.getSaldo() > 0) {
                        uusiVapaaPeli(pelaaja);
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
            System.out.print("Aseta alkupanos: ");
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
                    korttienVaihto(pelaaja, pakka);
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

    public static void korttienVaihto(Pelaaja pelaaja, Korttipakka pakka) {
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
                    lisapanos(pelaaja);
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

    public static void lisapanos(Pelaaja pelaaja) {
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
                    if (korotus >= 0 && korotus <= maksimikorotus) {
                        if (korotus > pelaaja.getSaldo()) {
                            throw new CustomException("Jäljellä oleva saldo: " + pelaaja.getSaldo() + " kolikkoa");
                        }
                        pelaaja.setPanos(korotus + pelaaja.getPanos());
                        System.out.println("Uusi kokonaispanos: " + pelaaja.getPanos() + " kolikkoa");
                        pelaaja.setSaldo(pelaaja.getSaldo()-korotus);
                        System.out.println();
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

    /*public static void vetopokeriKilpapeli(Pelaaja pelaaja) {
        Scanner lukija = new Scanner(System.in);
        boolean uudestaan = true;
        while (uudestaan) {
            tallennus(pelaaja);
            System.out.println(
                    "VETOPOKERI: Kilpapeli" + "\n" +
                            "('P')  Pelaa" + "\n" +
                            "('S')  Näytä saldo" + "\n" +
                            "('T')  Talleta kolikoita" + "\n" +
                            "('E')  Exit");
            System.out.print("Valitse: ");
            String input = lukija.nextLine();
            try {*/

}