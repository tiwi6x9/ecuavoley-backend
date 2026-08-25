package com.spe.ecuavoley.mapper;

import org.springframework.stereotype.Component;

import com.spe.ecuavoley.dto.PartidoEnVivoResponse;
import com.spe.ecuavoley.model.Partido;

@Component
public class PartidoMapper {

    public PartidoEnVivoResponse toEnVivoResponse(
            Partido partido) {

        PartidoEnVivoResponse response =
                new PartidoEnVivoResponse();

        response.setId(partido.getId());

        response.setEquipoA(partido.getEquipoA());
        response.setEquipoB(partido.getEquipoB());

        response.setPuntosA(partido.getPuntosA());
        response.setPuntosB(partido.getPuntosB());

        response.setSetsA(partido.getSetsA());
        response.setSetsB(partido.getSetsB());

        response.setSetActual(partido.getSetActual());
        response.setMetaPuntos(partido.getMetaPuntos());

        response.setEquipoCambio(
                partido.getEquipoCambio());

        response.setEstado(
                partido.getEstado().name());

        if (partido.getCampeonato() != null) {

            response.setTipo("CAMPEONATO");

            response.setCampeonatoId(
                    partido.getCampeonato().getId());

            response.setCampeonatoNombre(
                    partido.getCampeonato().getNombre());

            if (partido.getFechaCampeonato() != null) {

                response.setFechaNumero(
                        partido
                                .getFechaCampeonato()
                                .getNumero());

                response.setFecha(
                        partido
                                .getFechaCampeonato()
                                .getFecha());
            }

            if (partido.getCancha() != null) {
                response.setCanchaNombre(
                        partido
                                .getCancha()
                                .getNombre());
            }

        } else {

            response.setTipo("GENERAL");
        }

        return response;
    }
}