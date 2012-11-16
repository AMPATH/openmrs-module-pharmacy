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
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugTransactionsController {

    private static final Log log = LogFactory.getLog(DrugTransactionsController.class);

    private JSONArray data;

    public PharmacyService service;

    String drop = null;

    private String filter = null;

    private String uuidfilter = null;

    private boolean exit = false;

    private String originalbindrug;

    private LocationService serviceLocation;

    private String incomingdrug;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private ConceptService serviceDrugs;

    private boolean editPharmacy = false;

    private UserContext userService;

    private boolean deletePharmacy = false;
    private List<PharmacyLocationUsers> locationUsersByUserName;
    private int sizeUsers;
    private List<DrugTransactions> drugTransactions;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Drug> allDrugs;
    private int size1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugTransactions")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {


        String locationVal = null;


        service = Context.getService(PharmacyService.class);
        locationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = locationUsersByUserName.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = locationUsersByUserName.get(0).getLocation();


        }
        serviceLocation = Context.getLocationService();
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        drop = request.getParameter("drop");
        filter = request.getParameter("sSearch");

        drugTransactions = service.getDrugTransactions();
        int size = drugTransactions.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        response.setContentType("application/jsonObject");


        if (filter.length() > 2) {
            originalbindrug = filter;

            allDrugs = serviceDrugs.getAllDrugs();
            size1 = allDrugs.size();
            for (int i = 0; i < size1; i++) {
                uuidfilter = getString(allDrugs, i, originalbindrug);
                if (uuidfilter != null)
                    break;

            }
        }
        try {

            for (int i = 0; i < size; i++) {

                if (drugTransactions.get(i).getLocation() != null) {
                    if (service.getPharmacyLocationsByUuid(drugTransactions.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {
                        jsonArray = getArray(drugTransactions, i, locationVal);
                        if (jsonArray != null)
                            jsonObject.accumulate("aaData", jsonArray);
                    }
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

                jsonObject.accumulate("aaData", data);
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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugTransactions")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

    }

    public synchronized JSONArray getArray(List<DrugTransactions> pharmacyStore, int size, String location) {

        if (filter.length() > 2) {

            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {

                if (pharmacyStore.get(size).getLocation() != null) {
                    if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                            .equalsIgnoreCase(location)) {

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
                        data.put(pharmacyStore.get(size).getQuantityIn());
                        data.put(pharmacyStore.get(size).getDateCreated());
                        if (pharmacyStore.get(size).getCategory() == null) {

                            data.put("test");
                        } else
                            data.put(pharmacyStore.get(size).getCategory().getName());

                        data.put("[" + pharmacyStore.get(size).getCreator().getNames() + "]");

                        data.put(pharmacyStore.get(size).getComment());

                        return data;
                    }

                }
            } else {
                return null;

            }

        } else {
            if (pharmacyStore.get(size).getLocation() != null) {

                if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                        .equalsIgnoreCase(location)) {

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
                    data.put(pharmacyStore.get(size).getQuantityIn());
                    data.put(pharmacyStore.get(size).getDateCreated());
                    if (pharmacyStore.get(size).getCategory() == null) {

                        data.put("test");
                    } else
                        data.put(pharmacyStore.get(size).getCategory().getName());

                    data.put("[" + pharmacyStore.get(size).getCreator().getNames() + "]");

                    data.put(pharmacyStore.get(size).getComment());
                    return data;
                }
            }
        }
        return null;
    }

    public synchronized String getDropDown(List<PharmacyStore> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public synchronized String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;
    }
}
