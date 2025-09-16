/*  Spelling of numbers in Norwegian (Norsk)
    spoken in Norway
    @(#) $Id: NorSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-01-07: seasons, weekdays, months
    2006-01-04: UTF-8 "åtte"
    2005-06-06, Georg Fischer: copied from FinSpeller
    
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
 *  Spells numbers in Norwegian (Norgk)
 *  @author Dr. Georg Fischer
 */
public class NorSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: NorSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public NorSpeller() {
        super();
        setIso639("nor,no");
        setDescription("Norwegian (Norsk)");
        setMaxLog(true);
        setSeparator(true);

        wordN = new String[]
        { "null"
        , "ett"
        , "to"
        , "tre"
        , "fire"
        , "fem"
        , "seks"
        , "syv"  // "sju" ???
        , "åtte"
        , "ni"
        };
        wordN0 = new String[]
        { "null"
        , "ti"
        , "tyve"
        , "tretti"
        , "firti"
        , "femti"
        , "seksti"
        , "sytti"
        , "åtti"
        , "nitti"
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

        setMorphem("01", "en");
        setMorphem("018", "atten");
        setMorphem("h1", "hundre");
        setMorphem("t1", "tusen");
        setMorphem("m1", "lion");
        setMorphem("m3", "liar");
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
                putWord("hundre");
                if (digitN0 != 0 || digitN != 0) {
                    putMorphem("p3");
                }
                break;
            default:
                spellN(digitN00);
                putWord("hundre");
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
                spellN0(digitN0);
                if (digitN >= 1 && (! singleTuple || logTuple <= 0)) {
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord("tusen");
                    break;
                default:
                    spellN000(logTuple, "lion", "liar");
                /*
                    spellN000(logTuple / 2 + 1);
                    if (logTuple % 2 == 0) { // even
                        append("on");
                        if (! singleTuple) {
                            append("ar"); // zweimillion"en"
                        }
                    }
                */
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
                    { "M\u00e5nad"  // 0
                    , "Januari" // 1
                    , "Februari"    // 2
                    , "Mars"    // 3
                    , "April"   // 4
                    , "Maj" // 5
                    , "Juni"    // 6
                    , "Juli"    // 7
                    , "Augusti" // 8
                    , "September"   // 9
                    , "Oktober" // 10
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
                    , "vår"
                    , "sommer"
                    , "høst"
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
                    , "M\u00e5ndag" // 1
                    , "Tisdag"  // 2
                    , "Onsdag"  // 3
                    , "Torsdag" // 4
                    , "Fredag"  // 5
                    , "L\u00f6rdag" // 6
                    , "S\u00f6ndag" // 7
                    })[weekDay];
        }
        return result;
    }

}
