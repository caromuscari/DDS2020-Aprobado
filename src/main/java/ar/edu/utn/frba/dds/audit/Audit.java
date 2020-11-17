package ar.edu.utn.frba.dds.audit;

import ar.edu.utn.frba.dds.App;
import ar.edu.utn.frba.dds.Repositorios.RepositorioProveedores;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.query.Query;
import org.bson.types.ObjectId;
import spark.Request;
import spark.Response;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Entity("Audit")
public class Audit {
    @Id
    @JsonIgnore
    private ObjectId id;
    private String accion;
    private String entidad;
    private String usuario;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date fecha;

    public Audit() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }


    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Audit(String accion, String entidad, String usuario) {
        this.accion = accion;
        this.entidad = entidad;
        this.usuario = usuario;
        this.fecha = new Date();
    }

    public static Object list (Request request, Response response){
        try {
            String accion = request.queryParams("accion");
            String entidad = request.queryParams("entidad");
            Query<Audit> queryAudit = App.getDatastore().createQuery(Audit.class);
            if(accion != null){
                queryAudit.filter("accion",accion);
            }
            if(entidad != null){
                queryAudit.filter("entidad",entidad);
            }
            List<Audit> auditList = queryAudit.asList();
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(auditList);
        }catch (Exception e){
            return null;
        }
    }

    public static void crearAuditoria(String accion, String entidad, String usuario){
        Audit audit = new Audit(accion,entidad,usuario);
        App.getDatastore().save(audit);
    }
}
