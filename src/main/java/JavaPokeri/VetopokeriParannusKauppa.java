package JavaPokeri;


class VetopokeriParannusKauppa {

    private  Pelaaja pelaaja = null;
    private String[] parannustenTilat;
    private Boolean[] vetopokeriParannustenTilat;
    private final int[] parannustenHinnat = {200, 1000, 2000, 5000, 10000, 30000, 50000, 100000, 200000, 500000};
    private int hinta;
    private int parannuksenIndeksi;


    VetopokeriParannusKauppa(Pelaaja pelaaja) {
        this.pelaaja = pelaaja;
        vetopokeriParannustenTilat = pelaaja.getVetopokeriParannustenTilat();

        parannustenTilat = new String[10];

        // Alustetaan parannustenTilat pelaajan tietojen perusteella
        for (int i=0; i<10; i++) {
            if (vetopokeriParannustenTilat[i]) {
                parannustenTilat[i] = "Käytössä";

            } else {
                parannustenTilat[i] = "Hinta: " + parannustenHinnat[i] + " kolikkoa";
            }
        }
    }


    /** Metodi tulostaa vetopokerin parannuskaupan valikon. */
    void tulostaVetopokeriParannukset() {
        String[] jasenet = new Parannukset().getJasenet();
        System.out.println();
        System.out.println();
        System.out.println(
                "--- VETOPOKERI PARANNUKSET ---\n" +
                        "Saldo: " + pelaaja.getSaldo() + " kolikkoa\n");
        System.out.println("Ostettavat parannukset:");

        for (int i=0; i<9; i++) {
            System.out.println("('" + (i+1) + "')  " + jasenet[i] + "     " + parannustenTilat[i]);
        }
        System.out.println("('10') " + jasenet[9] + "     " + parannustenTilat[9]);
        System.out.println("('T')  Takaisin");
        System.out.print("Valitse ostettava parannus: ");
    }


    /** Metodi tarkistaa onko käyttäjän valitseman parannuksen ostaminen mahdollista. */
    boolean ostoMahdollista(String input) throws CustomException {

        for (int i=0; i<10; i++) {
            if (input.equals(String.valueOf(i+1))) {
                if (vetopokeriParannustenTilat[i]) {
                    throw new CustomException("Kyseinen parannus on jo ostettu.");

                } else if (i > 0 && !vetopokeriParannustenTilat[i] && !vetopokeriParannustenTilat[i-1]){
                    throw new CustomException("Parannukset on osetettava järjestyksessä.");

                } else {
                    hinta = parannustenHinnat[i];
                    parannuksenIndeksi = i;
                    return true;
                }
            }
        }
        return false;
    }


    /** Metodi suorittaa parannuksen ostotapahtuman ja päivittää pelaajan vetopokerin parannuksia koskevat
        attribuutit. */
    void parannuksenOsto() throws CustomException {

        System.out.println("Haluatko ostaa parannuksen " + hinta + " kolikolla? ('K') Kyllä, ('E') Ei");
        System.out.print("Valitse syöttämällä kirjain: ");
        String input2 = Kayttoliittyma.tekstiSyote();

        boolean uudestaan = true;
        while (uudestaan) {

            if (input2.equals("K")) {
                if (pelaaja.getSaldo() < hinta) {
                    throw new CustomException("Ei riittävästi kolikoita!" + "\n" +
                            "Pelaajan " + pelaaja.getNimi() + " pelitilin saldo: " + pelaaja.getSaldo() + " kolikkoa");
                }
                Boolean[] parannukset = pelaaja.getVetopokeriParannustenTilat();
                parannukset[parannuksenIndeksi] = true;
                pelaaja.setVetopokeriParannuksenTilat(parannukset);
                pelaaja.setSaldo(pelaaja.getSaldo()-hinta);
                System.out.println("Parannus ostettu. Saldo: " + pelaaja.getSaldo());
                uudestaan = false;

            } else if (input2.equals("E")) {
                uudestaan = false;
            }
        }
    }


}
