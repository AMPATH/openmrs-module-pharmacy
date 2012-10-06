package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy drug units
 */
public class RegimenNames extends BaseOpenmrsData {

    private String regimenName;

    private Integer id;

    /** default constructor */
    public RegimenNames() {
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
    public String getRegimenName() {

        return regimenName;
    }

    /**
     * @param  regimenName
     */

    public void setRegimenName(String regimenName) {

        this.regimenName = regimenName;

    }
}
