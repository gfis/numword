#!/usr/bin/perl

# rename some variables
# @(#) $Id: rename.pl 113 2009-04-06 14:57:07Z gfis $
# 2022-01-25: for the log4j 1.x -> 2.17.1 conversion
# 2009-04-06: Dr. Georg Fischer
#-------------------------------------------------------

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
