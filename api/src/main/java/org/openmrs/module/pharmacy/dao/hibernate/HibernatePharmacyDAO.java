/**
 *
 */
package org.openmrs.module.pharmacy.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.module.pharmacy.dao.PharmacyDAO;
import org.openmrs.module.pharmacy.model.*;

import java.util.List;

/**
 * @author Ampath Developers
 */
public class HibernatePharmacyDAO implements PharmacyDAO {

    @SuppressWarnings("unused")
    private static final Log log = LogFactory.getLog(HibernatePharmacyDAO.class);
    LocationSetter loc= new LocationSetter();
    private SessionFactory sessionFactory;

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDoses(org.openmrs.module.pharmacy.model.Dose)
     */

    public Dose saveDoses(Dose doses) {

        sessionFactory.getCurrentSession().saveOrUpdate(doses);

        return doses;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDoses()
     */

    @SuppressWarnings("unchecked")
    public List<Dose> getDoses() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Dose.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDosesByUuid(java.lang.String)
     */

    public Dose getDosesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Dose.class).add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<Dose> doses = criteria.list();
        if (null == doses || doses.isEmpty()) {
            return null;
        }
        return doses.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugFormulation(org.openmrs.module.pharmacy.model.DrugFormulation)
     */

    public DrugFormulation saveDrugFormulation(DrugFormulation drugFormulation) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugFormulation);

        return drugFormulation;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFormulation()
     */
    @SuppressWarnings("unchecked")
    public List<DrugFormulation> getDrugFormulation() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFormulation.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFormulationByUuid(java.lang.String)
     */

    public DrugFormulation getDrugFormulationByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFormulation.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugFormulation> drugFormulation = criteria.list();
        if (null == drugFormulation || drugFormulation.isEmpty()) {
            return null;
        }
        return drugFormulation.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFormulationByName(java.lang.String)
     */

    public DrugFormulation getDrugFormulationByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFormulation.class)
                .add(Expression.eq("formulation", name));

        @SuppressWarnings("unchecked")
        List<DrugFormulation> drugFormulation = criteria.list();
        if (null == drugFormulation || drugFormulation.isEmpty()) {
            return null;
        }
        return drugFormulation.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugName(org.openmrs.module.pharmacy.model.DrugName)
     */

    public DrugName saveDrugName(DrugName drugName) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugName);

        return drugName;
    }




    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugName()
     */
    @SuppressWarnings("unchecked")
    public List<DrugName> getDrugName() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugName.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugNameByUuid(java.lang.String)
     */

    public DrugName getDrugNameByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugName.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugName> drugName = criteria.list();
        if (null == drugName || drugName.isEmpty()) {
            return null;
        }
        return drugName.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugNameByName(java.lang.String)
     */

    public DrugName getDrugNameByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugName.class)
                .add(Expression.eq("drugName", name));

        @SuppressWarnings("unchecked")
        List<DrugName> drugName = criteria.list();
        if (null == drugName || drugName.isEmpty()) {
            return null;
        }
        return drugName.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugFrequency(org.openmrs.module.pharmacy.model.DrugFrequency)
     */

    public DrugFrequency saveDrugFrequency(DrugFrequency drugFrequency) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugFrequency);

        return drugFrequency;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFrequency()
     */

    @SuppressWarnings("unchecked")
    public List<DrugFrequency> getDrugFrequency() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFrequency.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFrequencyByUuid(java.lang.String)
     */

    public DrugFrequency getDrugFrequencyByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFrequency.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugFrequency> drugFrequency = criteria.list();
        if (null == drugFrequency || drugFrequency.isEmpty()) {
            return null;
        }
        return drugFrequency.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugFrequencyByName(java.lang.String)
     */

    public DrugFrequency getDrugFrequencyByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugFrequency.class)
                .add(Expression.eq("frequencyName", name));

        @SuppressWarnings("unchecked")
        List<DrugFrequency> drugFrequency = criteria.list();
        if (null == drugFrequency || drugFrequency.isEmpty()) {
            return null;
        }
        return drugFrequency.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugs(org.openmrs.module.pharmacy.model.Drugs )
     */

    public Drugs saveDrugs(Drugs drug) {
        sessionFactory.getCurrentSession().saveOrUpdate(drug);

        return drug;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugs()
     */

    @SuppressWarnings("unchecked")
    public List<Drugs> getDrugs() {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Drugs.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugsByUuid(java.lang.String)
     */

    public Drugs getDrugsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Drugs.class).add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<Drugs> drug = criteria.list();
        if (null == drug || drug.isEmpty()) {
            return null;
        }
        return drug.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugsByName(java.lang.String)
     */

    public Drugs getDrugsByName(String name) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Drugs.class)
                .add(Expression.eq("drugName", name));

        @SuppressWarnings("unchecked")
        List<Drugs> drug = criteria.list();
        if (null == drug || drug.isEmpty()) {
            return null;

        }

        return drug.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugStrength(org.openmrs.module.pharmacy.model.DrugStrength)
     */

    public DrugStrength saveDrugStrength(DrugStrength drugstrength) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugstrength);

        return drugstrength;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugStrength()
     */

    @SuppressWarnings("unchecked")
    public List<DrugStrength> getDrugStrength() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugStrength.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugStrengthByUuid(java.lang.String)
     */

    public DrugStrength getDrugStrengthByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugStrength.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugStrength> drugStrength = criteria.list();
        if (null == drugStrength || drugStrength.isEmpty()) {
            return null;
        }
        return drugStrength.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugStrengthByName(java.lang.String)
     */

    public DrugStrength getDrugStrengthByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugStrength.class)
                .add(Expression.eq("drugStrength", name));

        @SuppressWarnings("unchecked")
        List<DrugStrength> drugStrength = criteria.list();
        if (null == drugStrength || drugStrength.isEmpty()) {
            return null;
        }
        return drugStrength.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugUnits(org.openmrs.module.pharmacy.model.DrugUnits)
     */

    public DrugUnits saveDrugUnits(DrugUnits drugunits) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugunits);

        return drugunits;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugUnits()
     */

    @SuppressWarnings("unchecked")
    public List<DrugUnits> getDrugUnits() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugUnits.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugUnitsByUuid(java.lang.String)
     */

    public DrugUnits getDrugUnitsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugUnits.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugUnits> drugUnits = criteria.list();
        if (null == drugUnits || drugUnits.isEmpty()) {
            return null;
        }
        return drugUnits.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugUnitsByName(java.lang.String)
     */

    public DrugUnits getDrugUnitsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugUnits.class)
                .add(Expression.eq("drugUnitName", name));

        @SuppressWarnings("unchecked")
        List<DrugUnits> drugUnits = criteria.list();
        if (null == drugUnits || drugUnits.isEmpty()) {
            return null;
        }
        return drugUnits.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugUnitsListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<DrugUnits> getDrugUnitsListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugUnits.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugUnits(org.openmrs.module.pharmacy.model.DrugUnits)
     */

    public RegimenNames saveRegimenNames(RegimenNames regimenNames) {
        sessionFactory.getCurrentSession().saveOrUpdate(regimenNames);

        return regimenNames;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenNames()
     */

    @SuppressWarnings("unchecked")
    public List<RegimenNames> getRegimenNames() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenNames.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenNamesByUuid(java.lang.String)
     */

    public RegimenNames getRegimenNamesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenNames.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<RegimenNames> regimenNames = criteria.list();
        if (null == regimenNames || regimenNames.isEmpty()) {
            return null;
        }
        return regimenNames.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenNamesByName(java.lang.String)
     */

    public RegimenNames getRegimenNamesByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenNames.class)
                .add(Expression.eq("regimenName", name));

        @SuppressWarnings("unchecked")
        List<RegimenNames> regimenNames = criteria.list();
        if (null == regimenNames || regimenNames.isEmpty()) {
            return null;
        }
        return regimenNames.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenNamesListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<RegimenNames> getRegimenNamesListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenNames.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveIndicators(org.openmrs.module.pharmacy.model.Indicators)
     */

    public Indicators saveIndicators(Indicators indicators) {
        sessionFactory.getCurrentSession().saveOrUpdate(indicators);

        return indicators;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicators()
     */

    @SuppressWarnings("unchecked")
    public List<Indicators> getIndicators() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicatorsByUuid(java.lang.String)
     */

    public Indicators getIndicatorsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<Indicators> indicators = criteria.list();
        if (null == indicators || indicators.isEmpty()) {
            return null;
        }
        return indicators.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getIndicatorsListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<Indicators> getIndicatorsListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Indicators.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyEncounter(org.openmrs.module.pharmacy.model.PharmacyEncounter)
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyEncounter);

        return pharmacyEncounter;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounter()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounter() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterByUuid(java.lang.String)
     */

    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyEncounter> pharmacyEncounter = criteria.list();
        if (null == pharmacyEncounter || pharmacyEncounter.isEmpty()) {
            return null;
        }
        return pharmacyEncounter.get(0);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterListByPatientId(Person)
     */
    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("person", id));


        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounter.class)
                .add(Expression.eq("uuid", uuid)).add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyEncounterType(org.openmrs.module.pharmacy.model.PharmacyEncounterType)
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyEncounterType);

        return pharmacyEncounterType;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterType()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounterType> getPharmacyEncounterType() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterTypeByUuid(java.lang.String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyEncounterType> pharmacyEncounterType = criteria.list();
        if (null == pharmacyEncounterType || pharmacyEncounterType.isEmpty()) {
            return null;
        }
        return pharmacyEncounterType.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyEncounterTypeListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyEncounterType.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);

        return pharmacyStore;
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore) {
        for (PharmacyStore pharmacy : pharmacyStore) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacy);
        }


        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventory()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyStore> getPharmacyInventory() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByUuid(String)
     */

    public PharmacyStore getPharmacyInventoryByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStore> pharmacyStore = criteria.list();
        if (null == pharmacyStore || pharmacyStore.isEmpty()) {
            return null;
        }
        return pharmacyStore.get(0);
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByCategory(PharmacyCategory)
     *
     * */

    public  List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("category", uuid));


        return criteria.list();
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryByDrugUuid(org.openmrs.Drug,String)
     */

    public PharmacyStore getPharmacyInventoryByDrugUuid(Drug uuid,String location) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("drugs", uuid))
                .add(Expression.eq("location", location));

        @SuppressWarnings("unchecked")
        List<PharmacyStore> pharmacyStore = criteria.list();
        if (null == pharmacyStore || pharmacyStore.isEmpty()) {
            return null;
        }
        return pharmacyStore.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventoryListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStore.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugTransactions);

        return drugTransactions;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions) {

        for (DrugTransactions pharmacyStore : drugTransactions) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyInventory()
     */

    @SuppressWarnings("unchecked")
    public List<DrugTransactions> getDrugTransactions() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugTransactionsByUuid(java.lang.String)
     */

    public DrugTransactions getDrugTransactionsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugTransactions> drugTransactions = criteria.list();
        if (null == drugTransactions || drugTransactions.isEmpty()) {
            return null;
        }
        return drugTransactions.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugTransactionsListByUuid(java.lang.String)
     */

    @SuppressWarnings("unchecked")
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugTransactions.class)
                .add(Expression.eq("uuid", uuid));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyExtra(List)
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyExtra) {
        for (PharmacyExtra pharmacyOs : pharmacyExtra) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }
        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyExtra()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyExtra> getPharmacyExtra() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyExtra.class);

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyExtraByUuid(java.lang.String)
     */

    public PharmacyExtra getPharmacyExtraByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyExtra.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyExtra> pharmacyOs = criteria.list();
        if (null == pharmacyOs || pharmacyOs.isEmpty()) {
            return null;
        }
        return pharmacyOs.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyObs(List)
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs) {
        for (PharmacyObs pharmacyOs : pharmacyObs) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }
        return true;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyObs()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyObs> getPharmacyObs() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class);

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyObsByUuid(java.lang.String)
     */

    public PharmacyObs getPharmacyObsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyObs> pharmacyObs = criteria.list();
        if (null == pharmacyObs || pharmacyObs.isEmpty()) {
            return null;
        }
        return pharmacyObs.get(0);
    }


    @SuppressWarnings("unchecked")
    public List<PharmacyObs> getPharmacyObsByEncounterId(String uuid)
    {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyObs.class)
                .add(Expression.eq("pharmacyEncounter", uuid));


        return criteria.list();
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrders()
     */

    @SuppressWarnings("unchecked")
    public List<PharmacyOrders> getPharmacyOrders() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("voided", false));

        return criteria.list();

    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrdersByUuid(java.lang.String)
     */

    public PharmacyOrders getPharmacyOrdersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyOrders> pharmacyOrders = criteria.list();
        if (null == pharmacyOrders || pharmacyOrders.isEmpty()) {
            return null;
        }
        return pharmacyOrders.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyOrdersByEncounterId(PharmacyEncounter)
     */

    public  List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyOrders.class)
                .add(Expression.eq("pharmacyEncounter", uuid));


        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveRegimen(org.openmrs.module.pharmacy.model.Regimen)
     */

    public Regimen saveRegimen(Regimen regimen) {
        sessionFactory.getCurrentSession().saveOrUpdate(regimen);

        return regimen;

    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimen()
     */

    @SuppressWarnings("unchecked")
    public List<Regimen> getRegimen() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Regimen.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimen(String category)
     */

    @SuppressWarnings("unchecked")
    public List<Regimen> getRegimen(String category) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Regimen.class)
                .add(Expression.eq("voided", false))
                .add(Expression.eq("category", category));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenByUuid(java.lang.String)
     */

    public Regimen getRegimenByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Regimen.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<Regimen> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenByName(java.lang.String)
     */

    public Regimen getRegimenByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Regimen.class)
                .add(Expression.eq("regimenNames", name));

        @SuppressWarnings("unchecked")
        List<Regimen> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenById(int)
     */

    public Regimen getRegimenById(int name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(Regimen.class)
                .add(Expression.eq("id", name));

        @SuppressWarnings("unchecked")
        List<Regimen> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveRegimenCombination(org.openmrs.module.pharmacy.model.RegimenCombination)
     */

    public RegimenCombination saveRegimenCombination(RegimenCombination Combination) {
        sessionFactory.getCurrentSession().saveOrUpdate(Combination);

        return Combination;
    }




    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenCombination()
     */

    @SuppressWarnings("unchecked")
    public List<RegimenCombination> getRegimenCombination() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenCombination.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenCombination()
     */

    @SuppressWarnings("unchecked")
    public List<RegimenCombination> getRegimenCombination(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenCombination.class);
        criteria.add(Expression.eq("voided", false));
        criteria.add(Restrictions.ilike("lname", name+"%"));

//		        .add(Expression.eq("voided", false)
//		       .add(Restrictions.ilike("lname", name+"%")));
//		
        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getRegimenCombinationByUuid(java.lang.String)
     */

    public RegimenCombination getRegimenCombinationByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(RegimenCombination.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<RegimenCombination> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugDispenseSettings(org.openmrs.module.pharmacy.model.DrugDispenseSettings)
     */

    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugDispenseSettings);

        return drugDispenseSettings;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettings()
     */

    public List<DrugDispenseSettings> getDrugDispenseSettings() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByUuid(java.lang.String)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /* @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByUuid(java.lang.String)
    */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(PharmacyLocations uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("location", uuid));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByBatch(int)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("batchId", name));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugDispenseSettingsByDrugId(Drug)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugDispenseSettings.class)
                .add(Expression.eq("drugId", id));

        @SuppressWarnings("unchecked")
        List<DrugDispenseSettings> drugDispenseSettings = criteria.list();
        if (null == drugDispenseSettings || drugDispenseSettings.isEmpty()) {
            return null;
        }
        return drugDispenseSettings.get(0);
    }



    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacySupplier(org.openmrs.module.pharmacy.model.PharmacySupplier)
     */

    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacySupplier);

        return pharmacySupplier;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplier()
     */

    public List<PharmacySupplier> getPharmacySupplier() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByUuid(java.lang.String)
     */

    public PharmacySupplier getPharmacySupplierByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacySupplier> regimen = criteria.list();
        if (null == regimen || regimen.isEmpty()) {
            return null;
        }
        return regimen.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByName(java.lang.String)
     */

    public PharmacySupplier getPharmacySupplierByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacySupplier.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacySupplier> pharmacySupplier = criteria.list();
        if (null == pharmacySupplier || pharmacySupplier.isEmpty()) {
            return null;
        }
        return pharmacySupplier.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyTransactionTypes(org.openmrs.module.pharmacy.model.PharmacyTransactionTypes)
     */

    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyTransactionType);

        return pharmacyTransactionType;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypes()
     */

    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByUuid(java.lang.String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyTransactionTypes> pharmacyTransactionTypes = criteria.list();
        if (null == pharmacyTransactionTypes || pharmacyTransactionTypes.isEmpty()) {
            return null;
        }
        return pharmacyTransactionTypes.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByName(java.lang.String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyTransactionTypes.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyTransactionTypes> pharmacyTransactionTypes = criteria.list();
        if (null == pharmacyTransactionTypes || pharmacyTransactionTypes.isEmpty()) {
            return null;
        }
        return pharmacyTransactionTypes.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */

    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacySupplier);

        return pharmacySupplier;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */

    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier) {

        for (PharmacyStoreIncoming pharmacyOs : pharmacySupplier) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOs);
        }

        return true;


    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreIncoming()
     */

    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreIncomingByUuid(java.lang.String)
     */

    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreIncoming.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreIncoming> pharmacyStoreIncoming = criteria.list();
        if (null == pharmacyStoreIncoming || pharmacyStoreIncoming.isEmpty()) {
            return null;
        }
        return pharmacyStoreIncoming.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */

    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing pharmacyStoreOutgoing) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStoreOutgoing);

        return pharmacyStoreOutgoing;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */

    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> pharmacyStoreOutgoing) {

        for (PharmacyStoreOutgoing pharmacyStore : pharmacyStoreOutgoing) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreOutgoing()
     */

    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreOutgoingByUuid(java.lang.String)
     */

    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreOutgoing.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreOutgoing> pharmacyStoreOutgoing = criteria.list();
        if (null == pharmacyStoreOutgoing || pharmacyStoreOutgoing.isEmpty()) {
            return null;
        }
        return pharmacyStoreOutgoing.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#setPharmacyLocation(java.lang.String)
     */

    public boolean setPharmacyLocation(String doses) {
        // TODO Auto-generated method stub
        loc.setName(doses);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#setPharmacyLocationNull()
     */

    public boolean setPharmacyLocationNull() {
        // TODO Auto-generated method stub
        loc.setName(null);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocation()
     */

    public String getPharmacyLocation() {
        if(loc.getName().equalsIgnoreCase("none")){

            return "none";

        }
        else
            return	loc.getName();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyOrders(java.util.List)
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders) {
        for (PharmacyOrders pharmacyOrderss : pharmacyOrders) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyOrderss);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyCategory(org.openmrs.module.pharmacy.model.PharmacyCategory)
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyCategory);

        return pharmacyCategory;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategory()
     */

    public List<PharmacyCategory> getPharmacyCategory() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategoryByUuid(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyCategory> pharmacyCategory = criteria.list();
        if (null == pharmacyCategory || pharmacyCategory.isEmpty()) {
            return null;
        }
        return pharmacyCategory.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyCategoryByName(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyCategory.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyCategory> pharmacyCategory = criteria.list();
        if (null == pharmacyCategory || pharmacyCategory.isEmpty()) {
            return null;
        }
        return pharmacyCategory.get(0);
    }




    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyGeneralVariables(org.openmrs.module.pharmacy.model.PharmacyGeneralVariables)
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyGeneralVariables);

        return pharmacyGeneralVariables;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariables()
     */

    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariablesByUuid(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyGeneralVariables> pharmacyGeneralVariables = criteria.list();
        if (null == pharmacyGeneralVariables || pharmacyGeneralVariables.isEmpty()) {
            return null;
        }
        return pharmacyGeneralVariables.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyGeneralVariablesByName(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyGeneralVariables.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyGeneralVariables> pharmacyGeneralVariables = criteria.list();
        if (null == pharmacyGeneralVariables || pharmacyGeneralVariables.isEmpty()) {
            return null;
        }
        return pharmacyGeneralVariables.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyLocations);

        return pharmacyLocations;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocations()
     */

    public List<PharmacyLocations> getPharmacyLocations() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByUuid(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyLocations> pharmacyLocations = criteria.list();
        if (null == pharmacyLocations || pharmacyLocations.isEmpty()) {
            return null;
        }
        return pharmacyLocations.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByName(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocations.class)
                .add(Expression.eq("name", name));

        @SuppressWarnings("unchecked")
        List<PharmacyLocations> pharmacyLocations = criteria.list();
        if (null == pharmacyLocations || pharmacyLocations.isEmpty()) {
            return null;
        }
        return pharmacyLocations.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyLocationUsers);

        return pharmacyLocationUsers;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocations()
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsers() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyLocationsByUuid(java.lang.String)
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyLocationUsers> pharmacyLocationsUsers = criteria.list();
        if (null == pharmacyLocationsUsers || pharmacyLocationsUsers.isEmpty()) {
            return null;
        }
        return pharmacyLocationsUsers.get(0);
    }


    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyLocationUsers.class)
                .add(Expression.eq("userName", name));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#saveDrugMaxMin(org.openmrs.module.pharmacy.model.DrugMaxMin)
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin) {
        sessionFactory.getCurrentSession().saveOrUpdate(drugMaxMin);

        return drugMaxMin;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMin()
     */

    public List<DrugMaxMin> getDrugMaxMin() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMinByUuid(java.lang.String)
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<DrugMaxMin> drugMaxMin = criteria.list();
        if (null == drugMaxMin || drugMaxMin.isEmpty()) {
            return null;
        }
        return drugMaxMin.get(0);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getDrugMaxMinByDrug(Drug)
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(DrugMaxMin.class)
                .add(Expression.eq("drug", name));

        @SuppressWarnings("unchecked")
        List<DrugMaxMin> drugMaxMin = criteria.list();
        if (null == drugMaxMin || drugMaxMin.isEmpty()) {
            return null;
        }
        return drugMaxMin.get(0);
    }


    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacyStoreApproved) {
        sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStoreApproved);

        return pharmacyStoreApproved;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacyStoreApproved) {

        for (PharmacyStoreApproved pharmacyStore : pharmacyStoreApproved) {
            sessionFactory.getCurrentSession().saveOrUpdate(pharmacyStore);
        }
        return false;
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreApproved()
     */

    public List<PharmacyStoreApproved> getPharmacyStoreApproved() {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreApproved.class)
                .add(Expression.eq("voided", false));

        return criteria.list();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyStoreApprovedByUuid(java.lang.String)
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PharmacyStoreApproved.class)
                .add(Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")
        List<PharmacyStoreApproved> pharmacyStoreApproved = criteria.list();
        if (null == pharmacyStoreApproved || pharmacyStoreApproved.isEmpty()) {
            return null;
        }
        return pharmacyStoreApproved.get(0);
    }

}
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
	
	
