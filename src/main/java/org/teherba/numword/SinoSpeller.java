/*  Common Methods for Spelling of numbers in Chines, Japanese, Korean, Thai
    spoken in Far East Asia 六
    @(#) $Id: SinoSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2016-02-15: spellIdeographic(number)
    2006-01-06: Georg Fischer, copied from ChiSpeller 
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    tested < 1 000 000 000 000
    Retrieved from "http://home.unilang.org/main/wiki2/index.php/Translations:_Numbers_-_Greek"
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
/** Common methods for the spelling of numbers in Far East Asian languages
 *  @author Dr. Georg Fischer
 */
public abstract class SinoSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: SinoSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /** No-args Constructor
     */
    public SinoSpeller() {
        super();
        // setIso639("chi,zho,zh"); 
        // setDescription("Chinese (中文 (zhōngwén))");
        setLogTuple(4); // grouping for base 10000
        setMaxLog(12); // < 10000 * 10000 * 10000
        setSeparator(false);

        wordN = new String[]
        { "零"     //  0 (líng)
        , "一"     //  1 (yī)
        , "二"     //  2 (èr)
        , "三"     //  3 (sān)
        , "四"     //  4 (sì)
        , "五"     //  5 (wŭ)
        , "六"     //  6 (lìu)
        , "七"     //  7 (qī)
        , "八"     //  8 (bā)
        , "九"     //  9 (jĭu)
        };
    
        wordN0 = new String[]
        { "十"
        };
        setMorphem("z1", "十");    // (shí)
        setMorphem("h1", "百");    // (băi)
        setMorphem("t1", "千");    // (yī-qīan)
        setMorphem("tz", "万");    // (yī-wàn)
        setMorphem("mh", "亿");    // (yī-yì)
        setMorphem("wm", "月");    // "month" = yuè
        enumerateMorphems();
    } // Constructor
    
    /** Appends the wording for a triple of digits, 
     *  plus the remaining power of 1000
     *  @param number the remaining part of the whole number
     */
    public void spellTuple(String number) {
        // thousands
        switch (digitN000) {
            case 0:
                break;
            default:
                if (digitN000 >= 1) {
                    spellN(digitN000);
                }
                putMorphem("t1");
                break;
        } // switch 1000

        // hundreds
        switch (digitN00) {
            case 0:
                break;
            default:
                if (digitN00 > 1) {
                    spellN(digitN00);
                }
                putMorphem("h1");
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
            case 1: // 10..19
                putMorphem("z1");
                if (digitN > 0) {
                    spellN(digitN);
                }
                break; 
            default:
                spellN(digitN0);
                putMorphem("z1");
                if (digitN >= 1) {
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        switch (logTuple) {
            case 0: // no 10000s
                break;
            case 1: 
                if (! zeroTuple) {
                    putMorphem("tz");
                }
                break;
            case 2: 
                if (! zeroTuple) {
                    putMorphem("mh");
                }
                break;
            default:
                // not reached since maxLog = 
                break;
        } // switch logTuple
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
        result.setLength(0);
        int chpos = 0;
        while (chpos < number.length()) {
            switch (Character.digit(number.charAt(chpos), 10)) {
                default:
                case 0: result.append('\u96f6'); break;
                case 1: result.append('\u4e00'); break;
                case 2: result.append('\u4e8c'); break;
                case 3: result.append('\u4e09'); break;
                case 4: result.append('\u56db'); break;
                case 5: result.append('\u4e94'); break;
                case 6: result.append('\u516d'); break;
                case 7: result.append('\u4e03'); break;
                case 8: result.append('\u516b'); break;
                case 9: result.append('\u4e5d'); break;
            } // switch
            chpos ++;
        } // while chpos
        return result.toString();
    } // spellIdeographic

    /** Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 1 && month <= 12) {
            result += getMorphem("wm");
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
                    , "季"
                    , "季"
                    , "季"
                    , "季"
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
            result = "星期" + wordN[weekDay] ; //  "星期一"    xīngqi yī weekday 1
        }
        return result;
    } // spellWeekDay

} // SinoSpeller
