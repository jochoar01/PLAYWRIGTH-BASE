package com.practice.basics;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests para JavaBasics.
 * Descomenta los tests y hazlos pasar implementando los métodos.
 */
public class JavaBasicsTest {

    private JavaBasics basics = new JavaBasics();

    @Test
    public void testSumar() {
        assertThat(basics.sumar(2, 3)).isEqualTo(5);
        assertThat(basics.sumar(10, 20)).isEqualTo(30);
        assertThat(basics.sumar(-5, 5)).isEqualTo(0);
    }

    @Test
    public void testFactorial() {
        assertThat(basics.factorial(5)).isEqualTo(120);
        assertThat(basics.factorial(3)).isEqualTo(6);
        assertThat(basics.factorial(0)).isEqualTo(1);
    }

    @Test
    public void testEsPar() {
        assertThat(basics.esPar(4)).isTrue();
        assertThat(basics.esPar(7)).isFalse();
        assertThat(basics.esPar(0)).isTrue();
    }

    @Test
    public void testEncontrarMaximo() {
        assertThat(basics.encontrarMaximo(new int[]{1, 5, 3, 9, 2})).isEqualTo(9);
        assertThat(basics.encontrarMaximo(new int[]{-1, -5, -3})).isEqualTo(-1);
    }

    @Test
    public void testInvertirTexto() {
        assertThat(basics.invertirTexto("hola")).isEqualTo("aloh");
        assertThat(basics.invertirTexto("Java")).isEqualTo("avaJ");
    }

    @Test
    public void testContarVocales() {
        assertThat(basics.contarVocales("hola mundo")).isEqualTo(4);
        assertThat(basics.contarVocales("programacion")).isEqualTo(5);
    }

    @Test
    public void testEsPalindromo() {
        assertThat(basics.esPalindromo("reconocer")).isTrue();
        assertThat(basics.esPalindromo("anilina")).isTrue();
        assertThat(basics.esPalindromo("hola")).isFalse();
    }
}
