package cl.ucn.biblioteca.servicio.api;

import java.util.HashMap;
import java.util.Set;

import cl.ucn.biblioteca.api.*;
import cl.ucn.biblioteca.api.impl.InventarioImpl;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServicioInventarioLibroImpl implements ServicioInventarioLibro{

	private BundleContext contexto;

	public ServicioInventarioLibroImpl(BundleContext contexto) {
		this.contexto = contexto;
	}

	@Override
	public void ingresar(String isbn, String titulo, String autor, String categoria) throws ExcepcionLibroExistente, ExcepcionLibroInvalido {
		Inventario inventario = this.buscarLibroEnInventario();
		LibroMutable libro = inventario.crearLibro(isbn);
		libro.setAutor(autor);
		libro.setCategoria(categoria);
		libro.setTitulo(titulo);
		inventario.guardarLibro(libro);
	}

	@Override
	public void remover(String isbn) throws ExcepcionLibroNoEncontrado {
		Inventario inventario = this.buscarLibroEnInventario();
		inventario.removerLibro(isbn);

	}

	@Override
	public void modificarCategoria(String isbn, String categoria) throws ExcepcionLibroNoEncontrado {
		Inventario inventario = this.buscarLibroEnInventario();
		LibroMutable libro = inventario.cargarLibroParaEdicion(isbn);
		libro.setCategoria(categoria);
	}

	@Override
	public Libro obtener(String isbn) throws ExcepcionLibroNoEncontrado {
		// TODO Auto-generated method stub
		Inventario inventario = this.buscarLibroEnInventario();
		return inventario.cargarLibro(isbn);
	}

	@Override
	public Libro obtenerByTitulo(String titulo) throws ExcepcionLibroNoEncontrado {
		Inventario inventario = this.buscarLibroEnInventario();
		HashMap<Inventario.CriterioBusqueda, String> criterio = new HashMap<Inventario.CriterioBusqueda, String>();
		criterio.put(Inventario.CriterioBusqueda.TITULO_LIKE, titulo);
		Set<String> libros = inventario.buscarLibros(criterio);
		String isbnLibro ="";
		for (String isbn : libros) {
			isbnLibro = isbn;
		}
		return inventario.cargarLibro(isbnLibro);
	}

	private Inventario buscarLibroEnInventario()  {
		
		String nombre = Inventario.class.getName();
		ServiceReference ref = this.contexto.getServiceReference(nombre);
		return (Inventario) this.contexto.getService(ref);
	}
}
