#!/usr/bin/perl

# replace < and > in Javadoc comments
# @(#) $Id: rename.pl 113 2009-04-06 14:57:07Z gfis $
# 2017-05-28: Dr. Georg Fischer
#-------------------------------------------------------
#
#  Copyright 2017 Dr. Georg Fischer <punctum at punctum dot kom>
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
use strict;

my @buffer = ();
my $file = shift (@ARGV);
print STDERR "process $file\n";
open (IN, "<", $file) || die "cannot read $file";
while (<IN>) {
	if (m{\A\s{0,10}\*}) { # in comment
		s{\<\-}{\&lt;\-}g;
		s{\-\>}{\-\&gt;}g;
		s{\<\=}{\&lt;\=}g;
		s{\>\=}{\&gt;\=}g;
	}
	push(@buffer, $_);
} # while
close IN;

open (OUT, ">", $file) || die "cannot write $file";
print OUT join("", @buffer);
close OUT;
