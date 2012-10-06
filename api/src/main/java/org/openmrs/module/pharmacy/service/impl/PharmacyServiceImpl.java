package org.openmrs.module.pharmacy.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.pharmacy.dao.PharmacyDAO;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;

import java.util.List;

/**
 * @author Ampath developers
 */
public class PharmacyServiceImpl extends BaseOpenmrsService implements PharmacyService {

    protected static final Log log = LogFactory.getLog(PharmacyServiceImpl.class);

    private PharmacyDAO pharmacyDAO;

    public void setPharmacyDAO(PharmacyDAO pharmacyDAO) {
        this.pharmacyDAO = pharmacyDAO;
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDoses(org.openmrs.module.pharmacy.model.Dose)
     */

    public Dose saveDoses(Dose doses) {
        return pharmacyDAO.saveDoses(doses);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDoses()
     */

    public List<Dose> getDoses() {
        return pharmacyDAO.getDoses();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDosesByUuid(java.lang.String)
     */

    public Dose getDosesByUuid(String uuid) {
        return pharmacyDAO.getDosesByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugFormulation(org.openmrs.module.pharmacy.model.DrugFormulation)
     */

    public DrugFormulation saveDrugFormulation(DrugFormulation drugFormulation) {
        return pharmacyDAO.saveDrugFormulation(drugFormulation);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFormulation()
     */

    public List<DrugFormulation> getDrugFormulation() {
        return pharmacyDAO.getDrugFormulation();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFormulationByUuid(java.lang.String)
     */

    public DrugFormulation getDrugFormulationByUuid(String uuid) {
        return pharmacyDAO.getDrugFormulationByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFormulationByName(java.lang.String)
     */

    public DrugFormulation getDrugFormulationByName(String name) {
        return pharmacyDAO.getDrugFormulationByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugName(org.openmrs.module.pharmacy.model.DrugName)
     */

    public DrugName saveDrugName(DrugName drugName) {
        return pharmacyDAO.saveDrugName(drugName);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugName()
     */

    public List<DrugName> getDrugName() {
        return pharmacyDAO.getDrugName();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugNameByUuid(java.lang.String)
     */

    public DrugName getDrugNameByUuid(String uuid) {
        return pharmacyDAO.getDrugNameByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugNameByName(java.lang.String)
     */

    public DrugName getDrugNameByName(String name) {
        return pharmacyDAO.getDrugNameByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugFrequency(org.openmrs.module.pharmacy.model.DrugFrequency)
     */

    public DrugFrequency saveDrugFrequency(DrugFrequency drugFrequency) {
        return pharmacyDAO.saveDrugFrequency(drugFrequency);

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFrequency()
     */

    public List<DrugFrequency> getDrugFrequency() {
        return pharmacyDAO.getDrugFrequency();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFrequencyByUuid(java.lang.String)
     */

    public DrugFrequency getDrugFrequencyByUuid(String uuid) {
        return pharmacyDAO.getDrugFrequencyByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugFrequencyByName(java.lang.String)
     */

    public DrugFrequency getDrugFrequencyByName(String name) {
        return pharmacyDAO.getDrugFrequencyByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugs(org.openmrs.module.pharmacy.model.Drugs)
     */

    public Drugs saveDrugs(Drugs drug) {
        return pharmacyDAO.saveDrugs(drug);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugs()
     */

    public List<Drugs> getDrugs() {
        return pharmacyDAO.getDrugs();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugsByUuid(java.lang.String)
     */

    public Drugs getDrugsByUuid(String uuid) {
        return pharmacyDAO.getDrugsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugsByName(java.lang.String)
     */

    public Drugs getDrugsByName(String name) {
        return pharmacyDAO.getDrugsByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugStrength(org.openmrs.module.pharmacy.model.DrugStrength)
     */

    public DrugStrength saveDrugStrength(DrugStrength drugStrength) {
        return pharmacyDAO.saveDrugStrength(drugStrength);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugStrength()
     */

    public List<DrugStrength> getDrugStrength() {
        return pharmacyDAO.getDrugStrength();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugStrengthByUuid(java.lang.String)
     */

    public DrugStrength getDrugStrengthByUuid(String one) {
        return pharmacyDAO.getDrugStrengthByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugStrengthByName(java.lang.String)
     */

    public DrugStrength getDrugStrengthByName(String name) {
        return pharmacyDAO.getDrugStrengthByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugUnits(org.openmrs.module.pharmacy.model.DrugUnits)
     */

    public DrugUnits saveDrugUnits(DrugUnits drugunits) {
        return pharmacyDAO.saveDrugUnits(drugunits);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugUnits()
     */

    public List<DrugUnits> getDrugUnits() {
        return pharmacyDAO.getDrugUnits();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugUnitsByUuid(java.lang.String)
     */

    public DrugUnits getDrugUnitsByUuid(String one) {
        return pharmacyDAO.getDrugUnitsByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugUnitsByName(java.lang.String)
     */

    public DrugUnits getDrugUnitsByName(String name) {
        return pharmacyDAO.getDrugUnitsByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugUnitsListByUuid(java.lang.String)
     */

    public List<DrugUnits> getDrugUnitsListByUuid(String one) {
        return pharmacyDAO.getDrugUnitsListByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveRegimenNames(RegimenNames)
     */

    public RegimenNames saveRegimenNames(RegimenNames regimenNames) {
        return pharmacyDAO.saveRegimenNames(regimenNames);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenNames()
     */

    public List<RegimenNames> getRegimenNames() {
        return pharmacyDAO.getRegimenNames();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenNamesByUuid(java.lang.String)
     */

    public RegimenNames getRegimenNamesByUuid(String one) {
        return pharmacyDAO.getRegimenNamesByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenNamesByName(java.lang.String)
     */

    public RegimenNames getRegimenNamesByName(String name) {
        return pharmacyDAO.getRegimenNamesByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenNamesListByUuid(java.lang.String)
     */

    public List<RegimenNames> getRegimenNamesListByUuid(String one) {
        return pharmacyDAO.getRegimenNamesListByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveIndicators(org.openmrs.module.pharmacy.model.Indicators)
     */

    public Indicators saveIndicators(Indicators indicators) {
        return pharmacyDAO.saveIndicators(indicators);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicators()
     */

    public List<Indicators> getIndicators() {
        return pharmacyDAO.getIndicators();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicatorsByUuid(java.lang.String)
     */

    public Indicators getIndicatorsByUuid(String uuid) {
        return pharmacyDAO.getIndicatorsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getIndicatorsListByUuid(java.lang.String)
     */

    public List<Indicators> getIndicatorsListByUuid(String one) {
        return pharmacyDAO.getIndicatorsListByUuid(one);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyEncounter(org.openmrs.module.pharmacy.model.PharmacyEncounter)
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter) {
        return pharmacyDAO.savePharmacyEncounter(pharmacyEncounter);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounter()
     */

    public List<PharmacyEncounter> getPharmacyEncounter() {
        return pharmacyDAO.getPharmacyEncounter();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterByUuid(java.lang.String)
     */

    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterListByUuid(java.lang.String)
     */

    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterListByUuid(uuid);

    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterListByUuid(java.lang.String)
     */

    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id) {
        return pharmacyDAO.getPharmacyEncounterListByPatientId(id);

    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyEncounterType(org.openmrs.module.pharmacy.model.PharmacyEncounterType)
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType) {
        return pharmacyDAO.savePharmacyEncounterType(pharmacyEncounterType);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterType()
     */

    public List<PharmacyEncounterType> getPharmacyEncounterType() {
        return pharmacyDAO.getPharmacyEncounterType();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterTypeByUuid(java.lang.String)
     */

    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterTypeByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyEncounterTypeListByUuid(java.lang.String)
     */

    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyEncounterTypeListByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore) {
        return pharmacyDAO.savePharmacyInventory(pharmacyStore);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyInventory(org.openmrs.module.pharmacy.model.PharmacyStore)
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore) {
        return pharmacyDAO.savePharmacyInventory(pharmacyStore);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventory()
     */

    public List<PharmacyStore> getPharmacyInventory() {
        return pharmacyDAO.getPharmacyInventory();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByUuid(java.lang.String)
     */

    public PharmacyStore getPharmacyInventoryByUuid(String uuid) {
        return pharmacyDAO.getPharmacyInventoryByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByCategory(PharmacyCategory)
     */

    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid) {
        return pharmacyDAO.getPharmacyInventoryByCategory(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryByDrugUuid(org.openmrs.Drug,String)      */

    public PharmacyStore getPharmacyInventoryByDrugUuid(Drug uuid,String location) {
        return pharmacyDAO.getPharmacyInventoryByDrugUuid(uuid,location);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyInventoryListByUuid(java.lang.String)
     */

    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid) {
        return pharmacyDAO.getPharmacyInventoryListByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugTransactions(org.openmrs.module.pharmacy.model.DrugTransactions)
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions) {
        return pharmacyDAO.saveDrugTransactions(drugTransactions);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugTransactions(org.openmrs.module.pharmacy.model.DrugTransactions)
     */

    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions) {
        return pharmacyDAO.saveDrugTransactions(drugTransactions);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactions()
     */

    public List<DrugTransactions> getDrugTransactions() {
        return pharmacyDAO.getDrugTransactions();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactionsByUuid(java.lang.String)
     */

    public DrugTransactions getDrugTransactionsByUuid(String uuid) {
        return pharmacyDAO.getDrugTransactionsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugTransactionsListByUuid(java.lang.String)
     */

    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid) {
        return pharmacyDAO.getDrugTransactionsListByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyObs(List)
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs) {
        return pharmacyDAO.savePharmacyObs(pharmacyObs);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyObs()
     */

    public List<PharmacyObs> getPharmacyObs() {
        return pharmacyDAO.getPharmacyObs();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyExtra(List)
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyExtra) {
        return pharmacyDAO.savePharmacyExtra(pharmacyExtra);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyExtra()
     */

    public List<PharmacyExtra> getPharmacyExtra() {
        return pharmacyDAO.getPharmacyExtra();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyExtraByUuid(java.lang.String)
     */

    public PharmacyExtra getPharmacyExtraByUuid(String uuid) {
        return pharmacyDAO.getPharmacyExtraByUuid(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyObsByUuid(java.lang.String)
     */

    public PharmacyObs getPharmacyObsByUuid(String uuid) {
        return pharmacyDAO.getPharmacyObsByUuid(uuid);
    }




    public List<PharmacyObs> getPharmacyObsByEncounterId(String id) {
        return pharmacyDAO.getPharmacyObsByEncounterId(id);

    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyOrders(List)
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders) {
        return pharmacyDAO.savePharmacyOrders(pharmacyOrders);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyOrders()
     */

    public List<PharmacyOrders> getPharmacyOrders() {
        return pharmacyDAO.getPharmacyOrders();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyOrdersByUuid(java.lang.String)
     */

    public PharmacyOrders getPharmacyOrdersByUuid(String uuid) {
        return pharmacyDAO.getPharmacyOrdersByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyOrdersByEncounterId(PharmacyEncounter)
     */

    public List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid) {
        return pharmacyDAO.getPharmacyOrdersByEncounterId(uuid);
    }



    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveRegimen(org.openmrs.module.pharmacy.model.Regimen)
     */

    public Regimen saveRegimen(Regimen regimen) {
        return pharmacyDAO.saveRegimen(regimen);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimen()
     */

    public List<Regimen> getRegimen() {
        return pharmacyDAO.getRegimen();
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimen(java.lang.String)
     */

    public List<Regimen> getRegimen(String category) {
        return pharmacyDAO.getRegimen(category);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenByUuid(java.lang.String)
     */

    public Regimen getRegimenByUuid(String uuid) {
        return pharmacyDAO.getRegimenByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenByName(java.lang.String)
     */

    public Regimen getRegimenByName(String uuid) {
        return pharmacyDAO.getRegimenByName(uuid);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenById(int)
     */

    public Regimen getRegimenById(int uuid) {
        return pharmacyDAO.getRegimenById(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveRegimenCombination(org.openmrs.module.pharmacy.model.RegimenCombination)
     */

    public RegimenCombination saveRegimenCombination(RegimenCombination Combination) {
        return pharmacyDAO.saveRegimenCombination(Combination);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenCombination()
     */

    public List<RegimenCombination> getRegimenCombination() {
        return pharmacyDAO.getRegimenCombination();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getRegimenCombinationByUuid(java.lang.String)
     */

    public RegimenCombination getRegimenCombinationByUuid(String uuid) {
        return pharmacyDAO.getRegimenCombinationByUuid(uuid);
    }




    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacySupplier(org.openmrs.module.pharmacy.model.PharmacySupplier)
     */
    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier) {
        return pharmacyDAO.savePharmacySupplier(pharmacySupplier);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplier()
     */
    public List<PharmacySupplier> getPharmacySupplier() {
        return pharmacyDAO.getPharmacySupplier();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByUuid(java.lang.String)
     */
    public PharmacySupplier getPharmacySupplierByUuid(String uuid) {
        return pharmacyDAO.getPharmacySupplierByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacySupplierByName(java.lang.String)
     */
    public PharmacySupplier getPharmacySupplierByName(String name) {
        return pharmacyDAO.getPharmacySupplierByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#savePharmacyTransactionTypes(org.openmrs.module.pharmacy.model.PharmacyTransactionTypes)
     */
    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType) {
        return pharmacyDAO.savePharmacyTransactionTypes(pharmacyTransactionType);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypes()
     */
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes() {
        return pharmacyDAO.getPharmacyTransactionTypes();
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByUuid(java.lang.String)
     */
    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid) {
        return pharmacyDAO.getPharmacyTransactionTypesByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.dao.PharmacyDAO#getPharmacyTransactionTypesByName(java.lang.String)
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name) {
        return pharmacyDAO.getPharmacyTransactionTypesByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */
    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier) {
        return pharmacyDAO.savePharmacyStoreIncoming(pharmacySupplier);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreIncoming(org.openmrs.module.pharmacy.model.PharmacyStoreIncoming)
     */
    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier) {

        return pharmacyDAO.savePharmacyStoreIncoming(pharmacySupplier);

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreIncoming()
     */
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming() {
        return pharmacyDAO.getPharmacyStoreIncoming();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreIncomingByUuid(java.lang.String)
     */
    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreIncomingByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */
    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing savePharmacyStoreOutgoing) {
        return pharmacyDAO.savePharmacyStoreOutgoing(savePharmacyStoreOutgoing);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreOutgoing(org.openmrs.module.pharmacy.model.PharmacyStoreOutgoing)
     */
    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> savePharmacyStoreOutgoing) {

        return pharmacyDAO.savePharmacyStoreOutgoing(savePharmacyStoreOutgoing);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreOutgoing()
     */
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing() {
        return pharmacyDAO.getPharmacyStoreOutgoing();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreOutgoingByUuid(java.lang.String)
     */
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreOutgoingByUuid(uuid);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#setPharmacyLocation(java.lang.String)
     */
    public boolean setPharmacyLocation(String loc) {
        pharmacyDAO.setPharmacyLocation(loc);
        return true;
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#setPharmacyLocationNull()
     */
    public void setPharmacyLocationNull() {
        pharmacyDAO.setPharmacyLocationNull();

    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocation()
     */

    public String getPharmacyLocation() {
        return pharmacyDAO.getPharmacyLocation();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugDispenseSettings(org.openmrs.module.pharmacy.model.DrugDispenseSettings)
     */
    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings) {
        return pharmacyDAO.saveDrugDispenseSettings(drugDispenseSettings);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettings()
     */

    public List<DrugDispenseSettings> getDrugDispenseSettings() {
        return pharmacyDAO.getDrugDispenseSettings();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByUuid(java.lang.String)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid) {
        return pharmacyDAO.getDrugDispenseSettingsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByBatch(int)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name) {
        return pharmacyDAO.getDrugDispenseSettingsByBatch(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByDrugId(Drug)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id) {
        return pharmacyDAO.getDrugDispenseSettingsByDrugId(id);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugDispenseSettingsByLocation(org.openmrs.module.pharmacy.model.PharmacyLocations) (Drug)
     */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(PharmacyLocations id) {
        return pharmacyDAO.getDrugDispenseSettingsByLocation(id);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyCategory(org.openmrs.module.pharmacy.model.PharmacyCategory)
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory) {
        return pharmacyDAO.savePharmacyCategory(pharmacyCategory);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategory()
     */

    public List<PharmacyCategory> getPharmacyCategory() {
        return pharmacyDAO.getPharmacyCategory();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategoryByUuid(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid) {
        return pharmacyDAO.getPharmacyCategoryByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyCategoryByName(java.lang.String)
     */

    public PharmacyCategory getPharmacyCategoryByName(String name) {
        return pharmacyDAO.getPharmacyCategoryByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyGeneralVariables(org.openmrs.module.pharmacy.model.PharmacyGeneralVariables)
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables) {
        return pharmacyDAO.savePharmacyGeneralVariables(pharmacyGeneralVariables);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariables()
     */

    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables() {
        return pharmacyDAO.getPharmacyGeneralVariables();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariablesByUuid(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid) {
        return pharmacyDAO.getPharmacyGeneralVariablesByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyGeneralVariablesByName(java.lang.String)
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name) {
        return pharmacyDAO.getPharmacyGeneralVariablesByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyLocations(org.openmrs.module.pharmacy.model.PharmacyLocations)
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations) {
        return pharmacyDAO.savePharmacyLocations(pharmacyLocations);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocations()
     */

    public List<PharmacyLocations> getPharmacyLocations() {
        return pharmacyDAO.getPharmacyLocations();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationsByUuid(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid) {
        return pharmacyDAO.getPharmacyLocationsByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationsByName(java.lang.String)
     */

    public PharmacyLocations getPharmacyLocationsByName(String name) {
        return pharmacyDAO.getPharmacyLocationsByName(name);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyLocationUsers(org.openmrs.module.pharmacy.model.PharmacyLocationUsers)
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers) {
        return pharmacyDAO.savePharmacyLocationUsers(pharmacyLocationUsers);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsers()
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsers() {
        return pharmacyDAO.getPharmacyLocationUsers();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsersByUuid(java.lang.String)
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid) {
        return pharmacyDAO.getPharmacyLocationUsersByUuid(uuid);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyLocationUsersByUserName(java.lang.String)
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name) {
        return pharmacyDAO.getPharmacyLocationUsersByUserName(name);
    }
    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#saveDrugMaxMin(org.openmrs.module.pharmacy.model.DrugMaxMin)
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin) {
        return pharmacyDAO.saveDrugMaxMin(drugMaxMin);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMin()
     */

    public List<DrugMaxMin> getDrugMaxMin() {
        return pharmacyDAO.getDrugMaxMin();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMinByUuid(java.lang.String)
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid) {
        return pharmacyDAO.getDrugMaxMinByUuid(uuid);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getDrugMaxMinByDrug(org.openmrs.Drug)
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name) {
        return pharmacyDAO.getDrugMaxMinByDrug(name);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved savePharmacyStoreApproved) {
        return pharmacyDAO.savePharmacyStoreApproved(savePharmacyStoreApproved);
    }


    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#savePharmacyStoreApproved(org.openmrs.module.pharmacy.model.PharmacyStoreApproved)
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> savePharmacyStoreApproved) {
        return pharmacyDAO.savePharmacyStoreApproved(savePharmacyStoreApproved);
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreApproved()
     */

    public List<PharmacyStoreApproved> getPharmacyStoreApproved() {
        return pharmacyDAO.getPharmacyStoreApproved();
    }

    /**
     * @see org.openmrs.module.pharmacy.service.PharmacyService#getPharmacyStoreApprovedByUuid(java.lang.String)
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid) {
        return pharmacyDAO.getPharmacyStoreApprovedByUuid(uuid);
    }

}
