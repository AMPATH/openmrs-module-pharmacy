package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

/**
 * @author Ampath Developers Pharmacy DrugFrequency
 */
public class DrugFrequency extends BaseOpenmrsData {

    private Integer id;

    private String frequencyName;

    private Concept concept;

    /** default constructor */
    public DrugFrequency() {
    }

    /**
     * @return the id
     */
    public Integer getId() {

        return id;
    }

    /**
     * @param  id
     */

    public void setId(Integer id) {

        this.id = id;

    }

    /**
     * @return frequencyName
     */
    public String getFrequencyName() {

        return frequencyName;
    }

    /**
     * @param frequencyName
     */

    public void setFrequencyName(String frequencyName) {

        this.frequencyName = frequencyName;

    }

    /**
     * @return concept
     */
    public Concept getConcept() {

        return concept;
    }

    /**
     * @param concept
     */

    public void setConcept(Concept concept) {

        this.concept = concept;

    }

}
