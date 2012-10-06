package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;

/**
 * @author Ampath Developers Pharmacy Drug to add drugs
 */
public class Drugs extends BaseOpenmrsData {

    private Integer id;

    private DrugName drugName;

    private Concept concept;

    private DrugFormulation drugFormulation;

    private DrugStrength drugStrength;

    private DrugUnits drugUnit;

    /** default constructor */
    public Drugs() {
    }

    /**
     * @return  id
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
     * @return drugName
     */
    public DrugName getDrugName() {

        return drugName;
    }

    /**
     * @param drugName
     */

    public void setDrugName(DrugName drugName) {

        this.drugName = drugName;

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

    /**
     * @return drugFormulation
     */
    public DrugFormulation getDrugFormulation() {

        return drugFormulation;
    }

    /**
     * @param drugFormulation
     */

    public void setDrugFormulation(DrugFormulation drugFormulation) {

        this.drugFormulation = drugFormulation;

    }

    /**
     * @return drugFormulation
     */
    public DrugStrength getDrugStrength() {

        return drugStrength;
    }

    /**
     * @param drugStrength
     */

    public void setDrugStrength(DrugStrength drugStrength) {

        this.drugStrength = drugStrength;

    }

    /**
     * @return drugUnit
     */
    public DrugUnits getDrugUnit() {

        return drugUnit;
    }

    /**
     * @param drugUnit
     */

    public void setDrugUnit(DrugUnits drugUnit) {

        this.drugUnit = drugUnit;

    }
}
