/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;


/**
 *
 */
public class PharmacyLocationUsers extends BaseOpenmrsData {

    private String userName;
    private String location;

    private Integer id;

    /** default constructor */
    public PharmacyLocationUsers() {
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
     * @return  userName
     */
    public String getUserName() {

        return userName;
    }

    /**
     * @param userName
     */

    public void setUserName(String userName) {

        this.userName = userName;

    }



    /**
     * @return  location
     */
    public String getLocation() {

        return location;
    }

    /**
     * @param location
     */

    public void setLocation(String location) {

        this.location = location;

    }
}
