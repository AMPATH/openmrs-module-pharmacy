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

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/transactionsName")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {

        userService = Context.getUserContext();
        String drop = request.getParameter("drop");
        String drug = request.getParameter("drug");
        service = Context.getService(PharmacyService.class);

        String locationVal=null;

        List<PharmacyLocationUsers> listUsers= service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers =listUsers.size();





        if(sizeUsers>1){
            locationVal=request.getSession().getAttribute("location").toString();

        }
        else if(sizeUsers==1)
        {
            locationVal=listUsers.get(0).getLocation();


        }
        List<PharmacyTransactionTypes> list = service.getPharmacyTransactionTypes();
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

                    } else
                        jsons.put("" + null);

                    response.getWriter().print(jsons);
                } else if (drop.equalsIgnoreCase("total")) {




                    List<PharmacyStore> listSize = service.getPharmacyInventory();
                    int sizeList = listSize.size();

                    if (size != 0) {
                        for (int i = 0; i < sizeList; i++) {


                            int val = getCheckSize(listSize, i, drug);

                            if (val == 0) {

                            } else {
                                jsons.put("" + val);
                            }
                            if (val != 0)
                                break;
                        }
                    } else
                        jsons.put("" + null);

                    response.getWriter().print(jsons);
                }
                else if(drop.equalsIgnoreCase("totalTwo")){
                    List<PharmacyStore> listSize = service.getPharmacyInventory();
                    int sizeList = listSize.size();

                    int total=0;
                    for (int i = 0; i < sizeList; i++) {


                        if ((service.getPharmacyLocationsByUuid(listSize.get(i).getLocation()).getName()
                                .equalsIgnoreCase(locationVal))) {

                            if (drug.equals(listSize.get(i).getDrugs())) {

                                total += listSize.get(i).getQuantity();

                            }

                        }
                    }
                    jsons.put("" + total);
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
                List<PharmacyTransactionTypes> list = service.getPharmacyTransactionTypes();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, transactionsName);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyTransactionTypes transactionNamee = new PharmacyTransactionTypes();
                    transactionNamee.setName(transactionsName);
                    transactionNamee.setDescription(description);

                    service.savePharmacyTransactionTypes(transactionNamee);

                } else //do code to display to the user
                {

                }

            } else if (edit.equalsIgnoreCase("true")) {
                PharmacyTransactionTypes transactionName = new PharmacyTransactionTypes();

                transactionName = service.getPharmacyTransactionTypesByUuid(uuid);

                if (userService.getAuthenticatedUser().getUserId().equals( transactionName.getCreator().getUserId())) {

                    // saving/updating a record
                    transactionName.setName(transactionsName);
                    transactionName.setDescription(description);

                    service.savePharmacyTransactionTypes(transactionName);
                }
            }

        }

        else if (uuidvoid != null) {

            PharmacyTransactionTypes transactionNamev = new PharmacyTransactionTypes();

            transactionNamev = service.getPharmacyTransactionTypesByUuid(uuidvoid);

            transactionNamev.setVoided(true);
            transactionNamev.setVoidReason(reason);

            service.savePharmacyTransactionTypes(transactionNamev);

        }

    }

    public synchronized JSONArray getArray(List<PharmacyTransactionTypes> pharmacyTransactionTypes, int size) {

        drugNamess = new JSONArray();
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
