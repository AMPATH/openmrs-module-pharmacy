package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * @author Ampath Developers Pharmacy RegimenCombination
 */
public class RegimenCombination extends BaseOpenmrsData {

    private Integer id;

    private Drug drugName;
    private boolean options;
    private Regimen pharmacyRegimen;

    private RegimenNames regimenNames;

    /** default constructor */
    public RegimenCombination() {
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
     * @return pharmacyRegimen
     */
    public Regimen getPharmacyRegimen() {

        return pharmacyRegimen;
    }

    /**
     * @param pharmacyRegimen
     */
    public void setPharmacyRegimen(Regimen pharmacyRegimen) {

        this.pharmacyRegimen = pharmacyRegimen;

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
     * @return options
     */
    public boolean getOptions() {

        return options;
    }

    /**
     * @param options
     */
    public void setOptions(boolean options) {

        this.options = options;

    }



}
