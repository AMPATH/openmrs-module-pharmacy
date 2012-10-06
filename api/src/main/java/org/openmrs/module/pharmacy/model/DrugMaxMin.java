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
import org.openmrs.Drug;


/**
 *
 */
public class DrugMaxMin extends BaseOpenmrsData {



    private Drug drug;

    private int max;
    private int min;
    private int total;

    private Integer id;

    /** default constructor */
    public DrugMaxMin() {
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
     * @return  max
     */
    public int getMax() {

        return max;
    }

    /**
     * @param max
     */

    public void setMax(int max) {

        this.max = max;

    }


    /**
     * @return  min
     */
    public int getMin() {

        return min;
    }

    /**
     * @param min
     */

    public void setMin(int min) {

        this.min = min;

    }

    /**
     * @return  total
     */
    public int getTotal() {

        return total;
    }

    /**
     * @param total
     */

    public void setTotal(int total) {

        this.total = total;

    }


    /**
     * @return  drug
     */
    public Drug getDrug() {

        return drug;
    }

    /**
     * @param drug
     */

    public void setDrug(Drug drug) {

        this.drug = drug;

    }
}
