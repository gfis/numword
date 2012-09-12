<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%--
    Shows the manifest, the license and the notices.
    @(#) $Id: metaInf.jsp 13 2008-09-05 05:58:51Z gfis $
    2008-02-13: Java 1.5 types
    2007-12-23, Georg Fischer: copied from unit/web/metaInf.jsp
--%>
<%--
 * Copyright 2007 Dr. Georg Fischer <punctum at punctum dot kom>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
--%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.TreeSet"%>

<% response.setContentType("text/html; charset=UTF-8"); %>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>File Transformation</title>
    <link rel="stylesheet" type="text/css" href="stylesheet.css" />
</head>
<body>
<%
    String CVSID = "@(#) $Id: metaInf.jsp 13 2008-09-05 05:58:51Z gfis $";
    String line = null;
    String fileName = null;
    String view = request.getParameter("view");
    if (view == null) {
        view = "manifest";
    }
    if (view.equals("package")) {
        // Package [] packs = this.getClass().getClassLoader().getPackages();
        Package [] packs = Package.getPackages();
        TreeSet/*<1.5*/<String>/*1.5>*/ tree = new TreeSet/*<1.5*/<String>/*1.5>*/();
%>
<tt>
<pre>
<%
        for (int ipack = 0; ipack < packs.length; ipack ++) {
            tree.add(packs[ipack].getName());
        } // for ipack
        Iterator/*<1.5*/<String>/*1.5>*/ iter = tree.iterator();
        while (iter.hasNext()) {
            out.println((String) iter.next());
        } // while iter
%>
</pre>
</tt>
<%
    } else {
        if (false) {
        } else if (view.equals("license")) {
            %>
            <a name="license" />
            <h2>License</h2>
            <%
            fileName = "LICENSE.txt";
        } else if (view.equals("notice")) {
            %>
            <a name="notice" />
            <h2>Included Software Packages</h2>
            <%
            fileName = "NOTICE.txt";
        } else if (view.equals("root")) {
            %>
            <a name="Root Directory" />
            <h2>License</h2>
            <%
            fileName = ".";
        } else { // if (view.equals("manifest")) {
            %>
            <a name="manifest" />
            <h2>JAR Manifest</h2>
            <%
            fileName = "META-INF/MANIFEST.MF";
        }
%>
<tt>
<pre>
<%
        BufferedReader reader = new BufferedReader(new InputStreamReader
                (this.getClass().getClassLoader().getResourceAsStream(fileName)));
        while ((line = reader.readLine()) != null) {
            out.println(line);
        } // while
    } // not "package"
%>
</pre>
</tt>

<p />
<br /><a href="docs/api/index.html">API</a>
<p />
<font size="-1">
Questions, remarks to: <a href="mailto:punctum@punctum.com">Dr. Georg Fischer</a>
</font>
</p>
    
</body>
</html>
