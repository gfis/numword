/*  Spelling of numbers in Japan (Nippon)
    spoken in Japan
    @(#) $Id: JpnSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-06: Georg Fischer, copied from ChiSpeller 
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
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
import  org.teherba.numword.SinoSpeller;
/**
 *  Spells numbers in Japanese  日本語　(nihongo) 
 *  @author Dr. Georg Fischer
 */
public class JpnSpeller extends SinoSpeller {
    public final static String CVSID = "@(#) $Id: JpnSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public JpnSpeller() {
        super();
        setIso639("jpn,ja"); 
        setDescription("Japanese (日本語)");
        setMorphem("tz", "万");    // 
        setMorphem("mh", "億");    // (ichi-oku)
        setMorphem("wm", "月");    // [hachigatsu]
        enumerateMorphems();
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
                    , "1"
                    , "2"
                    , "3"
                    , "4"
                    })[season];
        }
        return result;
    }

    /**
     *  Returns the week day's name
     *  @param weekDay number of day in week, &gt;= 0 and &lt;= 7,
     *  1 -&gt; Monday, 7 (and 0) -&gt; Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { ""
                    , "月曜日"   // [getsuyōbi] 
                    , "火曜日"   // [kayōbi] ("fire day")
                    , "水曜日"   // [suiyōbi] ("water day")
                    , "木曜日"   // [mokuyōbi] ("wood day")
                    , "金曜日"   // [kinyōbi] ("gold day")
                    , "土曜日"   // [doyōbi] ("earth day")
                    , "日曜日"   // [nichiyōbi]
                    })[weekDay];
        }
        return result;
    }

}
