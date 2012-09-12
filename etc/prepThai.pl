#!/usr/bin/perl

# prepare Thai cardinal words
# @(#) $Id: prepThai.pl 113 2009-04-06 14:57:07Z gfis $
# 2009-04-06: Dr. Georg Fischer
#-------------------------------------------------------
#
#  Copyright 2006 Dr. Georg Fischer <punctum at punctum dot kom>
# 
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
# 
#       http://www.apache.org/licenses/LICENSE-2.0
# 
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#
#-------------------------------------------------------
use strict;

while (<DATA>) {
	my ($thai, $phon, @rest) = split;
	
	print "\t\t, \"$thai\" // $phon ", join(" ", @rest), "\n";
} # while
__DATA__
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


