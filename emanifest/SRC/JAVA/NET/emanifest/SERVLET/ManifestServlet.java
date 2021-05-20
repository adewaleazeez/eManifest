/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emanifest.servlet;

import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import net.emanifest.hibernate.Bols;
import net.emanifest.hibernate.Boltypes;
import net.emanifest.hibernate.Carriers;
import net.emanifest.hibernate.Containers;
import net.emanifest.hibernate.Customs;
import net.emanifest.hibernate.HibernateHelper;
import net.emanifest.hibernate.Nations;
import net.emanifest.hibernate.Packages;
import net.emanifest.hibernate.Ports;
import net.emanifest.hibernate.Transports;
import net.emanifest.hibernate.TwmBol;
import net.emanifest.hibernate.TwmManifest;
import net.emanifest.hibernate.Xmlfolders;

/**
 *
 * @author Adewale Azeez
 */
public class ManifestServlet extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static String operation;
    private static String table;
    private static String serialno;
    private static String Registry_Number;
    private static String Departure_Date;
    private static String Customs_Office_Code;
    private static String Master_Information;
    private static String Last_Discharge_Date;
    private static String Date_of_Arrival;
    private static String Time_of_Arrival;
    private static String Place_of_Departure_Code;
    private static String Place_of_Destination_Code;
    private static String Carrier_Code;
    private static String Carrier_Name;
    private static String Carrier_Address;
    private static String Name_of_Transporter;
    private static String Place_of_Transporter;
    private static String Mode_of_Transport_Code;
    private static String Nationality_of_Transporter_Code;
    private static String Registration_Number;
    private static String Registration_Date;
    private static String Gross_Tonnage;
    private static String Net_Tonnage;
    private static String entryDate;
    private static String Bol_Reference;
    private static String Line_Number;
    private static String Previous_Document_Reference;
    private static String Bol_Nature;
    private static String Unique_Carrier_Reference;
    private static String Total_Number_of_Containers;
    private static String Total_Gross_Mass_Manifested;
    private static String Volume_in_Cubic_Meters;
    private static String BOL_Type_Code;
    private static String Exporter_Code;
    private static String Exporter_Name;
    private static String Exporter_Address;
    private static String Consignee_Code;
    private static String Consignee_Name;
    private static String Consignee_Address;
    private static String Notify_Code;
    private static String Notify_Name;
    private static String Notify_Address;
    private static String Package_Type_Code;
    private static String Number_of_Packages;
    private static String Shipping_Marks;
    private static String Goods_Description;
    private static String Number_of_Seals;
    private static String Information;
    private static String Sealing_Party_Code;
    private static String General_Information;
    private static String Location_Code;
    private static String Location_Information;
    private static String Container_Identification;
    private static String Container_Number;
    private static String Container_Type_Code;
    private static String Empty_Full_Indicator;
    private static String Marks_of_Seals_1;
    private static String Marks_of_Seals_2;
    private static String Marks_of_Seals_3;
    private static String Container_Sealing_Party_Code;
    private static String userName;
    private static String container_content;

    public String createXmlTree(Document doc) throws Exception {
        HibernateHelper helper = new HibernateHelper();
        String resp = "generateXMLfailed";
        String BOL_XML_folder = "", MAN_XML_folder = "", REG_XML_folder = "", Batch_folder = "", backupFolder = "";
        List xmlfolders = helper.getXmlFilenames();
        if (xmlfolders != null) {
            Iterator k = xmlfolders.iterator();
            while (k.hasNext()) {
                Xmlfolders obj = (Xmlfolders) k.next();
                BOL_XML_folder = obj.getBol().trim();
                MAN_XML_folder = obj.getManifest().trim();
                REG_XML_folder = obj.getRegistration().trim();
                Batch_folder = obj.getBatchfile().trim();
            }
        }

        int total_number_of_bols = 0, total_number_of_containers = 0, total_number_of_packages = 0, total_gross_mass = 0;
        String unique_container_reference = "";
        List elements = helper.getAllBols(Registry_Number, entryDate, Departure_Date);

        if (elements != null && elements.size() > 0) {
            Iterator i = elements.iterator();
            while (i.hasNext()) {
                TwmBol element = (TwmBol) i.next();
                //This method creates an element node
                Element TWM_BOL = doc.createElement("TWM_BOL");
                //adding a node after the last child node of the specified node.
                doc.appendChild(TWM_BOL);

                Element Identification_segment = doc.createElement("Identification_segment");
                TWM_BOL.appendChild(Identification_segment);

                Element Registry_number = doc.createElement("Registry_number");
                Identification_segment.appendChild(Registry_number);

                Text text = null;
                String registryNumber = ((element.getRegistryNumber() == null) ? "" : element.getRegistryNumber().trim());
                if (registryNumber.length() > 0) {
                    text = doc.createTextNode(registryNumber);
                    Registry_number.appendChild(text);
                }

                Element Date = doc.createElement("Date");
                Identification_segment.appendChild(Date);

                String departureDate = ((element.getDepartureDate() == null) ? "" : element.getDepartureDate().toString());
                if (departureDate.length() > 0) {
                    text = doc.createTextNode(departureDate);
                    Date.appendChild(text);
                }

                Element Bol_reference = doc.createElement("Bol_reference");
                Identification_segment.appendChild(Bol_reference);

                String bolReference = ((element.getBolReference() == null) ? "" : element.getBolReference().trim());
                if (bolReference.length() > 0) {
                    text = doc.createTextNode(bolReference);
                    Bol_reference.appendChild(text);
                }

                Element Customs_office_segment = doc.createElement("Customs_office_segment");
                Identification_segment.appendChild(Customs_office_segment);

                Element Code = doc.createElement("Code");
                Customs_office_segment.appendChild(Code);

                String customsOfficeCode = ((element.getCustomsOfficeCode() == null) ? "" : element.getCustomsOfficeCode().trim());
                if (customsOfficeCode.length() > 0) {
                    text = doc.createTextNode(customsOfficeCode);
                    Code.appendChild(text);
                }

                Element Bol_specific_segment = doc.createElement("Bol_specific_segment");
                TWM_BOL.appendChild(Bol_specific_segment);

                Element Line_number = doc.createElement("Line_number");
                Bol_specific_segment.appendChild(Line_number);

                String lineNumber = ((element.getLineNumber() == null) ? "" : element.getLineNumber().toString());
                if (!lineNumber.equals("") && !lineNumber.equals("0")) {


                    text = doc.createTextNode(lineNumber);
                    Line_number.appendChild(text);
                }

                Element Previous_document_reference = doc.createElement("Previous_document_reference");
                Bol_specific_segment.appendChild(Previous_document_reference);

                String previousDocumentReference = ((element.getPreviousDocumentReference() == null) ? "" : element.getPreviousDocumentReference().trim());
                if (previousDocumentReference.length() > 0) {
                    text = doc.createTextNode(previousDocumentReference);
                    Previous_document_reference.appendChild(text);
                }

                Element Bol_Nature = doc.createElement("Bol_Nature");
                Bol_specific_segment.appendChild(Bol_Nature);

                String bolNature = ((element.getBolNature() == null) ? "" : element.getBolNature().trim());
                if (bolNature.length() > 0) {
                    text = doc.createTextNode(bolNature);
                    Bol_Nature.appendChild(text);
                }
                Element Unique_carrier_reference = doc.createElement("Unique_carrier_reference");
                Bol_specific_segment.appendChild(Unique_carrier_reference);

                String uniqueCarrierReference = ((element.getUniqueCarrierReference() == null) ? "" : element.getUniqueCarrierReference().trim());
                if (uniqueCarrierReference.length() > 0) {
                    text = doc.createTextNode(uniqueCarrierReference);
                    Unique_carrier_reference.appendChild(text);
                }

                Element Total_number_of_containers = doc.createElement("Total_number_of_containers");
                Bol_specific_segment.appendChild(Total_number_of_containers);

                String container_str = element.getContainerContent();
                String[] containers = container_str.split("@@@");
                String totalNumberOfContainers = (((containers.length - 1) > 0) ? (containers.length - 1) + "" : "0");
                // && !totalNumberOfContainers.equals("0")
                if (!totalNumberOfContainers.equals("")) {

                    text = doc.createTextNode(totalNumberOfContainers);
                    Total_number_of_containers.appendChild(text);
                }

                Element Total_gross_mass_manifested = doc.createElement("Total_gross_mass_manifested");
                Bol_specific_segment.appendChild(Total_gross_mass_manifested);

                String totalGrossMassManifested = ((element.getTotalGrossMassManifested() == null) ? "" : element.getTotalGrossMassManifested().toString());
                if (!totalGrossMassManifested.equals("") && !totalGrossMassManifested.equals("0")) {

                    text = doc.createTextNode(totalGrossMassManifested);
                    Total_gross_mass_manifested.appendChild(text);
                }

                Element Volume_in_cubic_meters = doc.createElement("Volume_in_cubic_meters");
                Bol_specific_segment.appendChild(Volume_in_cubic_meters);

                String volumeInCubicMeters = ((element.getVolumeInCubicMeters() == null) ? "" : element.getVolumeInCubicMeters().toString());
                if (!volumeInCubicMeters.equals("") && !volumeInCubicMeters.equals("0")) {

                    text = doc.createTextNode(volumeInCubicMeters);
                    Volume_in_cubic_meters.appendChild(text);
                }

                Element Bol_type_segment = doc.createElement("Bol_type_segment");
                Bol_specific_segment.appendChild(Bol_type_segment);

                Code = doc.createElement("Code");
                Bol_type_segment.appendChild(Code);

                String bolTypeCode = ((element.getBolTypeCode() == null) ? "" : element.getBolTypeCode().trim());
                if (bolTypeCode.length() > 0) {
                    text = doc.createTextNode(bolTypeCode);
                    Code.appendChild(text);
                }

                Element Exporter_segment = doc.createElement("Exporter_segment");
                Bol_specific_segment.appendChild(Exporter_segment);

                Code = doc.createElement("Code");
                Exporter_segment.appendChild(Code);

                String exporterCode = ((element.getExporterCode() == null) ? "" : element.getExporterCode().trim());
                if (exporterCode.length() > 0) {
                    text = doc.createTextNode(exporterCode);
                    Code.appendChild(text);
                }

                Element Name = doc.createElement("Name");
                Exporter_segment.appendChild(Name);

                String exporterName = ((element.getExporterName() == null) ? "" : element.getExporterName().trim());
                if (exporterName.length() > 0) {
                    text = doc.createTextNode(exporterName);
                    Name.appendChild(text);
                }

                Element Address = doc.createElement("Address");
                Exporter_segment.appendChild(Address);

                String exporterAddress = ((element.getExporterAddress() == null) ? "" : element.getExporterAddress().trim());
                if (exporterAddress.length() > 0) {
                    text = doc.createTextNode(exporterAddress);
                    Address.appendChild(text);
                }

                Element Consignee_segment = doc.createElement("Consignee_segment");
                Bol_specific_segment.appendChild(Consignee_segment);

                Code = doc.createElement("Code");
                Consignee_segment.appendChild(Code);

                String consigneeCode = ((element.getConsigneeCode() == null) ? "" : element.getConsigneeCode().trim());
                if (consigneeCode.length() > 0) {
                    text = doc.createTextNode(consigneeCode);
                    Code.appendChild(text);
                }

                Name = doc.createElement("Name");
                Consignee_segment.appendChild(Name);

                String consigneeName = ((element.getConsigneeName() == null) ? "" : element.getConsigneeName().trim());
                if (consigneeName.length() > 0) {
                    text = doc.createTextNode(consigneeName);
                    Name.appendChild(text);
                }

                Address = doc.createElement("Address");
                Consignee_segment.appendChild(Address);

                String consigneeAddress = ((element.getConsigneeAddress() == null) ? "" : element.getConsigneeAddress().trim());
                if (consigneeAddress.length() > 0) {
                    text = doc.createTextNode(consigneeAddress);
                    Address.appendChild(text);
                }

                Element Notify_segment = doc.createElement("Notify_segment");
                Bol_specific_segment.appendChild(Notify_segment);

                Code = doc.createElement("Code");
                Notify_segment.appendChild(Code);

                String notifyCode = ((element.getNotifyCode() == null) ? "" : element.getNotifyCode().trim());
                if (notifyCode.length() > 0) {
                    text = doc.createTextNode(notifyCode);
                    Code.appendChild(text);
                }

                Name = doc.createElement("Name");
                Notify_segment.appendChild(Name);

                String notifyName = ((element.getNotifyName() == null) ? "" : element.getNotifyName().trim());
                if (notifyName.length() > 0) {
                    text = doc.createTextNode(notifyName);
                    Name.appendChild(text);
                }

                Address = doc.createElement("Address");
                Notify_segment.appendChild(Address);

                String notifyAddress = ((element.getNotifyAddress() == null) ? "" : element.getNotifyAddress().trim());
                if (notifyAddress.length() > 0) {
                    text = doc.createTextNode(notifyAddress);
                    Address.appendChild(text);
                }

                Element Place_of_loading_segment = doc.createElement("Place_of_loading_segment");
                Bol_specific_segment.appendChild(Place_of_loading_segment);

                Code = doc.createElement("Code");
                Place_of_loading_segment.appendChild(Code);

                String placeOfLoadingCode = ((element.getPlaceOfLoadingCode() == null) ? "" : element.getPlaceOfLoadingCode().trim());
                if (placeOfLoadingCode.length() > 0) {
                    text = doc.createTextNode(placeOfLoadingCode);
                    Code.appendChild(text);
                }

                Element Place_of_unloading_segment = doc.createElement("Place_of_unloading_segment");
                Bol_specific_segment.appendChild(Place_of_unloading_segment);

                Code = doc.createElement("Code");
                Place_of_unloading_segment.appendChild(Code);

                String placeOfUnloadingCode = ((element.getPlaceOfUnloadingCode() == null) ? "" : element.getPlaceOfUnloadingCode().trim());
                if (placeOfUnloadingCode.length() > 0) {
                    text = doc.createTextNode(placeOfUnloadingCode);
                    Code.appendChild(text);
                }

                Element Packages_segment = doc.createElement("Packages_segment");
                Bol_specific_segment.appendChild(Packages_segment);

                Element Package_type_code = doc.createElement("Package_type_code");
                Packages_segment.appendChild(Package_type_code);

                String packageTypeCode = ((element.getPackageTypeCode() == null) ? "" : element.getPackageTypeCode().trim());
                if (packageTypeCode.length() > 0) {
                    text = doc.createTextNode(packageTypeCode);
                    Package_type_code.appendChild(text);
                }

                Element Number_of_packages = doc.createElement("Number_of_packages");
                Packages_segment.appendChild(Number_of_packages);

                String numberOfPackages = ((element.getNumberOfPackages() == null) ? "" : element.getNumberOfPackages().toString());
                if (!numberOfPackages.equals("") && !numberOfPackages.equals("0")) {

                    text = doc.createTextNode(numberOfPackages);
                    Number_of_packages.appendChild(text);
                }

                Element Shipping_marks = doc.createElement("Shipping_marks");
                Bol_specific_segment.appendChild(Shipping_marks);

                String shippingMarks = ((element.getShippingMarks() == null) ? "" : element.getShippingMarks().trim());
                if (shippingMarks.length() > 0) {
                    text = doc.createTextNode(shippingMarks);
                    Shipping_marks.appendChild(text);
                }

                Element Goods_description = doc.createElement("Goods_description");
                Bol_specific_segment.appendChild(Goods_description);

                String goodsDescription = ((element.getGoodsDescription() == null) ? "" : element.getGoodsDescription().trim());
                if (goodsDescription.length() > 0) {
                    text = doc.createTextNode(goodsDescription);
                    Goods_description.appendChild(text);
                }

                Element Freight_segment = doc.createElement("Freight_segment");
                Bol_specific_segment.appendChild(Freight_segment);

                Element Value = doc.createElement("Value");
                Freight_segment.appendChild(Value);

                String freightValue = ((element.getFreightValue() == null) ? "" : element.getFreightValue().toString());
                if (!freightValue.equals("") && !freightValue.equals("0")) {

                    text = doc.createTextNode(freightValue);
                    Value.appendChild(text);
                }

                Element Currency = doc.createElement("Currency");
                Freight_segment.appendChild(Currency);

                String freightCurrency = ((element.getFreightCurrencyCode() == null) ? "" : element.getFreightCurrencyCode().trim());
                if (freightCurrency.length() > 0) {
                    text = doc.createTextNode(freightCurrency);
                    Currency.appendChild(text);
                }

                Element Indicator_segment = doc.createElement("Indicator_segment");
                Freight_segment.appendChild(Indicator_segment);

                Code = doc.createElement("Code");
                Indicator_segment.appendChild(Code);

                String freightIndicator = ((element.getFreightIndicatorCode() == null) ? "" : element.getFreightIndicatorCode().trim());
                if (freightIndicator.length() > 0) {
                    text = doc.createTextNode(freightIndicator);
                    Code.appendChild(text);
                }

                Element Customs_segment = doc.createElement("Customs_segment");
                Bol_specific_segment.appendChild(Customs_segment);

                Value = doc.createElement("Value");
                Customs_segment.appendChild(Value);

                String customsValue = ((element.getCustomsValue() == null) ? "" : element.getCustomsValue().toString());
                if (!customsValue.equals("") && !customsValue.equals("0")) {

                    text = doc.createTextNode(customsValue);
                    Value.appendChild(text);
                }

                Currency = doc.createElement("Currency");
                Customs_segment.appendChild(Currency);

                String customsCurrency = ((element.getCustomsCurrencyCode() == null) ? "" : element.getCustomsCurrencyCode().trim());
                if (customsCurrency.length() > 0) {
                    text = doc.createTextNode(customsCurrency);
                    Currency.appendChild(text);
                }

                Element Transport_segment = doc.createElement("Transport_segment");
                Bol_specific_segment.appendChild(Transport_segment);

                Value = doc.createElement("Value");
                Transport_segment.appendChild(Value);

                String transportValue = ((element.getTransportValue() == null) ? "" : element.getTransportValue().toString());
                if (!transportValue.equals("") && !transportValue.equals("0")) {

                    text = doc.createTextNode(transportValue);
                    Value.appendChild(text);
                }

                Currency = doc.createElement("Currency");
                Transport_segment.appendChild(Currency);

                String transportCurrency = ((element.getTransportCurrencyCode() == null) ? "" : element.getTransportCurrencyCode().trim());
                if (transportCurrency.length() > 0) {
                    text = doc.createTextNode(transportCurrency);
                    Currency.appendChild(text);
                }
                Element Insurance_segment = doc.createElement("Insurance_segment");
                Bol_specific_segment.appendChild(Insurance_segment);

                Value = doc.createElement("Value");
                Insurance_segment.appendChild(Value);

                String insuranceValue = ((element.getInsuranceValue() == null) ? "" : element.getInsuranceValue().toString());
                if (!insuranceValue.equals("") && !insuranceValue.equals("0")) {

                    text = doc.createTextNode(insuranceValue);
                    Value.appendChild(text);
                }

                Currency = doc.createElement("Currency");
                Insurance_segment.appendChild(Currency);

                String insuranceCurrency = ((element.getInsuranceCurrencyCode() == null) ? "" : element.getInsuranceCurrencyCode().trim());
                if (insuranceCurrency.length() > 0) {
                    text = doc.createTextNode(insuranceCurrency);
                    Currency.appendChild(text);
                }

                Element Seals_segment = doc.createElement("Seals_segment");
                Bol_specific_segment.appendChild(Seals_segment);

                Element Number_of_seals = doc.createElement("Number_of_seals");
                Seals_segment.appendChild(Number_of_seals);

                String numberOfSeals = ((element.getNumberOfSeals() == null) ? "" : element.getNumberOfSeals().toString());
                if (!numberOfSeals.equals("") && !numberOfSeals.equals("0")) {

                    text = doc.createTextNode(numberOfSeals);
                    Number_of_seals.appendChild(text);
                }

                Element Marks_of_seals = doc.createElement("Marks_of_seals");
                Seals_segment.appendChild(Marks_of_seals);

                String marksOfSeals = ((element.getMarksOfSeals() == null) ? "" : element.getMarksOfSeals().trim());
                if (marksOfSeals.length() > 0) {
                    text = doc.createTextNode(marksOfSeals);
                    Marks_of_seals.appendChild(text);
                }

                Element Sealing_party_code = doc.createElement("Sealing_party_code");
                Seals_segment.appendChild(Sealing_party_code);

                String sealingPartyCode = ((element.getSealingPartyCode() == null) ? "" : element.getSealingPartyCode().trim());
                if (sealingPartyCode.length() > 0) {
                    text = doc.createTextNode(sealingPartyCode);
                    Sealing_party_code.appendChild(text);
                }

                Element Information_part_a = doc.createElement("Information_part_a");
                Bol_specific_segment.appendChild(Information_part_a);

                String generalInformation = ((element.getGeneralInformation() == null) ? "" : element.getGeneralInformation().trim());
                if (generalInformation.length() > 0) {
                    text = doc.createTextNode(generalInformation);
                    Information_part_a.appendChild(text);
                }

                Element Operations_segment = doc.createElement("Operations_segment");
                Bol_specific_segment.appendChild(Operations_segment);

                Element Location_segment = doc.createElement("Location_segment");
                Operations_segment.appendChild(Location_segment);

                Code = doc.createElement("Code");
                Location_segment.appendChild(Code);

                String locationCode = ((element.getLocationCode() == null) ? "" : element.getLocationCode().trim());
                if (locationCode.length() > 0) {
                    text = doc.createTextNode(locationCode);
                    Code.appendChild(text);
                }

                Element Information = doc.createElement("Information");
                Location_segment.appendChild(Information);

                String locationInformation = ((element.getLocationInformation() == null) ? "" : element.getLocationInformation().trim());
                if (locationInformation.length() > 0) {
                    text = doc.createTextNode(locationInformation);
                    Information.appendChild(text);
                }

                for (int k = 1; k < containers.length; k++) {
                    String[] containerelements = containers[k].split("~_~", 8);
                    if (!unique_container_reference.contains(containerelements[0])) {
                        unique_container_reference += containerelements[0] + " ";
                        total_number_of_containers++;
                    }
                    Element Container = doc.createElement("Container");
                    TWM_BOL.appendChild(Container);

                    Element Reference = doc.createElement("Reference");
                    Container.appendChild(Reference);

                    String containerIdentification = ((containerelements[0] == null) ? "" : containerelements[0].trim());
                    if (containerIdentification.length() > 0) {
                        text = doc.createTextNode(containerIdentification);
                        Reference.appendChild(text);
                    }

                    Element Number = doc.createElement("Number");
                    Container.appendChild(Number);

                    String containerNumber = ((containerelements[2] == null) ? "" : containerelements[2].trim());
                    if (!containerNumber.equals("") && !containerNumber.equals("0")) {

                        text = doc.createTextNode(containerNumber);
                        Number.appendChild(text);
                    }

                    Element Type = doc.createElement("Type");
                    Container.appendChild(Type);

                    String containerTypeCode = ((containerelements[1] == null) ? "" : containerelements[1].trim());
                    if (containerTypeCode.length() > 0) {
                        text = doc.createTextNode(containerTypeCode);
                        Type.appendChild(text);
                    }

                    Element Empty_full = doc.createElement("Empty_full");
                    Container.appendChild(Empty_full);

                    String emptyFullIndicator = ((containerelements[4] == null) ? "" : containerelements[4].trim());
                    if (emptyFullIndicator.length() > 0) {
                        text = doc.createTextNode(emptyFullIndicator);
                        Empty_full.appendChild(text);
                    }
                    Element Seals = doc.createElement("Seals");
                    Container.appendChild(Seals);

                    String marksOfSeals1 = ((containerelements[5] == null) ? "" : containerelements[5].trim());
                    if (marksOfSeals1.length() > 0) {
                        text = doc.createTextNode(marksOfSeals1);
                        Seals.appendChild(text);
                    }

                    Element Marks1 = doc.createElement("Marks1");
                    Container.appendChild(Marks1);

                    String marksOfSeals2 = ((containerelements[6] == null) ? "" : containerelements[6].trim());
                    if (marksOfSeals2.length() > 0) {
                        text = doc.createTextNode(element.getMarksOfSeals2());
                        Marks1.appendChild(text);
                    }

                    Element Marks2 = doc.createElement("Marks2");
                    Container.appendChild(Marks2);

                    String marksOfSeals3 = ((containerelements[7] == null) ? "" : containerelements[7].trim());
                    if (marksOfSeals3.length() > 0) {
                        text = doc.createTextNode(element.getMarksOfSeals3());
                        Marks2.appendChild(text);
                    }

                    Element Sealing_party = doc.createElement("Sealing_party");
                    Container.appendChild(Sealing_party);

                    String containerSealingPartyCode = ((containerelements[3] == null) ? "" : containerelements[3].trim());
                    if (containerSealingPartyCode.length() > 0) {
                        text = doc.createTextNode(containerSealingPartyCode);
                        Sealing_party.appendChild(text);
                    }
                }
                total_number_of_bols++;
                //total_number_of_containers += element.getTotalNumberOfContainers();
                total_number_of_packages += element.getNumberOfPackages();
                total_gross_mass += element.getTotalGrossMassManifested();

                //TransformerFactory instance is used to create Transformer objects.
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                // create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                String xmlString = sw.toString();

                File aDirectory = new File(Batch_folder.replace("startUpload.bat", "") + "tempupload/");
                if (!aDirectory.exists()) {
                    aDirectory.mkdirs();
                }
                aDirectory = new File(Batch_folder.replace("startUpload.bat", "") + "xml_backup/");
                if (!aDirectory.exists()) {
                    aDirectory.mkdirs();
                }

                File file = new File(BOL_XML_folder + "BOL_" + element.getRegistryNumber().trim() + "_" + element.getBolReference() + ".xml");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();

                backupFolder = element.getEntryDate().toString().replaceAll("-", "_") + "_" + element.getRegistryNumber() + "/";
                aDirectory = new File(Batch_folder.replace("startUpload.bat", "") + "xml_backup/" + backupFolder);
                aDirectory.mkdirs();

                file = new File(Batch_folder.replace("startUpload.bat", "") + "xml_backup/" + backupFolder + "BOL_" + element.getRegistryNumber().trim() + "_" + element.getBolReference() + ".xml");
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();
                bw.close();
                try {
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();

                    //creating a new instance of a DOM to build a DOM tree.
                    doc = docBuilder.newDocument();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            resp = "generateXMLsuccess";
        }

        elements = helper.getAManifest(Registry_Number, entryDate, Departure_Date);
        if (elements != null && elements.size() > 0) {
            Iterator i = elements.iterator();
            while (i.hasNext()) {
                TwmManifest element = (TwmManifest) i.next();
                //This method creates an element node
                Element TWM_Manifest = doc.createElement("TWM_Manifest");
                //adding a node after the last child node of the specified node.
                doc.appendChild(TWM_Manifest);

                Element Identification_segment = doc.createElement("Identification_segment");
                TWM_Manifest.appendChild(Identification_segment);

                Element Registry_number = doc.createElement("Registry_number");
                Identification_segment.appendChild(Registry_number);

                Text text = null;
                String registryNumber = ((element.getRegistryNumber() == null) ? "" : element.getRegistryNumber().trim());
                if (registryNumber.length() > 0) {
                    text = doc.createTextNode(registryNumber);
                    Registry_number.appendChild(text);
                }

                Element Date = doc.createElement("Date");
                Identification_segment.appendChild(Date);

                String departureDate = ((element.getDepartureDate() == null) ? "" : element.getDepartureDate().toString());
                if (departureDate.length() > 0) {
                    text = doc.createTextNode(departureDate);
                    Date.appendChild(text);
                }

                Element Customs_office_segment = doc.createElement("Customs_office_segment");
                Identification_segment.appendChild(Customs_office_segment);

                Element Code = doc.createElement("Code");
                Customs_office_segment.appendChild(Code);

                String customsOfficeCode = ((element.getCustomsOfficeCode() == null) ? "" : element.getCustomsOfficeCode().trim());
                if (customsOfficeCode.length() > 0) {
                    text = doc.createTextNode(customsOfficeCode);
                    Code.appendChild(text);
                }

                Element General_segment = doc.createElement("General_segment");
                TWM_Manifest.appendChild(General_segment);

                Element Master_information = doc.createElement("Master_information");
                General_segment.appendChild(Master_information);

                String masterInformation = ((element.getMasterInformation() == null) ? "" : element.getMasterInformation().trim());
                if (masterInformation.length() > 0) {
                    text = doc.createTextNode(masterInformation);
                    Master_information.appendChild(text);
                }

                Element Last_discharge = doc.createElement("Last_discharge");
                General_segment.appendChild(Last_discharge);

                String lastDischargeDate = ((element.getLastDischargeDate() == null) ? "" : element.getLastDischargeDate().toString());
                if (lastDischargeDate.length() > 0) {
                    text = doc.createTextNode(lastDischargeDate);
                    Last_discharge.appendChild(text);
                }

                Element Arrival_segment = doc.createElement("Arrival_segment");
                General_segment.appendChild(Arrival_segment);

                Element Date_of_arrival = doc.createElement("Date_of_arrival");
                Arrival_segment.appendChild(Date_of_arrival);

                String dateOfArrival = ((element.getDateOfArrival() == null) ? "" : element.getDateOfArrival().toString());
                if (dateOfArrival.length() > 0) {
                    text = doc.createTextNode(dateOfArrival);
                    Date_of_arrival.appendChild(text);
                }

                Element Time_of_arrival = doc.createElement("Time_of_arrival");
                Arrival_segment.appendChild(Time_of_arrival);

                String timeOfArrival = ((element.getTimeOfArrival() == null) ? "" : element.getTimeOfArrival().trim());
                if (timeOfArrival.length() > 0) {
                    text = doc.createTextNode(timeOfArrival);
                    Time_of_arrival.appendChild(text);
                }

                Element Departure_segment = doc.createElement("Departure_segment");
                General_segment.appendChild(Departure_segment);

                Code = doc.createElement("Code");
                Departure_segment.appendChild(Code);

                String placeOfDepartureCode = ((element.getPlaceOfDepartureCode() == null) ? "" : element.getPlaceOfDepartureCode().trim());
                if (placeOfDepartureCode.length() > 0) {
                    text = doc.createTextNode(placeOfDepartureCode);
                    Code.appendChild(text);
                }

                Element Destination_segment = doc.createElement("Destination_segment");
                General_segment.appendChild(Destination_segment);

                Code = doc.createElement("Code");
                Destination_segment.appendChild(Code);

                String placeOfDestinationCode = ((element.getPlaceOfDestinationCode() == null) ? "" : element.getPlaceOfDestinationCode().trim());
                if (placeOfDestinationCode.length() > 0) {
                    text = doc.createTextNode(placeOfDestinationCode);
                    Code.appendChild(text);
                }

                Element Carrier_segment = doc.createElement("Carrier_segment");
                General_segment.appendChild(Carrier_segment);

                Code = doc.createElement("Code");
                Carrier_segment.appendChild(Code);

                String carrierCode = ((element.getCarrierCode() == null) ? "" : element.getCarrierCode().trim());
                if (carrierCode.length() > 0) {
                    text = doc.createTextNode(carrierCode);
                    Code.appendChild(text);
                }

                Element Name = doc.createElement("Name");
                Carrier_segment.appendChild(Name);

                String carrierName = ((element.getCarrierName() == null) ? "" : element.getCarrierName().trim());
                if (carrierName.length() > 0) {
                    text = doc.createTextNode(carrierName);
                    Name.appendChild(text);
                }

                Element Address = doc.createElement("Address");
                Carrier_segment.appendChild(Address);

                String carrierAddress = ((element.getCarrierAddress() == null) ? "" : element.getCarrierAddress().trim());
                if (carrierAddress.length() > 0) {
                    text = doc.createTextNode(carrierAddress);
                    Address.appendChild(text);
                }

                Element Transport_segment = doc.createElement("Transport_segment");
                General_segment.appendChild(Transport_segment);

                Element Name_of_transporter = doc.createElement("Name_of_transporter");
                Transport_segment.appendChild(Name_of_transporter);

                String nameOfTransporter = ((element.getNameOfTransporter() == null) ? "" : element.getNameOfTransporter().trim());
                if (nameOfTransporter.length() > 0) {
                    text = doc.createTextNode(nameOfTransporter);
                    Name_of_transporter.appendChild(text);
                }

                Element Place_of_transporter = doc.createElement("Place_of_transporter");
                Transport_segment.appendChild(Place_of_transporter);

                String placeOfTransporter = ((element.getPlaceOfTransporter() == null) ? "" : element.getPlaceOfTransporter().trim());
                if (placeOfTransporter.length() > 0) {
                    text = doc.createTextNode(placeOfTransporter);
                    Place_of_transporter.appendChild(text);
                }
                Element Mode_of_transport_segment = doc.createElement("Mode_of_transport_segment");
                Transport_segment.appendChild(Mode_of_transport_segment);

                Code = doc.createElement("Code");
                Mode_of_transport_segment.appendChild(Code);

                String modeOfTransportCode = ((element.getModeOfTransportCode() == null) ? "" : element.getModeOfTransportCode().trim());
                if (modeOfTransportCode.length() > 0) {
                    text = doc.createTextNode(modeOfTransportCode);
                    Code.appendChild(text);
                }

                Element Nationality_of_transport_segment = doc.createElement("Nationality_of_transport_segment");
                Transport_segment.appendChild(Nationality_of_transport_segment);

                Code = doc.createElement("Code");
                Nationality_of_transport_segment.appendChild(Code);

                String nationalityOfTransporterCode = ((element.getNationalityOfTransporterCode() == null) ? "" : element.getNationalityOfTransporterCode().trim());
                if (nationalityOfTransporterCode.length() > 0) {
                    text = doc.createTextNode(nationalityOfTransporterCode);
                    Code.appendChild(text);
                }

                Element Transporter_registration_segment = doc.createElement("Transporter_registration_segment");
                Transport_segment.appendChild(Transporter_registration_segment);

                Element Registration_number = doc.createElement("Registration_number");
                Transporter_registration_segment.appendChild(Registration_number);

                String registrationNumber = ((element.getRegistrationNumber() == null) ? "" : element.getRegistrationNumber().trim());
                if (registrationNumber.length() > 0) {
                    text = doc.createTextNode(registrationNumber);
                    Registration_number.appendChild(text);
                }

                Element Registration_date = doc.createElement("Registration_date");
                Transporter_registration_segment.appendChild(Registration_date);

                String registrationDate = ((element.getRegistrationDate() == null) ? "" : element.getRegistrationDate().toString());
                if (registrationDate.length() > 0) {
                    text = doc.createTextNode(registrationDate);
                    Registration_date.appendChild(text);
                }

                Element Tonnage_segment = doc.createElement("Tonnage_segment");
                General_segment.appendChild(Tonnage_segment);

                Element Gross_tonnage = doc.createElement("Gross_tonnage");
                Tonnage_segment.appendChild(Gross_tonnage);

                String grossTonnage = ((element.getGrossTonnage() == null) ? "" : element.getGrossTonnage().toString());
                if (!grossTonnage.equals("") && !grossTonnage.equals("0")) {

                    text = doc.createTextNode(grossTonnage);
                    Gross_tonnage.appendChild(text);
                }

                Element Net_tonnage = doc.createElement("Net_tonnage");
                Tonnage_segment.appendChild(Net_tonnage);

                String netTonnage = ((element.getNetTonnage() == null) ? "" : element.getNetTonnage().toString());
                if (!netTonnage.equals("") && !netTonnage.equals("0")) {

                    text = doc.createTextNode(netTonnage);
                    Net_tonnage.appendChild(text);
                }


                //TransformerFactory instance is used to create Transformer objects.
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                // create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                String xmlString = sw.toString();

                File file = new File(MAN_XML_folder + "MAN_" + element.getRegistryNumber().trim() + ".xml");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();

                file = new File(Batch_folder.replace("startUpload.bat", "") + "xml_backup/" + backupFolder + "MAN_" + element.getRegistryNumber().trim() + ".xml");
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();
                bw.close();
                try {
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();

                    //creating a new instance of a DOM to build a DOM tree.
                    doc = docBuilder.newDocument();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            resp = "generateXMLsuccess";
        }

        elements = helper.getAManifest(Registry_Number, entryDate, Departure_Date);
        if (elements != null && elements.size() > 0) {
            Iterator i = elements.iterator();
            while (i.hasNext()) {
                TwmManifest element = (TwmManifest) i.next();
                //This method creates an element node
                Element eRegistrationRequest = doc.createElement("eRegistrationRequest");
                //adding a node after the last child node of the specified node.
                doc.appendChild(eRegistrationRequest);

                Element Identification = doc.createElement("Identification");
                eRegistrationRequest.appendChild(Identification);

                Element Registry_number = doc.createElement("Registry_number");
                Identification.appendChild(Registry_number);

                Text text = null;

                String registryNumber = ((element.getRegistryNumber() == null) ? "" : element.getRegistryNumber().trim());
                if (registryNumber.length() > 0) {
                    text = doc.createTextNode(registryNumber);
                    Registry_number.appendChild(text);
                }

                Element Date = doc.createElement("Date");
                Identification.appendChild(Date);

                String departureDate = ((element.getDepartureDate() == null) ? "" : element.getDepartureDate().toString());
                if (departureDate.length() > 0) {
                    text = doc.createTextNode(departureDate);
                    Date.appendChild(text);
                }

                Element Customs_office_code = doc.createElement("Customs_office_code");
                Identification.appendChild(Customs_office_code);

                String customsOfficeCode = ((element.getCustomsOfficeCode() == null) ? "" : element.getCustomsOfficeCode().trim());
                if (customsOfficeCode.length() > 0) {
                    text = doc.createTextNode(customsOfficeCode);
                    Customs_office_code.appendChild(text);
                }

                Element Totals_segment = doc.createElement("Totals_segment");
                eRegistrationRequest.appendChild(Totals_segment);

                Element Total_number_of_bols = doc.createElement("Total_number_of_bols");
                Totals_segment.appendChild(Total_number_of_bols);

                text = doc.createTextNode(total_number_of_bols + "");
                Total_number_of_bols.appendChild(text);

                Element Total_number_of_containers = doc.createElement("Total_number_of_containers");
                Totals_segment.appendChild(Total_number_of_containers);

                text = doc.createTextNode(total_number_of_containers + "");
                Total_number_of_containers.appendChild(text);

                Element Total_number_of_packages = doc.createElement("Total_number_of_packages");
                Totals_segment.appendChild(Total_number_of_packages);

                text = doc.createTextNode(total_number_of_packages + "");
                Total_number_of_packages.appendChild(text);

                Element Total_gross_mass = doc.createElement("Total_gross_mass");
                Totals_segment.appendChild(Total_gross_mass);

                text = doc.createTextNode(total_gross_mass + "");
                Total_gross_mass.appendChild(text);

                //TransformerFactory instance is used to create Transformer objects.
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                // create string from xml tree
                StringWriter sw = new StringWriter();
                StreamResult result = new StreamResult(sw);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                String xmlString = sw.toString();

                File file = new File(REG_XML_folder + "eRegistrationRequest_" + element.getRegistryNumber().trim() + ".xml");
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();

                file = new File(Batch_folder.replace("startUpload.bat", "") + "xml_backup/" + backupFolder + "eRegistrationRequest_" + element.getRegistryNumber().trim() + ".xml");
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
                bw.write(xmlString);
                bw.flush();
                bw.close();
                try {
                    DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();

                    //creating a new instance of a DOM to build a DOM tree.
                    doc = docBuilder.newDocument();

                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            resp = "generateXMLsuccess";
        }

        return resp;

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HibernateHelper helper = new HibernateHelper();

        String resp = "";

        operation = request.getParameter("operation");
        if (operation == null) {
            operation = "";
        }
        serialno = request.getParameter("serialno");
        if (serialno == null) {
            serialno = "";
        }
        table = request.getParameter("table");
        if (table == null) {
            table = "";
        }
        Registry_Number = request.getParameter("Registry_Number");
        if (Registry_Number == null) {
            Registry_Number = "";
        }
        Departure_Date = request.getParameter("Departure_Date");
        if (Departure_Date == null) {
            Departure_Date = "";
        }
        Customs_Office_Code = request.getParameter("Customs_Office_Code");
        if (Customs_Office_Code == null) {
            Customs_Office_Code = "";
        }
        Master_Information = request.getParameter("Master_Information");
        if (Master_Information == null) {
            Master_Information = "";
        }
        Last_Discharge_Date = request.getParameter("Last_Discharge_Date");
        if (Last_Discharge_Date == null) {
            Last_Discharge_Date = "";
        }
        Date_of_Arrival = request.getParameter("Date_of_Arrival");
        if (Date_of_Arrival == null) {
            Date_of_Arrival = "";
        }
        Time_of_Arrival = request.getParameter("Time_of_Arrival");
        if (Time_of_Arrival == null) {
            Time_of_Arrival = "";
        }
        Place_of_Departure_Code = request.getParameter("Place_of_Departure_Code");
        if (Place_of_Departure_Code == null) {
            Place_of_Departure_Code = "";
        }
        Place_of_Destination_Code = request.getParameter("Place_of_Destination_Code");
        if (Place_of_Destination_Code == null) {
            Place_of_Destination_Code = "";
        }
        Carrier_Code = request.getParameter("Carrier_Code");
        if (Carrier_Code == null) {
            Carrier_Code = "";
        }
        Carrier_Name = request.getParameter("Carrier_Name");
        if (Carrier_Name == null) {
            Carrier_Name = "";
        }
        Carrier_Address = request.getParameter("Carrier_Address");
        if (Carrier_Address == null) {
            Carrier_Address = "";
        }
        Name_of_Transporter = request.getParameter("Name_of_Transporter");
        if (Name_of_Transporter == null) {
            Name_of_Transporter = "";
        }
        Place_of_Transporter = request.getParameter("Place_of_Transporter");
        if (Place_of_Transporter == null) {
            Place_of_Transporter = "";
        }
        Mode_of_Transport_Code = request.getParameter("Mode_of_Transport_Code");
        if (Mode_of_Transport_Code == null) {
            Mode_of_Transport_Code = "";
        }
        Nationality_of_Transporter_Code = request.getParameter("Nationality_of_Transporter_Code");
        if (Nationality_of_Transporter_Code == null) {
            Nationality_of_Transporter_Code = "";
        }
        Registration_Number = request.getParameter("Registration_Number");
        if (Registration_Number == null) {
            Registration_Number = "";
        }
        Registration_Date = request.getParameter("Registration_Date");
        if (Registration_Date == null) {
            Registration_Date = "";
        }
        Gross_Tonnage = request.getParameter("Gross_Tonnage");
        if (Gross_Tonnage == null) {
            Gross_Tonnage = "0";
        }
        Net_Tonnage = request.getParameter("Net_Tonnage");
        if (Net_Tonnage == null) {
            Net_Tonnage = "0";
        }
        entryDate = request.getParameter("entryDate");
        if (entryDate == null) {
            entryDate = "";
        }
        Bol_Reference = request.getParameter("Bol_Reference");
        if (Bol_Reference == null) {
            Bol_Reference = "";
        }
        Line_Number = request.getParameter("Line_Number");
        if (Line_Number == null) {
            Line_Number = "0";
        }
        Previous_Document_Reference = request.getParameter("Previous_Document_Reference");
        if (Previous_Document_Reference == null) {
            Previous_Document_Reference = "";
        }
        Bol_Nature = request.getParameter("Bol_Nature");
        if (Bol_Nature == null) {
            Bol_Nature = "";
        }
        Unique_Carrier_Reference = request.getParameter("Unique_Carrier_Reference");
        if (Unique_Carrier_Reference == null) {
            Unique_Carrier_Reference = "";
        }
        Total_Number_of_Containers = request.getParameter("Total_Number_of_Containers");
        if (Total_Number_of_Containers == null) {
            Total_Number_of_Containers = "0";
        }
        Total_Gross_Mass_Manifested = request.getParameter("Total_Gross_Mass_Manifested");
        if (Total_Gross_Mass_Manifested == null) {
            Total_Gross_Mass_Manifested = "0";
        }
        Volume_in_Cubic_Meters = request.getParameter("Volume_in_Cubic_Meters");
        if (Volume_in_Cubic_Meters == null) {
            Volume_in_Cubic_Meters = "0";
        }
        BOL_Type_Code = request.getParameter("BOL_Type_Code");
        if (BOL_Type_Code == null) {
            BOL_Type_Code = "";
        }
        Exporter_Code = request.getParameter("Exporter_Code");
        if (Exporter_Code == null) {
            Exporter_Code = "";
        }
        Exporter_Name = request.getParameter("Exporter_Name");
        if (Exporter_Name == null) {
            Exporter_Name = "";
        }
        Exporter_Address = request.getParameter("Exporter_Address");
        if (Exporter_Address == null) {
            Exporter_Address = "";
        }
        Consignee_Code = request.getParameter("Consignee_Code");
        if (Consignee_Code == null) {
            Consignee_Code = "";
        }
        Consignee_Name = request.getParameter("Consignee_Name");
        if (Consignee_Name == null) {
            Consignee_Name = "";
        }
        Consignee_Address = request.getParameter("Consignee_Address");
        if (Consignee_Address == null) {
            Consignee_Address = "";
        }
        Notify_Code = request.getParameter("Notify_Code");
        if (Notify_Code == null) {
            Notify_Code = "";
        }
        Notify_Name = request.getParameter("Notify_Name");
        if (Notify_Name == null) {
            Notify_Name = "";
        }
        Notify_Address = request.getParameter("Notify_Address");
        if (Notify_Address == null) {
            Notify_Address = "";
        }
        Package_Type_Code = request.getParameter("Package_Type_Code");
        if (Package_Type_Code == null) {
            Package_Type_Code = "";
        }
        Number_of_Packages = request.getParameter("Number_of_Packages");
        if (Number_of_Packages == null) {
            Number_of_Packages = "0";
        }
        Shipping_Marks = request.getParameter("Shipping_Marks");
        if (Shipping_Marks == null) {
            Shipping_Marks = "";
        }
        Goods_Description = request.getParameter("Goods_Description");
        if (Goods_Description == null) {
            Goods_Description = "";
        }
        Number_of_Seals = request.getParameter("Number_of_Seals");
        if (Number_of_Seals == null) {
            Number_of_Seals = "0";
        }
        Information = request.getParameter("Information");
        if (Information == null) {
            Information = "";
        }
        Sealing_Party_Code = request.getParameter("Sealing_Party_Code");
        if (Sealing_Party_Code == null) {
            Sealing_Party_Code = "";
        }
        General_Information = request.getParameter("General_Information");
        if (General_Information == null) {
            General_Information = "";
        }
        Location_Code = request.getParameter("Location_Code");
        if (Location_Code == null) {
            Location_Code = "";
        }
        Location_Information = request.getParameter("Location_Information");
        if (Location_Information == null) {
            Location_Information = "";
        }
        Container_Identification = request.getParameter("Container_Identification");
        if (Container_Identification == null) {
            Container_Identification = "";
        }
        Container_Number = request.getParameter("Container_Number");
        if (Container_Number == null) {
            Container_Number = "0";
        }
        Container_Type_Code = request.getParameter("Container_Type_Code");
        if (Container_Type_Code == null) {
            Container_Type_Code = "";
        }
        Empty_Full_Indicator = request.getParameter("Empty_Full_Indicator");
        if (Empty_Full_Indicator == null) {
            Empty_Full_Indicator = "";
        }
        Marks_of_Seals_1 = request.getParameter("Marks_of_Seals_1");
        if (Marks_of_Seals_1 == null) {
            Marks_of_Seals_1 = "";
        }
        Marks_of_Seals_2 = request.getParameter("Marks_of_Seals_2");
        if (Marks_of_Seals_2 == null) {
            Marks_of_Seals_2 = "";
        }
        Marks_of_Seals_3 = request.getParameter("Marks_of_Seals_3");
        if (Marks_of_Seals_3 == null) {
            Marks_of_Seals_3 = "";
        }
        Container_Sealing_Party_Code = request.getParameter("Container_Sealing_Party_Code");
        if (Container_Sealing_Party_Code == null) {
            Container_Sealing_Party_Code = "";
        }
        container_content = request.getParameter("container_content");
        if (container_content == null) {
            container_content = "";
        }

        userName = request.getParameter("userName");
        if (userName == null) {
            userName = "";
        }


        TwmManifest twmManifest = new TwmManifest();
        TwmBol twmBol = new TwmBol();
        if (operation.equals("add") || operation.equals("delete") || operation.equals("update")) {
            twmManifest.setTimeOfArrival(Time_of_Arrival);
            twmManifest.setRegistryNumber(Registry_Number);
            twmManifest.setRegistrationNumber(Registration_Number);
            twmManifest.setPlaceOfTransporter(Place_of_Transporter);
            twmManifest.setPlaceOfDestinationCode(Place_of_Destination_Code);
            twmManifest.setPlaceOfDepartureCode(Place_of_Departure_Code);
            twmManifest.setNetTonnage(Integer.parseInt(Net_Tonnage));
            twmManifest.setNationalityOfTransporterCode(Nationality_of_Transporter_Code);
            twmManifest.setNameOfTransporter(Name_of_Transporter);
            twmManifest.setModeOfTransportCode(Mode_of_Transport_Code);
            twmManifest.setMasterInformation(Master_Information);
            twmManifest.setGrossTonnage(Integer.parseInt(Gross_Tonnage));
            twmManifest.setEntryDate(helper.convertDate(entryDate));
            twmManifest.setDepartureDate(helper.convertDate(Departure_Date));
            twmManifest.setDateOfArrival(helper.convertDate(Date_of_Arrival));
            twmManifest.setCustomsOfficeCode(Customs_Office_Code);
            twmManifest.setCarrierName(Carrier_Name);
            twmManifest.setCarrierCode(Carrier_Code);
            twmManifest.setCarrierAddress(Carrier_Address);

            twmBol.setVolumeInCubicMeters(Integer.parseInt(Volume_in_Cubic_Meters));
            twmBol.setUniqueCarrierReference(Unique_Carrier_Reference);
            //twmBol.setTotalNumberOfContainers(Integer.parseInt(Total_Number_of_Containers));
            twmBol.setTotalGrossMassManifested(Integer.parseInt(Total_Gross_Mass_Manifested));
            twmBol.setShippingMarks(Shipping_Marks);
            twmBol.setSealingPartyCode(Sealing_Party_Code);
            twmBol.setRegistryNumber(Registry_Number);
            twmBol.setPreviousDocumentReference(Previous_Document_Reference);
            twmBol.setPlaceOfUnloadingCode(Place_of_Departure_Code);
            twmBol.setPlaceOfLoadingCode(Place_of_Destination_Code);
            twmBol.setPackageTypeCode(Package_Type_Code);
            twmBol.setNumberOfSeals(Integer.parseInt(Number_of_Seals));
            twmBol.setNumberOfPackages(Integer.parseInt(Number_of_Packages));
            twmBol.setNotifyName(Notify_Name);
            twmBol.setNotifyCode(Notify_Code);
            twmBol.setNotifyAddress(Notify_Address);
            //twmBol.setMarksOfSeals3(Information);
            //twmBol.setMarksOfSeals2(Information);
            //twmBol.setMarksOfSeals1(Information);
            twmBol.setMarksOfSeals(Marks_of_Seals_1);
            twmBol.setLocationInformation(Location_Information);
            twmBol.setLocationCode(Location_Code);
            twmBol.setLineNumber(Integer.parseInt(Line_Number));
            twmBol.setGoodsDescription(Goods_Description);
            twmBol.setGeneralInformation(General_Information);
            twmBol.setExporterName(Exporter_Name);
            twmBol.setExporterCode(Exporter_Code);
            twmBol.setExporterAddress(Exporter_Address);
            twmBol.setEntryDate(helper.convertDate(entryDate));
            //twmBol.setEmptyFullIndicator(Empty_Full_Indicator);
            twmBol.setDepartureDate(helper.convertDate(Departure_Date));
            twmBol.setCustomsOfficeCode(Customs_Office_Code);
            //twmBol.setContainerTypeCode(Container_Type_Code);
            //twmBol.setContainerSealingPartyCode(Container_Sealing_Party_Code);
            //twmBol.setContainerNumber(Integer.parseInt(Container_Number));
            //twmBol.setContainerIdentification(Container_Identification);
            twmBol.setConsigneeName(Consignee_Name);
            twmBol.setConsigneeCode(Consignee_Code);
            twmBol.setConsigneeAddress(Consignee_Address);
            twmBol.setBolTypeCode(BOL_Type_Code);
            twmBol.setBolReference(Bol_Reference);
            twmBol.setBolNature(Bol_Nature);
            twmBol.setContainerContent(container_content);
        }

        if (operation.equals("add")) {
            helper.addManifests(twmManifest);
            resp = helper.addBols(twmBol);
            if (resp.equals("boladded")) {
                String description = " added BOL: " + Registry_Number + " - " + Bol_Reference;
                helper.insertActivity(userName, description);
            }
        }



        if (operation.equals("delete")) {
            int serial = helper.manifestExists(Registry_Number, entryDate).getSerialno();
            twmManifest.setSerialno(serial);
            twmBol.setSerialno(Integer.parseInt(serialno));

            resp = helper.deleteBols(twmBol);
            if (resp.equals("boldeleted")) {
                String description = " added BOL: " + Registry_Number + " - " + Bol_Reference;
                helper.insertActivity(userName, description);
            }
            TwmBol checktwmBol = helper.bolExists(Registry_Number, entryDate);
            if (checktwmBol == null) {
                helper.deleteManifests(twmManifest);
            }
        }

        if (operation.equals("update")) {
            int serial = helper.manifestExists(Registry_Number, entryDate).getSerialno();
            twmManifest.setSerialno(serial);
            twmBol.setSerialno(Integer.parseInt(serialno));

            helper.updateManifests(twmManifest);
            helper.updateBols(twmManifest);
            resp = helper.updateBols(twmBol);
            if (resp.equals("bolupdated")) {
                String description = " added BOL: " + Registry_Number + " - " + Bol_Reference;
                helper.insertActivity(userName, description);
            }
        }

        if (operation.equals("generateXMLs")) {
            try {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                //creating a new instance of a DOM to build a DOM tree.
                Document doc = docBuilder.newDocument();

                //ManifestServlet manifest = new ManifestServlet();
                //resp = manifest.
                resp = createXmlTree(doc);

                //out.println("<b>Xml File Created Successfully</b>");
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (operation.equals("getXMLObject")) {
            resp = "getXMLObject";
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                InputStream inputStream = new FileInputStream(new File(table));
                org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(inputStream);
                StringWriter stw = new StringWriter();
                Transformer serializer = TransformerFactory.newInstance().newTransformer();
                serializer.transform(new DOMSource(doc), new StreamResult(stw));
                resp += stw.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (operation.equals("checkFeedBack")) {
            resp = "nofeedback";

            try {
                DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
                Document doc = null;
                String Batch_folder = "";
                List xmlfolders = helper.getXmlFilenames();
                if (xmlfolders != null) {
                    Iterator k = xmlfolders.iterator();
                    while (k.hasNext()) {
                        Xmlfolders obj = (Xmlfolders) k.next();
                        Batch_folder = obj.getBatchfile().trim();
                    }
                }
                Batch_folder = Batch_folder.replace("/", "\\");
                String batchFolder = Batch_folder.replace("startUpload.bat", "");

                File bsresponse = new File(batchFolder + "inout\\bsresponse");
                File err_bol = new File(batchFolder + "inout\\err\\bol");
                File err_manifest = new File(batchFolder + "inout\\err\\manifest");
                File mrresponse = new File(batchFolder + "inout\\mrresponse");
                File msresponse = new File(batchFolder + "inout\\msresponse");
                File notsent = new File(batchFolder + "inout\\notsent");
                File[] files = bsresponse.listFiles();
                String voyageno = "";
                String date = "";
                if (files.length > 0) {

                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();

                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Bill_of_Lading~_~" + files[k] + "_~_";
                        }
                    }
                }

                files = err_bol.listFiles();
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();

                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Error_in_Bill_of_Lading~_~" + files[k] + "_~_";
                        }
                    }
                }


                files = err_manifest.listFiles();
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();

                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Error_in_Manifest~_~" + files[k] + "_~_";
                        }
                    }
                }

                files = mrresponse.listFiles();
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();

                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Manifest_Registration~_~" + files[k] + "_~_";
                        }
                    }
                }

                files = msresponse.listFiles();
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();
                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Manifest~_~" + files[k] + "_~_";
                        }
                    }
                }

                files = notsent.listFiles();
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        doc = docBuilder.parse(files[k]);

                        NodeList voyagetag = doc.getElementsByTagName("Registry_number");
                        Element voyageElements = (Element) voyagetag.item(0);
                        voyageno = voyageElements.getChildNodes().item(0).getNodeValue();

                        NodeList datetag = doc.getElementsByTagName("Date");
                        Element dateElements = (Element) datetag.item(0);
                        date = dateElements.getChildNodes().item(0).getNodeValue();

                        if (date.compareTo(entryDate) >= 0 && date.compareTo(Departure_Date) <= 0 && voyageno.equals(Registry_Number)) {
                            resp += "Not_Sent~_~" + files[k] + "_~_";
                        }
                    }
                }

                if (resp.contains("nofeedback") && resp.length() > 10) {
                    resp = resp.replace("nofeedback", "checkFeedBack");
                }
            } catch (Exception e) {
            }

        }

        if (operation.equals("uploadXML")) {
            String Batch_filename = "";
            List xmlfolders = helper.getXmlFilenames();
            if (xmlfolders != null) {
                Iterator k = xmlfolders.iterator();
                while (k.hasNext()) {
                    Xmlfolders obj = (Xmlfolders) k.next();
                    Batch_filename = obj.getBatchfile().trim();
                }
            }
            Batch_filename = Batch_filename.replace("/", "\\");
            String batchFolder = Batch_filename.replace("startUpload.bat", "");

            File aDirectory = new File(batchFolder + "tempupload");
            File[] files = aDirectory.listFiles();
            if (files.length == 0) {
                resp = "uploadXMLnofile";
            } else {
                if (files.length > 0) {
                    for (int k = 0; k < files.length; k++) {
                        if (files[k].getName().contains("MAN_")) {
                            files[k].renameTo(new File(batchFolder + "inout\\in\\" + files[k].getName()));
                        }
                    }
                }

                Runtime.getRuntime().exec("cmd.exe /c start " + Batch_filename + " " + batchFolder);
                long start = System.nanoTime();
                long end = System.nanoTime();
                long difference = 0;

                aDirectory = new File(batchFolder + "inout\\in");
                files = aDirectory.listFiles();
                int secs = files.length * 300;
                System.out.println("secs   "+secs);
                while (files.length > 0) {
                    files = aDirectory.listFiles();
                    end = System.nanoTime();
                    difference = end - start;
                    if (((difference / 1000000000) >= secs) || (files.length == 0)) {
                        break;
                    }
                }

                if (files.length == 0) {
                    aDirectory = new File(batchFolder + "tempupload");
                    files = aDirectory.listFiles();
                    if (files.length > 0) {
                        for (int k = 0; k < files.length; k++) {
                            if (files[k].getName().contains("BOL_")) {
                                files[k].renameTo(new File(batchFolder + "inout\\in\\" + files[k].getName()));
                            }
                        }
                        
                        aDirectory = new File(batchFolder + "inout\\in");
                        files = aDirectory.listFiles();
                        secs = files.length * 300;
                        start = System.nanoTime();
                        while (files.length > 0) {
                            files = aDirectory.listFiles();
                            end = System.nanoTime();
                            difference = end - start;
                            System.out.println("Bol seconds  " + (difference / 1000000000) + "     files.length  " + files.length);
                            if (((difference / 1000000000) >= secs) || (files.length == 0)) {
                                break;
                            }
                        }

                        if (files.length == 0) {
                            aDirectory = new File(batchFolder + "tempupload");
                            files = aDirectory.listFiles();
                            secs = files.length * 300;
                            start = System.nanoTime();
                            if (files.length > 0) {
                                for (int k = 0; k < files.length; k++) {
                                    files[k].renameTo(new File(batchFolder + "inout\\in\\" + files[k].getName()));
                                }
                                while (files.length > 0) {
                                    files = aDirectory.listFiles();
                                    end = System.nanoTime();
                                    difference = end - start;
                                    System.out.println("Register seconds  " + (difference / 1000000000) + "     files.length  " + files.length);
                                    if (((difference / 1000000000) >= secs) || (files.length == 0)) {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                resp = "uploadXMLfailed";
                files = aDirectory.listFiles();
                if (files.length == 0) {
                    resp = "uploadXMLsuccess";
                }
            }
        }
        if (operation.equals("getMyBol")) {
            resp = "getMyBol";
            List elements = helper.getMyBol(serialno);
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                while (i.hasNext()) {
                    TwmBol element = (TwmBol) i.next();
                    resp += element.getBolNature().trim() + "_~_";
                    resp += element.getBolReference().trim() + "_~_";
                    resp += element.getBolTypeCode().trim() + "_~_";
                    resp += element.getConsigneeAddress().trim() + "_~_";
                    resp += element.getConsigneeCode().trim() + "_~_";
                    resp += element.getConsigneeName().trim() + "_~_";
                    resp += element.getExporterAddress().trim() + "_~_";
                    resp += element.getExporterCode().trim() + "_~_";
                    resp += element.getExporterName().trim() + "_~_";
                    resp += element.getGeneralInformation().trim() + "_~_";
                    resp += element.getGoodsDescription().trim() + "_~_";
                    resp += element.getLocationCode().trim() + "_~_";
                    resp += element.getLocationInformation().trim() + "_~_";
                    resp += element.getMarksOfSeals().trim() + "_~_";
                    resp += element.getNotifyAddress().trim() + "_~_";
                    resp += element.getNotifyCode().trim() + "_~_";
                    resp += element.getNotifyName().trim() + "_~_";
                    resp += element.getPackageTypeCode().trim() + "_~_";
                    resp += element.getPreviousDocumentReference().trim() + "_~_";
                    resp += element.getSealingPartyCode().trim() + "_~_";
                    resp += element.getShippingMarks().trim() + "_~_";
                    resp += element.getUniqueCarrierReference().trim() + "_~_";
                    resp += element.getLineNumber().toString() + "_~_";
                    resp += element.getNumberOfPackages() + "_~_";
                    resp += element.getNumberOfSeals() + "_~_";
                    resp += element.getTotalGrossMassManifested().toString() + "_~_";
                    resp += element.getVolumeInCubicMeters().toString() + "_~_";
                    resp += element.getRegistryNumber().trim() + "_~_";
                    resp += helper.reverseDate(element.getEntryDate().toString()) + "_~_";
                    resp += element.getContainerContent();
                }
            }
        }
        if (operation.equals("getManifest")) {
            resp = "getManifest";
            List elements = helper.getManifest(Registry_Number, entryDate);
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                while (i.hasNext()) {
                    TwmManifest element = (TwmManifest) i.next();
                    resp += element.getCarrierAddress().trim() + "_~_";
                    resp += element.getCarrierCode().trim() + "_~_";
                    resp += element.getCarrierName().trim() + "_~_";
                    resp += element.getCustomsOfficeCode().trim() + "_~_";
                    resp += element.getMasterInformation().trim() + "_~_";
                    resp += element.getModeOfTransportCode().trim() + "_~_";
                    resp += element.getNameOfTransporter().trim() + "_~_";
                    resp += element.getNationalityOfTransporterCode().trim() + "_~_";
                    resp += element.getPlaceOfDepartureCode().trim() + "_~_";
                    resp += element.getPlaceOfDestinationCode().trim() + "_~_";
                    resp += element.getPlaceOfTransporter().trim() + "_~_";
                    resp += element.getRegistrationNumber().trim() + "_~_";
                    resp += element.getRegistryNumber().trim() + "_~_";
                    resp += element.getTimeOfArrival().trim() + "_~_";
                    resp += helper.reverseDate(element.getDateOfArrival().toString()) + "_~_";
                    resp += helper.reverseDate(element.getDepartureDate().toString()) + "_~_";
                    resp += helper.reverseDate(element.getEntryDate().toString()) + "_~_";
                    resp += element.getGrossTonnage().toString() + "_~_";
                    resp += ((element.getLastDischargeDate() != null) ? helper.reverseDate(element.getLastDischargeDate().toString()) : "") + "_~_";
                    resp += element.getNetTonnage().toString() + "_~_";
                    resp += ((element.getRegistrationDate() != null) ? helper.reverseDate(element.getRegistrationDate().toString()) : "") + "_~_";
                    resp += element.getSerialno().toString() + "_~_getManifest";
                }
            }
        }
        if (operation.equals("getAllVoyagenos")) {
            resp = "getAllVoyagenos";
            List elements = helper.getVoyageno(entryDate, Departure_Date);
            String voyageno = "";
            String entrydate = "";
            String vesselname = "";
            int flg = 0, counter = 0;
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                while (i.hasNext()) {
                    TwmManifest element = (TwmManifest) i.next();
                    voyageno = element.getRegistryNumber().trim();
                    vesselname = element.getNameOfTransporter().trim();
                    entrydate = helper.reverseDate(element.getEntryDate().toString());
                    resp += ++counter + "_~_" + voyageno + "_~_" + vesselname + "_~_" + entrydate + "getAllVoyagenos";
                }
            }
        }
        if (operation.equals("getVoyageno")) {
            resp = "getVoyageno";
            List elements = helper.getVoyageno(entryDate, Departure_Date);
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                while (i.hasNext()) {
                    TwmManifest element = (TwmManifest) i.next();
                    resp += element.getRegistryNumber().trim();
                    break;
                }
            }
        }
        if (operation.equals("getAllBols")) {
            resp = "getAllBols";
            List elements = helper.getAManifest(Registry_Number, entryDate, Departure_Date);
            String voyageno = "";
            String vesselname = "";
            String loadingport = "";
            String unloadingport = "";
            String departuredate = "";
            String arrivaldate = "";
            String entrydate = "";
            String customsofficecode = "";
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                while (i.hasNext()) {
                    TwmManifest element = (TwmManifest) i.next();
                    voyageno = element.getRegistryNumber().trim();
                    vesselname = element.getNameOfTransporter().trim();
                    loadingport = element.getPlaceOfDepartureCode().trim();
                    unloadingport = element.getPlaceOfDestinationCode().trim();
                    departuredate = helper.reverseDate(element.getDepartureDate().toString());
                    arrivaldate = helper.reverseDate(element.getDateOfArrival().toString());
                    entrydate = helper.reverseDate(element.getEntryDate().toString());
                    customsofficecode = element.getCustomsOfficeCode().trim();
                }
            }


            elements = helper.getAllBols(Registry_Number, entryDate, Departure_Date);
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                int flg = 1;
                int counter = 0;
                int sno = 0;
                String lineno = "";
                String bolref = "";
                String exporter = "";
                String consignee = "";
                while (i.hasNext()) {
                    TwmBol element = (TwmBol) i.next();
                    sno = element.getSerialno();
                    bolref = element.getBolReference().trim();
                    lineno = element.getLineNumber().toString();
                    exporter = element.getExporterName().trim();
                    consignee = element.getConsigneeName().trim();
                    if (flg == 1) {
                        flg = 0;
                        resp += "<tr style='font-weight:bold; color:#999999;background-color:#FFFFFF;'>";
                        resp += "<td width='10%' align='right'>" + (++counter) + ".</td>";
                        resp += "<td width='10%'><a href=javascript:populateMyBol('" + sno + "')>" + lineno + "</a></td>";
                        resp += "<td width='10%'>" + bolref + "</td>";
                        resp += "<td width='10%'>" + exporter + "</td>";
                        resp += "<td width='10%'>" + consignee + "</td>";
                        resp += "<td width='10%'>" + voyageno + "</td>";
                        resp += "<td width='10%'>" + vesselname + "</td>";
                        resp += "<td width='10%'>" + loadingport + "</td>";
                        resp += "<td width='10%'>" + unloadingport + "</td>";
                        resp += "<td width='10%'>" + departuredate + "</td>";
                        resp += "<td width='10%'>" + arrivaldate + "</td>";
                        resp += "<td width='10%'>" + customsofficecode + "</td></tr>";
                    } else {
                        flg = 1;
                        resp += "<tr style='font-weight:bold; color:#FFFFFF;background-color:#999999;'>";
                        resp += "<td width='10%' align='right'>" + (++counter) + ".</td>";
                        resp += "<td width='10%'><a href=javascript:populateMyBol('" + sno + "')>" + lineno + "</a></td>";
                        resp += "<td width='10%'>" + bolref + "</td>";
                        resp += "<td width='10%'>" + exporter + "</td>";
                        resp += "<td width='10%'>" + consignee + "</td>";
                        resp += "<td width='10%'>" + voyageno + "</td>";
                        resp += "<td width='10%'>" + vesselname + "</td>";
                        resp += "<td width='10%'>" + loadingport + "</td>";
                        resp += "<td width='10%'>" + unloadingport + "</td>";
                        resp += "<td width='10%'>" + departuredate + "</td>";
                        resp += "<td width='10%'>" + arrivaldate + "</td>";
                        resp += "<td width='10%'>" + customsofficecode + "</td></tr>";
                    }
                }
            }
            resp += "getAllBols" + entrydate;
        }
        if (operation.equals(
                "getNextBol")) {
            List elements = helper.getNextBol(Registry_Number, entryDate);
            resp = "getNextBol" + (elements.size() + 1);
        }
        if (operation.equals("getAllRecords")) {
            resp = "getAllRecords";
            List elements = helper.getAllCodes(table, serialno);
            if (elements != null && elements.size() > 0) {
                Iterator i = elements.iterator();
                int sno = 0;
                String codes = "";
                String descs = "";
                while (i.hasNext()) {
                    if (table.equals("ports")) {
                        Ports element = (Ports) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("bols")) {
                        Bols element = (Bols) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("boltypes")) {
                        Boltypes element = (Boltypes) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("carriers")) {
                        Carriers element = (Carriers) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getName().trim();
                    }
                    if (table.equals("customs")) {
                        Customs element = (Customs) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("nations")) {
                        Nations element = (Nations) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("containers")) {
                        Containers element = (Containers) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("packages")) {
                        Packages element = (Packages) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }
                    if (table.equals("transports")) {
                        Transports element = (Transports) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                    }

                    resp += sno + "_~_" + codes + "_~_" + descs + "getAllRecords";
                }
                //resp = table + "getAllRecords" + resp;
            }
        }

        out.write(resp);

        out.close();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);




    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);




    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";


    }// </editor-fold>
}
