package JavaPokeri;

record Kilpapelitulos(String nimi, Integer pisteet) implements Comparable<Kilpapelitulos> {

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
