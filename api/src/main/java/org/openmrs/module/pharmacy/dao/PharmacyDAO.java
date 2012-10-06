/**
 *
 */
package org.openmrs.module.pharmacy.dao;

import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.module.pharmacy.model.*;

import java.util.List;


/**
 * @author Ampath Developers
 */
public interface PharmacyDAO {

    /**
     * save Doses
     *
     * @param doses to be saved
     * @return saved doses object
     */

    public Dose saveDoses(Dose doses);

    /**
     * @return all the doses
     */
    public List<Dose> getDoses();

    /**
     * @return dose object by uuid
     */

    public Dose getDosesByUuid(String uuid);






    /**
     * save LocationSetter
     *
     * @param doses to be saved
     * @return LocationSetter  object
     */

    public boolean setPharmacyLocation(String doses);

    /**
     * save LocationSetter
     *
     * @return LocationSetter  object
     */

    public boolean setPharmacyLocationNull();

    /**
     * @return dose object by uuid
     */

    public String getPharmacyLocation();


    /**
     * save PharmacyStoreIncoming
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreIncoming object
     */

    public PharmacyStoreIncoming savePharmacyStoreIncoming(PharmacyStoreIncoming pharmacySupplier);
    /**
     * save PharmacyStoreIncoming
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreIncoming object
     */

    public boolean savePharmacyStoreIncoming(List<PharmacyStoreIncoming> pharmacySupplier);

    /**
     * @return all the PharmacyStoreIncoming
     */
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming();

    /**
     * @return PharmacyStoreIncoming object by uuid
     */

    public PharmacyStoreIncoming getPharmacyStoreIncomingByUuid(String uuid);



    /**
     * save PharmacyStoreOutgoing
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreOutgoing object
     */

    public PharmacyStoreOutgoing savePharmacyStoreOutgoing(PharmacyStoreOutgoing pharmacySupplier);
    /**
     * save PharmacyStoreOutgoing
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreOutgoing object
     */

    public boolean savePharmacyStoreOutgoing(List<PharmacyStoreOutgoing> pharmacySupplier);

    /**
     * @return all the PharmacyStoreOutgoing
     */
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing();

    /**
     * @return PharmacyStoreOutgoing object by uuid
     */

    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid);

    /**
     * save PharmacyStoreApproved
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreApproved object
     */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacySupplier);
    /**
     * save PharmacyStoreApproved
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacyStoreApproved object
     */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacySupplier);

    /**
     * @return all the PharmacyStoreApproved
     */
    public List<PharmacyStoreApproved> getPharmacyStoreApproved();

    /**
     * @return PharmacyStoreApproved object by uuid
     */

    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid);

    /**
     * save DrugDispenseSettings
     *
     * @param drugDispenseSettings to be saved
     * @return saved DrugDispenseSettings object
     */



    public DrugDispenseSettings saveDrugDispenseSettings(DrugDispenseSettings drugDispenseSettings);

    /**
     * @return all the DrugDispenseSettings
     */
    public List<DrugDispenseSettings> getDrugDispenseSettings();

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int name);

    /**
     * @return DrugDispenseSettings object by drug id
     */

    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug name);
    /**
     * @return DrugDispenseSettings object by drug id
     */

    public DrugDispenseSettings getDrugDispenseSettingsByLocation(PharmacyLocations name);



    /**
     * save PharmacySupplier
     *
     * @param pharmacySupplier to be saved
     * @return saved PharmacySupplier object
     */

    public PharmacySupplier savePharmacySupplier(PharmacySupplier pharmacySupplier);

    /**
     * @return all the PharmacySupplier
     */
    public List<PharmacySupplier> getPharmacySupplier();

    /**
     * @return PharmacySupplier object by uuid
     */

    public PharmacySupplier getPharmacySupplierByUuid(String uuid);

    /**
     * @return DrugFormulation object by uuid
     */

    public PharmacySupplier getPharmacySupplierByName(String name);

    /**
     * save PharmacyCategory
     *
     * @param pharmacyCategory to be saved
     * @return saved PharmacyCategory object
     */

    public PharmacyCategory savePharmacyCategory(PharmacyCategory pharmacyCategory);

    /**
     * @return all the PharmacyCategory
     */
    public List<PharmacyCategory> getPharmacyCategory();

    /**
     * @return PharmacyCategory object by uuid
     */

    public PharmacyCategory getPharmacyCategoryByUuid(String uuid);

    /**
     * @return PharmacyCategory object by name
     */

    public PharmacyCategory getPharmacyCategoryByName(String name);


    /**
     * save PharmacyGeneralVariables
     *
     * @param pharmacyGeneralVariables to be saved
     * @return saved PharmacyGeneralVariables object
     */

    public PharmacyGeneralVariables savePharmacyGeneralVariables(PharmacyGeneralVariables pharmacyGeneralVariables);

    /**
     * @return all the PharmacyGeneralVariables
     */
    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables();

    /**
     * @return PharmacyGeneralVariables object by uuid
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid);

    /**
     * @return PharmacyGeneralVariables object by name
     */

    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name);


    /**
     * save PharmacyTransactionTypes
     *
     * @param pharmacyTransactionType to be saved
     * @return saved PharmacyTransactionTypes object
     */

    public PharmacyTransactionTypes savePharmacyTransactionTypes(PharmacyTransactionTypes pharmacyTransactionType);

    /**
     * @return all the PharmacyTransactionType
     */
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes();

    /**
     * @return PharmacyTransactionTypes object by uuid
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid);

    /**
     * @return PharmacyTransactionTypes object by uuid
     */

    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name);

    /**
     * save PharmacyLocations
     *
     * @param pharmacyLocations to be saved
     * @return saved PharmacyLocations object
     */

    public PharmacyLocations savePharmacyLocations(PharmacyLocations pharmacyLocations);

    /**
     * @return all the PharmacyLocations
     */
    public List<PharmacyLocations> getPharmacyLocations();

    /**
     * @return PharmacyLocations object by uuid
     */

    public PharmacyLocations getPharmacyLocationsByUuid(String uuid);

    /**
     * @return PharmacyLocations object by uuid
     */

    public PharmacyLocations getPharmacyLocationsByName(String name);


    /**
     * save PharmacyLocationUsers
     *
     * @param pharmacyLocationUsers to be saved
     * @return saved PharmacyLocationUsers object
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers);

    /**
     * @return all the PharmacyLocationUsers
     */
    public List<PharmacyLocationUsers> getPharmacyLocationUsers();

    /**
     * @return PharmacyLocationUsers object by uuid
     */

    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid);


    /**
     * @return PharmacyLocationUsers object by uuid
     */

    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name);

    /**
     * save DrugMaxMin
     *
     * @param drugMaxMin to be saved
     * @return saved DrugMaxMin object
     */

    public DrugMaxMin saveDrugMaxMin(DrugMaxMin drugMaxMin);

    /**
     * @return all the DrugMaxMin
     */
    public List<DrugMaxMin> getDrugMaxMin();

    /**
     * @return PharmacyLocations object by uuid
     */

    public DrugMaxMin getDrugMaxMinByUuid(String uuid);

    /**
     * @return DrugMaxMin object by uuid
     */

    public DrugMaxMin getDrugMaxMinByDrug(Drug name);





    /**
     * save grugFormulation
     *
     * @param drugFormulation to be saved
     * @return saved drugFormulation object
     */

    public DrugFormulation saveDrugFormulation(DrugFormulation drugFormulation);

    /**
     * @return all the DrugFormulation
     */
    public List<DrugFormulation> getDrugFormulation();

    /**
     * @return DrugFormulation object by uuid
     */

    public DrugFormulation getDrugFormulationByUuid(String uuid);

    /**
     * @return DrugFormulation object by uuid
     */

    public DrugFormulation getDrugFormulationByName(String name);

    /**
     * save drugName
     *
     * @param drugName to be saved
     * @return saved drugName object
     */

    public DrugName saveDrugName(DrugName drugName);

    /**
     * @return all the drugName
     */
    public List<DrugName> getDrugName();

    /**
     * @return drugName object by uuid
     */

    public DrugName getDrugNameByUuid(String uuid);

    /**
     * @return drugName object by uuid
     */

    public DrugName getDrugNameByName(String name);

    /**
     * save DrugFrequency
     *
     * @param drugFrequency to be saved
     * @return saved drugFrequency object
     */

    public DrugFrequency saveDrugFrequency(DrugFrequency drugFrequency);

    /**
     * @return all the DrugFrequency
     */
    public List<DrugFrequency> getDrugFrequency();

    /**
     * @return one the DeliveryAttribute object
     */
    public DrugFrequency getDrugFrequencyByUuid(String uuid);

    /**
     * @return one the DeliveryAttribute object by name
     */
    public DrugFrequency getDrugFrequencyByName(String name);

    /**
     * save drugs
     *
     * @param drug to be saved
     * @return saved drugs object
     */

    public Drugs saveDrugs(Drugs drug);

    /**
     * @return all the drugs
     */
    public List<Drugs> getDrugs();

    /**
     * @return drugs object by uuid
     */
    public Drugs getDrugsByUuid(String uuid);

    /**
     * @return drugs object by name
     */
    public Drugs getDrugsByName(String name);

    /**
     * save drugStrength
     *
     * @param drugStrength to be saved
     * @return saved drugStrength object
     */

    public DrugStrength saveDrugStrength(DrugStrength drugStrength);

    /**
     * @return all the drugStrength
     */
    public List<DrugStrength> getDrugStrength();

    /**
     * @return drugStrength by uuid
     */
    public DrugStrength getDrugStrengthByUuid(String one);

    /**
     * @return drugStrength by name
     */
    public DrugStrength getDrugStrengthByName(String name);

    /**
     * save DrugUnits
     *
     * @param drugUnits to be saved
     * @return DrugUnits object
     */

    public DrugUnits saveDrugUnits(DrugUnits drugUnits);

    /**
     * @return all the DrugUnits
     */
    public List<DrugUnits> getDrugUnits();

    /**
     * @return one of the DrugUnits by uuid
     */
    public DrugUnits getDrugUnitsByUuid(String one);

    /**
     * @return one of the DrugUnits by name
     */
    public DrugUnits getDrugUnitsByName(String name);

    /**
     * @return list of the DrugUnits by uuis
     */
    public List<DrugUnits> getDrugUnitsListByUuid(String one);

    /**
     * save RegimenNames
     *
     * @param regimenNames to be saved
     * @return RegimenNames object
     */

    public RegimenNames saveRegimenNames(RegimenNames regimenNames);

    /**
     * @return all the RegimenNames
     */
    public List<RegimenNames> getRegimenNames();

    /**
     * @return one of the RegimenNames by uuid
     */
    public RegimenNames getRegimenNamesByUuid(String one);

    /**
     * @return one of the RegimenNames by name
     */
    public RegimenNames getRegimenNamesByName(String name);

    /**
     * @return list of the RegimenNames by uuis
     */
    public List<RegimenNames> getRegimenNamesListByUuid(String one);

    /**
     * save Indicators
     *
     * @param indicators to be saved
     * @return saved indicators object
     */

    public Indicators saveIndicators(Indicators indicators);

    /**
     * @return all the inidicators
     */
    public List<Indicators> getIndicators();

    /**
     * @return one indicators object by uuid
     */
    public Indicators getIndicatorsByUuid(String uuid);

    /**
     * @return list indicators object by uuid
     */
    public List<Indicators> getIndicatorsListByUuid(String one);

    /**
     * save pharmacyEncounter
     *
     * @param pharmacyEncounter to be saved
     * @return saved pharmacyEncounter object
     */

    public PharmacyEncounter savePharmacyEncounter(PharmacyEncounter pharmacyEncounter);

    /**
     * @return all the pharmacyEncounter
     */
    public List<PharmacyEncounter> getPharmacyEncounter();

    /**
     * @return one pharmacyEncounter object by uuid
     *
     */
    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid);


    public List<PharmacyEncounter> getPharmacyEncounterListByPatientId(Person id);
    /**
     * save pharmacyEncounterType
     *
     * @param pharmacyEncounterType to be saved
     * @return saved pharmacyEncounter object
     */

    public PharmacyEncounterType savePharmacyEncounterType(PharmacyEncounterType pharmacyEncounterType);

    /**
     * @return all the pharmacyEncounterType
     */
    public List<PharmacyEncounterType> getPharmacyEncounterType();

    /**
     * @return one pharmacyEncounterType object by uuid
     */
    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid);

    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore);
    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore);

    /**
     * @return all the pharmacyInventory
     */
    public List<PharmacyStore> getPharmacyInventory();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByUuid(String uuid);

    /* @return one pharmacyInventory object by Category
    */
    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid);

    /**
     * @return list pharmacyInventory object by uuid
     */
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid);
    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByDrugUuid(Drug uuid,String location);

    /**
     * save drugTransactions
     *
     * @param drugTransactions to be saved
     * @return saved drugTransactions object
     */

    public DrugTransactions saveDrugTransactions(DrugTransactions drugTransactions);
    /**
     * save drugTransactions
     *
     * @param drugTransactions to be saved
     * @return saved drugTransactions object
     */

    public boolean saveDrugTransactions(List<DrugTransactions> drugTransactions);

    /**
     * @return all the DrugTransaction
     */
    public List<DrugTransactions> getDrugTransactions();

    /**
     * @return one DrugTransaction object by uuid
     */
    public DrugTransactions getDrugTransactionsByUuid(String uuid);

    /**
     * @return list DrugTransactions object by uuid
     */
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid);



    /**
     * save pharmacyExtra
     *
     * @param pharmacyObs to be saved
     * @return saved pharmacyExtra object
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyObs);

    /**
     * @return all the pharmacyExtra
     */
    public List<PharmacyExtra> getPharmacyExtra();

    /**
     * @return one pharmacyExtra object by uuid
     */
    public PharmacyExtra getPharmacyExtraByUuid(String uuid);


    /**
     * save pharmacyObs
     *
     * @param pharmacyObs to be saved
     * @return saved phamacyObs object
     */

    public boolean savePharmacyObs(List<PharmacyObs> pharmacyObs);

    /**
     * @return all the PharmacyObs
     */
    public List<PharmacyObs> getPharmacyObs();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyObs getPharmacyObsByUuid(String uuid);

    /**
     * @return one pharmacyInventory object by uuid
     */
    public List<PharmacyObs> getPharmacyObsByEncounterId(String uuid);

    /**
     * save pharmacyOrders
     *
     * @param pharmacyOrders to be saved
     * @return saved pharmacyOrders object
     */

    public boolean savePharmacyOrders(List<PharmacyOrders>   pharmacyOrders);

    /**
     * @return all the pharmacyOrders
     */
    public List<PharmacyOrders> getPharmacyOrders();

    /**
     * @return one pharmacyOrders object by uuid
     */
    public PharmacyOrders getPharmacyOrdersByUuid(String uuid);

    /**
     * @return one pharmacyOrders object by  encounter uuid
     */
    public  List<PharmacyOrders> getPharmacyOrdersByEncounterId(PharmacyEncounter uuid);

    /**
     * save Regimen
     *
     * @param regimen to be saved
     * @return saved regimen object
     */

    public Regimen saveRegimen(Regimen regimen);

    /**
     * @return all the regimen
     */
    public List<Regimen> getRegimen();
    /**
     * @return all the regimen
     */
    public List<Regimen> getRegimen(String category);

    /**
     * @return one regimen object by uuid
     */
    public Regimen getRegimenByUuid(String uuid);

    /**
     * @return one of the Regimen by name
     */
    public Regimen getRegimenByName(String name);
    /**
     * @return one of the Regimen by name
     */
    public Regimen getRegimenById(int name);

    /**
     * save RegimenCombination
     *
     * @param combination to be saved
     * @return saved regimenCombination object
     */

    public RegimenCombination saveRegimenCombination(RegimenCombination combination);

    /**
     * @return all the regimenCombination
     */
    public List<RegimenCombination> getRegimenCombination();

    /**
     * @return all the regimenCombination
     */
    public List<RegimenCombination> getRegimenCombination(String name);

    /**
     * @return one regimenCombination object by uuid
     */
    public RegimenCombination getRegimenCombinationByUuid(String uuid);

}
