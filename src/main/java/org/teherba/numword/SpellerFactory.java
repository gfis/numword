/* Selects the applicable speller
    @(#) $Id: SpellerFactory.java 657 2011-03-17 07:56:38Z gfis $
    2012-09-13: dynamic speller array with Class.forName().newInstance
    2011-03-14: RomanSpeller
    2008-12-30: Thai
    2008-02-15: Java 1.5 types
    2007-02-08: copied from org.teherba.xtrans.TransformerFactory
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
import  java.util.ArrayList;
import  java.util.HashMap;
import  java.util.Iterator;
import  java.util.StringTokenizer;
import  org.apache.log4j.Logger;

/** Selects a specific speller, and iterates over the descriptions
 *  of all spellers and their codes.
 *  Initially, a list of all available spellers is built, and classes
 *  which cannot be instantiated are <em>silently</em> ignored.
 *  @author Dr. Georg Fischer
 */
public class SpellerFactory {
    public final static String CVSID = "@(#) $Id: SpellerFactory.java 657 2011-03-17 07:56:38Z gfis $";
    /** log4j logger (category) */
    private Logger log;

    /** Array of spellers for different natural languages */
    private ArrayList<BaseSpeller> spellers;

    /** Map of 2-letter ISO codes to Wikipedia links */
    private HashMap<String, String> wikiLinks;

    /** Attempts to instantiate the speller for some language
     *  @param lang 3-letter language code (lowercase), e.g. "deu", "fra"
     */
    private void addSpeller(String lang) {
        try {
            BaseSpeller speller = (BaseSpeller) Class.forName("org.teherba.numword." + lang + "Speller").newInstance();
            spellers.add(speller);
        } catch (Exception exc) {
            // ignore any error silently - this language will not be known
        }
    } // addSpeller

    /** No-args Constructor.
     *  The order of the languages here defines the order in the user interfaces.
     */
    public SpellerFactory() {
        log = Logger.getLogger(SpellerFactory.class.getName());
        try {
            spellers    = new ArrayList<BaseSpeller>(64);
            addSpeller("Ara");  // Arabic
            addSpeller("Ces");  // Czech
            addSpeller("Chi");  // Chinese
            addSpeller("Dan");  // Danish
            addSpeller("Deu");  // German
            addSpeller("Eng");  // English
            addSpeller("Epo");  // Esperanto
            addSpeller("Est");  // Estonian
            addSpeller("Fin");  // Finnish
            addSpeller("Fra");  // French
            addSpeller("Gle");  // Gaelic / Irish
            addSpeller("Geo");  // Georgian
            addSpeller("Gre");  // Greek
            addSpeller("Hun");  // Hungarian = Magyar
            addSpeller("Ice");  // Icelandic
            addSpeller("Ita");  // Italian
            addSpeller("Jpn");  // Japanese
            addSpeller("Kor");  // Korean
            addSpeller("Lat");  // Latin
            addSpeller("Lav");  // Latvia
            addSpeller("Lit");  // Lithunian
            addSpeller("Nld");  // Netherlands
            addSpeller("Nor");  // Norwegian
            addSpeller("Pol");  // Polski
            addSpeller("Por");  // Portuguese
            addSpeller("Roh");  // Rumantsch Grischun
            addSpeller("Rum");  // Romanian
            addSpeller("Rus");  // Russian
            addSpeller("Slk");  // Slovak
            addSpeller("Slv");  // Slovenian
            addSpeller("Spa");  // Spanish
            addSpeller("Swe");  // Swedish
            addSpeller("Tha");  // Thai
            addSpeller("Tlh");  // Klingon
            addSpeller("Tur");  // Turkish
            addSpeller("Vie");  // Vietnamese

            addSpeller("Braille");
            addSpeller("Roman");
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // Constructor(0)

    /** Gets an iterator over all implemented spellers.
     *  @return list iterator over <em>allSpellers</em>
     */
    public Iterator<BaseSpeller> getIterator() {
        return spellers.iterator();
    } // getIterator

    /** Gets the number of available spellers
     *  @return number of languages which can be spelled
     */
    public int getCount() {
        return spellers.size();
    } // getCount

    /** Determines whether the language code denotes this speller class.
     *  @param speller the speller to be tested
     *  @param language code for the desired language
     */
    public boolean isApplicable(BaseSpeller speller, String language) {
        boolean result = false;
        StringTokenizer tokenizer = new StringTokenizer(speller.getIso639(), ",");
        while (! result && tokenizer.hasMoreTokens()) { // try all tokens
            if (language.equals(tokenizer.nextToken())) {
                result = true;
            }
        } // while all tokens
        return result;
    } // isApplicable

    /** Gets the applicable speller for a specified language code.
     *  @param language ISO639 abbreviation for the language
     *  @return the speller for that language, or <em>null</em> if the
     *  language was not found
     */
    public BaseSpeller getSpeller(String language) {
        BaseSpeller result = null;
        // determine the applicable speller for 'language'
        Iterator<BaseSpeller> siter = getIterator();
        boolean notFound = true;
        while (notFound && siter.hasNext()) {
            BaseSpeller speller = siter.next();
            if (isApplicable(speller, language)) {
                result = speller;
                notFound = false;
            }
        } // while not found
        return result;
    } // getSpeller

} // SpellerFactory
