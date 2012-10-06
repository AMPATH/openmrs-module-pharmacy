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


/**
 *
 *  @author Ampath Developers PharmacySupplier
 */
public class PharmacySupplier  extends BaseOpenmrsData{

    private String name;
    private String description;

    private Integer id;

    /** default constructor */
    public PharmacySupplier() {
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
     * @param name
     */

    public void setName(String name) {

        this.name = name;

    }


    /**
     * @return  description
     */
    public String getDescription() {

        return description;
    }

    /**
     * @param description
     */

    public void setDescription(String description) {

        this.description = description;

    }
}
