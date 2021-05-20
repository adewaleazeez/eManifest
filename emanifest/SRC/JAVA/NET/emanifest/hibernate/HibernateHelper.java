/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emanifest.hibernate;

/*import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;*/
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import org.hibernate.Query;
import org.hibernate.Session;
//import org.hibernate.SessionFactory;

/**
 *
 * @author Adewale
 */
public class HibernateHelper {

    /* Date Converters begin */
    public Date convertDate(String date) {
        //return java.sql.Date.valueOf(date.substring(6) + "-" + date.substring(3, 5) + "-" + date.substring(0, 2));
        return java.sql.Date.valueOf(date);
    }

    public String reverseDate(String date) {
        return (date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4));
    }
    /* Date Converters end */

    /* Activities methods begin */
    public void insertActivity(String currentUser, String description) {
        Date today = new Date();
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String time = timeFormatter.format(today);
        String date = dateFormatter.format(today);
        while (true) {
            if (activityExists(currentUser, date, time) == null) {
                break;
            }
            today = new Date();
            date = dateFormatter.format(today);
            time = timeFormatter.format(today);
        }

        ActivitiesId activityId = new ActivitiesId();
        activityId.setUserEmail(currentUser);
        activityId.setActivityDate(date);
        activityId.setActivityTime(time);
        Activities activ = new Activities();
        activ.setId(activityId);
        activ.setDescriptions(currentUser + description);
        insertActivity(activ);
    }

    public void insertActivity(Activities activity) {

        if (activity != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(activity);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Activities activityExists(String useremail, String date, String time) {
        Activities activity = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Activities as activity where activity.id.userEmail='" + useremail + "' and activity.id.activityDate='" + date + "' and activity.id.activityTime='" + time + "'");
            activity = (Activities) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activity;
    }
    /* Activities methods end */

    /* Users methods begin */
    public String insertUser(Users user) {
        Users checkUser = userExists(user.getUserName());
        if (checkUser == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(user);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "inserted";
        } else {
            return "recordexists";
        }
    }

    public String updateUser(Users user) {

        Users checkUser = userExists(user.getUserName());

        if (checkUser != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(user);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "updated";
        } else {

            return "recordnotexist";
        }
    }

    public Users userExists(String username) {
        Users user = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Users as user where user.userName='" + username + "'");
            user = (Users) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public List fetchAllUsers() {
        List<Users> usersList = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Users as users where users.userName<>''");
            usersList = (List<Users>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public Users fetchUser(String username, String password) {
        Users user = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Users as users where users.userName='" + username + "' and users.userPassword='" + password + "'");
            user = (Users) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;

    }
    /* Users methods end */

    /* Setup methods begin */
    public String addBolNatures(Bols bols) {
        Bols checkbol = bolNatureExists(bols.getCode());
        if (checkbol == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(bols);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boladded";
        } else {
            return "bolexists";
        }
    }

    public String addBoltypes(Boltypes boltypes) {
        Boltypes checkboltype = boltypeExists(boltypes.getCode());
        if (checkboltype == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(boltypes);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boltypeadded";
        } else {
            return "boltypeexists";
        }
    }

    public String addCarriers(Carriers carriers) {
        Carriers checkcarrier = carrierExists(carriers.getCode());
        if (checkcarrier == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(carriers);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "carrieradded";
        } else {
            return "carrierexists";
        }
    }

    public String addCustoms(Customs customs) {
        Customs checkcustom = customExists(customs.getCode());
        if (checkcustom == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(customs);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "customadded";
        } else {
            return "customexists";
        }
    }

    public String addNations(Nations nations) {
        Nations checknation = nationExists(nations.getCode());
        if (checknation == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(nations);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "nationadded";
        } else {
            return "nationexists";
        }
    }

    public String addContainers(Containers containers) {
        Containers checkcontainer = containerExists(containers.getCode());
        if (checkcontainer == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(containers);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "containeradded";
        } else {
            return "containerexists";
        }
    }

    public String addPackages(Packages packages) {
        Packages checkpackage = packageExists(packages.getCode());
        if (checkpackage == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(packages);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "packageadded";
        } else {
            return "packageexists";
        }
    }

    public String addPorts(Ports ports) {
        Ports checkport = portExists(ports.getCode());
        if (checkport == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(ports);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "portadded";
        } else {
            return "portexists";
        }
    }

    public String addTransports(Transports transports) {
        Transports checktransport = transportExists(transports.getCode());
        if (checktransport == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(transports);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "transportadded";
        } else {
            return "transportexists";
        }
    }

    public Bols bolNatureExists(String code) {
        Bols bol = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Bols as bol where bol.code='" + code + "'");
            bol = (Bols) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bol;
    }

    public Boltypes boltypeExists(String code) {
        Boltypes boltype = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Boltypes as boltype where boltype.code='" + code + "'");
            boltype = (Boltypes) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return boltype;
    }

    public Carriers carrierExists(String code) {
        Carriers carrier = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Carriers as carrier where carrier.code='" + code + "'");
            carrier = (Carriers) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carrier;
    }

    public Customs customExists(String code) {
        Customs custom = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Customs as custom where custom.code='" + code + "'");
            custom = (Customs) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return custom;
    }

    public Nations nationExists(String code) {
        Nations nation = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Nations as nation where nation.code='" + code + "'");
            nation = (Nations) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nation;
    }

    public Containers containerExists(String code) {
        Containers container = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Containers as container where container.code='" + code + "'");
            container = (Containers) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return container;
    }

    public Packages packageExists(String code) {
        Packages packages = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Packages as packages where packages.code='" + code + "'");
            packages = (Packages) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packages;
    }

    public Ports portExists(String code) {
        Ports port = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Ports as port where port.code='" + code + "'");
            port = (Ports) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return port;
    }

    public Transports transportExists(String code) {
        Transports transport = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Transports as transport where transport.code='" + code + "'");
            transport = (Transports) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transport;
    }

    public String deleteBolNatures(Bols bols) {
        Bols checkbol = bolNatureExists(bols.getCode());
        if (checkbol != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(bols);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boldeleted";
        } else {
            return "bolnotexist";
        }
    }

    public String deleteBoltypes(Boltypes boltypes) {
        Boltypes checkboltype = boltypeExists(boltypes.getCode());
        if (checkboltype != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(boltypes);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boltypedeleted";
        } else {
            return "boltypenotexist";
        }
    }

    public String deleteCarriers(Carriers carriers) {
        Carriers checkcarrier = carrierExists(carriers.getCode());
        if (checkcarrier != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(carriers);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "carrierdeleted";
        } else {
            return "carriernotexist";
        }
    }

    public String deleteCustoms(Customs customs) {
        Customs checkcustom = customExists(customs.getCode());
        if (checkcustom != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(customs);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "customdeleted";
        } else {
            return "customnotexist";
        }
    }

    public String deleteNations(Nations nations) {
        Nations checknation = nationExists(nations.getCode());
        if (checknation != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(nations);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "nationdeleted";
        } else {
            return "nationnotexist";
        }
    }

    public String deleteContainers(Containers containers) {
        Containers checkcontainer = containerExists(containers.getCode());
        if (checkcontainer != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(containers);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "containerdeleted";
        } else {
            return "containernotexist";
        }
    }

    public String deletePackages(Packages packages) {
        Packages checkpackage = packageExists(packages.getCode());
        if (checkpackage != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(packages);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "packagedeleted";
        } else {
            return "packagenotexist";
        }
    }

    public String deletePorts(Ports ports) {
        Ports checkport = portExists(ports.getCode());
        if (checkport != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(ports);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "portdeleted";
        } else {
            return "portnotexist";
        }
    }

    public String deleteTransports(Transports transports) {
        Transports checktransport = transportExists(transports.getCode());
        if (checktransport != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(transports);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "transportdeleted";
        } else {
            return "transportnotexist";
        }
    }

    public String updateBolNatures(Bols bols) {

        Bols checkbol = bolNatureExists(bols.getCode());

        if (checkbol != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(bols);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "bolupdated";
        } else {

            return "bolnotexist";
        }
    }

    public String updateBoltypes(Boltypes boltypes) {

        Boltypes checkboltype = boltypeExists(boltypes.getCode());

        if (checkboltype != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(boltypes);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "boltypeupdated";
        } else {

            return "boltypenotexist";
        }
    }

    public String updateCarriers(Carriers carriers) {

        Carriers checkcarrier = carrierExists(carriers.getCode());

        if (checkcarrier != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(carriers);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "carrierupdated";
        } else {

            return "carriernotexist";
        }
    }

    public String updateCustoms(Customs customs) {

        Customs checkcustom = customExists(customs.getCode());

        if (checkcustom != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(customs);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "customupdated";
        } else {

            return "customnotexist";
        }
    }

    public String updateNations(Nations nations) {

        Nations checknation = nationExists(nations.getCode());

        if (checknation != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(nations);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "nationupdated";
        } else {

            return "nationnotexist";
        }
    }

    public String updateContainers(Containers containers) {

        Containers checkcontainer = containerExists(containers.getCode());

        if (checkcontainer != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(containers);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "containerupdated";
        } else {

            return "containernotexist";
        }
    }

    public String updatePackages(Packages packages) {

        Packages checkpackage = packageExists(packages.getCode());

        if (checkpackage != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(packages);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "packageupdated";
        } else {

            return "packagenotexist";
        }
    }

    public String updatePorts(Ports ports) {
        Ports checkport = portExists(ports.getCode());

        if (checkport != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(ports);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "portupdated";
        } else {

            return "portnotexist";
        }
    }

    public String updateTransports(Transports transports) {

        Transports checktransport = transportExists(transports.getCode());

        if (checktransport != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(transports);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "transportupdated";
        } else {

            return "transportnotexist";
        }
    }

    public List getAllRecords(String table, String serialno) {
        int sno1 = 0, sno2 = 0;
        if (!table.equals("ports")) {
            sno1 = Integer.parseInt(serialno);
            sno2 = Integer.parseInt(serialno) + 99;
        }
        if (table.equals("ports")) {
            List<Ports> portsList = null;
            serialno = serialno.substring(0, 2);
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Ports as ports where ports.code like '" + serialno + "%'");
                portsList = (List<Ports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return portsList;
        }
        /*if (table.equals("ports")) {
        List<Ports> portsList = null;

        try {
        final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Query q = session.createQuery("from Ports as ports where ports.code like '"+serialno+"%'");
        portsList = (List<Ports>) q.list();
        session.getTransaction().commit();
        } catch (Exception e) {
        e.printStackTrace();
        }
        return portsList;
        }*/
        if (table.equals("bols")) {
            List<Bols> bolsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Bols as bols where bols.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                bolsList = (List<Bols>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bolsList;
        }
        if (table.equals("boltypes")) {
            List<Boltypes> boltypesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Boltypes as boltypes where boltypes.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                boltypesList = (List<Boltypes>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return boltypesList;
        }
        if (table.equals("carriers")) {
            List<Carriers> carriersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Carriers as carriers where carriers.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                carriersList = (List<Carriers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carriersList;
        }
        if (table.equals("customs")) {
            List<Customs> customsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Customs as customs where customs.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                customsList = (List<Customs>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customsList;
        }
        if (table.equals("nations")) {
            List<Nations> nationsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Nations as nations where nations.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                nationsList = (List<Nations>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nationsList;
        }
        if (table.equals("containers")) {
            List<Containers> containersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Containers as containers where containers.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                containersList = (List<Containers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return containersList;
        }
        if (table.equals("packages")) {
            List<Packages> packagesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Packages as packages where packages.code>'' and (serialno>=" + sno1 + " and serialno<=" + sno2 + ")");
                packagesList = (List<Packages>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packagesList;
        }
        if (table.equals("transports")) {
            List<Transports> transportsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Transports as transports where transports.code>'' and serialno>=" + sno1 + " and serialno<=" + sno2);
                transportsList = (List<Transports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transportsList;
        }
        return null;
    }

    public List getAllCodes(String table, String code) {
        if (table.equals("ports")) {
            List<Ports> portsList = null;
            code = code.substring(0, 2);
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Ports as ports where ports.code like '" + code + "%'");
                portsList = (List<Ports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return portsList;
        }
        if (table.equals("bols")) {
            List<Bols> bolsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Bols as bols where bols.code>''");
                bolsList = (List<Bols>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bolsList;
        }
        if (table.equals("boltypes")) {
            List<Boltypes> boltypesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Boltypes as boltypes where boltypes.code>''");
                boltypesList = (List<Boltypes>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return boltypesList;
        }
        if (table.equals("carriers")) {
            List<Carriers> carriersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Carriers as carriers where carriers.code>''");
                carriersList = (List<Carriers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carriersList;
        }
        if (table.equals("customs")) {
            List<Customs> customsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Customs as customs where customs.code>''");
                customsList = (List<Customs>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customsList;
        }
        if (table.equals("nations")) {
            List<Nations> nationsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Nations as nations where nations.code>''");
                nationsList = (List<Nations>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nationsList;
        }
        if (table.equals("containers")) {
            List<Containers> containersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Containers as containers where containers.code>''");
                containersList = (List<Containers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return containersList;
        }
        if (table.equals("packages")) {
            List<Packages> packagesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Packages as packages where packages.code>''");
                packagesList = (List<Packages>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packagesList;
        }
        if (table.equals("transports")) {
            List<Transports> transportsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Transports as transports where transports.code>''");
                transportsList = (List<Transports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transportsList;
        }
        return null;
    }

    public String addXMLs(Xmlfolders xmlfolders) {
        Xmlfolders checkxmlfolder = xmlfolderExists(xmlfolders.getSerialno());
        if (checkxmlfolder == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(xmlfolders);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "xmlfolderadded";
        } else {
            String resp = updateXMLs(xmlfolders);
            return resp;
        }
    }

    public String updateXMLs(Xmlfolders xmlfolders) {

        Xmlfolders checkxmlfolder = xmlfolderExists(xmlfolders.getSerialno());

        if (checkxmlfolder != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(xmlfolders);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "xmlfolderupdated";
        } else {
            String resp = addXMLs(xmlfolders);
            return resp;
        }
    }

    public Xmlfolders xmlfolderExists(int serialno) {
        Xmlfolders xmlfolder = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Xmlfolders as xmlfolder where xmlfolder.serialno=" + serialno);
            xmlfolder = (Xmlfolders) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlfolder;
    }

    public List getARecord(String table, String serialno) {
        if (table.equals("xml")) {
            List<Xmlfolders> xmlfoldersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Xmlfolders as xmlfolders where xmlfolders.serialno=1");
                xmlfoldersList = (List<Xmlfolders>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return xmlfoldersList;
        }
        if (table.equals("bols")) {
            List<Bols> bolsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Bols as bols where bols.serialno=" + serialno);
                bolsList = (List<Bols>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bolsList;
        }
        if (table.equals("boltypes")) {
            List<Boltypes> boltypesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Boltypes as boltypes where boltypes.serialno=" + serialno);
                boltypesList = (List<Boltypes>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return boltypesList;
        }
        if (table.equals("carriers")) {
            List<Carriers> carriersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Carriers as carriers where carriers.serialno=" + serialno);
                carriersList = (List<Carriers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return carriersList;
        }
        if (table.equals("customs")) {
            List<Customs> customsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Customs as customs where customs.serialno=" + serialno);
                customsList = (List<Customs>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customsList;
        }
        if (table.equals("nations")) {
            List<Nations> nationsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Nations as nations where nations.serialno=" + serialno);
                nationsList = (List<Nations>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return nationsList;
        }
        if (table.equals("containers")) {
            List<Containers> containersList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Containers as containers where containers.serialno=" + serialno);
                containersList = (List<Containers>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return containersList;
        }
        if (table.equals("packages")) {
            List<Packages> packagesList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Packages as packages where packages.serialno=" + serialno);
                packagesList = (List<Packages>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return packagesList;
        }
        if (table.equals("ports")) {
            List<Ports> portsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Ports as ports where ports.serialno=" + serialno);
                portsList = (List<Ports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return portsList;
        }
        if (table.equals("transports")) {
            List<Transports> transportsList = null;

            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                Query q = session.createQuery("from Transports as transports where transports.serialno=" + serialno);
                transportsList = (List<Transports>) q.list();
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return transportsList;
        }
        return null;
    }
    /* Setup methods end */

    /* Manifest/Bol methods Begin */
    public String addManifests(TwmManifest twmManifest) {
        TwmManifest checktwmManifest = manifestExists(twmManifest.getRegistryNumber(), twmManifest.getNameOfTransporter(), twmManifest.getEntryDate().toString());
        if (checktwmManifest == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(twmManifest);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "twmManifestadded";
        } else {
            return "twmManifestexists";
        }
    }

    public String addBols(TwmBol twmBol) {
        TwmBol checktwmBol = bolExists(twmBol.getRegistryNumber(), twmBol.getEntryDate().toString(), twmBol.getLineNumber().toString());
        if (checktwmBol == null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(twmBol);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boladded";
        } else {
            return "bolexists";
        }
    }

    public String deleteManifests(TwmManifest twmManifest) {
        TwmManifest checktwmManifest = manifestExists(twmManifest.getRegistryNumber(), twmManifest.getNameOfTransporter(), twmManifest.getEntryDate().toString());
        if (checktwmManifest != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(twmManifest);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "mandeleted";
        } else {
            return "mannotexist";
        }
    }

    public String deleteBols(TwmBol twmBol) {
        TwmBol checktwmBol = bolExists(twmBol.getRegistryNumber(), twmBol.getEntryDate().toString(), twmBol.getLineNumber().toString());
        if (checktwmBol != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.delete(twmBol);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "boldeleted";
        } else {
            return "bolnotexist";
        }
    }

    public String updateManifests(TwmManifest twmManifest) {
        TwmManifest checktwmManifest = manifestExists(twmManifest.getRegistryNumber(), twmManifest.getNameOfTransporter(), twmManifest.getEntryDate().toString());

        if (checktwmManifest != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(twmManifest);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "manifestupdated";
        } else {

            return "manifestnotexist";
        }
    }

    public void updateBols(TwmManifest twmManifest) {
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("update TwmBol as t set t.departureDate='" + twmManifest.getDepartureDate() + "', t.customsOfficeCode='" + twmManifest.getCustomsOfficeCode() + "' where t.registryNumber='" + twmManifest.getRegistryNumber() + "' and t.entryDate='" + twmManifest.getEntryDate() + "'");
            q.executeUpdate();
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String updateBols(TwmBol twmBol) {

        TwmBol checktwmManifest = bolExists(twmBol.getRegistryNumber(), twmBol.getEntryDate().toString(), twmBol.getLineNumber().toString());

        if (checktwmManifest != null) {
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.merge(twmBol);
                session.getTransaction().commit();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return "bolupdated";
        } else {

            return "bolnotexist";
        }
    }

    public TwmManifest manifestExists(String regno, String transporter, String date) {
        TwmManifest twmManifest = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmManifest as twmManifest where twmManifest.registryNumber='" + regno + "' and twmManifest.nameOfTransporter='" + transporter + "' and twmManifest.entryDate='" + date + "'");
            twmManifest = (TwmManifest) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmManifest;
    }

    public TwmManifest manifestExists(String regno, String date) {
        TwmManifest twmManifest = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmManifest as twmManifest where twmManifest.registryNumber='" + regno + "' and twmManifest.entryDate='" + date + "'");
            twmManifest = (TwmManifest) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmManifest;
    }

    public TwmBol bolExists(String regno, String date, String lineno) {
        TwmBol twmBol = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmBol as twmBol where twmBol.registryNumber='" + regno + "' and twmBol.entryDate='" + date + "' and twmBol.lineNumber='" + lineno + "' ");
            twmBol = (TwmBol) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmBol;
    }

    public TwmBol bolExists(String regno, String date) {
        TwmBol twmBol = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmBol as twmBol where twmBol.registryNumber='" + regno + "' and twmBol.entryDate='" + date + "' ");
            twmBol = (TwmBol) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmBol;
    }

    public List getAllBols(String regno, String date1, String date2) {
        List<TwmBol> twmBolList = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String sql = "";
            if (!regno.equals("")) {
                sql = "from TwmBol as twmBol where twmBol.registryNumber='" + regno + "' and twmBol.entryDate>='" + date1 + "' and twmBol.entryDate<='" + date2 + "'";
            } else {
                sql = "from TwmBol as twmBol where twmBol.entryDate>='" + date1 + "' and twmBol.entryDate<='" + date2 + "'";
            }
            Query q = session.createQuery(sql);
            twmBolList = (List<TwmBol>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmBolList;
    }

    public List getAManifest(String regno, String date1, String date2) {
        List<TwmManifest> twmManifestList = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String sql = "";
            if (!regno.equals("")) {
                sql = "from TwmManifest as twmManifest where twmManifest.registryNumber='" + regno + "' and twmManifest.entryDate>='" + date1 + "' and twmManifest.entryDate<='" + date2 + "'";
            } else {
                sql = "from TwmManifest as twmManifest where twmManifest.entryDate>='" + date1 + "' and twmManifest.entryDate<='" + date2 + "'";
            }
            Query q = session.createQuery(sql);
            twmManifestList = (List<TwmManifest>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmManifestList;
    }

    public List getVoyageno(String date1, String date2) {
        List<TwmManifest> twmManifestList = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            String sql = "";
            sql = "from TwmManifest as twmManifest where twmManifest.entryDate>='" + date1 + "' and twmManifest.entryDate<='" + date2 + "' order by registryNumber";
            Query q = session.createQuery(sql);
            twmManifestList = (List<TwmManifest>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmManifestList;
    }

    public List getMyBol(String serialno) {
        List<TwmBol> twmBolList = null;

        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmBol as twmBol where twmBol.serialno=" + serialno);
            twmBolList = (List<TwmBol>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmBolList;

    }

    public List getManifest(String regno, String date) {
        List<TwmManifest> twmManifestList = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmManifest as twmManifest where twmManifest.registryNumber='" + regno + "' and twmManifest.entryDate='" + date + "'");
            twmManifestList = (List<TwmManifest>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmManifestList;

    }

    public List getNextBol(String regno, String date) {
        List<TwmBol> twmBolList = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from TwmBol as twmBol where twmBol.registryNumber='" + regno + "' and twmBol.entryDate='" + date + "'");
            twmBolList = (List<TwmBol>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return twmBolList;

    }

    public List getXmlFilenames() {
        List<Xmlfolders> xmlfoldersList = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Xmlfolders as xmlfolders where xmlfolders.serialno='1'");
            xmlfoldersList = (List<Xmlfolders>) q.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlfoldersList;

    }
    /* Manifest/Bol methods end */

    public Eregistrationrequest checkLicense() {
        Eregistrationrequest checkRec = null;
        try {
            final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query q = session.createQuery("from Eregistrationrequest as e");
            checkRec = (Eregistrationrequest) q.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkRec;
    }

    public void updateLicense() {
        if (checkLicense() == null) {
            Eregistrationrequest checkRec = new Eregistrationrequest();

            checkRec.setCustomsOfficeCode("1");
            checkRec.setDepartureDate(convertDate("2011-03-01"));
            checkRec.setRegistryNumber("1");
            checkRec.setSerialno(1);
            checkRec.setTotalGrossMass(1.0);
            checkRec.setTotalNumberOfBols(1);
            checkRec.setTotalNumberOfContainers(1);
            checkRec.setTotalNumberOfPackages(1);
            try {
                final Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.save(checkRec);
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
