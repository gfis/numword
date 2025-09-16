/*  Spelling of numbers in  Lithuanian (Lietuvių)
    spoken in Lithuania (Litauen)
    @(#) $Id: LitSpeller.java 521 2010-07-26 07:06:10Z gfis $
    2006-01-13, Georg Fischer: copied from CesSpeller
    
    caution, UTF-8 is essential! compile with -encoding UTF-8
    unsure for N00
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
import  org.teherba.numword.SlavicSpeller;
/**
 *  Spells numbers in Lithuanian (Lietuvių)
 *  @author Dr. Georg Fischer
 */
public class LitSpeller extends SlavicSpeller {
    public final static String CVSID = "@(#) $Id: LitSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public LitSpeller() {
        super();
        setIso639("lit,lt");
        setDescription("Lithuanian (Lietuvių)");
        // setMaxLog(false);
        // setSeparator(true);
        wordN = new String[]
        { "nulis" 
        , "vienas"
        , "du"      
        , "trys"    
        , "keturi" 
        , "penki"
        , "šeši"
        , "septyni"
        , "aštuoni"
        , "devyni"
        };
        word1N = new String[]
        { "dešimt"
        , "vienuolika"
        , "dvylika"     
        , "trylika"     
        , "keturiolika" 
        , "penkiolika"
        , "šešiolika"
        , "septyniolika"
        , "aštuoniolika"
        , "devyniolika"
        };
        wordN0 = new String[]
        { "0"
        , "dešimt"
        , "dvidešimt"      
        , "tridešimt"  
        , "keturiasdešimt" 
        , "penkiasdešimt"
        , "šešiasdešimt"
        , "septyniasdešimt"
        , "aštuoniasdešimt"
        , "devyniasdešimt"
        };
        wordN00 = new String[]
        { "0"
        , "šimtas"
        , "dušimtas"       
        , "tryšimtas"  
        , "keturšimtas" 
        , "penkšimtas"
        , "šešimtas"
        , "septynšimtas"
        , "aštuonšimtas"
        , "devynšimtas"
        };
        
        setMorphem("h1", "šimtas");
        setMorphem("t1", "tukstantis");
        setMorphem("m1", "jonas");
        setMorphem("m3", "jardas"); // ???
        setMorphem("p0", " ");
        enumerateMorphems();
    }
    
    /**
     *  Returns the month's name
     *  @param month month's number, &gt;= 1 and &lt;= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { ""            // 0
                    , "sausis"      // 1
                    , "vasaris"     // 2
                    , "kovas"       // 3
                    , "balandis"    // 4
                    , "gegužė"        // 5
                    , "birželis"   // 6
                    , "liepa"       // 7
                    , "rugpjūtis"  // 8
                    , "rusėjis"        // 9
                    , "spalis"      // 10
                    , "lapkritis"   // 11
                    , "gruodis"     // 12
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
                    , "pavasaris"
                    , "vasara"
                    , "ruduo"
                    , "žiema"
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
                    { ""                // 0
                    , "pirmadienis"     // 1
                    , "antradienis"     // 2
                    , "trečiadienis"   // 3
                    , "ketvirtadienis"  // 4
                    , "penktadienis"    // 5
                    , "šeštadienis"       // 6
                    , "sekmadienis"     // 7
                    })[weekDay];
        }
        return result;
    }

}
