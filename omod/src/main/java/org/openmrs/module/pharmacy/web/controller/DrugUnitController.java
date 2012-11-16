package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugUnits;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugUnitController {

    private static final Log log = LogFactory.getLog(DrugUnitController.class);

    private JSONArray drugunitsA;

    public PharmacyService service;

    private boolean found = false;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private List<DrugUnits> drugUnits;
    private int size;
    private JSONArray jsonArray;
    private JSONObject jsonObject;
    private DrugUnits drugunits;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugUnit")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        userService = Context.getUserContext();

        String drop = request.getParameter("drop");
        drugUnits = service.getDrugUnits();
        size = drugUnits.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {

                        jsonArray.put(getDropDown(drugUnits, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(drugUnits, i));

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);

                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugUnit")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String unitsreason = request.getParameter("unitsreason");
        String unitsuuidvoid = request.getParameter("unitsuuidvoid");

        String unitsname = request.getParameter("unitsname");
        String unitsedit = request.getParameter("unitsedit");
        String unitsuuid = request.getParameter("unitsuuid");
        userService = Context.getUserContext();
        if (unitsedit != null) {
            if (unitsedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                drugUnits = service.getDrugUnits();
                size = drugUnits.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(drugUnits, i, unitsname);
                    if (found)
                        break;
                }

                if (!found) {

                    drugunits = new DrugUnits();
                    drugunits.setDrugUnitName(unitsname);
                    service.saveDrugUnits(drugunits);

                } else //do code to display to the user
                {

                }

            } else if (unitsedit.equalsIgnoreCase("true")) {
                drugunits = new DrugUnits();
                drugunits = service.getDrugUnitsByUuid(unitsuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugunits.getCreator().getUserId())) {

                    // saving/updating a record
                    drugunits.setDrugUnitName(unitsname);

                    service.saveDrugUnits(drugunits);

                }

            }

        } else if (unitsuuidvoid != null) {

            drugunits = new DrugUnits();
            drugunits = service.getDrugUnitsByUuid(unitsuuidvoid);

            drugunits.setVoided(true);
            drugunits.setVoidReason(unitsreason);

            service.saveDrugUnits(drugunits);

        }

    }

    public synchronized JSONArray getArray(List<DrugUnits> drugunits, int size) {

        drugunitsA = new JSONArray();
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

            drugunitsA.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            drugunitsA.put("");
        drugunitsA.put(drugunits.get(size).getUuid());
        drugunitsA.put(drugunits.get(size).getDrugUnitName());

        if (deletePharmacy) {
            drugunitsA.put("<a href=#?uuid=" + drugunits.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugunitsA.put("");
        return drugunitsA;
    }

    public synchronized String getDropDown(List<DrugUnits> drugunits, int size) {

        return drugunits.get(size).getDrugUnitName();
    }

    public synchronized boolean getCheck(List<DrugUnits> drugunits, int size, String name) {
        if (drugunits.get(size).getDrugUnitName().equalsIgnoreCase(name)) {

            return true;

        } else
            return false;

    }
}
