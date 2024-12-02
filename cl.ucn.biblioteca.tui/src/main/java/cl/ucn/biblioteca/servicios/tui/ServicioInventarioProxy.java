package cl.ucn.biblioteca.servicios.tui;

import cl.ucn.biblioteca.api.*;
import cl.ucn.biblioteca.servicio.api.ServicioInventarioLibro;

import org.apache.felix.service.command.Descriptor;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;


public class ServicioInventarioProxy {

	public static final String AMBITO = "libro";
	public static final String[] FUNCIONES = new String[] {"obtener", "ingresar","remover","modificarCategoria","obtenerByTitulo"};
	private BundleContext contexto;
	public ServicioInventarioProxy(BundleContext contexto)
	{
		this.contexto = contexto;
	}

	@Descriptor("Ingresar libro")
	public String ingresar(@Descriptor("ISBN") String isbn,
						   @Descriptor("Titulo") String titulo,
						   @Descriptor("Autor") String autor,
						   @Descriptor("Categoria") String categoria)
			throws ExcepcionLibroExistente, ExcepcionLibroInvalido
	{
		ServicioInventarioLibro servicio = obtenerServicio();
		servicio.ingresar(isbn, titulo, autor, categoria);
		return isbn;
	}
	@Descriptor("Obtener libro")
	public String obtener(@Descriptor("ISBN") String isbn)
            throws ExcepcionLibroNoEncontrado {
		ServicioInventarioLibro servicio = obtenerServicio();
		Libro libro = servicio.obtener(isbn);
		return libro.toString();
	}
	@Descriptor("Remover libro")
	public String remover(@Descriptor("ISBN") String isbn)
            throws ExcepcionLibroNoEncontrado {
		ServicioInventarioLibro servicio = obtenerServicio();
		servicio.remover(isbn);
		return "Se elimino el libro con ISBN: " + isbn;
	}
	@Descriptor("Modificar categoria")
	public String modificarCategoria(@Descriptor("ISBN") String isbn,
						   @Descriptor("Categoria") String categoria)
            throws ExcepcionLibroNoEncontrado {
		ServicioInventarioLibro servicio = obtenerServicio();
		servicio.modificarCategoria(isbn, categoria);
		return "Se a modificado la categoria del libro con ISBN: " + isbn+"a: "+categoria;
	}

	@Descriptor("Obtener libro por titulo")
	public String obtenerByTitulo(@Descriptor("Titulo") String titulo)
			throws ExcepcionLibroExistente, ExcepcionLibroInvalido, ExcepcionLibroNoEncontrado {
		ServicioInventarioLibro servicio = obtenerServicio();
		Libro libro = servicio.obtenerByTitulo(titulo);
		return libro.toString();
	}


	protected ServicioInventarioLibro obtenerServicio() {

		ServiceReference referencia = contexto.getServiceReference(ServicioInventarioLibro.class.getName());
		if (referencia == null)
			throw new RuntimeException("ServicioInventarioLibro no esta registrado, no puedo invocar operacion");

		ServicioInventarioLibro servicio = (ServicioInventarioLibro) this.contexto.getService(referencia);
		if (servicio == null)
			throw new RuntimeException("ServicioInventarioLibro no esta registrado, no puedo invocar operacion");

		return servicio;
	}

}
