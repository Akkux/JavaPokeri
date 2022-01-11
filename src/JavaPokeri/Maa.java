package JavaPokeri;

public enum Maa {

    /*PATA(Character.toString((char)0x2664)), HERTTA(Character.toString((char)0x2661)),
    RISTI(Character.toString((char)0x2667)), RUUTU(Character.toString((char)0x2662)),
    JOKERI(Character.toString((char)0x2606));*/

    PATA("PATA"), HERTTA("HERTTA"),
    RISTI("RISTI"), RUUTU("RUUTU"),
    JOKERI("JOKERI");

    public final String symboli;

    Maa(String symboli) {
        this.symboli = symboli;
    }

    /*
    UNICODE-maat
    solid:
    pata:0x2660
    hertta:0x2665
    risti:0x2663
    ruutu:0x2666
    hollow:
    pata:0x2664
    hertta:0x2661
    risti:0x2667
    ruutu:0x2662
    */

}