package org.openmrs.module.pharmacy.model;

/**
 * @author Ampath Developers Pharmacy Dose to set doses
 */

public class LocationSetter {

    private Integer id;



    private String name="none";

    /** default constructor */
    public LocationSetter() {
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
     * @return  name
     */
    public String getName() {

        return name;
    }

    /**
     * @param  name
     */

    public Boolean setName(String name) {

        this.name = name;
        return true;

    }



}
