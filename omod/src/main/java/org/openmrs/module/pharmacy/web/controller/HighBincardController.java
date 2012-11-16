package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Location;
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
import java.util.*;

@Controller
public class HighBincardController {

    private static final Log log = LogFactory.getLog(HighBincardController.class);

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

    private PharmacyStoreIncoming pharmacyStoreIncoming = null;

    private PharmacyStoreOutgoing pharmacyStoreOutgoing = null;

    private Location locationClass = null;

    private Date dateVal = null;

    private int inVal = 0;

    private int outVal = 0;

    private int totalVal = 0;

    private String filter = null;

    private String uuidfilter = null;

    private boolean exit = false;

    private String bindrug;

    private String binquantityin;

    private String binmax;

    private String binmin;

    private String incomingnumber;

    private String binnumber;

    private String binbatch;

    private String bins11;

    private String deliveryno;

    private String uuiddialog;

    private Calendar currentDate;

    private Calendar readDate;

    private Date dateC;

    private GregorianCalendar one;

    private GregorianCalendar two;

    private ConceptService serviceDrugs;

    private Date givenDate;

    public Date newDate = new Date();

    private int monthsDiff;

    private UserContext userService;
    ;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int sizeUsers;
    private List<PharmacyStore> pharmacyInventory;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private int size;
    private List<Drug> drugList;
    private List<Drug> allDrugs;
    private List<PharmacyStore> pharmacyStoreList;
    private PharmacyStore pharmacyStore;
    private DrugTransactions drugTransactions;
    private List<PharmacyStore> pharmacyInventory1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/highBincard")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUsersByUserName.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }


        serviceLocation = Context.getLocationService();
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        uuiddialog = request.getParameter("uuid");
        filter = request.getParameter("sSearch");

        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        dateC = new Date();

        currentDate.setTime(dateC);

        one = new GregorianCalendar();
        two = new GregorianCalendar();

        one.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        pharmacyInventory = service.getPharmacyInventory();
        size = pharmacyInventory.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");


        if (uuiddialog == null) {
            if (filter.length() > 2) {
                originalbindrug = filter;
                drugList = serviceDrugs.getAllDrugs();
                size = drugList.size();
                for (int i = 0; i < size; i++) {
                    uuidfilter = getString(drugList, i, originalbindrug);
                    if (uuidfilter != null)
                        break;

                }
            }
        }
        try {
            if (dialog != null) {

                for (int i = 0; i < size; i++) {
                    if (service.getPharmacyLocationsByUuid(pharmacyInventory.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {

                        JSONArray val = getArrayDialog(pharmacyInventory, i, locationVal);

                        if (val != null)
                            jsonObject.accumulate("aaData", val);
                    }
                    if (exit)
                        break;
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (service.getPharmacyLocationsByUuid(pharmacyInventory.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {
                        JSONArray val = getArray(pharmacyInventory, i, locationVal);

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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/highBincard")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        bindrug = request.getParameter("bindrug");
        binquantityin = request.getParameter("binquantityin");
        String binquantityOut = request.getParameter("binquantityout");
        binmax = request.getParameter("binmax");
        binmin = request.getParameter("binmin");

        userService = Context.getUserContext();

        binnumber = request.getParameter("binnumber");

        binbatch = request.getParameter("binbatch");
        bins11 = request.getParameter("bins11");
        deliveryno = request.getParameter("delivery");

        String binreason = request.getParameter("binreason");
        String binuuidvoid = request.getParameter("binuuidvoid");

        String outgoingexpire = request.getParameter("binexpire");

        String location = request.getParameter("location");
        String less = request.getParameter("less");
        String date = request.getParameter("date");
        String incoming = request.getParameter("incoming");
        String outgoing = request.getParameter("outgoing");
        String total = request.getParameter("total");
        String dosesname = request.getParameter("dosesname");
        String binedit = request.getParameter("binedit");
        String binuuid = request.getParameter("binuuid");
        String bincom = request.getParameter("bincom");

        System.out.println("bindrug=" + bindrug);
        System.out.println("incoming=" + incoming);
        System.out.println("location=" + location);

        if (binuuidvoid == null) {

            originalbindrug = bindrug;
            allDrugs = serviceDrugs.getAllDrugs();

            int dnames = allDrugs.size();
            for (int i = 0; i < dnames; i++) {

                uuid = getString(allDrugs, i, originalbindrug);

                if (uuid != null)
                    break;

            }

        }
        serviceLocation = Context.getLocationService();

        if (total != null) {

            totalVal = Integer.parseInt(total);
        }

        if (binquantityin != null) {

            inVal = Integer.parseInt(binquantityin);
        }

        if (binquantityOut != null) {

            outVal = Integer.parseInt(binquantityOut);
        }
        if (incoming != null) {
            pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incoming);

        }

        if (outgoing != null) {
            pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoing);

        }

        if (location != null) {
            locationClass = serviceLocation.getLocation(location);

        }

        if (date != null) {
            if (!date.isEmpty()) {

                try {
                    dateVal = new SimpleDateFormat("dd-MM-yyyy").parse(date);
                } catch (ParseException e) {
                    // TODO Auto-generated catch blockreturn datad;
                    log.error("Error generated", e);
                }
            }

        }

        if (binedit != null) {
            if (binedit.equalsIgnoreCase("false")) {

                ///check for same entry before saving
                pharmacyStoreList = service.getPharmacyInventory();
                size = pharmacyStoreList.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(pharmacyStoreList, i, bindrug);
                    if (found)
                        break;
                }

                if (!found) {

                    pharmacyStore = new PharmacyStore();

                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    drugTransactions = new DrugTransactions();

                    //transactions
                    drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                    drugTransactions.setQuantityOut(0);
                    drugTransactions.setQuantityIn(Integer.parseInt(binquantityin));
                    drugTransactions.setexpireDate(dateVal);
                    drugTransactions.setComment("New entry");

                    if (location != null) {

                        drugTransactions.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                    } else {

                    }

                    drugTransactions.setexpireDate(dateVal);

                    service.saveDrugTransactions(drugTransactions);

                    pharmacyStore.setQuantity(inVal);
                    pharmacyStore.setQuantityIn(inVal);
                    pharmacyStore.setQuantityOut(outVal);
                    pharmacyStore.setChangeReason(bincom);

                    pharmacyStore.setLocation(locationClass.getUuid());

                    pharmacyStore.setExpireDate(new Date());

                    pharmacyStore.setIncoming(pharmacyStoreIncoming);
                    pharmacyStore.setOutgoing(pharmacyStoreOutgoing);

                    if (!binmax.isEmpty()) {

                        pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                    } else if (binmax.isEmpty()) {
                        pharmacyStore.setMaxLevel(0);
                    }

                    if (!binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                    } else if (binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(0);
                    }

                    service.savePharmacyInventory(pharmacyStore);

                } else //do code to display to the user
                {

                    pharmacyInventory1 = service.getPharmacyInventory();
                    int number = pharmacyInventory1.size();

                    for (int i = 0; i < number; i++) {

                        String uuiddrug = getDrug(pharmacyInventory1, i, bindrug);

                        if (uuiddrug != null) {

                            pharmacyStore = new PharmacyStore();
                            pharmacyStore = service.getPharmacyInventoryByUuid(uuiddrug);

                            int tot = 0;
                            if (less != null) {

                                tot = pharmacyStore.getQuantity() - inVal;
                            } else
                                tot = pharmacyStore.getQuantity() + inVal;

                            pharmacyStore.setQuantity(tot);

                            pharmacyStore.setQuantityIn(inVal);

                            pharmacyStore.setQuantityOut(outVal);

                            pharmacyStore.setChangeReason(bincom);

                            pharmacyStore.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                            pharmacyStore.setExpireDate(dateVal);

                            if (pharmacyStoreIncoming != null) {
                                pharmacyStore.setIncoming(pharmacyStoreIncoming);
                            }
                            if (pharmacyStoreOutgoing != null) {
                                pharmacyStore.setOutgoing(pharmacyStoreOutgoing);
                            }

                            pharmacyStore.setChangeReason(bincom);

                            if (!binmax.isEmpty()) {

                                pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                            } else if (binmax.isEmpty()) {
                                pharmacyStore.setMaxLevel(0);
                            }

                            if (!binmin.isEmpty()) {
                                pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                            } else if (binmin.isEmpty()) {
                                pharmacyStore.setMinLevel(0);
                            }
                            //							//transactions
                            drugTransactions = new DrugTransactions();

                            drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                            drugTransactions.setQuantityOut(0);
                            drugTransactions.setQuantityIn(inVal);
                            drugTransactions.setexpireDate(dateVal);
                            drugTransactions.setComment("Addition to an existing entryz");
                            if (location != null) {

                                drugTransactions.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                            }

                            service.saveDrugTransactions(drugTransactions);

                            service.savePharmacyInventory(pharmacyStore);

                        }

                    }
                }

            } else if (binedit.equalsIgnoreCase("true")) {
                //
                pharmacyStore = new PharmacyStore();
                pharmacyStore = service.getPharmacyInventoryByUuid(binuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStore.getCreator().getUserId())) {

                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    pharmacyStore.setQuantity(inVal);

                    Date date2 = null;
                    try {
                        if (outgoingexpire != null) {
                            date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire.substring(0, 10));
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }

                    pharmacyStore.setQuantity(null);
                    pharmacyStore.setQuantityOut(null);

                    if (!binmax.isEmpty()) {

                        pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                    } else if (binmax.isEmpty()) {
                        pharmacyStore.setMaxLevel(0);
                    }

                    if (!binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                    } else if (binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(0);
                    }

                    pharmacyStore.setExpireDate(date2);

                    pharmacyStore.setBatchNo(Integer.parseInt(binbatch));
                    pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));
                    pharmacyStore.setS11(Integer.parseInt(bins11));

                    pharmacyStore.setChangeReason(bincom);
                    service.savePharmacyInventory(pharmacyStore);
                    //
                }
            }
        } else if (binuuidvoid != null) {
            pharmacyStore = new PharmacyStore();
            pharmacyStore = service.getPharmacyInventoryByUuid(binuuidvoid);

            pharmacyStore.setVoided(true);
            pharmacyStore.setVoidReason(binreason);

            service.savePharmacyInventory(pharmacyStore);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size, String location) {

        if (filter.length() > 2) {

            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {
                if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                        .equalsIgnoreCase(location)) {

                    readDate.setTime(pharmacyStore.get(size).getExpireDate());

                    two.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));

                    if (daysBetween(one.getTime(), two.getTime()) <= 0) {
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
                        data.put(pharmacyStore.get(size).getUuid());
                        data.put(pharmacyStore.get(size).getDrugs().getName());
                        data.put(pharmacyStore.get(size).getQuantity());
                        if (pharmacyStore.get(size).getCategory() == null) {

                            data.put("test");
                        } else
                            data.put(pharmacyStore.get(size).getCategory().getName());

                        data.put(pharmacyStore.get(size).getMinLevel());

                        data.put(pharmacyStore.get(size).getMaxLevel());
                        data.put(pharmacyStore.get(size).getExpireDate());

                        data.put(pharmacyStore.get(size).getBatchNo());
                        data.put(pharmacyStore.get(size).getS11());
                        data.put(pharmacyStore.get(size).getDeliveryNo());
                        if (pharmacyStore.get(size).getSupplier() == null) {

                            data.put("pending");
                        } else
                            data.put(pharmacyStore.get(size).getSupplier().getName());

                        if (deletePharmacy) {
                            data.put("<img src='/openmrs/moduleResources/pharmacy/images/delete.png' />");
                            deletePharmacy = false;
                        } else
                            data.put("");

                        return data;

                    } else
                        return null;

                }
            } else {
                return null;

            }

        } else {

            if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                    .equalsIgnoreCase(location)) {

                readDate.setTime(pharmacyStore.get(size).getExpireDate());

                two.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));
                givenDate = pharmacyStore.get(size).getExpireDate();

                monthsDiff = ((givenDate.getYear() - newDate.getYear()) * 12) + (givenDate.getMonth() - newDate.getMonth());

                if (monthsDiff <= 6) {
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
                    data.put(pharmacyStore.get(size).getUuid());
                    data.put(pharmacyStore.get(size).getDrugs().getName());

                    data.put(pharmacyStore.get(size).getQuantity());
                    if (pharmacyStore.get(size).getCategory() == null) {

                        data.put("test");
                    } else
                        data.put(pharmacyStore.get(size).getCategory().getName());

                    data.put(pharmacyStore.get(size).getMinLevel());

                    data.put(pharmacyStore.get(size).getMaxLevel());
                    data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getDeliveryNo());
                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    if (deletePharmacy) {
                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/delete.png' />");
                        deletePharmacy = false;
                    } else
                        data.put("");

                    return data;
                } else {
                    return null;

                }

            }

        }
        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStore> pharmacyStore, int size, String location) {
        if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                .equalsIgnoreCase(location)) {
            if (uuiddialog != null) {

                if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid()
                        .equals(pharmacyStore.get(size).getDrugs().getUuid())) {

                    datad = new JSONArray();
                    datad.put(pharmacyStore.get(size).getUuid());
                    datad.put(pharmacyStore.get(size).getDrugs().getName());

                    datad.put(pharmacyStore.get(size).getQuantity());

                    readDate.setTime(pharmacyStore.get(size).getExpireDate());

                    two.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));

                    datad.put(daysBetween(two.getTime(), one.getTime()));//<input type="checkbox" id="check1" />

                    datad.put("<input type='checkbox' id='check1'/>");
                    return datad;

                } else {

                    datad = new JSONArray();
                    datad.put("Not in store");
                    datad.put("Not in store");

                    datad.put("Not in store");

                    readDate.setTime(pharmacyStore.get(size).getExpireDate());

                    datad.put("Not in store");

                    datad.put("Not in store");
                    return datad;
                }
            } else
                return null;

        } else
            return null;

    }

    public synchronized int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public synchronized String getDropDown(List<PharmacyStore> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public synchronized boolean getCheck(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<PharmacyStore> pharmacyStore, int size, String name) {
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
