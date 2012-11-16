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
    private List<RegimenNames> regimenNames;
    private int size;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private RegimenNames names;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/regimenName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        regimenNames = service.getRegimenNames();
        size = regimenNames.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

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
        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {
                        jsonArray.put(getDropDown(regimenNames, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(regimenNames, i));

                }

                if (!jsonObject.has("aaData")) {

                    datad2 = new JSONArray();

                    datad2.put("None");
                    datad2.put("None");
                    datad2.put("None");

                    datad2.put("None");
                    datad2.put("None");

                    jsonObject.accumulate("aaData", datad2);

                }
                jsonObject.accumulate("iTotalRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iTotalDisplayRecords", jsonObject.getJSONArray("aaData").length());
                jsonObject.accumulate("iDisplayStart", 0);
                jsonObject.accumulate("iDisplayLength", 10);
                response.getWriter().print(jsonObject);
            }
            response.flushBuffer();
            editPharmacy = false;

            deletePharmacy = false;
        } catch (Exception e) {
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
                regimenNames = service.getRegimenNames();
                int size = regimenNames.size();
                if (size == 0) {

                    RegimenNames regimenNames = new RegimenNames();

                    regimenNames.setRegimenName(regimennamename);
                    service.saveRegimenNames(regimenNames);

                }

                for (int i = 0; i < size; i++) {

                    found = getCheck(regimenNames, i, regimennamename);

                    if (found)
                        break;
                }

                if (!found) {

                    names = new RegimenNames();

                    names.setRegimenName(regimennamename);
                    service.saveRegimenNames(names);

                } else //do code to display to the user
                {

                }
                found = true;
            } else if (regimennameedit.equalsIgnoreCase("true")) {
                names = new RegimenNames();
                names = service.getRegimenNamesByUuid(regimennameuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(names.getCreator().getUserId())) {

                    // saving/updating a record
                    names.setRegimenName(regimennamename);

                    service.saveRegimenNames(names);
                }

            }

        } else if (regimennameuuidvoid != null) {

            names = new RegimenNames();
            names = service.getRegimenNamesByUuid(regimennameuuidvoid);

            names.setVoided(true);
            names.setVoidReason(regimennamereason);

            service.saveRegimenNames(names);

        }

    }

    public synchronized JSONArray getArray(List<RegimenNames> regimenNames, int size) {

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
