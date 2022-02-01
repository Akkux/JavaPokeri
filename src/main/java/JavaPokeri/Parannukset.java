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
                "Lisää pakkaan 5. JOKERI",
                "Lisää pakkaan 6. JOKERI",
                "Lisää pakkaan 7. JOKERI",
                "Lisää pakkaan 8. JOKERI",
                "Lisää pakkaan 9. JOKERI",
                "Lisää pakkaan 10. JOKERI"
        };
        return jasenet;
    }

}
