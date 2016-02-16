/*  Spelling of numbers in Portuguese (Português)
    spoken in Portugal, Brasilia 
    and former Portugese colonies
    @(#) $Id: PorSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2005 Dr. Georg Fischer <punctum@punctum.com>
    2006-01-04: weekdays and months; UTF-8 "Português"
    2005-06-09, Georg Fischer: copied from SpaSpeller.java

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
import  org.teherba.numword.BaseSpeller;
/**
 *  Spells numbers in Portuguese (Português)
 *  @author Dr. Georg Fischer
 */
public class PorSpeller extends BaseSpeller {
    public final static String CVSID = "@(#) $Id: PorSpeller.java 521 2010-07-26 07:06:10Z gfis $";

    /**
     *  Constructor
     */
    public PorSpeller() {
        super();
        setIso639("por,pt");
        setDescription("Portuguese (Português)");
        setMaxLog(true); // for PT: mil milhões
        setSeparator(true);

        wordN = new String[]
        { "zero" 
        , "um"
        , "dois"
        , "três"
        , "quatro"
        , "cinco"
        , "seis"
        , "sete"
        , "oito"
        , "nove"
        };
        wordN0 = new String[]
        { ""
        , "dez"
        , "vinte"
        , "trinta"
        , "quarenta"
        , "cinquenta"
        , "sessenta"
        , "setenta"
        , "oitenta"
        , "noventa"
        };
        wordN00 = new String[]
        { ""
        , "cem"
        , "dozentos"
        , "trezentos"
        , "quatrocentos"
        , "quinhentos"
        , "seiscentos"
        , "setecentos"
        , "oitocentos"
        , "novecentos"
        };
        word1N = new String[]
        { "dez"
        , "onze"
        , "doze"
        , "treze"
        , "catorze"
        , "quinze"
        , "dezasseis"
        , "dezassete"
        , "dezoito"
        , "dezanove"
        };

        setMorphem("t1", "mil");
        setMorphem("m1", "hão");
        setMorphem("m2", "hões");
        setMorphem("p0", " ");
        setMorphem("p1", "e"); // and
        enumerateMorphems();
        // "li" must be set after 'enumerateMorphems'
        removeMorphem("e02");
        setMorphem("l1", "milhão"); 
        setMorphem("l2", "milhões");
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
                if (digitN0 > 0 || digitN > 0) {
                    putWord("e"); // and
                }
                break;
        } // switch 100
        
        // put tens and ones
        switch (digitN0) {
            case 0: // 0-9
                if (nullOnly) {
                    spellN(digitN);
                }
                else 
                if (digitN > 0) {
                    if (! singleTuple || digitN != 1 || logTuple % 2 == 0) {
                        spellN(digitN);
                    }
                }
                break; 
            case 1: // 10-19
                spell1N(digitN);
                break;
            default:
                spellN0(digitN0);
                if (digitN > 0) {
                    putWord("e"); // and 
                    spellN(digitN);
                }
                break;
        } // switch digitN0
        
        switch (logTuple) {
            case 0: // no thousands
                break;
            case 1:
                if (! zeroTuple) {
                    putWord("mil");
                }
                break;
            default:
                if (logTuple % 2 == 0) { // even, 10**(6*N)
                    if (! previousZeroTuple || ! zeroTuple) {
                        spellN000(logTuple / 2 + 1);
                        append((previousZeroTuple && singleTuple) ? "hão" : "hões");
                    }
                }
                else { // odd, 10**(6*N+3)
                    if (! zeroTuple) {
                        putWord("mil");
                    }
                }
                break;
        } // switch logTuple
    } // spellTuple

    /**
     *  Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "0"   // 0
                    , "janeiro"     // 1
                    , "fevereiro"   // 2
                    , "mar\u00e7o"  // 3 &cedil;
                    , "abril"       // 4
                    , "maio"        // 5
                    , "junho"       // 6
                    , "julho"       // 7
                    , "agosto"      // 8
                    , "setembro"    // 9
                    , "outubro"     // 10
                    , "novembro"    // 11
                    , "dezembro"    // 12
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
                    , "primavera"               
                    , "verão"
                    , "outono"
                    , "inverno"
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
                    , "segunda" // 1
                    , "ter\u00e7a " // 2 &ccedil;
                    , "quarta"  // 3
                    , "quinta"  // 4
                    , "sexta"   // 5
                    , "s\u00e1bado" // 6 &aacute;
                    , "domingo" // 7
                    })[weekDay];
        }
        return result;
    }

}
