package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy drug formulation
 */
public class DrugFormulation extends BaseOpenmrsData {

    private String formulation;

    private Integer id;

    /** default constructor */
    public DrugFormulation() {
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
     * @return formulationName
     */
    public String getFormulation() {

        return formulation;
    }

    /**
     * @param formulation
     */

    public void setFormulation(String formulation) {

        this.formulation = formulation;

    }

}
