package ar.edu.utn.frba.dds.Repositorios;

import ar.edu.utn.frba.dds.Operaciones.Egreso;
import ar.edu.utn.frba.dds.Operaciones.MedioDePago;
import ar.edu.utn.frba.dds.Operaciones.TipoPago;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMedioDePago {

    private List<MedioDePago> medios = new ArrayList<>();
    private EntityManager entityManager;

    public RepositorioMedioDePago(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public MedioDePago crearMedioDePago(String identificador, Long numero, String tipo) {
        MedioDePago medio = new MedioDePago(identificador, numero, tipo);
        this.entityManager.persist(medio);
        return medio;
    }

    public List<MedioDePago> obtenerMediosDePago() {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<MedioDePago> consulta = cb.createQuery(MedioDePago.class);
        Root<MedioDePago> mediosDePago = consulta.from(MedioDePago.class);
        return this.entityManager.createQuery(consulta.select(mediosDePago)).getResultList();
    }

    public MedioDePago obtenerItemsPorId(String id) {
        for (MedioDePago medio : obtenerMediosDePago()) {
            if (medio.getIdentificador().equals(id)) {
                return medio;
            }
        }
        return null;
    }

    public void eliminarMedioDePago(MedioDePago medio){
        this.entityManager.remove(medio);
    }

}
