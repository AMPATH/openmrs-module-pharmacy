package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Drug;
import org.openmrs.Location;
import org.openmrs.Role;
import org.openmrs.api.AdministrationService;
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
import java.util.*;

@Controller
public class DrugBincardController {

    private static final Log log = LogFactory.getLog(DrugBincardController.class);

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

    private PharmacyStoreIncoming pharmacyStoreIncoming = null;

    private PharmacyStoreOutgoing pharmacyStoreOutgoing = null;

    private Location locationClass = null;

    private Date dateVal = null;

    private int inVal = 0;

    private int outVal = 0;

    private int totalVal = 0;

    private String filter = null;

    private String uuidfilter = null;

    private boolean exit = false;

    private String bindrug;

    private String binquantityin;

    private String binmax;

    private String binmin;

    private String incomingnumber;

    private String binnumber;

    private String binbatch;

    private String bins11;

    private String deliveryno;

    private String uuiddialog;

    private Calendar currentDate;

    private Calendar readDate;

    private Date dateC;

    private GregorianCalendar one;

    private GregorianCalendar two;

    private String dialogShow;

    private JSONArray datad2;

    private ConceptService serviceDrugs;

    private String bintotal = null;

    private int total;

    private String druguuid;

    private String druguuidshow = null;

    private String drugname;

    private JSONArray jsonsAll;

    private Date newDate;

    private Date givenDate;

    private int monthsDiff;

    private String category;

    private UserContext userService;

    private AdministrationService userAdmin;

    private boolean editPharmacy = false;

    private boolean deletePharmacy = false;

    private JSONObject json;

    private Integer minLevel;

    private Integer maxLevel;

    private boolean monthsAlert = false;

    private boolean minAlert = false;

    private String row_id;

    private String value,s11number;

    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/drugBincard")
    public synchronized void pageLoad(HttpServletRequest request, HttpServletResponse response) {
        serviceLocation = Context.getLocationService();
        String locationVal=null;
        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers= service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers =listUsers.size();





        if(sizeUsers>1){
            locationVal=request.getSession().getAttribute("location").toString();

        }
        else if(sizeUsers==1)
        {
            locationVal=listUsers.get(0).getLocation();


        }
        userService = Context.getUserContext();
        drop = request.getParameter("drop");
        dialog = request.getParameter("dialog");
        dialogShow = request.getParameter("dialog");
        druguuidshow = request.getParameter("druguuidshow");
        s11number = request.getParameter("s11number");
        bintotal = request.getParameter("bintotal");
        uuiddialog = request.getParameter("uuid");
        filter = request.getParameter("sSearch");


        category = request.getParameter("category");

        if (filter == null)
            filter="a";

        currentDate = Calendar.getInstance();
        readDate = Calendar.getInstance();

        Date dateC = new Date();

        currentDate.setTime(dateC);

        one = new GregorianCalendar();
        two = new GregorianCalendar();

        one.set(currentDate.get(currentDate.YEAR), currentDate.get(currentDate.MONTH),
                currentDate.get(currentDate.DAY_OF_MONTH));

        service = Context.getService(PharmacyService.class);
        serviceDrugs = Context.getConceptService();
        List<PharmacyStore> List = service.getPharmacyInventory();

        List<PharmacyStore> ListTotal = service.getPharmacyInventoryByCategory(service.getPharmacyCategoryByName(category));

        List<Drug> drugList = Context.getConceptService().getAllDrugs();
        int size = List.size();
        int drugSize = drugList.size();
        json = new JSONObject();
        JSONArray jsons = new JSONArray();
        response.setContentType("application/json");

         if(filter.startsWith("s11"))
               s11number= filter.substring(4);

        if (uuiddialog == null && druguuidshow == null && filter != null) {

            if (filter.length() > 2 && !filter.startsWith("s11")) {

                originalbindrug = filter;

                serviceLocation = Context.getLocationService();
                //
                //				bindrug = filter.substring(0, filter.indexOf("("));
                //
                //				drugstrength = originalbindrug.substring(originalbindrug.indexOf("(") + 1, originalbindrug.indexOf(","));
                //				drugunit = originalbindrug.substring(originalbindrug.indexOf(",") + 1, originalbindrug.indexOf("/"));
                //				formulation = originalbindrug.substring(originalbindrug.indexOf("/") + 1, originalbindrug.indexOf(")"));
                //
                //
                //			String uuidvalue = service.getDrugNameByName(bindrug).getUuid();
                //			String drugstrengthuuid = service.getDrugStrengthByName(drugstrength).getUuid();
                //			String drugsunituuid = service.getDrugUnitsByName(drugunit).getUuid();
                //			String drugsformulationuuid = service.getDrugFormulationByName(formulation).getUuid();

                List<Drug> dname = serviceDrugs.getAllDrugs();
                int dnames = dname.size();
                for (int i = 0; i < dnames; i++) {
                    uuidfilter = getString(dname, i, originalbindrug);
                    if (uuidfilter != null)
                        break;

                }

            }
        }
        try {

            if (dialog != null) {

                for (int i = 0; i < size; i++) {
                    if (service.getPharmacyLocationsByUuid(List.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal)) {

                        datad2 = new JSONArray();
                        datad2 = getArrayDialog(List, i,locationVal);
                        if (datad2 != null)
                            json.accumulate("aaData", datad2);
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
            else if (druguuidshow != null) {
                jsonsAll = new JSONArray();
                for (int i = 0; i < size; i++) {


                    if ((service.getPharmacyLocationsByUuid(List.get(i).getLocation()).getName()
                            .equalsIgnoreCase(locationVal) && (List.get(i).getCategory().getName().equalsIgnoreCase(category)))) {

                        if (druguuidshow.equals(List.get(i).getDrugs().getUuid())) {

                            jsons = new JSONArray();

                            jsons.put(List.get(i).getDrugs().getName());
                            jsons.put(List.get(i).getQuantity());
                            jsons.put(10);
                            jsons.put(List.get(i).getMaxLevel());
                            jsons.put(List.get(i).getMinLevel());

                            jsons.put(List.get(i).getExpireDate().toString().substring(0, 10));

                            jsons.put(List.get(i).getBatchNo());
                            jsons.put(List.get(i).getS11());
                            jsons.put(List.get(i).getDeliveryNo());

                            if (List.get(i).getSupplier() == null)
                                jsons.put(List.get(i).getSupplier());
                            else
                                jsons.put(List.get(i).getSupplier().getName());

                            jsonsAll.put(jsons);

                        }

                    }

                }

                response.getWriter().print(jsonsAll);
            }

            else if (bintotal != null) {
                //load totals for each drug




//                for (int t = 0; t < drugSize; t++) {

                    total = 0;
                    druguuid = null;
                    drugname = null;
                HashMap test = new HashMap();
                HashMap add = new HashMap();


                size=ListTotal.size();
                     for (int i = 0; i < size; i++) {
                         //System.out.println("--------------------------------------------------------------");


                        druguuid = null;
                        drugname = null;

                            if ((service.getPharmacyLocationsByUuid(ListTotal.get(i).getLocation()).getName()
                                    .equalsIgnoreCase(locationVal))) {

                                    //name
                                //name                 drugname

                                    total += ListTotal.get(i).getQuantity();

                                    druguuid = ListTotal.get(i).getDrugs().getUuid();
                                    drugname = ListTotal.get(i).getDrugs().getName();
                                    newDate = new Date();
                                    givenDate = ListTotal.get(i).getExpireDate();

                                    monthsDiff = ((givenDate.getYear() - newDate.getYear()) * 12)
                                            + (givenDate.getMonth() - newDate.getMonth());
                                    if (monthsDiff <= 6) {
                                        monthsAlert = true;

                                    }
                                    if (ListTotal.get(i).getQuantity() <=  ListTotal.get(i).getMinLevel()) {
                                        minAlert = true;

                                    }

                                for (int t = 0; t < size; t++) {
                                    if(ListTotal.get(i).getDrugs().getName().equalsIgnoreCase(ListTotal.get(t).getDrugs().getName())){


                                            test.put(druguuid,total) ;




                                    }

                                }
                            }


//

                    }
//                }         for (int t = 0; t < size; t++) {


                if (druguuid != null && drugname != null) {

                }
                for (int ch = 0; ch < size; ch++) {



                   if(!add.containsKey(ListTotal.get(ch).getDrugs().getUuid()))   {



                    if(test.containsKey(ListTotal.get(ch).getDrugs().getUuid()))  {

                        add.put(ListTotal.get(ch).getDrugs().getUuid(),ListTotal.get(ch).getDrugs().getUuid());





                        data = new JSONArray();
                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/details_open.png'/>");
                        data.put(ListTotal.get(ch).getDrugs().getUuid());
                        data.put(ListTotal.get(ch).getDrugs().getName());

                        data.put(test.get(ListTotal.get(ch).getDrugs().getUuid()));


                        newDate = new Date();
                        givenDate = ListTotal.get(ch).getExpireDate();

                        monthsDiff = ((givenDate.getYear() - newDate.getYear()) * 12)
                                + (givenDate.getMonth() - newDate.getMonth());


                        if (minAlert)
                            data.put("8");
                        else
                            data.put("7");

                        if (monthsAlert)
                            data.put(monthsDiff);
                        else
                            data.put("7");

                        json.accumulate("aaData", data);




                        data = new JSONArray();
                        data.put("<img src='/openmrs/moduleResources/pharmacy/images/details_open.png'/>");
                        data.put(ListTotal.get(ch).getDrugs().getUuid());
                        data.put(ListTotal.get(ch).getDrugs().getName());

                        data.put(test.get(ListTotal.get(ch).getDrugs().getUuid()));

                        newDate = new Date();
                        givenDate = ListTotal.get(ch).getExpireDate();

                        monthsDiff = ((givenDate.getYear() - newDate.getYear()) * 12)
                                + (givenDate.getMonth() - newDate.getMonth());
                        if (monthsDiff <= 6) {

                            data.put(monthsDiff);
                        }
                        else{
                            data.put("7");


                        }
                        if (ListTotal.get(ch).getQuantity() <=  ListTotal.get(ch).getMinLevel()) {
                            data.put("8");

                        }
                        {
                            data.put("7");

                        }

                        json.accumulate("aaData", data);


                        break;
                    }




                    }
                }

                if (!json.has("aaData")) {

                    data = new JSONArray();
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");

                    data.put("No entry");
                    data.put("No entry");
                    data.put("No entry");



                    json.accumulate("aaData", data);
                }



                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            } else {



                for (int i = 0; i < size; i++) {

                    if (List.get(i).getCategory() != null) {
                        if (category.equals("-111")) {

                        }

                        else if ((service.getPharmacyLocationsByUuid(List.get(i).getLocation()).getName()
                                .equalsIgnoreCase(locationVal))
                                && (List.get(i).getCategory().getName().equalsIgnoreCase(category))) {

                            JSONArray val =getArray(List, i,locationVal);
                            if (val!= null)
                                json.accumulate("aaData", val);

                        }
                        if (exit)
                            break;
                    }

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

                    json.accumulate("aaData", data);
                }

                json.accumulate("iTotalRecords", json.getJSONArray("aaData").length());
                json.accumulate("iTotalDisplayRecords", json.getJSONArray("aaData").length());
                json.accumulate("iDisplayStart", 0);
                json.accumulate("iDisplayLength", 10);

                response.getWriter().print(json);
            }
            exit = false;



            response.flushBuffer();
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/drugBincard")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        serviceDrugs = Context.getConceptService();

        userService = Context.getUserContext();
        service = Context.getService(PharmacyService.class);
        bindrug = request.getParameter("bindrug");
        binquantityin = request.getParameter("binquantityin");
        String binquantityOut = request.getParameter("binquantityout");
        binmax = request.getParameter("binmax");
        binmin = request.getParameter("binmin");

        binnumber = request.getParameter("binnumber");

        binbatch = request.getParameter("binbatch");
        bins11 = request.getParameter("bins11");
        deliveryno = request.getParameter("delivery");
        row_id = request.getParameter("row_id");

        value = request.getParameter("value");



        String binreason = request.getParameter("binreason");
        String binuuidvoid = request.getParameter("binuuidvoid");

        String outgoingexpire = request.getParameter("binexpire");

        String location = request.getParameter("location");
        String less = request.getParameter("less");
        String date = request.getParameter("date");
        String incoming = request.getParameter("incoming");
        String outgoing = request.getParameter("outgoing");
        String total = request.getParameter("total");
        String dosesname = request.getParameter("dosesname");
        String binedit = request.getParameter("binedit");
        String binuuid = request.getParameter("binuuid");
        String bincom = request.getParameter("bincom");


        if (binuuidvoid == null) {
            originalbindrug = bindrug;


            List<Drug> dname = serviceDrugs.getAllDrugs();
            int dnames = dname.size();
            for (int i = 0; i < dnames; i++) {
                uuid = getString(dname, i, originalbindrug);
                if (uuid != null)
                    break;

            }

        }
        serviceLocation = Context.getLocationService();
        if (total != null) {

            totalVal = Integer.parseInt(total);
        }

        if (binquantityin != null) {

            inVal = Integer.parseInt(binquantityin);
        }

        if (binquantityOut != null) {

            outVal = Integer.parseInt(binquantityOut);
        }
        if (incoming != null) {
            pharmacyStoreIncoming = service.getPharmacyStoreIncomingByUuid(incoming);

        }

        if (outgoing != null) {
            pharmacyStoreOutgoing = service.getPharmacyStoreOutgoingByUuid(outgoing);

        }

        if (date != null) {


            if (!date.isEmpty()) {

                try {
                    dateVal = new SimpleDateFormat("dd-MM-yyyy").parse(date);
                }
                catch (ParseException e) {
                    // TODO Auto-generated catch blockreturn datad;
                    log.error("Error generated", e);
                }
            }

        }


        if (binedit != null) {
            if (binedit.equalsIgnoreCase("false")) {

                ///check for same entry before saving
                List<PharmacyStore> list = service.getPharmacyInventory();
                int size = list.size();
                for (int i = 0; i < size; i++) {

                    found = getCheck(list, i, bindrug);
                    if (found)
                        break;
                }

                if (!found) {

                    PharmacyStore pharmacyStore = new PharmacyStore();

                    //get drug details 		drugstrength drugunit formulation

                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    DrugTransactions drugTransactions = new DrugTransactions();

                    //transactions

                    drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                    drugTransactions.setQuantityOut(0);
                    drugTransactions.setQuantityIn(Integer.parseInt(binquantityin));
                    drugTransactions.setexpireDate(dateVal);
                    drugTransactions.setComment("New entry");

                    if (location != null) {

                        drugTransactions.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                    } else {

                    }

                    drugTransactions.setexpireDate(dateVal);

                    //drugTransactions.setLocation(location)
                    service.saveDrugTransactions(drugTransactions);

                    pharmacyStore.setQuantity(inVal);
                    pharmacyStore.setQuantityIn(inVal);
                    pharmacyStore.setQuantityOut(outVal);
                    pharmacyStore.setChangeReason(bincom);

                    pharmacyStore.setLocation(service.getPharmacyLocationsByName(location).getUuid());

                    pharmacyStore.setExpireDate(new Date());

                    pharmacyStore.setIncoming(pharmacyStoreIncoming);
                    pharmacyStore.setOutgoing(pharmacyStoreOutgoing);

                    if (!binmax.isEmpty()) {

                        pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                    } else if (binmax.isEmpty()) {
                        pharmacyStore.setMaxLevel(0);
                    }

                    if (!binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                    } else if (binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(0);
                    }

                    service.savePharmacyInventory(pharmacyStore);

                } else //do code to display to the user
                {

                    List<PharmacyStore> listcheck = service.getPharmacyInventory();
                    int number = listcheck.size();

                    for (int i = 0; i < number; i++) {

                        String uuiddrug = getDrug(listcheck, i, bindrug);

                        if (uuiddrug != null) {

                            PharmacyStore pharmacyStore = new PharmacyStore();
                            pharmacyStore = service.getPharmacyInventoryByUuid(uuiddrug);

                            int tot = 0;
                            if (less != null) {

                                tot = pharmacyStore.getQuantity() - inVal;
                            } else
                                tot = pharmacyStore.getQuantity() + inVal;

                            pharmacyStore.setQuantity(tot);

                            pharmacyStore.setQuantityIn(inVal);

                            pharmacyStore.setQuantityOut(outVal);

                            pharmacyStore.setChangeReason(bincom);

                            pharmacyStore.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                            pharmacyStore.setExpireDate(dateVal);

                            if (pharmacyStoreIncoming != null) {
                                pharmacyStore.setIncoming(pharmacyStoreIncoming);
                            }
                            if (pharmacyStoreOutgoing != null) {
                                pharmacyStore.setOutgoing(pharmacyStoreOutgoing);
                            }

                            //pharmacyStore.setQuantityOut(null);
                            pharmacyStore.setChangeReason(bincom);
                            //pharmacyStore.setLocation(null);
                            //pharmacyStore.setexpireDate(null);
                            //pharmacyStore.setIncoming(null);
                            //pharmacyStore.setOutgoing(null);
                            if (!binmax.isEmpty()) {

                                pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                            } else if (binmax.isEmpty()) {
                                pharmacyStore.setMaxLevel(0);
                            }

                            if (!binmin.isEmpty()) {
                                pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                            } else if (binmin.isEmpty()) {
                                pharmacyStore.setMinLevel(0);
                            }
                            //							//transactions
                            DrugTransactions drugTransactions = new DrugTransactions();

                            drugTransactions.setDrugs(serviceDrugs.getDrugByUuid(uuid));
                            drugTransactions.setQuantityOut(0);
                            drugTransactions.setQuantityIn(inVal);
                            drugTransactions.setexpireDate(dateVal);
                            drugTransactions.setComment("Addition to an existing entry");
                            if (location != null) {

                                drugTransactions.setLocation(service.getPharmacyLocationsByName(location).getUuid());
                            }

                            service.saveDrugTransactions(drugTransactions);

                            service.savePharmacyInventory(pharmacyStore);

                        }

                    }
                }

            } else if (binedit.equalsIgnoreCase("true")) {
                //

                PharmacyStore pharmacyStore = new PharmacyStore();
                pharmacyStore = service.getPharmacyInventoryByUuid(binuuid);
                if (userService.getAuthenticatedUser().getUserId().equals(pharmacyStore.getCreator().getUserId())) {
                    pharmacyStore.setDrugs(serviceDrugs.getDrugByUuid(uuid));

                    pharmacyStore.setQuantity(inVal);

                    Date date2 = null;
                    try {
                        if (outgoingexpire != null) {
                            date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire.substring(0, 10));
                        }
                    }
                    catch (ParseException e) {
                        // TODO Auto-generated catch block
                        log.error("Error generated", e);
                    }

                    pharmacyStore.setQuantityOut(null);

                    if (!binmax.isEmpty()) {

                        pharmacyStore.setMaxLevel(Integer.parseInt(binmax));

                    } else if (binmax.isEmpty()) {
                        pharmacyStore.setMaxLevel(0);
                    }

                    if (!binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(Integer.parseInt(binmin));

                    } else if (binmin.isEmpty()) {
                        pharmacyStore.setMinLevel(0);
                    }

                    pharmacyStore.setExpireDate(date2);

                    pharmacyStore.setBatchNo(Integer.parseInt(binbatch));
                    pharmacyStore.setDeliveryNo(Integer.parseInt(deliveryno));
                    pharmacyStore.setS11(Integer.parseInt(bins11));

                    pharmacyStore.setChangeReason(bincom);
                    service.savePharmacyInventory(pharmacyStore);
                    //

                }
            }

        }
        else if (value != null && row_id !=null) {


            PharmacyStore pharmacyStore=	service.getPharmacyInventoryByUuid(row_id);

            if(value.contains(",")){
                String	val =value.substring(0, value.indexOf(","));
                String 	reason =value.substring(value.indexOf(",")+1);


                pharmacyStore.setQuantity(Integer.parseInt(val));
                pharmacyStore.setStockReason(reason);
                service.savePharmacyInventory(pharmacyStore);

            }
//			
        }

        else if (binuuidvoid != null) {
            PharmacyStore pharmacyStore = new PharmacyStore();
            pharmacyStore = service.getPharmacyInventoryByUuid(binuuidvoid);

            pharmacyStore.setVoided(true);
            pharmacyStore.setVoidReason(binreason);

            service.savePharmacyInventory(pharmacyStore);

        }

    }

    public  synchronized JSONArray getArray(List<PharmacyStore> pharmacyStore, int size,String location) {

        if(s11number!=null){

              if(!s11number.equalsIgnoreCase("all")) {
            if ((Integer.parseInt(s11number)==pharmacyStore.get(size).getS11())) {
                    if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                            .equalsIgnoreCase(location)) {

                        data = new JSONArray();
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

                            data.put("edit");
                            editPharmacy = false;
                        } else
                            data.put("");

                        data.put("");
                        data.put(pharmacyStore.get(size).getUuid());

                        data.put(pharmacyStore.get(size).getDrugs().getName());

                        data.put(pharmacyStore.get(size).getQuantity());

                        data.put(pharmacyStore.get(size).getMinLevel());

                        data.put(pharmacyStore.get(size).getMaxLevel());
                        data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                        data.put(10);

                        data.put(pharmacyStore.get(size).getBatchNo());
                        data.put(pharmacyStore.get(size).getS11());
                        data.put(pharmacyStore.get(size).getDeliveryNo());

                        if (pharmacyStore.get(size).getSupplier() == null) {

                            data.put("pending");
                        } else
                            data.put(pharmacyStore.get(size).getSupplier().getName());

                        if (deletePharmacy) {
                            data.put("void");
                            deletePharmacy = false;
                        } else
                            data.put("");
                        return data;
                    }
        }
              }
            else{

                  if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                          .equalsIgnoreCase(location)) {

                      data = new JSONArray();
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

                          data.put("edit");
                          editPharmacy = false;
                      } else
                          data.put("");

                      data.put("");
                      data.put(pharmacyStore.get(size).getUuid());

                      data.put(pharmacyStore.get(size).getDrugs().getName());

                      data.put(pharmacyStore.get(size).getQuantity());

                      data.put(pharmacyStore.get(size).getMinLevel());

                      data.put(pharmacyStore.get(size).getMaxLevel());
                      data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                      data.put(10);

                      data.put(pharmacyStore.get(size).getBatchNo());
                      data.put(pharmacyStore.get(size).getS11());
                      data.put(pharmacyStore.get(size).getDeliveryNo());

                      if (pharmacyStore.get(size).getSupplier() == null) {

                          data.put("pending");
                      } else
                          data.put(pharmacyStore.get(size).getSupplier().getName());

                      if (deletePharmacy) {
                          data.put("void");
                          deletePharmacy = false;
                      } else
                          data.put("");
                      return data;
                  }


              }
                }


       else if ( filter.length() > 2) {
            if (uuidfilter.equalsIgnoreCase(pharmacyStore.get(size).getDrugs().getUuid())) {
                  if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                        .equalsIgnoreCase(location)) {

                      data = new JSONArray();
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

                        data.put("edit");
                        editPharmacy = false;
                    } else
                        data.put("");

                    data.put("");
                    data.put(pharmacyStore.get(size).getUuid());

                    data.put(pharmacyStore.get(size).getDrugs().getName());

                    data.put(pharmacyStore.get(size).getQuantity());

                    data.put(pharmacyStore.get(size).getMinLevel());

                    data.put(pharmacyStore.get(size).getMaxLevel());
                    data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                    data.put(10);

                    data.put(pharmacyStore.get(size).getBatchNo());
                    data.put(pharmacyStore.get(size).getS11());
                    data.put(pharmacyStore.get(size).getDeliveryNo());

                    if (pharmacyStore.get(size).getSupplier() == null) {

                        data.put("pending");
                    } else
                        data.put(pharmacyStore.get(size).getSupplier().getName());

                    if (deletePharmacy) {
                        data.put("void");
                        deletePharmacy = false;
                    } else
                        data.put("");
                    return data;
                }
            } else {
                //exit=true;

                return null;

            }

        } else {


            if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                    .equalsIgnoreCase(location)) {
                                                 //)&&(pharmacyStore.get(size).getQuantity()>=pharmacyStore.get(size).getMinLevel())
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


                data = new JSONArray();

                if (editPharmacy) {

                    data.put("edit");
                    editPharmacy = false;
                } else
                    data.put("");
                data.put("");
                data.put(pharmacyStore.get(size).getUuid());
                data.put(pharmacyStore.get(size).getDrugs().getName());

                data.put(pharmacyStore.get(size).getQuantity());
                data.put(pharmacyStore.get(size).getMaxLevel());
                data.put(pharmacyStore.get(size).getMinLevel());


                data.put(pharmacyStore.get(size).getExpireDate().toString().substring(0, 10));

                data.put(10);

                data.put(pharmacyStore.get(size).getBatchNo());
                data.put(pharmacyStore.get(size).getS11());
                data.put(pharmacyStore.get(size).getDeliveryNo());
                if (pharmacyStore.get(size).getSupplier() == null) {

                    data.put("pending");
                } else
                    data.put(pharmacyStore.get(size).getSupplier().getName());

                if (deletePharmacy) {
                    data.put("void");
                    deletePharmacy = false;
                } else
                    data.put("");
                return data;
            }

        }
        return null;
    }

    public  synchronized JSONArray getArrayDialog(List<PharmacyStore> pharmacyStore, int size,String location) {
        if (service.getPharmacyLocationsByUuid(pharmacyStore.get(size).getLocation()).getName()
                .equalsIgnoreCase(location) ) {
            if (uuiddialog != null) {
                if (uuiddialog.equals("123")) {

                    return null;
                }

                else if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid()
                        .equals(pharmacyStore.get(size).getDrugs().getUuid())) {
//					readDate.setTime(pharmacyStore.get(size).getExpireDate());
//					
//					two.set(readDate.get(readDate.YEAR), readDate.get(readDate.MONTH), readDate.get(readDate.DAY_OF_MONTH));
//					
//					int num = daysBetween(two.getTime(), one.getTime());
                    newDate = new Date();
                    int num =getMonthsDifference(pharmacyStore.get(size).getExpireDate(),newDate);
                    datad2 = new JSONArray();

                    datad2.put(pharmacyStore.get(size).getUuid());
                    datad2.put(pharmacyStore.get(size).getDrugs().getName());
                    datad2.put(pharmacyStore.get(size).getQuantity());

                    datad2.put(pharmacyStore.get(size).getBatchNo());

                    datad2.put(num);
                    datad2.put("<input type='checkbox' id='check1' />");

                    return datad2;

                } else {

                    return null;
                }
            } else {

                if (service.getPharmacyStoreOutgoingByUuid(uuiddialog).getDrugs().getUuid()
                        .equals(pharmacyStore.get(size).getDrugs().getUuid())) {
                    datad = new JSONArray();

                    datad.put(pharmacyStore.get(size).getDrugs().getName());

                    datad.put(pharmacyStore.get(size).getQuantity());

                    return datad2;

                }
                return null;
            }

        } else
            return null;

    }

    public synchronized int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static final int getMonthsDifference(Date date1, Date date2) {



        int m1 = date1.getYear() * 12 + date1.getMonth();
        int m2 = date2.getYear() * 12 + date2.getMonth();



        return m1 - m2 + 1;
    }

    public synchronized String getDropDown(List<PharmacyStore> pharmacyStore, int size) {

        return pharmacyStore.get(size).getUuid();
    }

    public  synchronized boolean getCheck(List<PharmacyStore> pharmacyStore, int size, String name) {

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return true;

        } else
            return false;

    }

    public synchronized String getDrug(List<PharmacyStore> pharmacyStore, int size, String name) {
        service = Context.getService(PharmacyService.class);

        if (pharmacyStore.get(size).getDrugs().getName().equals(name)) {

            return pharmacyStore.get(size).getUuid();

        } else
            return null;

    }

    public synchronized  String getString(List<Drug> dname, int size, String text) {

        if ((dname.get(size).getName().equalsIgnoreCase(text))) {

            return dname.get(size).getUuid();
        }
        return null;
    }

    public  synchronized int months_betweens(Date date1, Date date2) {
        return date2.getMonth() - date1.getMonth() + (12 * (date2.getYear() - date1.getYear()));
    }
}
