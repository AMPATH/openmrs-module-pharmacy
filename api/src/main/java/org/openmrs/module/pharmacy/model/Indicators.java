package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers Pharmacy Indicators
 */

public class Indicators extends BaseOpenmrsData {

    private String indicatorName;

    private Integer id;

    /** default constructor */
    public Indicators() {
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
     * @return indicatorName
     */
    public String getIndicatorName() {

        return indicatorName;
    }

    /**
     * @param indicatorName
     */

    public void setIndicatorName(String indicatorName) {

        this.indicatorName = indicatorName;

    }
}
