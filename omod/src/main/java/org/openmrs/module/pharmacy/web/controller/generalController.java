package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyGeneralVariables;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class generalController {

    private static final Log log = LogFactory.getLog(generalController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray supplierNames;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/generalName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);

        List<PharmacyGeneralVariables> list = service.getPharmacyGeneralVariables();
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
                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            }
            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/generalName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        service = Context.getService(PharmacyService.class);
        String generalName = request.getParameter("generalname");
        String description = request.getParameter("description");
        String edit = request.getParameter("generaledit");
        String uuid = request.getParameter("generaluuid");
        String uuidvoid = request.getParameter("generaluuidvoid");
        String reason = request.getParameter("generalreason");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<PharmacyGeneralVariables> list = service.getPharmacyGeneralVariables();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, generalName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyGeneralVariables pharmacyGeneralVariables = new PharmacyGeneralVariables();

                    pharmacyGeneralVariables.setName(generalName);
                    pharmacyGeneralVariables.setDescription(description);

                    service.savePharmacyGeneralVariables(pharmacyGeneralVariables);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacyGeneralVariables pharmacyGeneralVariables = new PharmacyGeneralVariables();

                pharmacyGeneralVariables = service.getPharmacyGeneralVariablesByUuid(uuid);

                // saving/updating a record
                pharmacyGeneralVariables.setName(generalName);//(drugName);
                pharmacyGeneralVariables.setDescription(description);
                service.savePharmacyGeneralVariables(pharmacyGeneralVariables);

            }

        } else if (uuidvoid != null) {

            PharmacyGeneralVariables pharmacyGeneralVariables = new PharmacyGeneralVariables();

            pharmacyGeneralVariables = service.getPharmacyGeneralVariablesByUuid(uuidvoid);

            pharmacyGeneralVariables.setVoided(true);
            pharmacyGeneralVariables.setVoidReason(reason);

            service.savePharmacyGeneralVariables(pharmacyGeneralVariables);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyGeneralVariables> generalNamee, int size) {

        supplierNames = new JSONArray();
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

            supplierNames.put("edit");
            editPharmacy = false;
        } else
            supplierNames.put("");
        supplierNames.put("");
        supplierNames.put(generalNamee.get(size).getUuid());
        supplierNames.put(generalNamee.get(size).getName());
        supplierNames.put(generalNamee.get(size).getDescription());
        if (deletePharmacy) {
            supplierNames.put("void");
            deletePharmacy = false;
        } else
            supplierNames.put("");

        return supplierNames;
    }

    public synchronized String getDropDown(List<PharmacyGeneralVariables> generalNamee, int size) {

        return generalNamee.get(size).getName();
    }

    public synchronized boolean getCheck(List<PharmacyGeneralVariables> generalNamee, int size, String names) {
        if (generalNamee.get(size).getName().equalsIgnoreCase(names)) {

            return true;

        } else
            return false;

    }
}
