package cl.psp.laboratorio.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class NuevoBundleImplActivator implements BundleActivator {
    ServiceRegistration reg = null;

    @Override
    public void start(BundleContext context) throws Exception {
        ClasePrueba servicio = new ClasePrueba(context);
        reg = context.registerService(String.valueOf(ClasePruebaInterface.class), servicio, null);
        System.out.println("Servicio de Biblioteca registrado");

    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO Auto-generated method stub
        if (reg !=null) {
            reg.unregister();
            System.out.println("osgi-example-service: Service unregistered");
        }
    }
}
