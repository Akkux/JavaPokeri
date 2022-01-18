package JavaPokeri;

public class Kilpapelitulos implements Comparable<Kilpapelitulos>{
    public final String nimi;
    public final Integer pisteet;

    public Kilpapelitulos(String nimi, Integer pisteet) {
        this.nimi = nimi;
        this.pisteet = pisteet;
    }

    public String getNimi() {
        return nimi;
    }

    public Integer getPisteet() {
        return pisteet;
    }

    @Override
    public int compareTo(Kilpapelitulos o) {
        return (this.pisteet).compareTo(o.getPisteet());
    }

}
