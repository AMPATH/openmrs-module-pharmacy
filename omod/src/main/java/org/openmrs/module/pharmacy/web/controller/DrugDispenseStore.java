/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugDispenseSettings;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 *
 */
@Controller
public class DrugDispenseStore {

    private static final Log log = LogFactory.getLog(DrugDispenseStore.class);

    private String dialogShow;

    private PharmacyService service;

    private int size;

    private LocationService serviceLocation;

    private JSONArray datad2;

    JSONObject json;

    private JSONArray datadFrm;

    private String dialog;

    private Calendar currentDate;

    private Calendar readDate;

    private Date dateC;

    private GregorianCalendar one;

    private GregorianCalendar two;

    private String batchNo;

    private String batchName;

    private String drugName;

    private String delivery;

    private int sizeDrug;

    private String inventoryNo;

    private String voiduuid;

    private String voidreason;

    private String uuidedit;

    private String dispenseedit;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size1, size2, size3;
    private List<PharmacyLocationUsers> locationUsersByUserName;
    private List<DrugDispenseSettings> drugDispenseSettings;

    private List<PharmacyStore> pharmacyStoreList;
    private DrugDispenseSettings drugDispense;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDispenseStore")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size1 = pharmacyLocationUsersByUserName.size();


        if (size1 > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (size1 == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }
        userService = Context.getUserContext();
        dialogShow = request.getParameter("drugID");
        dialog = request.getParameter("uuid");

        serviceLocation = Context.getLocationService();
        datadFrm = new JSONArray();

        drugDispenseSettings = service.getDrugDispenseSettings();

        size = drugDispenseSettings.size();
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        dateC = new Date();

        currentDate.setTime(dateC);

        one = new GregorianCalendar();
        two = new GregorianCalendar();

        one.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        try {

            if (dialog != null) {
                json = new JSONObject();
                pharmacyStoreList = service.getPharmacyInventory();
                size2 = pharmacyStoreList.size();

                for (int i = 0; i < size2; i++) {
                    if (service.getPharmacyLocationsByUuid(pharmacyStoreList.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {

                        datadFrm = new JSONArray();

                        datadFrm = getArray(pharmacyStoreList, i, dialog, locationVal);
                        if (datadFrm != null)
                            json.accumulate("aaData", datadFrm);
                    }

                }

                if (!json.has("aaData")) {
                    datad2 = new JSONArray();

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    json.accumulate("aaData", datad2);
                }
                dialog = null;
            }

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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDispenseStore")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        locationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        size3 = locationUsersByUserName.size();


        if (size3 > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (size3 == 1) {
            locationVal = locationUsersByUserName.get(0).getLocation();


        }
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();
        userService = Context.getUserContext();

        dateC = new Date();

        currentDate.setTime(dateC);

        one = new GregorianCalendar();
        two = new GregorianCalendar();

        one.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        service = Context.getService(PharmacyService.class);

        inventoryNo = request.getParameter("inventoryNo"); //inventory

        drugName = request.getParameter("dispensedrug"); //from the drop down

        dispenseedit = request.getParameter("dispenseedit"); //from the drop down

        uuidedit = request.getParameter("dispenseuuid"); //from the drop down

        //void

        voidreason = request.getParameter("dispensereason");
        voiduuid = request.getParameter("dispenseuuidvoid");

        drugDispenseSettings = service.getDrugDispenseSettings();

        sizeDrug = drugDispenseSettings.size();

        if (sizeDrug == 0) {
            //save a new copy
            drugDispense = new DrugDispenseSettings();

            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));

            service.saveDrugDispenseSettings(drugDispense);

        }
        for (int i = 0; i < sizeDrug; i++) {

            if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(i).getLocation().getUuid()).getName()
                    .equalsIgnoreCase(locationVal)) {

                if (drugDispenseSettings.get(i).getDrugId().getName().equalsIgnoreCase(drugName)) {

                    //do void and editing here

                    if (voiduuid != null) {

                        drugDispense = new DrugDispenseSettings();

                        drugDispense = service.getDrugDispenseSettingsByUuid(voiduuid);
                        drugDispense.setVoided(true);
                        drugDispense.setVoidReason(voidreason);

                        service.saveDrugDispenseSettings(drugDispense);
                    } else if (dispenseedit != null) {
                        drugDispense = new DrugDispenseSettings();

                        drugDispense = service.getDrugDispenseSettingsByUuid(uuidedit);
                        if (userService.getAuthenticatedUser().getUserId() == drugDispense.getCreator().getUserId()) {

                            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
                            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
                            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
                            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
                            service.saveDrugDispenseSettings(drugDispense);
                        }
                    }

                } else {

                    //save a new copy
                    drugDispense = new DrugDispenseSettings();

                    drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
                    drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
                    drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
                    drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));

                    service.saveDrugDispenseSettings(drugDispense);

                }

            }

        }

    }

    public synchronized JSONArray getArrayDialog(List<DrugDispenseSettings> drugDispenseSettings, int size, String dialogShow, String locatin) {

        if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(size).getLocation().getUuid()).getName()
                .equalsIgnoreCase(locatin)) {

            if (dialogShow != null) {

                if (drugDispenseSettings.get(size).getDrugId().getName().equalsIgnoreCase(dialogShow)) {
                    datad2 = new JSONArray();
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

                        datad2.put("edit");
                        editPharmacy = false;
                    } else
                        datad2.put("");

                    datad2.put(drugDispenseSettings.get(size).getUuid());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getName());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getDrugId());

                    datad2.put(drugDispenseSettings.get(size).getBatchId());

                    datad2.put(drugDispenseSettings.get(size).getInventoryId().getExpireDate().toString().substring(0, 10));

                    if (deletePharmacy) {
                        datad2.put("void");
                        deletePharmacy = false;
                    } else
                        datad2.put("");

                    return datad2;
                } else
                    return null;

            } else
                return null;

        } else {

            return null;
        }
    }

    public synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size, String id, String location) {

        if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                .equalsIgnoreCase(location)) {

            if (dialog != null) {

                if (pharmacyStore.get(size).getDrugs().getName().equalsIgnoreCase(id)) {

                    readDate.setTime(pharmacyStore.get(size).getExpireDate());

                    two.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));

                    int num = daysBetween(two.getTime(), one.getTime());

                    datad2 = new JSONArray();

                    datad2.put(pharmacyStore.get(size).getUuid());
                    datad2.put(pharmacyStore.get(size).getDrugs().getName());
                    datad2.put(pharmacyStore.get(size).getQuantity());

                    datad2.put(pharmacyStore.get(size).getBatchNo());

                    datad2.put(num);
                    datad2.put("<input type='radio' name='check1' />");

                    return datad2;

                } else
                    return null;

            } else
                return null;

        } else {

            return null;
        }
    }

    public synchronized int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

}
