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
import  org.teherba.numword.BrailleSpeller;
import  org.teherba.numword.RomanSpeller;

import  org.teherba.numword.AraSpeller;
import  org.teherba.numword.CesSpeller;
import  org.teherba.numword.ChiSpeller;
import  org.teherba.numword.DanSpeller;
import  org.teherba.numword.DeuSpeller;
import  org.teherba.numword.EngSpeller;
import  org.teherba.numword.EpoSpeller;
import  org.teherba.numword.EstSpeller;
import  org.teherba.numword.FinSpeller;
import  org.teherba.numword.FraSpeller;
import  org.teherba.numword.GeoSpeller;
import  org.teherba.numword.GleSpeller;
import  org.teherba.numword.GreSpeller;
import  org.teherba.numword.HunSpeller;
import  org.teherba.numword.IceSpeller;
import  org.teherba.numword.ItaSpeller;
import  org.teherba.numword.JpnSpeller;
import  org.teherba.numword.KorSpeller;
import  org.teherba.numword.LatSpeller;
import  org.teherba.numword.LavSpeller;
import  org.teherba.numword.LitSpeller;
import  org.teherba.numword.NldSpeller;
import  org.teherba.numword.NorSpeller;
import  org.teherba.numword.PolSpeller;
import  org.teherba.numword.PorSpeller;
import  org.teherba.numword.RohSpeller;
import  org.teherba.numword.RumSpeller;
import  org.teherba.numword.RusSpeller;
import  org.teherba.numword.SlkSpeller;
import  org.teherba.numword.SlvSpeller;
import  org.teherba.numword.SpaSpeller;
import  org.teherba.numword.SweSpeller;
import  org.teherba.numword.ThaSpeller;
import  org.teherba.numword.TlhSpeller;
import  org.teherba.numword.TurSpeller;
import  org.teherba.numword.VieSpeller;
import  java.util.ArrayList; // asList
import  java.util.HashMap;
import  java.util.Iterator;
import  java.util.List;
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

        fillWikiLinks();
    } // Constructor(0)

    /** Gets an iterator over all implemented spellers.
     *  @return list iterator over <em>allSpellers</em>
     */
    public Iterator<BaseSpeller> getIterator() {
        Iterator<BaseSpeller> result = spellers.iterator();
        return result;
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

    /** Fills a hashmap with the article names of number 18 in various
     *  Wikipedia projects. These serve as patterns for the generation of
     *  links to the number articles below in {@link getWikipediaLink}.
     *  The <em>put</em> method calls are generated by <em>etc/wikipedia/prep_wiki.pl</em>
     *  from the portuguese page.
     */
    private void fillWikiLinks () {
        wikiLinks = new HashMap<String, String>(40);
        wikiLinks.put("pt", "Dezoito"); // Portuguese
        wikiLinks.put("ar", "18_(%D8%B9%D8%AF%D8%AF)"); // العربية
        wikiLinks.put("ca", "Divuit"); // Català
        wikiLinks.put("co", "18_(numeru)"); // Corsu
        wikiLinks.put("cv", "18_(%D1%85%D0%B8%D1%81%D0%B5%D0%BF)"); // Чӑвашла
        wikiLinks.put("cy", "Un_deg_wyth"); // Cymraeg
        wikiLinks.put("da", "18_(tal)"); // Dansk
        wikiLinks.put("de", "Achtzehn"); // Deutsch
        wikiLinks.put("en", "18_(number)"); // English
        wikiLinks.put("eo", "Dek_ok"); // Esperanto
        wikiLinks.put("es", "Dieciocho"); // Español
        wikiLinks.put("eu", "Hemezortzi"); // Euskara
        wikiLinks.put("fa", "%DB%B1%DB%B8_(%D8%B9%D8%AF%D8%AF)"); // فارسی
        wikiLinks.put("fi", "18_(luku)"); // Suomi
        wikiLinks.put("fr", "18_(nombre)"); // Français
        wikiLinks.put("gn", "Papoapy"); // Avañe'ẽ
        wikiLinks.put("he", "18_(%D7%9E%D7%A1%D7%A4%D7%A8)"); // עברית
        wikiLinks.put("ht", "18_(nonm)"); // Kreyòl ayisyen
        wikiLinks.put("hu", "18_(sz%C3%A1m)"); // Magyar
        wikiLinks.put("ia", "18_(numero)"); // Interlingua
        wikiLinks.put("id", "18_(angka)"); // Bahasa Indonesia
        wikiLinks.put("ig", "Iri_na_asat%E1%BB%8D"); // Igbo
        wikiLinks.put("is", "18_(tala)"); // Íslenska
        wikiLinks.put("it", "18_(numero)"); // Italiano
        wikiLinks.put("ja", "18"); // 日本語
        wikiLinks.put("ko", "18"); // 한국어
        wikiLinks.put("ku", "Hejde"); // Kurdî / كوردی
        wikiLinks.put("la", "Duodeviginti"); // Latina
        wikiLinks.put("lt", "18_(skai%C4%8Dius)"); // Lietuvių
        wikiLinks.put("lv", "18_(skaitlis)"); // Latviešu
        wikiLinks.put("mk", "18_(%D0%B1%D1%80%D0%BE%D1%98)"); // Македонски
        wikiLinks.put("ms", "18_(nombor)"); // Bahasa Melayu
        wikiLinks.put("nl", "18_(getal)"); // Nederlands
        wikiLinks.put("nn", "Talet_18"); // ‪Norsk (nynorsk)‬
        wikiLinks.put("no", "18_(tall)"); // ‪Norsk (bokmål)‬
        wikiLinks.put("pl", "18_(liczba)"); // Polski
        wikiLinks.put("ru", "18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)"); // Русский
        wikiLinks.put("sl", "18_(%C5%A1tevilo)"); // Slovenščina
        wikiLinks.put("sv", "18_(tal)"); // Svenska
        wikiLinks.put("th", "18"); // ไทย
        wikiLinks.put("uk", "18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)"); // Українська
        wikiLinks.put("vi", "18_(s%E1%BB%91)"); // Tiếng Việt
        wikiLinks.put("xh", "Ishumi_elinesibhozo"); // isiXhosa
        wikiLinks.put("yi", "18_(%D7%A0%D7%95%D7%9E%D7%A2%D7%A8)"); // ייִדיש
        wikiLinks.put("zh", "18"); // 中文
        // end of code generated by etc/wikipedia/prep_wiki.pl
    } // fillWikiLinks

    /** Gets a link to the Wikipedia article for a number, in the specified language.
     *  Depending on the language, the article name starts with the digit sequence
     *  or the numeral word. In the first case, 18 is replaced by <em>number</em>,
     *  whereas the number word is used for the link in the second case.
     *  The link is opened in a new window.
     *  @param speller reference to the applicable language code
     *  @param number digit sequence
     *  @param word numeral word
     *  @return complete element of the form
     *  &lt;a href=&quot;http://en.wikipedia.org/wiki/18_(number)&quot; target=&quot;new&quot;>18&lt;/a&gt;
     */
    protected String getWikipediaLink(BaseSpeller speller, String number, String word) {
        StringTokenizer tokenizer = new StringTokenizer(speller.getIso639(), ",");
        String result = number;
        boolean busy = true;
        while (busy && tokenizer.hasMoreTokens()) { // try all tokens
            String iso2 = tokenizer.nextToken();
            if (iso2.length() == 2) { // might be "de", "en" ...
                String article = wikiLinks.get(iso2);
                if (article != null) { // wikiLink map entry was found
                    busy = false;
                    if (article.length() > 0 && Character.isDigit(article.charAt(0))) { // "18_(nombre)"
                        int underPos = article.indexOf('_');
                        if (underPos < 0) {
                            underPos = article.length();
                        }
                        article = number + article.substring(underPos);
                    } else { // "Achtzehn"
                        if (article.length() > 0) {
                            article = word.substring(0, 1).toUpperCase() + word.substring(1).replaceAll("\\s", "_");
                        }
                    }
                    result = "<a href=\"http://" + iso2 + ".wikipedia.org/wiki/" + article + "\" target=\"new\">"
                            + number + "</a>";
                } // entry found
            } // length 2
        } // while all tokens
        return result;
    } // getWikipediaLink

} // SpellerFactory