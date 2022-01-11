package JavaPokeri;

public enum Arvo {
    ÄSSÄ("A", 14), KAKSI(2), KOLME(3),
    NELJÄ(4), VIISI(5), KUUSI(6), SEITSEMÄN(7),
    KAHDEKSAN(8), YHDEKSÄN(9), KYMMENEN(10), JÄTKÄ("J", 11),
    KUNINGATAR("Q", 12), KUNINGAS("K", 13),
    JOKERI("JOKERI", 0);

    public final String symboli;
    public final int numeroarvo;

    Arvo(String symboli, int arvo) {
        this.symboli = symboli;
        this.numeroarvo = arvo;
    }

    Arvo(int arvo) {
        this.symboli = "" + arvo;
        this.numeroarvo = arvo;
    }
}