/*  Spelling of numbers in Vietnamese
    spoken in Vietnam
    @(#) $Id: VieSpeller.java 521 2010-07-26 07:06:10Z gfis $
	2009-05-08: correction by Hang Yen Hoang
    2006-04-26: months
    2006-03-02, Georg Fischer: copied from TurSpeller; UTF-8 "mười"

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
*/
/*
 * Copyright 2006 Dr. Georg Fischer <dr dot georg dot fischer at gmail dot ...>
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
 *  Spells numbers in Esperanto.
 *  @author Dr. Georg Fischer
 */
public class VieSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: VieSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public VieSpeller() {
        super();
        setIso639("vie,vi");
        setDescription("Vietnamese (tiếng Việt)");
        setMaxLog(36);
        setSeparator(true);

        wordN = new String[]
        { "không"
        , "một"
        , "hai"
        , "ba"
        , "bốn"
        , "năm"
        , "sáu"
        , "bảy"
        , "tám"
        , "chín"
        };

        setMorphem("z1", "mười");
        setMorphem("h1", "trăm");
        setMorphem("t1", "ngàn"); // "ŋàn");
        setMorphem("t2", "nghìn"); //"ŋìn");
        setMorphem("m1", "triệu");
        setMorphem("m3", "tỷ");
        setMorphem("p0", " ");
        setMorphem("p1", "-");
        setMorphem("p3", "lẻ");
        enumerateMorphems();
    }
    
    /**
     *  Appends the base word for 10**3
     *  @param log1000 logarithm of 1000, = 2, 3, 4 ...
     */
    protected void spellN000(int log1000) {
        putWord(new String []
        { ""
        , ""
        , "triệu"     // 10^6
        , "tỷ"            // 10^9
    //    , "ŋàntỷ"       // 10^12
        , "ngàntỷ"       // 10^12
        , "triệutỷ"     // 10^15
        , "títỷ"     // 10^18
    //    , "ŋàn títỷ"   // 10^21
        , "ngàn títỷ"   // 10^21
        , "triệu títỷ" // 10^24
        , "titítỷ"       // 10^27
    //    , "ŋàn titítỷ" // 10^30
        , "ngàn titítỷ" // 10^30
        , "triệu titítỷ"// 10^33
        , "tịtitítỷ"   // 10^36
        }[log1000]);
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
                // putMorphem("h1");
                // break;
            default:
                spellN(digitN00);
                putMorphem("h1");
                if (digitN0 + digitN > 0) {
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
            case 1: 
                putMorphem("z1");
                if (digitN >= 1) {
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
        
        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    if (singleTuple) {
                        putMorphem("t1");
                    }
                    else {
                        putMorphem("t2");
                    }
                    break;
                default:
                    spellN000(logTuple);
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
        if (month >= 1 && month <= 12) {
            switch (month) {
                case 10:
                    result = "tháng " + getMorphem("z1");
                    break;
                case 11:
                case 12:
                    result = "tháng " + getMorphem("z1") + " " + wordN[month - 10];
                    break;
                default:
                    result = "tháng " + wordN[month];
                    break;
            } // switch 
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
                    , "mùa xuân" //, le nur bei Einern ohne Zehner, "sự nhảy" 
                    , "mùa hạ"
                    , "mùa thu"
                    , "mùa đông"
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
        switch (weekDay) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                result = "ngày thứ " + wordN[weekDay + 1];
                break;
            case 7:
                result = "ngày chủ nhật";
                break;
            default:
                break;
        } // switch
        return result;
    }

}
