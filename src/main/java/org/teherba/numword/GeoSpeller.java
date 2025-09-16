/*  Spelling of numbers in Georgian
    spoken in Georgia
    @(#) $Id: GeoSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-01-11, Georg Fischer: copied from DeuSpeller

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    40,60,80: m davor
    100: keine 1 davor
    bis 1000 keine Leerzeichen
    keine 1 am Anfang
    10-i 000
    33-i 000
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
 *  Spells numbers in Georgian
 *  @author Dr. Georg Fischer
 */
public class GeoSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: GeoSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public GeoSpeller() {
        super();
        setIso639("geo,ka,kat");
        setDescription("Georgian");
        setMaxLog(9);
        setSeparator(true);

        wordN = new String[]
        {  "ნულ"          // 0 nul-i ი
        ,  "ერთ"          // 1 ert-i
        ,  "ორ"             // 2 or-i
        ,  "სამ"          // 3 sam-i
        ,  "ოთხ"          // 4 otkh-i
        ,  "ხუთ"          // 5 khut-i
        ,  "ექვს"           // 6 ekvs-i
        ,  "შვიდ"           // 7 shvid-i
        ,  "რვა"          // 8 rva-i
        , "ცხრა"            // 9 tskhra-i
        ,    "ათ"           // 10 at-i
        ,  "თერთმეტ"      // 11 = 10 + 1
        ,   "თორმეტ"        // 12 = 10 + 2
        ,    "ცამეტ"      // 13 = 10 + 3
        ,  "თოთხმეტ"      // 14
        ,  "თხუთმეტ"      // 15
        , "თექვსმეტ"        // 16
        ,  "ჩვიდმეტ"      // 17
        ,  "თვრამეტ"      // 18
        ,  "ცხრამეტ"      // 19
        };

        setMorphem("h1", "ას");         // as-i
        setMorphem("t1", "ათას");       // at-as
        setMorphem("l1", "მილიონ");     // milion-i
        setMorphem("m1", "ლიონ");
        setMorphem("p0", "ი");            // -i at the end of a number
        setMorphem("p1", "და");         // da, before 1-19
        setMorphem("p2", "მ");            // m, before 20s
        setMorphem("20", "ოც");             // =20, the base number
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
                }
                else 
                if (digitN >= 1) {
                    spellN(digitN);
                }
                break; 
            case 1: 
                spellN(10 + digitN);
                break;
            default:
                int nn = digitN0 * 10 + digitN;
                int m20 = nn / 20;
                int r20 = nn % 20;
                // put: m20 * e20 + "da" + r20
                if (m20 > 1) {
                    putWord(wordN[m20]); // remove "-i"
                }
                append(getMorphem("p2"));
                append(getMorphem("20"));
                if (r20 > 0) {
                    append(getMorphem("p1")); // da
                    append(wordN[r20]);
                }
                break;
        } // switch digitN0
        
        if (zeroTuple) {
            append(getMorphem("p0")); // -i
        } 
        else { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    if (true) {
                        append(getMorphem("p0")); // -i
                    }
                    break;
                case 1:
                    putMorphem("t1");
                    break;
                case 2:
                    putMorphem("m1");
                    break;
                default:
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
                    { "0"   // 0
                    , "იანვარი" // ianvari]   // 1
                    , "თებერვალი" // t'ebervali]  // 2
                    , "მარტი" // marti]   // 3
                    , "აპრილი" // aprili]   // 4
                    , "მაისი" // maisi]   // 5
                    , "ივნისი" // ivnisi]   // 6
                    , "ივლისი" // ivlisi]   // 7
                    , "აგვისტო" // agvisto]   // 8
                    , "სექტემბერი" // sek'temberi]  // 9
                    , "ოქტომბერი" // ok'tomberi]  // 10
                    , "ნოემბერი" // noemberi]   // 11
                    , "დეკემბერი" // dekemberi]   // 12
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
                    , "გაზაფხული" // gazaṗĥuli
                    , "ზაფხული"     // zaṗĥuli
                    , "შემოდგომა"   // šemodgoma
                    , "ზამთარი"     // zamṭari
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
                    , "ორშაბათი" // oršabat’i]
                    , "სამშაბათი" // samšabat’i]
                    , "ოთხშაბათი" // ot’xšabat’i]
                    , "ხუთშაბათი" // xut'šabat'i]
                    , "პარასკევი" // paraskevi]
                    , "შაბათი" // šabat’i]
                    , "კვირა" // kvira] (from Greek)
                    })[weekDay];
        }
        return result;
    } // spellWeekDay


}
