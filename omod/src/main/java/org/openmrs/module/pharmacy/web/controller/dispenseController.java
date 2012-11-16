package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.Drug;
import org.openmrs.Person;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.EncounterService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
public class dispenseController {

    private static final Log log = LogFactory.getLog(dispenseController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    public EncounterService patientService;

    private boolean found = false;

    private JSONArray drugNamess;

    private String formulation;

    private List<PharmacyEncounter> list = null;

    private int size = 0;

    private UserService usersService;

    private List<PharmacyOrders> listOrders;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private int psize, sizeList;

    private List<PharmacyOrders> listDrugs;

    private int listDrugsSize;
    private ContainerFactory containerFactory;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int sizePharmacyLocationUsers;
    private List<PharmacyObs> listObs;

    private List<Regimen> listRegimen;
    private int sizeRegimen;

    private JSONObject jsonObject, jsonObject1;
    private JSONArray jsonArray, jsonArray1, jsonArray2;
    private PharmacyEncounter pharmacyEncounterByUuid;
    private Iterator iterator;
    private Person person;
    private List<User> userList;

    private PharmacyTransactionTypes pharmacyTransactionTypes;
    private List<RegimenCombination> regimenCombination;
    private int size1;


    /**
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/dispense")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


        // declare parameters
        String locationVal = null;
        String patientId = request.getParameter("patientID");//encounterDetails
        String encounterDetails = request.getParameter("encounterDetails");//
        String users = request.getParameter("users");//
        String pId = request.getParameter("Pid");//
        String age = request.getParameter("age");//
        String drug = request.getParameter("drug");
        String passUserId = request.getParameter("passUserId");
        String query = request.getParameter("q");
        String drugs = request.getParameter("drugs");
        String values = request.getParameter("values");
        String regimen = request.getParameter("regimen");
        String filter = request.getParameter("filter");
        String encounter = request.getParameter("encounter");
        String pen = request.getParameter("Pen");
        String drugID = request.getParameter("drugCheck");
        String totVal = request.getParameter("total");

        //get openmrs variables

        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();


        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizePharmacyLocationUsers = pharmacyLocationUsersByUserName.size();


        if (sizePharmacyLocationUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizePharmacyLocationUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }


        if (patientId != null) {
            list = service.getPharmacyEncounter();
            size = list.size();
        }


        listRegimen = service.getRegimen(filter);
        sizeRegimen = listRegimen.size();


        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (encounterDetails != null)

            {
                pharmacyEncounterByUuid = service.getPharmacyEncounterByUuid(encounterDetails);
                // get encounter details for a patient
                if (pharmacyEncounterByUuid.getLocation().getName().equalsIgnoreCase(locationVal))


                {
                    jsonArray = new JSONArray();


                    jsonArray.put("Encounter Details");
                    jsonArray.put("Patient name:" + pharmacyEncounterByUuid.getPerson().getNames());
                    jsonArray.put("Encounter date:" + pharmacyEncounterByUuid.getDateTime());
                    jsonArray.put("Encounter location:" + pharmacyEncounterByUuid.getLocation().getName());
                    jsonArray.put("Encounter Creator:" + pharmacyEncounterByUuid.getCreator().getName());

                    listObs = service.getPharmacyObs();


                    //get obs details
                    jsonArray.put("Obs Details");

                    for (int y = 0; y < listObs.size(); y++) {
                        if (listObs.get(y).getPharmacyEncounter().getUuid().contentEquals(pharmacyEncounterByUuid.getUuid())) {

                            jsonArray.put("Question:"
                                    + Context.getConceptService().getConcept(listObs.get(y).getConcept()).getDisplayString());
                            jsonArray.put("Given value:" + listObs.get(y).getValueNumeric());
                        }

                    }

                    //get drug details that was given to that patient

                    jsonArray.put("Drug Details");
                    listOrders = service.getPharmacyOrders();
                    for (int y = 0; y < listOrders.size(); y++) {

                        if (listOrders.get(y).getPharmacyEncounter().getUuid().contentEquals(pharmacyEncounterByUuid.getUuid())) {


                            if (Context.getConceptService().getConcept(listOrders.get(y).getConcept()) != null) {

                                jsonArray.put("Drug given :" + Context.getConceptService().getConcept(listOrders.get(y).getConcept()).getDisplayString());
                                jsonArray.put("Drug given :" + listOrders.get(y).getQuantity());
                                jsonArray.put("Days given :" + listOrders.get(y).getMonthsNo());

                                jsonArray.put("Next visit date :" + listOrders.get(y).getNextVisitDate().toString().substring(0, 10));


                            }

                        }
                    }

                    response.getWriter().print(jsonArray);
                }
            } else if (patientId != null) {


                for (int i = 0; i < size; i++) {

                    if (list.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {

                        jsonArray1 = new JSONArray();
                        jsonArray1 = getEncounterSummary(list, i, patientId);

                        if (jsonArray1 != null)
                            jsonObject.accumulate("aaData", jsonArray1);
                    }
                }

                if (!jsonObject.has("aaData")) {
                    drugNamess = new JSONArray();

                    drugNamess.put("None");
                    drugNamess.put("None");

                    drugNamess.put("None");
                    drugNamess.put("None");
                    drugNamess.put("None");

                    jsonObject.accumulate("aaData", drugNamess);
                }

                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);

                //reset
                list = null;
                size = 0;
            } else if (pId != null) {


                //get the patientName to display
                jsonArray = new JSONArray();

                jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(pId)).getGivenName());

                response.getWriter().print(jsonArray);

                list = null;
                size = 0;

            } else if (drugID != null) {         // this check if there is enough drugs in store to dispense

                jsonObject1 = new JSONObject(drugID); // this parses the jsonObject
                iterator = jsonObject1.keys(); //gets all the keys
                boolean booleanCheck = true;


                while (iterator.hasNext()) {
                    String key = iterator.next().toString(); // get key
                    Object on = jsonObject1.get(key); // get value
                    //check in the store of the quantities for the id are enough to give out this
                    //all check in the dispense settings if this id was set to  batch no from the inventory

                    if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))) == null) {

                        booleanCheck = false;
                        break;

                    } else {


                        if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))).getLocation().getName().equalsIgnoreCase(locationVal)) {

                            if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))).getInventoryId().getQuantity() < Integer.parseInt(on.toString())) {

                                booleanCheck = false;
                                break;

                            } else {

                                booleanCheck = true;


                            }

                        } else {


                            booleanCheck = false;
                            break;
                        }


                    }

                }

                response.getWriter().print("" + booleanCheck);


            } else if (age != null) {

                //get patient age

                jsonArray = new JSONArray();

                jsonArray.put(Context.getPatientService().getPatient(Integer.parseInt(age)).getAge());

                response.getWriter().print(jsonArray);

                list = null;
                size = 0;

            } else if (encounter != null) {


                // get regimens


                person = Context.getPersonService().getPerson(Integer.parseInt(pen));
                list = Context.getService(PharmacyService.class).getPharmacyEncounterListByPatientId(person);


                sizeList = list.size();


                Map<Object, Long> mp = new HashMap<Object, Long>();

                for (int i = 0; i < sizeList; i++) {

                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();

                    cal1.setTime(list.get(i).getDateCreated());
                    cal2.setTime(new Date());


                    long milis1 = cal1.getTimeInMillis();
                    long milis2 = cal2.getTimeInMillis();

                    //
                    // Calculate difference in milliseconds
                    //
                    long diff = milis2 - milis1;


                    //
                    long diffMinutes = diff / (60 * 1000);

                    mp.put(list.get(i).getUuid(), diffMinutes);


                }


                if (!list.isEmpty()) {


                    Long min = Collections.min(mp.values());

                    Set s = mp.entrySet();

                    Iterator it = s.iterator();

                    while (it.hasNext()) {
                        // key=value separator this by Map.Entry to get key and value
                        Map.Entry m = (Map.Entry) it.next();

                        // getKey is used to get key of Map
                        String key = (String) m.getKey();
                        if (m.getValue().equals(min)) {


                            listDrugs = service.getPharmacyOrdersByEncounterId(service.getPharmacyEncounterByUuid(key));


                            break;
                        }


                    }
                    listDrugsSize = listDrugs.size();
                    jsonArray = new JSONArray();


                    for (int i = 0; i < listDrugsSize; i++) {

                        if (listDrugs.get(i).getRegimenId() != 0) {


                            jsonObject.append("" + i, getRegimen(service.getRegimenById(listDrugs.get(i).getRegimenId())));
                        }
                    }


                }
                response.getWriter().print(jsonObject);


            } else if (passUserId != null) {

                //get the authenticated user Id

                jsonArray = new JSONArray();

                jsonArray.put(Context.getUserContext().getAuthenticatedUser().getSystemId());

                response.getWriter().print(jsonArray);

                list = null;
                size = 0;

            } else if (query != null) {

                //get  username

                userList = Context.getUserService().getUsers(query, Context.getUserService().getRoles(), true);

                psize = userList.size();

                jsonArray2 = new JSONArray();
                for (int i = 0; i < psize; i++) {


                    jsonArray2.put(userList.get(i).getUsername());


                }

                response.getWriter().print(jsonArray2);


            } else if (regimen != null) {     // get filter of the regimens


                for (int i = 0; i < sizeRegimen; i++) {

                    jsonObject.append("" + i, getRegimenFilter(listRegimen, i));

                }
                response.getWriter().print(jsonObject);


            } else if (drugs != null) {
                // get drug details

                String[] numbersArray = values.split("/");
                int size = numbersArray.length;

                List<Drug> p = Context.getConceptService().getDrugs(drugs);
                psize = p.size();
                JSONArray temp = new JSONArray();
                for (int i = 0; i < psize; i++) {


                    for (int y = 0; y < size; y++) {

                        if (numbersArray[y].contains("|")) {

                            if (p.get(i).getName().equalsIgnoreCase((numbersArray[y].substring(0, numbersArray[y].indexOf("|")))))
                                temp.put(p.get(i).getName() + "|" + p.get(i).getConcept() + "#" + p.get(i).getId());

                        } else {

                            if (p.get(i).getName().equalsIgnoreCase((numbersArray[y])))
                                temp.put(p.get(i).getName() + "|" + p.get(i).getConcept() + "#" + p.get(i).getId());


                        }

                    }

                }
                response.getWriter().print(temp);


            } else if (users != null) {

                // get users for autocomplete
                List<User> userlist = Context.getUserService().getAllUsers();
                for (User user : userlist) {
                    jsonArray.put(user);
                }
                response.getWriter().print(jsonArray);
                list = null;
                size = 0;

            }

            response.flushBuffer();

        } catch (Exception e) {
            log.error("Error generated");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/dispense")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String transactionsName = request.getParameter("transactionsname");
        String description = request.getParameter("description");
        String transactionsEdit = request.getParameter("transactionsEdit");
        String transactionsUuid = request.getParameter("transactionsUuid");
        String transactionsUuidVoid = request.getParameter("transactionsUuidVoid");
        String transactionsReason = request.getParameter("transactionsReason");

        if (transactionsEdit != null) {
            if (transactionsEdit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                List<PharmacyTransactionTypes> list = service.getPharmacyTransactionTypes();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, transactionsName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyTransactionTypes transactionNamee = new PharmacyTransactionTypes();
                    transactionNamee.setName(transactionsName);
                    transactionNamee.setDescription(description);

                    service.savePharmacyTransactionTypes(transactionNamee);

                } else //do code to display to the user  for entry already entered entry
                {

                }

            } else if (transactionsEdit.equalsIgnoreCase("true")) {

                pharmacyTransactionTypes = service.getPharmacyTransactionTypesByUuid(transactionsUuid);

                // saving/updating a record
                pharmacyTransactionTypes.setName(transactionsName);
                pharmacyTransactionTypes.setDescription(description);

                service.savePharmacyTransactionTypes(pharmacyTransactionTypes);

            }

        } else if (transactionsUuidVoid != null) {


            pharmacyTransactionTypes = service.getPharmacyTransactionTypesByUuid(transactionsUuidVoid);

            pharmacyTransactionTypes.setVoided(true);
            pharmacyTransactionTypes.setVoidReason(transactionsReason);

            service.savePharmacyTransactionTypes(pharmacyTransactionTypes);

        }

    }

    public synchronized JSONArray getEncounterSummary(List<PharmacyEncounter> encounter, int size, String id) {
        service = Context.getService(PharmacyService.class);

        //	get summarized encounter details
        if (encounter.get(size).getPerson().getId().equals(Integer.parseInt(id))) {

            String date = encounter.get(size).getDateTime().toString().substring(0, 10);
            String string = encounter.get(size).getEncounter().getName();

            Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
            for (Role rl : xvc) {

                if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider")) || (rl.getRole().equals("	Authenticated "))) {

                    editPharmacy = true;
                    deletePharmacy = true;
                }


                if (rl.hasPrivilege("Edit Pharmacy")) {
                    editPharmacy = true;
                }

                if (rl.hasPrivilege("Delete Pharmacy")) {
                    deletePharmacy = true;
                }

            }
            drugNamess = new JSONArray();
            if (editPharmacy) {

                drugNamess.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                editPharmacy = false;
            } else
                drugNamess.put("");

            drugNamess.put(encounter.get(size).getUuid());
            drugNamess.put(date);
            drugNamess.put(string);
            drugNamess.put("");
            return drugNamess;
        } else
            return null;

    }

    public synchronized String getDropDown(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size) {

        return pharmacyTransactionTypes.get(size).getName();
    }

    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;

    }

    public synchronized boolean getCheck(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size, String drugNamess) {
        if (pharmacyTransactionTypes.get(size).getName().equalsIgnoreCase(drugNamess)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDropDownUsers(List<User> list2, int size) {
        return list2.get(size).getUsername();

    }

    public synchronized JSONArray getRegimenFilter(List<Regimen> regimen, int size) {

        jsonArray2 = new JSONArray();

        jsonArray2.put(regimen.get(size).getRegimenNames().getRegimenName());
        if (regimen.get(size).getDrugName() != null)
            jsonArray2.put(regimen.get(size).getDrugName().getName());
        else
            jsonArray2.put("None");

        if (regimen.get(size).getCombination()) {
            String uuid = regimen.get(size).getRegimenNames().getRegimenName();

            regimenCombination = service.getRegimenCombination();
            size1 = regimenCombination.size();

            for (int i = 0; i < size1; i++) {

                String found = getDrug(regimenCombination, i, uuid);
                if (found != null) {


                    if (regimenCombination.get(i).getOptions()) {

                        if (regimenCombination.get(i).getDrugName() != null) {
                            jsonArray2.put(regimenCombination.get(i).getDrugName().getName());

                        } else
                            jsonArray2.put("None");

                    } else {

                        if (regimenCombination.get(i).getDrugName() != null) {
                            jsonArray2.put(regimenCombination.get(i).getDrugName().getName());

                        } else
                            jsonArray2.put("None");
                    }


                }

            }

        } else {
            jsonArray2.put("None");
            jsonArray2.put("None");
            jsonArray2.put("None");
            jsonArray2.put("None");
            jsonArray2.put("None");
        }


        jsonArray2.put(regimen.get(size).getId());

        return jsonArray2;
    }

    public synchronized JSONArray getRegimen(Regimen regimen) {

        JSONArray data = new JSONArray();


//		data.put(regimen.get(size).getUuid());
        data.put(regimen.getRegimenNames().getRegimenName());
        if (regimen.getDrugName() != null)
            data.put(regimen.getDrugName().getName());
        else
            data.put("None");

        if (regimen.getCombination()) {
            String uuid = regimen.getRegimenNames().getRegimenName();

            regimenCombination = service.getRegimenCombination();
            size1 = regimenCombination.size();

            for (int i = 0; i < size1; i++) {

                String found = getDrug(regimenCombination, i, uuid);
                if (found != null) {

                    if (regimenCombination.get(i).getDrugName() != null) {
                        data.put(regimenCombination.get(i).getDrugName().getName());

                    } else
                        data.put("None");

                }

            }

        } else {
            data.put("None");
            data.put("None");
            data.put("None");
            data.put("None");
        }


        data.put(regimen.getId());

        return data;
    }


    public synchronized String getDrug(List<RegimenCombination> regimenCombination, int size, String uuid) {
        service = Context.getService(PharmacyService.class);
        if (regimenCombination.get(size).getRegimenNames().getRegimenName().equalsIgnoreCase(uuid)) {

            return regimenCombination.get(size).getUuid();

        } else
            return null;

    }

    // get the value of selected drug
    public synchronized String ArrayDataOne(String jsonText) {

        String value = "";
        JSONParser parser = new JSONParser();


        containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iterator1 = json.entrySet().iterator();

            while (iterator1.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator1.next();

                if (entry.getValue().toString().contains("|"))
                    value += "#" + entry.getValue().toString().substring(entry.getValue().toString().indexOf("|"));
                else
                    value += "#|" + entry.getValue().toString();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value.substring(2);

    }

    // get days diffrence
    public static long daysBetween(Calendar startDate, Calendar endDate) {
        Calendar date = (Calendar) startDate.clone();
        long daysBetween = 0;
        while (date.before(endDate)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }


}
