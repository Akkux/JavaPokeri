package JavaPokeri;

class Kortti {

    private Maa maa;
    private Arvo arvo;

    public Kortti(Maa maa, Arvo arvo) {
        this.maa = maa;
        this.arvo = arvo;
    }

    public Kortti() {
    }

    public void setMaa(Maa maa) {
        this.maa = maa;
    }

    public void setArvo(Arvo arvo) {
        this.arvo = arvo;
    }

    public Arvo getArvo() {
        return arvo;
    }

    public Maa getMaa() {
        return maa;
    }

    public int getNumeroarvo() {
        return arvo.numeroarvo;
    }

    @Override
    public String toString() {
        if (arvo.symboli == "JOKERI") {
            return maa.symboli + " JOKERI";
        }
        return  maa.symboli + " " + arvo.symboli;
    }

}