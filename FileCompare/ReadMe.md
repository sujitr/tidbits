File Compare Utility
====================
This package is a utility library which provides ways to compare two files.
Specifically it has two methods to compare files by employing two different mechanisms.

## Normal byte match method
This is a normal approach for matching files byte by byte reading from filesystem. This takes a bit more time as more IO is involved.

## Memory Mapped file comparison approach
In this way both the files are mapped to memory and matched with byte sequence. This is usually faster as no IO is involved.