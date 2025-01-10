package com.alura.LiterAlura.principal;

import com.alura.LiterAlura.dto.DatosLibro;
import com.alura.LiterAlura.dto.DatosResponse;
import com.alura.LiterAlura.model.Autor;
import com.alura.LiterAlura.model.IdiomaEntity;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.IdiomaRepository;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "http://gutendex.com";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private IdiomaRepository idiomaRepository;
    private final String colorTexto = "\033[1;95m";
    private final String colorConsola = "\033[1;97m";
    private final String colorResultado = "\033[1;94m";
    private List<Autor> listadoAutores;
    private List<Libro> listadoLibros;
    private final String separador = "**************************";

    public Principal(AutorRepository autorRepository,LibroRepository libroRepository,IdiomaRepository idiomaRepository){
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.idiomaRepository = idiomaRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = "\n" + separador + """
                    \nBienvenido a LiterAlura
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Buscar autor registrado
                    7 - Top 10 libros registrados más descargados
                    
                    0 - Salir
                    
                    Ingresa la opcion:
                    """;

            System.out.println(colorTexto + menu + colorConsola);
            boolean opcionValida = false;
            int numeroMinimo = 0;
            int numeroMaximo = 7;
            String msgErrorMenu = "Debe ingresar una opción válida entre " + numeroMinimo + " y " + numeroMaximo;
            do {
                try {
                    opcion = teclado.nextInt();
                    teclado.nextLine();
                    if ( (opcion >= numeroMinimo) && (opcion <= numeroMaximo) ) {
                        opcionValida = true;
                    }
                    else {
                        System.out.println(msgErrorMenu);
                    }
                }
                catch (InputMismatchException e){
                    System.out.println(msgErrorMenu);
                    teclado.nextLine();
                }
            } while (!opcionValida);

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 6:
                    buscarAutor();
                    break;
                case 7:
                    top10DescargadosRegistrados();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println(msgErrorMenu);
            }
        }
    }

    private void top10DescargadosRegistrados() {
        listadoLibros = libroRepository.findTop10ByOrderByDownloadCountDesc();
        if (listadoLibros.isEmpty()){
            System.out.println("Sin resultados");
        }
        else{
            System.out.println(colorResultado + "Estos son los 10 títulos más descargados: ");
            for (int i = 0; i < listadoLibros.size(); i++) {
                System.out.println(separador + (i+1) + separador);
                System.out.println(listadoLibros.get(i));
            }
        }
    }

    private void buscarAutor() {
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        var nombre = teclado.nextLine();
        listadoAutores = autorRepository.findByNameContainingIgnoreCase(nombre);
        if (listadoAutores.isEmpty()){
            System.out.println("Sin resultados");
        }
        else{
            System.out.println(colorResultado + "Estos son los autores coincidentes: ");
            for (int i = 0; i < listadoAutores.size(); i++) {
                System.out.println(separador + (i+1) + separador);
                System.out.println(listadoAutores.get(i).toStringLibros());
            }
        }
    }

    private void listarLibrosPorIdioma() {
        List<IdiomaEntity> listadoIdiomas = idiomaRepository.findAll();
        if (listadoIdiomas.isEmpty()){
            System.out.println("No se encontraron idiomas");
        }
        else {
            var idiomasDisponibles = "";
            System.out.println("Indica el nro del idioma en que deseas buscar titulos:");
            int nroIdiomas = listadoIdiomas.size();

            for (int i = 0; i < listadoIdiomas.size(); i++) {
                idiomasDisponibles += " [" + (i+1) + " " + listadoIdiomas.get(i).getNombre() + "] ";
                if ((i+1) % 6 == 0){ idiomasDisponibles += "\n"; }
            }
            System.out.println(idiomasDisponibles);

            boolean nroValido = false;
            var nroIdioma = 0;
            String msgError = "Debe ingresar un número entero hasta " + nroIdiomas;
            do {
                try {
                    nroIdioma = teclado.nextInt();
                    teclado.nextLine();
                    if ( (nroIdioma >= 1) && (nroIdioma <= nroIdiomas) ) {
                        nroValido = true;
                    }
                    else {
                        System.out.println(msgError);
                    }
                }
                catch (InputMismatchException e){
                    System.out.println(msgError);
                    teclado.nextLine();
                }
            } while (!nroValido);

            nroIdioma -= 1;
            listadoLibros = libroRepository.libroPorIdioma(listadoIdiomas.get(nroIdioma));
            if (listadoLibros.isEmpty()){
                System.out.println("Para el idioma '" + listadoIdiomas.get(nroIdioma).getNombre() +
                        "' no se encontraron resultados");
            }
            else {
                System.out.println(colorConsola+"Para el idioma '" + listadoIdiomas.get(nroIdioma).getNombre() +
                        "' se encuentra(n) "+ listadoLibros.size() +" titulo(s)");
                listadoLibros.forEach(libro -> System.out.println(colorResultado + "\t" + libro.getTitulo() + colorConsola));
            }
        }
    }

    private void listarAutoresVivos() {
        boolean nroValido = false;
        var anio = 0;
        String msgErrorAnio = "Ingrese un año válido";
        System.out.println("Ingresa el año: ");
        do {
            try {
                anio = teclado.nextInt();
                teclado.nextLine();
                nroValido = true;
            }
            catch (InputMismatchException e){
                System.out.println(msgErrorAnio);
                teclado.nextLine();
            }
        } while (!nroValido);

        listadoAutores = autorRepository.autoresVivosPorAnio(anio);
        if (listadoAutores.isEmpty()){
            System.out.println("No se encontraron coincidencias");
        }
        else {
            System.out.println(colorResultado + "Este es el listado de autores vivos en el año "+anio+":");
            listadoAutores
                    .forEach(autor ->
                            System.out.println(separador + autor));
            System.out.println(separador + "\n");
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println(colorResultado + "Este es el listado de libros registrados:");
        listadoLibros = libroRepository.findAll();
        if (listadoLibros.isEmpty()){
            System.out.println("No se encontraron coincidencias");
        }
        else {
            listadoLibros
                    .forEach(libro ->
                            System.out.println( separador + "\n" +
                                    libro));
            System.out.println(separador + "\n");
        }
    }

    private void listarAutoresRegistrados() {
        listadoAutores = autorRepository.findAll();
        if (listadoAutores.isEmpty()){
            System.out.println("No se encontraron coincidencias");
        }
        else {
            System.out.println(colorResultado + "Este es el listado de autores registrados:");
            listadoAutores
                    .forEach(autor ->
                            System.out.println(separador + "\n" +
                                    autor.toStringLibros()));
            System.out.println(separador + "\n");
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println(colorTexto + "Escribe el titulo o el autor del libro que deseas buscar" + colorConsola);
        var nombreLibro = teclado.nextLine()
                .replace(" ","%20");
        teclado.nextLine();

        var json = consumoAPI.obtenerDatos(URL_BASE + "/books?search=" + nombreLibro);
        //System.out.println(json);
        if (json == null){
            System.out.println("Sin resultados\n");
        }
        else {
            DatosResponse datosResponse = conversor.obtenerDatos(json, DatosResponse.class);
            var totalLibros = datosResponse.count();
            if (totalLibros > 0){
                var contador = datosResponse.results().size();
                for (int i = 0; i < contador; i++) {
                    DatosLibro datosLibro = datosResponse.results().get(i);
                    //System.out.println(i + " " + datosLibro +"\n");
                    Libro libro = new Libro(datosLibro);
                    System.out.println(colorResultado + separador +(i+1)+ separador + colorConsola);
                    System.out.println(colorResultado + libro + colorConsola);

                    // Revisa si tiene informacion del autor
                    if (!datosLibro.authors().isEmpty()){
                        // revisa si el autor ya existe
                        Optional<Autor> autorOptional;
                        autorOptional = autorRepository.findByNameIgnoreCase(libro.getAutor().getName());
                        if (autorOptional.isEmpty()){
                            autorRepository.save(libro.getAutor());
                            System.out.println(libro.getAutor().getName() + " guardado");
                        }
                        else {
                            libro.setAutor(autorOptional.get());
                            System.out.println("Autor ya registrado");
                        }
                    }

                    // revisa si el idioma ya existe
                    Optional<IdiomaEntity> idiomaEntityOptional = idiomaRepository.findByNombreIgnoreCase(String.valueOf(datosLibro.languages().get(0)));
                    if (idiomaEntityOptional.isEmpty()){
                        idiomaRepository.save(libro.getIdioma());
                        System.out.println("Idioma guardado");
                    }
                    else{
                        libro.setIdioma(idiomaEntityOptional.get());
                        System.out.println("Idioma ya registrado");
                    }

                    // revisa si el libro ya existe
                    Optional<Libro> libroOptional = libroRepository.findByTituloIgnoreCase(libro.getTitulo());
                    if(libroOptional.isEmpty()){
                        libroRepository.save(libro);
                        System.out.println(libro.getTitulo() + " guardado\n");
                    }
                    else{
                        System.out.println("Titulo ya registrado\n");
                    }
                }
            }
            else {
                System.out.println("Sin resultados\n");
            }
        }
    }

}