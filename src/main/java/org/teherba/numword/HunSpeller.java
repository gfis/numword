/*  Spelling of numbers in Hungarian (Magyar)
    spoken in Hungary
    @(#) $Id: HunSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-01-04, Georg Fischer: from iCalendar; UTF-8 "kettő"
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    - copied from SpaSpeller, totally incomplete
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
 *  Spells numbers in Hungarian
 *  @author Dr. Georg Fischer
 */
public class HunSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: HunSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public HunSpeller() {
        super();
        setIso639("hun,mag,hu");
        setDescription("Hungarian (Magyar)");
        setMaxLog(7);
        setSeparator("-");
        wordN = new String[]
        { "nulla" 
        , "egy"
        , "kettő"
        , "három"
        , "négy" 
        , "öt"
        , "hat"
        , "hét"
        , "nyolc"
        , "kilenc"
        };
        wordN0 = new String[]
        { "nulla"
        , "tíz"
        , "húsz"
        , "harminc"
        , "negyven"
        , "ötven"
        , "hatvan"
        , "hetven"
        , "nyolcvan"
        , "kilencven"
        };
        wordN00 = new String[]
        { "nulla"
        , "száz"
        , "kétszáz"
        , "háromszáz"
        , "négyszáz" 
        , "ötszáz"
        , "hatszáz"
        , "hétszáz"
        , "nyolcszáz"
        , "kilencszáz"
        };
        word1N = new String[]
        { "tíz"
        , "tizenegy"
        , "tizenkettő"
        , "tizenhárom"
        , "tizennégy" 
        , "tizenöt"
        , "tizenhat"
        , "tizenhét"
        , "tizennyolc"
        , "tizenkilenc"
        };
        /*
            1000 = ezer
            1001 = ezeregy
            1010 = ezertíz
            1100 = ezeregyszáz
        
            2000 = kétezer
            2001 = kétezer-egy
            2010 = kétezer-tíz
            2100 = kétezer-egyszáz
            10 000 = tízezer
            100 000 = százezer
        
            1 000 000 = millió
            1 001 100 = egymillió-ezeregyszáz
            1 000 000 000 = milliárd
            9 876 543 210 = kilencmilliárd-nyolcszázhetvenhatmillió-ötszáznegyvenháromezer-kétszáztíz
        */
    }
    
    /**
     *  Appends the wording for a triple of digits, 
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        putSeparator();
        // hundreds
        switch (digitN00) {
            case 0:
                break;
            default:
                append(wordN[digitN00]);
                append("sto");
                break;
        } // switch 100
        
        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    append(wordN[0]); // lonely 0
                }
                else 
                if (digitN != 0) {
                    if (digitN == 1) {
                        switch (logTuple) {
                            case 0:
                                append(wordN[digitN]);
                                break;
                            case 1:
                                // not "uno mil..."
                                break;
                            default:
                                append("un"); // un millione
                                break;
                        }
                    }
                    else {
                        append(wordN[digitN]);
                    }
                }
                break; 
            case 1: // 10-19
                append(word1N[digitN]);
                break;
            default:
                String tens = wordN0[digitN0];
                if (digitN == 1 || digitN == 8) { // remove trailing vowel
                    tens = tens.substring(0, tens.length() - 1);
                }
                append(tens);
                append(wordN[digitN]);
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord((singleTuple) ? "tisic" : "tisic");
                    break;
                default:
                    spellN000(logTuple);
                    append((singleTuple) ? "lione" : "lioni");
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /**
     *  Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { ""    // 0
                    , "janu\u00e1r" // 1
                    , "febru\u00e1r"// 2
                    , "m\u00e1rcius"// 3
                    , "\u00e1prilis"// 4
                    , "m\u00e1jus"  // 5
                    , "junius"      // 6
                    , "j\u00falius" // 7
                    , "augusztus"   // 8
                    , "szeptember"  // 9
                    , "okt\u00f3ber"// 10
                    , "november"    // 11
                    , "december"    // 12
                    })[month];
        }
        return result;
    }

    /**
     *  Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -&gt; Spring, 2 -&gt; Summer, 3 -&gt; Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "tavasz"
                    , "nyár"
                    , "ősz"
                    , "tél"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { ""    // 0
                    , "h\u00e9tf\u0151"// 1
                    , "kedd"            // 2
                    , "szerda"          // 3
                    , "cs\u00fct\u00f6rt\u00f6k"    // 4
                    , "p\u00e9ntek"     // 5
                    , "szombat"         // 6
                    , "vas\u00e1rnap"   // 7
                    })[weekDay];
        }
        return result;
    }

}
