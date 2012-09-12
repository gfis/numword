#!/usr/bin/perl

# rename some variables
# @(#) $Id: rename.pl 113 2009-04-06 14:57:07Z gfis $
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

use strict;
use utf8;

my $file = shift (@ARGV);
undef $/; # slurp mode
open (IN, "<", $file) || die "cannot read $file";
my $buffer = <IN>;
close IN;

# $buffer =~ s[previousZeroTriple]
#           [previousZeroTuple]g;
$buffer =~ s[logTuple]
            [lenTuple]g;
$buffer =~ s[log1000]
            [logTuple]g;
open (OUT, ">", $file) || die "cannot write $file";
print OUT $buffer;
close OUT;
