/*  Spelling of numbers in Estonian 
    spoken in Estonia 
    @(#) $Id: EstSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-10, Georg Fischer: copied from FinSpeller - üks
    
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
import  org.teherba.numword.FinSpeller;
/**
 *  Spells numbers in Estonian
 *  @author Dr. Georg Fischer
 */
public class EstSpeller extends FinSpeller {
    public final static String CVSID = "@(#) $Id: EstSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public EstSpeller() {
        super();
        setIso639("et,est");
        setDescription("Estonian");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "null"
        , "üks"
        , "kaks"
        , "kolm"
        , "neli"
        , "viis"
        , "kuus"
        , "seitse"
        , "kaheksa"
        , "üheksa"
        };
        wordN0 = new String[]
        { "null"
        , "kümme"
        , "kakskümmend"
        , "kolmkümmend"
        , "nelikümmend"
        , "viiskümmend"
        , "kuuskümmend"
        , "seitsekümmend"
        , "kaheksakümmend"
        , "üheksakümmend"
        };
        word1N = new String[]
        { "kümme"
        , "üksteist"
        , "kaksteist"
        , "kolmteist"
        , "neliteist"
        , "viisteist"
        , "kuusteist"
        , "seitseteist"
        , "kaheksateist"
        , "üheksateist"
        };

        setMorphem("h1", "sada");
        setMorphem("t1", "tuhat");
        setMorphem("m1", "jon");
        setMorphem("m3", "jard");
        setMorphem("p0", " ");
        setMorphem("p3", "ten"); 
        enumerateMorphems();
    }
    
    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 1 && month <= 12) {
            result = (new String [] 
                    { ""
                    , "jaanuar"
                    , "veebruar"
                    , "märts"
                    , "aprill"
                    , "mai"
                    , "juuni"
                    , "juuli"
                    , "august"
                    , "september"
                    , "oktoober"
                    , "november"
                    , "detsember"
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
        if (season >= 1 && season <= 4) {
            result = (new String [] 
                    { "?"
                    , "kevät"
                    , "kesä"
                    , "sysky"
                    , "talvi"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "0"
                    , "Maanantai"
                    , "Tiistai"
                    , "Keskiviikko"
                    , "Torstai"
                    , "Perjantai"
                    , "Launantai"
                    , "Sunnuntai"
                    })[weekDay];
        }
        return result;
    }

}
