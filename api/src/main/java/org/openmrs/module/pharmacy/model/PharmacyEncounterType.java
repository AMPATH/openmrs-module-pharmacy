package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy encounters type
 */
public class PharmacyEncounterType extends BaseOpenmrsData {

    private Integer id;

    private String name;

    /** default constructor */
    public PharmacyEncounterType() {
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
     * @return name
     */
    public String getName() {

        return name;
    }

    /**
     * @param name
     */

    public void setName(String name) {

        this.name = name;

    }

}
