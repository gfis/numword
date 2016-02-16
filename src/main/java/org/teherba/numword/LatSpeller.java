/*  Spelling of numbers in Latinum (Latin)
    formerly spoken in the ancient Roman Empire (western Europe),
    by the clerical and scientific people in the Middle Ages,
    and by priests of the Roman Catholic Church until today
    @(#) $Id: LatSpeller.java 657 2011-03-17 07:56:38Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2016-02-15: spellIdeographic(number) 
    2006-01-06: super, "lat"
    2005-06-01, Georg Fischer
    
    pure ASCII encoding
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
import  org.teherba.numword.LatSpeller;

/** Spells numbers in Latin (Latinum)
 *  @author Dr. Georg Fischer
 */
public class LatSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: LatSpeller.java 657 2011-03-17 07:56:38Z gfis $";
    
    /** Constructor
     */
    public LatSpeller() {
        super();
        setIso639("lat,la");
        setDescription("Latin (Latinum)");
        setMaxLog(6);
        setSeparator(true);

        wordN = new String[]
        { "nihil" // nullus?
        , "unum"
        , "duo"
        , "tres"
        , "quattuor"
        , "quinque"
        , "sex"
        , "septem"
        , "octo"
        , "novem"
        };
        wordN0 = new String[]
        { ""
        , "decem"
        , "viginti"
        , "triginta"
        , "quadraginta"
        , "quinquaginta"
        , "sexaginta"
        , "septuaginta"
        , "octoginta"
        , "nonaginta"
        , "centum" // for 98 = "duode...", 99 = "unde..."
        };
        word1N = new String[]
        { "decem"
        , "undecim"
        , "duodecim"
        , "tredecim"
        , "quattuordecim"
        , "quindecim"
        , "sedecim"
        , "septendecim"
        , "duodeviginti"
        , "undeviginti" 
        };
        wordN00 = new String[]
        { ""
        , "centum"
        , "ducenti"
        , "trecenti"
        , "quadringenti"
        , "quingenti"
        , "sescenti"
        , "septingenti"
        , "octingenti"
        , "nongenti"
        };

        setMorphem("-1", "unde");
        setMorphem("-2", "duode");
        setMorphem("l1", "mille milia");
        setMorphem("l2", "milia milia"); // ???
        setMorphem("m1", "mille milia");
        setMorphem("t1", "mille");
        setMorphem("t2", "milia");
        setMorphem("p0", " ");
        enumerateMorphems();
    } // Constructor
    
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
                spellN00(digitN00);
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0); // lonely 0
                }
                else 
                if (digitN > 0 && (digitN != 1 || logTuple != 1)) {
                    // not "unum mille"
                    spellN(digitN);
                }
                break; 
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                switch (digitN) {
                    case 8:
                        putWord("duode");
                        append(wordN0[digitN0 + 1]);
                        break;
                    case 9:
                        putWord("unde");
                        append(wordN0[digitN0 + 1]);
                        break;
                    default:
                        spellN0(digitN0);
                        if (digitN >= 1) {
                            spellN (digitN);
                        }
                        break;
                } // switch digitN
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    if (singleTuple) {
                        putWord("mille");
                    }
                    else {
                        putWord("milia");
                    }
                    break;
                default: 
                    // ??? 10**6 = mille milia
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /** Returns a sequence of digits as symbols in that language's script.
     *  Usually the digits are big-endian, that is the 
     *  digit with lowest value is rightmost.
     *  @param number a sequence of digit characters, maybe
     *  interspersed with non-digits (spaces, punctuation).
     *  @return null if the language does not spport ideographic numbers,
     *  or a sequence of digits in that language's script.
     */
    public String spellIdeographic(String number) {
        return (new RomanSpeller()).spellCardinal(number);
    } // spellIdeographic

    /** Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "mensis"
                    , "Januarius"
                    , "Februarius"
                    , "Martius"
                    , "Aprilis"
                    , "Maius"
                    , "Junius"
                    , "Julius"
                    , "Augustus"
                    , "September"
                    , "October"
                    , "November"
                    , "December"
                    })[month];
        }
        return result;
    } // spellMonthName

    /** Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { ""
                    , "ver"
                    , "aestas"
                    , "autumnus"
                    , "hiems"
                    })[season];
        }
        return result;
    } // spellSeason

    /** Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { ""
                    , "dies lunae"
                    , "dies Martis"
                    , "dies Mercurii"
                    , "dies Jovis"
                    , "dies Veneris"
                    , "dies Saturni"
                    , "dies Solis"
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

} // LatSpeller 
