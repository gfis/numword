/*  Spelling of numbers in Arabic  ديسمبر 
    spoken in Arabia, North Africa, Near East Asia and by muslims all over the world
    @(#) $Id: AraSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2016-02-15: spellIdeographic(number)
    2006-01-04: Georg Fischer, copied from GreSpeller
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    months, weekdays ok; nubmers are totally untested
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
 *  Spells numbers in Arabic العربية (al-ʿarabīyâ)
 *  @author Dr. Georg Fischer
 */
public class AraSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: AraSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**
     *  Constructor
     */
    public AraSpeller() {
        super();
        setIso639("ara,ar"); 
        setDescription("Arabic (ديسمبر)");
        setLogTuple(3);
        setMaxLog(9);
        setDirection("rtl");
        setSeparator(true);

        wordN = new String[]
    /*      
        { 
        "٠"
        , "١"  // 
        , "٢"  
        , "٣"  // 
        , "٤"  // 
        , "٥" 
        , "٦"
        , "٧"  // 
        , "٨"  // 
        , "٩"  // 
    */  
        { "صف"            //  sifr
        , "واحد"        //  (wahid)     eins
        , "اثنين"      //  (itnen)     zwei
        , "ثلاثة"      //  (talata)    drei
        , "اربعة"      //  (arba`a)    vier
        , "خمسة"        //  (hamsa)     fuenf
        , "ستّة"        //  (sitta)     sechs
        , "سبعة"        //  (saba`a)    sieben
        , "ثمانية"        //  (tamania)   acht
        , "تسعة"        //  (tisa`a)    neun
        };
        word1N = new String[]
        { "عشرة"        // 10 (`asara)  zehn
        , "حضاشر"      // 11 (hidasar) elf
        , "اطناشر"        // 12 (itnasar) zwoelf
        , "ثلاثة عشر"   // 13 (talata)  drei
        , "أربعة عشر"   // 14 (arba`a)  vier
        , "خمسة عشر"     // 15 (khamsa)      fuenf
        , "ستة عشر"   // 16 (sitta)       sechs
        , "سبعة عشر"     // 17 (saba`a)  sieben
        , "ثمانية عشر"     // 18 (tamania) acht
        , "تسعة عشر"     // 19 (tisa`a)  neun
        };
        wordN0 = new String[]
        { "0"
        , "عشرة"        // 10 ‘ashra
        , "عشرون"      // 20 ‘ishrun
        , "ثلاثون"        // 30 thalathun
        , "أربعون"        // 40 arba’un
        , "خمسون"      // 50 khamsun
        , "ستون"        // 60 sittun
        , "سبعون"      // 70 sab'un
        , "ثمانون"        // 80 thamanun
        , "تسعون"      // 90 tis'un
        };
    
        setMorphem("h1", "مائة");   // 100 mi'a
        setMorphem("t1", "ألف"); // 1000 'alf
        setMorphem("t2", "ألفين"); // 2000 alfain
        setMorphem("m1", "مليون");
        setMorphem("p0", " ");
        setMorphem("p3", "و");
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
            case 1:
                putMorphem("h1");
                break;
            default:
                spellN(digitN00);
                append(getMorphem("h1"));
                break;
        } // switch 100
        
        // tens and ones
        switch (digitN0) {
            case 0:
                if (nullOnly) {
                    spellN(0);
                }
                else
                if (digitN == 1 && logTuple < 1) {
                    spellN(digitN);
                }
                else
                if (digitN > 1) {
                    spellN(digitN);
                }
                break; 
            case 1: 
                spell1N(digitN);
                break;
            default:
                spellN(digitN0);
                if (digitN >= 1) {
                    putMorphem("p3");
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
                        append(getMorphem("t1"));
                    }
                    break;
                default:
                    putMorphem("m1");
                    // spellN000(logTuple, getMorphem("m1"), getMorphem("m3"));
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
        result.setLength(0);
        int chpos = 0;
        while (chpos < number.length()) {
            result.append((char) ('\u0660' + Character.digit(number.charAt(chpos), 10)));
            chpos ++;
        } // while chpos
        return result.toString();
    } // spellIdeographic

    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "0"           // 0
                    , "يناير"      // [yanaīr]"   // 1
                    , "فبراير"        // [fibraīr]"  // 2
                    , "فبراير"        // [mārs]" // 3
                    , "أبريل"      // [ābrīl]"   // 4
                    , "مايو"        // [māyū]"    // 5
                    , "يونيو"      // [yūnyū]"   // 6
                    , "يوليو"      // [yūlyū]"   // 7
                    , "أغسطس"      // [āghustus]" // 8
                    , "سبتمبر"        // [sibtambar]" // 9
                    , "أكتوبر"        // [ūktūbar]" // 10
                    , "نوفمبر"        // [nūfambar]" // 11
                    , "ديسمبر"        // [dīsambar]" // 12
                    })[month];
        }
        return result;
    }

    /**
     *  Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     *  der Frühling = ar rabia    
     *  der Sommer as-saif   
     *  der Herbst - al harif   
     *  der Winter - al schita
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "0"           //  Arabic - ربيع   صيف  خريف    شتاء
                    , "ربيع"            // (rabi`)  Fruehling
                    , "صيف"          // (sef)    Sommer
                    , "خريف"            // (harif)  Herbst
                    , "شتة"          // (sita)   Winter
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
                    { "0"
                    , "الاثنين"          // [a ithinin / alithnain]"     // 1
                    , "الثلاثاء"            // [al tholatha'a / aththalāthā']"        // 2
                    , "الأربعاء"            // [al arbia’a / alārba’ā’]"    // 3
                    , "الخميس"            // [al kamis / alxamīs]"   // 4
                    , "الجمعة"            // [al gomia'a / ajum'ah]"      // 5
                    , "السبت"          // [al sabit / assabt]"     // 6
                    , "الأحد"          // [al ahad / alāhad]" // 7
                    })[weekDay];
        }
        return result;
    }

}
