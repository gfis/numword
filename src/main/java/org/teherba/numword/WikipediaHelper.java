/*  Gets language designations and links from a Wikipedia article
    @(#) $Id: WikipediaHelper.java 657 2011-03-17 07:56:38Z gfis $
    2017-05-28: javadoc 1.8
    2016-01-18: Javadoc links corrected; URIReader moved to org.teherba.common
    2012-09-15: extracted from org.teherba.numword.SpellerFactory
*/
/*
 * Copyright 2012 Dr. Georg Fischer <punctum at punctum dot kom>
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
import  org.teherba.common.URIReader;
import  java.io.BufferedReader;
import  java.io.InputStreamReader;
import  java.text.SimpleDateFormat;
import  java.util.Date;
import  java.util.HashMap;
import  java.util.StringTokenizer;
import  java.util.regex.Matcher;
import  java.util.regex.Pattern;
import  org.apache.log4j.Logger;

/** Reads language designations and links from a Wikipedia article,
 *  and stores them in a hash map.
 *  An Internet connection is needed for the execution of the {@link #main} method.
 *  The code of method {@link #fillWikiLinks} is dynamically modified by the {@link #main}
 *  method.
 *  @author Dr. Georg Fischer
 */
public class WikipediaHelper {
    public final static String CVSID = "@(#) $Id: WikipediaHelper.java 657 2011-03-17 07:56:38Z gfis $";
    /** log4j logger (category) */
    private Logger log;

    /** Map of 2-letter ISO codes to Wikipedia links */
    private HashMap<String, String> wikiLinks;
    /** ISO timestamp without milliseconds */
    public static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");

    /** No-args Constructor.
     *  The order of the languages here defines the order in the user interfaces.
     */
    public WikipediaHelper() {
        log = Logger.getLogger(WikipediaHelper.class.getName());
        try {
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
        wikiLinks = new HashMap<String, String>(40);
        fillWikiLinks();
    } // Constructor(0)

    /** Fills a hashmap with the article names of number 18 in various
     *  Wikipedia projects. These serve as patterns for the generation of
     *  links to the number articles below in {@link #getWikipediaLink}.
     *  The <em>put</em> method calls here are dynamically generated by
     *  the {@link #main} method from the English Wikipedia page for "18_(number)".
     */
    private void fillWikiLinks () {
// START OF REPLACEMENT
// by org.teherba.numword.WikipediaHelper at 2012-09-16 12:48:02
        wikiLinks.put("ab", "%D0%96%D3%99%D0%B0%D0%B0"); // Аҧсшәа
        wikiLinks.put("ar", "18_(%D8%B9%D8%AF%D8%AF)"); // العربية
        wikiLinks.put("gn", "Papoapy"); // Avañe'ẽ
        wikiLinks.put("az", "18_(%C9%99d%C9%99d)"); // Azərbaycanca
        wikiLinks.put("bg", "18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)"); // Български
        wikiLinks.put("bo", "%E0%BC%A1%E0%BC%A8_(%E0%BD%82%E0%BE%B2%E0%BD%84%E0%BD%A6%E0%BC%8B%E0%BD%80%E0%BC%8D)"); // བོད་ཡིག
        wikiLinks.put("ca", "Divuit"); // Català
        wikiLinks.put("cv", "18_(%D1%85%D0%B8%D1%81%D0%B5%D0%BF)"); // Чӑвашла
        wikiLinks.put("cs", "18_(%C4%8D%C3%ADslo)"); // Česky
        wikiLinks.put("ny", "Khumi_n%27zisanu_n%27zitatu"); // Chi-Chewa
        wikiLinks.put("sn", "Gumi_nesere"); // ChiShona
        wikiLinks.put("tum", "Khumi_na_vikhondi_na_vitutatu"); // ChiTumbuka
        wikiLinks.put("cy", "Un_deg_wyth"); // Cymraeg
        wikiLinks.put("da", "18_(tal)"); // Dansk
        wikiLinks.put("de", "Achtzehn"); // Deutsch
        wikiLinks.put("myv", "18_(%D0%BB%D0%BE%D0%B2%D0%BE%D0%BC%D0%B0_%D0%B2%D0%B0%D0%BB)"); // Эрзянь
        wikiLinks.put("es", "Dieciocho"); // Español
        wikiLinks.put("eo", "Dek_ok"); // Esperanto
        wikiLinks.put("eu", "Hemezortzi"); // Euskara
        wikiLinks.put("fa", "%DB%B1%DB%B8_(%D8%B9%D8%AF%D8%AF)"); // فارسی
        wikiLinks.put("fr", "18_(nombre)"); // Français
        wikiLinks.put("ff", "Sappo_e_joweetati"); // Fulfulde
        wikiLinks.put("gan", "18"); // 贛語
        wikiLinks.put("ko", "18"); // 한국어
        wikiLinks.put("ig", "Iri_na_asat%E1%BB%8D"); // Igbo
        wikiLinks.put("id", "18_(angka)"); // Bahasa Indonesia
        wikiLinks.put("ia", "18_(numero)"); // Interlingua
        wikiLinks.put("ik", "Akimiaq_pi%C5%8Basut"); // Iñupiak
        wikiLinks.put("xh", "Ishumi_elinesibhozo"); // IsiXhosa
        wikiLinks.put("is", "18_(tala)"); // Íslenska
        wikiLinks.put("it", "18_(numero)"); // Italiano
        wikiLinks.put("he", "18_(%D7%9E%D7%A1%D7%A4%D7%A8)"); // עברית
        wikiLinks.put("rn", "Cumi_na_munani"); // Kirundi
        wikiLinks.put("ht", "18_(nonm)"); // Kreyòl ayisyen
        wikiLinks.put("ku", "Hejde"); // Kurdî
        wikiLinks.put("lbe", "%D0%90%D1%86%D3%80%D0%BD%D0%B8%D1%8F_%D0%BC%D1%8F%D0%B9%D0%B2%D0%B0"); // Лакку
        wikiLinks.put("la", "Duodeviginti"); // Latina
        wikiLinks.put("lv", "18_(skaitlis)"); // Latviešu
        wikiLinks.put("lt", "18_(skai%C4%8Dius)"); // Lietuvių
        wikiLinks.put("ln", "Z%C3%B3mi_na_mwambe"); // Lingála
        wikiLinks.put("lg", "Kkumi_na_munaana"); // Luganda
        wikiLinks.put("lmo", "N%C3%BCmar_18"); // Lumbaart
        wikiLinks.put("hu", "18_(sz%C3%A1m)"); // Magyar
        wikiLinks.put("mk", "18_(%D0%B1%D1%80%D0%BE%D1%98)"); // Македонски
        wikiLinks.put("ms", "18_(nombor)"); // Bahasa Melayu
        wikiLinks.put("nah", "Caxt%C5%8Dlon%C4%93yi"); // Nāhuatl
        wikiLinks.put("nl", "18_(getal)"); // Nederlands
        wikiLinks.put("ja", "18"); // 日本語
        wikiLinks.put("nap", "Dici%C3%B2tte"); // Nnapulitano
        wikiLinks.put("no", "18_(tall)"); // ‪norsk (bokmål)‬
        wikiLinks.put("nn", "Talet_18"); // ‪norsk (nynorsk)‬
        wikiLinks.put("uz", "18_(son)"); // Oʻzbekcha
        wikiLinks.put("pnb", "18"); // پنجابی
        wikiLinks.put("pl", "18_(liczba)"); // Polski
        wikiLinks.put("pt", "Dezoito"); // Português
        wikiLinks.put("ru", "18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)"); // Русский
        wikiLinks.put("nso", "18_(nomoro)"); // Sesotho sa Leboa
        wikiLinks.put("scn", "Dicidottu"); // Sicilianu
        wikiLinks.put("simple", "18_(number)"); // Simple English
        wikiLinks.put("sl", "18_(%C5%A1tevilo)"); // Slovenščina
        wikiLinks.put("srn", "Numro_18"); // Sranantongo
        wikiLinks.put("fi", "18_(luku)"); // Suomi
        wikiLinks.put("sv", "18_(tal)"); // Svenska
        wikiLinks.put("tl", "18_(bilang)"); // Tagalog
        wikiLinks.put("th", "18"); // ไทย
        wikiLinks.put("ti", "%E1%8B%93%E1%88%B0%E1%88%AD%E1%89%B0_%E1%88%BE%E1%88%98%E1%8A%95%E1%89%B0"); // ትግርኛ
        wikiLinks.put("tr", "18_(say%C4%B1)"); // Türkçe
        wikiLinks.put("uk", "18_(%D1%87%D0%B8%D1%81%D0%BB%D0%BE)"); // Українська
        wikiLinks.put("za", "Cib_bat"); // Vahcuengh
        wikiLinks.put("vep", "18_(lugu)"); // Vepsän kel’
        wikiLinks.put("vi", "18_(s%E1%BB%91)"); // Tiếng Việt
        wikiLinks.put("vls", "18_(getal)"); // West-Vlams
        wikiLinks.put("war", "18_(ihap)"); // Winaray
        wikiLinks.put("yi", "18_(%D7%A0%D7%95%D7%9E%D7%A2%D7%A8)"); // ייִדיש
        wikiLinks.put("zh", "18"); // 中文
// END OF REPLACEMENT
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
     *  &lt;a href=&quot;http://en.wikipedia.org/wiki/18_(number)&quot; target=&quot;new&quot;&gt;18&lt;/a&gt;
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

    /** Main program which reads a reference Wikipedia article
     *  and extracts the Interwiki links to various languages.
     *  The program reads the source text of this class {@link WikipediaHelper}
     *  from STDIN, replaces the body of method {@link #fillWikiLinks}
     *  and writes the resulting updated class code to STDOUT.
     *  @param args optional link to the reference article,
     *  default is "http://en.wikipedia.org/wiki/18_(number)".
     */
    public static void main(String args[]) {
        Logger log = Logger.getLogger(WikipediaHelper.class.getName());
        WikipediaHelper speller = new WikipediaHelper();
        String referenceURL = "http://en.wikipedia.org/wiki/18_(number)";
        int iarg = 0;
        if (args.length >= 1) { // take another URL from the commandline argument
            referenceURL = args[iarg ++];
        } // another
        try {
            Pattern interwikiPattern = Pattern.compile("\\s*\\<li\\s+class\\=\\\"interwiki\\-\\w+\\\"\\>\\<a\\s+"
                 + "href\\=\\\"\\/\\/(\\w+)\\.wikipedia\\.org\\/wiki\\/([^\\\"]+)\\\"[^\\>]*\\>([^\\<]+)\\<");
            BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
            String jline = "";
            boolean
            busy = true;
            while (busy && (jline = jin.readLine()) != null) { // copy part before fillWikiLinks
                System.out.println(jline);
                if (jline.startsWith("// START OF REPLACEMENT")) {
                    busy = false;
                    System.out.println("// by org.teherba.numword.WikipediaHelper at "
                            + TIMESTAMP_FORMAT.format(new java.util.Date()));
                }
            } // while part before

            // Generated body
            URIReader rin = new URIReader(referenceURL);
            String rline = "";
            while ((rline = rin.readLine()) != null) {
               Matcher matcher = interwikiPattern.matcher(rline);
                if (matcher.lookingAt()) {
                    // wikiLinks.put("pt", "Dezoito"); // Portuguese
                    System.out.println("        wikiLinks.put(\"" + matcher.group(1) + "\", \"" + matcher.group(2) + "\"); // " + matcher.group(3));
                } // if match prefix
            } // while reading
            rin.close();

            busy = true;
            while (busy && (jline = jin.readLine()) != null) { // skip over old body of method fillWikiLink
                if (jline.startsWith("// END OF REPLACEMENT")) {
                    busy = false;
                    System.out.println(jline);
                }
            } // while skipping

            busy = true;
            while (busy && (jline = jin.readLine()) != null) { // copy part behind
                System.out.println(jline);
            } // while part behind

        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
        }
    } // main

} // WikipediaHelper
