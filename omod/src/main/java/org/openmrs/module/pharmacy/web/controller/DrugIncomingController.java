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
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size, size1;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Drug> allDrugs;
    private List<PharmacyStoreIncoming> pStoreIncoming;
    private List<PharmacyStoreOutgoing> pStoreOutgoing;
    private PharmacyStoreIncoming pharmacyStoreIncoming;
    private DrugTransactions drugTransactions;
    private PharmacyStore pharmacyStore;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugIncoming")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        filter = request.getParameter("sSearch");

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        String locationVal = null;
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size = pharmacyLocationUsersByUserName.size();

        if (size > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (size == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }


        List<PharmacyStoreIncoming> List = service.getPharmacyStoreIncoming();
        int size = List.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");


        if (filter.length() > 2) {
            originalbindrug = filter;

            serviceLocation = Context.getLocationService();
            serviceDrugs = Context.getConceptService();

            allDrugs = serviceDrugs.getAllDrugs();
            size1 = allDrugs.size();
            for (int i = 0; i < size1; i++) {

                uuidfilter = getString(allDrugs, i, originalbindrug);

                if (uuidfilter != null)
                    break;

            }
        }
        try {
            if (dialog != null) {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArrayDialog(List, i));
                }

            } else {
                for (int i = 0; i < size; i++) {

                    if (List.get(i).getLocation().getName().equalsIgnoreCase(locationVal)) {

                        JSONArray val = getArray(List, i, locationVal);
                        if (val != null)
                            jsonObject.accumulate("aaData", val);
                    }
                    if (exit)
                        break;
                    data = new JSONArray();
                }

                if (!jsonObject.has("aaData")) {

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
                    jsonObject.accumulate("aaData", data);
                }

            }
            exit = false;

            jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
            jsonObject.accumulate("iDisplayStart", 0);
            jsonObject.accumulate("iDisplayLength", 10);

            response.getWriter().print(jsonObject);

            response.flushBuffer();
        } catch (Exception e) {

            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugIncoming")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        String incomingedit;


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
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size = pharmacyLocationUsersByUserName.size();


        if (size > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (size == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }
        serviceDrugs = Context.getConceptService();

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
        if (incomingedit != null) {
            //edit an entry

            if (incomingedit.equalsIgnoreCase("false")) {

                if (!destination.equalsIgnoreCase(locationVal)) {

                    pStoreIncoming = new ArrayList<PharmacyStoreIncoming>();
                    pStoreOutgoing = new ArrayList<PharmacyStoreOutgoing>();


                    pharmacyStoreIncoming = new PharmacyStoreIncoming();
                    serviceLocation = Context.getLocationService();

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


                }

            } else if (incomingedit.equalsIgnoreCase("true")) {
                if (!destination.equalsIgnoreCase(locationVal)) {

                    pharmacyStoreIncoming = new PharmacyStoreIncoming();
                    pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incominguuid);

                    if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreIncoming.getCreator().getUserId())) {

                        pharmacyStoreIncoming.setDrugs(serviceDrugs.getDrug(incomingdrug));

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

            pharmacyStoreIncoming = new PharmacyStoreIncoming();
            pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incominguuidvoid);

            pharmacyStoreIncoming.setVoided(true);
            pharmacyStoreIncoming.setVoidReason(incomingreason);

            service.savePharmacyStoreIncoming(pharmacyStoreIncoming);

        } else if (incominguuidextra != null) {

            if (supplierout != null) {

                //capture supplier related transactions
                if (service.getPharmacyLocationsByName(destination) != service.getPharmacyLocationsByName(service
                        .getPharmacyLocation())) {
                    incomingnumber = incomingnumberap;
                    Date date = null;
                    try {
                        if (incomingexpirea != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(incomingexpirea);
                        }
                    } catch (ParseException e) {

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


                    pharmacyStore = new PharmacyStore();

                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    drugTransactions = new DrugTransactions();

                    //transactions
                    drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                    drugTransactions.setQuantityOut(0);
                    drugTransactions.setQuantityIn(Integer.parseInt(incomingnumber));
                    drugTransactions.setexpireDate(date);
                    drugTransactions.setCategory(pharmacyStoreApprove.getCategory());
                    drugTransactions.setComment("New entry");

                    drugTransactions
                            .setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());


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
                pharmacyStoreIncoming = new PharmacyStoreIncoming();
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
                    pharmacyStoreIncoming.setMaxLevel(100);
                    pharmacyStoreIncoming.setMinLevel(10);


                    pharmacyStoreIncoming.setSupplier(service.getPharmacySupplierByName(supplierout));


                    pharmacyStore = new PharmacyStore();
                    pharmacyStore.setDrugs(pharmacyStoreIncoming.getDrugs());

                    DrugTransactions drugTransactions = new DrugTransactions();

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
