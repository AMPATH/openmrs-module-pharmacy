package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.*;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class hivController {

    private static final Log log = LogFactory.getLog(hivController.class);
    private ContainerFactory containerFactory;
    private String[][] encdata;
    private ConceptService conceptService;
    private PharmacyService service;
    private String patientID = null;
    private String prescriber = null;
    private String pharmacyUser = null;
    private String question;
    private String question_ans;
    private boolean morethanOne = false;
    private String questionTwo;
    private String question_ansTwo;
    private String questionThree;
    private String question_ansThree;
    private String date;
    private String nextVisitDate;
    private String noOfMonths;
    private ArrayList<String> drugDispensed;
    private ArrayList<ArrayList<String>> drugAll;
    private boolean obsSave;
    private boolean drugsSave;
    private boolean save;
    private String userName;

    private String dispensedBy;
    private PharmacyEncounter pEncounter;

    PharmacyOrders ppharmacyOrders = null;
    PharmacyObs ppharmacyObs = null;
    List<PharmacyOrders> pharmacyOrders;
    List<PharmacyObs> pharmacyObs;
    List<PharmacyExtra> pharmacyExt;
    private int two;
    private int three;
    private int four;
    private int one;
    private int currentRegimen;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/hiv")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/hiv")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();


        String locationVal = null;

        service = Context.getService(PharmacyService.class);
        List<PharmacyLocationUsers> listUsers = service.getPharmacyLocationUsersByUserName(Context.getAuthenticatedUser().getUsername());
        int sizeUsers = listUsers.size();


        if (sizeUsers > 1) {
            locationVal = request.getSession().getAttribute("location").toString();

        } else if (sizeUsers == 1) {
            locationVal = listUsers.get(0).getLocation();


        }

        service = Context.getService(PharmacyService.class);
        String jsonText = request.getParameter("values");

        JSONParser parser = new JSONParser();

        String[][] obsExtra = ArrayExtra(parser, jsonText);

        String[][] obsEnc = ArrayEnc(parser, jsonText);

//		 


        pEncounter = new PharmacyEncounter();


        for (int i = 2; i < obsEnc.length; i++) {
            if (obsEnc[i][1].equals("2")) {
                pEncounter.setPerson(Context.getPatientService().getPatient(Integer.parseInt(obsEnc[i][2])));

                patientID = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("3")) {

                pEncounter.setEncounter(Context.getEncounterService().getEncounterType(obsEnc[i][2]));
            } else if (obsEnc[i][1].equals("4")) {
                pEncounter.setDateTime(DateHelper(obsEnc[i][2]));


            } else if (obsEnc[i][1].equals("5")) {

                nextVisitDate = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("6")) {

                noOfMonths = obsEnc[i][2];

            } else if (obsEnc[i][1].equals("0.1")) {
                pEncounter.setFormName("Pediatric ARV Prescription");
            }


            pEncounter.setLocation(service.getPharmacyLocationsByName(locationVal));


        }


        String[][] obsObs = ArrayObs(parser, jsonText, locationVal);


    }


    public synchronized String[][] ArrayEnc(JSONParser parser, String jsonText) {

        String[][] encdata = null;


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;


            encdata = new String[8][3];

            for (int i = 2; i <= 7; i++) {


                String value = ArrayDataOne(array.get(i).toString());
                String name = value.substring(0, value.indexOf("#"));


                if (i <= 7) {


                    encdata[i][0] = value.substring(value.indexOf("#"), value.indexOf("#|")).substring(1);
                    encdata[i][1] = value.substring(0, value.indexOf("#"));

                    encdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                }


            }


        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return encdata;
    }


    public synchronized String[][] ArrayObs(JSONParser parser, String jsonText, String locationVal) {
        String[][] obsdata = null;

        pharmacyOrders = new ArrayList<PharmacyOrders>();

        pharmacyObs = new ArrayList<PharmacyObs>();


        drugAll = new ArrayList<ArrayList<String>>();


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;

            obsdata = new String[array.size()][3];

            for (int i = 0; i < array.size(); i++) {

                String value = ArrayDataOne(array.get(i).toString());


                String name = value.substring(0, value.indexOf("#"));
                if (name.equals("prescriber")) {

                    obsdata[i][1] = "909003";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);
                    userName = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("memberDet#2")) {

                    obsdata[i][1] = "909004";
                    obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);

                } else if (name.equals("dispensedby")) {

                    dispensedBy = value.substring(value.indexOf("#|")).substring(2);

                } else {


                    if (i > 7) {

                        log.info("1==" + value);

                        if (!value.startsWith("reg")) {
                            obsdata[i][0] = value.substring(value.indexOf("#"), value.indexOf("#|")).substring(1);

                            //concept id
                            obsdata[i][1] = value.substring(0, value.indexOf("#"));
                            //answer id
                            obsdata[i][2] = value.substring(value.indexOf("#|")).substring(2);
                        } else {

                            System.out.println("<<<<<<<<<<<<<<<<<<<<<<111111<<<<<<<<<<<<<<<<<<<<<<<" + value.substring(value.indexOf("#|")).substring(2));

                            currentRegimen = Integer.parseInt(value.substring(value.indexOf("#|")).substring(2));
                        }

                    }


                }


            }


            for (int i = 0; i < obsdata.length; i++) {
                if (obsdata[i][1] != null) {
                    if (obsdata[i][1].equals("5089")) {


                        obsSave = true;
                    }


                    if (obsdata[i][1].equals("1895")) {

                        if (obsdata[i][2] != null | !obsdata[i][2].isEmpty()) {
                            ppharmacyOrders = new PharmacyOrders();
                            drugDispensed = new ArrayList<String>();
                            obsSave = false;
                            drugsSave = true;
                        }

                    }


                }


                if (obsSave) {


                    if (obsdata[i][2] != null) {
                        if (!obsdata[i][2].isEmpty()) {

                            if (obsdata[i][1].equals("5089")) {
                                ppharmacyObs = new PharmacyObs();

                                ppharmacyObs.setConcept(obsdata[i][1]);
                                ppharmacyObs.setValueCoded(Integer.parseInt(obsdata[i][2]));


                                if (!userName.equals("null")) {


                                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(userName).getUuid());
                                }

                                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                                if (patientID != null)
                                    ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                                ppharmacyObs.setPharmacyEncounter(pEncounter);
                                ppharmacyObs.setValueDatetime(null);


                                ppharmacyObs.setValueNumeric(null);
                                ppharmacyObs.setValueText(null);

                                ppharmacyObs.setValueCodedName(null);
                                ppharmacyObs.setValueGroupId(null);
                                ppharmacyObs.setValueModifier(null);
                                ppharmacyObs.setValueText(null);
                                ppharmacyObs.setValueBoolen(false);
                                ppharmacyObs.setComment(null);

                                ppharmacyObs.setDateStarted(null);
                                ppharmacyObs.setDateStopped(null);


                                pharmacyObs.add(ppharmacyObs);


                            } else if (obsdata[i][1].equals("1724")) {
                                ppharmacyObs = new PharmacyObs();
                                ppharmacyObs.setConcept(obsdata[i][1]);
                                ppharmacyObs.setValueCoded(Integer.parseInt(obsdata[i][2]));


                                if (!userName.equals("null")) {


                                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(userName).getUuid());
                                }

                                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                                if (patientID != null)
                                    ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                                ppharmacyObs.setPharmacyEncounter(pEncounter);
                                ppharmacyObs.setValueDatetime(null);


                                ppharmacyObs.setValueNumeric(null);
                                ppharmacyObs.setValueText(null);

                                ppharmacyObs.setValueCodedName(null);
                                ppharmacyObs.setValueGroupId(null);
                                ppharmacyObs.setValueModifier(null);
                                ppharmacyObs.setValueText(null);
                                ppharmacyObs.setValueBoolen(false);
                                ppharmacyObs.setComment(null);

                                ppharmacyObs.setDateStarted(null);
                                ppharmacyObs.setDateStopped(null);


                                pharmacyObs.add(ppharmacyObs);


                            } else if (obsdata[i][1].equals("1705")) {
                                ppharmacyObs = new PharmacyObs();

                                ppharmacyObs.setConcept(obsdata[i][1]);
                                ppharmacyObs.setValueCoded(Integer.parseInt(obsdata[i][2]));


                                if (!userName.equals("null")) {


                                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(userName).getUuid());
                                }

                                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                                if (patientID != null)
                                    ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                                ppharmacyObs.setPharmacyEncounter(pEncounter);

                                ppharmacyObs.setValueDatetime(null);


                                ppharmacyObs.setValueNumeric(null);
                                ppharmacyObs.setValueText(null);

                                ppharmacyObs.setValueCodedName(null);
                                ppharmacyObs.setValueGroupId(null);
                                ppharmacyObs.setValueModifier(null);
                                ppharmacyObs.setValueText(null);
                                ppharmacyObs.setValueBoolen(false);
                                ppharmacyObs.setComment(null);

                                ppharmacyObs.setDateStarted(null);
                                ppharmacyObs.setDateStopped(null);


                                pharmacyObs.add(ppharmacyObs);


                            } else if (obsdata[i][1].equals("1252")) {
                                ppharmacyObs = new PharmacyObs();

                                ppharmacyObs.setConcept(obsdata[i][1]);
                                ppharmacyObs.setValueCoded(Integer.parseInt(obsdata[i][2]));


                                if (!userName.equals("null")) {


                                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(userName).getUuid());
                                }

                                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                                if (patientID != null)
                                    ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                                ppharmacyObs.setPharmacyEncounter(pEncounter);

                                ppharmacyObs.setValueDatetime(null);


                                ppharmacyObs.setValueNumeric(null);
                                ppharmacyObs.setValueText(null);

                                ppharmacyObs.setValueCodedName(null);
                                ppharmacyObs.setValueGroupId(null);
                                ppharmacyObs.setValueModifier(null);
                                ppharmacyObs.setValueText(null);
                                ppharmacyObs.setValueBoolen(false);
                                ppharmacyObs.setComment(null);

                                ppharmacyObs.setDateStarted(null);
                                ppharmacyObs.setDateStopped(null);


                                pharmacyObs.add(ppharmacyObs);


                            }

                        }


                    }


                }


                if (drugsSave) {


                    if (!obsdata[i][2].isEmpty()) {

                        if (obsdata[i][1].equals("1895")) {


                            if (!obsdata[i][2].isEmpty()) {
                                ppharmacyOrders.setConcept(obsdata[i][2].substring(0, obsdata[i][2].indexOf("#")));
                                ppharmacyOrders.setMedication(obsdata[i][1]);

                            }


                            int age = Context.getPatientService().getPatient(Integer.parseInt(patientID)).getAge();

                            System.out.println("obsdata[i][2]obsdata[i][2]obsdata[i][2]===" + age);
                            System.out.println("obsdata[i][2]obsdata[i][2]obsdata[i][2]===" + obsdata[i][2].substring(obsdata[i][2].indexOf("#") + 1));

                            if (age >= 16) {

                                if (!obsdata[i][2].isEmpty()) {
                                    ppharmacyOrders.setDrugId(Context.getConceptService().getDrugByNameOrId(obsdata[i][2].substring(obsdata[i][2].indexOf("#") + 1)));
                                    //drugDispensed.add()
                                    drugDispensed.add("" + obsdata[i][2].substring(obsdata[i][2].indexOf("#") + 1));
                                } else
                                    ppharmacyOrders.setDrugId(null);


                            }


                        } else if (obsdata[i][1].equals("90901")) {

//										  if( !obsdata[i][2].isEmpty()){
//											  ppharmacyOrders.setWeightRange(obsdata[i][2]);
//											  
//											  }

                        } else if (obsdata[i][1].equals("1939")) {


                            if (!obsdata[i][2].isEmpty()) {

                                ppharmacyObs = new PharmacyObs();

                                ppharmacyObs.setConcept(obsdata[i][1]);
                                ppharmacyObs.setValueCoded(Integer.parseInt(obsdata[i][2].substring(obsdata[i][2].indexOf(",") + 1)));


                                if (!userName.equals("null")) {


                                    ppharmacyObs.setPrescriberId(Context.getUserService().getUserByUsername(userName).getUuid());
                                }

                                ppharmacyObs.setLocation(service.getPharmacyLocationsByName(locationVal));

                                if (patientID != null)
                                    ppharmacyObs.setPerson(Context.getPatientService().getPatient(Integer.parseInt(patientID)));


                                ppharmacyObs.setPharmacyEncounter(pEncounter);

                                ppharmacyObs.setValueDatetime(null);


                                ppharmacyObs.setValueNumeric(null);
                                ppharmacyObs.setValueText(null);

                                ppharmacyObs.setValueCodedName(null);
                                ppharmacyObs.setValueGroupId(null);
                                ppharmacyObs.setValueModifier(null);
                                ppharmacyObs.setValueText(null);
                                ppharmacyObs.setValueBoolen(false);
                                ppharmacyObs.setComment(null);

                                ppharmacyObs.setDateStarted(null);
                                ppharmacyObs.setDateStopped(null);


                                pharmacyObs.add(ppharmacyObs);


                            }


                            if (!obsdata[i][2].isEmpty()) {
                                ppharmacyOrders.setDrugId(Context.getConceptService().getDrugByNameOrId(obsdata[i][2].substring(obsdata[i][2].indexOf(",") + 1)));
                                //drugDispensed.add()
                                drugDispensed.add("" + obsdata[i][2].substring(obsdata[i][2].indexOf(",") + 1));
                            } else
                                ppharmacyOrders.setDrugId(null);


                        } else if (obsdata[i][1].equals("1900")) {

                            if (!obsdata[i][2].isEmpty()) {
                                ppharmacyOrders.setDoseId(obsdata[i][1]);

                            }


                        } else if (obsdata[i][1].equals("90902")) {
                            if (!obsdata[i][2].isEmpty()) {
                                one = Integer.parseInt(obsdata[i][2]);

                            }
                        } else if (obsdata[i][1].equals("90903")) {
                            if (!obsdata[i][2].isEmpty()) {
                                two = Integer.parseInt(obsdata[i][2]);
                            }


                        } else if (obsdata[i][1].equals("90904")) {

                            if (!obsdata[i][2].isEmpty()) {
                                three = Integer.parseInt(obsdata[i][2]);

                            }


                        } else if (obsdata[i][1].equals("90905")) {

                            if (!obsdata[i][2].isEmpty()) {
                                four = Integer.parseInt(obsdata[i][2]);
                            }

                        } else if (obsdata[i][1].equals("90906")) {

                            if (!obsdata[i][2].isEmpty()) {
                                ppharmacyOrders.setPillCount(Integer.parseInt(obsdata[i][2]));

                            }

                        } else if (obsdata[i][1].equals("90907")) {

                            if (!obsdata[i][2].isEmpty()) {
                                ppharmacyOrders.setQuantity(Integer.parseInt(obsdata[i][2]));


                            }


                            drugsSave = false;
                            int totalVal = 0;
                            if (one != 0) {
                                totalVal += one;

                            }

                            if (two != 0) {
                                totalVal += one;

                            }

                            if (three != 0) {
                                totalVal += one;

                            }
                            if (four != 0) {
                                totalVal += one;

                            }
                            ppharmacyOrders.setQuantityRequested(totalVal);
                            drugDispensed.add("" + totalVal);

                            if (nextVisitDate != null)
                                ppharmacyOrders.setNextVisitDate(DateHelper(nextVisitDate));
                            else
                                ppharmacyOrders.setNextVisitDate(null);


                            if (!noOfMonths.isEmpty()) {


                                ppharmacyOrders.setMonthsNo(Integer.parseInt(noOfMonths));

                            } else
                                ppharmacyOrders.setMonthsNo(0);


                            ppharmacyOrders.setPharmacyEncounter(pEncounter);
                            ppharmacyOrders.setInstructions(null);
                            ppharmacyOrders.setStartDate(null);
                            ppharmacyOrders.setAutoEndDate(null);
                            ppharmacyOrders.setDiscontinuedDate(null);
                            ppharmacyOrders.setDiscontinued(false);
                            ppharmacyOrders.setDiscontinuedReason(null);
                            ppharmacyOrders.setPlan(null);

                            if (!dispensedBy.isEmpty()) {
                                ppharmacyOrders.setDispensedBy(dispensedBy);
                            }
                            ppharmacyOrders.setRegimenId(currentRegimen);


                            ppharmacyOrders.setDispensed(true);
                            ppharmacyOrders.setPharmacyExtra(null);

                            drugAll.add(drugDispensed);

                            pharmacyOrders.add(ppharmacyOrders);


                        }
                    }
                    ;


                }


            }


            service.savePharmacyEncounter(pEncounter);


            service.savePharmacyObs(pharmacyObs);
            service.savePharmacyOrders(pharmacyOrders);
            //remove from store if there

            for (int i = 0; i < drugAll.size(); i++) {

                PharmacyStore pharmacyStore = new PharmacyStore();


                // get from drug dispense settings
                pharmacyStore = service.getPharmacyInventoryByDrugUuid(Context.getConceptService().getDrug(drugAll.get(i).get(0)), service.getPharmacyLocationsByName(locationVal).getUuid());
                if (pharmacyStore != null && pharmacyStore.getQuantity() > Integer.parseInt(drugAll.get(i).get(1))) {
                    pharmacyStore.setQuantity((pharmacyStore.getQuantity() - Integer.parseInt(drugAll.get(i).get(1))));
                    service.savePharmacyInventory(pharmacyStore);


                }
            }


        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return obsdata;
    }

    public synchronized String[][] ArrayExtra(JSONParser parser, String jsonText) {
        String[][] obsExtra = null;


        try {


            Object obj = parser.parse(jsonText);
            JSONArray array = (JSONArray) obj;

            obsExtra = new String[2][2];

            for (int i = 0; i < array.size(); i++) {

                String value = ArrayDataOne(array.get(i).toString());
                String name = value.substring(0, value.indexOf("#"));
                //System.out.println("value="+ value);

                if (name.equals("prescriber")) {

                    obsExtra[0][0] = "909003";
                    obsExtra[0][1] = value.substring(value.indexOf("#|")).substring(2);

                    userName = value.substring(value.indexOf("#|")).substring(2);
                } else if (name.equals("dispensedby")) {

                    obsExtra[1][0] = "909004";
                    obsExtra[1][1] = value.substring(value.indexOf("#|")).substring(2);
                    dispensedBy = value.substring(value.indexOf("#|")).substring(2);

                }


            }

        } catch (Exception e) {
            log.error("Error generated", e);
        }

        return obsExtra;
    }


    public synchronized String ArrayDataOne(String jsonText) {
        String value = "";
        JSONParser parser = new JSONParser();


        containerFactory = new ContainerFactory() {
            public List creatArrayContainer() {
                return new LinkedList();
            }

            public Map createObjectContainer() {
                return new LinkedHashMap();
            }

        };


        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

                if (entry.getValue().toString().contains("|"))
                    value += "#" + entry.getValue().toString().substring(entry.getValue().toString().indexOf("|"));
                else
                    value += "#|" + entry.getValue().toString();
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value.substring(2);

    }

    public synchronized Date DateHelper(String outgoingexpire) {


        Date date2 = null;
        try {
            if (outgoingexpire != null) {
                date2 = new SimpleDateFormat("MM/dd/yyyy").parse(outgoingexpire.substring(0, 9));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error("Error generated", e);
        }
        return date2;
    }

}
