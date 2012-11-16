package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Role;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.model.PharmacyStore;
import org.openmrs.module.pharmacy.model.PharmacyTransactionTypes;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@Controller
public class TransactionNamesController {

    private static final Log log = LogFactory.getLog(TransactionNamesController.class);

    private JSONArray drugStrengthA;

    public PharmacyService service;

    private boolean found = false;

    private JSONArray drugNamess;

    private String originalbindrug;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private UserContext userService;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONArray datad2;
    private int sizeUsers;
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private List<PharmacyTransactionTypes> pharmacyTransactionTypes;
    private int size;
    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private List<PharmacyStore> pharmacyStoreList;
    private List<PharmacyStore> pharmacyInventory;
    private PharmacyTransactionTypes pharmacyTransactionTypes1;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/transactionsName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        service = Context.getService(PharmacyService.class);

        String locationVal = null;

        pharmacyLocationUsersByUserName = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        sizeUsers = pharmacyLocationUsersByUserName.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = pharmacyLocationUsersByUserName.get(0).getLocation();


        }
        pharmacyTransactionTypes = service.getPharmacyTransactionTypes();
        size = pharmacyTransactionTypes.size();
        jsonObject = new JSONObject();

        jsonArray = new JSONArray();

        try {

            if (drop != null) {
                if (drop.equalsIgnoreCase("drop")) {
                    if (size != 0) {
                        for (int i = 0; i < size; i++) {
                            jsonArray.put("" + getDropDown(pharmacyTransactionTypes, i));
                        }

                    } else
                        jsonArray.put("" + null);

                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("total")) {


                    pharmacyStoreList = service.getPharmacyInventory();
                    size = pharmacyStoreList.size();

                    if (size != 0) {
                        for (int i = 0; i < size; i++) {


                            int val = getCheckSize(pharmacyStoreList, i, drug);

                            if (val == 0) {

                            } else {
                                jsonArray.put("" + val);
                            }
                            if (val != 0)
                                break;
                        }
                    } else
                        jsonArray.put("" + null);

                    response.getWriter().print(jsonArray);
                } else if (drop.equalsIgnoreCase("totalTwo")) {
                    pharmacyInventory = service.getPharmacyInventory();
                    size = pharmacyInventory.size();

                    int total = 0;
                    for (int i = 0; i < size; i++) {


                        if ((service.getPharmacyLocationsByUuid(pharmacyInventory.get(i).getLocation()).getName()
                                .equalsIgnoreCase(locationVal))) {

                            if (drug.equals(pharmacyInventory.get(i).getDrugs())) {

                                total += pharmacyInventory.get(i).getQuantity();

                            }

                        }
                    }
                    jsonArray.put("" + total);
                    response.getWriter().print(jsonArray);
                }

            } else {

                if (size != 0) {
                    for (int i = 0; i < size; i++) {

                        jsonObject.accumulate("aaData", getArray(pharmacyTransactionTypes, i));

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

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/transactionsName")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        String transactionsName = request.getParameter("transactionsname");//description
        String description = request.getParameter("description");//description
        String edit = request.getParameter("transactionseedit");
        String uuid = request.getParameter("transactionsuuid");
        String uuidvoid = request.getParameter("transactionsuuidvoid");
        String reason = request.getParameter("transactionsreason");
        userService = Context.getUserContext();
        if (edit != null) {
            if (edit.equalsIgnoreCase("false")) {
                //check for same entry before saving
                pharmacyTransactionTypes = service.getPharmacyTransactionTypes();
                int size = pharmacyTransactionTypes.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(pharmacyTransactionTypes, i, transactionsName);
                    if (found)
                        break;
                }

                if (!found) {

                    pharmacyTransactionTypes1 = new PharmacyTransactionTypes();
                    pharmacyTransactionTypes1.setName(transactionsName);
                    pharmacyTransactionTypes1.setDescription(description);

                    service.savePharmacyTransactionTypes(pharmacyTransactionTypes1);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                pharmacyTransactionTypes1 = new PharmacyTransactionTypes();

                pharmacyTransactionTypes1 = service.getPharmacyTransactionTypesByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyTransactionTypes1.getCreator().getUserId())) {

                    // saving/updating a record
                    pharmacyTransactionTypes1.setName(transactionsName);
                    pharmacyTransactionTypes1.setDescription(description);

                    service.savePharmacyTransactionTypes(pharmacyTransactionTypes1);
                }
            }

        } else if (uuidvoid != null) {

            pharmacyTransactionTypes1 = new PharmacyTransactionTypes();

            pharmacyTransactionTypes1 = service.getPharmacyTransactionTypesByUuid(uuidvoid);

            pharmacyTransactionTypes1.setVoided(true);
            pharmacyTransactionTypes1.setVoidReason(reason);

            service.savePharmacyTransactionTypes(pharmacyTransactionTypes1);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size) {

        drugNamess = new JSONArray();
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

            drugNamess.put("edit");
            editPharmacy = false;
        } else
            drugNamess.put("");
        drugNamess.put("");
        drugNamess.put(pharmacyTransactionTypes.get(size).getUuid());
        drugNamess.put(pharmacyTransactionTypes.get(size).getName());
        drugNamess.put(pharmacyTransactionTypes.get(size).getDescription());

        if (deletePharmacy) {
            drugNamess.put("void");
            deletePharmacy = false;
        } else
            drugNamess.put("");
        return drugNamess;
    }

    public synchronized String getDropDown(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size) {

        return pharmacyTransactionTypes.get(size).getName();
    }

    public synchronized Integer getCheckSize(List<PharmacyStore> pharmacyStore, int size, String name) {

        if ((pharmacyStore.get(size).getDrugs().getName().equals(name))) {

            return pharmacyStore.get(size).getQuantity();

        } else
            return 0;

    }

    public synchronized boolean getCheck(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size, String drugNamess) {
        if (pharmacyTransactionTypes.get(size).getName().equalsIgnoreCase(drugNamess)) {

            return true;

        } else
            return false;

    }
}
