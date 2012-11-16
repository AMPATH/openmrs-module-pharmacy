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
    private String formulation;

    private String originalbindrug;

    private String dialog = null;

    private LocationService serviceLocation;

    private String filter = null;

    private String uuidfilter = null;

    private String approveddrug, approvedquantityin, approvedmax, approvedbatch, approveds11, approvedmin, approvednumber;

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

    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;

    private int sizeUsers;

    private List<PharmacyStoreApproved> pharmacyStoreApproved;
    private int size, size1;
    private List<Drug> allDrugs;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName1;
    private List<PharmacyStore> pharmacyStoreArray;
    private List<DrugTransactions> drugTransactionArray;
    private PharmacyStoreApproved pharmacyStoreApprovedObject;
    private PharmacyStore pharmacyStore;
    private DrugTransactions drugTransactions;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugApproved")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUsersByUserName.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();
        }

        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        filter = request.getParameter("sSearch");
        if (filter == null)
            filter = "a";

        pharmacyStoreApproved = service.getPharmacyStoreApproved();
        size = pharmacyStoreApproved.size();

        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");
        //check datatabe filter
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
                    // get the drugs with quantity
                    jsonObject.accumulate("aaData", getDrugDetailsForDialog(pharmacyStoreApproved, i));
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (pharmacyStoreApproved.get(i).getDestination().getName().equalsIgnoreCase(locationVal)) {

                        JSONArray jsonArray1 = getDetailsForDatables(pharmacyStoreApproved, i, locationVal);
                        if (jsonArray1 != null)
                            jsonObject.accumulate("aaData", jsonArray1);

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
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugApproved")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {


        approveddrug = request.getParameter("approveddrug");
        approvedquantityin = request.getParameter("approvedquantityin");
        approvednumber = request.getParameter("approvednumber");
        approvedmax = request.getParameter("approvedmax");
        approvedmin = request.getParameter("approvedmin");
        approvedmin = request.getParameter("approvedmin");
        requisition = request.getParameter("requisitionp");
        issued = request.getParameter("issued");
        authorized = request.getParameter("authorizedp");
        answers = request.getParameter("answers");
        uuid1 = request.getParameter("one");
        uuid2 = request.getParameter("two");
        uuid3 = request.getParameter("three");
        uuid4 = request.getParameter("four");
        approvedbatch = request.getParameter("approvedbatch");
        approveds11 = request.getParameter("approveds11");

        String approvedExpire = request.getParameter("approvedexpirea");

        String approvedReason = request.getParameter("approvedReason");
        String approvedUuidVoid = request.getParameter("approvedUuidVoid");

        String approvedUuidExtra = request.getParameter("approvedUuidExtra");

        String destination = request.getParameter("destination");
        String location = request.getParameter("location");
        String supplier = request.getParameter("supplierout");
        String transactions = request.getParameter("transactions");
        String deliveryNo = request.getParameter("delivery");

        String approvedEdit = request.getParameter("approvedEdit");
        String approvedUuid = request.getParameter("approvedUuid");
        String approvedCom = request.getParameter("approvedCom");


        String locationVal = null;

        String[] drugId;
        String[] drugQ;
        service = Context.getService(PharmacyService.class);

        userService = Context.getUserContext();


        pharmacyLocationUsersByUserName1 = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size = pharmacyLocationUsersByUserName1.size();

        if (size > 1) {

            locationVal = locationVal;

        } else if (size == 1) {
            locationVal = pharmacyLocationUsersByUserName1.get(0).getLocation();
        }


        serviceLocation = Context.getLocationService();


        pharmacyStoreArray = new ArrayList<PharmacyStore>();
        drugTransactionArray = new ArrayList<DrugTransactions>();
        originalbindrug = approveddrug;
        drugId = request.getParameterValues("drugId");
        drugQ = request.getParameterValues("quantity");


        if (approvedUuidVoid == null) {
            serviceDrugs = Context.getConceptService();
            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuid = getString(dname, i, originalbindrug);
                if (uuid != null)
                    break;

            }

        }
        if (approvedEdit != null) {
            if (approvedEdit.equalsIgnoreCase("false")) {

                ///check for same entry before saving
                pharmacyStoreApproved = service.getPharmacyStoreApproved();
                int size = pharmacyStoreApproved.size();
                for (int i = 0; i < size; i++) {
                    found = getCheck(pharmacyStoreApproved, i, approveddrug);
                    if (found)
                        break;
                }

                pharmacyStoreApprovedObject = new PharmacyStoreApproved();

                pharmacyStoreApprovedObject.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                pharmacyStoreApprovedObject.setQuantityIn(Integer.parseInt(approvedquantityin));

                if (approvedmax != null) {

                    pharmacyStoreApprovedObject.setMaxLevel(Integer.parseInt(approvedmax));

                } else if (approvedmax == null) {
                    pharmacyStoreApprovedObject.setMaxLevel(0);
                }

                if (approvedmin != null) {
                    pharmacyStoreApprovedObject.setMinLevel(Integer.parseInt(approvedmin));

                } else if (approvedmin == null) {
                    pharmacyStoreApprovedObject.setMinLevel(0);
                }

                if (approvedbatch != null) {
                    pharmacyStoreApprovedObject.setBatchNo(Integer.parseInt(approvedbatch));

                } else if (approvedbatch == null) {
                    pharmacyStoreApprovedObject.setBatchNo(0);
                }

                if (approveds11 != null) {
                    pharmacyStoreApprovedObject.setS11(Integer.parseInt(approveds11));

                } else if (approveds11 == null) {
                    pharmacyStoreApprovedObject.setS11(0);
                }

                Date date = null;
                try {
                    if (approvedExpire != null) {
                        date = new SimpleDateFormat("MM/dd/yyyy").parse(approvedExpire);
                    }
                } catch (ParseException e) {
                    log.error("Error generated", e);
                }

                pharmacyStoreApprovedObject.setExpireDate(date);
                serviceLocation = Context.getLocationService();

                pharmacyStoreApprovedObject.setDestination(service.getPharmacyLocationsByName(destination));
                pharmacyStoreApprovedObject.setLocation(service.getPharmacyLocationsByName(locationVal));

                pharmacyStoreApprovedObject.setChangeReason(null);

                if (supplier == null) {
                    pharmacyStoreApprovedObject.setSupplier(null);

                } else
                    pharmacyStoreApprovedObject.setSupplier(service.getPharmacySupplierByName(supplier));

                pharmacyStoreApprovedObject.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                service.savePharmacyStoreApproved(pharmacyStoreApprovedObject);

            } else if (approvedEdit.equalsIgnoreCase("true")) {
                //editing an entry

                pharmacyStoreApprovedObject = new PharmacyStoreApproved();
                pharmacyStoreApprovedObject = service.getPharmacyStoreApprovedByUuid(approvedUuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreApprovedObject.getCreator().getUserId())) {

                    pharmacyStoreApprovedObject.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    pharmacyStoreApprovedObject.setQuantityIn(Integer.parseInt(approvedquantityin));

                    pharmacyStoreApprovedObject.setMaxLevel(0);

                    pharmacyStoreApprovedObject.setMinLevel(0);

                    if (approvedbatch != null) {
                        pharmacyStoreApprovedObject.setBatchNo(Integer.parseInt(approvedbatch));

                    } else if (approvedbatch == null) {
                        pharmacyStoreApprovedObject.setBatchNo(0);
                    }

                    if (approveds11 != null) {
                        pharmacyStoreApprovedObject.setS11(Integer.parseInt(approveds11));

                    } else if (approveds11 == null) {
                        pharmacyStoreApprovedObject.setS11(0);
                    }

                    Date date = null;
                    try {
                        if (approvedExpire != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(approvedExpire);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }
                    pharmacyStoreApprovedObject.setExpireDate(date);
                    serviceLocation = Context.getLocationService();

                    pharmacyStoreApprovedObject.setDestination(service.getPharmacyLocationsByName(destination));
                    pharmacyStoreApprovedObject.setLocation(service.getPharmacyLocationsByName(location));

                    pharmacyStoreApprovedObject.setChangeReason(null);

                    if (supplier == null) {
                        pharmacyStoreApprovedObject.setSupplier(null);

                    } else
                        pharmacyStoreApprovedObject.setSupplier(service.getPharmacySupplierByName(supplier));

                    pharmacyStoreApprovedObject.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                    service.savePharmacyStoreApproved(pharmacyStoreApprovedObject);

                }
            }

        } else if (approvedUuidVoid != null) {

            //void an entry
            pharmacyStoreApprovedObject = new PharmacyStoreApproved();
            pharmacyStoreApprovedObject = service.getPharmacyStoreApprovedByUuid(approvedUuidVoid);

            pharmacyStoreApprovedObject.setVoided(true);
            pharmacyStoreApprovedObject.setVoidReason(approvedReason);

            service.savePharmacyStoreApproved(pharmacyStoreApprovedObject);

        } else if (approvedUuidExtra != null) {
            // approve
            for (int y = 0; y < drugId.length; y++) {

                pharmacyStoreApprovedObject = new PharmacyStoreApproved();
                pharmacyStoreApprovedObject = service.getPharmacyStoreApprovedByUuid(drugId[y]);

                if (requisition != null && authorized != null) {
                    pharmacyStoreApprovedObject.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApprovedObject.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApprovedObject.setIssued(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));
                }


                pharmacyStore = new PharmacyStore();

                pharmacyStore.setDrugs(pharmacyStoreApprovedObject.getDrugs());
                pharmacyStore.setQuantity(pharmacyStoreApprovedObject.getQuantityIn());

                pharmacyStore.setBatchNo(pharmacyStoreApprovedObject.getBatchNo());
                pharmacyStore.setCategory(pharmacyStoreApprovedObject.getCategory());

                pharmacyStore.setDeliveryNo(pharmacyStoreApprovedObject.getDeliveryNo());
                pharmacyStore.setExpireDate(pharmacyStoreApprovedObject.getDateCreated());
                pharmacyStore.setIncoming(pharmacyStoreApprovedObject.getIncoming());

                pharmacyStore.setLocation(pharmacyStoreApprovedObject.getDestination().getUuid());


                pharmacyStore.setMaxLevel(pharmacyStoreApprovedObject.getMaxLevel());
                pharmacyStore.setMinLevel(pharmacyStoreApprovedObject.getMinLevel());
                pharmacyStore.setS11(pharmacyStoreApprovedObject.getS11());
                pharmacyStore.setSupplier(pharmacyStoreApprovedObject.getSupplier());
                pharmacyStore.setTransaction(pharmacyStoreApprovedObject.getTransaction());


                pharmacyStoreArray.add(pharmacyStore);


                DrugTransactions drugTransaction = new DrugTransactions();

                drugTransaction.setDrugs(pharmacyStoreApprovedObject.getDrugs());
                drugTransaction.setQuantityIn(0);
                drugTransaction.setQuantityOut(Integer.parseInt(drugQ[y]));
                drugTransaction.setexpireDate(pharmacyStoreApprovedObject.getDateCreated());
                drugTransaction.setComment("Give out");

                drugTransaction.setLocation(pharmacyStoreApprovedObject.getDestination().getUuid());

                drugTransactionArray.add(drugTransaction);


                if (requisition != null && issued != null && authorized != null) {
                    pharmacyStoreApprovedObject.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApprovedObject.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApprovedObject.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
                }
                service.savePharmacyInventory(pharmacyStoreArray);
                service.saveDrugTransactions(drugTransactionArray);
                pharmacyStoreApprovedObject.setApproved(true);
                service.savePharmacyStoreApproved(pharmacyStoreApprovedObject);

            }

            // approve an larger file of request
            if (approveds11 == "app") {


                pharmacyStoreApprovedObject = new PharmacyStoreApproved();
                pharmacyStoreApprovedObject = service.getPharmacyStoreApprovedByUuid(approvedUuidExtra);
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

                            pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid1);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        } else if (i == 2) {

                            pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid2);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 3) {

                            pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid3);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 4) {
                            pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid4);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        }

                    }

                }

                if (total == pharmacyStoreApprovedObject.getQuantityIn()) {

                    pharmacyStoreApprovedObject.setApproved(true);
                    pharmacyStoreApprovedObject.setQuantityIn(0);
                } else {
                    if (total < pharmacyStoreApprovedObject.getQuantityIn()) {
                        pharmacyStoreApprovedObject.setQuantityIn((pharmacyStoreApprovedObject.getQuantityIn() - total));
                    }

                }

                pharmacyStoreApprovedObject.setS11(Integer.parseInt(approveds11));


                if (requisition != null && issued != null && authorized != null) {
                    pharmacyStoreApprovedObject.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreApprovedObject.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreApprovedObject.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
                }
                drugTransactions = new DrugTransactions();

                //transactions
                drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                drugTransactions.setQuantityOut(0);
                drugTransactions.setQuantityIn(Integer.parseInt(approvednumber));
                drugTransactions.setexpireDate(null);
                drugTransactions.setComment("Give out");

                drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                service.saveDrugTransactions(drugTransactions);

                service.savePharmacyStoreApproved(pharmacyStoreApproved);
            }

        }

    }

    public synchronized JSONArray getDetailsForDatables(List<PharmacyStoreApproved> pharmacyApproved, int size, String location) {
        // for datatables filter
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

        } else    // get the details for datatables
        {
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

    public synchronized JSONArray getDrugDetailsForDialog(List<PharmacyStoreApproved> pharmacyStore, int size) {

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
