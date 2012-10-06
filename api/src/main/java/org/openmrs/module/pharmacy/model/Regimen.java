package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * @author Ampath Developers Pharmacy Regimen
 */
public class Regimen extends BaseOpenmrsData {

    private Integer id;

    private Drug drugName;

    private boolean combination;
    private String category;

    private RegimenNames regimenNames;

    /** default constructor */
    public Regimen() {
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
     * @return combination
     */
    public boolean getCombination() {

        return combination;
    }

    /**
     * @param combination
     */
    public void setCombination(boolean combination) {

        this.combination = combination;

    }

    /**
     * @return drugName
     */
    public Drug getDrugName() {

        return drugName;
    }

    /**
     * @param drug
     */

    public void setDrugName(Drug drug) {

        this.drugName = drug;

    }

    /**
     * @return regimenNames
     */
    public RegimenNames getRegimenNames() {

        return regimenNames;
    }

    /**
     * @param regimenNames
     */

    public void setRegimenNames(RegimenNames regimenNames) {

        this.regimenNames = regimenNames;

    }
    /**
     * @return category
     */
    public String getCategory() {

        return category;
    }

    /**
     * @param category
     */

    public void setCategory(String category) {

        this.category = category;

    }



}
