package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Drug;

import java.util.Date;

/**
 * @author Ampath Developers PharmacyOrders
 */
public class PharmacyDrugOrder extends BaseOpenmrsData {

    private Integer id;

    private String concept;
    private String medication;

    private PharmacyEncounter pharmacyEncounter;






    private Drug drugId;

    private String changeId;

    private String indicationId;
    private String doseId;
    private String dispensedBy;


    private int quantity;
    private int pillCount;
    private int regimenId;







    private String instructions;

    private Date startDate;

    private Date autoEndDate;

    private boolean discontinued;

    private Date discontinuedDate;

    private String discontinuedReason;

    private String plan;
    private String valueText;

    private boolean dispensed;




    private Date nextVisitDate;

    private int monthsNo;

    private double amount;


    private double waiverAmount;

    private int receiptNo;

    private int waiverNo;

    private int invoiceNo;


    private PharmacyExtra pharmacyExtra;



    private int quantityRequested;











    /** default constructor */
    public PharmacyDrugOrder() {
    }


    /**
     * @return  waiverAmount
     */
    public double getWaiverAmount() {

        return waiverAmount;
    }

    /**
     * @param  waiverAmount
     */

    public void setWaiverAmount(double waiverAmount) {

        this.waiverAmount = waiverAmount;

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
     * @return  nextVisitDate
     */
    public Date getNextVisitDate() {

        return nextVisitDate;
    }

    /**
     * @param  nextVisitDate
     */

    public void setNextVisitDate(Date nextVisitDate) {

        this.nextVisitDate = nextVisitDate;

    }






    /**
     * @return  monthsNo
     */
    public int getMonthsNo() {

        return monthsNo;
    }

    /**
     * @param  monthsNo
     */

    public void setMonthsNo(int monthsNo) {

        this.monthsNo = monthsNo;

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
     * @return  waiverNo
     */
    public int getWaiverNo() {

        return waiverNo;
    }

    /**
     * @param  waiverNo
     */

    public void setWaiverNo(int waiverNo) {

        this.waiverNo = waiverNo;

    }



    /**
     * @return  receiptNo
     */
    public int getReceiptNo() {

        return receiptNo;
    }

    /**
     * @param  receiptNo
     */

    public void setReceiptNo(int receiptNo) {

        this.receiptNo = receiptNo;

    }



    /**
     * @return  invoiceNo
     */
    public int getInvoiceNo() {

        return invoiceNo;
    }

    /**
     * @param  invoiceNo
     */

    public void setInvoiceNo(int invoiceNo) {

        this.invoiceNo = invoiceNo;

    }



    /**
     * @return concept
     */
    public String getConcept() {

        return concept;
    }




    /**
     * @param dispensedBy
     */

    public void setDispensedBy(String dispensedBy) {

        this.dispensedBy = dispensedBy;

    }

    /**
     * @return dispensedBy
     */
    public String getDispensedBy() {

        return dispensedBy;
    }
    /**
     * @param valueText
     */

    public void setValueText(String valueText) {

        this.valueText = valueText;

    }

    /**
     * @return valueText
     */
    public String getValueText() {

        return valueText;
    }
    /**
     * @param medication
     */

    public void setMedication(String medication) {

        this.medication = medication;

    }

    /**
     * @return medication
     */
    public String getMedication() {

        return medication;
    }

    /**
     * @param concept
     */

    public void setConcept(String concept) {

        this.concept = concept;

    }



    /**
     * @return pharmacyExtra
     */
    public PharmacyExtra getPharmacyExtra() {

        return pharmacyExtra;
    }

    /**
     * @param pharmacyExtra
     */

    public void setPharmacyExtra(PharmacyExtra pharmacyExtra) {

        this.pharmacyExtra = pharmacyExtra;

    }

    /**
     * @return pharmacyEncounter
     */
    public PharmacyEncounter getPharmacyEncounter() {

        return pharmacyEncounter;
    }

    /**
     * @param pharmacyEncounter
     */

    public void setPharmacyEncounter(PharmacyEncounter pharmacyEncounter) {

        this.pharmacyEncounter = pharmacyEncounter;

    }



    /**
     * @return instructions
     */
    public String getInstructions() {

        return instructions;
    }

    /**
     * @param instructions
     */

    public void setInstructions(String instructions) {

        this.instructions = instructions;

    }

    /**
     * @return startDate
     */
    public Date getStartDate() {

        return startDate;
    }

    /**
     * @param startDate
     */

    public void setStartDate(Date startDate) {

        this.startDate = startDate;

    }

    /**
     * @return AutoEndDate
     */
    public Date getAutoEndDate() {

        return autoEndDate;
    }

    /**
     * @param AutoEndDate
     */

    public void setAutoEndDate(Date AutoEndDate) {

        this.autoEndDate = AutoEndDate;

    }

    /**
     * @return discontinued
     */
    public boolean getDiscontinued() {

        return discontinued;
    }

    /**
     * @param discontinued
     */

    public void setDiscontinued(boolean discontinued) {

        this.discontinued = discontinued;

    }

    /*
      * @return discontinuedDate
      */
    public Date getDiscontinuedDate() {

        return discontinuedDate;
    }

    /**
     * @param discontinuedDate
     */

    public void setDiscontinuedDate(Date discontinuedDate) {

        this.discontinuedDate = discontinuedDate;

    }

    /**
     * @return discontinuedReason
     */
    public String getDiscontinuedReason() {

        return discontinuedReason;
    }

    /**
     * @param discontinuedReason
     */

    public void setDiscontinuedReason(String discontinuedReason) {

        this.discontinuedReason = discontinuedReason;

    }

    /**
     * @return plan
     */
    public String getPlan() {

        return plan;
    }

    /**
     * @param plan
     */

    public void setPlan(String plan) {

        this.plan = plan;

    }


    /**
     * @return dispensed
     */
    public boolean getDispensed() {

        return dispensed;
    }

    /**
     * @param dispensed
     */

    public void setDispensed(boolean dispensed) {

        this.dispensed = dispensed;

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
     * @return changeId
     */
    public String getChangeId() {

        return changeId;
    }

    /**
     * @param changeId
     */

    public void setChangeId(String changeId) {

        this.changeId = changeId;

    }











    /**
     * @return indicationId
     */
    public String getIndicationId() {

        return indicationId;
    }

    /**
     * @param indicationId
     */

    public void setIndicationId(String indicationId) {

        this.indicationId = indicationId;

    }





    /**
     * @return doseId
     */
    public String getDoseId() {

        return doseId;
    }

    /**
     * @param doseId
     */

    public void setDoseId(String doseId) {

        this.doseId = doseId;

    }



    /**
     * @return regimenId
     */
    public int getRegimenId() {

        return regimenId;
    }

    /**
     * @param regimenId
     */

    public void setRegimenId(int regimenId) {

        this.regimenId = regimenId;

    }


    /**
     * @return quantityRequested
     */
    public int getQuantityRequested() {

        return quantityRequested;
    }

    /**
     * @param quantityRequested
     */

    public void setQuantityRequested(int quantityRequested) {

        this.quantityRequested = quantityRequested;

    }

    /**
     * @return quantity
     */
    public int getQuantity() {

        return quantity;
    }

    /**
     * @param quantity
     */

    public void setQuantity(int quantity) {

        this.quantity = quantity;

    }



    /**
     * @return pillCount
     */
    public int getPillCount() {

        return pillCount;
    }


    /**
     * @param pillCount
     */

    public void setPillCount(int pillCount) {

        this.pillCount = pillCount;

    }


}
