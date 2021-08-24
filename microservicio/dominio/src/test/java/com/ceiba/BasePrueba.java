package com.ceiba;

import static org.junit.Assert.fail;

import java.util.function.Supplier;

import org.junit.Assert;

public class BasePrueba {

    private static final String PERO_FUE_LANZADA = " Pero fue lanzada ";
    private static final String SE_ESPERABA_LA_EXCEPCION = "Se esperaba la excepcion ";
    private static final String SIN_EXCEPCIONES = "No hay excepciones";

    public static <T> void assertThrows(Supplier<T> supplier, Class<? extends Exception> exception, String message) {
        try {
            supplier.get();
            fail();
        } catch (Exception e) {
            Assert.assertTrue(SE_ESPERABA_LA_EXCEPCION + exception.getCanonicalName() + PERO_FUE_LANZADA
                    + e.getClass().getCanonicalName(), exception.isInstance(e));
            Assert.assertTrue(e.getMessage().contains(message));
        }
    }

    public static void assertThrows(Thunk thunk, Class<? extends Exception> exception, String message) {
        try {
            thunk.execute();
            fail();
        } catch (Exception e) {
            Assert.assertTrue(SE_ESPERABA_LA_EXCEPCION + exception.getCanonicalName() + PERO_FUE_LANZADA
                    + e.getClass().getCanonicalName(), exception.isInstance(e));
            Assert.assertTrue(e.getMessage().contains(message));
        }
    }

    @FunctionalInterface
    public interface Thunk {
        void execute();
    }
    
    public static <T> void assertNoThrows(Supplier<T> supplier) {
        try {
            supplier.get();
            String mensaje = "No hay excepciones";
            Assert.assertEquals(SIN_EXCEPCIONES, mensaje);
        } catch (Exception e) {
            fail();
        }
    }

    public static void assertNoThrows(Thunk thunk) {
        try {
            thunk.execute();
            String mensaje = "No hay excepciones";
            Assert.assertEquals(SIN_EXCEPCIONES, mensaje);
        } catch (Exception e) {
            fail();
        }
    }

}
