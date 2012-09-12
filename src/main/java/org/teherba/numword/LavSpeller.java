/*  Spelling of numbers in Latvian (Latviešu)
    spoken in Latvia (Lettland)
    @(#) $Id: LavSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-13, Georg Fischer: copied from LitSpeller
    
    caution, UTF-8 is essential! compile with -encoding UTF-8
    unsure for N00
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
import  org.teherba.numword.SlavicSpeller;
/**
 *  Spells numbers in Latvian (Latviešu)
 *  @author Dr. Georg Fischer
 */
public class LavSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: LavSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public LavSpeller() {
        super();
        setIso639("lav,lv");
        setDescription("Latvian (Latviešu)");
        // setMaxLog(false);
        // setSeparator(true);
        wordN = new String[]
        { "nulle" 
        , "viens"
        , "divi"        
        , "trīs"                           
        , "četri" 
        , "pieci"
        , "seši"
        , "septiņi"
        , "astoņi"
        , "deviņi"
        };
        word1N = new String[]
        { "desmit"
        , "vienpadsmit"
        , "divpadsmit"      
        , "trīspadsmit"                            
        , "četrpadsmit" 
        , "piecpadsmit"
        , "sešpadsmit"
        , "septiņpadsmit"
        , "astoņpadsmit"
        , "deviņpadsmit"
        };
        wordN0 = new String[]
        { "0" 
        , "desmit"
        , "divdesmit"       
        , "trīsdesmit"     
        , "četrdesmit" 
        , "piecdesmit"
        , "sešdesmit"
        , "septiņdesmit"
        , "astoņdesmit"
        , "deviņdesmit"
        };
        wordN00 = new String[]
        { "0"
        , "simt"
        , "divsimt"     
        , "trīsimt"                            
        , "četrsimt" 
        , "piecsimt"
        , "sešimt"
        , "septiņsimt"
        , "astoņsimt"
        , "deviņsimt"
        };
        
        setMorphem("h1", "simt");
        setMorphem("t1", "tūkstoš");
        setMorphem("m1", "jons");
        setMorphem("m3", "jards"); // ???
        setMorphem("p0", " ");
        enumerateMorphems();
    }
    
    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { ""    // 0
                    , "janvāris"   // 1
                    , "februāris"  // 2
                    , "marts"       // 3
                    , "aprīlis"        // 4
                    , "majis"       // 5
                    , "jūnijs"     // 6
                    , "jūlijs"     // 7
                    , "augusts"     // 8
                    , "septembris"  // 9
                    , "oktobris"    // 10
                    , "novembris"   // 11
                    , "decembris"   // 12
                    })[month];
        }
        return result;
    }

    /**
     *  Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"
                    , "pavasaris"
                    , "vasara"
                    , "ruduns"
                    , "ziema"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { ""    // 0
                    , "pirmdiena"   // 1
                    , "otrdiena"    // 2
                    , "trešdiena"  // 3
                    , "ceturtdiena" // 4
                    , "piektdiena"  // 5
                    , "sestdiena"   // 6
                    , "svētdiena"  // 7
                    })[weekDay];
        }
        return result;
    }

}
