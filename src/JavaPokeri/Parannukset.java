package JavaPokeri;

public class Parannukset implements AvattavatAsiat {

    private final String[] jasenet;

    public Parannukset() {
        jasenet = alustaJasenet();
    }

    @Override
    public String[] getJasenet() {
        return jasenet;
    }

    public String[] alustaJasenet() {
        String[] jasenet = {
                "Lisää pakkaan 1. JOKERI",
                "Lisää pakkaan 2. JOKERI",
                "Lisää pakkaan 3. JOKERI",
                "Lisää pakkaan 4. JOKERI",
                "Lisää pakkaan 5. JOKERI"
        };
        return jasenet;
    }

}
