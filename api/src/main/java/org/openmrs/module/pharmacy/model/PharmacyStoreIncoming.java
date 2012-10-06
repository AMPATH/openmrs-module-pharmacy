/**
 *  contents of this file are subject to  OpenMRS Public License
 * Version 1.0 ( "License"); you may not use this file except in
 * compliance with  License. You may obtain a copy of  License at
 * http://license.openmrs.org
 *
 * Software distributed under  License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, eir express or implied. See 
 * License for  specific language governing rights and limitations
 * under  License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;
import org.openmrs.User;

import java.util.Date;


/**
 * @author Ampath Developers PharmacyStoreIncoming
 */
public class PharmacyStoreIncoming extends BaseOpenmrsData {

    private Integer id;

    private Drug drug;

    private Integer quantityIn;



    private Integer maxLevel;

    private Integer minLevel;

    private Integer batchNo;
    private Integer s11;
    private Integer deliveryNo;

    private Date expireDate;


    private boolean approved;


    private String changeReason;
    private String status;

    private PharmacyLocations location;


    private PharmacyLocations destination;


    private PharmacySupplier supplier;


    private PharmacyTransactionTypes transaction;


    private PharmacyCategory category;



    private User requested;
    private User issued;
    private User authorized;




    /** default constructor */
    public PharmacyStoreIncoming() {
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
     * @return  requested
     */
    public User getRequested() {

        return requested;
    }

    /**
     * @param  requested
     */

    public void setRequested(User requested) {

        this.requested = requested;

    }
    /**
     * @return  issued
     */
    public User getissued() {

        return issued;
    }

    /**
     * @param  issued
     */

    public void setIssued(User issued) {

        this.issued = issued;

    }
    /**
     * @return  authorized
     */
    public User getAuthorized() {

        return authorized;
    }

    /**
     * @param  authorized
     */

    public void setAuthorized(User authorized) {

        this.authorized = authorized;

    }










    /**
     * @return  deliveryNo
     */
    public Integer getDeliveryNo() {

        return deliveryNo;
    }

    /**
     * @param DeliveryNo
     */

    public void setDeliveryNo(Integer DeliveryNo) {

        this.deliveryNo = DeliveryNo;

    }




    /**
     * @return drug
     */
    public Drug getDrugs() {

        return drug;
    }

    /**
     * @param drugsId
     */
    public void setDrugs(Drug drugsId) {

        this.drug = drugsId;

    }


    /**
     * @return approved
     */
    public boolean getApproved() {

        return approved;
    }

    /**
     * @param  num
     */
    public void setApproved(boolean num) {

        this.approved = num;

    }

    /**
     * @param quantityIn
     */
    public void setQuantityIn(int quantityIn) {

        this.quantityIn = quantityIn;

    }

    /**
     * @return quantityIn
     */
    public Integer getQuantityIn() {

        return quantityIn;
    }


    /**
     * @param maxLevel
     */
    public void setMaxLevel(int maxLevel) {

        this.maxLevel = maxLevel;

    }

    /**
     * @return maxLevel
     */
    public Integer getMaxLevel() {

        return maxLevel;
    }
    /**
     * @param minLevel
     */
    public void setMinLevel(int minLevel) {

        this.minLevel = minLevel;

    }

    /**
     * @return minLevel
     */
    public Integer getMinLevel() {

        return minLevel;
    }




    /**
     * @param batchNo
     */
    public void setBatchNo(int batchNo) {

        this.batchNo = batchNo;

    }

    /**
     * @return batchNo
     */
    public Integer getBatchNo() {

        return batchNo;
    }


    /**
     * @param s11
     */
    public void setS11(int s11) {

        this.s11 = s11;

    }

    /**
     * @return s11
     */
    public Integer getS11() {

        return s11;
    }

    /**
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {

        this.expireDate = expireDate;

    }

    /**
     * @return expireDate
     */
    public Date getExpireDate() {

        return expireDate;
    }

    /**
     * @param changeReason
     */
    public void setChangeReason(String changeReason) {

        this.changeReason = changeReason;

    }

    /**
     * @return changeReason
     */
    public String getChangeReason() {

        return changeReason;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {

        this.status = status;

    }

    /**
     * @return status
     */
    public String getStatus() {

        return status;
    }


    /**
     * @param location
     */
    public void setLocation(PharmacyLocations location) {

        this.location = location;

    }

    /**
     * @return location
     */
    public PharmacyLocations getLocation() {

        return location;
    }


    /**
     * @param destination
     */
    public void setDestination(PharmacyLocations destination) {

        this.destination = destination;

    }

    /**
     * @return location
     */
    public PharmacyLocations getDestination() {

        return destination;
    }


    /**
     * @param supplier
     */
    public void setSupplier(PharmacySupplier supplier) {

        this.supplier = supplier;

    }

    /**
     * @return supplier
     */
    public PharmacySupplier getSupplier() {

        return supplier;
    }



    /**
     * @param transaction
     */
    public void setTransaction(PharmacyTransactionTypes transaction) {

        this.transaction = transaction;

    }

    /**
     * @return supplier
     */
    public PharmacyTransactionTypes getTransaction() {

        return transaction;
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



}