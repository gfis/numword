/*  Spelling of numbers in Quenya, invented by J.R.R. Tolkien as a language for High Elves
	in his books "The Lord of the Rings", "The Hobbit" etc.
    @(#) $Id: QyaSpeller.java 13 2008-09-05 05:58:51Z gfis $
    Copyright (c) 2007 Dr. Georg Fischer <punctum@punctum.com>
    2007-11-17, Georg Fischer: copied from DeuSpeller

    caution: UTF-8 is essential! compile with "-encoding UTF-8"
    
    incomplete !

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

/** Spells numbers in the artificial language Quenya invented by J.R.R. Tolkien;
 *  as described at http://en.wikibooks.org/wiki/Quenya
 *  @author Dr. Georg Fischer
 */
public class QyaSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: QyaSpeller.java 13 2008-09-05 05:58:51Z gfis $";

    /** Constructor
     */
    public QyaSpeller() {
        super();
        setIso639("qya");
        setDescription("Quenya (J.R.R. Tolkien)");
        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
        { "munta"
        , "minë"
        , "atta"
        , "neldë"
        , "canta"
        , "lempë" // 5
        , "enquë"
        , "otso"
        , "tolto"
        , "nertë"
        };
        wordN0 = new String[]
        { ""
        , "cainen"
        , "yucainen"
        , "nelcainen"
        , "cancainen"
        , "lemincainen" // 50
        , "eneccainen"
        , "otsocainen"
        , "tolcainen"
        , "nercainen"
        };
        wordN00 = new String[]
        { ""
        , "tuxa"
        , "yutuxa"
        , "neltuxa"
        , "cantuxa"
        , "lemintuxa" // 50
        , "enectuxa"
        , "otsotuxa"
        , "toltuxa"
        , "nertuxa"
        };
        word1N = new String[]
        { "cainen"
        , "minquë"
        , "yunquë"
        , "nelcëa"
        , "cancëa"
        , "lencëa" // 15
        , "encëa"
        , "occëa"
        , "tolcëa"
        , "nercëa"
        };

        // adapt to German consonants k, z instead of c
        for (int iw = 2; iw < wordN000.length; iw ++) {
            wordN000[iw] = wordN000[iw]
                            .replaceAll("deci", "dezi")
                            .replaceAll("oct" , "okt" );
            wordN000[iw] = Character.toUpperCase(wordN000[iw].charAt(0))
                        + wordN000[iw].substring(1);
        } // for iw

        setMorphem("h1", "tuxa");
        setMorphem("t1", "húmë");
        setMorphem("m1", "mindóra"); // 1 million
        setMorphem("m2", "lionen");
        setMorphem("m3", "liarde");
        setMorphem("m4", "liarden");
        setMorphem("p0", " ");
        // setMorphem("p1", "s"); // for ein"s"?
        setMorphem("p2", "e");
        setMorphem("p3", "und");
        enumerateMorphems();
        // tricky: leading zeroes make alternate keys = aliases for the same numerical value
        setMorphem("01" , "eins");
        setMorphem("001", "erst"); // c.f. this.parseString(,)
        setMorphem("02" , "zwo");
        setMorphem("03" , "dritt"); // c.f. this.parseString(,)
        setMorphem("05" , "fuenf");
        setMorphem("07" , "sieb");
        setMorphem("012", "zwoelf");
        setMorphem("015", "fuenfzehn");
        setMorphem("030", "dreissig");
        setMorphem("050", "fuenfzig");
    }

    /** Overlay for special treatment of "dritt(el)" and "acht(el)"
     *  and for "s" after "zwanzig";
     *  in order to equalize "er", "tens", "ter", "te",
     *  "ten", "tem", "tes" endings;
     *  parses a string, and returns a digit sequence if
     *  it starts with a number word;
     *  always tries to match a longest prefix;
     *  @param text string to be parsed
     *  @param offset offset in <em>text</em> where to start parsing
     *  @return new offset in <em>text</em> of first particle not recognized,
     *  and sequence of digit characters in global <em>result</em>
     */
    public int parseString(String text, int offset) {
        offset = super.parseString(text, offset);
        if (offset > 0) {
            // an additional "e" was eaten as "p2" morphem (to be ignored)
            if  (text.substring(0, offset).endsWith("e")) {
                offset --; // keep "e" behind number word
            }
            if  (   text.substring(offset).startsWith("e")
                &&  (   text.substring(0, offset).endsWith("dritt")
                    ||  text.substring(0, offset).endsWith("acht")
                    ||  text.substring(0, offset).endsWith("erst")
                    )                           // but not "hundert"
                )
            {
                offset --; // keep "t" behind number word
            }
            else
            if  (   text.substring(offset).startsWith("ste")
                &&  (   text.substring(0, offset).endsWith("ig")
                    ||  getResult().length() > 2 // numeric value >= 100
                    )
                )
            {
                offset ++; // eat "s"
            }
        }
        return offset;
    }

    /** Appends the wording for a triple of digits,
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
                } else if (digitN >= 1) {
                    spellN(digitN);
                    if (number.length() == 0 && digitN == 1) {
                        append("s"); // dreihundertein"s"
                    }
                }
                break;
            case 1: // 10..19 with some exceptions
                spell1N(digitN);
                break;
            default:
                if (digitN >= 1) {
                    spellN(digitN);
                    putMorphem("p3");
                }
                spellN0(digitN0);
                break;
        } // switch digitN0

        if (! zeroTuple) { // append thousand, million ... */
            switch (logTuple) {
                case 0: // no thousands
                    break;
                case 1:
                    putMorphem("t1");
                    break;
                default:
                    if (singleTuple) {
                        append("e"); // ein"e"million ...
                    }
                    append(" ");
                    spellN000Morphem(logTuple);
                    append(" ");
                    break;
            } // switch logTuple
        } // thousands ...
    } // spellTuple

    /** Pairs of German month numbers, month names and their abbreviations */
    private String months[] = new String[] 
            { "00", "Monat"       // 0
            , "01", "Januar"      // 1
            , "02", "Februar"     // 2
            , "03", "März"        // 3
            , "04", "April"       // 4
            , "05", "Mai"         // 5
            , "06", "Juni"        // 6
            , "07", "Juli"        // 7
            , "08", "August"      // 8
            , "09", "September"   // 9
            , "10", "Oktober"     // 10
            , "11", "November"    // 11
            , "12", "Dezember"    // 12
            // abbreviations (3 letters)
            , "01", "Jan"         
            , "02", "Feb"         
            , "03", "Mär"         
            , "04", "Apr"         
            , "05", "Mai"         
            , "06", "Jun"         
            , "07", "Jul"         
            , "08", "Aug"         
            , "09", "Sep"         
            , "10", "Okt"         
            , "11", "Nov"         
            , "12", "Dez"
            // aliases and other abbreviations         
            , "01", "Jänner"      
            , "01", "Jaenner"     
            , "03", "Maerz"       
            , "02", "Febr"        
            , "09", "Sept"        
            };

    /** Returns the month's number as String (between "01" and "12")
     *  @param name name of the month, or an alias or abbreviation
     *  @return number between "01" and "12", or null if not found
     */
    public String parseMonth(String name) {
        return lookupWord(name, months);
    }
    
    /** Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        return (month >= 0 && month <= 12) 
                ? months[month * 2 + 1]
                : Integer.toString(month);
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
                    { "Jahreszeit"
                    , "Frühling"
                    , "Sommer"
                    , "Herbst"
                    , "Winter"
                    })[season];
        }
        return result;
    }

    /** Pairs of German weekday numbers, weekday names and their abbreviations */
    private String weekdays[] = new String[] 
            { "00", "Wochentag"
            , "01", "Montag"
            , "02", "Dienstag"
            , "03", "Mittwoch"
            , "04", "Donnerstag"
            , "05", "Freitag"
            , "06", "Samstag"
            , "07", "Sonntag"
            , "01", "Mo"
            , "02", "Di"
            , "03", "Mi"
            , "04", "Do"
            , "05", "Fr"
            , "06", "Sa"
            , "07", "So"
            };

    /** Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String []
                    { "Wochentag"
                    , "Montag"
                    , "Dienstag"
                    , "Mittwoch"
                    , "Donnerstag"
                    , "Freitag"
                    , "Samstag"
                    , "Sonntag"
                    })[weekDay];
        }
        return result;
    }

}
