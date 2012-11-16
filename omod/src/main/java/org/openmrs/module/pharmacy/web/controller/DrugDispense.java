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
import org.json.JSONException;
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
public class DrugDispense {

    private static final Log log = LogFactory.getLog(DrugDispenseSettings.class);

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

    private GregorianCalendar gregorianCalendar;

    private GregorianCalendar calendar;

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

    private String days;

    private String amount;

    private String dose = null;

    private String option;

    private Integer b;

    private String quantity;

    private String form;

    private String front;

    private String back;
    private boolean found;
    private List<PharmacyLocationUsers> pharmacyLocationUserses;
    private int sizeUsers;
    private List<DrugDispenseSettings> drugDispenseSettings;
    private DrugDispenseSettings drugDispense;
    ;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDispense")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        pharmacyLocationUserses = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUserses.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUserses.get(0).getLocation();


        }
        userService = Context.getUserContext();

        service = Context.getService(PharmacyService.class);
        serviceLocation = Context.getLocationService();
        datadFrm = new JSONArray();

        drugDispenseSettings = service.getDrugDispenseSettings();

        size = drugDispenseSettings.size();
        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        dateC = new Date();

        currentDate.setTime(dateC);

        gregorianCalendar = new GregorianCalendar();
        calendar = new GregorianCalendar();

        gregorianCalendar.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        try {

            json = new JSONObject();
            if (dialogShow != null && dialog == null) {
                if (size != 0) {


                    for (int i = 0; i < size; i++) {


                        if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(i).getLocation().getUuid())

                                .getName().equalsIgnoreCase(locationVal)) {
                            datadFrm = new JSONArray();

                            datadFrm = getArrayDialog(drugDispenseSettings, i, dialogShow, locationVal);

                            if (datadFrm != null)
                                json.accumulate("aaData", datadFrm);
                        }

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
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("");

                    json.accumulate("aaData", datad2);

                }
                dialogShow = null;

                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);

                response.flushBuffer();
            } else if (dialog != null && dialogShow == null) {

                List<PharmacyStore> listStore = service.getPharmacyInventory();
                int sizeStore = listStore.size();

                for (int i = 0; i < sizeStore; i++) {

                    if (service.getPharmacyLocationsByUuid(listStore.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {

                        datadFrm = new JSONArray();

                        datadFrm = getArray(listStore, i, dialog, locationVal);
                        if (datadFrm != null)
                            json.accumulate("aaData", datadFrm);
                    }

                    datad2 = new JSONArray();
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
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);

                response.flushBuffer();
            } else if (dose != null) {

                DrugDispenseSettings drugDispense = new DrugDispenseSettings();
                //change calendar
                datad2 = new JSONArray();
                if (Context.getConceptService().getDrugByNameOrId(dose) != null && service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(
                        dose)) != null) {

                    drugDispense = service.getDrugDispenseSettingsByDrugId(Context.getConceptService().getDrugByNameOrId(
                            dose));
                    datad2.put(drugDispense.getOption().getName());
                    datad2.put(drugDispense.getValue());

                    datad2.put(drugDispense.getAmount());
                } else {
                    datad2.put(0);
                    datad2.put(0);

                    datad2.put(0);
                }

                //json.accumulate("aaData", datad2);

                response.getWriter().print(datad2);

                response.flushBuffer();

            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDispense")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {


        inventoryNo = request.getParameter("inventoryNo"); //inventory

        drugName = request.getParameter("dispensedrug"); //from the drop down

        option = request.getParameter("optionval"); //from the drop down

        dispenseedit = request.getParameter("dispenseedit"); //from the drop down

        uuidedit = request.getParameter("dispenseuuid"); //from the drop down

        days = request.getParameter("value"); //from the drop down

        amount = request.getParameter("price");

        quantity = request.getParameter("quantity");


        form = request.getParameter("form");


        front = request.getParameter("front");


        back = request.getParameter("back");


        //void

        voidreason = request.getParameter("dispensereason");
        voiduuid = request.getParameter("dispenseuuidvoid");
        String locationVal = null;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }


        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        dateC = new Date();

        currentDate.setTime(dateC);

        gregorianCalendar = new GregorianCalendar();
        calendar = new GregorianCalendar();

        gregorianCalendar.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        service = Context.getService(PharmacyService.class);


        drugDispenseSettings = service.getDrugDispenseSettings();

        sizeDrug = drugDispenseSettings.size();

        if (sizeDrug == 0) {
            //save a new copy

            drugDispense = new DrugDispenseSettings();

            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
            drugDispense.setOption(service.getPharmacyGeneralVariablesByName(option));

            drugDispense.setValue(Integer.parseInt(days));
            drugDispense.setAmount(Double.parseDouble(amount));


            drugDispense.setQuantity(Integer.parseInt(quantity));
            drugDispense.setForm(form);
            drugDispense.setBack(back);
            drugDispense.setFront(front);

            service.saveDrugDispenseSettings(drugDispense);

        }


        for (int i = 0; i < size; i++) {


            found = getCheck(drugDispenseSettings, i, locationVal, drugName);
            if (found)
                break;
        }


        if (!found) {

            if (voiduuid != null) {


                drugDispense = new DrugDispenseSettings();

                drugDispense = service.getDrugDispenseSettingsByUuid(voiduuid);
                drugDispense.setVoided(true);
                drugDispense.setVoidReason(voidreason);

                service.saveDrugDispenseSettings(drugDispense);
            } else {


                drugDispense = new DrugDispenseSettings();


                drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
                drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
                drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
                drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
                drugDispense.setOption(service.getPharmacyGeneralVariablesByName(option));

                drugDispense.setValue(Integer.parseInt(days));
                drugDispense.setAmount(Double.parseDouble(amount));

                drugDispense.setQuantity(Integer.parseInt(quantity));
                drugDispense.setForm(form);
                drugDispense.setBack(back);
                drugDispense.setFront(front);
                service.saveDrugDispenseSettings(drugDispense);
            }


        } else {


            drugDispense = service.getDrugDispenseSettingsByDrugId(service.getPharmacyInventoryByUuid(inventoryNo).getDrugs());

            drugDispense.setBatchId(service.getPharmacyInventoryByUuid(inventoryNo).getBatchNo());
            drugDispense.setInventoryId(service.getPharmacyInventoryByUuid(inventoryNo));
            drugDispense.setDrugId(Context.getConceptService().getDrugByNameOrId(drugName));
            drugDispense.setLocation(service.getPharmacyLocationsByName(locationVal));
            drugDispense.setOption(service.getPharmacyGeneralVariablesByName(option));

            drugDispense.setValue(Integer.parseInt(days));
            drugDispense.setAmount(Double.parseDouble(amount));

            drugDispense.setQuantity(Integer.parseInt(quantity));
            drugDispense.setForm(form);
            drugDispense.setBack(back);
            drugDispense.setFront(front);
            service.saveDrugDispenseSettings(drugDispense);

        }


    }

    public synchronized JSONArray getArrayDialog(List<DrugDispenseSettings> drugDispenseSettings, int size, String dialogShow, String location) {

        if (service.getPharmacyLocationsByUuid(drugDispenseSettings.get(size).getLocation().getUuid()).getName()
                .equalsIgnoreCase(location)) {

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


                    datad2.put("");
                    datad2.put(drugDispenseSettings.get(size).getUuid());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getName());
                    datad2.put(drugDispenseSettings.get(size).getDrugId().getDrugId());

                    datad2.put(drugDispenseSettings.get(size).getOption().getName());
                    datad2.put(drugDispenseSettings.get(size).getValue());
                    try {
                        datad2.put(drugDispenseSettings.get(size).getAmount());
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }

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

                    calendar.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));

                    int num = daysBetween(calendar.getTime(), gregorianCalendar.getTime());

                    datad2 = new JSONArray();

                    datad2.put(pharmacyStore.get(size).getUuid());
                    datad2.put(pharmacyStore.get(size).getDrugs().getName());
                    datad2.put(pharmacyStore.get(size).getQuantity());

                    datad2.put(pharmacyStore.get(size).getBatchNo());

                    datad2.put(num);
                    datad2.put("<input type='checkbox' id='check1' />");

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

    public synchronized boolean getCheck(List<DrugDispenseSettings> DrugDispenseSettings, int size, String location, String drug) {


        if (DrugDispenseSettings.get(size).getLocation().getName().equalsIgnoreCase(location) && (DrugDispenseSettings.get(size).getDrugId().getName().equals(drug))) {

            return true;

        } else
            return false;

    }

}
