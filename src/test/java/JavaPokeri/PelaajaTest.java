package JavaPokeri;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


class PelaajaTest {

    private static Stream<Arguments> luoKasia() {

        ArrayList<Kortti> kuningasvarisuora = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.ÄSSÄ),
                new Kortti(Maa.PATA, Arvo.KUNINGAS),
                new Kortti(Maa.PATA, Arvo.KUNINGATAR),
                new Kortti(Maa.PATA, Arvo.JÄTKÄ),
                new Kortti(Maa.PATA, Arvo.KYMMENEN) ));

        ArrayList<Kortti> viitoset = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> varisuora = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.VIISI),
                new Kortti(Maa.PATA, Arvo.KUUSI) ));

        ArrayList<Kortti> neloset = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME) ));

        ArrayList<Kortti> tayskasi = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.KOLME) ));

        ArrayList<Kortti> vari = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.VIISI),
                new Kortti(Maa.PATA, Arvo.VIISI),
                new Kortti(Maa.PATA, Arvo.VIISI) ));

        ArrayList<Kortti> suora = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.VIISI),
                new Kortti(Maa.HERTTA, Arvo.ÄSSÄ) ));

        ArrayList<Kortti> kolmoset = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.HERTTA, Arvo.NELJÄ) ));

        ArrayList<Kortti> kaksiParia = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.HERTTA, Arvo.NELJÄ) ));

        ArrayList<Kortti> pari = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.KYMMENEN),
                new Kortti(Maa.HERTTA, Arvo.KYMMENEN) ));




        //JOKERI-testejä
        ArrayList<Kortti> jokerikuningasvarisuora1 = new ArrayList<>(List.of(
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.PATA, Arvo.KUNINGATAR),
                new Kortti(Maa.PATA, Arvo.JÄTKÄ),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokeriviitoset1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerivarisuora1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerineloset1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.HERTTA, Arvo.KOLME) ));

        ArrayList<Kortti> jokeritayskasi1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.HERTTA, Arvo.KOLME) ));

        ArrayList<Kortti> jokerivari1 = new ArrayList<Kortti>(List.of(
                new Kortti(Maa.PATA, Arvo.ÄSSÄ),
                new Kortti(Maa.PATA, Arvo.ÄSSÄ),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerivari2 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.YHDEKSÄN),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerisuora1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.KUUSI),
                new Kortti(Maa.HERTTA, Arvo.VIISI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerisuora2 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.ÄSSÄ),
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.HERTTA, Arvo.VIISI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerisuora3 = new ArrayList<>(List.of(
                new Kortti(Maa.HERTTA, Arvo.KAKSI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI) ));

        ArrayList<Kortti> jokerikolmoset1 = new ArrayList<>(List.of(
                new Kortti(Maa.PATA, Arvo.KAKSI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.PATA, Arvo.YHDEKSÄN),
                new Kortti(Maa.HERTTA, Arvo.KYMMENEN) ));

        ArrayList<Kortti> jokeripari1 = new ArrayList<>(List.of(
                new Kortti(Maa.JOKERI, Arvo.JOKERI),
                new Kortti(Maa.PATA, Arvo.KOLME),
                new Kortti(Maa.PATA, Arvo.NELJÄ),
                new Kortti(Maa.PATA, Arvo.YHDEKSÄN),
                new Kortti(Maa.HERTTA, Arvo.KYMMENEN) ));

        return Stream.of(
                Arguments.of(kuningasvarisuora, "Tulos: Kuningasvärisuora!\r\n" + "Voitit 125 kolikkoa."),
                Arguments.of(viitoset, "Tulos: Viitoset!\r\n" + "Voitit 125 kolikkoa."),
                Arguments.of(varisuora, "Tulos: Värisuora!\r\n" + "Voitit 38 kolikkoa."),
                Arguments.of(neloset, "Tulos: Neloset!\r\n" + "Voitit 25 kolikkoa."),
                Arguments.of(tayskasi, "Tulos: Täyskäsi!\r\n" + "Voitit 10 kolikkoa."),
                Arguments.of(vari, "Tulos: Väri!\r\n" + "Voitit 8 kolikkoa."),
                Arguments.of(suora, "Tulos: Suora!\r\n" + "Voitit 5 kolikkoa."),
                Arguments.of(kolmoset, "Tulos: Kolmoset!\r\n" + "Voitit 3 kolikkoa."),
                Arguments.of(kaksiParia, "Tulos: Kaksi paria!\r\n" + "Voitit 2 kolikkoa."),
                Arguments.of(pari, "Tulos: 10-A pari!\r\n" + "Voitit 1 kolikkoa."),

                Arguments.of(jokerikuningasvarisuora1, "Tulos: Kuningasvärisuora!\r\n" + "Voitit 125 kolikkoa."),
                Arguments.of(jokeriviitoset1, "Tulos: Viitoset!\r\n" + "Voitit 125 kolikkoa."),
                Arguments.of(jokerivarisuora1, "Tulos: Värisuora!\r\n" + "Voitit 38 kolikkoa."),
                Arguments.of(jokerineloset1, "Tulos: Neloset!\r\n" + "Voitit 25 kolikkoa."),
                Arguments.of(jokeritayskasi1, "Tulos: Täyskäsi!\r\n" + "Voitit 10 kolikkoa."),
                Arguments.of(jokerivari1, "Tulos: Väri!\r\n" + "Voitit 8 kolikkoa."),
                Arguments.of(jokerivari2, "Tulos: Väri!\r\n" + "Voitit 8 kolikkoa."),
                Arguments.of(jokerisuora1, "Tulos: Suora!\r\n" + "Voitit 5 kolikkoa."),
                Arguments.of(jokerisuora2, "Tulos: Suora!\r\n" + "Voitit 5 kolikkoa."),
                Arguments.of(jokerisuora3, "Tulos: Suora!\r\n" + "Voitit 5 kolikkoa."),
                Arguments.of(jokerikolmoset1, "Tulos: Kolmoset!\r\n" + "Voitit 3 kolikkoa."),
                Arguments.of(jokeripari1, "Tulos: 10-A pari!\r\n" + "Voitit 1 kolikkoa.")
        );
    }

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    public String output(Pelaaja pelaaja) {
        pelaaja.kadenTarkistus("vapaapeli");
        return outputStreamCaptor.toString().trim();
    }

    @ParameterizedTest
    @MethodSource("luoKasia")
    void kadenTarkistusTest(ArrayList<Kortti> kasi, String output) {
        assertEquals(
                output, output(new Pelaaja(kasi)));
    }


}