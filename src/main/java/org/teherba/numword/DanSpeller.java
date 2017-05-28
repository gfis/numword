/*  Spelling of numbers in Danish (Dansk)
    spoken in Denmark, Greenland
    @(#) $Id: DanSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-01: weekdays and months; UTF-8 "Lørdag"
    2005-06-08, Georg Fischer: copied from NorSpeller
    
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
/**
 *  Spells numbers in Danish (Danks)
 *  @author Dr. Georg Fischer
 */
public class DanSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: DanSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public DanSpeller() {
        super();
        setIso639("dan,da");
        setDescription("Danish (Dansk)");
        setMaxLog(false);
        setSeparator(false);
        
        wordN = new String[]
        { "nul"
        , "en"
        , "to"
        , "tre"
        , "fire"
        , "fem"
        , "seks"
        , "syv"
        , "otte"
        , "ni"
        };
        wordN0 = new String[]
        { "nul"
        , "ti"
        , "tyve"
        , "tredive"     // "treti"
        , "fyrre"       // "firti"
        , "halvtreds"   // "femti"
        , "tres"        // "seksti"
        , "halvfjerds"  // "syvti"
        , "firs"        // "otti"
        , "halviems"    // "niti"
        };
        word1N = new String[]
        { "ti"
        , "elleve"
        , "tolv"
        , "tretten"
        , "fjorten"
        , "femten"
        , "seksten"
        , "sytten"
        , "åtten"
        , "nitten"
        };

        setMorphem("h1", "hundrede");
        setMorphem("t1", "tusind");
        setMorphem("m1", "lion");
        setMorphem("m2", "lionar");
        setMorphem("m3", "liard");
        setMorphem("m4", "liardar");
        setMorphem("p0", " ");
        setMorphem("p3", "og");
        enumerateMorphems();
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
                putMorphem("h1");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3");
                }
                break;
            default:
                spellN(digitN00);
                putMorphem("h1");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3");
                }
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                }
                else
                if (digitN >= 1) {
                    spellN(digitN);
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
                    append(" ");
                    spellN000Morphem(logTuple);
                    append(" ");
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
                    { "Monat"   // 0
                    , "Januar"  // 1
                    , "Februar" // 2
                    , "Marts"   // 3
                    , "April"   // 4
                    , "Maj"     // 5
                    , "Juni"    // 6
                    , "Juli"    // 7
                    , "August"  // 8
                    , "September"   // 9
                    , "Oktober"     // 10
                    , "November"    // 11
                    , "December"    // 12
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
                    , "forår"
                    , "sommer"
                    , "efterår"
                    , "vinter"
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
                    { "0"
                    , "Mandag"
                    , "Tirsdag"
                    , "Onsdag"
                    , "Torsdag"
                    , "Fredag"
                    , "Lørdag"
                    , "Søndag"
                    })[weekDay];
        }
        return result;
    }
}
