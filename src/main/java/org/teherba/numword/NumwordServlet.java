/*  Spell Numbers in Different Languages
    (write the number words, weekday, month and season names)
    @(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $
    2016-02-03: JSPs replaced by teherba.numword.view.*View.java; UTF-8 response
    2011-10-17: spellClock
    2009-04-06: renamed from NumberServlet
    2007-02-08: use SpellerFactory
    2005-07-27, Dr. Georg Fischer

    The language specific modules are named according to the
    common 2-letter language/country codes and the
    3-letter language codes of ISO 639-2T (for "terminological" use).
*/
/*
 * Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
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

package org.teherba.numword;
import  org.teherba.numword.BaseSpeller;
import  org.teherba.numword.NumwordCommand;
import  org.teherba.numword.SpellerFactory;
import  org.teherba.numword.Unicode;
import  org.teherba.numword.view.IndexView;
import  org.teherba.numword.view.MessageView;
import  org.teherba.numword.view.MetaInfView;
import  org.teherba.numword.view.UniblockView;
import  org.teherba.numword.view.UnicodeView;
import  org.teherba.numword.view.UnilistView;
import  java.io.IOException;
import  javax.servlet.RequestDispatcher;
import  javax.servlet.ServletConfig;
import  javax.servlet.ServletContext;
import  javax.servlet.ServletException;
import  javax.servlet.http.HttpServlet;
import  javax.servlet.http.HttpServletRequest;
import  javax.servlet.http.HttpServletResponse;
import  javax.servlet.http.HttpSession;
import  org.apache.log4j.Logger;

/** Spell numbers in some language.
 *  This class is the servlet interface to {@link BaseSpeller},
 *  and ressembles the functionality of the commandline interface in
 *  {@link NumwordCommand}.
 *  @author Dr. Georg Fischer
 */
public class NumwordServlet extends HttpServlet {
    public final static String CVSID = "@(#) $Id: NumwordServlet.java 820 2011-11-07 21:59:07Z gfis $";
    // public final static long serialVersionUID = 19470629004L;

    /** log4j logger (category) */
    private Logger log;
    /** instance of the {@link NumwordCommand} commandline interface */
    private NumwordCommand command;

    /** Commandline interface to Unix command <em>unicode</em> */
    private Unicode unicode;

    /** Delivers a subclass of {@link BaseSpeller} */
    private SpellerFactory factory;

    /** Instance for message output */
    private MessageView messageView;

    /** Called by the servlet container to indicate to a servlet
     *  that the servlet is being placed into service.
     *  @param config object containing the servlet's configuration and initialization parameters
     *  @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config); // ???
        log = Logger.getLogger(NumwordServlet.class.getName());
        command = new NumwordCommand();
        unicode = new Unicode();

        messageView = new MessageView();
        messageView.add("en.001", "invalid function {parm}");
        messageView.add("en.003", "invalid language code");
        messageView.add("en.005", "invalid length - more than {parm} digits");
        messageView.add("en.505", "unknown message code {parm}");
    } // init

    /** Creates the response for a HTTP GET request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doGet

    /** Creates the response for a HTTP POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        generateResponse(request, response);
    } // doPost

    /** Gets the value of an HTML input field, maybe as empty string
     *  @param request request for the HTML form
     *  @param name name of the input field
     *  @param defaultValue default value if the field is not present in the request
     *  @return non-null (but possibly empty) string value of the input field
     */
    private String getInputField(HttpServletRequest request, String name, String defaultValue) {
        String value = request.getParameter(name);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    } // getInputField

    /** Creates the response for a HTTP GET or POST request.
     *  @param request fields from the client input form
     *  @param response data to be sent back the user's browser
     *  @throws IOException
     */
    public void generateResponse(HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");
            HttpSession session = request.getSession();
            String view     = getInputField(request, "view"    , "");
            String language = getInputField(request, "language", "deu");
            if (language.equals("")) {
                language = "de";
            }
            String function = getInputField(request, "function", "c");
            String digits   = getInputField(request, "digits"  , "1981");
            session.setAttribute("command"  , command);
            session.setAttribute("language" , language);
            session.setAttribute("digits"   , digits  );
            Object obj = session.getAttribute("factory");
            if (obj == null) {
                factory = new SpellerFactory();
                session.setAttribute("factory", factory);
            } else {
                factory = (SpellerFactory) obj;
            }
            BaseSpeller speller = factory.getSpeller(language);
            if (speller == null) {
                session.setAttribute("messno", "003"); // invalid language code
                messageView.forward(request, response);

            } else if (view.equals("index")) { // main input form
                //                  =====
                if (false) {
                } else if (function.equals      ("c")
                       ||  function.equals      ("C")    // check for alphabetic "digits" input field
                       ||  function.equals      ("d")
                       ||  function.equals      ("g")
                       ||  function.startsWith  ("h")
                       ||  function.equals      ("m")
                       ||  function.equals      ("m3")
                       ||  function.equals      ("p")
                       ||  function.equals      ("s")
                       ||  function.equals      ("w")
                       ||  function.equals      ("w2")
                       ) { // known function code
                    session.setAttribute("function", function);
                    (new IndexView()).forward(request, response);
                } else { 
                    session.setAttribute("messno", "001"); // invalid function
                    session.setAttribute("parm"  , function);
                    messageView.forward(request, response);
                }
                
            } else if (view.equals("uniblock")) {
            	//                  ========
                if (digits.length() > 2) {
                    session.setAttribute("messno", "005"); // invalid length
                    session.setAttribute("parm"  , "2");
                    messageView.forward(request, response);
                } else {
                    session.setAttribute("digits", digits);
                    (new UniblockView()).forward(request, response);
                }

            } else if (view.equals("unicode")) {
            	//                  =======
                if (digits.length() > 4) {
                    session.setAttribute("messno", "005"); // invalid length
                    session.setAttribute("parm"  , "4");
                    messageView.forward(request, response);
                } else {
                    session.setAttribute("text", unicode.describe(digits));
                    (new UnicodeView()).forward(request, response);
                }

            } else if (view.equals("unilist")) {
                //                  =======
                (new UnilistView()).forward(request, response);


            } else if (view.equals("manifest")
                    || view.equals("license" )
                    || view.equals("notice"  )
                    ) { //          ========
                (new MetaInfView()).forward(request, response);

            } // switch view
        } catch (Exception exc) {
            response.getWriter().write(exc.getMessage());
            System.out.println(exc.getMessage());
            throw new IOException(exc.getMessage());
        }
    } // generateResponse

} // NumwordServlet
