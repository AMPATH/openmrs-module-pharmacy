package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacySupplier;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class SupplierNamesController {

    private static final Log log = LogFactory.getLog(SupplierNamesController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray supplierNames;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/supplierName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        List<PharmacySupplier> list = service.getPharmacySupplier();
        int size = list.size();
        JSONObject json = new JSONObject();

        JSONArray jsons = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsons.put("" + getDropDown(list, i));
                        }
                    } else {
                        jsons.put("" + null);

                    }
                    response.getWriter().print(jsons);
                }

            } else {

                if (size != 0) {
                    for (int i = 0; i < size; i++) {

                        json.accumulate("aaData", getArray(list, i));

                    }

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

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/supplierName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {

        String supplierName = request.getParameter("suppliername");
        String description = request.getParameter("description");
        String edit = request.getParameter("supplieredit");
        String uuid = request.getParameter("supplieruuid");
        String uuidvoid = request.getParameter("supplieruuidvoid");
        String reason = request.getParameter("supplierreason");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {

                //check for same entry before saving
                List<PharmacySupplier> list = service.getPharmacySupplier();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, supplierName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacySupplier supplierNamee = new PharmacySupplier();
                    supplierNamee.setName(supplierName);
                    supplierNamee.setDescription(description);

                    service.savePharmacySupplier(supplierNamee);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacySupplier supplierNamee = new PharmacySupplier();

                supplierNamee = service.getPharmacySupplierByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals(supplierNamee.getCreator().getUserId())) {

                    // saving/updating a record
                    supplierNamee.setName(supplierName);//(drugName);
                    supplierNamee.setDescription(description);

                    service.savePharmacySupplier(supplierNamee);
                }
            }

        }

        else if (uuidvoid != null) {

            PharmacySupplier supplierNamee = new PharmacySupplier();

            supplierNamee = service.getPharmacySupplierByUuid(uuidvoid);

            supplierNamee.setVoided(true);
            supplierNamee.setVoidReason(reason);

            service.savePharmacySupplier(supplierNamee);

        }

    }

    public synchronized JSONArray getArray(List<PharmacySupplier> supplierNamee, int size) {

        supplierNames = new JSONArray();

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

            supplierNames.put("edit");
            editPharmacy = false;
        } else
            supplierNames.put("");

        supplierNames.put("");
        supplierNames.put(supplierNamee.get(size).getUuid());
        supplierNames.put(supplierNamee.get(size).getName());
        supplierNames.put(supplierNamee.get(size).getDescription());
        if (deletePharmacy) {
            supplierNames.put("void");
            deletePharmacy = false;
        } else
            supplierNames.put("");
        return supplierNames;
    }

    public synchronized String getDropDown(List<PharmacySupplier> supplierNamee, int size) {

        return supplierNamee.get(size).getName();
    }

    public synchronized boolean getCheck(List<PharmacySupplier> supplierNamee, int size, String names) {
        if (supplierNamee.get(size).getName().equalsIgnoreCase(names)) {

            return true;

        } else
            return false;

    }
}
