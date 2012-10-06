package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy drug units
 */
public class DrugUnits extends BaseOpenmrsData {

    private String drugUnitName;

    private Integer id;

    /** default constructor */
    public DrugUnits() {
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
     * @return drugUnit name
     */
    public String getDrugUnitName() {

        return drugUnitName;
    }

    /**
     * @param drugUnit Name
     */

    public void setDrugUnitName(String drugUnit) {

        this.drugUnitName = drugUnit;

    }
}
