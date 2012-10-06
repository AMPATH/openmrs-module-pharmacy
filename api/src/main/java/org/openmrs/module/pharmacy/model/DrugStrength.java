package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

/**
 * @author Ampath Developers Pharmacy drug strength
 */

public class DrugStrength extends BaseOpenmrsData {

    private String drugStrength;

    private Concept concept;

    private Integer id;

    /** default constructor */
    public DrugStrength() {
    }

    /**
     * @return the id
     */
    public Integer getId() {

        return id;
    }

    /**
     * @param id
     */

    public void setId(Integer id) {

        this.id = id;

    }

    /**
     * @return drugStrength name
     */
    public String getDrugStrength() {

        return drugStrength;
    }

    /**
     * @param drugStrength Name
     */

    public void setDrugStrength(String drugStrength) {

        this.drugStrength = drugStrength;

    }

    /**
     * @return concept
     */
    public Concept getConcept() {

        return concept;
    }

    /**
     * @param
     */

    public void setConcept(Concept concept) {

        this.concept = concept;

    }
}
