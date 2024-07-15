package com.rameunderscore.literalurachallenge.principal;

import com.rameunderscore.literalurachallenge.model.*;
import com.rameunderscore.literalurachallenge.repository.LibroRepository;
import com.rameunderscore.literalurachallenge.service.ConsumoAPI;
import com.rameunderscore.literalurachallenge.service.ConvierteDatos;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repository;

    public Principal(LibroRepository repository) {
        this.repository = repository;
    }

    public void mostrarMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);

        var opcion = -1;
        while (opcion != 0){
            var menu = """
                    1 - Buscar libro por titulo
                    2 - Historial de libros buscados
                    3 - Lista de Autores
                    4 - Autores vivos por: Anhio
                    5 - Buscar por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroAPI();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarAutoresBuscados();
                    break;
                case 4:
                    mostrarAutoresVivosPorFecha();
                    break;
                case 5:
                    mostrarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando aplicacion. Gracias por usar!");
                    break;
                default:
                    System.out.println("Opcion invalida");
                    break;
            }
        }
    }

    private DatosLibro getDatosLibro(){
        System.out.println("Ingresa el nombre del libro a buscar: ");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()){
            System.out.println("Libro encontrado...");
            return libroBuscado.get();
        } else {
            System.out.println("libro no encontrado, intenta con otro título\n");
            return null;
        }
    }

    private void buscarLibroAPI(){
        Optional<DatosLibro> datosOpcional = Optional.ofNullable(getDatosLibro());

        if(datosOpcional.isPresent()) {
            DatosLibro datos = datosOpcional.get();

            Libro libro = new Libro(datos);
            List<Autores> autores = new ArrayList<>();
            for (DatosAutores datosAutor : datos.listaDeAutores()) {
                Autores autor = new Autores(datosAutor);
                autor.setLibro(libro);
                autores.add(autor);
            }
            libro.setAutor(autores);
            try {
                repository.save(libro);
                System.out.println(libro.getTitulo() + " guardado de forma exitosa!");
                System.out.println("********* LIBRO *********");

                System.out.println("Nombre: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().toString());
                System.out.println("Idioma: " + libro.getIdiomas());
                System.out.println("Numero de descargas: " + libro.getTotalDescargas() + "\n");
            } catch (DataIntegrityViolationException e) {
                System.out.println("Error: El libro ya está almacenado en la base de datos, intenta con otro libro.\n");
            }
        }
    }

    private void mostrarLibrosBuscados(){
        List<Libro> libros = repository.findAll();

        libros.stream().forEach(System.out::println);
    }

    private void mostrarAutoresBuscados(){
        List<Autores> mostarListaAutores = repository.findByAutor();

        Map<String, List<String>> autoresConLibros = mostarListaAutores.stream()
                .collect(Collectors.groupingBy(
                        Autores::getNombreAutor,
                        Collectors.mapping(a -> a.getLibro().getTitulo(), Collectors.toList())
                ));

        autoresConLibros.forEach((nombre, libros) -> {
            Autores autor = mostarListaAutores.stream()
                    .filter(a -> a.getNombreAutor().equals(nombre))
                    .findFirst().orElse(null);
            if (autor != null) {
                System.out.println("********* AUTOR *********");
                System.out.println("Nombre: " + nombre);
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de muerte: " + autor.getFechaFallecimiento());
                System.out.println("Libros: " + libros + "\n");
            }
        });
    }

    private void mostrarAutoresVivosPorFecha(){
        System.out.println("Ingresa el año a consultar:");
        String anhio = teclado.nextLine();

        List<Autores> autoresVivos = repository.filtarFechaAutor(anhio);
        if (autoresVivos.isEmpty()){
            System.out.println("Sin autores vivos en el año indicado...\n");
            return;
        }

        Map<String, List<String>> autoresConLibros = autoresVivos.stream()
                .collect(Collectors.groupingBy(
                        Autores::getNombreAutor,
                        Collectors.mapping(a -> a.getLibro().getTitulo(), Collectors.toList())
                ));

        autoresConLibros.forEach((nombre, libros) -> {
            Autores autor = autoresVivos.stream()
                    .filter(a -> a.getNombreAutor().equals(nombre))
                    .findFirst().orElse(null);
            if (autor != null) {
                System.out.println("+++++++++ AUTOR +++++++++");
                System.out.println("Nombre: " + nombre);
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de muerte: " + autor.getFechaFallecimiento());
                System.out.println("Libros: " + libros + "\n");
            }
        });
    }

    private void mostrarLibrosPorIdioma(){
        System.out.println("""
                Escriba el idioma del libro:
                ES: Español
                EN: Ingles
                FR: Frances
                IT: Italiano
                PT: Portugues
                """);

        var idiomaSelecionado = teclado.nextLine();

        try {
            List<Libro> libroPorIdioma = repository.findByIdiomas(Idioma.valueOf(idiomaSelecionado.toUpperCase()));
            libroPorIdioma.forEach(n -> System.out.println(
                    "+++++++++ LIBRO +++++++++" +
                            "\nTitulo: " + n.getTitulo() +
                            "\nIdioma: " + n.getIdiomas() +
                            "\nAutor: " + n.getAutor().stream().map(Autores::getNombreAutor).collect(Collectors.joining()) +
                            "\nNumero de descargas: " + n.getTotalDescargas() +
                            "\n"
            ));
        } catch (IllegalArgumentException e){
            System.out.println("Idioma no encontrado, intenta de nuevo.\n");
        }

    }
}
