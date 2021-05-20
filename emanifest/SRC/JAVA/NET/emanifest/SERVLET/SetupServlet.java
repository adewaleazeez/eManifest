/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emanifest.servlet;

import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import net.emanifest.hibernate.HibernateHelper;
import java.util.List;
import net.emanifest.hibernate.Bols;
import net.emanifest.hibernate.Boltypes;
import net.emanifest.hibernate.Carriers;
import net.emanifest.hibernate.Containers;
import net.emanifest.hibernate.Customs;
import net.emanifest.hibernate.Nations;
import net.emanifest.hibernate.Packages;
import net.emanifest.hibernate.Ports;
import net.emanifest.hibernate.Transports;
import net.emanifest.hibernate.Xmlfolders;

/**
 *
 * @author Adewale Azeez
 */
public class SetupServlet extends HttpServlet {

    private static String operation;
    private static String table;
    private static String serialno;
    private static String code;
    private static String desc;
    private static String name;
    private static String address;
    private static String manifest;
    private static String lading;
    private static String registration;
    private static String batchfile;
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

        code = request.getParameter("code");
        if (code == null) {
            code = "";
        }

        desc = request.getParameter("desc");
        if (desc == null) {
            desc = "";
        }

        table = request.getParameter("table");
        if (table == null) {
            table = "";
        }

        name = request.getParameter("name");
        if (name == null) {
            name = "";
        }

        address = request.getParameter("address");
        if (address == null) {
            address = "";
        }

        manifest = request.getParameter("manifest");
        if (manifest == null) {
            manifest = "";
        }

        lading = request.getParameter("lading");
        if (lading == null) {
            lading = "";
        }

        registration = request.getParameter("registration");
        if (registration == null) {
            registration = "";
        }

        batchfile = request.getParameter("batchfile");
        if (batchfile == null) {
            batchfile = "";
        }

        Bols bols = new Bols();
        Boltypes boltypes = new Boltypes();
        Carriers carriers = new Carriers();
        Customs customs = new Customs();
        Nations nations = new Nations();
        Containers containers = new Containers();
        Packages packages = new Packages();
        Ports ports = new Ports();
        Transports transports = new Transports();
        Xmlfolders xmlfolders = new Xmlfolders();

        if (operation.equals("add")) {
            resp = "";
            if (table.equals("xml")) {
                xmlfolders.setSerialno(1);
                xmlfolders.setBol(lading);
                xmlfolders.setManifest(manifest);
                xmlfolders.setRegistration(registration);
                xmlfolders.setBatchfile(batchfile);
                resp = helper.addXMLs(xmlfolders);
            }

            if (table.equals("bols")) {
                bols.setCode(code);
                bols.setDescription(desc);
                resp = helper.addBolNatures(bols);
            }

            if (table.equals("boltypes")) {
                boltypes.setCode(code);
                boltypes.setDescription(desc);
                resp = helper.addBoltypes(boltypes);
            }

            if (table.equals("carriers")) {
                carriers.setCode(code);
                carriers.setName(desc);
                carriers.setAddress(address);
                resp = helper.addCarriers(carriers);
            }

            if (table.equals("customs")) {
                customs.setCode(code);
                customs.setDescription(desc);
                resp = helper.addCustoms(customs);
            }

            if (table.equals("nations")) {
                nations.setCode(code);
                nations.setDescription(desc);
                resp = helper.addNations(nations);
            }

            if (table.equals("containers")) {
                containers.setCode(code);
                containers.setDescription(desc);
                resp = helper.addContainers(containers);
            }

            if (table.equals("packages")) {
                packages.setCode(code);
                packages.setDescription(desc);
                resp = helper.addPackages(packages);
            }

            if (table.equals("ports")) {
                ports.setCode(code);
                ports.setDescription(desc);
                resp = helper.addPorts(ports);
            }

            if (table.equals("transports")) {
                transports.setCode(code);
                transports.setDescription(desc);
                resp = helper.addTransports(transports);
            }
        }

        if (operation.equals("delete")) {
            resp = "addfailed";
            if (table.equals("bols")) {
                bols.setSerialno(Integer.parseInt(serialno));
                bols.setCode(code);
                bols.setDescription(desc);
                resp = helper.deleteBolNatures(bols);
            }

            if (table.equals("boltypes")) {
                boltypes.setSerialno(Integer.parseInt(serialno));
                boltypes.setCode(code);
                boltypes.setDescription(desc);
                resp = helper.deleteBoltypes(boltypes);
            }

            if (table.equals("carriers")) {
                carriers.setSerialno(Integer.parseInt(serialno));
                carriers.setCode(code);
                carriers.setName(desc);
                carriers.setAddress(address);
                resp = helper.deleteCarriers(carriers);
            }

            if (table.equals("customs")) {
                customs.setSerialno(Integer.parseInt(serialno));
                customs.setCode(code);
                customs.setDescription(desc);
                resp = helper.deleteCustoms(customs);
            }

            if (table.equals("nations")) {
                nations.setSerialno(Integer.parseInt(serialno));
                nations.setCode(code);
                nations.setDescription(desc);
                resp = helper.deleteNations(nations);
            }

            if (table.equals("containers")) {
                containers.setSerialno(Integer.parseInt(serialno));
                containers.setCode(code);
                containers.setDescription(desc);
                resp = helper.deleteContainers(containers);
            }

            if (table.equals("packages")) {
                packages.setSerialno(Integer.parseInt(serialno));
                packages.setCode(code);
                packages.setDescription(desc);
                resp = helper.deletePackages(packages);
            }

            if (table.equals("ports")) {
                ports.setSerialno(Integer.parseInt(serialno));
                ports.setCode(code);
                ports.setDescription(desc);
                resp = helper.deletePorts(ports);
            }

            if (table.equals("transports")) {
                transports.setSerialno(Integer.parseInt(serialno));
                transports.setCode(code);
                transports.setDescription(desc);
                resp = helper.deleteTransports(transports);
            }


        }

        if (operation.equals("update")) {
            resp = "addfailed";
            if (table.equals("xml")) {
                xmlfolders.setSerialno(1);
                xmlfolders.setBol(lading);
                xmlfolders.setManifest(manifest);
                xmlfolders.setRegistration(registration);
                xmlfolders.setBatchfile(batchfile);
                resp = helper.updateXMLs(xmlfolders);
            }

            if (table.equals("bols")) {
                bols.setSerialno(Integer.parseInt(serialno));
                bols.setCode(code);
                bols.setDescription(desc);
                resp = helper.updateBolNatures(bols);
            }

            if (table.equals("boltypes")) {
                boltypes.setSerialno(Integer.parseInt(serialno));
                boltypes.setCode(code);
                boltypes.setDescription(desc);
                resp = helper.updateBoltypes(boltypes);
            }

            if (table.equals("carriers")) {
                carriers.setSerialno(Integer.parseInt(serialno));
                carriers.setCode(code);
                carriers.setName(desc);
                carriers.setAddress(address);
                resp = helper.updateCarriers(carriers);
            }

            if (table.equals("customs")) {
                customs.setSerialno(Integer.parseInt(serialno));
                customs.setCode(code);
                customs.setDescription(desc);
                resp = helper.updateCustoms(customs);
            }

            if (table.equals("nations")) {
                nations.setSerialno(Integer.parseInt(serialno));
                nations.setCode(code);
                nations.setDescription(desc);
                resp = helper.updateNations(nations);
            }

            if (table.equals("containers")) {
                containers.setSerialno(Integer.parseInt(serialno));
                containers.setCode(code);
                containers.setDescription(desc);
                resp = helper.updateContainers(containers);
            }

            if (table.equals("packages")) {
                packages.setSerialno(Integer.parseInt(serialno));
                packages.setCode(code);
                packages.setDescription(desc);
                resp = helper.updatePackages(packages);
            }

            if (table.equals("ports")) {
                ports.setSerialno(Integer.parseInt(serialno));
                ports.setCode(code);
                ports.setDescription(desc);
                resp = helper.updatePorts(ports);
            }

            if (table.equals("transports")) {
                transports.setSerialno(Integer.parseInt(serialno));
                transports.setCode(code);
                transports.setDescription(desc);
                resp = helper.updateTransports(transports);
            }
        }

        if (operation.equals("getAllRecs")) {
            resp = "";
            List elements = helper.getAllRecords(table,serialno);
            if (elements != null) {
                Iterator i = elements.iterator();
                //int flg = 1;
                int counter = 0;
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

                    resp += sno + "_~_" + codes + "_~_" + descs + "getAllRecs";
                }
                resp = table + "getAllRecs" + resp;
            }
        }

        if (operation.equals("getARecord")) {
            resp = "";
            List elements = helper.getARecord(table, serialno);
            if (elements != null) {
                Iterator i = elements.iterator();
                int sno = 0;
                String codes = "";
                String descs = "";
                String addrs = "";
                String manif = "";
                String ladin = "";
                String regis = "";
                String batch = "";
                while (i.hasNext()) {
                    if (table.equals("xml")) {
                        Xmlfolders element = (Xmlfolders) i.next();
                        sno = element.getSerialno();
                        manif = element.getManifest().trim();
                        ladin = element.getBol().trim();
                        regis = element.getRegistration().trim();
                        batch = element.getBatchfile().trim();
                        resp += "getARecord" + sno + "getARecord" + manif + "getARecord" + ladin + "getARecord" + regis + "getARecord" + batch;
                        System.out.println("resp   "+resp);
                    }
                    if (table.equals("bols")) {
                        Bols element = (Bols) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("boltypes")) {
                        Boltypes element = (Boltypes) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("carriers")) {
                        Carriers element = (Carriers) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getName().trim();
                        addrs = element.getAddress().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs + "getARecord" + addrs;
                    }
                    if (table.equals("customs")) {
                        Customs element = (Customs) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("nations")) {
                        Nations element = (Nations) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("containers")) {
                        Containers element = (Containers) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("packages")) {
                        Packages element = (Packages) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("ports")) {
                        Ports element = (Ports) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                    if (table.equals("transports")) {
                        Transports element = (Transports) i.next();
                        sno = element.getSerialno();
                        codes = element.getCode().trim();
                        descs = element.getDescription().trim();
                        resp += "getARecord" + sno + "getARecord" + codes + "getARecord" + descs;
                    }
                }
                resp = table + resp;
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
