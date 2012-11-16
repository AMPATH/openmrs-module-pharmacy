package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.DrugFrequency;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class DrugFrequencyController {

    private static final Log log = LogFactory.getLog(DrugFrequencyController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;
    private List<DrugFrequency> drugFrequencyList;
    private int size;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<DrugFrequency> serviceDrugFrequency;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugFrequency")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String uuid = request.getParameter("uuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        drugFrequencyList = service.getDrugFrequency();
        size = drugFrequencyList.size();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {

                        jsonArray.put("" + getDropDown(drugFrequencyList, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(drugFrequencyList, i));

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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugFrequency")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String frequencyReason = request.getParameter("frequencyReason");
        String frequencyUuidVoid = request.getParameter("frequencyUuidVoid");

        String frequencyName = request.getParameter("frequencyName");
        String frequencyEdit = request.getParameter("frequencyEdit");
        String frequencyUuid = request.getParameter("frequencyUuid");

        userService = Context.getUserContext();
        if (frequencyEdit != null) {
            // code to edit an entry
            if (frequencyEdit.equalsIgnoreCase("false")) {

                serviceDrugFrequency = service.getDrugFrequency();
                size = serviceDrugFrequency.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(serviceDrugFrequency, i, frequencyName);
                    if (found)
                        break;
                }

                if (!found) {

                    DrugFrequency drugFrequency = new DrugFrequency();
                    drugFrequency.setFrequencyName(frequencyName);
                    service.saveDrugFrequency(drugFrequency);

                } else //do code to display to the user
                {

                }

            } else if (frequencyEdit.equalsIgnoreCase("true")) {

                DrugFrequency drugFrequency = new DrugFrequency();
                drugFrequency = service.getDrugFrequencyByUuid(frequencyUuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugFrequency.getCreator().getUserId())) {

                    // saving/updating a record
                    drugFrequency.setFrequencyName(frequencyName);

                    service.saveDrugFrequency(drugFrequency);
                }
            }

        } else if (frequencyUuidVoid != null) {

            DrugFrequency drugFrequency = new DrugFrequency();
            drugFrequency = service.getDrugFrequencyByUuid(frequencyUuidVoid);

            drugFrequency.setVoided(true);
            drugFrequency.setVoidReason(frequencyReason);

            service.saveDrugFrequency(drugFrequency);

        }

    }

    public synchronized JSONArray getArray(List<DrugFrequency> frequency, int size) {
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if ((rl.getRole().equals("System Developer")) || (rl.getRole().equals("Provider"))
                    || (rl.getRole().equals("	Authenticated "))) {

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
        drugStrengthA = new JSONArray();

        if (editPharmacy) {

            drugStrengthA.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
            editPharmacy = false;
        } else
            drugStrengthA.put("");
        drugStrengthA.put(frequency.get(size).getUuid());
        drugStrengthA.put(frequency.get(size).getFrequencyName());
        if (deletePharmacy) {
            drugStrengthA.put("<a href=#?uuid=" + frequency.get(size).getUuid() + ">Void</a>");
            deletePharmacy = false;
        } else
            drugStrengthA.put("");

        return drugStrengthA;
    }

    public synchronized String getDropDown(List<DrugFrequency> frequency, int size) {

        return frequency.get(size).getFrequencyName();
    }

    public synchronized boolean getCheck(List<DrugFrequency> frequency, int size, String frequencyName) {
        if (frequency.get(size).getFrequencyName().equalsIgnoreCase(frequencyName)) {

            return true;

        } else
            return false;

    }
}
