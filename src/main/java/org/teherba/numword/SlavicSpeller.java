/*  Common Methods for Slavic Lanugages
    @(#) $Id: SlavicSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04, Georg Fischer: copied from RusSpeller.java
    
    pure ASCII
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
 *  Spells numbers in slavic languages
 *  @author Dr. Georg Fischer
 */
public class SlavicSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: SlavicSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public SlavicSpeller() {
        super();
        setMaxLog(true);
        setSeparator(true);
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
                                // not "odin mil..."
                                break;
                            default:
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
            default:
                spellN0(digitN0);
                if (digitN > 0) {
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    if (digitN00 + digitN0 == 0) { 
                        switch (digitN) {
                            case 0:
                                break;
                            case 1:
                                putWord(getMorphem("t1"));
                                break;
                            case 2:
                                putWord(getMorphem("t2"));
                                break;
                            default:
                                putWord(getMorphem("t3"));
                                break;
                        }
                            
                    } 
                    else { // >= 10
                                putWord(getMorphem("t3"));
                    }
                    break;
                default:
                    spellN000(logTuple, getMorphem("m1"), getMorphem("m3"));
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /**
     *  Returns the season's name - no seasons known for slavic languages
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "1"
                    , "2"
                    , "3"
                    , "4"
                    })[season];
        }
        return result;
    }

}
