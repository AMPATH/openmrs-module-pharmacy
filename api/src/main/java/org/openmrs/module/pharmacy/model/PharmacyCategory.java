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
 *  @author Ampath Developers PharmacySupplier
 */
public class PharmacyCategory  extends BaseOpenmrsData{

    private String name;
    private String description;

    private Integer id;

    /** default constructor */
    public PharmacyCategory() {
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
