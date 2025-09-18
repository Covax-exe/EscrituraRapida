package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase que genera palabras o frases aleatorias para el juego.
 * Incluye una mezcla grande de frases en español y referencias de cultura pop.
 */
public class WordGenerator {

    private final List<String> words; // Lista de palabras/frases disponibles
    private final Random random; // Generador aleatorio

    /**
     * Constructor: inicializa el listado de palabras/frases.
     */
    public WordGenerator() {
        words = new ArrayList<>();
        random = new Random();

        // --- Frases normales y coloquiales ---
        words.add("El perro corre rápido");
        words.add("Hoy hace mucho calor en Cali");
        words.add("Me gusta el chocolate con pan");
        words.add("Estudiar en la noche es complicado");
        words.add("La lluvia cae fuerte en septiembre");
        words.add("El café colombiano es el mejor");
        words.add("Las estrellas brillan en el cielo nocturno");
        words.add("No estaba muerto andaba de parranda");
        words.add("Me dio pereza levantarme de la cama");
        words.add("Se armó la gorda en la fiesta");
        words.add("Me salió más caro el caldo que los huevos");

        // --- Refranes ---
        words.add("Más vale tarde que nunca");
        words.add("Camarón que se duerme se lo lleva la corriente");
        words.add("El que madruga Dios lo ayuda");
        words.add("A caballo regalado no se le mira el diente");
        words.add("Al mal tiempo buena cara");
        words.add("Ojos que no ven corazón que no siente");
        words.add("Cría cuervos y te sacarán los ojos");

        // --- Cultura pop (mezcla en español e inglés) ---
        words.add("Que la fuerza te acompañe");
        words.add("No me quiero ir señor Stark");
        words.add("Hakuna Matata");
        words.add("Hasta el infinito y más allá");
        words.add("Winter is coming");
        words.add("May the Force be with you");
        words.add("I'll be back");
        words.add("I am inevitable");
        words.add("Wubba Lubba Dub Dub!");
        words.add("Bazinga!");
        words.add("All your base are belong to us");
        words.add("Praise the Sun \\[T]/");
        words.add("It's dangerous to go alone!");
        words.add("Fus Ro Dah!");
        words.add("¡Hadouken!");
        words.add("Among Us sus");
        words.add("Doge: wow, much code, very fast");
        words.add("One does not simply walk into Mordor");
        words.add("Shrek es amor, Shrek es vida.");

        // --- Trabalenguas y pangramas ---
        words.add("Tres tristes tigres tragaban trigo en un trigal");
        words.add("El veloz murciélago hindú comía feliz cardillo y kiwi");
        words.add("La cigüeña tocaba el saxofón detrás del palenque de paja");
        words.add("Quiere la boca exhausta vid, kiwi, piña y fugaz jamón");
        words.add("Pablito clavó un clavito en la calva de un calvito");
        words.add("Sphinx of black quartz, judge my vow");
        words.add("The quick brown fox jumps over the lazy dog");

        // --- Frases largas descriptivas ---
        words.add("En una pequeña aldea todos se conocían y compartían historias");
        words.add("La biblioteca estaba llena de libros antiguos y aroma a papel viejo");
        words.add("El trayecto hacia la cumbre fue duro pero la vista valió la pena");
        words.add("Caminó bajo la lluvia sin prisa, pensando en el futuro incierto");
    }

    /**
     * Devuelve una palabra o frase aleatoria.
     */
    public String getRandomWord() {
        int index = random.nextInt(words.size());
        return words.get(index);
    }
}
