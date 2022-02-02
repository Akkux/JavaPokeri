package JavaPokeri;

class Ohjeet {

    Ohjeet() {}

    void tulostaOhjeet() {
        System.out.println();
        System.out.println();
        System.out.println("""
                ---JAVAPOKERI OHJEET---

                VETOPOKERI:
                   Vetopokerissa pelaaja asettaa ensin alkupanoksen, jonka jälkeen hänelle jaetaan korttipakasta
                   viisi sattumanvaraista korttia. Korttipakka muodostuu tavanomaisista 52 kortista. Ensimmäisen jaon
                   jälkeen pelaajalla on mahdollisuus vaihtaa mitkä tahansa saamistaan viidestä kortista yhden kerran,
                   jolloin niiden tilalle tulee samasta pakasta uudet sattumanvaraiset kortit. Ennen uuden käden
                   paljastusta pelaaja saa vielä korottaa panostaan. Korotuksen maksimimäärä on puolet alkupanoksesta.
                   Tavoitteena on saada aikaan mahdollisimman hyvä pokerikäsi. Voitot jaetaan käden arvon ja panoksen
                   suuruuden mukaan.
                   
                   Korttipakassa on oletuksena 52 korttia, mutta pelaaja voi ostaa vetopokerin kaupasta parannuksia,
                   jotka lisäävät pakkaan JOKERI-kortteja.

                POKERIKÄSIEN ARVOJÄRJESTYS:
                   Kuningasvärisuora  250 * panos
                   Viitoset           250 * panos
                   Värisuora           75 * panos
                   Neloset             50 * panos
                   Täyskäsi            20 * panos
                   Väri                15 * panos
                   Suora               10 * panos
                   Kolmoset             5 * panos
                   Kaksi paria          3 * panos
                   10-A pari            2 * panos

                VETOPOKERI: Vapaapeli
                   Vapaapelissä pelaajan tavoitteena on kasvattaa oman tilin saldoa. Uusien käsien jakoa ei ole 
                   rajoitettu, joten pelata voi niin monta kertaa kuin haluaa. Vapaapeliä voi pelata myös ilman panosta 
                   (aseta alkupanos 0), mutta silloin voiton suuruus on vain puolet samanarvoisen pokerikäden 
                   voittokertoimesta (pyöristetty ylöspäin). Esimerkiksi täyskäsi maksaa siis panoksen kanssa 
                   20*panos kolikkoa, mutta ilman panosta vain 10 kolikkoa. Vapaapelissä suurin hyväksytty alkupanos 
                   on 500 kolikkoa.

                VETOPOKERI: Kilpapeli
                   Kilpapelissä pelaaja aloittaa 100 kolikkolla ja uusia käsiä jaetaan vain 5 kappaletta. Jokainen uusi 
                   jako suoritetaan täydestä korttipakasta. Pelimuodossa pyritään kasvattamaan kolikoiden määrä 
                   mahdollisimman suureksi näiden 5 jaon aikana. Kilpapelissä jokaisen jaon minimipanos on 10 kolikkoa. 
                   Kilpapelissä panoksen suuruudella ei ole ylärajaa. Pelaaja häviää, jos kilpapelin saldo tippuu alle 
                   tarvittavan minimipanoksen. Uuden kilpapelin aloittaminen maksaa pelaajalle aina 100 kolikkoa. 
                   Kolikoita voi kerätä lisää vetopokerin vapaapelistä. Kilpapelin lopullinen tulos lisätään lopuksi 
                   aina pelaajan saldoon.

                """);
    }
}
