package com.spe.ecuavoley.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.spe.ecuavoley.model.Partido;

import com.spe.ecuavoley.mapper.PartidoMapper;

@Service
public class PartidoSseService {

    private final PartidoMapper partidoMapper;

    public PartidoSseService(
            PartidoMapper partidoMapper) {

        this.partidoMapper = partidoMapper;
    }

    private static final long SSE_TIMEOUT = 30 * 60 * 1000L;

    private final Map<Long, List<SseEmitter>> emittersPorPartido = new ConcurrentHashMap<>();

    /**
     * Registra un nuevo espectador para recibir actualizaciones
     * de un partido específico.
     */
    public SseEmitter suscribirse(Long partidoId) {

        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT);

        emittersPorPartido
                .computeIfAbsent(
                        partidoId,
                        id -> new CopyOnWriteArrayList<>())
                .add(emitter);

        emitter.onCompletion(() -> eliminarEmitter(partidoId, emitter));

        emitter.onTimeout(() -> {
            eliminarEmitter(partidoId, emitter);
            emitter.complete();
        });

        emitter.onError(error -> eliminarEmitter(partidoId, emitter));

        return emitter;
    }

    /**
     * Envía el estado actualizado del partido a todos
     * los espectadores conectados.
     */
    public void enviarActualizacion(Partido partido) {

        List<SseEmitter> emitters = emittersPorPartido.get(partido.getId());

        if (emitters == null || emitters.isEmpty()) {
            return;
        }

        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(
                        SseEmitter.event()
                                .name("partido-actualizado")
                                .data(partidoMapper.toEnVivoResponse(partido)));
            } catch (IOException exception) {
                eliminarEmitter(partido.getId(), emitter);
                emitter.complete();
            }
        }
    }

    /**
     * Elimina una conexión SSE que terminó o produjo un error.
     */
    private void eliminarEmitter(
            Long partidoId,
            SseEmitter emitter) {

        List<SseEmitter> emitters = emittersPorPartido.get(partidoId);

        if (emitters == null) {
            return;
        }

        emitters.remove(emitter);

        if (emitters.isEmpty()) {
            emittersPorPartido.remove(partidoId);
        }
    }

}