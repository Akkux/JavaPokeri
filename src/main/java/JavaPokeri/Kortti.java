package JavaPokeri;

import java.io.Serializable;
import java.util.Objects;

class Kortti implements Serializable {

    private final Maa maa;
    private final Arvo arvo;

    Kortti(Maa maa, Arvo arvo) {
        this.maa = maa;
        this.arvo = arvo;
    }

    Maa getMaa() {
        return maa;
    }

    int getNumeroarvo() {
        return arvo.numeroarvo;
    }

    @Override
    /** Metodi määrittelee kuinka monta välilyöntiä maan nimeä ennen tulostetaan,
        jotta korttien arvot tulevat samalle pystyriville kättä tulostaessa. */
    public String toString() {
        if (Objects.equals(maa.symboli, "PATA")) {
            return "  " + maa.symboli + " " + arvo.symboli;
        } if (Objects.equals(maa.symboli, "RISTI")) {
            return " " + maa.symboli + " " + arvo.symboli;
        } if (Objects.equals(maa.symboli, "RUUTU")) {
            return " " + maa.symboli + " " + arvo.symboli;
        }
        return  maa.symboli + " " + arvo.symboli;
    }

}