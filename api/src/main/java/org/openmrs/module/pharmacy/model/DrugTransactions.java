package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * @author Ampath Developers DrugTransactions
 */
public class DrugTransactions extends BaseOpenmrsData {

    private Integer id;

    private Drug drugs;

    private Integer quantityIn;

    private Integer quantityOut;

    private String comment;

    private String location;

    private Date expireDate;
    private PharmacyCategory category;

    /** default constructor */
    public DrugTransactions() {
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
     * @return drug
     */
    public Drug getDrugs() {

        return drugs;
    }

    /**
     * @param drugs
     */
    public void setDrugs(Drug drugs) {

        this.drugs = drugs;

    }

    /**
     * @return comment
     */
    public String getComment() {

        return comment;
    }



    /**
     * @param comment
     */
    public void setComment(String comment) {

        this.comment = comment;

    }
    /**
     * @param location
     */
    public void setLocation(String location) {

        this.location = location;

    }


    /**
     * @return location
     */
    public String getLocation() {

        return location;
    }


    /**
     * @param category
     */
    public void setCategory(PharmacyCategory category) {

        this.category = category;

    }


    /**
     * @return category
     */
    public PharmacyCategory getCategory() {

        return category;
    }

    /**
     * @param expireDate
     */
    public void setexpireDate(Date expireDate) {

        this.expireDate = expireDate;

    }


    /**
     * @return expireDate
     */
    public Date getExpireDate() {

        return expireDate;
    }

    /**
     * @return quantityIn
     */
    public Integer getQuantityIn() {

        return quantityIn;
    }

    /**
     * @param quantityIn
     */
    public void setQuantityIn(Integer quantityIn) {

        this.quantityIn = quantityIn;

    }

    /**
     * @return quantityOut
     */
    public Integer getQuantityOut() {

        return quantityOut;
    }

    /**
     * @param quantityOut
     */
    public void setQuantityOut(Integer quantityOut) {

        this.quantityOut = quantityOut;

    }

}
