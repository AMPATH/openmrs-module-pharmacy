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

    private String originalbindrug;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private List<PharmacyEncounter> list = null;

    private int size = 0;

    private UserService usersService;

    private List<PharmacyOrders> listOrders;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private int psize;

    private List<PharmacyOrders> listDrugs;
    JSONParser parser;
    private int listDrugsSize;
    private ContainerFactory containerFactory;


    /**
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/dispense")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String locationVal = null;
        String patientId = request.getParameter("patientID");//encounterDetails
        String encounterDetails = request.getParameter("encounterDetails");//
        String users = request.getParameter("users");//
        String Pname = request.getParameter("Pid");//
        String age = request.getParameter("age");//
        String drug = request.getParameter("drug");
        String pass = request.getParameter("pass");
        String query = request.getParameter("q");
        String drugs = request.getParameter("drugs");
        String values = request.getParameter("values");
        String regimen = request.getParameter("regimen");
        String filter = request.getParameter("filter");
        String encounter = request.getParameter("encounter");
        String pen = request.getParameter("Pen");
        String drugID = request.getParameter("drugCheck");
        String totVal = request.getParameter("total");

        service = Context.getService(PharmacyService.class);
        patientService = Context.getEncounterService();
        usersService = Context.getUserService();
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        List<PharmacyObs> listObs;

        if (patientId != null) {
            list = service.getPharmacyEncounter();
            size = list.size();
        }


        List<Regimen> listRegimen = service.getRegimen(filter);
        int sizeRegimen = listRegimen.size();


        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (encounterDetails != null) {
                PharmacyEncounter listTwo = service.getPharmacyEncounterByUuid(encounterDetails);
                if (listTwo.getLocation().getName().equalsIgnoreCase(locationVal)) {
                    jsons = new JSONArray();
                    // create obs data

                    jsons.put("Encounter Details");
                    jsons.put("Patient name:" + listTwo.getPerson().getNames());
                    jsons.put("Encounter date:" + listTwo.getDateTime());
                    jsons.put("Encounter location:" + listTwo.getLocation().getName());
                    jsons.put("Encounter Creator:" + listTwo.getCreator().getName());

                    listObs = service.getPharmacyObs();
                    jsons.put("Obs Details");
                    for (int y = 0; y < listObs.size(); y++) {
                        if (listObs.get(y).getPharmacyEncounter().getUuid().contentEquals(listTwo.getUuid())) {

                            jsons.put("Question:"
                                    + Context.getConceptService().getConcept(listObs.get(y).getConcept()).getDisplayString());
                            jsons.put("Given value:" + listObs.get(y).getValueNumeric());
                        }

                    }
                    jsons.put("Drug Details");
                    listOrders = service.getPharmacyOrders();
                    for (int y = 0; y < listOrders.size(); y++) {

                        if (listOrders.get(y).getPharmacyEncounter().getUuid().contentEquals(listTwo.getUuid())) {


                            if (Context.getConceptService().getConcept(listOrders.get(y).getConcept()) != null) {

                                jsons.put("Drug given :" + Context.getConceptService().getConcept(listOrders.get(y).getConcept()).getDisplayString());
                                jsons.put("Drug given :" + listOrders.get(y).getQuantity());  //listTwo
                                jsons.put("Days given :" + listOrders.get(y).getMonthsNo());  //

                                jsons.put("Next visit date :" + listOrders.get(y).getNextVisitDate().toString().substring(0, 10));


                            }
                            //jsons.put("Drug details:");
                        }
                    }

                    response.getWriter().print(jsons);
                }
            } else if (patientId != null) {


                for (int i = 0; i < size; i++) {

                    if (list.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {

                        JSONArray temp = new JSONArray();
                        temp = getArray(list, i, patientId);

                        if (temp != null)
                            json.accumulate("aaData", temp);
                    }
                }

                if (!json.has("aaData")) {
                    drugNamess = new JSONArray();

                    drugNamess.put("None");
                    drugNamess.put("None");

                    drugNamess.put("None");
                    drugNamess.put("None");
                    drugNamess.put("None");

                    json.accumulate("aaData", drugNamess);
                }

                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);
                response.getWriter().print(json);
                json = new JSONObject();
                list = null;
                size = 0;
            } else if (Pname != null) {

                jsons = new JSONArray();

                jsons.put(Context.getPatientService().getPatient(Integer.parseInt(Pname)).getGivenName());

                response.getWriter().print(jsons);

                list = null;
                size = 0;

            } else if (drugID != null) {

                JSONObject jObj = new JSONObject(drugID); // this parses the json
                Iterator it = jObj.keys(); //gets all the keys
                boolean msq = true;


                while (it.hasNext()) {
                    String key = it.next().toString(); // get key
                    Object on = jObj.get(key); // get value
                    //check in the store of the quantities for the id are enough to give out this
                    //all check in the dispense settings if this id was set to  batch no from the inventory

                    if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))) == null) {

                        msq = false;
                        break;

                    } else {


                        if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))).getLocation().getName().equalsIgnoreCase(locationVal)) {

                            if (service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrug(Integer.parseInt(key))).getInventoryId().getQuantity() < Integer.parseInt(on.toString())) {

                                msq = false;
                                break;

                            } else {

                                msq = true;


                            }

                        } else {


                            msq = false;
                            break;
                        }


                    }

                }

                response.getWriter().print("" + msq);


            } else if (age != null) {

                jsons = new JSONArray();

                jsons.put(Context.getPatientService().getPatient(Integer.parseInt(age)).getAge());

                response.getWriter().print(jsons);

                list = null;
                size = 0;

            } else if (encounter != null) {


                Person p = Context.getPersonService().getPerson(Integer.parseInt(pen));
                list = Context.getService(PharmacyService.class).getPharmacyEncounterListByPatientId(p);


                int sizePenc = list.size();


                Map<Object, Long> mp = new HashMap<Object, Long>();

                for (int i = 0; i < sizePenc; i++) {

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
                    jsons = new JSONArray();


                    for (int i = 0; i < listDrugsSize; i++) {

                        if (listDrugs.get(i).getRegimenId() != 0) {


                            json.append("" + i, getArrayRegimen(service.getRegimenById(listDrugs.get(i).getRegimenId())));
                        }
                    }


                }
                response.getWriter().print(json);


            } else if (pass != null) {

                jsons = new JSONArray();

                jsons.put(Context.getUserContext().getAuthenticatedUser().getSystemId());

                response.getWriter().print(jsons);

                list = null;
                size = 0;

            } else if (query != null) {
//				Context.getUserService().getUsers(query, Context.getUserService().getRoles(), true);
//				List<Patient> p= Context.getPersonService().get(query);
//				roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider";
//				List(Role) cv =Context.getUserService().getInheritingRoles(arg0)
//				

                List<User> p = Context.getUserService().getUsers(query, Context.getUserService().getRoles(), true);


                psize = p.size();

                JSONArray temp = new JSONArray();
                for (int i = 0; i < psize; i++) {


                    temp.put(p.get(i).getUsername());


                }

                response.getWriter().print(temp);


            } else if (regimen != null) {


                for (int i = 0; i < sizeRegimen; i++) {

                    json.append("" + i, getArrayRegimen(listRegimen, i));

                }
                response.getWriter().print(json);


            } else if (drugs != null) {


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

                List<User> userlist = Context.getUserService().getAllUsers();


                for (User user : userlist) {

                    jsons.put(user);


                }

                //jsons.put(Context.getPatientService().getPatient(Integer.parseInt(Pname)).getNames());

                response.getWriter().print(jsons);

                list = null;
                size = 0;

            }

            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch blocktl
            e.printStackTrace();
            log.error("Error generated");
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/dispense")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String transactionsName = request.getParameter("transactionsname");//description
        String description = request.getParameter("description");//description
        String edit = request.getParameter("transactionseedit");
        String uuid = request.getParameter("transactionsuuid");
        String uuidvoid = request.getParameter("transactionsuuidvoid");
        String reason = request.getParameter("transactionsreason");

        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
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

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacyTransactionTypes transactionName = new PharmacyTransactionTypes();

                transactionName = service.getPharmacyTransactionTypesByUuid(uuid);

                // saving/updating a record
                transactionName.setName(transactionsName);
                transactionName.setDescription(description);

                service.savePharmacyTransactionTypes(transactionName);

            }

        } else if (uuidvoid != null) {

            PharmacyTransactionTypes transactionNamev = new PharmacyTransactionTypes();

            transactionNamev = service.getPharmacyTransactionTypesByUuid(uuidvoid);

            transactionNamev.setVoided(true);
            transactionNamev.setVoidReason(reason);

            service.savePharmacyTransactionTypes(transactionNamev);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyEncounter> encounter, int size, String id) {
        service = Context.getService(PharmacyService.class);

        //	if(service.getPharmacyLocation().){}
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

    public synchronized JSONArray getArrayRegimen(List<Regimen> regimen, int size) {

        JSONArray data = new JSONArray();


//		data.put(regimen.get(size).getUuid());
        data.put(regimen.get(size).getRegimenNames().getRegimenName());
        if (regimen.get(size).getDrugName() != null)
            data.put(regimen.get(size).getDrugName().getName());
        else
            data.put("None");

        if (regimen.get(size).getCombination()) {
            String uuid = regimen.get(size).getRegimenNames().getRegimenName();

            List<RegimenCombination> combi = service.getRegimenCombination();
            int combinu = combi.size();

            for (int i = 0; i < combinu; i++) {

                String found = getDrug(combi, i, uuid);
                if (found != null) {


                    if (combi.get(i).getOptions()) {

                        if (combi.get(i).getDrugName() != null) {
                            data.put(combi.get(i).getDrugName().getName());

                        } else
                            data.put("None");

                    } else {

                        if (combi.get(i).getDrugName() != null) {
                            data.put(combi.get(i).getDrugName().getName());

                        } else
                            data.put("None");
                    }


                }

            }

        } else {
            data.put("None");
            data.put("None");
            data.put("None");
            data.put("None");
            data.put("None");
        }


        data.put(regimen.get(size).getId());

        return data;
    }

    public synchronized JSONArray getArrayRegimen(Regimen regimen) {

        JSONArray data = new JSONArray();


//		data.put(regimen.get(size).getUuid());
        data.put(regimen.getRegimenNames().getRegimenName());
        if (regimen.getDrugName() != null)
            data.put(regimen.getDrugName().getName());
        else
            data.put("None");

        if (regimen.getCombination()) {
            String uuid = regimen.getRegimenNames().getRegimenName();

            List<RegimenCombination> combi = service.getRegimenCombination();
            int combinu = combi.size();

            for (int i = 0; i < combinu; i++) {

                String found = getDrug(combi, i, uuid);
                if (found != null) {

                    if (combi.get(i).getDrugName() != null) {
                        data.put(combi.get(i).getDrugName().getName());

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
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

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
