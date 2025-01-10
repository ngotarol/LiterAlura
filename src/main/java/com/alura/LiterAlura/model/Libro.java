package com.alura.LiterAlura.model;

import com.alura.LiterAlura.dto.DatosLibro;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 1000)
    private String titulo;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private IdiomaEntity idioma;

    private Long downloadCount;

    public Libro(){}

    public Libro(Long id, String titulo, Autor autor, IdiomaEntity idioma, Long downloadCount) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.idioma = idioma;
        this.downloadCount = downloadCount;
    }

    public IdiomaEntity getIdioma() {
        return idioma;
    }

    public void setIdioma(IdiomaEntity idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString(){
        String nombreAutor = "";
        try{
            nombreAutor = autor.getName();
        } catch (Exception e) {
            nombreAutor = "sin autor registrado";
        }
        return "Titulo: " + titulo +
                "\nAutor: " + nombreAutor +
                "\nIdioma: " + idioma +
                "\nNro Descargas: " + downloadCount;
    }

    public Libro(DatosLibro datosLibro){
        this.titulo = datosLibro.title();
        this.downloadCount = datosLibro.download_count();

        if (!datosLibro.languages().isEmpty()){
            this.idioma = datosLibro.languages().get(0);
        }

        if (!datosLibro.authors().isEmpty()){
            this.autor = new Autor(datosLibro.authors().get(0));
        }
    }
}
