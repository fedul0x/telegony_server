<%-- 
    Document   : index
    Created on : 02.01.2012, 0:18:52
    Author     : Ivashin Alexey
--%>

<%@page import="telegony.general.Zone"%>
<%@page import="telegony.general.ZoneType"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.hibernate.Query"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="telegony.dataaccess.HibernateUtil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <% Session cs = HibernateUtil.currentSession();

            Transaction tx = cs.beginTransaction();
            
            /*cs.saveOrUpdate(ZoneType.GENERAL_ZONE_TYPE);
            cs.saveOrUpdate(ZoneType.INNER_ZONE_TYPE);
            cs.saveOrUpdate(ZoneType.OUTER_ZONE_TYPE);*/
            Zone zone = new Zone();
            zone.setZoneType(ZoneType.INNER_ZONE_TYPE);
            cs.saveOrUpdate(zone);
            
            out.print("This is zone types");
            Query query = cs.createQuery("from ZoneType");
            for (Iterator it = query.iterate(); it.hasNext();) {
                it.next();
                out.print(((ZoneType)it).getDescription());

            }

            tx.commit();

            HibernateUtil.closeSession();%>
    </body>
</html>
