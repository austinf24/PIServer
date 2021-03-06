package edu.virginia.psyc.pi.DAO;

import edu.virginia.psyc.pi.domain.DoNotDelete;
import edu.virginia.psyc.pi.persistence.Questionnaire.QuestionnaireData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by dan on 10/26/15.
 */
@Entity
@Table(name="TestQuestionnaire")
@Data
@EqualsAndHashCode(callSuper=false)
@DoNotDelete
public class TestUndeleteable extends QuestionnaireData {

    private String value;

}
