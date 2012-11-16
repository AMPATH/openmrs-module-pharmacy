package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyLocations;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugDetailsController {

    private static final Log log = LogFactory.getLog(DrugDetailsController.class);

    private JSONArray data;

    public PharmacyService service;

    public LocationService serviceLocation;

    private boolean found = false;

    private ConceptService serviceDrugs;

    private JSONArray supplierNames;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private boolean setLocation = false;

    private String bar = null;

    private String drug = null;

    private String id;
    private List<Drug> allDrugs;
    private List<PharmacyLocations> pharmacyLocations;
    private List<PharmacyLocationUsers> pharmacyLocationUsers;
    private int size, size1, size2;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<Drug> listDrugs;
    private Drug drugByNameOrId;


    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        String drop = request.getParameter("drop");
        bar = request.getParameter("bar");
        String searchDrug = request.getParameter("searchDrug");
        drug = request.getParameter("drug");
        id = request.getParameter("id");

        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        userService = Context.getUserContext();
        serviceLocation = Context.getLocationService();

        allDrugs = serviceDrugs.getAllDrugs();
        pharmacyLocations = service.getPharmacyLocations();
        pharmacyLocationUsers = service.getPharmacyLocationUsers();

        size = allDrugs.size();
        size2 = pharmacyLocations.size();
        size1 = pharmacyLocationUsers.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();
        try {

            if (drop != null) {

                if (drop.equalsIgnoreCase("drug")) {

                    drugByNameOrId = Context.getConceptService().getDrugByNameOrId(id);
                    jsonArray.put("" + drugByNameOrId.getName());
                    jsonArray.put("" + drugByNameOrId.getConcept().getId());
                    jsonArray.put("" + drugByNameOrId.getConcept().getDisplayString());

                    response.getWriter().print(jsonArray);

                } else if (drop.equalsIgnoreCase("drop")) { // get details of a drug with its ID

                    listDrugs = serviceDrugs.getDrugs(searchDrug);
                    int sizeD = listDrugs.size();
                    if (bar != null) {
                        for (int i = 0; i < sizeD; i++) {
                            jsonArray.put("" + listDrugs.get(i).getName() + "|" + listDrugs.get(i).getId());
                        }


                    } else {


                        for (int i = 0; i < sizeD; i++) {
                            jsonArray.put("" + listDrugs.get(i).getName());
                        }
                    }
                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("location")) {


                    String name = Context.getAuthenticatedUser().getUsername();

                    Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
                    for (Role rl : xvc) {
                        if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))) {
                            setLocation = true;
                        }


                        if (rl.hasPrivilege("Set Location")) {
                            setLocation = true;
                        }


                    }
                    if (setLocation) {

                        for (int ii = 0; ii < size1; ii++) {
                            String val = getDropDownLocation(pharmacyLocationUsers, ii, name);

                            if (!val.contentEquals("null"))
                                jsonArray.put("" + val);
                        }
                    } else {

                        jsonArray.put("No permission");
                    }

                    jsonObject.accumulate("", jsonArray);
                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("locationAll")) {
                    for (int ii = 0; ii < size2; ii++) {
                        System.out.println(pharmacyLocations.get(ii).getName());
                        jsonArray.put("" + pharmacyLocations.get(ii).getName());
                    }
                    jsonObject.accumulate("", jsonArray);
                    response.getWriter().print(jsonArray);


                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(allDrugs, i));

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);

                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();

        } catch (Exception e) {
            // drugs
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {


    }

    public synchronized JSONArray getArray(List<Drug> drug, int size) {

        data = new JSONArray();
        Collection<Privilege> xc = userService.getAuthenticatedUser().getPrivileges();

        for (Privilege p : xc) {
            if (p.getPrivilege().equalsIgnoreCase("Edit Pharmacy")) {
                editPharmacy = true;
            }

            if (p.getPrivilege().equalsIgnoreCase("Delete Pharmacy")) {
                deletePharmacy = true;
            }

        }

        if (editPharmacy) {

            data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            data.put("");

        data.put(drug.get(size).getUuid());

        data.put(drug.get(size).getName());
        data.put(service.getDrugFormulation());
        data.put(service.getDrugStrength());
        data.put(service.getDrugUnits());
        if (deletePharmacy) {
            data.put("<a href=#?uuid=" + drug.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            data.put("");
        return data;

    }

    public String getDropDownLocation(List<PharmacyLocationUsers> list2, int size, String name) {


        if (list2.get(size).getUserName().equalsIgnoreCase(name)) {


            return list2.get(size).getLocation();
        } else
            return "null";


    }

}
