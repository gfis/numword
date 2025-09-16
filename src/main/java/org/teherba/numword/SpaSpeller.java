/*  Spelling of numbers in Spanish (Español, Catalan)
    spoken in Spain, all countries of Middle and South America (except Brasilia),
    the Philipines and other former Spanish colonies
    @(#) $Id: SpaSpeller.java 820 2011-11-07 21:59:07Z gfis $
    2011-11-06: spellClock; Dresden -> 
    2006-01-01: weekdays and months
    2005-06-07, Georg Fischer
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"

*/
/*
 * Copyright 2005 Dr. Georg Fischer <dr dot georg dot fischer at gmail dot ...>
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
/**
 *  Spells numbers in Spanish (Español)
 *  @author Dr. Georg Fischer
 */
public class SpaSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: SpaSpeller.java 820 2011-11-07 21:59:07Z gfis $";

    /**
     *  Constructor
     */
    public SpaSpeller() {
        super();
        setIso639("spa,es");
        setDescription("Spanish (Español)");
        setMaxLog(false);
        setSeparator(true);

        wordN = new String[]
        { "cero" 
        , "uno"
        , "dos"
        , "tres"
        , "cuatro"
        , "cinco"
        , "seis"
        , "siete"
        , "ocho"
        , "nueve"
        };
        wordN0 = new String[]
        { ""
        , "diez"
        , "veinte"
        , "treinta"
        , "cuarenta"
        , "cincuenta"
        , "sesenta"
        , "setenta"
        , "ochenta"
        , "noventa"
        };
        wordN00 = new String[]
        { ""
        , "ciento"
        , "doscientos" // docientas
        , "trescientos" // trecientos
        , "cuatrocientos"
        , "quinientos" // quincientos
        , "seiscientos"
        , "setecientos"
        , "ochocientos"
        , "novecientos"
        };
        word1N = new String[]
        { "diez"
        , "once"
        , "doce"
        , "trece"
        , "catorce"
        , "quince"
        , "dieciséis"
        , "diecisiete"
        , "dieciocho"
        , "diecinueve"
        };

        // alternate spellings
        setMorphem("01"     , "un");
        setMorphem("02"     , "dós");
        setMorphem("03"     , "trés");
        setMorphem("06"     , "séis");
        setMorphem("020"    , "veinti");
        setMorphem("0200"   , "docientas");
        setMorphem("0300"   , "trecientos");
        setMorphem("0500"   , "quinientos");
        
        setMorphem("t1", "mil");
        setMorphem("m1", "lón");
        setMorphem("m2", "lones");
        setMorphem("p0", " ");
        setMorphem("p1", "s");
        // setMorphem("p2", "i");
        setMorphem("p2", "y");
    /*  
        for (int iw = 1; iw < wordN0  .length; iw ++) { // remove trailing vowel
            morphMap.put("0" + String.valueOf(iw *  10)
                    , wordN0 [iw].substring(0, wordN0[iw].length() - 1));
        }
    */
        enumerateMorphems();
        // "li" must be set after 'enumerateMorphems'
        removeMorphem("e02");
        setMorphem("l1", "millón"); 
        setMorphem("l2", "millones");
    }
    
    /**
     *  Appends the wording for a triple of digits, 
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // hundreds
        switch (digitN00) {
            case 0:
                break;
            case 1:
                spellN00(digitN00);
                break;
            default:
                spellN00(digitN00);
            /*
                if (digitN != 0 || digitN0 != 0) {
                    append("s");
                }
            */
                break;
        } // digitN00
        
        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    putWord(wordN[0]); // lonely 0
                }
                else 
                if (digitN != 0) {
                    if (digitN == 1) {
                        switch (logTuple) {
                            case 0:
                                spellN(digitN);
                                break;
                            case 1:
                                // not "uno mil..."
                                break;
                            default:
                                putWord("un"); // un millione
                                break;
                        }
                    }
                    else {
                        spellN(digitN);
                    }
                }
                break; 
            case 1: // 10-19
                spell1N(digitN);
                break;
            case 2: // 20-29
                if (digitN > 0) {
                    putWord(getMorphem("020")); // veinti
                    switch (digitN) {
                        case 2:
                        case 3:
                        case 6:
                            append(getMorphem("0" + String.valueOf(digitN))); // dós, trés, séis
                            break;
                        default:
                            append(wordN[digitN]);
                            break;
                    } // switch digitN
                }
                else {
                    spellN0(digitN0); // veinte
                }   
                break;
            default:
                spellN0(digitN0);
                if (digitN > 0) {
                    putWord("y");
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord((singleTuple) ? "mil" : "mil");
                    break;
                default:
                    spellN000(logTuple);
                    append((singleTuple) ? "lón" : "lones");
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /** Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "mes" // 0
                    , "enero"   // 1
                    , "febrero" // 2
                    , "marzo"   // 3
                    , "abril"   // 4
                    , "mayo"    // 5
                    , "junio"   // 6
                    , "julio"   // 7
                    , "agosto"  // 8
                    , "septiembre"  // 9
                    , "octubre" // 10
                    , "noviembre"   // 11
                    , "diciembre"   // 12
                    })[month];
        }
        return result;
    } // spellMonth

    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "primavera"
                    , "verano"
                    , "otoño"
                    , "invierno"
                    })[season];
        }
        return result;
    } // spellSeason

    /** Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "0"
                    , "lunes"   // 1
                    , "martes"  // 2
                    , "mi\u00e9rcoles"  // 3
                    , "jueves"  // 4
                    , "viernes" // 5
                    , "s\u00e1bado" // 6
                    , "domingo" // 7
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

    /** Returns a denotation of the day's time, possibly in several variants
     *  @param hour hour 0..24
     *  @param minute minute 0..59
     *  @param variant the code behind the commandline option "-h":
     *  empty or 0 (official), 1,2,3 for  a language specific variant.
     *  @return phrase corresponding to the denotation of the time, for example
     *  <ul>
     *  <li>Français, variant "1": 18:15 =&gt; "six et quart"</li>
     *  </ul> 
     */
    public String spellClock(int hour, int minute, String variant) {
        String result = String.valueOf(hour   + 100).substring(1) + ':' 
                      + String.valueOf(minute + 100).substring(1);
        int hour12 = hour + (minute > 30 ? 1 : 0);
        hour12 = (hour12 == 0 ? 12 : (hour12 >= 13 ? hour12 - 12 : hour12)); 
        String spellHour = (spellCardinal(String.valueOf(hour12))
                + " hora"
                + (hour12 > 1 ? "s" : "") 
                ).replaceAll("uno horas?", "una hora");
        if (false) {
        } else if (variant.length() == 0 || variant.equals("0")) {
            result  = (spellCardinal(String.valueOf(hour)).replace("zero", "zéro")
                    + " hora"
                    + (hour > 1 ? "s" : "")
                ).replaceAll("uno horas?", "una hora");
            if (minute > 0) {
                result += " " + spellCardinal(String.valueOf(minute)); 
            }
        } else if (variant.equals("1")) { 
            if (minute % 15 == 0) {
                switch (minute / 15) {
                    default:
                    case 0:
                        result  = spellHour;
                        break;
                    case 1:
                        result  = spellHour + " y quarto";
                        break;
                    case 2:
                        result  = spellHour + " y media";
                        break;
                    case 3:
                        result  = spellHour + " minus quarto";
                        break;
                } // switch 0..3
            } else {
                result  = spellHour 
                        + (minute < 30  ? " y "     + spellCardinal(String.valueOf(     minute))
                                        : " minus " + spellCardinal(String.valueOf(60 - minute))) 
                        ;
            }
        } // switch variant
        return result;
    } // spellClock(3)

    //================================================================
    /** Returns a planet
     *  @param planet number of the planet (3 = earth, 0 = sun, -1 = moon)
     *  @return planet's name
     */
    public String spellPlanet(int planet) {
        String result = super.spellPlanet(planet);
        switch (planet) {
            case -1:    result = "Luna";        break;
            case 0:     result = "Sol";         break;
            case 1:     result = "Mercurio";    break;
            case 2:     result = "Venus";       break;
            case 3:     result = "Tierra";      break;
            case 4:     result = "Marte";       break;
            case 5:     result = "Jupiter";     break;
            case 6:     result = "Saturno";     break;
            case 7:     result = "Urano";       break;
            case 8:     result = "Neptuno";     break;
        } // switch
        return result;
    } // spellPlanet(int)

} // SpaSpeller
