package com.jfelixy.encurtadorurl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "url_mappings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String longUrl;

    @Column(unique = true)
    private String shortKey;

    private LocalDateTime dataCriacao;

    @PrePersist
    public void prePersist(){
        dataCriacao = LocalDateTime.now();
    }
}
