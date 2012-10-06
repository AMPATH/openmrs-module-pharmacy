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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugFrequency")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String uuid = request.getParameter("uuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        List<DrugFrequency> list = service.getDrugFrequency();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {

                        jsons.put("" + getDropDown(list, i));
                    }

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

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugFrequency")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String frequencyreason = request.getParameter("frequencyreason");
        String frequencyuuidvoid = request.getParameter("frequencyuuidvoid");

        String frequencyname = request.getParameter("frequencyname");
        String frequencyedit = request.getParameter("frequencyedit");
        String frequencyuuid = request.getParameter("frequencyuuid");
        userService = Context.getUserContext();
        if (frequencyedit != null) {
            if (frequencyedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<DrugFrequency> list = service.getDrugFrequency();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, frequencyname);
                    if (found)
                        break;
                }

                if (!found) {

                    DrugFrequency drugFrequency = new DrugFrequency();
                    drugFrequency.setFrequencyName(frequencyname);
                    service.saveDrugFrequency(drugFrequency);

                } else //do code to display to the user
                {

                }

            } else if (frequencyedit.equalsIgnoreCase("true")) {

                DrugFrequency drugFrequency = new DrugFrequency();
                drugFrequency = service.getDrugFrequencyByUuid(frequencyuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(drugFrequency.getCreator().getUserId())) {

                    // saving/updating a record
                    drugFrequency.setFrequencyName(frequencyname);

                    service.saveDrugFrequency(drugFrequency);
                }
            }

        }

        else if (frequencyuuidvoid != null) {

            DrugFrequency drugFrequency = new DrugFrequency();
            drugFrequency = service.getDrugFrequencyByUuid(frequencyuuidvoid);

            drugFrequency.setVoided(true);
            drugFrequency.setVoidReason(frequencyreason);

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
