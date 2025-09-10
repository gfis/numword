/*  Spelling of numbers in Italian (Italiano)
    spoken in Italy
    @(#) $Id: ItaSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-04-15: corr. with Vladimiro Macchi
    2006-01-04: weekdays and months; UTF-8 "tré"
    2005-06-07, Georg Fischer

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    - No separator, except before and after words for 10**6.
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
 *  Spells numbers in Italian (Italiano)
 *  @author Dr. Georg Fischer
 */
public class ItaSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: ItaSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     * word for 3 (with accent grave) if behind a bigger number
     */
    private static final String TRE = "tré"; 
         
    /**
     *  Constructor
     */
    public ItaSpeller() {
        super();
        setIso639("ita,it");
        setDescription("Italian (Italiano)");
        setMaxLog(false);
        setSeparator(true);

        wordN = new String[]
        { "zero" 
        , "uno"
        , "due"
        , "tre"
        , "quattro"
        , "cinque"
        , "sei"
        , "sette"
        , "otto"
        , "nove"
        };
        word1N = new String[]
        { "dieci"
        , "undici"
        , "dodici"
        , "tredici"
        , "quattordici"
        , "quindici"
        , "sedici"
        , "diciassette"
        , "diciotto"
        , "diciannove"
        };
        wordN0 = new String[]
        { ""
        , "dieci"
        , "venti"
        , "trenta"
        , "quaranta"
        , "cinquanta"
        , "sessanta"
        , "settanta"
        , "ottanta"
        , "novanta"
        };
        // tricky: leading zero makes alternate key, but yields same numerical value
        setMorphem("01", "un");
        setMorphem("03", TRE); 
        for (int iw = 1; iw < wordN0  .length; iw ++) { // remove trailing vowel
            morphMap.put("0" + String.valueOf(iw *  10)
                    , wordN0 [iw].substring(0, wordN0[iw].length() - 1));
        }
        
        setMorphem("h1", "cento");
        setMorphem("t1", "mille");
        setMorphem("t2", "mila");
        setMorphem("m1", "lione");
        setMorphem("m2", "lioni");
        setMorphem("p0", " ");
        enumerateMorphems();
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
            case 1:
                append(getMorphem("h1"));
                break;
            default:
                append(wordN[digitN00]);
                append("cento");
                break;
        } // switch 100
        
        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    append(wordN[0]); // lonely 0
                }
                else {
                    switch (digitN) {
                        case 0:
                            break;
                        case 1:
                            switch (logTuple) {
                                case 0:
                                    append(wordN[digitN]);
                                    break;
                                case 1:
                                    // not "uno mil..."
                                    break;
                                default:
                                    append(getMorphem("01")); // un millione
                                    break;
                            }
                            break;
                        case 3:
                            if (digitN00 == 0) {
                                append(wordN[digitN]);
                            }
                            else {
                                append(TRE);
                            }
                            break;
                        default:
                            append(wordN[digitN]);
                            break;
                    } // switch digitN
                } // not nullOnly
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
                switch (digitN) {
                    case 0:
                        break;
                    case 3:
                        append(TRE);
                        break;
                    default:
                        append(wordN[digitN]);
                        break;
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putWord((singleTuple) ? getMorphem("t1") : getMorphem("t2"));
                    break;
                default:
                    spellN000(logTuple);
                    append( (singleTuple) ? getMorphem("m1") : getMorphem("m2"));
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
                    { "mese"        // 0
                    , "gennaio"     // 1
                    , "febbraio"    // 2
                    , "marzo"       // 3
                    , "aprile"      // 4
                    , "maggio"      // 5
                    , "giugno"      // 6
                    , "luglio"      // 7
                    , "agosto"      // 8
                    , "settembre"   // 9
                    , "ottobre"     // 10
                    , "novembre"    // 11
                    , "dicembre"    // 12
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
                    , "primavera"
                    , "estate"
                    , "autunno"
                    , "inverno"
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
                    , "lunedì"     // 1
                    , "martedì"        // 2
                    , "mercoledì"  // 3
                    , "giovedì"        // 4
                    , "venerdì"        // 5
                    , "sabato"      // 6
                    , "domenica"    // 7
                    })[weekDay];
        }
        return result;
    }

}
