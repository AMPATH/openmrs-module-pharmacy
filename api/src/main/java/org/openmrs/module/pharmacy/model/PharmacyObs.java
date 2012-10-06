package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.ConceptName;
import org.openmrs.Patient;

import java.util.Date;

public class PharmacyObs extends BaseOpenmrsData {

    private Integer id;

    protected Patient person;

    protected PharmacyEncounter pharmacyEncounter;



    protected PharmacyLocations location;

    protected String concept;

    protected int valueCoded;

    protected ConceptName valueCodedName;

    protected Integer valueGroupId;

    protected Date valueDatetime;

    protected Double valueNumeric;

    protected boolean valueBoolen;
    protected String valueModifier;

    protected String valueText;

    protected String comment;

    protected Date dateStarted;

    protected Date dateStopped;

    protected String prescriberId;

    /** default constructor */
    public PharmacyObs() {
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
     * @return  valueBoolen
     */
    public boolean getValueBoolen() {

        return valueBoolen;
    }

    /**
     * @param  valueBoolen
     */

    public void setValueBoolen(boolean valueBoolen) {

        this.valueBoolen = valueBoolen;

    }
    /**
     * @return  prescriberId
     */
    public String getPrescriberId() {

        return prescriberId;
    }

    /**
     * @param  prescriberId
     */

    public void setPrescriberId(String prescriberId) {

        this.prescriberId = prescriberId;

    }
    /**
     * @return person
     */
    public Patient getPerson() {

        return person;
    }

    /**
     * @param person
     */

    public void setPerson(Patient person) {

        this.person = person;

    }

    /**
     * @return encounter
     */
    public PharmacyEncounter getPharmacyEncounter() {

        return pharmacyEncounter;
    }

    /**
     * @param pharmacyEcounter
     */

    public void setPharmacyEncounter(PharmacyEncounter pharmacyEcounter) {

        this.pharmacyEncounter = pharmacyEcounter;

    }

    /**
     * @return location
     */
    public PharmacyLocations getLocation() {

        return location;
    }

    /**
     * @param location
     */

    public void setLocation(PharmacyLocations location) {

        this.location = location;

    }

    /**
     * @return concept
     */
    public String getConcept() {

        return concept;
    }

    /**
     * @param concept
     */

    public void setConcept(String concept) {

        this.concept = concept;

    }

    /**
     * @return valuecoded
     */
    public int getValueCoded() {

        return valueCoded;
    }

    /**
     * @param valuecoded
     */

    public void setValueCoded(int valuecoded) {

        this.valueCoded = valuecoded;

    }

    /**
     * @return valueCodedName
     */
    public ConceptName getValueCodedName() {

        return valueCodedName;
    }

    /**
     * @param valueCodedName
     */

    public void setValueCodedName(ConceptName valueCodedName) {

        this.valueCodedName = valueCodedName;

    }

    /**
     * This converts  value_numeric to a value_boolean, essentially
     *
     * @return Boolean of  obs value
     */
    public Boolean getValueAsBoolean() {
        return (getValueNumeric() == null ? null : getValueNumeric() != 0);
    }

    /**
     * @return Returns  valueDatetime.
     */
    public Date getValueDatetime() {
        return valueDatetime;
    }

    /**
     * @param valueDatetime  valueDatetime to set.
     */
    public void setValueDatetime(Date valueDatetime) {
        this.valueDatetime = valueDatetime;
    }

    /**
     * @return Returns  valueGroupId.
     */
    public Integer getValueGroupId() {
        return valueGroupId;
    }

    /**
     * @param valueGroupId  valueGroupId to set.
     */
    public void setValueGroupId(Integer valueGroupId) {
        this.valueGroupId = valueGroupId;
    }

    /**
     * @return Returns  valueModifier.
     */
    public String getValueModifier() {
        return valueModifier;
    }

    /**
     * @param valueModifier  valueModifier to set.
     */
    public void setValueModifier(String valueModifier) {
        this.valueModifier = valueModifier;
    }

    /**
     * @return Returns  valueNumeric.
     */
    public Double getValueNumeric() {
        return valueNumeric;
    }

    /**
     * @param valueNumeric  valueNumeric to set.
     */
    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    /**
     * @return Returns  valueText.
     */
    public String getValueText() {
        return valueText;
    }

    /**
     * @param valueText  valueText to set.
     */
    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    /**
     * @return comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param  comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return Returns  dateStarted.
     */
    public Date getDateStarted() {
        return dateStarted;
    }

    /**
     * @param dateStarted  dateStarted to set.
     */
    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    /**
     * @return Returns  dateStopped.
     */
    public Date getDateStopped() {
        return dateStopped;
    }

    /**
     * @param dateStopped  dateStopped to set.
     */
    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
    }

}
