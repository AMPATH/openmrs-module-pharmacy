package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.EncounterType;
import org.openmrs.Patient;

import java.util.Date;

/**
 * @author Ampath Developers Pharmacy encounters
 */
public class PharmacyEncounter extends BaseOpenmrsData {

    private Integer id;

    private String formName;

    private Patient person;

    private PharmacyLocations location;



    private EncounterType encounter;

    private Date dateTime;

    /** default constructor */
    public PharmacyEncounter() {
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
     * @return dateTime
     */
    public Date getDateTime() {

        return dateTime;
    }

    /**
     * @param  dateTime
     */

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }


    /**
     * @return encounter
     */
    public EncounterType getEncounter() {

        return encounter;
    }

    /**
     * @param  encounter
     */

    public void setEncounter(EncounterType encounter) {

        this.encounter = encounter;

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
     * @return form name
     */
    public String getFormName() {

        return formName;
    }

    /**
     * @param formName
     */

    public void setFormName(String formName) {

        this.formName = formName;

    }

}
