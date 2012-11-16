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
    private List<PharmacyGeneralVariables> pharmacyGeneralVariables;
    private int size;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private PharmacyGeneralVariables generalVariables;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/generalName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);

        pharmacyGeneralVariables = service.getPharmacyGeneralVariables();
        size = pharmacyGeneralVariables.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();


        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {

                    for (int i = 0; i < size; i++) {
                        jsonArray.put("" + getDropDown(pharmacyGeneralVariables, i));
                    }

                    response.getWriter().print(jsonArray);
                }

            } else {

                for (int i = 0; i < size; i++) {

                    jsonObject.accumulate("aaData", getArray(pharmacyGeneralVariables, i));

                }

                if (!jsonObject.has("aaData")) {

                    datad2 = new JSONArray();
                    datad2.put("None");
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
                pharmacyGeneralVariables = service.getPharmacyGeneralVariables();
                size = pharmacyGeneralVariables.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(pharmacyGeneralVariables, i, generalName);
                    if (found)
                        break;
                }

                if (!found) {

                    generalVariables = new PharmacyGeneralVariables();

                    generalVariables.setName(generalName);
                    generalVariables.setDescription(description);

                    service.savePharmacyGeneralVariables(generalVariables);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                generalVariables = new PharmacyGeneralVariables();

                generalVariables = service.getPharmacyGeneralVariablesByUuid(uuid);

                // saving/updating a record
                generalVariables.setName(generalName);//(drugName);
                generalVariables.setDescription(description);
                service.savePharmacyGeneralVariables(generalVariables);

            }

        } else if (uuidvoid != null) {

            generalVariables = new PharmacyGeneralVariables();

            generalVariables = service.getPharmacyGeneralVariablesByUuid(uuidvoid);

            generalVariables.setVoided(true);
            generalVariables.setVoidReason(reason);

            service.savePharmacyGeneralVariables(generalVariables);

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
