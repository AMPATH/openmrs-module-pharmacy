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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("uuid");
        String drop = request.getParameter("drop");
        bar = request.getParameter("bar");
        String searchDrug = request.getParameter("searchDrug");
        drug = request.getParameter("drug");
        id = request.getParameter("id");

        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        List<Drug> list = serviceDrugs.getAllDrugs();
        serviceLocation = Context.getLocationService();
        List<PharmacyLocations> list2 = service.getPharmacyLocations();
        List<PharmacyLocationUsers> locationUsers = service.getPharmacyLocationUsers();

        int size = list.size();
        int size2 = list2.size();
        int sizeUsers = locationUsers.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();
        try {

            if (drop != null) {

                if (drop.equalsIgnoreCase("drug")) {

                    Drug drug = Context.getConceptService().getDrugByNameOrId(id);
                    jsons.put("" + drug.getName());
                    jsons.put("" + drug.getConcept().getId());
                    jsons.put("" + drug.getConcept().getDisplayString());

                    response.getWriter().print(jsons);
                } else if (drop.equalsIgnoreCase("drop")) {

                    List<Drug> listDrugs = serviceDrugs.getDrugs(searchDrug);
                    int sizeD = listDrugs.size();
                    if (bar != null) {
                        for (int i = 0; i < sizeD; i++) {
                            jsons.put("" + listDrugs.get(i).getName() + "|" + listDrugs.get(i).getId());
                        }


                    } else {


                        for (int i = 0; i < sizeD; i++) {
                            jsons.put("" + listDrugs.get(i).getName());
                        }
                    }
                    response.getWriter().print(jsons);
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

                        for (int ii = 0; ii < sizeUsers; ii++) {
                            String val = getDropDownLocation(locationUsers, ii, name);

                            if (!val.contentEquals("null"))
                                jsons.put("" + val);
                        }
                    } else {

                        jsons.put("No permission");
                    }

                    json.accumulate("", jsons);
                    response.getWriter().print(jsons);
                } else if (drop.equalsIgnoreCase("locationAll")) {
                    for (int ii = 0; ii < size2; ii++) {
                        System.out.println(list2.get(ii).getName());
                        jsons.put("" + list2.get(ii).getName());
                    }
                    json.accumulate("", jsons);
                    response.getWriter().print(jsons);


                }

            } else {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArray(list, i));

                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            }
            response.flushBuffer();

        } catch (Exception e) {
            // drugs
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugDetails")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String drugsformulation = request.getParameter("drugsformulation");
        String drugsstrength = request.getParameter("drugsstrength");
        String drugsunits = request.getParameter("drugsunits");

        String drugsreason = request.getParameter("drugsreason");
        String drugsuuidvoid = request.getParameter("drugsuuidvoid");

        String drugsname = request.getParameter("drugsname");
        String drugsedit = request.getParameter("drugsedit");
        String drugsuuid = request.getParameter("drugsuuid");

        if (drugsedit != null) {
            if (drugsedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<Drug> list = serviceDrugs.getAllDrugs();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, drugsname, drugsformulation, drugsstrength, drugsunits);
                    if (found)
                        break;
                }

                if (!found) {
                    //					Drug drug = new Drug();
                    //
                    //					drug.setConcept(null);
                    ////					drug.setDrugFormulation(service.getDrugFormulationByName(drugsformulation));
                    ////
                    ////					drug.setDrugStrength(service.getDrugStrengthByName(drugsstrength));
                    ////					drug.setDrugUnit(service.getDrugUnitsByName(drugsunits));
                    ////					drug.setDrugName(service.getDrugNameByName(drugsname));
                    //
                    //					service.saveDrugs(drug);

                } else //do code to display to the user
                {

                }

            } else if (drugsedit.equalsIgnoreCase("true")) {

                //				Drug drug = new Drug();
                //				drug = service.getDrugsByUuid(drugsuuid);
                //
                //				drug.setConcept(null);
                //				drug.setDrugFormulation(service.getDrugFormulationByName(drugsformulation));
                //
                //				drug.setDrugStrength(service.getDrugStrengthByName(drugsstrength));
                //				drug.setDrugUnit(service.getDrugUnitsByName(drugsunits));
                //				drug.setDrugName(service.getDrugNameByName(drugsname));
                //
                //				service.saveDrugs(drug);

            }

        } else if (drugsuuidvoid != null) {
            //
            //			Drug drug = new Drug();
            //			drug = service.getDrugsByUuid(drugsuuidvoid);
            //
            //			drug.setRetired(true);
            //			drug.setRetireReason(drugsreason);
            //
            //			service.saveDrugs(drug);
            //
        }

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

    public synchronized String getDropDown(List<Drug> drug, int size) {

        return drug.get(size).getName();

    }

    public synchronized String getDropDownBarcode(List<Drug> drug, int size) {

        return drug.get(size).getName() + "|" + drug.get(size).getId();

    }

    public String getDropDownLocation(List<PharmacyLocationUsers> list2, int size, String name) {


        if (list2.get(size).getUserName().equalsIgnoreCase(name)) {


            return list2.get(size).getLocation();
        } else
            return "null";


    }

    public synchronized boolean getCheck(List<Drug> drug, int size, String drugName, String drugsformulation,
                                         String drugsstrength, String drugsunits) {

        if ((drug.get(size).getName().equals(drugName))) {

            return true;

        } else
            return false;

    }
}
