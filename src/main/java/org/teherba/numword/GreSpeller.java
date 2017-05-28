/*  Spelling of numbers in Greek (Ελληνικά)  
    spoken in Greece
    @(#) $Id: GreSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-09: tested up to 10**15
    2006-01-04: weekdays and months; UTF-8 "Ελληνικά"
    2005-06-06, Georg Fischer: copied from NorSpeller
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    remove ena in "ena 1000"
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
/**
 *  Spells numbers in Greek (Ελληνικά)
 *  @author Dr. Georg Fischer
 */
public class GreSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: GreSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public GreSpeller() {
        super();
        setIso639("gre,ell,el"); 
        setDescription("Greek (Ελληνικά)");
        setMaxLog(15);
        setSeparator(true);

        wordN = new String[]
        { "μηδέν"
        , "ένα"  // [m], μια [f], ένα [n]
        , "δύο"  
        , "τρία"    // τρεις [m,f], τρία [n]
        , "τέσσερα"  // τέσσερις [m,f], τέσσερα [n]
        , "πέντε" 
        , "έξι"
        , "επτά"    // εφτά
        , "οκτώ"    // οχτώ
        , "εννιά"  // εννέα"  // εννιά
        };
        wordN0 = new String[]
        { "0"
        , "δέκα"
        , "είκοσι"
        , "τριάντα"
        , "σαράντα"
        , "πενήντα"
        , "εξήντα"
        , "εβδομήντα"
        , "ογδόντα"
        , "ενενήντα"
        };
        wordN00 = new String[]
        { "0"
        , "εκατό"          // 100
        , "διακόσια"        // 200
        , "τριακόσια"      // 300
        , "τετρακόσια"
        , "πεντακόσια"        // 500
        , "εξακόσια"
        , "επτακόσια"      // εφτακόσια
        , "οκτακόσια"      // οχτακόσια
        , "εννιακόσια"
        };
        word1N = new String[]
        { "δέκα"            // ένδεκα
        , "έντεκα"
        , "δώδεκα"
        , "δεκατρία"
        , "δεκατέσσερα"
        , "δεκαπέντε"      // 15
        , "δεκαέξι"      // δεκάξι
        , "δεκαεπτά"        // δεκαεφτά
        , "δεκαοκτώ"        // δεκαοχτώ
        , "δεκαεννιά"      // δεκαεννέα"  // δεκαεννιά
        };
    
        /*
            1000 = χίλια   
            10 000 = δέκα χιλιάδες  
            100 000 = εκατό χιλιάδες   
            1 000 000 = ένα εκατομμύριο   
            1 000 000 000 = ένα δισεκατομμύριο     
            1 000 000 000 000 = ένα τρισεκατομμύριο
        
            1000 = χίλια
            2000 = δύο χιλιάδες
            10 000 = δέκα χιλιάδες
            100 000 = εκατό χιλιάδες
            
            1 000 000 = ένα εκατομμύριο
            1 000 000 000 = ένα δισεκατομμύριο
            1 000 000 000 000 = ένα τρισεκατομμύριο
        */
        setMorphem("t1", "χίλια");
        setMorphem("t2", "χιλιάδες");
        setMorphem("m1", "εκατομμύριο");
        setMorphem("m2", "εκατομμύριο"); // ???
        setMorphem("p0", " ");
        enumerateMorphems();
        // "li" must be set after 'enumerateMorphems'
        setMorphem("l1", "миллион"); 
    }
    
    /**
     *  Appends the word for 10**3
     *  @param log1000 logarithm of 1000, = 2, 3, 4 ...
     */
    protected void spellN000(int log1000) {
        switch (log1000) {
            case 2:
                putWord("εκατομμύριο");
                break;
            case 3:
                putWord("δισεκατομμύριο");
                break;
            case 4:
                putWord("τρισεκατομμύριο");
                break;
            default:
                break;
        } // switch log1000
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
                if (digitN > 0) {
                    spellN(digitN);
                }
                break; 
            case 1: 
                spell1N(digitN);
                break;
            default:
                spellN0(digitN0);
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
                    putWord((singleTuple) ? getMorphem("t1") : getMorphem("t2"));
                    break;
                default:
                    spellN000(logTuple);
                /*
                    append("lion");
                    if (! singleTuple) {
                        append("s"); // two million"s"
                    }
                */
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
                    { "0"   // 0
                    , "Ιανουάριος" // Ianuários
                    , "Φεβρουάριος" // Februários
                    , "Μάρτιος" // Mártios
                    , "Απρίλιος" // Aprílios
                    , "Μάιος" // Máios
                    , "Ιούνιος" // Iúnios
                    , "Ιούλιος" // Iúlios
                    , "Αύγουστος" // Ávgustos
                    , "Σεπτέμβριος" // Septémvrios
                    , "Οκτώβριος" // Októvrios
                    , "Νοέμβριος" // Noémvrios
                    , "Δεκέμβριος" // Dekémvrios
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
                    , "ἓαρ"
                    , "θέρος"
                    , "φθινόπωρον"
                    , "χειμών"
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
                    , "Δευτέρα"  // Dheftéra // 1
                    , "Τρίτη"      // Tríti   // 2
                    , "Τετάρτη"      // tetárti // 3
                    , "Πέμπτη"        // pémpti  // 4
                    , "Παρασκευή"  // Paraskeví// 5
                    , "Σάββατο"  // Sávato  // 6
                    , "Κυριακή"  // Kyriakí // 7
                    })[weekDay];
        }
        return result;
    }


}
