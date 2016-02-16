/*  Spelling of numbers in Thai
    spoken in Thailand
    @(#) $Id: ThaSpeller.java 521 2010-07-26 07:06:10Z gfis $
    Copyright (c) 2006 Dr. Georg Fischer <punctum@punctum.com>
    2016-02-15: spellIdeographic(number)
    2008-12-30: Georg Fischer, copied from KorSpeller 
    
    not correct for numbers >= 10000, and combinations of digits
    
    caution: UTF-8 is essential! compile with "-encoding UTF-8"
	Links:
	http://www.thai-language.com/id/589825 Month Names etc.
	http://www.panix.com/~clay/thai.html Thai Language Ressources
	http://www.learningthai.com/numbers.html Numbers 0-10000
*/
/*
 * Copyright 2008 Dr. Georg Fischer <punctum at punctum dot kom>
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
/**	Spells numbers in Thai 
 *  @author Dr. Georg Fischer
 */
public class ThaSpeller extends SinoSpeller {
    public final static String CVSID = "@(#) $Id: ThaSpeller.java 521 2010-07-26 07:06:10Z gfis $";
    
    /**	Constructor
     */
    public ThaSpeller() {
        super();
        setIso639("tha,th"); 
        setDescription("Thai (ไทย)");

        setMaxLog(true);
        setSeparator(false);

        wordN = new String[]
		{ "ศูนย์" // suun[y]R zero; the number or quantity zero
		, "หนึ่ง" // neungL one; the number or quantity one
		, "สอง" // saawngR two; the number or quantity two
		, "สาม" // saamR three; the number or quantity three
		, "สี่" // seeL four; the number or quantity four
		, "ห้า" // haaF five; the number or quantity five
		, "หก" // hohkL six; the number or quantity six
		, "เจ็ด" // jetL seven; the number or quantity seven
		, "แปด" // bpaaetL eight; the number or quantity eight
		, "เก้า" // gaoF nine; the number or quantity nine
        };
        wordN0 = new String[]
        { ""
		, "สิบ" // sipL ten; the number or quantity ten
		, "ยี่สิบ" // yeeF sipL twenty; 20
		, "สามสิบ" // saamR sipL thirty; 30
		, "สี่สิบ" // seeL sipL forty; 40
		, "ห้าสิบ" // haaF sipL fifty; 50
		, "หกสิบ" // hohkL sipL sixty; 60
		, "เจ็ดสิบ" // jetL sipL seventy; 70
		, "แปดสิบ" // bpaaetL sipL eighty; 80
		, "เก้าสิบ" // gaoF sipL ninety; 90
        };
        word1N = new String[]
		{ "สิบ" // sipL ten; the number or quantity ten
		, "สิบเอ็ด" // sipL etL eleven; 11
		, "สิบสอง" // sipL saawngR twelve; 12
		, "สิบสาม" // sipL saamR thirteen; 13
		, "สิบสี่" // sipL seeL fourteen; 14
		, "สิบห้า" // sipL haaF firfteen; 15
		, "สิบหก" // sipL hohkL sixteen; 16
		, "สิบเจ็ด" // sipL jetL seventeen; 17
		, "สิบแปด" // sipL bpaaetL eighteen; 18
		, "สิบเก้า" // sipL gaoF nineteen; 19
        };

        wordN0 = new String[]
        { "สิบ" // sipL ten; the number or quantity ten
        };
        setMorphem("z1", "สิบ");    // sipL ten; the number or quantity ten
        setMorphem("h1", "ร้อย");   // raawyH hundred; the number one hundred; 100
        setMorphem("t1", "พัน");    // phanM thousand; the number one thousand (1,000)
        setMorphem("tz", "หมื่น");   // meuunL ten-thousand (10,000)
        setMorphem("m1", "ล้าน");   // laanH million; the number one million (1,000,000)
        setMorphem("mh", "ร้อยล้าน");// raawyH laanH one hundred million (100,000,000)

	/*
        // adapt to German consonants k, z instead of c
        for (int iw = 2; iw < wordN000.length; iw ++) {
            wordN000[iw] = wordN000[iw]
                            .replaceAll("deci", "dezi")
                            .replaceAll("oct" , "okt" );
            wordN000[iw] = Character.toUpperCase(wordN000[iw].charAt(0))
                        + wordN000[iw].substring(1);
        } // for iw
		, "ร้อย" // raawyH hundred; the number one hundred; 100
		, "ร้อยหนึ่ง" // raawyH neungL one hundred one; 101
		, "พัน" // phanM thousand; the number one thousand (1,000)
		, "สองพัน" // saawngR phanM two-thousand (2,000)
		, "สี่พัน" // seeL phanM four thousand (4,000)
		, "หมื่น" // meuunL ten-thousand (10,000)
		, "แสน" // saaenR hundred-thousand (100,000)
		, "ล้าน" // laanH million; the number one million (1,000,000)
	*/
	/*
        setMorphem("mh", "億");    // (ichi-oku)
        setMorphem("wm", "\uc6d4"); 

        setMorphem("m2", "lionen");
        setMorphem("m3", "liarde");
        setMorphem("m4", "liarden");
        setMorphem("p0", " ");
        // setMorphem("p1", "s"); // for ein"s"?
        setMorphem("p2", "e");
        setMorphem("p3", "und");
    */
        enumerateMorphems();
    } // Constructor
    
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
            result.append((char) ('\u0e50' + Character.digit(number.charAt(chpos), 10)));
            chpos ++;
        } // while chpos
        return result.toString();
    } // spellIdeographic

    /** Returns the month's name
     *  @param month month's number, >= 1 and <= 12
     *  @return word denoting the month
     */
    public String spellMonth(int month) {
        String result = Integer.toString(month);
        if (month >= 0 && month <= 12) {
            result = (new String [] 
                    { "เดือน"   // month of the year
                    , "มกราคม" // mohkH gaL raaM khohmM   // 1
                    , "กุมภาพันธ์" // goomM phaaM phan[t]M  // 2
                    , "มีนาคม" // meeM naaM khohmM  // 3
                    , "เมษายน" // maehM saaR yohnM  // 4
                    , "พฤษภาคม" // 	phreutH saL phaaM khohmM  // 5
                    , "มิถุนายน" // 	miH thooL naaM yohnM  // 6
                    , "กรกฎาคม" // 	gaL raH gaL daaM khohmM   // 7
                    , "สิงหาคม" // singR haaR khohmM   // 8
                    , "กันยายน" // 	ganM yaaM yohnM  // 9
                    , "ตุลาคม" // 	dtooL laaM khohmM  // 10
                    , "พฤศจิกายน" // phreutH saL jiL gaaM yohnM  // 11
                    , "ธันวาคม" // 	thanM waaM khohmM   // 12
                    })[month];
        }
        return result;
    } // spellMonth

    /**	Returns the season's name
     *  @param season number of the quarter in the year:
     *  1 -> Spring, 2 -> Summer, 3 -> Autumn, 4 = Winter
     *  @return word denoting the season
     */
    public String spellSeason(int season) {
        String result = Integer.toString(season);
        if (season >= 0 && season <= 4) {
            result = (new String [] 
                    { "หน้า" // naaF
                    , "ฤดูใบไม้ผลิ" // reuH duuM baiM maaiH phliL
                    , "ฤดูร้อน" // reuH duuM raawnH
                    , "ฤดูใบไม้ร่วง" // reuH duuM baiM maaiH ruaangF
                    , "ฤดูหนาว" // reuH duuM naaoR
                    })[season];
        }
        return result;
    } // spellSeason

    /**	Returns the week day's name
     *  @param weekDay number of day in week, >= 0 and <= 7,
     *  1 -> Monday, 7 (and 0) -> Sunday
     *  @return word denoting the day in the week
     */
    public String spellWeekDay(int weekDay) {
        String result = Integer.toString(weekDay);
        if (weekDay >= 0 && weekDay <= 7) {
            result = (new String [] 
                    { "วันธรรมดา" // wan thammada = weekday
                   	, "วันจันทร์"   // wanM janM 
                    , "วันอังคาร"   // wanM angM khaanM
                   	, "วันพุธ"   // wanM phootH
                   	, "วันพฤหัสฯ"   // wanM phaH reuH hatL, colloquial; formal: วันพฤหัสบดี
                   	, "วันศุกร์"   // wanM sook[r]L
                   	, "วันเสาร์"   // wanM sao[r]R
                    , "วันอาทิตย์" // wanM aaM thit[y]H - Sunday
                    })[weekDay];
        }
        return result;
    } // spellWeekDay

} // ThaSpeller
/*
from http://www.thai-language.com/id/589826 
ศูนย์ 	suun[y]R	zero; the number or quantity zero
หนึ่ง 	neungL	one; the number or quantity one
สอง 	saawngR	two; the number or quantity two
สาม 	saamR	three; the number or quantity three
สี่ 	seeL	four; the number or quantity four
ห้า 	haaF	five; the number or quantity five
หก 	hohkL	six; the number or quantity six
เจ็ด 	jetL	seven; the number or quantity seven
แปด 	bpaaetL	eight; the number or quantity eight
เก้า 	gaoF	nine; the number or quantity nine
สิบ 	sipL	ten; the number or quantity ten
สิบเอ็ด	sipL etL	eleven; 11
สิบสอง	sipL saawngR	twelve; 12
สิบสาม	sipL saamR	thirteen; 13
สิบสี่	sipL seeL	fourteen; 14
สิบห้า	sipL haaF	firfteen; 15
สิบหก	sipL hohkL	sixteen; 16
สิบเจ็ด	sipL jetL	seventeen; 17
สิบแปด	sipL bpaaetL	eighteen; 18
สิบเก้า	sipL gaoF	nineteen; 19
ยี่สิบ	yeeF sipL	twenty; 20
ยี่สิบเอ็ด	yeeF sipL etL	twenty-one; 21
ยี่สิบสอง	yeeF sipL saawngR	twenty-two; 22
ยี่สิบสาม	yeeF sipL saamR	twenty-three; 23
ยี่สิบสี่	yeeF sipL seeL	twenty-four; 24
ยี่สิบห้า	yeeF sipL haaF	twenty-five; 25
ยี่สิบหก	yeeF sipL hohkL	twenty-six; 26
ยี่สิบเจ็ด	yeeF sipL jetL	twenty-seven; 27
ยี่สิบแปด	yeeF sipL bpaaetL	twenty-eight; 28
ยี่สิบเก้า	yeeF sipL gaoF	twenty-nine; 29
สามสิบ	saamR sipL	thirty; 30
สามสิบเอ็ด	saamR sipL etL	[spoken] thirty-one
สี่สิบ	seeL sipL	forty; 40
ห้าสิบ	haaF sipL	fifty; 50
หกสิบ	hohkL sipL	sixty; 60
เจ็ดสิบ	jetL sipL	seventy; 70
แปดสิบ	bpaaetL sipL	eighty; 80
เก้าสิบ	gaoF sipL	ninety; 90
ร้อย 	raawyH	hundred; the number one hundred; 100
ร้อยหนึ่ง	raawyH neungL	one hundred one; 101
พัน 	phanM	thousand; the number one thousand (1,000)
สองพัน	saawngR phanM	two-thousand (2,000)
สี่พัน	seeL phanM	four thousand (4,000)
หมื่น 	meuunL	ten-thousand (10,000)
แสน 	saaenR	hundred-thousand (100,000)
ล้าน 	laanH	million; the number one million (1,000,000)
สิบล้าน	sipL laanH	ten million (10,000,000)
ร้อยล้าน	raawyH laanH	one hundred million (100,000,000)
พันล้าน	phanM laanH	billion (1,000,000,000)
หมื่นล้าน	meuunL laanH	ten-billion (10,000,000,000)
แสนล้าน	saaenR laanH	one hundred-billion (100,000,000,000)
ล้านล้าน	laanH laanH	One trillion (1,000,000,000,000)
กัป 	gapL	kalp, or eon, a vast period of time, the period of time between creation and recreation of the world and universe. It is given in Hindu chronology as 4,320,000,000 years
กัลป์ 	gan[p]M	eternity; a very long period of time
อสงไขย 	aL sohngR khaiR	[a number followed by 140 ciphers or โกฏิ 26 (10 million26)]
หนึ่งโกฏิ	neungL gohtL	[an ancient numeral that equals] ten million
ตัวเลขโรมัน 	dtuaaM laehkF rohM manM	Roman numeral(s)
หมื่นสองพันสามร้อยสี่สิบห้า
meuunL saawngR phanM saamR raawyH seeL sipL haaF
twelve thousand, three hundred forty-five

Numbers
	
0
1
2
3
4
5
6
7
8
9
10
11
12
13
14
15
16
17
18
19
20
21
22
23
24
25
26
27
28
29
30
31
32
40
41
42
50
60
70
80
90
100
200
300
400
500
1000
10,000
20,000
100,000
200,000
300,000
400,000
500,000
one million
one billion
	

Suun
Neung
Sawng
Saam
Sii
Haa
Hok
Jeht
Bairt
Kao
Sib
Sib - et
Sib - sawng
Sib - saam
Sib - sii
Sib - haa
Sib - hok
Sib - Jeht
Sib - bairt
Sib - kao
Yi - sib
Yi - sib - et
Yi - sib - sawng
Yi - sib - saam
Yi - sib - sii
Yi - sib - haa
Yi - sib - hok
Yi - sib - jeht
Yi - sib- bairt
Yi - sib - kao
Saam - sib
Saam - sib - et
Saam - sib - sawng
Sii - sib
Sii - sib - et
Sii - sib - sawng
Haa - sib
Hok - sib
Jeht - sib
Bairt - sib
Kao - sib
Neung - rawy
Sawng - rawy
Saam - rawy
Sii - rawy
Haa - rawy
Phan
Neung - Meun
Sawng - meun
Neung - Saen
Sawng - saen
Saam - saen
Sii - saen
Haa - saen
Neung - Laan
Phan - Laan

Colours
Brown
Blue
Black
Grey
Green
Magnolia
Purple
Pink
Red
Sky Blue
White
Yellow
Light
Dark
Colour
	

Sii naam taan
Sii naam ngoen
Sii dam
Sii thao
Sii kiaow
Sii pheuak
Sii muang
Sii chomphuu
Sii daeng
Sii faa
Sii khao
Sii leuang
Awn
Kae
Sii

Weekdays
Sunday
Monday
Tuesday
Wednesday
Thursday
Friday
Saturday

wan aathit
wan jan
wan angkhaan
wan phut
wan phreuhat
wan suk
wan sao

Months
Months ending in 31 days are known in Thai as "Khom" while months ending in 30 days are known as "Yon". February has its own ending, "Phan". Also, in common speech Thai's often shorten a term for a month, so that "deuan mokaraa khom" (January) is just spoken as "deuan mokaraa"
January
February
March
April
May
June
July
August
September
October
November
December
deuan mokaraa - khom
deuan kumphaa - phan
deuan miinaa - khom
deuan mehsaa - yon
deuan phreutaphaa - khom
deuan mithunaa - yon
deuan karakadaa - khom
deuan singhaa - khom
deuan kanyaa -yon
deuan tulaa - khom
deuan phreutsajikaa - yon
deuan thanawaa - khom 
*/
