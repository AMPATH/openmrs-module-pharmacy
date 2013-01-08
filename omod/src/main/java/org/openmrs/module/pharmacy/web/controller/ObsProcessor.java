package org.openmrs.module.pharmacy.web.controller;

/**
 * Created with IntelliJ IDEA.
 * User: nelson
 * Date: 12/28/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class ObsProcessor {


    private String encounterType;

    private String encounterDate;
    private String form;
    private String formVersion;



    /** default constructor */
    public ObsProcessor() {
    }

    /**
     * @return  id
     */
    public String getEncounterType() {

        return encounterType;
    }

    /**
     * @param  encounterType
     */

    public void setEncounterType(String encounterType) {

        this.encounterType = encounterType;

    }

    /**
     * @return  encounterDate
     */
    public String getEncounterDate() {

        return encounterDate;
    }

    /**
     * @param  encounterDate
     */

    public void setEncounterDate(String encounterDate) {

        this.encounterDate = encounterDate;


    }

    /**
     * @return  form
     */
    public String getForm() {

        return form;
    }

    /**
     * @param  form
     */

    public void setForm(String form) {

        this.form = form;


    }
    /**
     * @return  formVersion
     */
    public String getFormVersion() {

        return formVersion;
    }

    /**
     * @param  formVersion
     */

    public void setFormVersion(String formVersion) {

        this.formVersion = formVersion;


    }






}
