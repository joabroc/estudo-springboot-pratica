package org.example.analytics.model;

import jakarta.persistence.*;

@Entity
@Table(name = "categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_categoria")
    private String tipoCategoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo_categoria() {
        return tipoCategoria;
    }

    public void setTipo_categoria(String tipo_categoria) {
        this.tipoCategoria = tipo_categoria;
    }
}
