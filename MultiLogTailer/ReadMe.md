# Multiple Log file tail Script

This shell script can be used to tail two (or more, after changes) log files to be tailed
simultaneously for last 'n' lines (currently configured for 5 lines) every 10 seconds, in the same terminal and without scroll trail.

The terminal refreshes every 10 seconds to clear out the old logs and print the fresh output.

This script picks up the latest of both the log files in the folder, irrespective of the instance and rotation sequence.
Pickup happens based on latest log file timestamp which was changed most recently. The log files are selected with the match of name pattern. 
