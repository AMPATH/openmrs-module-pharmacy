package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * @author Ampath Developers Pharmacy Dose to set doses
 */

public class Dose extends BaseOpenmrsData {

    private Integer id;

    private DrugFrequency drugFrequency;

    private Drug pharmacyDrug;

    private Integer quantity;

    /** default constructor */
    public Dose() {
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
     * @return the quantity
     */
    public Integer getQuantity() {

        return quantity;
    }

    /**
     * @param quantity
     */

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;

    }

    /**
     * @return the drugFrequency
     */
    public DrugFrequency getFrequency() {

        return drugFrequency;
    }

    /**
     * @param  drugFrequency
     */
    public void setFrequency(DrugFrequency drugFrequency) {

        this.drugFrequency = drugFrequency;

    }

    /**
     * @return the pharmacyDrug
     */
    public Drug getPharmacyDrug() {

        return pharmacyDrug;
    }

    /**
     * @param  pharmacyDrug
     */
    public void setPharmacyDrug(Drug pharmacyDrug) {

        this.pharmacyDrug = pharmacyDrug;

    }

}
