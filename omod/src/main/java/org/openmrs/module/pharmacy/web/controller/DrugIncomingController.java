package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class DrugIncomingController {

    private static final Log log = LogFactory.getLog(DrugIncomingController.class);

    private JSONArray data;

    private JSONArray datad;

    public PharmacyService service;

    String drop = null;

    private boolean found = false;

    private String uuid;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private String originalbindrug;

    private String dialog = null;

    private LocationService serviceLocation;

    private String incomingmax = null;

    private String incomingmin = null;

    private String incomingbatch = null;

    private String incomings11 = null;

    private String incomingexpire = null;

    private String supplier = null;

    private String deliveryno;

    private String incomingnumber;

    private String incomingdrug;

    private String incomingquantityin;

    private String filter = null;

    private String uuidfilter = null;

    private boolean exit = false;

    private ConceptService serviceDrugs;

    private String category;

    private UserContext userService;

    private String requisition;

    private String issued;

    private String authorized;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private String supplierout;

    private String incomingdrugg;

    private String originalbindrugg;

    private String uuidg;

    private String incomingnumberap;

    private String incomingexpirea;
    private boolean approvePharmacy;
    private String[] incomingdrugArray;
    private String[] incomingquantityinArray;
    private String[] categoryArray;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugIncoming")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();

        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        filter = request.getParameter("sSearch");


        List<PharmacyStoreIncoming> List = service.getPharmacyStoreIncoming();
        int size = List.size();
        JSONObject json = new JSONObject();
        JSONArray jsons = new JSONArray();
        response.setContentType("application/json");


        if (filter.length() > 2) {
            originalbindrug = filter;

            serviceLocation = Context.getLocationService();
            serviceDrugs = Context.getConceptService();

            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuidfilter = getString(dname, i, originalbindrug);
                if (uuidfilter != null)
                    break;

            }
        }
        try {
            if (dialog != null) {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArrayDialog(List, i));
                }

            } else {
                for (int i = 0; i < size; i++) {

                    if (List.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {

                        JSONArray val = getArray(List, i, locationVal);
                        if (val != null)
                            json.accumulate("aaData", val);
                    }
                    if (exit)
                        break;
                    data = new JSONArray();
                }

                if (!json.has("aaData")) {

                    data = new JSONArray();
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");

                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    json.accumulate("aaData", data);
                }

            }
            exit = false;

            json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
            json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
            json.accumulate("iDisplayStart", 0);
            json.accumulate("iDisplayLength", 10);

            response.getWriter().print(json);

            response.flushBuffer();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugIncoming")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        String incomingedit;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        serviceDrugs = Context.getConceptService();
        incomingdrug = request.getParameter("incomingdrug");
        incomingdrugg = request.getParameter("incomingdrugg");


        incomingdrugArray = request.getParameterValues("incomingdrug");


        categoryArray = request.getParameterValues("incomingcategory");


        incomingquantityinArray = request.getParameterValues("incomingquantityin");


        incomingquantityin = request.getParameter("incomingquantityin");

        incomingmax = request.getParameter("incomingmax");
        incomingmin = request.getParameter("incomingmin");

        incomingnumber = request.getParameter("incomingnumber");

        incomingnumberap = request.getParameter("incomingnumberap");
        incomingbatch = request.getParameter("incomingbatch");
        incomings11 = request.getParameter("incomings11");

        incomingexpire = request.getParameter("incomingexpire");
        incomingexpirea = request.getParameter("incomingexpirea");
        userService = Context.getUserContext();
        String incomingreason = request.getParameter("incomingreason");
        String incominguuidvoid = request.getParameter("incominguuidvoid");
        String incominguuidextra = request.getParameter("incominguuidextra");


        requisition = request.getParameter("requisition");
        issued = request.getParameter("issued");
        authorized = request.getParameter("authorized");

        String destination = request.getParameter("location");
        String location = request.getParameter("location");
        supplier = request.getParameter("supplier");
        supplierout = request.getParameter("supplierout");

        category = request.getParameter("incomingcategory");
        String transactions = request.getParameter("transactions");
        deliveryno = request.getParameter("delivery");

        incomingedit = request.getParameter("incomingedit");
        String incominguuid = request.getParameter("incominguuid");
        String incomingcom = request.getParameter("incomingcom");
        originalbindrug = incomingdrug;
        originalbindrugg = incomingdrugg;
        serviceLocation = Context.getLocationService();

        if (incomingdrugArray != null && incominguuid == null) {
            incomingedit = "false";


        }


//        if (incominguuidvoid == null) {
//            //			incomingdrug = incomingdrug.substring(0, incomingdrug.indexOf("("));
//            //
//            //			drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
//            //			drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
//            //			formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
//            //
//            //
//            //		String uuidvalue = service.getDrugNameByName(incomingdrug).getUuid();
//            //		String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
//            //		String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
//            //		String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();
//
//            List<Drug> dname = serviceDrugs.getAllDrugs();
//            int dnames = dname.size();
//            for (int i = 0; i < dnames; i++) {
//                uuid = getString(dname, i, originalbindrug);
//                if (uuid != null)
//                    break;
//
//            }
//            for (int i = 0; i < dnames; i++) {
//                uuidg = getString(dname, i, originalbindrugg);
//                if (uuidg != null)
//                    break;
//
//            }
//        }


//        incomingedit=true&incominguuid=1580f2f0-1d44-483a-bb78-5335c2e1c2bf&incomingdrug=Triomune-15&location=Chulaimbo&incomingquantityin=370&transactions=Return+in&incomingcategory=ARV
        if (incomingedit != null) {

            if (incomingedit.equalsIgnoreCase("false")) {

//                ///check for same entry before saving
//                List<PharmacyStoreIncoming> list = service.getPharmacyStoreIncoming();
//                int size = list.size();
//                for (int i = 0; i < size; i++) {
//
//                    found = getCheck(list, i, incomingdrug);
//                    if (found)
//                        break;
//                }

                //if (service.getPharmacyLocationsByName(destination) != service.getPharmacyLocationsByName(locationVal)) {
                if (!destination.equalsIgnoreCase(locationVal)) {

                    List<PharmacyStoreIncoming> pStoreIncoming = new ArrayList<PharmacyStoreIncoming>();
                    List<PharmacyStoreOutgoing> pStoreOutgoing = new ArrayList<PharmacyStoreOutgoing>();


                    PharmacyStoreIncoming pharmacyStoreIncoming = new PharmacyStoreIncoming();
                    serviceLocation = Context.getLocationService();
                    //get drug details 		drugstrength drugunit formulation
                    System.out.println(incomingdrug + ">>>>>>>>>>>>>>>>>>>>>>sssssssssssss>>>>>>>>>>>>>>>>>>>>");


                    for (int y = 0; y < incomingdrugArray.length; y++) {

                        PharmacyStoreIncoming phStoreIncoming = new PharmacyStoreIncoming();
                        PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();


                        phStoreIncoming.setDrugs(serviceDrugs.getDrug(incomingdrugArray[y]));

                        phStoreIncoming.setQuantityIn(Integer.parseInt(incomingquantityinArray[y]));


                        phStoreIncoming.setMaxLevel(0);

                        phStoreIncoming.setMinLevel(0);


                        phStoreIncoming.setBatchNo(0);

                        phStoreIncoming.setExpireDate(null);

                        phStoreIncoming.setSupplier(null);


                        phStoreIncoming.setS11(Integer.parseInt(incomings11));


                        phStoreIncoming.setDestination(service.getPharmacyLocationsByName(destination));
                        phStoreIncoming.setLocation(service.getPharmacyLocationsByName(locationVal));

                        phStoreIncoming.setChangeReason(null);

                        phStoreIncoming.setCategory(service.getPharmacyCategoryByName(categoryArray[y]));

                        phStoreIncoming.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                        phStoreIncoming.setStatus("Sent");
                        phStoreIncoming.setRequested(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));


                        pStoreIncoming.add(phStoreIncoming);


                        pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrug(incomingdrugArray[y]));

                        pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(incomingquantityinArray[y]));

                        pharmacyStoreOutgoing
                                .setMaxLevel(1000);

                        pharmacyStoreOutgoing
                                .setMinLevel(10);


                        pharmacyStoreOutgoing.setBatchNo(0);


                        pharmacyStoreOutgoing.setS11(Integer.parseInt(incomings11));


                        pharmacyStoreOutgoing.setExpireDate(null);


                        pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
                        pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(locationVal));

                        pharmacyStoreOutgoing.setChangeReason(null);
                        pharmacyStoreOutgoing.setSupplier(null);

                        pharmacyStoreOutgoing.setCategory(service.getPharmacyCategoryByName(categoryArray[y]));

                        pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                        pharmacyStoreOutgoing.setStatus("New");
                        pharmacyStoreOutgoing.setIncoming(phStoreIncoming);
                        pharmacyStoreOutgoing.setRequested(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                        pStoreOutgoing.add(pharmacyStoreOutgoing);


                    }

                    service.savePharmacyStoreIncoming(pStoreIncoming);
                    service.savePharmacyStoreOutgoing(pStoreOutgoing);

//                    service.savePharmacyStoreIncoming(pharmacyStoreIncoming);
//
//
//
//
//                    //add the same details to outgoing
//
//                    PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
//
//                    //get drug details 		drugstrength drugunit formulation
//
//                    pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuid));
//
//                    pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(incomingquantityin));
//
//                    pharmacyStoreOutgoing
//                            .setMaxLevel(100);
//
//                    pharmacyStoreOutgoing
//                            .setMinLevel(10);
//
//                    if (incomingbatch != null) {
//                        pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(incomingbatch));
//
//                    } else if (incomingbatch == null) {
//                        pharmacyStoreOutgoing.setBatchNo(0);
//                    }
//
//                    if (incomings11 != null) {
//                        pharmacyStoreOutgoing.setS11(Integer.parseInt(incomings11));
//
//                    } else if (incomings11 == null) {
//                        pharmacyStoreOutgoing.setS11(0);
//                    }
//
//                    pharmacyStoreOutgoing.setExpireDate(date);
//                    serviceLocation = Context.getLocationService();
//
//                    pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
//                    pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(locationVal));
//
//                    pharmacyStoreOutgoing.setChangeReason(null);
//
//                    if (supplier == null) {
//                        pharmacyStoreOutgoing.setSupplier(null);
//
//                    } else
//                        pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));
//
//                    if (category == null) {
//                        pharmacyStoreOutgoing.setCategory(null);
//
//                    } else
//                        pharmacyStoreOutgoing.setCategory(service.getPharmacyCategoryByName(category));
//
//                    pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));
//                    pharmacyStoreOutgoing.setStatus("New");
//                    pharmacyStoreOutgoing.setIncoming(pharmacyStoreIncoming);
//                    if (requisition != null && issued != null && authorized != null) {
//
//                        pharmacyStoreOutgoing.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
//                        pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
//                        pharmacyStoreOutgoing.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
//
//
//
//                    }
//                    service.savePharmacyStoreOutgoing(pharmacyStoreOutgoing);

                }

            } else if (incomingedit.equalsIgnoreCase("true")) {


                System.out.println(incomingdrug + ">>>>>>>>>>>>>>>>>>>>>>EEEEEEEEEEEEE>>>>>>>>>>>>>>>>>>>>");

//                ncomings11=7887&transactions=From+suppliers&location=ARV+Storen&incomingdrug=Triomune-15&incomingcategory=ARV&
//                        incomingquantityin=298&incomingedit=true&incominguuid=1580f2f0-1d44-483a-bb78-5335c2e1c2bf


                if (!destination.equalsIgnoreCase(locationVal)) {

                    PharmacyStoreIncoming pharmacyStoreIncoming = new PharmacyStoreIncoming();
                    pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incominguuid);

                    if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreIncoming.getCreator().getUserId())) {

                        pharmacyStoreIncoming.setDrugs(serviceDrugs.getDrug(incomingdrug));
                        System.out.println(incomingdrug + ">>>>>>>>>>>>>>>>>>>>>>EEEEEEEEEEEEE>>>>>>>>>>>>>>>>>>>>" + incominguuid);

                        pharmacyStoreIncoming.setQuantityIn(Integer.parseInt(incomingquantityin));


                        serviceLocation = Context.getLocationService();
                        pharmacyStoreIncoming.setDestination(service.getPharmacyLocationsByName(location));
                        pharmacyStoreIncoming.setLocation(service.getPharmacyLocationsByName(locationVal));

                        pharmacyStoreIncoming.setChangeReason(null);
                        pharmacyStoreIncoming.setS11(Integer.parseInt(incomings11));

                        if (category == null) {
                            pharmacyStoreIncoming.setCategory(null);

                        } else
                            pharmacyStoreIncoming.setCategory(service.getPharmacyCategoryByName(category));

                        pharmacyStoreIncoming.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                        service.savePharmacyStoreIncoming(pharmacyStoreIncoming);

                    }
                }


            }

        } else if (incominguuidvoid != null) {

            PharmacyStoreIncoming pharmacyStoreIncoming = new PharmacyStoreIncoming();
            pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incominguuidvoid);

            pharmacyStoreIncoming.setVoided(true);
            pharmacyStoreIncoming.setVoidReason(incomingreason);

            service.savePharmacyStoreIncoming(pharmacyStoreIncoming);

        } else if (incominguuidextra != null) {

            if (supplierout != null) {
                log.info("");
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

                if (service.getPharmacyLocationsByName(destination) != service.getPharmacyLocationsByName(service
                        .getPharmacyLocation())) {
                    log.info("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
                    incomingnumber = incomingnumberap;
                    Date date = null;
                    try {
                        if (incomingexpirea != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(incomingexpirea);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block incominguuidextra incomingnumber
                        log.error("Error generated", e);
                    }

                    PharmacyStoreApproved pharmacyStoreApprove = new PharmacyStoreApproved();
                    pharmacyStoreApprove = service.getPharmacyStoreApprovedByUuid(incominguuidextra);
                    pharmacyStoreApprove.setApproved(true);
                    PharmacyStoreIncoming pharmacyStoreIncoming = new PharmacyStoreIncoming();
                    pharmacyStoreIncoming = pharmacyStoreApprove.getIncoming();

                    if (Integer.parseInt(incomingnumber) == pharmacyStoreIncoming.getQuantityIn()) {

                        pharmacyStoreIncoming.setApproved(true);
                        pharmacyStoreIncoming.setQuantityIn(0);
                    } else {
                        if (Integer.parseInt(incomingnumber) < pharmacyStoreIncoming.getQuantityIn()) {
                            pharmacyStoreIncoming.setQuantityIn((pharmacyStoreIncoming.getQuantityIn() - Integer
                                    .parseInt(incomingnumber)));
                        }

                    }

                    //					pharmacyStoreIncoming.setBatchNo(Integer.parseInt(incomingbatch));
                    //					pharmacyStoreIncoming.setDeliveryNo(Integer.parseInt(deliveryno));
                    //
                    //
                    //					pharmacyStoreIncoming.setExpireDate(date);
                    //					pharmacyStoreIncoming.setMaxLevel(service.getDrugMaxMinByDrug(
                    //					    service.getPharmacyStoreIncomingByUuid(incominguuidextra).getDrugs()).getMax());
                    //					pharmacyStoreIncoming.setMinLevel(service.getDrugMaxMinByDrug(
                    //					    service.getPharmacyStoreIncomingByUuid(incominguuidextra).getDrugs()).getMin());
                    //					pharmacyStoreIncoming.setS11(Integer.parseInt(incomings11));
                    //					pharmacyStoreIncoming.setSupplier(service.getPharmacySupplierByName(supplierout));
                    //
                    //					pharmacyStoreIncoming.setRequested(Context.getUserService().getUserByUsername(requisition));
                    //					pharmacyStoreIncoming.setIssued(Context.getUserService().getUserByUsername(issued));
                    //					pharmacyStoreIncoming.setAuthorized(Context.getUserService().getUserByUsername(authorized));
                    ////
                    PharmacyStore pharmacyStore = new PharmacyStore();

                    //add the approved to the main inventory

                    //get drug details 		drugstrength drugunit formulation

                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    DrugTransactions drugTransactions = new DrugTransactions();

                    //transactions
                    drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                    drugTransactions.setQuantityOut(0);
                    drugTransactions.setQuantityIn(Integer.parseInt(incomingnumber));
                    drugTransactions.setexpireDate(date);
                    drugTransactions.setCategory(pharmacyStoreApprove.getCategory());
                    drugTransactions.setComment("New entry");

                    drugTransactions
                            .setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                    //drugTransactions.setLocation(location)
                    service.saveDrugTransactions(drugTransactions);

                    pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                    pharmacyStore.setQuantity(Integer.parseInt(incomingnumber));

                    pharmacyStore.setQuantityIn(0);
                    pharmacyStore.setQuantityOut(0);
                    pharmacyStore.setChangeReason(null);

                    pharmacyStore.setBatchNo(Integer.parseInt(incomingbatch));
                    pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));

                    pharmacyStore.setExpireDate(date);
                    pharmacyStore.setMaxLevel(1000);
                    pharmacyStore.setMinLevel(10);
                    pharmacyStore.setS11(Integer.parseInt(incomings11));

                    pharmacyStore.setSupplier(service.getPharmacySupplierByName(supplier));

                    //
                    pharmacyStore.setCategory(pharmacyStoreApprove.getCategory());

                    pharmacyStore.setTransaction(pharmacyStoreApprove.getTransaction());
                    pharmacyStore.setIncoming(service.getPharmacyStoreIncomingByUuid(incominguuidextra));

                    service.savePharmacyInventory(pharmacyStore);

                    service.savePharmacyStoreApproved(pharmacyStoreApprove);
                    service.savePharmacyStoreIncoming(pharmacyStoreIncoming);

                }
            } else if (supplier != null) {
                System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO" + destination);
                PharmacyStoreIncoming pharmacyStoreIncoming = new PharmacyStoreIncoming();
                pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incominguuidextra);

                if (pharmacyStoreIncoming.getLocation().getName().equalsIgnoreCase(locationVal)) {
                    Date date = null;
                    try {
                        if (incomingexpire != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(incomingexpire);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }


                    if (Integer.parseInt(incomingnumber) == pharmacyStoreIncoming.getQuantityIn()) {

                        pharmacyStoreIncoming.setApproved(true);
                        pharmacyStoreIncoming.setQuantityIn(0);
                    } else {
                        if (Integer.parseInt(incomingnumber) < pharmacyStoreIncoming.getQuantityIn()) {
                            pharmacyStoreIncoming.setQuantityIn((pharmacyStoreIncoming.getQuantityIn() - Integer
                                    .parseInt(incomingnumber)));
                        }

                    }

                    pharmacyStoreIncoming.setBatchNo(Integer.parseInt(incomingbatch));
                    pharmacyStoreIncoming.setDeliveryNo(Integer.parseInt(deliveryno));

                    pharmacyStoreIncoming.setExpireDate(date);

//                    pharmacyStoreIncoming.setMaxLevel(service.getDrugMaxMinByDrug(pharmacyStoreIncoming.getDrugs()).getMin());
//                    pharmacyStoreIncoming.setMinLevel(service.getDrugMaxMinByDrug(pharmacyStoreIncoming.getDrugs()).getMax());

                    pharmacyStoreIncoming.setMaxLevel(100);
                    pharmacyStoreIncoming.setMinLevel(10);


                    pharmacyStoreIncoming.setSupplier(service.getPharmacySupplierByName(supplierout));

                    //
                    PharmacyStore pharmacyStore = new PharmacyStore();

                    //add the approved to the main inventory

                    //get drug details 		drugstrength drugunit formulation

                    System.out.println("--------------------" + pharmacyStoreIncoming.getDrugs().getName());

                    pharmacyStore.setDrugs(pharmacyStoreIncoming.getDrugs());

                    DrugTransactions drugTransactions = new DrugTransactions();

                    //transactions
                    drugTransactions.setDrugs(pharmacyStoreIncoming.getDrugs());
                    drugTransactions.setQuantityOut(0);
                    drugTransactions.setQuantityIn(Integer.parseInt(incomingnumber));
                    drugTransactions.setexpireDate(date);
                    drugTransactions.setCategory(pharmacyStoreIncoming.getCategory());
                    drugTransactions.setComment("New entry");

                    drugTransactions
                            .setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                    //drugTransactions.setLocation(location)
                    service.saveDrugTransactions(drugTransactions);

                    pharmacyStore.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                    pharmacyStore.setQuantity(Integer.parseInt(incomingnumber));

                    pharmacyStore.setQuantityIn(0);
                    pharmacyStore.setQuantityOut(0);
                    pharmacyStore.setChangeReason(null);

                    pharmacyStore.setBatchNo(Integer.parseInt(incomingbatch));
                    pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));

                    pharmacyStore.setExpireDate(date);

//                    pharmacyStore.setMaxLevel(service.getDrugMaxMinByDrug(pharmacyStoreIncoming.getDrugs()).getMin());
//                    pharmacyStore.setMinLevel(service.getDrugMaxMinByDrug(pharmacyStoreIncoming.getDrugs()).getMax());
                    pharmacyStore.setMaxLevel(1000);
                    pharmacyStore.setMinLevel(10);


                    pharmacyStore.setS11(pharmacyStoreIncoming.getS11());

                    pharmacyStore.setSupplier(service.getPharmacySupplierByName(supplier));

                    //
                    pharmacyStore.setCategory(pharmacyStoreIncoming.getCategory());

                    pharmacyStore.setTransaction(pharmacyStoreIncoming.getTransaction());

                    pharmacyStore.setIncoming(service.getPharmacyStoreIncomingByUuid(incominguuidextra));

                    service.savePharmacyInventory(pharmacyStore);

                    service.savePharmacyStoreIncoming(pharmacyStoreIncoming);

                }

            }

        }

    }

    public synchronized JSONArray getArray(List<PharmacyStoreIncoming> pharmacyStore, int size, String location) {

        if (filter.length() > 2) {

            if ((uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid()))
                    && (!pharmacyStore.get(size).getApproved())) {
                if (pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location)) {

                    data = new JSONArray();
                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    for (Role rl : xvc) {

                        if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))
                                || (rl.getRole().equals("	Authenticated "))) {

                            editPharmacy = true;
                            deletePharmacy = true;
                            approvePharmacy = true;
                        }

                        if (rl.hasPrivilege("Edit Pharmacy")) {
                            editPharmacy = true;
                        }
                        if (rl.hasPrivilege("Approve Pharmacy")) {
                            approvePharmacy = true;
                        }

                        if (rl.hasPrivilege("Delete Pharmacy")) {
                            deletePharmacy = true;
                        }
                    }
                    if (editPharmacy) {

                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");

                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());

                    data.put(pharmacyStore.get(size).getDrugs().getName());

                    data.put(pharmacyStore.get(size).getQuantityIn());
                    data.put(pharmacyStore.get(size).getDestination().getName());
                    data.put(pharmacyStore.get(size).getTransaction().getName());

                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    data.put(pharmacyStore.get(size).getMinLevel());
                    data.put(pharmacyStore.get(size).getMaxLevel());

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getExpireDate());
                    data.put(pharmacyStore.get(size).getDeliveryNo());

                    data.put("");

                    if (pharmacyStore.get(size).getApproved()) {

                        data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                    } else {


                        if (approvePharmacy) {
                            data.put("Approve");
                            approvePharmacy = false;
                        } else
                            data.put("");
                    }
                    if (deletePharmacy) {
                        data.put("<var>Delete<var/>");
                        deletePharmacy = false;
                    } else
                        data.put("");

                    data.put(pharmacyStore.get(size).getStatus());

                    return data;
                }
            } else {
                //exit=true;

                return null;
            }

        } else {

            if ((pharmacyStore.get(size).getLocation().getName().equalsIgnoreCase(location))
                    && (!pharmacyStore.get(size).getApproved())) {

                data = new JSONArray();
                Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                for (Role rl : xvc) {

                    if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))
                            || (rl.getRole().equals("	Authenticated "))) {

                        editPharmacy = true;
                        deletePharmacy = true;
                        approvePharmacy = true;
                    }

                    if (rl.hasPrivilege("Edit Pharmacy")) {
                        editPharmacy = true;
                    }
                    if (rl.hasPrivilege("Approve Pharmacy")) {
                        approvePharmacy = true;
                    }

                    if (rl.hasPrivilege("Delete Pharmacy")) {
                        deletePharmacy = true;
                    }

                }
                if (editPharmacy) {

                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");
                data.put("");
                data.put(pharmacyStore.get(size).getUuid());

                data.put(pharmacyStore.get(size).getDrugs().getName());
                data.put(pharmacyStore.get(size).getQuantityIn());

                data.put(pharmacyStore.get(size).getDestination().getName());

                data.put(pharmacyStore.get(size).getTransaction().getName());

                if (pharmacyStore.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());

                data.put(pharmacyStore.get(size).getMinLevel());

                data.put(pharmacyStore.get(size).getMaxLevel());

                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getExpireDate());
                data.put(pharmacyStore.get(size).getDeliveryNo());

                data.put("");

                if (pharmacyStore.get(size).getApproved()) {

                    data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                } else {


                    if (approvePharmacy) {
                        data.put("Approve");
                        approvePharmacy = false;
                    } else
                        data.put("");
                }


                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");
                data.put(pharmacyStore.get(size).getStatus());

                return data;

            }

        }

        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStoreIncoming> pharmacyStore, int size) {

        datad = new JSONArray();

        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());

        datad.put("");
        datad.put("");

        return datad;
    }

    public synchronized String getDropDown(List<PharmacyStoreIncoming> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public synchronized boolean getCheck(List<PharmacyStoreIncoming> pharmacyStore, int size, String name) {

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<PharmacyStoreIncoming> pharmacyStore, int size, String name) {
        service = Context.getService(PharmacyService.class);

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return pharmacyStore.get(size).getUuid();

        } else
            return null;

    }

    public synchronized String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;
    }

}
