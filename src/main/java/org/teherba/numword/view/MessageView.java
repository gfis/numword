/*  MessageView.java - show the language specific message text
 *  @(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $
 *  2016-01-18, Georg Fischer: copied from dbat/MessagePage.java; + 'add' method
 */
/*
 * Copyright 2016 Dr. Georg Fischer <punctum at punctum dot kom>
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
 */
package org.teherba.numword.view;
import  java.io.PrintWriter;
import  java.util.HashMap;
import  java.util.regex.Pattern;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.http.HttpSession;
import  org.apache.log4j.Logger;

/** This class stores the language-specific message texts,
 *  and prints the text for some numbered message.
 *  The code is extracted from the former <em>help.jsp</em>.
 *  @author Dr. Georg Fischer
 */
public class MessageView {
    public final static String CVSID = "@(#) $Id: 058b6a55bb7a7383cb32ef795569872161b7e1bf $";
    public final static long serialVersionUID = 19470629;

    /** log4j logger (category) */
    private Logger log;
    
    /** Map which stores the language-specific message text patterns.
     *  The texts are stored under keys of the form ll.nnn, with a 
     *  2-letter language code, and 3 digits for the message number.
     *  The texts may contain patterns "{parm}", "{par2}" and "{par3}" which are
     *  replaced by the values of up to 3 parameters.
     */
    private HashMap<String, String> hash;

    /** No-argument constructor
     */
    public MessageView() {
        log = Logger.getLogger(MessageView.class.getName());
        hash = new HashMap<String, String>();
        // the message texts should be {@link #add added} in the servlet's init method
    } // constructor()
    
    /** Adds a language-specific message text to the map.
     *  @param code ll.nnn = language code and message number, 
     *  possibly containing "{parm}", "{par2}", "{par3}"
     *  @param text text of the message in language ll
     */
    public void add(String code, String text) {
        hash.put(code, text);
    } // add
    
    /** Processes an http GET request
     *  @param request request with header fields
     *  @param response response with writer
     *  @throws IOException
     */
    public void forward(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String lang     = (String) session.getAttribute("lang");
        if (lang == null) {
            lang = "en";
        } // invalid language
        String messno   = (String) session.getAttribute("messno");
        if (messno == null) {
            messno = "505";
        }
        String parm     = (String) session.getAttribute("parm");
        if (parm == null) {
            parm = "";
        }
        String par2     = (String) session.getAttribute("par2");
        if (par2 == null) {
            par2 = "";
        }
        String par3     = (String) session.getAttribute("par3");
        if (par3 == null) {
            par3 = "";
        }
        String text = hash.get(lang + "." + messno);
        if (text == null) { // invalid messno
            text = hash.get(lang + "." + "505");
            text = text.replaceAll(Pattern.quote("{parm}"), messno);
            messno = "505";
        } else {
            text = text.replaceAll(Pattern.quote("{parm}"), parm);
            text = text.replaceAll(Pattern.quote("{par2}"), par2);
            text = text.replaceAll(Pattern.quote("{par3}"), par3);
        }

        try {
            PrintWriter out = response.getWriter();
            out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n");
            out.write("    \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
            out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
            out.write("<head>\n");
            out.write("  <meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml;charset=UTF-8\" />\n");
            out.write("  <meta name=\"robots\" content=\"noindex, nofollow\" />\n");
            out.write("  <link rel=\"stylesheet\" type=\"text/css\" href=\"stylesheet.css\" />\n");
            out.write("  <title>Message " + messno + "</title>\n");
            out.write("</head>\n<body>\n<!-- lang=" + lang + ", messno=" + messno + ", text=" + text);
            out.write(", parm=" + parm + ", par2=" + par2 + ", par3=" + par3 + "-->\n");
            out.write("<h3>" + messno + ": " + text + "</h3>\n");
            out.write("</body></html>\n");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        } finally {
        }
    } // forward

} // MessageView
