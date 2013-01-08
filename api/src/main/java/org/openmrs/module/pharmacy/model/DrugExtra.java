package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

/**
 * @author Ampath Developers Pharmacy drug formulation
 */
public class DrugExtra extends BaseOpenmrsData {



    private Integer id;


    private Drug drugId;

    private Integer value;

    private double amount;

    private Integer batchId;
    private PharmacyGeneralVariables option;

    private PharmacyLocations location;

    private PharmacyStore inventoryId;

    private Integer quantity;
    private String form;
    private String front;
    private String back;
    /** default constructor */
    public DrugExtra() {
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
     * @return  option
     */
    public PharmacyGeneralVariables getOption() {

        return option;
    }

    /**
     * @param  option
     */

    public void setOption(PharmacyGeneralVariables option) {

        this.option = option;

    }


    /**
     * @return  value
     */
    public Integer getValue() {

        return value;
    }

    /**
     * @param  value
     */

    public void setValue(Integer value) {

        this.value = value;

    }

    /**
     * @return  amount
     */
    public double getAmount() {

        return amount;
    }

    /**
     * @param  amount
     */

    public void setAmount(double amount) {

        this.amount = amount;

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
     * @return  batchId
     */
    public Integer getBatchId() {

        return batchId;
    }

    /**
     * @param  batchId
     */

    public void setBatchId(Integer batchId) {

        this.batchId = batchId;

    }
    /**
     * @return  quantity
     */
    public Integer getQuantity() {

        return quantity;
    }

    /**
     * @param  quantity
     */

    public void setQuantity(Integer quantity) {

        this.quantity = quantity;

    }
    /**
     * @return  form
     */
    public String  getForm() {

        return form;
    }

    /**
     * @param  form
     */

    public void setForm(String form) {

        this.form = form;

    }


    /**
     * @return  front
     */
    public String  getFront() {

        return front;
    }

    /**
     * @param  front
     */

    public void setFront(String front) {

        this.front = front;

    }

    /**
     * @return  back
     */
    public String  getBack() {

        return back;
    }

    /**
     * @param  back
     */

    public void setBack(String back) {

        this.back = back;

    }
    /**
     * @return drugId
     */
    public Drug getDrugId() {

        return drugId;
    }

    /**
     * @param drugId
     */

    public void setDrugId(Drug drugId) {

        this.drugId = drugId;

    }



    /**
     * @return inventoryId
     */
    public PharmacyStore getInventoryId() {

        return inventoryId;
    }

    /**
     * @param inventoryId
     */

    public void setInventoryId(PharmacyStore inventoryId) {

        this.inventoryId = inventoryId;

    }

}
