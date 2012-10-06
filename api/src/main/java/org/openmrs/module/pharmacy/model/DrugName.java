package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy drug names
 */
public class DrugName extends BaseOpenmrsData {

    private String drugName;

    private Integer id;

    /** default constructor */
    public DrugName() {
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
     * @return drugName
     */
    public String getDrugName() {

        return drugName;
    }

    /**
     * @param drugName
     */

    public void setDrugName(String drugName) {

        this.drugName = drugName;

    }

}
