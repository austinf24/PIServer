package edu.virginia.psyc.pi.persistence.Questionnaire;

import edu.virginia.psyc.pi.persistence.ParticipantDAO;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: dan
 * Date: 3/19/14
 * Time: 4:42 PM
 * This causes Spring to automatically create CRUD operations for the
 * given object:
 *    delete(T entity) which deletes the entity given as a parameter.
 *    findAll() which returns a list of entities.
 *    findOne(ID id) which returns the entity using the id given a parameter as a search criteria.
 *    save(T entity) which saves the entity given as a parameter.
 * Additional methods will be provided automatically by following a standard
 * naming convention, as is the case with findByEmailAddress
 */
public interface DASS21_ASRepository extends QuestionnaireRepository<DASS21_AS> {

    List<DASS21_AS> findByParticipantDAO(ParticipantDAO p);
    List<DASS21_AS> findBySessionId(String sessionId);

}
