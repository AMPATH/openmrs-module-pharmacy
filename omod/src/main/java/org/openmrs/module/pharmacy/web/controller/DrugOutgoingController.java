package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Role;
import org.openmrs.api.ConceptService;
import org.openmrs.api.LocationService;
import org.openmrs.api.context.Context;
import org.openmrs.api.context.UserContext;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
public class DrugOutgoingController {

    private static final Log log = LogFactory.getLog(DrugOutgoingController.class);

    private JSONArray data;

    private JSONArray datad;

    public PharmacyService service;

    String drop = null;

    private boolean found = false;

    private String uuid;

    private String drugstrength;

    private String drugunit;

    private String formulation;

    private String originalbindrug;

    private String dialog = null;

    private LocationService serviceLocation;

    private String filter = null;

    private String uuidfilter = null;

    private String outgoingdrug;

    private String outgoingquantityin;

    private String outgoingmax;

    private String outgoingbatch;

    private String outgoings11;

    private String outgoingmin;

    private String outgoingnumber;

    private boolean exit = false;

    private String answers;

    private String uuid1;

    private String uuid2;

    private String uuid3;

    private String uuid4;

    private ConceptService serviceDrugs;

    private UserContext userService;

    private String requisition;

    private String issued;

    private String authorized;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        userService = Context.getUserContext();
        String locationVal = null;

        service = Context.getService(PharmacyService.class);

        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        filter = request.getParameter("sSearch");

        List<PharmacyStoreOutgoing> List = service.getPharmacyStoreOutgoing();
        int size = List.size();

        JSONObject json = new JSONObject();
        JSONArray jsons = new JSONArray();
        response.setContentType("application/json");

        if (filter.length() > 2) {
            originalbindrug = filter;

            serviceLocation = Context.getLocationService();
            serviceDrugs = Context.getConceptService();

            //			outgoingdrug = filter.substring(0, filter.indexOf("("));
            //
            //			drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
            //			drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
            //			formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
            //
            //
            //		String uuidvalue = service.getDrugNameByName(outgoingdrug).getUuid();
            //		String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
            //		String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
            //		String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();
            //
            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuidfilter = getString(dname, i, originalbindrug);
                if (uuidfilter != null)
                    break;

            }
        }
        try {
            if (dialog != null) {

                for (int i = 0; i < size; i++) {

                    json.accumulate("aaData", getArrayDialog(List, i));
                }

            } else {
                for (int i = 0; i < size; i++) {
                    if (List.get(i).getDestination().getName().equalsIgnoreCase(locationVal)) {
                        JSONArray val = getArray(List, i, locationVal);
                        if (val != null)
                            json.accumulate("aaData", val);

                    }
                    if (exit)
                        break;
                    data = new JSONArray();
                }
                if (!json.has("aaData")) {

                    data = new JSONArray();
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");

                    json.accumulate("aaData", data);
                }

            }
            exit = false;
            json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
            json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
            json.accumulate("iDisplayStart", 0);
            json.accumulate("iDisplayLength", 10);

            response.getWriter().print(json);

            response.flushBuffer();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugOutgoing")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        serviceLocation = Context.getLocationService();
        List<PharmacyStoreOutgoing> pStoreIncoming = new ArrayList<PharmacyStoreOutgoing>();
        List<PharmacyStoreApproved> pStoreApproved = new ArrayList<PharmacyStoreApproved>();
        List<DrugTransactions> drugTransactions = new ArrayList<DrugTransactions>();


        String locationVal = null;
        String[] drugId;
        String[] drugQ, quantityToGive;
        service = Context.getService(PharmacyService.class);

        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }
        outgoingdrug = request.getParameter("outgoingdrug");
        outgoingquantityin = request.getParameter("outgoingquantityin");

        outgoingnumber = request.getParameter("outgoingnumber");
        outgoingmax = request.getParameter("outgoingmax");
        outgoingmin = request.getParameter("outgoingmin");
        outgoingmin = request.getParameter("outgoingmin");

        requisition = request.getParameter("requisition");
        issued = request.getParameter("issued");
        authorized = request.getParameter("authorized");

        userService = Context.getUserContext();
        answers = request.getParameter("answers");

        uuid1 = request.getParameter("one");
        uuid2 = request.getParameter("two");
        uuid3 = request.getParameter("three");
        uuid4 = request.getParameter("four");

        outgoingbatch = request.getParameter("outgoingbatch");
        outgoings11 = request.getParameter("outgoings11");

        String outgoingexpire = request.getParameter("outgoingexpire");

        String outgoingreason = request.getParameter("outgoingreason");
        String outgoinguuidvoid = request.getParameter("outgoinguuidvoid");

        String outgoinguuidextra = request.getParameter("outgoinguuidextra");

        String destination = request.getParameter("destination");
        String location = request.getParameter("location");
        String supplier = request.getParameter("supplierout");
        String transactions = request.getParameter("transactions");
        String deliveryno = request.getParameter("delivery");

        String outgoingedit = request.getParameter("outgoingedit");
        String outgoinguuid = request.getParameter("outgoinguuid");
        String outgoingcom = request.getParameter("outgoingcom");
        String authorizedo = request.getParameter("authorizedo");     //requisitiono
        String requisitiono = request.getParameter("authorizedo");     //
        originalbindrug = outgoingdrug;


        drugId = request.getParameterValues("drugId");


        drugQ = request.getParameterValues("quantity");

        //
        quantityToGive = request.getParameterValues("quantityToGive");


        if (outgoinguuidvoid == null) {

            //		outgoingdrug = outgoingdrug.substring(0, outgoingdrug.indexOf("("));
            //
            //		drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
            //		drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
            //		formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
            //
            //
            //		String uuidvalue = service.getDrugNameByName(outgoingdrug).getUuid();
            //		String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
            //		String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
            //		String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();
            //
            //

            serviceDrugs = Context.getConceptService();

            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuid = getString(dname, i, originalbindrug);
                if (uuid != null)
                    break;

            }

        }
        if (outgoingedit != null) {
            if (outgoingedit.equalsIgnoreCase("false")) {

                ///check for same entry before saving
                List<PharmacyStoreOutgoing> list = service.getPharmacyStoreOutgoing();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, outgoingdrug);
                    if (found)
                        break;
                }

                PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();

                //get drug details 		drugstrength drugunit formulation

                pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));

                if (outgoingmax != null) {

                    pharmacyStoreOutgoing.setMaxLevel(Integer.parseInt(outgoingmax));

                } else if (outgoingmax == null) {
                    pharmacyStoreOutgoing.setMaxLevel(0);
                }

                if (outgoingmin != null) {
                    pharmacyStoreOutgoing.setMinLevel(Integer.parseInt(outgoingmin));

                } else if (outgoingmin == null) {
                    pharmacyStoreOutgoing.setMinLevel(0);
                }

                if (outgoingbatch != null) {
                    pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(outgoingbatch));

                } else if (outgoingbatch == null) {
                    pharmacyStoreOutgoing.setBatchNo(0);
                }

                if (outgoings11 != null) {
                    pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

                } else if (outgoings11 == null) {
                    pharmacyStoreOutgoing.setS11(0);
                }

                Date date = null;
                try {
                    if (outgoingexpire != null) {
                        date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
                    }
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    log.error("Error generated", e);
                }

                pharmacyStoreOutgoing.setExpireDate(date);
                serviceLocation = Context.getLocationService();

                pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
                pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(locationVal));

                pharmacyStoreOutgoing.setChangeReason(null);

                if (supplier == null) {
                    pharmacyStoreOutgoing.setSupplier(null);

                } else
                    pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));

                pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                service.savePharmacyStoreOutgoing(pharmacyStoreOutgoing);

            } else if (outgoingedit.equalsIgnoreCase("true")) {

                PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoinguuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStoreOutgoing.getCreator().getUserId())) {

                    pharmacyStoreOutgoing.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    pharmacyStoreOutgoing.setQuantityIn(Integer.parseInt(outgoingquantityin));

                    pharmacyStoreOutgoing.setMaxLevel(0);

                    pharmacyStoreOutgoing.setMinLevel(0);

                    if (outgoingbatch != null) {
                        pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(outgoingbatch));

                    } else if (outgoingbatch == null) {
                        pharmacyStoreOutgoing.setBatchNo(0);
                    }

                    if (outgoings11 != null) {
                        pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

                    } else if (outgoings11 == null) {
                        pharmacyStoreOutgoing.setS11(0);
                    }

                    Date date = null;
                    try {
                        if (outgoingexpire != null) {
                            date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
                        }
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }
                    pharmacyStoreOutgoing.setExpireDate(date);
                    serviceLocation = Context.getLocationService();

                    pharmacyStoreOutgoing.setDestination(service.getPharmacyLocationsByName(destination));
                    pharmacyStoreOutgoing.setLocation(service.getPharmacyLocationsByName(location));

                    pharmacyStoreOutgoing.setChangeReason(null);

                    if (supplier == null) {
                        pharmacyStoreOutgoing.setSupplier(null);

                    } else
                        pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));

                    pharmacyStoreOutgoing.setTransaction(service.getPharmacyTransactionTypesByName(transactions));

                    service.savePharmacyStoreOutgoing(pharmacyStoreOutgoing);
                    PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();

                    pharmacyStoreApproved.setDrugs(pharmacyStoreOutgoing.getDrugs());
                    pharmacyStoreApproved.setQuantityIn(0);
                    pharmacyStoreApproved.setCategory(pharmacyStoreOutgoing.getCategory());
                    pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getDestination());

                    pharmacyStoreApproved.setLocation(pharmacyStoreOutgoing.getDestination());
                    pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getLocation());
                    pharmacyStoreApproved.setTransaction(pharmacyStoreOutgoing.getTransaction());
                    pharmacyStoreApproved.setIncoming(pharmacyStoreOutgoing.getIncoming());

                    pharmacyStoreApproved.setOutgoing(pharmacyStoreOutgoing);
                    pharmacyStoreApproved.setApproved(false);
                    pharmacyStoreApproved.setS11(pharmacyStoreOutgoing.getS11());

                    pharmacyStoreApproved.setVoided(pharmacyStoreOutgoing.getVoided());
                    pharmacyStoreApproved.setMaxLevel(pharmacyStoreOutgoing.getMaxLevel());
                    pharmacyStoreApproved.setMinLevel(pharmacyStoreOutgoing.getMinLevel());
                    pharmacyStoreApproved.setBatchNo(pharmacyStoreOutgoing.getBatchNo());
                    pharmacyStoreApproved.setStatus("Approved");
                }
            }

        } else if (outgoinguuidvoid != null) {
            PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
            pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoinguuidvoid);

            pharmacyStoreOutgoing.setVoided(true);
            pharmacyStoreOutgoing.setVoidReason(outgoingreason);

            service.savePharmacyStoreOutgoing(pharmacyStoreOutgoing);

        } else if (outgoinguuidextra != null) {
            boolean canSave = false;

            for (int y = 0; y < drugId.length; y++) {


                PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(drugId[y]);

                PharmacyStore pharmacyStore = new PharmacyStore();
                //
                pharmacyStore = service.getDrugDispenseSettingsByLocation(service.getPharmacyLocationsByName(locationVal)).getInventoryId();


                System.out.println(pharmacyStore.getQuantity() + "-----------------------------------------------" + pharmacyStoreOutgoing.getQuantityIn());

                if (Integer.parseInt(quantityToGive[y]) <= pharmacyStore.getQuantity()) {
                    canSave = true;
                    int num;

                    if (Integer.parseInt(quantityToGive[y]) == pharmacyStoreOutgoing.getQuantityIn()) {

                        pharmacyStoreOutgoing.setApproved(true);
                        pharmacyStoreOutgoing.setQuantityIn(0);
                        num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));
                        System.out.println(pharmacyStore.getQuantity() + "===pharmacyStore.getS11()pharmacyStore+==" + num + "===.getS11()pharmacyStore.getS11()" + Integer.parseInt(quantityToGive[y]));

                        pharmacyStore.setQuantity(num);
                        service.savePharmacyInventory(pharmacyStore);

                    } else {

                        if (Integer.parseInt(quantityToGive[y]) < pharmacyStoreOutgoing.getQuantityIn()) {
                            pharmacyStoreOutgoing.setQuantityIn((pharmacyStoreOutgoing.getQuantityIn() - Integer.parseInt(quantityToGive[y])));
                        }

                        System.out.println(pharmacyStore.getQuantity() + "===pharmacyStore.getS11()pharmacyStore.getS11()pharmacyStore.getS11()" + Integer.parseInt(quantityToGive[y]));
                        num = (pharmacyStore.getQuantity() - Integer.parseInt(quantityToGive[y]));


                        pharmacyStore.setQuantity(num);
                        service.savePharmacyInventory(pharmacyStore);

                    }

                    pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                    pharmacyStoreOutgoing.setIssued(Context.getUserService().getUserByUsername(Context.getAuthenticatedUser().getUsername()));

                    pStoreIncoming.add(pharmacyStoreOutgoing);

                    /////     //////////////////////////

                    DrugTransactions drugTransaction = new DrugTransactions();
                    //
                    //transactions
                    drugTransaction.setDrugs(pharmacyStoreOutgoing.getDrugs());
                    drugTransaction.setQuantityIn(0);
                    drugTransaction.setQuantityOut(Integer.parseInt(quantityToGive[y]));
                    drugTransaction.setexpireDate(pharmacyStore.getExpireDate());
                    drugTransaction.setComment("Give out");

                    drugTransaction.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                    drugTransactions.add(drugTransaction);

                    /////////////////////////////////


                    PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();

                    pharmacyStoreApproved.setDrugs(pharmacyStoreOutgoing.getDrugs());
                    pharmacyStoreApproved.setQuantityIn(Integer.parseInt(quantityToGive[y]));
                    pharmacyStoreApproved.setCategory(pharmacyStoreOutgoing.getCategory());
                    pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getDestination());

                    pharmacyStoreApproved.setLocation(pharmacyStoreOutgoing.getDestination());
                    pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getLocation());
                    pharmacyStoreApproved.setTransaction(pharmacyStoreOutgoing.getTransaction());
                    pharmacyStoreApproved.setIncoming(pharmacyStoreOutgoing.getIncoming());

                    pharmacyStoreApproved.setOutgoing(pharmacyStoreOutgoing);
                    pharmacyStoreApproved.setApproved(false);
                    pharmacyStoreApproved.setS11(pharmacyStoreOutgoing.getS11());


                    pharmacyStoreApproved.setVoided(pharmacyStoreOutgoing.getVoided());
                    pharmacyStoreApproved.setMaxLevel(pharmacyStore.getMaxLevel());
                    pharmacyStoreApproved.setMinLevel(pharmacyStore.getMinLevel());
                    pharmacyStoreApproved.setBatchNo(pharmacyStore.getBatchNo());
                    pharmacyStoreApproved.setExpireDate(pharmacyStore.getExpireDate());
                    pharmacyStoreApproved.setDeliveryNo(pharmacyStore.getDeliveryNo());
                    pharmacyStoreApproved.setRequested(pharmacyStoreOutgoing.getRequested());
                    pharmacyStoreApproved.setAuthorized(pharmacyStoreOutgoing.getAuthorized());
                    pharmacyStoreApproved.setIssued(pharmacyStoreOutgoing.getissued());

                    pharmacyStoreApproved.setStatus("Approved");

                    PharmacyStoreIncoming PharmacyStoreIncoming = pharmacyStoreOutgoing.getIncoming();
                    PharmacyStoreIncoming.setApproved(true);

                    PharmacyStoreIncoming.setStatus("Apprroved");

                    service.savePharmacyStoreIncoming(PharmacyStoreIncoming);

                    pStoreApproved.add(pharmacyStoreApproved);

                }
            }
            outgoinguuidextra = null;
            if (canSave) {
                service.savePharmacyStoreOutgoing(pStoreIncoming);
                service.savePharmacyStoreApproved(pStoreApproved);
                service.saveDrugTransactions(drugTransactions);
            }


            if (outgoings11 != null) {

                PharmacyStoreOutgoing pharmacyStoreOutgoing = new PharmacyStoreOutgoing();
                pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoinguuidextra);
                /* String to split. */

                String[] temp;

                /* delimiter */
                String delimiter = ",";
                /* given string will be split by the argument delimiter provided. */
                temp = answers.split(delimiter);
                int total = 0;
                String name = "uuid";

                for (int i = 0; i < temp.length; i++) {
                    if (!temp[i].isEmpty())
                        total += Integer.parseInt(temp[i]);

                    if (i >= 1) {

                        if (i == 1) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid1);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        } else if (i == 2) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid2);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 3) {

                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid3);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);
                        } else if (i == 4) {
                            PharmacyStore pharmacyStore = new PharmacyStore();

                            pharmacyStore = service.getPharmacyInventoryByUuid(uuid4);

                            pharmacyStore.setQuantity(pharmacyStore.getQuantity() - Integer.parseInt(temp[i]));

                            service.savePharmacyInventory(pharmacyStore);

                        }

                    }

                }

                if (total == pharmacyStoreOutgoing.getQuantityIn()) {

                    pharmacyStoreOutgoing.setApproved(true);
                    pharmacyStoreOutgoing.setQuantityIn(0);
                } else {
                    if (total < pharmacyStoreOutgoing.getQuantityIn()) {
                        pharmacyStoreOutgoing.setQuantityIn((pharmacyStoreOutgoing.getQuantityIn() - total));
                    }

                }

                //pharmacyStoreOutgoing.setBatchNo(Integer.parseInt(outgoingnumber));
                //pharmacyStoreOutgoing.setDeliveryNo(Integer.parseInt(deliveryno));
                //				Date date = null;
                //				try {
                //					if(outgoingexpire!=null){
                //					date = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire);
                //					}
                //				}
                //				catch (ParseException e) {
                //					// TODO Auto-generated catch block
                //					log.error("Error generated", e);
                //				}
                //
                //				pharmacyStoreOutgoing.setExpireDate(date);
                //				pharmacyStoreOutgoing.setMaxLevel(0);
                //				pharmacyStoreOutgoing.setMinLevel(0);
                pharmacyStoreOutgoing.setS11(Integer.parseInt(outgoings11));

                if (requisition != null && issued != null && authorized != null) {
                    pharmacyStoreOutgoing.setRequested(Context.getUserService().getUser(Integer.parseInt(requisition)));
                    pharmacyStoreOutgoing.setAuthorized(Context.getUserService().getUser(Integer.parseInt(authorized)));
                    pharmacyStoreOutgoing.setIssued(Context.getUserService().getUser(Integer.parseInt(issued)));
                }

//                //pharmacyStoreOutgoing.setSupplier(service.getPharmacySupplierByName(supplier));
//
//                DrugTransactions drugTransactions = new DrugTransactions();
//
//                //transactions
//                drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
//                drugTransactions.setQuantityOut(0);
//                drugTransactions.setQuantityIn(Integer.parseInt(outgoingnumber));
//                drugTransactions.setexpireDate(null);
//                drugTransactions.setComment("Give out");
//
//                drugTransactions.setLocation(service.getPharmacyLocationsByName(locationVal).getUuid());

                //drugTransactions.setLocation(location)
                service.saveDrugTransactions(drugTransactions);


                PharmacyStoreApproved pharmacyStoreApproved = new PharmacyStoreApproved();

                pharmacyStoreApproved.setDrugs(pharmacyStoreOutgoing.getDrugs());
                pharmacyStoreApproved.setQuantityIn(total);
                pharmacyStoreApproved.setCategory(pharmacyStoreOutgoing.getCategory());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getDestination());

                pharmacyStoreApproved.setLocation(pharmacyStoreOutgoing.getDestination());
                pharmacyStoreApproved.setDestination(pharmacyStoreOutgoing.getLocation());
                pharmacyStoreApproved.setTransaction(pharmacyStoreOutgoing.getTransaction());
                pharmacyStoreApproved.setIncoming(pharmacyStoreOutgoing.getIncoming());

                pharmacyStoreApproved.setOutgoing(pharmacyStoreOutgoing);
                pharmacyStoreApproved.setApproved(false);
                pharmacyStoreApproved.setS11(pharmacyStoreOutgoing.getS11());

                pharmacyStoreApproved.setVoided(pharmacyStoreOutgoing.getVoided());
                pharmacyStoreApproved.setMaxLevel(pharmacyStoreOutgoing.getMaxLevel());
                pharmacyStoreApproved.setMinLevel(pharmacyStoreOutgoing.getMinLevel());
                pharmacyStoreApproved.setBatchNo(pharmacyStoreOutgoing.getBatchNo());
                pharmacyStoreApproved.setStatus("Approved");

                //				pharmacyStore.setLocation(serviceLocation.getLocation(locationVal).getUuid());
                //
                //
                //				pharmacyStore.setQuantity(Integer.parseInt(incomingnumber));
                //
                //				pharmacyStore.setQuantityIn(0);
                //				pharmacyStore.setQuantityOut(0);
                //				pharmacyStore.setChangeReason(null);
                //
                //
                //
                //
                //				pharmacyStore.setBatchNo(Integer.parseInt(incomingbatch));
                //				pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));
                //
                //
                //				pharmacyStore.setExpireDate(date);
                //				pharmacyStore.setMaxLevel(Integer.parseInt(incomingmax));
                //				pharmacyStore.setMinLevel(Integer.parseInt(incomingmin));
                //				pharmacyStore.setS11(Integer.parseInt(incomings11));
                //
                //				pharmacyStore.setSupplier(service.getPharmacySupplierByName(supplier));
                //				pharmacyStore.setTransaction(pharmacyStoreIncoming.getTransaction());
                //				pharmacyStore.setIncoming(service.getPharmacyStoreIncomingByUuid(incominguuidextra));
                //
                //
                //
                //				service.savePharmacyInventory(pharmacyStore);

                service.savePharmacyStoreOutgoing(pharmacyStoreOutgoing);
                service.savePharmacyStoreApproved(pharmacyStoreApproved);

            }

        }

    }

    public synchronized JSONArray getArray(List<PharmacyStoreOutgoing> pharmacyStore, int size, String location) {

        if (filter.length() > 2) {

            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {

                if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                        && (!pharmacyStore.get(size).getApproved())) {

                    data = new JSONArray();
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
                    if (editPharmacy) {

                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/edit.png'/>");
                        editPharmacy = false;
                    } else
                        data.put("");

                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());
                    data.put(pharmacyStore.get(size).getDrugs().getName());
                    data.put(pharmacyStore.get(size).getQuantityIn());

                    data.put(pharmacyStore.get(size).getLocation().getName());
                    data.put(pharmacyStore.get(size).getDestination().getName());

                    data.put(pharmacyStore.get(size).getTransaction().getName());

                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getExpireDate());
                    data.put(pharmacyStore.get(size).getDeliveryNo());

                    data.put("");
                    if (pharmacyStore.get(size).getApproved()) {

                        data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                    } else
                        data.put("Approve");

                    if (deletePharmacy) {
                        data.put("Delete");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    data.put(pharmacyStore.get(size).getStatus());
                    data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                    data.put(pharmacyStore.get(size).getRequested().getUsername());
                    data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");

                    return data;
                }
            } else {
                return null;
            }

        } else {

            if ((pharmacyStore.get(size).getDestination().getName().equalsIgnoreCase(location))
                    && (!pharmacyStore.get(size).getApproved())) {

                data = new JSONArray();
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
                if (editPharmacy) {

                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");

                data.put("");
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getDrugs().getName());
                data.put(pharmacyStore.get(size).getQuantityIn());

                data.put(pharmacyStore.get(size).getLocation().getName());
                data.put(pharmacyStore.get(size).getDestination().getName());

                data.put(pharmacyStore.get(size).getTransaction().getName());

                if (pharmacyStore.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());

                data.put(pharmacyStore.get(size).getBatchNo());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getExpireDate());
                data.put(pharmacyStore.get(size).getDeliveryNo());

                data.put("");
                if (pharmacyStore.get(size).getApproved()) {

                    data.put("<dfn>Approved By:" + pharmacyStore.get(size).getCreator().getNames() + "<dfn/>");
                } else
                    data.put("Approve");

                if (deletePharmacy) {
                    data.put("Delete");
                    deletePharmacy = false;
                } else
                    data.put("");

                data.put(pharmacyStore.get(size).getStatus());
                data.put("<input type=\"checkbox\" name=\"check\" id=\"one\" >");
                data.put(pharmacyStore.get(size).getRequested().getUsername());
                data.put("<input id=\"" + pharmacyStore.get(size).getUuid() + "\" style=\"width: 40px; height: 20px;\"  type=\"text\" name=\"val\"  >");

                return data;
            }
        }
        return null;
    }

    public synchronized JSONArray getArrayDialog(List<PharmacyStoreOutgoing> pharmacyStore, int size) {

        datad = new JSONArray();

        datad.put(pharmacyStore.get(size).getDrugs().getName());
        datad.put(pharmacyStore.get(size).getQuantityIn());
        datad.put("");
        datad.put("");

        return datad;
    }

    public synchronized String getDropDown(List<PharmacyStoreOutgoing> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public synchronized boolean getCheck(List<PharmacyStoreOutgoing> pharmacyStore, int size, String name) {

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<PharmacyStoreOutgoing> pharmacyStore, int size, String name) {
        service = Context.getService(PharmacyService.class);

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return pharmacyStore.get(size).getUuid();

        } else
            return null;

    }

    public synchronized String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;
    }

}
