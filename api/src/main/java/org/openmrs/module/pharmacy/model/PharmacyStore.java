package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * @author Ampath Developers PharmacyStore
 */
public class PharmacyStore extends BaseOpenmrsData {

    private Integer id;

    private Drug drugs;

    private Integer quantity;

    private Integer quantityIn;

    private Integer quantityOut;

    private Integer maxLevel;

    private Integer minLevel;

    private String changeReason;
    private String stockReason;

    private String location;

    private Date expireDate;

    private PharmacyStoreIncoming incoming;

    private PharmacyStoreOutgoing outgoing;

    private PharmacyCategory category;

    private Integer batchNo;
    private Integer s11;
    private Integer deliveryNo;

    private PharmacySupplier supplier;


    private PharmacyTransactionTypes transaction;


    /** default constructor */
    public PharmacyStore() {
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
     * @param drugsId
     */
    public void setDrugs(Drug drugsId) {

        this.drugs = drugsId;

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
     * @return storeIncoming
     */
    public PharmacyStoreIncoming getIncoming() {

        return incoming;
    }

    /**
     * @param incoming
     */
    public void setIncoming(PharmacyStoreIncoming incoming) {

        this.incoming = incoming;

    }

    /**
     * @return storeOutgoing
     */
    public PharmacyStoreOutgoing getOutgoing() {

        return outgoing;
    }

    /**
     * @param outgoing
     */
    public void setOutgoing(PharmacyStoreOutgoing outgoing) {

        this.outgoing = outgoing;

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
     * @return changeReason
     */
    public String getChangeReason() {

        return changeReason;
    }

    /**
     * @param changeReason
     */
    public void setChangeReason(String changeReason) {

        this.changeReason = changeReason;

    }

    /**
     * @return stockReason
     */
    public String getStockReason() {

        return stockReason;
    }

    /**
     * @param stockReason
     */
    public void setStockReason(String stockReason) {

        this.stockReason = stockReason;

    }

    /**
     * @return quantity
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

    /**
     * @return maxLevel
     */
    public Integer getMaxLevel() {

        return maxLevel;
    }

    /**
     * @param maxlevel
     */
    public void setMaxLevel(Integer maxlevel) {

        this.maxLevel = maxlevel;

    }

    /**
     * @return minLevel
     */
    public Integer getMinLevel() {

        return minLevel;
    }

    /**
     * @param minLevel
     */
    public void setMinLevel(Integer minLevel) {

        this.minLevel = minLevel;

    }


    /**
     * @return  deliveryNo
     */
    public Integer getDeliveryNo() {

        return deliveryNo;
    }

    /**
     * @param  DeliveryNo
     */

    public void setDeliveryNo(Integer DeliveryNo) {

        this.deliveryNo = DeliveryNo;

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
