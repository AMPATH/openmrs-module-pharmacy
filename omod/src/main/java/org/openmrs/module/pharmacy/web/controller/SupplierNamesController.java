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
    private JSONObject jsonObject;
    private List<PharmacySupplier> pharmacySupplier;
    private int size;
    private JSONArray jsonArray;
    private PharmacySupplier pharmacySupplier1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/supplierName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String uuid = request.getParameter("nameuuid");
        String drop = request.getParameter("drop");
        service = Context.getService(PharmacyService.class);
        pharmacySupplier = service.getPharmacySupplier();
        size = pharmacySupplier.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsonArray.put("" + getDropDown(pharmacySupplier, i));
                        }
                    } else {
                        jsonArray.put("" + null);

                    }
                    response.getWriter().print(jsonArray);
                }

            } else {

                if (size != 0) {
                    for (int i = 0; i < size; i++) {

                        jsonObject.accumulate("aaData", getArray(pharmacySupplier, i));

                    }

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
                pharmacySupplier = service.getPharmacySupplier();
                size = pharmacySupplier.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(pharmacySupplier, i, supplierName);
                    if (found)
                        break;
                }

                if (!found) {

                    pharmacySupplier1 = new PharmacySupplier();
                    pharmacySupplier1.setName(supplierName);
                    pharmacySupplier1.setDescription(description);

                    service.savePharmacySupplier(pharmacySupplier1);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                pharmacySupplier1 = new PharmacySupplier();

                pharmacySupplier1 = service.getPharmacySupplierByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals(pharmacySupplier1.getCreator().getUserId())) {

                    // saving/updating a record
                    pharmacySupplier1.setName(supplierName);//(drugName);
                    pharmacySupplier1.setDescription(description);

                    service.savePharmacySupplier(pharmacySupplier1);
                }
            }

        } else if (uuidvoid != null) {

            pharmacySupplier1 = new PharmacySupplier();

            pharmacySupplier1 = service.getPharmacySupplierByUuid(uuidvoid);

            pharmacySupplier1.setVoided(true);
            pharmacySupplier1.setVoidReason(reason);

            service.savePharmacySupplier(pharmacySupplier1);

        }

    }

    public synchronized JSONArray getArray(List<PharmacySupplier> supplierNamee, int size) {

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
