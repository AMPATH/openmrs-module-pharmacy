/**
 *
 */
package org.openmrs.module.pharmacy.service;

import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.pharmacy.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 *
 * @author Ampath Developers
 */
@Transactional
public interface PharmacyService extends OpenmrsService {

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
     *d
     * @return LocationSetter  null
     */
    public void setPharmacyLocationNull();

    /**
     * @return location object
     */


    @Transactional(readOnly=true)
    public String getPharmacyLocation();

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
    @Transactional(readOnly=true)
    public Dose getDosesByUuid(String uuid);

    /* save PharmacyStoreApproved
      *
      * @param PharmacyStoreApproved to be saved
      * @return saved PharmacyStoreApproved object
      */

    public PharmacyStoreApproved savePharmacyStoreApproved(PharmacyStoreApproved pharmacySupplier);

    /* save PharmacyStoreApproved
    *
    * @param PharmacyStoreApproved to be saved
    * @return saved PharmacyStoreApproved object
    */

    public boolean savePharmacyStoreApproved(List<PharmacyStoreApproved> pharmacySupplier);

    /**
     * @return all the PharmacyStoreApproved
     */
    @Transactional(readOnly=true)
    public List<PharmacyStoreApproved> getPharmacyStoreApproved();

    /**
     * @return PharmacyStoreApproved object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStoreApproved getPharmacyStoreApprovedByUuid(String uuid);



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
    @Transactional(readOnly=true)
    public List<DrugFormulation> getDrugFormulation();

    /**
     * @return DrugFormulation object by uuid
     */
    @Transactional(readOnly=true)
    public DrugFormulation getDrugFormulationByUuid(String uuid);

    /**
     * @return DrugFormulation object by name
     */
    @Transactional(readOnly=true)
    public DrugFormulation getDrugFormulationByName(String name);



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
    @Transactional(readOnly=true)
    public List<PharmacyLocations> getPharmacyLocations();

    /**
     * @return PharmacyLocations object by uuid
     */

    @Transactional(readOnly=true)
    public PharmacyLocations getPharmacyLocationsByUuid(String uuid);

    /**
     * @return PharmacyLocations object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyLocations getPharmacyLocationsByName(String name);


    /**
     * save PharmacyLocations
     *
     * @param pharmacyLocationUsers to be saved
     * @return saved PharmacyLocations object
     */

    public PharmacyLocationUsers savePharmacyLocationUsers(PharmacyLocationUsers pharmacyLocationUsers);

    /**
     * @return all the PharmacyLocations
     */
    @Transactional(readOnly=true)
    public List<PharmacyLocationUsers> getPharmacyLocationUsers();

    /**
     * @return PharmacyLocationUsers object by uuid
     */

    @Transactional(readOnly=true)
    public PharmacyLocationUsers getPharmacyLocationUsersByUuid(String uuid);



    /**
     * @return PharmacyLocationUsers object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyLocationUsers> getPharmacyLocationUsersByUserName(String name);











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
    @Transactional(readOnly=true)
    public List<DrugName> getDrugName();

    /**
     * @return drugName object by uuid
     */
    @Transactional(readOnly=true)
    public DrugName getDrugNameByUuid(String uuid);

    /**
     * @return drugName object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<DrugFrequency> getDrugFrequency();

    /**
     * @return one the DeliveryAttribute object
     */
    @Transactional(readOnly=true)
    public DrugFrequency getDrugFrequencyByUuid(String uuid);

    /**
     * @return one the DeliveryAttribute object by name
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<Drugs> getDrugs();

    /**
     * @return drugs object by uuid
     */
    @Transactional(readOnly=true)
    public Drugs getDrugsByUuid(String name);

    /**
     * @return drugs object by name
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<DrugStrength> getDrugStrength();

    /**
     * @return drugStrength by uuid
     */
    @Transactional(readOnly=true)
    public DrugStrength getDrugStrengthByUuid(String one);

    /**
     * @return drugStrength by name
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<DrugUnits> getDrugUnits();

    /**
     * @return one of the DrugUnits by uuid
     */
    @Transactional(readOnly=true)
    public DrugUnits getDrugUnitsByUuid(String one);

    /**
     * @return one of the DrugUnits by uuid
     */
    @Transactional(readOnly=true)
    public DrugUnits getDrugUnitsByName(String name);

    /**
     * @return list of the DrugUnits by uuis
     */
    @Transactional(readOnly=true)
    public List<DrugUnits> getDrugUnitsListByUuid(String one);

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
    @Transactional(readOnly=true)
    public List<DrugTransactions> getDrugTransactions();

    /**
     * @return one DrugTransaction object by uuid
     */
    @Transactional(readOnly=true)
    public DrugTransactions getDrugTransactionsByUuid(String uuid);

    /**
     * @return list DrugTransactions object by uuid
     */
    @Transactional(readOnly=true)
    public List<DrugTransactions> getDrugTransactionsListByUuid(String uuid);

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
    @Transactional(readOnly=true)
    public List<RegimenNames> getRegimenNames();

    /**
     * @return one of the RegimenNames by uuid
     */
    @Transactional(readOnly=true)
    public RegimenNames getRegimenNamesByUuid(String one);

    /**
     * @return one of the RegimenNames by name
     */
    @Transactional(readOnly=true)
    public RegimenNames getRegimenNamesByName(String name);

    /**
     * @return list of the RegimenNames by uuis
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<Indicators> getIndicators();

    /**
     * @return one indicators object by uuid
     */
    @Transactional(readOnly=true)
    public Indicators getIndicatorsByUuid(String uuid);

    /**
     * @return list indicators object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getPharmacyEncounter();

    /**
     * @return one pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyEncounter getPharmacyEncounterByUuid(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounter> getPharmacyEncounterListByUuid(String uuid);

    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyEncounterType> getPharmacyEncounterType();

    /**
     * @return one pharmacyEncounterType object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyEncounterType getPharmacyEncounterTypeByUuid(String uuid);

    /**
     * @return list pharmacyEncounter object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyEncounterType> getPharmacyEncounterTypeListByUuid(String uuid);

    /**
     * save pharmacyInventory
     *
     * @param pharmacyStore to be saved
     * @return saved pharmacyInventory object
     */

    public PharmacyStore savePharmacyInventory(PharmacyStore pharmacyStore);
    /**
     * save pharmacyOrders
     *
     * @param pharmacyStore to be saved
     * @return saved PharmacyStore object
     */

    public boolean savePharmacyInventory(List<PharmacyStore> pharmacyStore);

    /**
     * @return all the pharmacyInventory
     */
    @Transactional(readOnly=true)
    public List<PharmacyStore> getPharmacyInventory();

    /**
     * @return one pharmacyInventory object by uuid
     */
    public PharmacyStore getPharmacyInventoryByUuid(String uuid);

    /**
     * @return one pharmacyInventory object by uuid
     */
    public List<PharmacyStore> getPharmacyInventoryByCategory(PharmacyCategory uuid);

    /**
     * @return list pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public List<PharmacyStore> getPharmacyInventoryListByUuid(String uuid);

    /**
     * save pharmacyExtra
     *
     * @param pharmacyObs to be saved
     * @return saved pharmacyExtra object
     */

    public boolean savePharmacyExtra(List<PharmacyExtra> pharmacyObs);

    /**
     * @return all the PharmacyExtra
     */
    @Transactional(readOnly=true)
    public List<PharmacyExtra> getPharmacyExtra();

    /**
     * @return one PharmacyExtra object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyObs> getPharmacyObs();

    /**
     * @return one pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyObs getPharmacyObsByUuid(String uuid);


    /**
     * @return one pharmacyInventory object by EncounterId
     */
    @Transactional(readOnly=true)
    public List<PharmacyObs> getPharmacyObsByEncounterId(String id);
    /**
     * @return one pharmacyInventory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStore getPharmacyInventoryByDrugUuid(Drug uuid,String location);

    /**
     * save pharmacyOrders
     *
     * @param pharmacyOrders to be saved
     * @return saved pharmacyOrders object
     */

    public boolean savePharmacyOrders(List<PharmacyOrders> pharmacyOrders);

    /**
     * @return all the pharmacyOrders
     */
    @Transactional(readOnly=true)
    public List<PharmacyOrders> getPharmacyOrders();

    /**
     * @return one pharmacyOrders object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyOrders getPharmacyOrdersByUuid(String uuid);

    /**
     * @return one pharmacyOrders object  by Encounter uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<Regimen> getRegimen();
    /**
     * @return all the regimen
     */
    @Transactional(readOnly=true)
    public List<Regimen> getRegimen(String category);

    /**
     * @return one regimen object by uuid
     */
    @Transactional(readOnly=true)
    public Regimen getRegimenByUuid(String uuid);

    /**
     * @return one regimen object by uuid
     */
    @Transactional(readOnly=true)
    public Regimen getRegimenByName(String uuid);


    /**
     * @return one regimen object by id
     */
    @Transactional(readOnly=true)
    public Regimen getRegimenById(int uuid);

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
    @Transactional(readOnly=true)
    public List<RegimenCombination> getRegimenCombination();

    /**
     * @return one regimenCombination object by uuid
     */
    @Transactional(readOnly=true)
    public RegimenCombination getRegimenCombinationByUuid(String uuid);





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
    @Transactional(readOnly=true)
    public List<DrugDispenseSettings> getDrugDispenseSettings();

    /**
     * @return DrugDispenseSettings object by uuid
     */

    public DrugDispenseSettings getDrugDispenseSettingsByUuid(String uuid);

    /**
     * @return DrugDispenseSettings object by uuid
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByBatch(int id);


    /**
     * @return DrugDispenseSettings object by drug id
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByDrugId(Drug id);

    /**
     * @return DrugDispenseSettings object by drug id
     */
    @Transactional(readOnly=true)
    public DrugDispenseSettings getDrugDispenseSettingsByLocation(PharmacyLocations id);

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
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyStoreIncoming> getPharmacyStoreIncoming();

    /**
     * @return PharmacyStoreIncoming object by uuid
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyStoreOutgoing> getPharmacyStoreOutgoing();

    /**
     * @return PharmacyStoreOutgoing object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyStoreOutgoing getPharmacyStoreOutgoingByUuid(String uuid);




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
    @Transactional(readOnly=true)
    public List<PharmacyTransactionTypes> getPharmacyTransactionTypes();

    /**
     * @return PharmacyTransactionTypes object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyTransactionTypes getPharmacyTransactionTypesByUuid(String uuid);

    /**
     * @return PharmacyTransactionTypes object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyTransactionTypes getPharmacyTransactionTypesByName(String name);

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
    @Transactional(readOnly=true)
    public List<PharmacyCategory> getPharmacyCategory();

    /**
     * @return PharmacyCategory object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyCategory getPharmacyCategoryByUuid(String uuid);

    /**
     * @return PharmacyCategory object by name
     */
    @Transactional(readOnly=true)
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
    @Transactional(readOnly=true)
    public List<PharmacyGeneralVariables> getPharmacyGeneralVariables();

    /**
     * @return PharmacyGeneralVariables object by uuid
     */
    @Transactional(readOnly=true)
    public PharmacyGeneralVariables getPharmacyGeneralVariablesByUuid(String uuid);

    /**
     * @return PharmacyGeneralVariables object by name
     */
    @Transactional(readOnly=true)
    public PharmacyGeneralVariables getPharmacyGeneralVariablesByName(String name);



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
    @Transactional(readOnly=true)
    public List<DrugMaxMin> getDrugMaxMin();

    /**
     * @return PharmacyLocations object by uuid
     */
    @Transactional(readOnly=true)
    public DrugMaxMin getDrugMaxMinByUuid(String uuid);

    /**
     * @return DrugMaxMin object by uuid
     */
    @Transactional(readOnly=true)
    public DrugMaxMin getDrugMaxMinByDrug(Drug name);

}
