package org.openmrs.module.pharmacy.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.pharmacy.model.PharmacyEncounter;
import org.openmrs.module.pharmacy.model.PharmacyLocationUsers;
import org.openmrs.module.pharmacy.service.PharmacyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@Authorized("Manage Pharmacy")
public class PsychiatryProcessorController {

    private static final Log log = LogFactory.getLog(PsychiatryProcessorController.class);
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
    private List<PharmacyLocationUsers> pharmacyLocationUsersByUserName;
    private int size;
    private JSONParser parser;
    private PharmacyEncounter pEncounter;

    @Authorized("Manage Pharmacy")
    @RequestMapping(method = RequestMethod.GET, value = "module/pharmacy/hivProcessor")
    public synchronized void pageLoad(ModelMap map) {

    }

    @RequestMapping(method = RequestMethod.POST, value = "module/pharmacy/hivProcessor")
    public synchronized void pageLoadd(HttpServletRequest request, HttpServletResponse response) {
        conceptService = Context.getConceptService();



        String jsonText = request.getParameter("values");
        EncounterProcessor encounterProcessor = new EncounterProcessor();
        ObsProcessor obsProcessor = new ObsProcessor();
        MedicationProcessor medicationProcessor = new MedicationProcessor();




        List<ObsProcessor> listObsProcessor = new ArrayList<ObsProcessor>();
        List<MedicationProcessor> listMedicationProcessors=  new ArrayList<MedicationProcessor>();


                JSONParser parser= new JSONParser();
        Object obj = null;
        try {
            obj = parser.parse(jsonText);

            JSONArray array = (JSONArray) obj;
            for (int i = 0; i < array.size(); i++) {
                String value = ArrayDataOne(array.get(i).toString());
//
                System.out.println(value);
//
//
                if(value.contains("*")){
                if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("Enc1"))   {

                    encounterProcessor.setEncounterType(value.substring(value.indexOf("||")+1,value.indexOf("|")));
//                    System.out.println("Enc1"+value.substring(value.indexOf("*")+1,value.indexOf("|")));
                }
                else if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("Enc2"))   {
//                    System.out.println("Enc2"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                    encounterProcessor.setEncounterDate(value.substring(value.indexOf("@")+1,(value.length()-1)));

                }
                else if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("Enc3"))   {
//                    System.out.println("Enc3 Name"+value.substring(value.indexOf("*")+1,(value.indexOf("|"))));
//                    System.out.println("Enc3 Version"+value.substring(value.indexOf("|")+1,(value.indexOf("#"))));
                    encounterProcessor.setForm(value.substring(value.indexOf("*")+1,(value.indexOf("|"))));
                    encounterProcessor.setFormVersion(value.substring(value.indexOf("|")+1,(value.indexOf("#"))));



                }
                else if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("Obs"))   {
                    System.out.println("Enc3 Concept"+value.substring(value.indexOf("|")+1,(value.indexOf("#"))));
                    System.out.println("Enc3 answ"+value.substring(value.indexOf("@")+1,(value.length()-1)).substring(value.substring(value.indexOf("@")+1,(value.length()-1)).indexOf("|")));

                    obsProcessor = new ObsProcessor();

                    obsProcessor.setConcept(value.substring(value.indexOf("|")+1,(value.indexOf("#"))));
                    obsProcessor.setConceptAnswer(value.substring(value.indexOf("@") + 1, (value.length() - 1)).substring(value.substring(value.indexOf("@") + 1, (value.length() - 1)).indexOf("|")));

                    listObsProcessor.add(obsProcessor);

                }
                else if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("ObsDrug"))   {

                    System.out.println("obs drug  q concept"+value.substring(value.indexOf("*")+1,(value.indexOf("#"))));
                    System.out.println("obs drug  a concept"+value.substring(value.indexOf("@")+1,(value.indexOf("|"))));
                    System.out.println("obs drug  id"+value.substring(value.indexOf("|")+1,(value.length()-1)));

                    medicationProcessor = new MedicationProcessor();

                    medicationProcessor.setConcept(value.substring(value.indexOf("*")+1,(value.indexOf("#"))));
                    medicationProcessor.setConceptAnswer(value.substring(value.indexOf("@")+1,(value.indexOf("|"))));
                    medicationProcessor.setDrugId(value.substring(value.indexOf("|")+1,((value.length()-1))));



                    listMedicationProcessors.add(medicationProcessor);


                }
                else if(value.substring(0,value.indexOf("*")).equalsIgnoreCase("ObsDrug1"))   {

                    System.out.println("dose"+value.substring(value.indexOf("*")+1,(value.indexOf("#"))));
                    System.out.println("dose text"+value.substring(value.indexOf("@")+1,(value.indexOf("|"))));
                    System.out.println("freq"+value.substring(value.indexOf("|")+1,(value.length()-1)));

                    medicationProcessor = new MedicationProcessor();

                    medicationProcessor.setDose(value.substring(value.indexOf("*")+1,(value.indexOf("#"))));
                    medicationProcessor.setUnits(value.substring(value.indexOf("@")+1,(value.indexOf("|"))));
                    medicationProcessor.setFrequency(value.substring(value.indexOf("|")+1,(value.length()-1)));

                    listMedicationProcessors.add(medicationProcessor);

                }



                }


                else
                {
                    if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("Quantity"))
                        {

                            System.out.println(""+value);
                            System.out.println("Quantity="+value.substring(value.indexOf("@")+1,(value.length()-1)));
                            medicationProcessor = new MedicationProcessor();

                           medicationProcessor.setquantity(value.substring(value.indexOf("@")+1,(value.length()-1)));
                            listMedicationProcessors.add(medicationProcessor);

                        }
                    else   if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("Other"))
                        {
                            System.out.println("Other"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                        }
                    else if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("PillCount"))
                        {
                            System.out.println("PillCount"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                            medicationProcessor = new MedicationProcessor();

                            medicationProcessor.setPillcount(value.substring(value.indexOf("@")+1,(value.length()-1)));
                            listMedicationProcessors.add(medicationProcessor);

                        }
                    else if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("Dispensed"))
                        {
                            System.out.println("Dispensed"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                            medicationProcessor = new MedicationProcessor();

                            medicationProcessor.setDispensed(value.substring(value.indexOf("@")+1,(value.length()-1)));
                            listMedicationProcessors.add(medicationProcessor);
                        }

                    else if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("Prescriber"))
                        {
                            System.out.println("Prescriber"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                            medicationProcessor = new MedicationProcessor();

                            medicationProcessor.setPrescriber(value.substring(value.indexOf("@")+1,(value.length()-1)));
                            listMedicationProcessors.add(medicationProcessor);
                        }
                    else if(value.substring(0,value.indexOf("@")).equalsIgnoreCase("Dispensed"))
                        {
                            System.out.println("Dispensed"+value.substring(value.indexOf("@")+1,(value.length()-1)));
                            medicationProcessor = new MedicationProcessor();

                            medicationProcessor.setDispensed(value.substring(value.indexOf("@")+1,(value.length()-1)));
                            listMedicationProcessors.add(medicationProcessor);
                        }



                }

            }  ///
             for (int y=0;y<listObsProcessor.size();y++){
                          System.out.println("Data"+listObsProcessor.get(y).getConcept());
                          System.out.println("Data"+listObsProcessor.get(y).getConceptAnswer());


             }


            //medicationProcessor

            for (int y=0;y<listMedicationProcessors.size();y++){
                System.out.println("Data"+listMedicationProcessors.size());



                if(y==0){



                }else   if(y==1){



                }
                else  if(y==2){



                }
                else  if(y==3){



                }
                else  if(y==4){



                }


            }




            } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        JSONArray array = (JSONArray) obj;
    }

    public synchronized String ArrayDataOne(String jsonText) {

        String value = "";
        JSONParser parser = new JSONParser();

        try {
            Map json = (Map) parser.parse(jsonText, containerFactory);
            Iterator iter = json.entrySet().iterator();

            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();

            value+=entry.getValue()+"@";
            }
        } catch (Exception pe) {
            log.info(pe);
        }
        return value;

    }

}
