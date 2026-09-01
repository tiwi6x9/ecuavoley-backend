package com.spe.ecuavoley.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.spe.ecuavoley.model.Dirigente;

public interface DirigenteRepository
        extends JpaRepository<Dirigente, Long> {
}