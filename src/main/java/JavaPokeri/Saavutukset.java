package JavaPokeri;

public class Saavutukset implements AvattavatAsiat {
    private final String[] jasenet;
    private final String[] kuvaukset;

    public Saavutukset() {
        jasenet = alustaJasenet();
        kuvaukset = alustaKuvaukset();
    }

    @Override
    public String[] getJasenet() {
        return jasenet;
    }

    public String[] getKuvaukset() {
        return kuvaukset;
    }

    public String[] alustaJasenet() {
        String[] jasenet = {
                "Nahka-Lassen kaataja",
                "NPC-Jannen kaataja",
                "Anilin kaataja",
                "Ernon kaataja",
                "Jytäpoikien kaataja",
                "Srinivasan kaataja",
                "Levrain kaataja",
                "Jari-Matin kaataja",
                "Pattersonin kaataja",
                "Fourierin kaataja",
                "Kuningasvärisuora",
                "Viitoset",
                "Värisuora",
                "Neloset",
                "Täyskäsi",
                "Väri",
                "Suora",
                "Kolmoset",
                "Kaksi paria",
                "10-A pari",
                "100 000 kolikkoa",
                "50 000 kolikkoa",
                "10 000 kolikkoa",
                "5 000 kolikkoa",
                "1 000 kolikkoa",
                "750 kolikkoa",
                "500 kolikkoa",
                "200 kolikkoa",
                "100 kolikkoa",
                "20 kolikkoa",
                "Miljonääri",
                "Satatuhatnääri",
                "Kymmenentuhatnääri",
                "Tuhatnääri",
                "Satanääri"
        };
        return jasenet;
    }

    public String[] alustaKuvaukset() {
        String[] kuvaukset = {
                "Päihitä Nahka-Lasse vetopokerin kilpapelissä",
                "Päihitä NPC-Janne vetopokerin kilpapelissä",
                "Päihitä Anil vetopokerin kilpapelissä",
                "Päihitä Erno vetopokerin kilpapelissä",
                "Päihitä Jytäpojat vetopokerin kilpapelissä",
                "Päihitä Srinivasa vetopokerin kilpapelissä",
                "Päihitä Levrai vetopokerin kilpapelissä",
                "Päihitä Jari-Matti vetopokerin kilpapelissä",
                "Päihitä David Patterson vetopokerin kilpapelissä",
                "Päihitä Jean Baptiste Joseph Fourier vetopokerin kilpapelissä",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on kuningasvärisuora",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on viitoset",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on värisuora",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on neloset",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on täyskäsi",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on väri",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on suora",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on kolmoset",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on kaksi paria",
                "Voita missä tahansa pelimuodosa kädellä jonka arvo on 10-A pari",
                "Voita yhdellä pokerikädellä 100 000 kolikkoa",
                "Voita yhdellä pokerikädellä 50 000 kolikkoa",
                "Voita yhdellä pokerikädellä 10 000 kolikkoa",
                "Voita yhdellä pokerikädellä 5 000 kolikkoa",
                "Voita yhdellä pokerikädellä 1 000 kolikkoa",
                "Voita yhdellä pokerikädellä 750 kolikkoa",
                "Voita yhdellä pokerikädellä 500 kolikkoa",
                "Voita yhdellä pokerikädellä 200 kolikkoa",
                "Voita yhdellä pokerikädellä 100 kolikkoa",
                "Voita yhdellä pokerikädellä 20 kolikkoa",
                "Kasvata saldo 1 000 000 kolikkoon",
                "Kasvata saldo 100 000 kolikkoon",
                "Kasvata saldo 10 000 kolikkoon",
                "Kasvata saldo 1 000 kolikkoon",
                "Kasvata saldo 100 kolikkoon",

        };
        return kuvaukset;
    }

}
