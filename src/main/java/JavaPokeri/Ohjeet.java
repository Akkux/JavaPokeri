package JavaPokeri;

public class Ohjeet {

    public Ohjeet() {
        System.out.println("---JAVAPOKERI OHJEET---\n\n" +
                "VETOPOKERI:\n" +
                "   Vetopokerissa pelaaja asettaa ensin alkupanoksen, jonka jälkeen hänelle jaetaan korttipakasta\n" +
                "   viisi sattumanvaraista korttia. Korttipakka muodostuu tavanomaisista 52 kortista. Ensimmäisen jaon \n" +
                "   jälkeen pelaajalla on mahdollisuus vaihtaa mitkä tahansa saamistaan viidestä kortista yhden kerran, \n" +
                "   jolloin niiden tilalle tulee samasta pakasta uudet sattumanvaraiset kortit. Ennen uuden käden \n" +
                "   paljastusta pelaaja saa vielä korottaa panostaan. Korotuksen maksimimäärä on puolet alkupanoksesta. \n" +
                "   Tavoitteena on saada aikaan mahdollisimman hyvä pokerikäsi. Voitot jaetaan käden arvon ja panoksen \n" +
                "   suuruuden mukaan." +
                "   \n" +
                "   Korttipakassa on oletuksena 52 korttia, mutta pelaaja voi ostaa vetopokerin kaupasta parannuksia,\n" +
                "   jotka lisäävät pakkaan JOKERI-kortteja.\n\n" +
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
                "   joten pelata voi niin monta kertaa kuin haluaa. Vapaapeliä voi pelata myös ilman panosta (aseta alkupanos 0),\n" +
                "   mutta silloin voiton suuruus on vain puolet samanarvoisen pokerikäden voittokertoimesta (pyöristetty ylöspäin).\n" +
                "   Esimerkiksi täyskäsi maksaa siis panoksen kanssa 20*panos kolikkoa, mutta ilman panosta vain 10 kolikkoa.\n" +
                "   Vapaapelissä suurin hyväksytty alkupanos on 500 kolikkoa.\n\n" +
                "VETOPOKERI: Kilpapeli\n" +
                "   Kilpapelissä pelaaja aloittaa 100 kolikkolla ja uusia käsiä jaetaan vain 5 kappaletta. Jokainen uusi jako\n" +
                "   suoritetaan täydestä korttipakasta. Pelimuodossa pyritään kasvattamaan kolikoiden määrä mahdollisimman suureksi\n" +
                "   näiden 5 jaon aikana. Kilpapelissä jokaisen jaon minimipanos on 10 kolikkoa. Kilpapelissä panoksen suuruudella\n" +
                "   ei ole ylärajaa. Pelaaja häviää, jos kilpapelin saldo tippuu alle tarvittavan minimipanoksen. Uuden kilpapelin\n" +
                "   aloittaminen maksaa pelaajalle aina 100 kolikkoa. Kolikoita voi kerätä lisää vetopokerin vapaapelistä.\n" +
                "   Kilpapelin lopullinen tulos lisätään lopuksi aina pelaajan saldoon.\n\n");
    }
}
