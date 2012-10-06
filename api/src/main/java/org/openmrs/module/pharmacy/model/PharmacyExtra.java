package org.openmrs.module.pharmacy.model;

import org.openmrs.BaseOpenmrsData;

/**
 * @author Ampath Developers PharmacyOrders
 */
public class PharmacyExtra extends BaseOpenmrsData {

    private Integer id;
    private PharmacyGeneralVariables question;

    private int questionAns;

    /** default constructor */
    public PharmacyExtra() {
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
     * @return  question
     */
    public PharmacyGeneralVariables getQuestion() {

        return question;
    }

    /**
     * @param  question
     */

    public void setQuestion(PharmacyGeneralVariables question) {

        this.question = question;

    }




    /**
     * @return  questionAns
     */
    public int getQuestionAns() {

        return questionAns;
    }

    /**
     * @param  questionAns
     */

    public void setQuestionAns(int questionAns) {

        this.questionAns = questionAns;

    }
}
