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
import org.openmrs.module.pharmacy.model.DrugTransactions;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.model.PharmacyStoreApproved;
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
public class DrugApprovedController {

    private static final Log log = LogFactory.getLog(DrugApprovedController.class);

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

    private String filter = null;

    private String uuidfilter = null;

    private String approveddrug;

    private String approvedquantityin;

    private String approvedmax;

    private String approvedbatch;

    private String approveds11;

    private String approvedmin;

    private String approvednumber;

    private boolean exit = false;

    private String answers;

    private String uuid1;

    private String uuid2;

    private String uuid3;

    private String uuid4;

    private ConceptService serviceDrugs;

    private UserContext userService;

    private String requisition;

    private String issued;

    private String authorized;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugApproved")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
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
        if (filter == null)
            filter = "a";

        List<PharmacyStoreApproved> List = service.getPharmacyStoreApproved();
        int size = List.size();

        JSONObject json = new JSONObject();
        JSONArray jsons = new JSONArray();
        response.setContentType("application/json");

        if (filter.length() > 2) {
            originalbindrug = filter;

            serviceLocation = Context.getLocationService();
            serviceDrugs = Context.getConceptService();

            //			approveddrug = filter.substring(0, filter.indexOf("("));
            //
            //			drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
            //			drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
            //			formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
            //
            //
            //		String uuidvalue = service.getDrugNameByName(approveddrug).getUuid();
            //		String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
            //		String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
            //		String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();
            //
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
                    if (List.get(i).getDestination().getName().equalsIgnoreCase(locationVal)) {

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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugApproved")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String locationVal = null;

        String[] drugId;
        String[] drugQ;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = locationVal;

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }


        serviceLocation = Context.getLocationService();
        approveddrug = request.getParameter("approveddrug");
        approvedquantityin = request.getParameter("approvedquantityin");

        approvednumber = request.getParameter("approvednumber");
        approvedmax = request.getParameter("approvedmax");
        approvedmin = request.getParameter("approvedmin");
        approvedmin = request.getParameter("approvedmin");

        requisition = request.getParameter("requisitionp");
        issued = request.getParameter("issued");
        authorized = request.getParameter("authorizedp");

        userService = Context.getUserContext();
        answers = request.getParameter("answers");

        uuid1 = request.getParameter("one");
        uuid2 = request.getParameter("two");
        uuid3 = request.getParameter("three");
        uuid4 = request.getParameter("four");

        approvedbatch = request.getParameter("approvedbatch");
        approveds11 = request.getParameter("approveds11");

        String approvedexpire = request.getParameter("approvedexpirea");

        String approvedreason = request.getParameter("approvedreason");
        String approveduuidvoid = request.getParameter("approveduuidvoid");

        String approveduuidextra = request.getParameter("approveduuidextra");

        String destination = request.getParameter("destination");
        String location = request.getParameter("location");
        String supplier = request.getParameter("supplierout");
        String transactions = request.getParameter("transactions");
        String deliveryno = request.getParameter("delivery");

        String approvededit = request.getParameter("approvededit");
        String approveduuid = request.getParameter("approveduuid");
        String approvedcom = request.getParameter("approvedcom");

        List<PharmacyStore> pharmacyStoreArray = new ArrayList<PharmacyStore>();
        List<DrugTransactions> drugTransactionArray = new ArrayList<DrugTransactions>();


        originalbindrug = approveddrug;


        drugId = request.getParameterValues("drugId");


        drugQ = request.getParameterValues("quantity");


        if (approveduuidvoid == null) {

            //		approveddrug = approveddrug.substring(0, approveddrug.indexOf("("));
            //
            //		drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
            //		drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
            //		formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
            //
            //
            //		String uuidvalue = service.getDrugNameByName(approveddrug).getUuid();
            //		String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
            //		String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
            //		String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();
            //
            //

            serviceDrugs = Context.getConceptService();

            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuid = getString(dname, i, originalbindrug);
                if (uuid != null)
                    break;

            }

        }
        if (approvededit != null) {
            if (approvededit.equalsIgnoreCase("false")) {

                ///check for same entry before saving
                List<PharmacyStoreApproved> list = service.getPharmacyStoreApproved();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, approveddrug);
                    if (found)
                        break;
                }

                PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();

                //get drug details 		drugstrength drugunit formulation

                pharmacyStoreApproved.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                pharmacyStoreApproved.setQuantityIn(Integer.parseInt(approvedquantityin));

                if (approvedmax != null) {

                    pharmacyStoreApproved.setMaxLevel(Integer.parseInt(approvedmax));

                } else if (approvedmax == null) {
                    pharmacyStoreApproved.setMaxLevel(0);
                }

                if (approvedmin != null) {
                    pharmacyStoreApproved.setMinLevel(Integer.parseInt(approvedmin));

                } else if (approvedmin == null) {
                    pharmacyStoreApproved.setMinLevel(0);
                }

                if (approvedbatch != null) {
                    pharmacyStoreApproved.setBatchNo(Integer.parseInt(approvedbatch));

                } else if (approvedbatch == null) {
                    pharmacyStoreApproved.setBatchNo(0);
                }

                if (approveds11 != null) {
                    pharmacyStoreApproved.setS11(Integer.parseInt(approveds11));

                } else if (approveds11 == null) {
                    pharmacyStoreApproved.setS11(0);
                }

                Date date = null;
                try {
                    if (approvedexpire != null) {
                        date = new SimpleDateFormat("MM/dd/yyyy").parse(approvedexpire);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    log.error("Error generated", e);
                }

                pharmacyStoreApproved.setExpireDate(date);
                serviceLocation = Context.getLocationService();

                pharmacyStoreApproved.setDestination(service.getPharmacyLocationsByName(destination));
                pharmacyStoreApproved.setLocation(service.getPharmacyLocationsByName(locationVal));

                pharmacyStoreApproved.setChangeReason(null);

                if (supplier == null) {
                    pharmacyStoreApproved.setSupplier(null);

                } else
                    pharmacyStoreApproved.setSupplier(service.getPharmacySupplierByName(supplier));

                pharmacyStoreApproved.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                service.savePharmacyStoreApproved(pharmacyStoreApproved);

            } else if (approvededit.equalsIgnoreCase("true")) {

                PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();
                pharmacyStoreApproved = service.getPharmacyStoreApprovedByUuid(approveduuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreApproved.getCreator().getUserId())) {

                    pharmacyStoreApproved.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    pharmacyStoreApproved.setQuantityIn(Integer.parseInt(approvedquantityin));

                    pharmacyStoreApproved.setMaxLevel(0);

                    pharmacyStoreApproved.setMinLevel(0);

                    if (approvedbatch != null) {
                        pharmacyStoreApproved.setBatchNo(Integer.parseInt(approvedbatch));

                    } else if (approvedbatch == null) {
                        pharmacyStoreApproved.setBatchNo(0);
                    }

                    if (approveds11 != null) {
                        pharmacyStoreApproved.setS11(Integer.parseInt(approveds11));

                    } else if (approveds11 == null) {
                        pharmacyStoreApproved.setS11(0);
                    }

                    Date date = null;
                    try {
                        if (approvedexpire != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(approvedexpire);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }
                    pharmacyStoreApproved.setExpireDate(date);
                    serviceLocation = Context.getLocationService();

                    pharmacyStoreApproved.setDestination(service.getPharmacyLocationsByName(destination));
                    pharmacyStoreApproved.setLocation(service.getPharmacyLocationsByName(location));

                    pharmacyStoreApproved.setChangeReason(null);

                    if (supplier == null) {
                        pharmacyStoreApproved.setSupplier(null);

                    } else
                        pharmacyStoreApproved.setSupplier(service.getPharmacySupplierByName(supplier));

                    pharmacyStoreApproved.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                    service.savePharmacyStoreApproved(pharmacyStoreApproved);

                }
            }

        } else if (approveduuidvoid != null) {
            PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();
            pharmacyStoreApproved = service.getPharmacyStoreApprovedByUuid(approveduuidvoid);

            pharmacyStoreApproved.setVoided(true);
            pharmacyStoreApproved.setVoidReason(approvedreason);

            service.savePharmacyStoreApproved(pharmacyStoreApproved);

        } else if (approveduuidextra != null) {

            for (int y = 0; y < drugId.length; y++) {
                System.out.println(drugId[y]);


                PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();
                pharmacyStoreApproved = service.getPharmacyStoreApprovedByUuid(drugId[y]);

                if (requisition != null && authorized != null) {
                    pharmacyStoreApproved.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApproved.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApproved.setIssued(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));
                }


                PharmacyStore pharmacyStore = new PharmacyStore();

                pharmacyStore.setDrugs(pharmacyStoreApproved.getDrugs());
                pharmacyStore.setQuantity(pharmacyStoreApproved.getQuantityIn());

                pharmacyStore.setBatchNo(pharmacyStoreApproved.getBatchNo());
                pharmacyStore.setCategory(pharmacyStoreApproved.getCategory());

                pharmacyStore.setDeliveryNo(pharmacyStoreApproved.getDeliveryNo());
                pharmacyStore.setExpireDate(pharmacyStoreApproved.getDateCreated());
                pharmacyStore.setIncoming(pharmacyStoreApproved.getIncoming());

                pharmacyStore.setLocation(pharmacyStoreApproved.getDestination().getUuid());


                pharmacyStore.setMaxLevel(pharmacyStoreApproved.getMaxLevel());
                pharmacyStore.setMinLevel(pharmacyStoreApproved.getMinLevel());
                pharmacyStore.setS11(pharmacyStoreApproved.getS11());
                pharmacyStore.setSupplier(pharmacyStoreApproved.getSupplier());
                pharmacyStore.setTransaction(pharmacyStoreApproved.getTransaction());


                pharmacyStoreArray.add(pharmacyStore);


                DrugTransactions drugTransaction = new DrugTransactions();

                drugTransaction.setDrugs(pharmacyStoreApproved.getDrugs());
                drugTransaction.setQuantityIn(0);
                drugTransaction.setQuantityOut(Integer.parseInt(drugQ[y]));
                drugTransaction.setexpireDate(pharmacyStoreApproved.getDateCreated());
                drugTransaction.setComment("Give out");

                drugTransaction.setLocation(pharmacyStoreApproved.getDestination().getUuid());

                drugTransactionArray.add(drugTransaction);


                if (requisition != null && issued != null && authorized != null) {
                    pharmacyStoreApproved.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApproved.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApproved.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
                }
                service.savePharmacyInventory(pharmacyStoreArray);
                service.saveDrugTransactions(drugTransactionArray);
                pharmacyStoreApproved.setApproved(true);
                service.savePharmacyStoreApproved(pharmacyStoreApproved);

            }


            if (approveds11 == "app") {

                PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();
                pharmacyStoreApproved = service.getPharmacyStoreApprovedByUuid(approveduuidextra);
                /* String to split. */

                String[] temp;

                /* delimiter */
                String delimiter = ",";
                /* given string will be split by the argument delimiter provided. */
                temp = answers.split(delimiter);
                int total = 0;
                String name = "uuid";

                for (int i = 0; i < temp.length; i++) {
                    if (!temp[i].isEmpty())
                        total += Integer.parseInt(temp[i]);

                    if (i >= 1) {

                        if (i == 1) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid1);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        } else if (i == 2) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid2);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 3) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid3);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 4) {
                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid4);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        }

                    }

                }

                if (total == pharmacyStoreApproved.getQuantityIn()) {

                    pharmacyStoreApproved.setApproved(true);
                    pharmacyStoreApproved.setQuantityIn(0);
                } else {
                    if (total < pharmacyStoreApproved.getQuantityIn()) {
                        pharmacyStoreApproved.setQuantityIn((pharmacyStoreApproved.getQuantityIn() - total));
                    }

                }

                pharmacyStoreApproved.setS11(Integer.parseInt(approveds11));


                if (requisition != null && issued != null && authorized != null) {
                    pharmacyStoreApproved.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApproved.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApproved.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
                }

                //pharmacyStoreApproved.setSupplier(service.getPharmacySupplierByName(supplier));

                DrugTransactions drugTransactions = new DrugTransactions();

                //transactions
                drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                drugTransactions.setQuantityOut(0);
                drugTransactions.setQuantityIn(Integer.parseInt(approvednumber));
                drugTransactions.setexpireDate(null);
                drugTransactions.setComment("Give out");

                drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                //drugTransactions.setLocation(location)
                service.saveDrugTransactions(drugTransactions);

                //				pharmacyStore.setLocation(serviceLocation.getLocation(service.getPharmacyLocation()).getUuid());
                //
                //
                //				pharmacyStore.setQuantity(Integer.parseInt(incomingnumber));
                //
                //				pharmacyStore.setQuantityIn(0);
                //				pharmacyStore.setQuantityOut(0);
                //				pharmacyStore.setChangeReason(null);
                //
                //
                //
                //
                //				pharmacyStore.setBatchNo(Integer.parseInt(incomingbatch));
                //				pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));
                //
                //
                //				pharmacyStore.setExpireDate(date);
                //				pharmacyStore.setMaxLevel(Integer.parseInt(incomingmax));
                //				pharmacyStore.setMinLevel(Integer.parseInt(incomingmin));
                //				pharmacyStore.setS11(Integer.parseInt(incomings11));
                //
                //				pharmacyStore.setSupplier(service.getPharmacySupplierByName(supplier));
                //				pharmacyStore.setTransaction(pharmacyStoreIncoming.getTransaction());
                //				pharmacyStore.setIncoming(service.getPharmacyStoreIncomingByUuid(incominguuidextra));
                //
                //
                //
                //				service.savePharmacyInventory(pharmacyStore);

                service.savePharmacyStoreApproved(pharmacyStoreApproved);
            }

        }

    }

    public synchronized JSONArray getArray(List<PharmacyStoreApproved> pharmacyApproved, int size, String location) {

        if (filter.length() > 2) {

            if (uuidfilter.equalsIgnoreCase(pharmacyApproved.get(size).getDrugs().getUuid())) {

                if ((pharmacyApproved.get(size).getDestination().getName().equalsIgnoreCase(location))
                        && (!pharmacyApproved.get(size).getApproved())) {

                    data = new JSONArray();

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

                    if (editPharmacy) {

                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");


                    data.put("");
                    data.put(pharmacyApproved.get(size).getUuid());
                    data.put(pharmacyApproved.get(size).getDrugs().getName());
                    data.put(pharmacyApproved.get(size).getQuantityIn());

                    data.put(pharmacyApproved.get(size).getLocation().getName());
                    data.put(pharmacyApproved.get(size).getDestination().getName());

                    data.put(pharmacyApproved.get(size).getTransaction().getName());

                    if (pharmacyApproved.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyApproved.get(size).getSupplier().getName());

                    data.put(pharmacyApproved.get(size).getBatchNo());
                    data.put(pharmacyApproved.get(size).getS11());
                    data.put(pharmacyApproved.get(size).getExpireDate());
                    data.put(pharmacyApproved.get(size).getDeliveryNo());

                    data.put("");
                    if (pharmacyApproved.get(size).getApproved()) {

                        data.put("<dfn>Approved By:" + pharmacyApproved.get(size).getCreator().getNames() + "<dfn/>");
                    } else
                        data.put("Approve");

                    if (deletePharmacy) {
                        data.put("Delete");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    data.put(pharmacyApproved.get(size).getStatus());
                    data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
//


                    if (pharmacyApproved.get(size).getAuthorized() != null)
                        data.put(pharmacyApproved.get(size).getAuthorized().getUsername());
                    else
                        data.put("null");

                    if (pharmacyApproved.get(size).getissued() != null)
                        data.put(pharmacyApproved.get(size).getissued().getUsername());
                    else
                        data.put("null");
                    return data;
                }
            } else {
                return null;
            }

        } else {
            if ((pharmacyApproved.get(size).getDestination().getName().equalsIgnoreCase(location)) && (!pharmacyApproved.get(size).getApproved())) {

                data = new JSONArray();
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
                if (editPharmacy) {

                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");

                data.put("");
                data.put(pharmacyApproved.get(size).getUuid());
                data.put(pharmacyApproved.get(size).getDrugs().getName());
                data.put(pharmacyApproved.get(size).getQuantityIn());

                data.put(pharmacyApproved.get(size).getLocation().getName());
                data.put(pharmacyApproved.get(size).getDestination().getName());

                data.put(pharmacyApproved.get(size).getTransaction().getName());

                if (pharmacyApproved.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyApproved.get(size).getSupplier().getName());

                data.put(pharmacyApproved.get(size).getBatchNo());
                data.put(pharmacyApproved.get(size).getS11());
                data.put(pharmacyApproved.get(size).getExpireDate());
                data.put(pharmacyApproved.get(size).getDeliveryNo());

                data.put("");
                if (pharmacyApproved.get(size).getApproved()) {

                    data.put("<dfn>Approved By:" + pharmacyApproved.get(size).getCreator().getNames() + "<dfn/>");
                } else
                    data.put("Approve");

                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");
                data.put(pharmacyApproved.get(size).getStatus() + "[" + pharmacyApproved.get(size).getDateCreated().toString().substring(0, 10) + "]");
                data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
//


                if (pharmacyApproved.get(size).getAuthorized() != null)
                    data.put(pharmacyApproved.get(size).getAuthorized().getUsername());
                else
                    data.put("null");

                if (pharmacyApproved.get(size).getissued() != null)
                    data.put(pharmacyApproved.get(size).getissued().getUsername());
                else
                    data.put("null");

                return data;
            }
        }
        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStoreApproved> pharmacyStore, int size) {

        datad = new JSONArray();

        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());
        datad.put("");
        datad.put("");

        return datad;
    }

    public synchronized String getDropDown(List<PharmacyStoreApproved> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public synchronized boolean getCheck(List<PharmacyStoreApproved> pharmacyStore, int size, String name) {

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<PharmacyStoreApproved> pharmacyStore, int size, String name) {
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
