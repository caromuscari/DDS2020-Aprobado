package dds;

import com.fasterxml.jackson.core.JsonProcessingException;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.persistence.EntityManager;
import java.security.NoSuchAlgorithmException;

@FunctionalInterface
public interface WithTransaction<E> {
    E method(Request req, Response res, EntityManager em) throws NoSuchAlgorithmException, JsonProcessingException;
}
