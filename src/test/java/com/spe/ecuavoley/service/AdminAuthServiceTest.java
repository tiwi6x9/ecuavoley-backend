package com.spe.ecuavoley.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas del servicio de autenticacion de administrador.
 *
 * Es el guardian de todos los endpoints protegidos (crear partidos,
 * actualizar marcadores, asignar dirigentes, etc.), asi que su
 * comportamiento debe quedar cubierto por pruebas antes de tocarlo
 * de nuevo mas adelante.
 */
class AdminAuthServiceTest {

    @Test
    void loginConCodigoCorrectoDevuelveToken() {
        AdminAuthService service = new AdminAuthService("1234");

        String token = service.login("1234");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void loginConCodigoIncorrectoDevuelveNull() {
        AdminAuthService service = new AdminAuthService("1234");

        assertNull(service.login("0000"));
    }

    @Test
    void loginConCodigoNuloDevuelveNull() {
        AdminAuthService service = new AdminAuthService("1234");

        assertNull(service.login(null));
    }

    @Test
    void isValidTokenReconoceElTokenActivo() {
        AdminAuthService service = new AdminAuthService("1234");

        String token = service.login("1234");

        assertTrue(service.isValidToken(token));
    }

    @Test
    void isValidTokenRechazaTokenIncorrecto() {
        AdminAuthService service = new AdminAuthService("1234");

        service.login("1234");

        assertFalse(service.isValidToken("token-invalido"));
    }

    @Test
    void isValidTokenRechazaSiNuncaHuboLogin() {
        AdminAuthService service = new AdminAuthService("1234");

        assertFalse(service.isValidToken("cualquier-token"));
    }

    @Test
    void isValidTokenRechazaTokenNulo() {
        AdminAuthService service = new AdminAuthService("1234");

        service.login("1234");

        assertFalse(service.isValidToken(null));
    }

    // Documenta el comportamiento actual: solo hay un token activo a
    // la vez en todo el servidor. Un login nuevo invalida el
    // anterior, aunque sea otra persona la que inicio sesion.
    @Test
    void unNuevoLoginInvalidaElTokenAnterior() {
        AdminAuthService service = new AdminAuthService("1234");

        String primerToken = service.login("1234");
        String segundoToken = service.login("1234");

        assertFalse(service.isValidToken(primerToken));
        assertTrue(service.isValidToken(segundoToken));
    }
}
