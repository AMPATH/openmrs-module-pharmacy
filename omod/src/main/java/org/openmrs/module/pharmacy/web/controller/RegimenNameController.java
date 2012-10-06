package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.RegimenNames;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class RegimenNameController {

    private static final Log log = LogFactory.getLog(RegimenNameController.class);

    private JSONArray drugunitsA;

    public PharmacyService service;

    private boolean found = true;

    private boolean checked = false;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/regimenName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        List<RegimenNames> list = service.getRegimenNames();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();
        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if((rl.getRole().equals("System Developer"))||(rl.getRole().equals("Provider"))||(rl.getRole().equals("	Authenticated "))){

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
        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {
                        jsons.put(getDropDown(list, i));
                    }

                    response.getWriter().print(jsons);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArray(list, i));

                }

                if (!json.has("aaData")) {

                    datad2 = new JSONArray();

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");

                    json.accumulate("aaData", datad2);

                }
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);
                response.getWriter().print(json);
            }
            response.flushBuffer();
            editPharmacy = false;

            deletePharmacy = false;
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/regimenName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();

        String regimennamereason = request.getParameter("regimennamereason");
        String regimennameuuidvoid = request.getParameter("regimennameuuidvoid");

        String regimennamename = request.getParameter("regimennamename");
        String regimennameedit = request.getParameter("regimennameedit");
        String regimennameuuid = request.getParameter("regimennameuuid");

        found = true;
        if (regimennameedit != null) {

            if (regimennameedit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<RegimenNames> list = service.getRegimenNames();
                int size = list.size();
                if (size == 0) {

                    RegimenNames regimenNames = new RegimenNames();

                    regimenNames.setRegimenName(regimennamename);
                    service.saveRegimenNames(regimenNames);

                }

                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, regimennamename);

                    if (found)
                        break;
                }

                if (!found) {

                    RegimenNames regimenNames = new RegimenNames();

                    regimenNames.setRegimenName(regimennamename);
                    service.saveRegimenNames(regimenNames);

                } else //do code to display to the user
                {

                }
                found = true;
            } else if (regimennameedit.equalsIgnoreCase("true")) {
                RegimenNames regimenNames = new RegimenNames();
                regimenNames = service.getRegimenNamesByUuid(regimennameuuid);
                if (userService.getAuthenticatedUser().getUserId().equals( regimenNames.getCreator().getUserId())) {

                    // saving/updating a record
                    regimenNames.setRegimenName(regimennamename);

                    service.saveRegimenNames(regimenNames);
                }

            }

        }

        else if (regimennameuuidvoid != null) {

            RegimenNames regimenNames = new RegimenNames();
            regimenNames = service.getRegimenNamesByUuid(regimennameuuidvoid);

            regimenNames.setVoided(true);
            regimenNames.setVoidReason(regimennamereason);

            service.saveRegimenNames(regimenNames);

        }

    }

    public synchronized JSONArray getArray(List<RegimenNames> regimenNames, int size) {

        drugunitsA = new JSONArray();

        Collection<Role> xvc = userService.getAuthenticatedUser().getAllRoles();
        for (Role rl : xvc) {

            if((rl.getRole().equals("System Developer"))||(rl.getRole().equals("Provider"))||(rl.getRole().equals("	Authenticated "))){

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

            drugunitsA.put("edit");
            editPharmacy = false;
        } else
            drugunitsA.put("");
        drugunitsA.put("");

        drugunitsA.put(regimenNames.get(size).getUuid());
        drugunitsA.put(regimenNames.get(size).getRegimenName());
        if (deletePharmacy) {
            drugunitsA.put("void");
            deletePharmacy = false;
        } else
            drugunitsA.put("");

        return drugunitsA;
    }

    public synchronized String getDropDown(List<RegimenNames> regimenNames, int size) {

        return regimenNames.get(size).getRegimenName();
    }

    public synchronized boolean getCheck(List<RegimenNames> regimenNames, int size, String name) {
        if (regimenNames.get(size).getRegimenName().equalsIgnoreCase(name)) {

            return true;

        } else
            return false;

    }
}
