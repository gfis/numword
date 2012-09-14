/*  Spelling of numbers in Deutsch (German)
    spoken in Germany, Austria
    and parts of Switzerland(Deutsch-Schweiz), France (Elsaß), Italy (Südtirol)
    @(#) $Id: DeuSpeller.java 852 2012-01-06 08:07:08Z gfis $
    2011-10-26: spellClock; Mar.L. = 92
    2009-11-24: spellGreeting
    2006-07-27: alias morphems for "drit", 5, 12, 15, 30, 50 (expanded umlauts)
                overlay of method 'parseString' for equalized declination endings
    2006-01-04: encoding UTF-8 "fünf"
    2005-06-01, Georg Fischer

    caution: UTF-8 is essential! compile with "-encoding UTF-8"

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

/** Spells numbers in German (Deutsch)
 *  @author Dr. Georg Fischer
 */
public class DeuSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: DeuSpeller.java 852 2012-01-06 08:07:08Z gfis $";

    /** Constructor
     */
    public DeuSpeller() {
        super();
        setIso639("deu,ger,de");
        setDescription("German (Deutsch)");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "null"
        , "ein"
        , "zwei"
        , "drei"
        , "vier"
        , "fünf"
        , "sechs"
        , "sieben"
        , "acht"
        , "neun"
        };
        wordN0 = new String[]
        { ""
        , "zehn"
        , "zwanzig"
        , "dreißig"
        , "vierzig"
        , "fünfzig"
        , "sechzig"
        , "siebzig"
        , "achtzig"
        , "neunzig"
        };
        word1N = new String[]
        { "zehn"
        , "elf"
        , "zwölf"
        , "dreizehn"
        , "vierzehn"
        , "fünfzehn"
        , "sechzehn"
        , "siebzehn"
        , "achtzehn"
        , "neunzehn"
        };

        // adapt to German consonants k, z instead of c
        for (int iw = 2; iw < wordN000.length; iw ++) {
            wordN000[iw] = wordN000[iw]
                            .replaceAll("deci", "dezi")
                            .replaceAll("oct" , "okt" );
            wordN000[iw] = Character.toUpperCase(wordN000[iw].charAt(0))
                        + wordN000[iw].substring(1);
        } // for iw

        setMorphem("h1", "hundert");
        setMorphem("t1", "tausend");
        setMorphem("m1", "lion");
        setMorphem("m2", "lionen");
        setMorphem("m3", "liarde");
        setMorphem("m4", "liarden");
        setMorphem("p0", " ");
        // setMorphem("p1", "s"); // for ein"s"?
        setMorphem("p2", "e");
        setMorphem("p3", "und");
        enumerateMorphems();
        // tricky: leading zeroes make alternate keys = aliases for the same numerical value
        setMorphem("01" , "eins");
        setMorphem("001", "erst"); // c.f. this.parseString(,)
        setMorphem("02" , "zwo");
        setMorphem("03" , "dritt"); // c.f. this.parseString(,)
        setMorphem("05" , "fuenf");
        setMorphem("07" , "sieb");
        setMorphem("012", "zwoelf");
        setMorphem("015", "fuenfzehn");
        setMorphem("030", "dreissig");
        setMorphem("050", "fuenfzig");
    } // Constructor

    /** Overlay for special treatment of "dritt(el)" and "acht(el)"
     *  and for "s" after "zwanzig";
     *  in order to equalize "er", "tens", "ter", "te",
     *  "ten", "tem", "tes" endings;
     *  parses a string, and returns a digit sequence if
     *  it starts with a number word;
     *  always tries to match a longest prefix;
     *  @param text string to be parsed
     *  @param offset offset in <em>text</em> where to start parsing
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset) {
        offset = super.parseString(text, offset);
        if (offset > 0) {
            // an additional "e" was eaten as "p2" morphem (to be ignored)
            if  (text.substring(0, offset).endsWith("e")) {
                offset --; // keep "e" behind number word
            }
            if  (   text.substring(offset).startsWith("e")
                &&  (   text.substring(0, offset).endsWith("dritt")
                    ||  text.substring(0, offset).endsWith("acht")
                    ||  text.substring(0, offset).endsWith("erst")
                    )                           // but not "hundert"
                )
            {
                offset --; // keep "t" behind number word
            }
            else
            if  (   text.substring(offset).startsWith("ste")
                &&  (   text.substring(0, offset).endsWith("ig")
                    ||  getResult().length() > 2 // numeric value >= 100
                    )
                )
            {
                offset ++; // eat "s"
            }
        }
        return offset;
    } // parseString

    /** Appends the wording for a triple of digits,
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // hundreds
        switch (digitN00) {
            case 0:
                break;
            default:
                spellN(digitN00);
                putMorphem("h1");
                break;
        } // switch 100

        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                } else if (digitN >= 1) {
                    spellN(digitN);
                    if (number.length() == 0 && digitN == 1) {
                        append("s"); // dreihundertein"s"
                    }
                }
                break;
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                if (digitN >= 1) {
                    spellN(digitN);
                    putMorphem("p3");
                }
                spellN0(digitN0);
                break;
        } // switch digitN0

        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putMorphem("t1");
                    break;
                default:
                    if (singleTuple) {
                        append("e"); // ein"e"million ...
                    }
                    append(" ");
                    spellN000Morphem(logTuple);
                    append(" ");
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    //================================================================
    /** Pairs of German month numbers, month names and their abbreviations */
    private String months[] = new String[]
            { "00", "Monat"       // 0
            , "01", "Januar"      // 1
            , "02", "Februar"     // 2
            , "03", "März"        // 3
            , "04", "April"       // 4
            , "05", "Mai"         // 5
            , "06", "Juni"        // 6
            , "07", "Juli"        // 7
            , "08", "August"      // 8
            , "09", "September"   // 9
            , "10", "Oktober"     // 10
            , "11", "November"    // 11
            , "12", "Dezember"    // 12
            // abbreviations (3 letters)
            , "01", "Jan"
            , "02", "Feb"
            , "03", "Mär"
            , "04", "Apr"
            , "05", "Mai"
            , "06", "Jun"
            , "07", "Jul"
            , "08", "Aug"
            , "09", "Sep"
            , "10", "Okt"
            , "11", "Nov"
            , "12", "Dez"
            // aliases and other abbreviations
            , "01", "Jänner"
            , "01", "Jaenner"
            , "03", "Maerz"
            , "02", "Febr"
            , "09", "Sept"
            };

    /** Returns the month's number as String (between "01" and "12")
     *  @param name name of the month, or an alias or abbreviation
     *  @return number between "01" and "12", or null if not found
     */
    public String parseMonth(String name) {
        return lookupWord(name, months);
    } // parseMonth

    /** Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        return (month >= 0 && month <= 12)
                ? months[month * 2 + 1]
                : Integer.toString(month);
    } // spellMonth

    //================================================================
    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String []
                    { "Jahreszeit"
                    , "Frühling"
                    , "Sommer"
                    , "Herbst"
                    , "Winter"
                    })[season];
        }
        return result;
    } // spellSeason

    //================================================================
    /** Pairs of German weekday numbers, weekday names and their abbreviations */
    private String weekdays[] = new String[]
            { "00", "Wochentag"
            , "01", "Montag"
            , "02", "Dienstag"
            , "03", "Mittwoch"
            , "04", "Donnerstag"
            , "05", "Freitag"
            , "06", "Samstag"
            , "07", "Sonntag"
            , "01", "Mo"
            , "02", "Di"
            , "03", "Mi"
            , "04", "Do"
            , "05", "Fr"
            , "06", "Sa"
            , "07", "So"
            };

    /** Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String []
                    { "Wochentag"
                    , "Montag"
                    , "Dienstag"
                    , "Mittwoch"
                    , "Donnerstag"
                    , "Freitag"
                    , "Samstag"
                    , "Sonntag"
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

    //================================================================
    /** Returns a denotation of the day's time, possibly in several variants
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @param variant the code behind the commandline option "-h":
     *  empty or 0 (official), 1,2,3 for  a language specific variant.
     *  @return phrase corresponding to the denotation of the time, for example
     *  <ul>
     *  <li>German, variant "1" = western:  18:45 =&gt; "viertel vor sieben"</li>
     *  <li>German, variant "2" = southern: 18:45 =&gt; "dreiviertel sieben"</li>
     *  </ul>
     */
    public String spellClock(int hour, int minute, String variant) {
        String result = String.valueOf(hour   + 100).substring(1) + ':'
                      + String.valueOf(minute + 100).substring(1);
        int hour12 = hour + (minute >= 30 ? 1 : 0);
        hour12 = (hour12 == 0 ? 12 : (hour12 >= 13 ? hour12 - 12 : hour12));
        String spellHour = spellCardinal(String.valueOf(hour12));
        if (false) {
        } else if (variant.length() == 0 || variant.equals("0")) {
            result  = spellCardinal(String.valueOf(hour)).replace("eins", "ein")
                    + " Uhr";
            if (minute > 0) {
                result += " " +spellCardinal(String.valueOf(minute));
            }
        } else if (variant.equals("1")) { // Western Germany, north/west of a line Luebeck/Saaarbruecken (c.f. de.wikipedia: Uhrzeit)
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        result  = "viertel nach "   + spellHour;
                        break;
                    case 2:
                        result  = "halb "           + spellHour;
                        break;
                    case 3:
                        result  = "viertel vor "    + spellHour;
                        break;
                } // switch 0..3
            } else {
                result  = (minute < 30  ? spellCardinal(String.valueOf(     minute)) + " nach "
                                        : spellCardinal(String.valueOf(60 - minute)) + " vor ")
                        + spellHour;
            }
        } else if (variant.equals("2")) { // Southern Germany, south/east of a line Luebeck/Saaarbruecken (c.f. de.wikipedia: Uhrzeit)
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        hour12 ++;
                        spellHour = spellCardinal(String.valueOf(hour12));
                        result  = "viertel "        + spellHour;
                        break;
                    case 2:
                        result  = "halb "           + spellHour;
                        break;
                    case 3:
                        result  = "dreiviertel "    + spellHour;
                        break;
                } // switch 0..3
            } else {
                result  = (minute < 30  ? spellCardinal(String.valueOf(     minute)) + " nach "
                                        : spellCardinal(String.valueOf(60 - minute)) + " vor ")
                        + spellHour;
            }
        } // switch variant
        return result;
    } // spellClock(3)

    //================================================================
    /** Get a word for one the 4 cardinal directions,
     *  and for the particle for 32th fractions
     *  @param cardDir a cardinal direction, 0 = North, 1 = East, 2 = South, 3 = West
     */
    protected String getCompassWord(int cardDir) {
        String result = "";
        switch (cardDir) {
            case 0: result = "nord"     ; break;
            case 1: result = "ost"      ; break;
            case 2: result = "süd"      ; break;
            case 3: result = "west"     ; break;
            case 4: result = "zu"       ; break;
        } // switch
        return result;
    } // getCompassWord

    /** Returns the language specific words for a cardinal direction
     *  @param code abbreviation, a sequence of the letters N,E,S,W.
     */
    protected String spellCompassCode(String code, String words[]) {
        String result = super.spellCompassCode(code);
        if (code.length() == 4) {
            // result += "en";
        }
        return result;
    } // spellCompassCode

    /** Returns a greeting corresponding to the parameter time:
     *  @return greeting corresponding to the time of the day
     */
    public String spellGreeting(int timeOfDay) {
        String result = "Guten Tag";
        timeOfDay /= 6;
        if (timeOfDay >= 0 && timeOfDay <= 4) {
            result = (new String[]
                    { "Auf Wiedersehen"
                    , "Guten Morgen"
                    , "Guten Tag"
                    , "Guten Abend"
                    , "Gute Nacht"
                    }
                    )[timeOfDay];
        } // in range
        return result;
    } // spellGreeting(int)

    //================================================================
    /** Returns a planet
     *  @param planet number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
        String result = super.spellPlanet(planet);
        switch (planet) {
            case -1:    result = "Mond";        break;
            case 0:     result = "Sonne";       break;
            case 1:     result = "Merkur";      break;
			case 2:		result = "Venus";		break;
            case 3:     result = "Erde";        break;
			case 4:		result = "Mars";		break;
			case 5:		result = "Jupiter";		break;
			case 6:		result = "Saturn";		break;
			case 7:		result = "Uranus";		break;
			case 8:		result = "Neptun";		break;
        } // switch
        return result;
    } // spellPlanet(int)

} // DeuSpeller
