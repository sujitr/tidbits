#!/bin/bash

:'
This is a single window log terminal to tail & view two log files together, given the initial prefix patterns of them.

This script scans the log folder to read the latest of both log files automatically so no need to specify any file name
while issuing this script command.

The log folder will have both the log files kept in daily rotation manner, where older log files are also present.
Apart from that the individual log files are being generated in parallel with 4 engines of the application which
can run in parallel.

This script picks up the latest of both the log files in the folder, irrespective of the engine and rotation sequence.
Pickup happens based on latest log file timestamp which was changed most recently.

The script tails both log files in one tail command and keep refreshing them every 10 seconds for last 5 lines after
clearing the screen, everytime. This reduces cluttering.
'

#this is the folder location where log files will be present
logPath='/local/data/logs/'
cd $logPath
while : ; do
trap - 1 2 15
# this picks up the latest log file starting with 'dimStd..'
latestLogFile1=`ls -t dimStd*.* | head -n1`
# this picks the latest log file with pattern 'SHARC1_cswReportError.log' or 'SHARC2_cswReportError.log'
latestLogFile2=`ls -t SHARC*_cswReportError.log | head -n1`
clear
tail -f -n5 $latestLogFile1 $latestLogFile2 &
tailpid=$!
#explicitly catching the control+c key press to abort the execution of this script
trap 'echo " [Interrupt] Log tail aborted!"; kill -9 $tailpid; exit 0' 1 2 15
sleep 10
# keep killing the last tail command to refresh the screen top
kill -9 $tailpid
done
